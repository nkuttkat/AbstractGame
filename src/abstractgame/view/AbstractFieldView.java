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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JComponent;
import javax.swing.text.FieldView;

import abstractgame.events.fieldevents.FieldTerrainChangedEvent;
import abstractgame.events.fieldevents.UnitAddedToFieldEvent;
import abstractgame.events.fieldevents.UnitRemovedFromFieldEvent;
import abstractgame.interfaces.FieldTerrainChangedListener;
import abstractgame.interfaces.UnitAddedToFieldListener;
import abstractgame.interfaces.UnitRemovedFromFieldListener;
import abstractgame.map.AbstractField;

/**
 * The Class AbstractFieldView is an abstract superclass for various graphical
 * representations of {@link AbstractField}'s. It handles some field and unit
 * events by default.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractFieldView extends JComponent implements
        UnitAddedToFieldListener, UnitRemovedFromFieldListener,
        FieldTerrainChangedListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The border color.
     */
    private Color borderColor;

    /**
     * The model.
     */
    private AbstractField model;

    /**
     * The shape.
     */
    private Shape shape;

    /**
     * The show border.
     */
    private boolean showBorder;

    public AbstractFieldView(AbstractField model) {
        this.setModel(model);
    }

    @Override
    public boolean contains(int x, int y) {
        return this.shape.contains(x, y);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.FieldTerrainChangedListener#fieldTerrainChanged(
	 * util.game.events.fieldevents.FieldTerrainChangedEvent)
     */
    @Override
    public void fieldTerrainChanged(FieldTerrainChangedEvent source) {
        this.repaint();
    }

    /**
     * Gets the border color.
     *
     * @return the border color
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public final AbstractField getModel() {
        return model;
    }

    /**
     * Gets the shape.
     *
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Initialize.
     *
     * @param sideLength the side length
     */
    public abstract void initialize(int sideLength);

    @Override
    protected void paintBorder(Graphics g) {
        if (this.showBorder()) {
            Graphics2D gCopy = (Graphics2D) g.create();
            gCopy.setColor(this.getBorderColor());
            gCopy.draw(this.shape);
        }
    }

    /**
     * Every subclass of {@link FieldView} has to implement it's own way to
     * calculate the position of each component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gCopy = (Graphics2D) g.create();
        gCopy.setColor(this.getForeground());
        gCopy.fill(this.shape);
    }

    /**
     * Sets the border color.
     *
     * @param borderColor the new border color
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public final void setModel(AbstractField model) {
        AbstractField oldModel = this.model;
        this.model = model;

        if (oldModel != null && oldModel.getView() == this) {
            oldModel.removeUnitAddedListener(this);
            oldModel.removeUnitRemovedListener(this);
            oldModel.removeTerrainChangedListener(this);
            oldModel.setView(null);
        }

        if (this.model != null) {
            this.model.addUnitAddedListener(this);
            this.model.addUnitRemovedListener(this);
            this.model.addTerrainChangedListener(this);

            if (this.model.getView() != this) {
                this.model.setView(this);
            }
        }
    }

    /**
     * Sets the shape.
     *
     * @param shape the new shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Show border.
     *
     * @return true, if successful
     */
    public boolean showBorder() {
        return this.showBorder;
    }

    /**
     * Show border.
     *
     * @param b the b
     */
    public void showBorder(boolean b) {
        this.showBorder = b;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitAddedToFieldListener#unitAddedToField(util.game
	 * .events.fieldevents.UnitAddedToFieldEvent)
     */
    @Override
    public void unitAddedToField(UnitAddedToFieldEvent event) {
        AbstractUnitView unitView = event.getUnit().getView();

        if (unitView != null) {
            this.add(unitView);
            this.repaint();
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitRemovedFromFieldListener#unitRemovedFromField
	 * (util.game.events.fieldevents.UnitRemovedFromFieldEvent)
     */
    @Override
    public void unitRemovedFromField(UnitRemovedFromFieldEvent event) {
        AbstractUnitView unitView = event.getUnit().getView();

        if (unitView != null) {
            this.remove(unitView);
            this.repaint();
        }
    }
}
