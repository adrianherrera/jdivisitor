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

package org.jdivisitor.debugger.event;

import org.jdivisitor.debugger.event.visitor.EventVisitor;
import org.jdivisitor.debugger.event.visitor.Visitable;
import com.sun.jdi.event.ClassUnloadEvent;

/**
 * Visitable class unload event.
 * 
 * @author Adrian Herrera
 * @see ClassUnloadEvent
 */
public class VisitableClassUnloadEvent implements Visitable {

    private final ClassUnloadEvent event;
    
    public VisitableClassUnloadEvent(ClassUnloadEvent event) {
        this.event = event;
    }
    
    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(event);
    }
}
