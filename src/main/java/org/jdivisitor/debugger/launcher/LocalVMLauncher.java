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
     * Constructor.
     *
     * @param mainClass The main class to launch. Cannot be {@code null} or
     * empty.
     */
    public LocalVMLauncher(String mainClass) {
        this(mainClass, StringUtils.EMPTY);
    }

    /**
     * Constructor.
     * 
     * @param mainClass The main class to launch. Cannot be {@code null} or
     * empty.
     * @param options The options to launch the virtual machine with
     */
    public LocalVMLauncher(String mainClass, String options) {
        this.mainClass = Validate.notNull(mainClass);
        this.options = Validate.notNull(options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VirtualMachine connect() throws Exception {
        List<LaunchingConnector> connectors = Bootstrap.virtualMachineManager()
                .launchingConnectors();
        LaunchingConnector connector = findConnector(
                "com.sun.jdi.CommandLineLaunch", connectors);
        Map<String, Connector.Argument> arguments = connectorArguments(connector);

        VirtualMachine vm = connector.launch(arguments);

        // TODO - Redirect stdout and stderr?

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
}
