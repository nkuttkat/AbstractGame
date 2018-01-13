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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import abstractgame.events.unitevents.UnitDirectionChangedEvent;
import abstractgame.events.unitevents.UnitMovedEvent;
import abstractgame.interfaces.UnitDirectionChangedListener;
import abstractgame.interfaces.UnitMovedListener;
import abstractgame.unit.AbstractUnit;

/**
 * The Class AbstractUnitView is an abstract superclass for various graphical
 * representations of {@link AbstractUnit}'s. It handles some field and unit
 * events by default.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractUnitView extends JComponent implements
        UnitMovedListener, UnitDirectionChangedListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The model.
     */
    private AbstractUnit model;

    private double theta;

    /**
     * Instantiates a new abstract unit view.
     */
    public AbstractUnitView() {
        this(null);
    }

    /**
     * Instantiates a new abstract unit view.
     *
     * @param abstractUnit the abstract unit
     */
    public AbstractUnitView(AbstractUnit abstractUnit) {
        this.setModel(abstractUnit);
    }

    protected void calculateRotationAngle(int previousDirection) {
        this.theta = -2 * Math.PI / this.getModel().getPosition().getDirections()
                * (this.getModel().getDirection() - previousDirection);
    }

    protected void rotate(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;

        if (this.getModel().hasPosition() && graphics2d != null) {
            graphics2d.rotate(theta, this.getImage().getWidth() / 2.0, this.getImage().getHeight() / 2.0);
        }
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public abstract BufferedImage getImage();

    /**
     * Gets the model.
     *
     * @return the model
     */
    public AbstractUnit getModel() {
        return model;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        this.calculateRotationAngle(0);
        this.rotate(graphics);
        super.paintComponent(graphics);
    }

    @Override
    public void repaint() {
        this.rotate(this.getGraphics());
        super.repaint();
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(AbstractUnit model) {
        AbstractUnit oldModel = this.model;
        this.model = model;

        if (oldModel != null && oldModel.getView() == this) {
            oldModel.removeUnitMovedListener(this);
            oldModel.removeUnitDirectionChangedListener(this);
            oldModel.setView(null);
        }

        if (this.model != null) {
            this.model.addUnitMovedListener(this);
            this.model.addUnitDirectionChangedListener(this);

            if (this.model.getView() != this) {
                this.model.setView(this);
            }
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitDirectionChangedListener#directionChanged(util
	 * .game.events.unitevents.UnitDirectionChangedEvent)
     */
    @Override
    public void unitDirectionChanged(UnitDirectionChangedEvent event) {
        this.calculateRotationAngle(event.getPreviousDirection());
        this.repaint();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitMovedListener#unitMoved(util.game.events.unitevents
	 * .UnitMovedEvent)
     */
    @Override
    public void unitMoved(UnitMovedEvent event) {

    }
}
