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

import javax.swing.JPanel;

import abstractgame.map.AbstractMap;

/**
 * The MapView is a superclass for all concrete map views for instance the
 * {@link HexMapView} or the {@link SquareMapView}. A MapView is a visualized
 * {@link AbstractMap} which is always the underlying model.
 *
 * @author Nils Kuttkat
 */
public abstract class MapView extends JPanel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The model.
     */
    private AbstractMap model;

    /**
     * Instantiates a new map view.
     *
     * @param model the model
     */
    public MapView(AbstractMap model) {
        this.model = model;
        this.setLayout(null);
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public AbstractMap getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(AbstractMap model) {
        this.model = model;
    }
}
