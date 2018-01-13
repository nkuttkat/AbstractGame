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

import abstractgame.events.unitevents.UnitDirectionChangedEvent;

/**
 * The listener interface for receiving unitDirectionChanged events. The class
 * that is interested in processing a unitDirectionChanged event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's addUnitDirectionChangedListener method. When
 * the unitDirectionChanged event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UnitDirectionChangedEvent
 */
public interface UnitDirectionChangedListener {

    /**
     * Direction changed.
     *
     * @param event the event
     */
    public void unitDirectionChanged(UnitDirectionChangedEvent event);

}
