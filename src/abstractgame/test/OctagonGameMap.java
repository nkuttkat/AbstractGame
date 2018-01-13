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
package abstractgame.test;

import nkutilities.Coordinate;
import abstractgame.map.AbstractMap;
import abstractgame.map.OctagonField;

/**
 * The Class OctagonGameMap.
 *
 * @author Nils Kuttkat
 */
public class OctagonGameMap extends AbstractMap {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7261355757954063682L;

    /**
     * Instantiates a new octagon game map.
     *
     * @param width the width
     * @param height the height
     */
    public OctagonGameMap(int width, int height) {
        super(width, height);
        for (int i = 0; i < this.getFields().length; i++) {
            for (int j = 0; j < this.getFields()[i].length; j++) {
                this.setField(new Coordinate(i, j), new OctagonField());
            }
        }
    }

}
