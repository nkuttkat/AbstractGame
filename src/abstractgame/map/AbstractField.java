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
import java.util.HashSet;

import nkutilities.Coordinate;
import nkutilities.graph.GraphNode;

import abstractgame.events.fieldevents.FieldPositionChangedEvent;
import abstractgame.events.fieldevents.FieldTerrainChangedEvent;
import abstractgame.events.fieldevents.UnitAddedToFieldEvent;
import abstractgame.events.fieldevents.UnitRemovedFromFieldEvent;
import abstractgame.interfaces.FieldPositionChangedListener;
import abstractgame.interfaces.FieldTerrainChangedListener;
import abstractgame.interfaces.UnitAddedToFieldListener;
import abstractgame.interfaces.UnitRemovedFromFieldListener;
import abstractgame.unit.AbstractUnit;
import abstractgame.view.AbstractFieldView;

/**
 * This class represents an abstract game field. <br>
 * Every AbstractField is a {@link GraphNode}, therefore a map is a Graph too.
 * <br>
 * This allows us to build graphs for every unit on the map to perform
 * pathfinding operations.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractField extends GraphNode implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -2128487589623700568L;

    /**
     * The map.
     */
    private AbstractMap map;

    /**
     * The max units.
     */
    private int maxUnits = Integer.MAX_VALUE;

    /**
     * The position.
     */
    private Coordinate position;

    /**
     * The position changed listener.
     */
    private final HashSet<FieldPositionChangedListener> positionChangedListener = new HashSet<>();

    /**
     * The terrain.
     */
    private Enum<?> terrain;

    /**
     * The terrain changed listener.
     */
    private final HashSet<FieldTerrainChangedListener> terrainChangedListener = new HashSet<>();

    /**
     * The unit added listener.
     */
    private final HashSet<UnitAddedToFieldListener> unitAddedListener = new HashSet<>();

    /**
     * The unit removed listener.
     */
    private final HashSet<UnitRemovedFromFieldListener> unitRemovedListener = new HashSet<>();

    /**
     * The units.
     */
    private final HashSet<AbstractUnit> units = new HashSet<>();

    /**
     * The view.
     */
    private AbstractFieldView view;

    /**
     * Instantiates a new abstract field.
     */
    public AbstractField() {
        this(AbstractTerrain.UNIVERSAL_TERRAIN);
    }

    /**
     * Instantiates a new abstract field.
     *
     * @param terrain the terrain
     */
    public AbstractField(Enum<?> terrain) {
        super();
        this.terrain = terrain;
    }

    /**
     * Adds the position changed listener.
     *
     * @param listener the listener
     */
    public void addPositionChangedListener(FieldPositionChangedListener listener) {
        this.positionChangedListener.add(listener);
    }

    /**
     * Adds the terrain changed listener.
     *
     * @param listener the listener
     */
    public void addTerrainChangedListener(FieldTerrainChangedListener listener) {
        this.terrainChangedListener.add(listener);
    }

    /**
     * Adds the unit.
     *
     * @param abstractUnit the abstractUnit
     */
    public void addUnit(AbstractUnit abstractUnit) {
        // check if the field can carry the unit and if the unit can access the
        // field
        if (abstractUnit != null && !this.units.contains(abstractUnit)
                && this.canCarry(abstractUnit) && abstractUnit.canAccess(this)) {
            this.units.add(abstractUnit);

            // tell the unit to reference this field if it doesn't allready
            if (!this.equals(abstractUnit.getPosition())) {
                abstractUnit.setPosition(this);
            }

            this.fireUnitAddedEvent(abstractUnit);
        }
    }

    /**
     * Adds the unit added listener.
     *
     * @param listener the listener
     */
    public void addUnitAddedListener(UnitAddedToFieldListener listener) {
        this.unitAddedListener.add(listener);
    }

    /**
     * Adds the unit removed listener.
     *
     * @param listener the listener
     */
    public void addUnitRemovedListener(UnitRemovedFromFieldListener listener) {
        this.unitRemovedListener.add(listener);
    }

    /**
     * Determines if the field can carry the unit. Subclasses may override this
     * method.
     *
     * @param abstractUnit the abstractUnit
     * @return true, if successful
     */
    public boolean canCarry(AbstractUnit abstractUnit) {
        return this.units.size() < this.maxUnits;
    }

    /**
     * Returns true if the field contains abstractUnit.
     *
     * @param abstractUnit the abstractUnit
     * @return true, if successful
     */
    public boolean containsUnit(AbstractUnit abstractUnit) {
        return this.units.contains(abstractUnit);
    }

    /**
     * Fire position changed event.
     */
    protected void firePositionChangedEvent() {
        this.positionChangedListener.forEach((listener) -> {
            listener.fieldPositionChanged(new FieldPositionChangedEvent(this));
        });
    }

    /**
     * Fire terrain changed event.
     */
    protected void fireTerrainChangedEvent() {
        this.terrainChangedListener.forEach((listener) -> {
            listener.fieldTerrainChanged(new FieldTerrainChangedEvent(this));
        });
    }

    /**
     * Fire unit added event.
     *
     * @param abstractUnit the abstract unit
     */
    protected void fireUnitAddedEvent(AbstractUnit abstractUnit) {
        this.unitAddedListener.forEach((listener) -> {
            listener.unitAddedToField(new UnitAddedToFieldEvent(this,
                    abstractUnit));
        });
    }

    /**
     * Fire unit removed event.
     *
     * @param abstractUnit the abstract unit
     */
    protected void fireUnitRemovedEvent(AbstractUnit abstractUnit) {
        this.unitRemovedListener.forEach((listener) -> {
            listener.unitRemovedFromField(new UnitRemovedFromFieldEvent(this,
                    abstractUnit));
        });
    }

    /**
     * Gets the direction for neighbor.
     *
     * @param abstractField the abstract field
     * @return the direction for neighbor
     */
    public int getDirectionForNeighbor(AbstractField abstractField) {
        int direction = Integer.MIN_VALUE;

        for (int i = 0; i < this.getDirections(); i++) {
            if (this.getNeighbor(i) == abstractField) {
                direction = i;
                break;
            }
        }

        return direction;
    }

    /**
     * Gets the number directions. This method must be implemented by concrete
     * sublasses.
     *
     * @return the directions
     */
    public abstract int getDirections();

    /**
     * Gets the distance to the given field.
     *
     * @param abstractField the abstractField
     * @return the distance
     */
    public abstract double getDistance(AbstractField abstractField);

    /**
     * Gets the map this field belons to.
     *
     * @return the map
     */
    public AbstractMap getMap() {
        return this.map;
    }

    /**
     * Gets the number of units the field can carry.
     *
     * @return the max units
     */
    public int getMaxUnits() {
        return maxUnits;
    }

    /**
     * Gets the neighbor in the given direction.
     *
     * @param direction the direction
     * @return the abstract field
     */
    public abstract AbstractField getNeighbor(int direction);

    /**
     * Gets all the neighbors.
     *
     * @return the neighbors
     */
    public HashSet<AbstractField> getNeighbors() {
        HashSet<AbstractField> hashSet = new HashSet<>();

        for (int direction = 0; direction < this.getDirections(); direction++) {
            hashSet.add(this.getNeighbor(direction));
        }

        return hashSet;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * Gets the terrain.
     *
     * @return the terrain
     */
    public Enum<?> getTerrain() {
        return terrain;
    }

    /**
     * Gets the units.
     *
     * @return the units
     */
    public HashSet<AbstractUnit> getUnits() {
        return this.units;
    }

    /**
     * Gets the view.
     *
     * @return the view
     */
    public AbstractFieldView getView() {
        return view;
    }

    /**
     * Checks if the given field is a neighbor.
     *
     * @param abstractField the abstract field
     * @return true, if is neighbor
     */
    public boolean isNeighbor(AbstractField abstractField) {
        return this.getNeighbors().contains(abstractField);
    }

    /**
     * Removes the position changed listener.
     *
     * @param listener the listener
     */
    public void removePositionChangedListener(
            FieldPositionChangedListener listener) {
        this.positionChangedListener.remove(listener);
    }

    /**
     * Removes the terrain changed listener.
     *
     * @param listener the listener
     */
    public void removeTerrainChangedListener(
            FieldTerrainChangedListener listener) {
        this.terrainChangedListener.remove(listener);
    }

    /**
     * Removes the unit.
     *
     * @param abstractUnit the abstractUnit
     */
    public void removeUnit(AbstractUnit abstractUnit) {
        if (this.units.contains(abstractUnit)) {
            this.units.remove(abstractUnit);

            // tell the unit to remove the reference
            if (this.equals(abstractUnit.getPosition())) {
                abstractUnit.setPosition(null);
            }

            this.fireUnitRemovedEvent(abstractUnit);
        }
    }

    /**
     * Removes the unit added listener.
     *
     * @param listener the listener
     */
    public void removeUnitAddedListener(UnitAddedToFieldListener listener) {
        this.unitAddedListener.remove(listener);
    }

    /**
     * Removes the unit removed listener.
     *
     * @param listener the listener
     */
    public void removeUnitRemovedListener(UnitRemovedFromFieldListener listener) {
        this.unitRemovedListener.remove(listener);
    }

    /**
     * Sets the max units.
     *
     * @param maxUnits the new max units
     */
    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    /**
     * Sets the neighbor edges for the given unit.
     *
     * @param unit the new neighbor edges
     */
    public void setNeighborEdges(AbstractUnit unit) {
        int startDirection = 0, currentDirection = startDirection;
        AbstractField neighbor;
        double weight = unit.getEdgeWeight(this);

        do {
            neighbor = this.getNeighbor(currentDirection);

            if (neighbor != null) {
                neighbor.setEdge(unit, this, weight * this.getDistance(neighbor));
            }

            currentDirection = (currentDirection + 1) % this.getDirections();
        } while (currentDirection != startDirection);
    }

    /**
     * Sets the position.
     *
     * @param coordinate the coordinate
     * @param map the map
     */
    public void setPosition(Coordinate coordinate, AbstractMap map) {
        // if map is null or contains the given position
        if (map == null || map.hasPosition(coordinate)) {
            // if the position is null or it is not the current position
            if (this.position == null || !this.position.equals(coordinate)) {
                // remember the old references
                AbstractMap currentMap = this.map;
                Coordinate currentPosition = this.position;

                // set the new references
                this.map = map;
                this.position = coordinate;

                // tell the map to delete the old reference if there is one
                if (currentMap != null && this.equals(currentMap.getField(currentPosition))) {
                    currentMap.setField(currentPosition, null);
                }

                // tell the map to set the new reference if there isn't one
                if (this.map != null
                        && !this.equals(this.map.getField(this.position))) {
                    this.map.setField(this.position, this);
                }

                this.firePositionChangedEvent();
            }
        }
    }

    /**
     * Sets the position.
     *
     * @param x the x
     * @param y the y
     * @param map the map
     */
    public void setPosition(int x, int y, AbstractMap map) {
        this.setPosition(new Coordinate(x, y), map);
    }

    /**
     * Sets the terrain.
     *
     * @param terrain the new terrain
     */
    public void setTerrain(Enum<?> terrain) {
        this.terrain = terrain;
        this.fireTerrainChangedEvent();
    }

    /**
     * Sets the view.
     *
     * @param view the new view
     */
    public void setView(AbstractFieldView view) {
        AbstractFieldView oldView = this.view;
        this.view = view;

        if (oldView != null && oldView.getModel() == this) {
            oldView.setModel(null);
        }

        if (this.view != null && this.view.getModel() != this) {
            this.view.setModel(this);
        }

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String string;
        if (this.position == null) {
            string = this.getClass().getName() + "(null)";
        } else {
            string = this.getClass().getName() + this.position;
        }

        return string;
    }
}
