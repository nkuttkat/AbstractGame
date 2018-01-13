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
package abstractgame.map;

import java.io.Serializable;

import abstractgame.game.AbstractGame;
import abstractgame.unit.AbstractUnit;

import nkutilities.Coordinate;

/**
 * The Class AbstractMap represents a game map and consists of
 * {@link AbstractField}'s.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractMap implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7491531564280512332L;

    /**
     * The fields.
     */
    private final AbstractField[][] fields;

    /**
     * The game.
     */
    private AbstractGame game;

    /**
     * Instantiates a new abstract map.
     *
     * @param width the width
     * @param height the height
     */
    public AbstractMap(int width, int height) {
        this.fields = new AbstractField[width][height];
    }

    /**
     * Builds the graph associated to a unit.
     *
     * @param unit the unit
     */
    public void buildGraph(AbstractUnit unit) {
        for (AbstractField[] column : this.fields) {
            for (AbstractField field : column) {
                field.setNeighborEdges(unit);
                field.addUnitAddedListener(unit);
                field.addUnitRemovedListener(unit);
                field.addTerrainChangedListener(unit);
            }
        }

        unit.hasGraph(true);
    }

    /**
     * Gets the field.
     *
     * @param coordinate the coordinate
     * @return the field
     */
    public AbstractField getField(Coordinate coordinate) {
        return this.getField(coordinate.getX(), coordinate.getY());
    }

    /**
     * Gets the field.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the field
     */
    public AbstractField getField(int x, int y) {
        try {
            return this.fields[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public AbstractField[][] getFields() {
        return this.fields;
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public AbstractGame getGame() {
        return game;
    }

    /**
     * Checks for position.
     *
     * @param coordinate the coordinate
     * @return true, if successful
     */
    public boolean hasPosition(Coordinate coordinate) {
        boolean hasPosition = false;

        if (coordinate != null) {
            hasPosition = this
                    .hasPosition(coordinate.getX(), coordinate.getY());
        }

        return hasPosition;
    }

    /**
     * Checks for position.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true, if successful
     */
    public boolean hasPosition(int x, int y) {
        @SuppressWarnings("unused")
        AbstractField dummy;
        try {
            dummy = this.fields[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    /**
     * Removes the unit's graph.
     *
     * @param unit the unit
     */
    public void removeGraph(AbstractUnit unit) {
        for (AbstractField[] column : this.fields) {
            for (AbstractField field : column) {
                field.getEdges().remove(unit);
                field.removeUnitAddedListener(unit);
                field.removeUnitRemovedListener(unit);
                field.removeTerrainChangedListener(unit);
            }
        }

        unit.hasGraph(false);
    }

    /**
     * Sets the field.
     *
     * @param coordinate the coordinate
     * @param abstractField the abstractField
     */
    public void setField(Coordinate coordinate, AbstractField abstractField) {
        if (this.hasPosition(coordinate)) {
            // remember the old reference and set the new one
            AbstractField currentField = this.getField(coordinate);
            this.fields[coordinate.getX()][coordinate.getY()] = abstractField;

            // tell the old field to delete the reference if there is one
            if (currentField != null
                    && currentField.getPosition() != null
                    && currentField.getPosition().equals(coordinate)) {
                currentField.setPosition(null, null);
            }

            // set the new reference if there isn't one
            if (abstractField != null
                    && (abstractField.getPosition() == null || !abstractField.getPosition().equals(coordinate))) {
                abstractField.setPosition(coordinate, this);
            }
        }
    }

    /**
     * Sets the field.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param abstractField the abstract field
     */
    public void setField(int x, int y, AbstractField abstractField) {
        this.setField(new Coordinate(x, y), abstractField);
    }

    /**
     * Sets the game.
     *
     * @param game the new game
     */
    public void setGame(AbstractGame game) {
        this.game = game;
    }
}
