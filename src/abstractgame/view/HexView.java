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

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import abstractgame.map.HexField;
import java.awt.Color;

/**
 * The Class HexView is a graphical representation of a hexagonal
 * {@link AbtractField}.
 *
 * @author Nils Kuttkat
 */
public class HexView extends AbstractFieldView {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new hex view.
     *
     * @param model the model
     */
    public HexView(HexField model) {
        super(model);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.view.FieldView#initialize(int)
     */
    @Override
    public void initialize(int sideLength) {
        this.setShape(new Polygon());
        this.setSize(2 * sideLength + 1, (int) (Math.round(Math.sin(Math.PI / 3) * sideLength) * 2) + 1);
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());
        this.setPreferredSize(this.getSize());
        this.setBorderColor(Color.BLACK);
        this.setForeground(Color.LIGHT_GRAY);
        this.showBorder(true);
        int height = this.getHeight() - 1;
        // calculate the position of the six corners based on the side length
        ((Polygon) this.getShape()).addPoint(Math.round(sideLength / 2), 0);
        ((Polygon) this.getShape()).addPoint(Math.round(sideLength / 2) + sideLength, 0);
        ((Polygon) this.getShape()).addPoint(2 * sideLength, height / 2);
        ((Polygon) this.getShape()).addPoint(Math.round(sideLength / 2) + sideLength, (2 * height / 2));
        ((Polygon) this.getShape()).addPoint(Math.round(sideLength / 2), (2 * height / 2));
        ((Polygon) this.getShape()).addPoint(0, height / 2);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.view.FieldView#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        int x, y, unevenYOffset, xStepSize, yStepSize;

        if (this.getModel() != null) {
            x = this.getModel().getPosition().getX();
            y = this.getModel().getPosition().getY();
            xStepSize = Math.round(this.getWidth() - (this.getWidth() / 4) - 1);
            yStepSize = Math.round(this.getHeight() - 1);

            if (x % 2 == 0) {
                unevenYOffset = 0;
            } else {
                unevenYOffset = this.getHeight() / 2;
            }

            this.setLocation(new Point(x * xStepSize, y * yStepSize + unevenYOffset));
        }

        super.paintComponent(g);
    }
}
