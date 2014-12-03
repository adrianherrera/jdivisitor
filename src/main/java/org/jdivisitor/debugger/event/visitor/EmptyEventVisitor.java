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
 * Provides a visitor with empty method bodies to be overriden by subclasses.
 * 
 * @author Adrian Herrera
 */
public abstract class EmptyEventVisitor implements EventVisitor {

    @Override
    public void visit(AccessWatchpointEvent event) {
    }

    @Override
    public void visit(BreakpointEvent event) {
    }

    @Override
    public void visit(ClassPrepareEvent event) {
    }

    @Override
    public void visit(ClassUnloadEvent event) {
    }

    @Override
    public void visit(ExceptionEvent event) {
    }

    @Override
    public void visit(LocatableEvent event) {
    }

    @Override
    public void visit(MethodEntryEvent event) {
    }

    @Override
    public void visit(MethodExitEvent event) {
    }

    @Override
    public void visit(ModificationWatchpointEvent event) {
    }

    @Override
    public void visit(MonitorContendedEnteredEvent event) {
    }

    @Override
    public void visit(MonitorContendedEnterEvent event) {
    }

    @Override
    public void visit(MonitorWaitedEvent event) {
    }

    @Override
    public void visit(MonitorWaitEvent event) {
    }

    @Override
    public void visit(StepEvent event) {
    }

    @Override
    public void visit(ThreadDeathEvent event) {
    }

    @Override
    public void visit(ThreadStartEvent event) {
    }

    @Override
    public void visit(VMDeathEvent event) {
    }

    @Override
    public void visit(VMDisconnectEvent event) {
    }

    @Override
    public void visit(VMStartEvent event) {
    }

    @Override
    public void visit(WatchpointEvent event) {
    }

}
