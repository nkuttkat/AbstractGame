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

import javax.swing.JLabel;

import abstractgame.map.HexField;
import abstractgame.view.HexView;

/**
 * The Class Hex.
 *
 * @author Nils Kuttkat
 */
public class Hex extends HexView {

    private static final long serialVersionUID = 1L;
    /**
     * The image.
     */
    private JLabel image = new JLabel();

    /**
     * Instantiates a new hex.
     *
     * @param model the model
     * @param sideLength the side length
     * @param listener the listener
     */
    public Hex(HexField model, int sideLength, MouseListener listener) {
        super(model, sideLength);
        this.addMouseListener(listener);
        this.showBorder(true);
    }

    /**
     * Sets the image.
     *
     * @param image the new image
     */
    public void setImage(JLabel image) {
        this.remove(this.image);
        this.image = image;
        this.add(image);
        image.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

}
