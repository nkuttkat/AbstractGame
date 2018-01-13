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
 * The Class SquareMapView is a graphical representation of a square
 * {@link AbstractMap}.
 *
 * @author Nils Kuttkat
 */
public abstract class SquareMapView extends MapView {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new square map view.
     *
     * @param model the model
     * @param fieldSize the field size
     */
    public SquareMapView(AbstractMap model, int fieldSize) {
        super(model);
        this.initialize(fieldSize);
    }

    /**
     * Initialize.
     *
     * @param fieldSize the field size
     */
    protected void initialize(int fieldSize) {
        SquareView prototype = new SquareView(fieldSize);
        this.setSize(this.getModel().getFields().length * (prototype.getWidth() - 1) + 1,
                this.getModel().getFields()[0].length * (prototype.getHeight() - 1) + 1);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());
    }

}
