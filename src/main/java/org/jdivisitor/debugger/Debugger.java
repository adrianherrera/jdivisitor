/*
 * JDIVisitor
 * Copyright (C) 2014  Jason Fager, Adrian Herrera
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

package org.jdivisitor.debugger;

import org.apache.commons.lang3.Validate;

import org.jdivisitor.debugger.request.EventRequestor;
import org.jdivisitor.debugger.event.visitor.EventVisitor;
import com.sun.jdi.VirtualMachine;

/**
 * The front end debugger that interacts with the back end over JDI. A typical
 * workflow would involve the following:
 *
 * <ul>
 * <li>Instantiate a new {@code Debugger} object
 * <li>Call {@code requestEvents} to request specific events that should be sent
 * to the debugger
 * <li>Call {@code run} to start the debugger. An optional {@code EventVisitor}
 * object can be used to interact with the debugger
 * <ul>
 *
 * @author Jason Fager
 * @author Adrian Herrera
 */
public final class Debugger {

    /**
     * The running virtual machine.
     */
    private final VirtualMachine vm;

    /**
     * Create a new debugger for the given virtual machine.
     *
     * @param vm Virtual machine
     */
    public Debugger(VirtualMachine vm) {
        this.vm = Validate.notNull(vm);
    }

    /**
     * Get the underlying virtual machine.
     *
     * @return The underlying virtual machine
     */
    public VirtualMachine vm() {
        return vm;
    }

    /**
     * Request JDI events to be sent to the debugger.
     *
     * @param eventRequestor Requests JDI events
     */
    public void requestEvents(EventRequestor eventRequestor) {
        eventRequestor.requestEvents(vm.eventRequestManager());
    }

    /**
     * Run the underlying virtual machine with no event visitor and no timeout.
     */
    public void run() {
        run(0);
    }

    /**
     * Run the underlying virtual machine with no event visitor and a given
     * timeout (in milliseconds).
     *
     * @param milliseconds Timeout in milliseconds
     */
    public void run(long milliseconds) {
        run(null, milliseconds);
    }

    /**
     * Run the underlying virtual machine with the given event visitor and no
     * timeout.
     *
     * @param visitor Event visitor to handle events
     */
    public void run(EventVisitor visitor) {
        run(visitor, 0);
    }

    /**
     * Run the underlying virtual machine with the given event visitor and a
     * given timeout (in milliseconds).
     *
     * @param visitor Event visitor to handle events
     * @param milliseconds Timeout in milliseconds
     */
    public void run(EventVisitor visitor, long milliseconds) {
        EventThread eventThread = new EventThread(vm, visitor);

        eventThread.start();
        try {
            eventThread.join(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
