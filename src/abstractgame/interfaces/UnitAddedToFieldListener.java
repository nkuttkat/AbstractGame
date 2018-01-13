/* 
 * Copyright (C) 2018 Nils Kuttkat <nkuttkat@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package abstractgame.interfaces;

import abstractgame.events.fieldevents.UnitAddedToFieldEvent;

/**
 * The listener interface for receiving unitAddedToField events. The class that
 * is interested in processing a unitAddedToField event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's addUnitAddedToFieldListener method. When
 * the unitAddedToField event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UnitAddedToFieldEvent
 */
public interface UnitAddedToFieldListener {

    /**
     * Unit added to field.
     *
     * @param event the event
     */
    public void unitAddedToField(UnitAddedToFieldEvent event);

}
