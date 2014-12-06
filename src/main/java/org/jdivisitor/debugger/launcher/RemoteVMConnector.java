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

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;

/**
 * Attach (via a socket) to a listening virtual machine in debug mode.
 *
 * @author Adrian Herrera
 *
 */
public class RemoteVMConnector extends VMConnector {

    /**
     * Socket connection details.
     */
    private final InetSocketAddress socketAddress;

    /**
     * Constructor.
     *
     * @param hostname Name of the host to connect to
     * @param port Port number the virtual machine is listening on
     */
    public RemoteVMConnector(String hostname, int port) {
        this(new InetSocketAddress(hostname, port));
    }

    /**
     * Constructor.
     *
     * @param sockAddr Socket address structure to connect to.
     */
    public RemoteVMConnector(InetSocketAddress sockAddr) {
        socketAddress = Validate.notNull(sockAddr);
    }

    @Override
    public VirtualMachine connect() throws Exception {
        List<AttachingConnector> connectors = Bootstrap.virtualMachineManager()
                .attachingConnectors();
        AttachingConnector connector = findConnector(
                "com.sun.jdi.SocketAttach", connectors);
        Map<String, Connector.Argument> arguments = connectorArguments(connector);

        VirtualMachine vm = connector.attach(arguments);

        // TODO - redirect stdout and stderr?

        return vm;
    }

    /**
     * Set the socket-attaching connector's arguments.
     *
     * @param connector A socket-attaching connector
     * @return The socket-attaching connector's arguments
     */
    private Map<String, Connector.Argument> connectorArguments(
            AttachingConnector connector) {
        Map<String, Connector.Argument> arguments = connector
                .defaultArguments();

        arguments.get("hostname").setValue(socketAddress.getHostName());
        arguments.get("port").setValue(
                Integer.toString(socketAddress.getPort()));

        return arguments;
    }
}
