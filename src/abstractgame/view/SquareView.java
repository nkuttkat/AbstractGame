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

import abstractgame.map.AbstractField;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.Color;

/**
 * The Class HexView is a graphical representation of a square or octagonal
 * {@link AbtractField}.
 *
 * @author Nils Kuttkat
 */
public class SquareView extends AbstractFieldView {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new square view.
     *
     * @param model the model
     */
    public SquareView(AbstractField model) {
        super(model);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.view.FieldView#initialize(int)
     */
    @Override
    public void initialize(int sideLength) {
        this.setShape(new Rectangle(sideLength, sideLength));
        this.setSize(sideLength + 1, sideLength + 1);
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());
        this.setPreferredSize(this.getSize());
        this.setBorderColor(Color.BLACK);
        this.setForeground(Color.LIGHT_GRAY);
        this.showBorder(true);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.view.FieldView#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        int xStepSize, yStepSize, x, y;

        if (this.getModel() != null) {
            x = this.getModel().getPosition().getX();
            y = this.getModel().getPosition().getY();
            xStepSize = this.getWidth() - 1;
            yStepSize = this.getHeight() - 1;
            this.setLocation(new Point(x * xStepSize, y * yStepSize));
        }

        super.paintComponent(g);
    }
}
