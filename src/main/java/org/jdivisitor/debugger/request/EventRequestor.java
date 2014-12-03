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

package org.jdivisitor.debugger.request;

import com.sun.jdi.request.EventRequestManager;

/**
 * Interface for classes to request specific JDI events be sent to the debugger.
 * Requests are managed (created/deleted) via a {@link EventRequestManager}.
 * 
 * @author Adrian Herrera
 * @see EventRequestManager
 */
public interface EventRequestor {

    /**
     * Request specific JDI events to be sent to the debugger.
     * 
     * @param erm Event request manager
     */
    public void requestEvents(EventRequestManager erm);
}
