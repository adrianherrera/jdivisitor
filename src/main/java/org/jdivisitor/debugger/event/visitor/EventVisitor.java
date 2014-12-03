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

package org.jdivisitor.debugger.event.visitor;

import com.sun.jdi.event.AccessWatchpointEvent;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.MonitorContendedEnterEvent;
import com.sun.jdi.event.MonitorContendedEnteredEvent;
import com.sun.jdi.event.MonitorWaitEvent;
import com.sun.jdi.event.MonitorWaitedEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.WatchpointEvent;

/**
 * Interface for classes that will implement the Visitor pattern to handle JDI
 * events.
 *
 * <p>
 * Events are an occurrence in a target VM that are of interest to a debugger.
 * The debugger back end will notify the front end of an event and an
 * {@code EventVisitor} will handle (or "visit") that event.
 * </p>
 *
 * @author Adrian Herrera
 * @see Visitable
 */
public interface EventVisitor {

    /**
     * Visit an {@code AccessWatchpointEvent}.
     *
     * @param event Event to visit
     */
    void visit(AccessWatchpointEvent event);

    /**
     * Visit a {@code BreakpointEvent}.
     *
     * @param event Event to visit
     */
    void visit(BreakpointEvent event);

    /**
     * Visit a {@code ClassPrepareEvent}.
     *
     * @param event Event to visit
     */
    void visit(ClassPrepareEvent event);

    /**
     * Visit a {@code ClassUnloadEvent}.
     *
     * @param event Event to visit
     */
    void visit(ClassUnloadEvent event);

    /**
     * Visit a {@code ExceptionEvent}.
     *
     * @param event Event to visit
     */
    void visit(ExceptionEvent event);

    /**
     * Visit a {@code LocatableEvent}.
     *
     * @param event Event to visit
     */
    void visit(LocatableEvent event);

    /**
     * Visit a {@code MethodEntryEvent}.
     *
     * @param event Event to visit
     */
    void visit(MethodEntryEvent event);

    /**
     * Visit a {@code MethodExitEvent}.
     *
     * @param event Event to visit
     */
    void visit(MethodExitEvent event);

    /**
     * Visit a {@code ModificationWatchpointEvent}.
     *
     * @param event Event to visit
     */
    void visit(ModificationWatchpointEvent event);

    /**
     * Visit a {@code MonitorContendedEnteredEvent}.
     *
     * @param event Event to visit
     */
    void visit(MonitorContendedEnteredEvent event);

    /**
     * Visit a {@code MonitorContendedEnterEvent}.
     *
     * @param event Event to visit
     */
    void visit(MonitorContendedEnterEvent event);

    /**
     * Visit a {@code MonitorWaitedEvent}.
     *
     * @param event Event to visit
     */
    void visit(MonitorWaitedEvent event);

    /**
     * Visit a {@code MonitorWaitEvent}.
     *
     * @param event Event to visit
     */
    void visit(MonitorWaitEvent event);

    /**
     * Visit a {@code StepEvent}.
     *
     * @param event Event to visit
     */
    void visit(StepEvent event);

    /**
     * Visit a {@code ThreadDeathEvent}.
     *
     * @param event Event to visit
     */
    void visit(ThreadDeathEvent event);

    /**
     * Visit a {@code ThreadStartEvent}.
     *
     * @param event Event to visit
     */
    void visit(ThreadStartEvent event);

    /**
     * Visit a {@code VMDeathEvent}.
     *
     * @param event Event to visit
     */
    void visit(VMDeathEvent event);

    /**
     * Visit a {@code VMDisconnectEvent}.
     *
     * @param event Event to visit
     */
    void visit(VMDisconnectEvent event);

    /**
     * Visit a {@code VMStartEvent}.
     *
     * @param event Event to visit
     */
    void visit(VMStartEvent event);

    /**
     * Visit a {@code WatchpointEvent}.
     *
     * @param event Event to visit
     */
    void visit(WatchpointEvent event);
}
