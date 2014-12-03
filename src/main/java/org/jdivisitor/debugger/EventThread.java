 /*
  * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
  * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  *
  * This code is free software; you can redistribute it and/or modify it
  * under the terms of the GNU General Public License version 2 only, as
  * published by the Free Software Foundation.  Oracle designates this
  * particular file as subject to the "Classpath" exception as provided
  * by Oracle in the LICENSE file that accompanied this code.
  *
  * This code is distributed in the hope that it will be useful, but WITHOUT
  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
  * version 2 for more details (a copy is included in the LICENSE file that
  * accompanied this code).
  *
  * You should have received a copy of the GNU General Public License version
  * 2 along with this work; if not, write to the Free Software Foundation,
  * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
  *
  * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
  * or visit www.oracle.com if you need additional information or have any
  * questions.
  */

package org.jdivisitor.debugger;

import org.apache.commons.lang3.Validate;

import org.jdivisitor.debugger.event.transform.EventTransformer;
import org.jdivisitor.debugger.event.visitor.EventVisitor;
import org.jdivisitor.debugger.event.visitor.Visitable;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;

/**
 * Processes incoming JDI events. As each event occurs, an {@code EventVisitor}
 * will handle the event.
 * 
 * @author Robert Field
 * @author Adrian Herrera
 */
class EventThread extends Thread {

    /**
     * The running virtual machine.
     */
    private final VirtualMachine vm;

    /**
     * Event visitor.
     */
    private final EventVisitor eventVisitor;

    /**
     * Connected to the virtual machine.
     */
    private boolean connected = true;

    /**
     * Create a new event thread to process JDI events. This event thread will
     * essentially step through the target and ignore all events.
     * 
     * @param vm The virtual machine generating the JDI events
     */
    public EventThread(VirtualMachine vm) {
        this(vm, null);
    }

    /**
     * Create a new event thread to process JDI events.
     * 
     * @param vm The virtual machine generating the JDI events
     * @param visitor Event visitor
     */
    public EventThread(VirtualMachine vm, EventVisitor visitor) {
        super("jdi-event-thread");
        this.vm = Validate.notNull(vm);
        this.eventVisitor = visitor;
    }

    /**
     * Run the event handling thread.
     * 
     * As long as the thread remains connected to the VM, events are removed
     * from the queue and dispatched.
     */
    @Override
    public void run() {
        EventQueue queue = vm.eventQueue();

        while (connected) {
            try {
                EventSet eventSet = queue.remove();

                EventIterator eventIterator = eventSet.eventIterator();
                while (eventIterator.hasNext()) {
                    handleEvent(eventIterator.nextEvent());
                }
                eventSet.resume();
            } catch (InterruptedException ie) {
                vm.dispose();
                break;
            } catch (VMDisconnectedException vmde) {
                handleDisconnectedException();
                break;
            }
        }
    }

    /**
     * A {@link VMDisconnectedException} has happened while dealing with another
     * event. We need to flush the event queue, dealing only with exit events (
     * {@link VMDeatEvent} and {@link VMDisconnectEvent}) so that we terminate
     * correctly.
     */
    private void handleDisconnectedException() {
        EventQueue queue = vm.eventQueue();

        while (connected) {
            try {
                EventSet eventSet = queue.remove();

                EventIterator eventIterator = eventSet.eventIterator();
                while (eventIterator.hasNext()) {
                    Event event = eventIterator.nextEvent();

                    if (event instanceof VMDeathEvent) {
                        handleEvent(event);
                    } else if (event instanceof VMDisconnectEvent) {
                        handleEvent(event);
                        connected = false;
                    }
                }
                eventSet.resume();
            } catch (InterruptedException ie) {
                vm.dispose();
                break;
            } catch (VMDisconnectedException vmde) {
                break;
            }
        }
    }

    /**
     * Handle the event by invoking the event visitor.
     * 
     * @param event Event to handle
     */
    private void handleEvent(Event event) {
        if (eventVisitor != null) {
            Visitable visitableEvent = EventTransformer.transform(event);
            visitableEvent.accept(eventVisitor);
        }
    }
}
