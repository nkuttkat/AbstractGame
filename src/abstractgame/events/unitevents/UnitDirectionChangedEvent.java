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
package abstractgame.events.unitevents;

/**
 * A UnitDirectionChangedEvent occurs every time a unit changes it's direction.
 *
 * @author Nils Kuttkat
 */
public class UnitDirectionChangedEvent extends UnitEvent {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The previous direction.
     */
    private int previousDirection;

    /**
     * Instantiates a new unit direction changed event.
     *
     * @param source the source
     * @param previousDirection the previous direction
     */
    public UnitDirectionChangedEvent(Object source, int previousDirection) {
        super(source);
    }

    /**
     * Gets the previous direction.
     *
     * @return the previous direction
     */
    public int getPreviousDirection() {
        return this.previousDirection;
    }
}
