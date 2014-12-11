/*
 * JDIVisitor
 * Copyright (C) 2014  Adrian Herrera
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.jdivisitor.debugger.launcher;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

/**
 * Create and start a local virtual machine in debug mode.
 *
 * @author Adrian Herrera
 */
public class LocalVMLauncher extends VMConnector {

    /**
     * The name of the main class to launch.
     */
    private final String mainClass;

    /**
     * Virtual machine options.
     */
    private final String options;

    /**
     * Output stream that the virtual machine's stdout will be redirected to.
     */
    private final OutputStream outStream;

    /**
     * Output stream that the virtual machine's stderr will be redirected to.
     */
    private final OutputStream errStream;

    /**
     * Constructor. <br>
     * <br>
     * Specify a main class only. No options will be used when starting the
     * virtual machine (debugee). The debugee virtual machine's {@code stdout}
     * and {@code stderr} will be redirected to the local {@code stdout} and
     * {@code stderr} respectively.
     *
     * @param mainClass The main class to launch (cannot be {@code null})
     */
    public LocalVMLauncher(String mainClass) {
        this(mainClass, StringUtils.EMPTY);
    }

    /**
     * Constructor. <br>
     * <br>
     * Specify a main class and options to launch it with. The debugee virtual
     * machine's {@code stdout} and {@code stderr} will be redirected to the
     * local {@code stdout} and {@code stderr} respectively.
     *
     * @param mainClass The main class to launch (cannot be {@code null})
     * @param options The options to launch the virtual machine with
     */
    public LocalVMLauncher(String mainClass, String options) {
        this(mainClass, options, System.out, System.err);
    }

    /**
     * Constructor. <br>
     * <br>
     * Specify a main class and options to launch it with. The debugee virtual
     * machine's {@code stdout} and {@code stderr} can be redirected.
     *
     * @param mainClass The main class to launch (cannot be {@code null})
     * @param options The options to launch the virtual machine with (cannot be
     * {@code null})
     * @param out Where to redirect the debugee's {@code stdout}. A {@code null}
     * value means the debugee's {@code stdout} will be ignored
     * @param err Where to redirect the debugee's {@code stderr}. A {@code null}
     * value means the debugee's {@code stderr} will be ignored
     */
    public LocalVMLauncher(String mainClass, String options, OutputStream out,
            OutputStream err) {
        this.mainClass = Validate.notNull(mainClass);
        this.options = Validate.notNull(options);
        outStream = out;
        errStream = err;
    }

    @Override
    public VirtualMachine connect() throws Exception {
        List<LaunchingConnector> connectors = Bootstrap.virtualMachineManager()
                .launchingConnectors();
        LaunchingConnector connector = findConnector(
                "com.sun.jdi.CommandLineLaunch", connectors);
        Map<String, Connector.Argument> arguments = connectorArguments(connector);

        VirtualMachine vm = connector.launch(arguments);
        redirectOutput(vm);

        return vm;
    }

    /**
     * Set the launching connector's arguments.
     *
     * @param connector A launching connector
     * @return The launching connector's arguments
     */
    private Map<String, Connector.Argument> connectorArguments(
            LaunchingConnector connector) {
        Map<String, Connector.Argument> arguments = connector
                .defaultArguments();

        arguments.get("main").setValue(mainClass);
        arguments.get("options").setValue(options);

        return arguments;
    }

    /**
     * Redirect the virtual machine's output ({@code stdout} and {@code stdin}).
     *
     * @param vm Virtual machine
     */
    private void redirectOutput(VirtualMachine vm) {
        Process process = vm.process();

        if (outStream != null) {
            redirectStream("jdi-out", process.getInputStream(), outStream);
        }

        if (errStream != null) {
            redirectStream("jdi-err", process.getErrorStream(), errStream);
        }
    }

    /**
     * Redirect an input stream to an output stream in a new daemon thread.
     *
     * @param name Thread name
     * @param in Input stream
     * @param out Output stream
     */
    private static void redirectStream(String name, InputStream in,
            OutputStream out) {
        Thread thread = new StreamRedirectThread(name, in, out);

        thread.setDaemon(true);
        thread.start();
    }
}
