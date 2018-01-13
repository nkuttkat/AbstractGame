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
package abstractgame.view.test;

import java.awt.event.MouseListener;

import abstractgame.map.AbstractField;
import abstractgame.map.AbstractMap;
import abstractgame.map.HexField;
import abstractgame.view.HexMapView;
import abstractgame.view.HexView;

/**
 * The Class HexGameMapView.
 *
 * @author Nils Kuttkat
 */
public class HexGameMapView extends HexMapView {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new hex game map view.
     *
     * @param model the model
     * @param fieldSize the field size
     * @param listener the listener
     */
    public HexGameMapView(AbstractMap model, int fieldSize,
            MouseListener listener) {
        super(model, fieldSize);
        for (AbstractField[] fieldArray : this.getModel().getFields()) {
            for (AbstractField field : fieldArray) {
                this.add(new HexView((HexField) field, fieldSize));
            }
        }
    }
}
