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
package abstractgame.events.fieldevents;

import java.util.EventObject;

import abstractgame.map.AbstractField;

/**
 * The Class FieldEvent is an abstract superclass for various field events.
 *
 * @author Nils Kuttkat
 */
public abstract class FieldEvent extends EventObject {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new field event.
     *
     * @param source the source
     */
    public FieldEvent(Object source) {
        super(source);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#getSource()
     */
    @Override
    public AbstractField getSource() {
        return (AbstractField) super.getSource();
    }

}
