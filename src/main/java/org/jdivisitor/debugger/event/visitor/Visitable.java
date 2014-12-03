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

import java.io.Serializable;

/**
 * Interface for classes that will implement the Visitor pattern.
 * 
 * @author Adrian Herrera
 * @see EventVisitor
 */
public interface Visitable extends Serializable {

    /**
     * Method to accept a visitor. This method usually doesn't do more than
     * {@code visitor.visit(this)}.
     * 
     * @param visitor The visitor
     */
    void accept(EventVisitor visitor);
}
