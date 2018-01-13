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

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import abstractgame.map.AbstractField;
import abstractgame.view.SquareView;

/**
 * The Class Square.
 *
 * @author Nils Kuttkat
 */
public class Square extends SquareView {

    private static final long serialVersionUID = 1L;
    /**
     * The image.
     */
    private BufferedImage image;

    /**
     * Instantiates a new square.
     *
     * @param model the model
     * @param sideLength the side length
     * @param listener the listener
     */
    public Square(AbstractField model, int sideLength, MouseListener listener) {
        super(model, sideLength);
        this.initialize(listener);
    }

    /**
     * Initialize.
     *
     * @param listener the listener
     */
    public void initialize(MouseListener listener) {
        this.addMouseListener(listener);
        this.showBorder(true);
        BufferedImage image = new BufferedImage(this.getWidth(),
                this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(this.getForeground());
        g.fill(this.getShape());
        this.setImage(image);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.view.SquareView#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(image, null, 0, 0);
    }

    /**
     * Sets the image.
     *
     * @param image the new image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_IN,
                1.0f);
        Graphics2D g = this.image.createGraphics();
        g.setComposite(ac);
    }
}
