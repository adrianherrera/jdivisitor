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

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;

/**
 * Abstract class for connecting to a target virtual machine in debug mode.
 *
 * @author Adrian Herrera
 */
public abstract class VMConnector {

    /**
     * Connect to the target virtual machine in debug mode.
     *
     * @return The virtual machine instance
     * @throws Exception An error occurred when connecting to the virtual
     *         machine
     */
    public abstract VirtualMachine connect() throws Exception;

    /**
     * Find the virtual machine connector with the given name.
     *
     * @param connectorName The connector to find
     * @param connectors List of connectors to search
     * @return The connector
     */
    protected static <T extends Connector> T findConnector(
            String connectorName, List<T> connectors) {
        for (T connector : connectors) {
            if (connector.name().equals(connectorName)) {
                return connector;
            }
        }

        String exceptionMsg = String.format("Connector '%s' not found",
                connectorName);
        throw new RuntimeException(exceptionMsg);
    }
}
