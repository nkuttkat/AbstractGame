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
package abstractgame.view;

import abstractgame.map.AbstractMap;

/**
 * The Class HexMapView is a graphical representation of a hexagonal
 * {@link AbstractMap}.
 *
 * @author Nils Kuttkat
 */
public abstract class HexMapView extends MapView {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new hex map view.
     *
     * @param model the model
     * @param fieldSize the field size
     */
    public HexMapView(AbstractMap model, int fieldSize) {
        super(model);
        this.initialize(fieldSize);
    }

    /**
     * Initializes the map especially the dimensions based on the.
     *
     * @param sideLength the side length {@link HexView}'s dimensions.
     */
    protected void initialize(int sideLength) {
        HexView prototype = new HexView(sideLength);
        int width, height;
        width = (int) Math.round(this.getModel().getFields().length
                * ((prototype.getWidth() - 1) * 0.75))
                + (int) Math.round((prototype.getWidth()) * 0.25) + 1;
        height = this.getModel().getFields()[0].length * (prototype.getHeight() - 1) + prototype.getHeight() / 2 + 1;
        this.setSize(width, height);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());
    }
}
