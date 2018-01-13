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
package abstractgame.events.gameevents;

import java.util.EventObject;

import abstractgame.game.AbstractGame;
import abstractgame.unit.AbstractUnit;

/**
 * The Class GameUnitsChangedEvent is an abstract superclass for classes
 * handling unit changes.
 *
 * @author Nils Kuttkat
 */
public class GameUnitsChangedEvent extends EventObject {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unit.
     */
    private final AbstractUnit unit;

    /**
     * Instantiates a new game units changed event.
     *
     * @param source the source
     * @param abstractUnit the abstract unit
     */
    public GameUnitsChangedEvent(Object source, AbstractUnit abstractUnit) {
        super(source);
        this.unit = abstractUnit;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#getSource()
     */
    @Override
    public AbstractGame getSource() {
        return (AbstractGame) super.getSource();
    }

    /**
     * Gets the unit that caused the event.
     *
     * @return the unit
     */
    public AbstractUnit getUnit() {
        return this.unit;
    }

}
