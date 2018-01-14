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
package abstractgame.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import nkutilities.graph.DijkstraPathfinder;
import nkutilities.graph.GraphNode;

import abstractgame.events.fieldevents.FieldTerrainChangedEvent;
import abstractgame.events.fieldevents.UnitAddedToFieldEvent;
import abstractgame.events.fieldevents.UnitRemovedFromFieldEvent;
import abstractgame.events.gameevents.UnitAddedToGameEvent;
import abstractgame.events.gameevents.UnitRemovedFromGameEvent;
import abstractgame.events.unitevents.UnitDirectionChangedEvent;
import abstractgame.events.unitevents.UnitMovedEvent;
import abstractgame.events.unitevents.UnitPlayerChangedEvent;
import abstractgame.game.AbstractGame;
import abstractgame.game.AbstractPlayer;
import abstractgame.interfaces.FieldTerrainChangedListener;
import abstractgame.interfaces.UnitAddedToFieldListener;
import abstractgame.interfaces.UnitAddedToGameListener;
import abstractgame.interfaces.UnitDirectionChangedListener;
import abstractgame.interfaces.UnitMovedListener;
import abstractgame.interfaces.UnitPlayerChangedListener;
import abstractgame.interfaces.UnitRemovedFromFieldListener;
import abstractgame.interfaces.UnitRemovedFromGameListener;
import abstractgame.map.AbstractField;
import abstractgame.map.AbstractMap;
import abstractgame.map.AbstractTerrain;
import abstractgame.view.AbstractUnitView;

/**
 * The Class AbstractUnit represents a unit in the game. A unit always belongs
 * to an {@link AbstractGame} and optionally to an {@link AbstractPlayer}.
 * Either it can have a position on the {@link AbstractMap} or it's position is
 * null. The first time an AbstractUnit is placed on the map it's graph will be
 * built. The graph will be updated every time the state of the
 * {@link AbstractMap} changes (terrain changes, units placed etc.), so thats
 * why a unit listens to various events.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractUnit implements Serializable,
        UnitAddedToFieldListener, UnitRemovedFromFieldListener,
        FieldTerrainChangedListener, UnitPlayerChangedListener,
        UnitAddedToGameListener, UnitRemovedFromGameListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7296611654781063867L;

    /**
     * The accessible terrains.
     */
    private final HashMap<Enum<?>, Double> accessibleTerrains = new HashMap<>();

    /**
     * The current health.
     */
    private double currentHealth;

    /**
     * The current movement points.
     */
    private double currentMovementPoints;

    /**
     * The direction.
     */
    private int direction;

    /**
     * The game.
     */
    private AbstractGame game;

    /**
     * The has graph.
     */
    private boolean hasGraph = false;

    /**
     * The max health.
     */
    private double maxHealth;

    /**
     * The max movement points.
     */
    private double maxMovementPoints;

    /**
     * The mobility role.
     */
    private AbstractMobilityRole mobilityRole;

    /**
     * The pathfinder.
     */
    private DijkstraPathfinder pathfinder;

    /**
     * The player.
     */
    private AbstractPlayer player;

    /**
     * The position.
     */
    private AbstractField position;

    /**
     * The unit direction changed listener.
     */
    private final HashSet<UnitDirectionChangedListener> unitDirectionChangedListener = new HashSet<>();

    /**
     * The unit moved listener.
     */
    private final HashSet<UnitMovedListener> unitMovedListener = new HashSet<>();

    /**
     * The unit player changed listener.
     */
    private final HashSet<UnitPlayerChangedListener> unitPlayerChangedListener = new HashSet<>();

    /**
     * The view.
     */
    private AbstractUnitView view;

    /**
     * Instantiates a new abstract unit.
     *
     * @param game the game
     * @param mobilityRole the mobility role
     */
    public AbstractUnit(AbstractGame game, AbstractMobilityRole mobilityRole) {
        this(game, mobilityRole, null);
    }

    /**
     * Instantiates a new abstract unit.
     *
     * @param game the game
     * @param mobilityRole the mobility role
     * @param player the player
     */
    public AbstractUnit(AbstractGame game, AbstractMobilityRole mobilityRole, AbstractPlayer player) {
        super();
        this.setGame(game);
        this.setPlayer(player);
        this.addTerrain(AbstractTerrain.UNIVERSAL_TERRAIN, 0);
        this.mobilityRole = mobilityRole;
        this.currentMovementPoints = Double.POSITIVE_INFINITY;
        this.maxMovementPoints = Double.POSITIVE_INFINITY;
        this.pathfinder = new DijkstraPathfinder();
    }

    /**
     * Adds the terrain.
     *
     * @param terrain the terrain
     * @param terrainModifier the terrainModifier
     */
    public void addTerrain(Enum<?> terrain, double terrainModifier) {
        this.getAccessibleTerrains().put(terrain, terrainModifier);
    }

    /**
     * Adds the terrain.
     *
     * @param terrain the terrain
     * @param terrainModifier the terrainModifier
     */
    public final void addTerrain(Enum<?> terrain, int terrainModifier) {
        this.addTerrain(terrain, (double) terrainModifier);
    }

    /**
     * Adds the unit direction changed listener.
     *
     * @param listener the listener
     */
    public void addUnitDirectionChangedListener(UnitDirectionChangedListener listener) {
        this.unitDirectionChangedListener.add(listener);
    }

    /**
     * Adds the unit moved listener.
     *
     * @param listener the listener
     */
    public void addUnitMovedListener(UnitMovedListener listener) {
        this.unitMovedListener.add(listener);
    }

    /**
     * Adds the unit player changed listener.
     *
     * @param listener the listener
     */
    public void addUnitPlayerChangedListener(UnitPlayerChangedListener listener) {
        this.unitPlayerChangedListener.add(listener);
    }

    /**
     * Determines if the unit can access the given field.
     *
     * @param abstractField the abstract field
     * @return true, if successful
     */
    public boolean canAccess(AbstractField abstractField) {
        return abstractField == null || (this.getAccessibleTerrains().containsKey(abstractField.getTerrain()));
    }

    /**
     * Determines if the unit can pass the given field. Subclasses must
     * implement this method because the rules could be tricky.
     *
     * @param abstractField the abstract field
     * @return true, if successful
     */
    public abstract boolean canPass(AbstractField abstractField);

    /**
     * Inflict damage on this unit.
     *
     * @param weaponDamage the weapon damage
     */
    public void damage(int weaponDamage) {
        if (this.getCurrentHealth() - weaponDamage <= 0) {
            this.setCurrentHealth(0);
            this.destroy();
        } else {
            this.setCurrentHealth(this.getCurrentHealth() - weaponDamage);
        }
    }

    /**
     * Destroy this unit (cut all references).
     */
    public void destroy() {
        this.game = null;
        this.setPlayer(null);
        this.setPosition(null);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.FieldTerrainChangedListener#terrainChanged(util.
	 * game.events.fieldevents.FieldTerrainChangedEvent)
     */
    @Override
    public void fieldTerrainChanged(FieldTerrainChangedEvent event) {
        event.getSource().setNeighborEdges(this);
    }

    /**
     * Fire unit direction changed event.
     *
     * @param previousDirection the previous direction
     */
    protected void fireUnitDirectionChangedEvent(int previousDirection) {
        this.unitDirectionChangedListener.forEach((listener) -> {
            listener.unitDirectionChanged(new UnitDirectionChangedEvent(this, previousDirection));
        });
    }

    /**
     * Fire unit moved event.
     *
     */
    protected void fireUnitMovedEvent() {
        this.unitMovedListener.forEach((listener) -> {
            listener.unitMoved(new UnitMovedEvent(this));
        });
    }

    /**
     * Fire unit player changed event.
     *
     */
    protected void fireUnitPlayerChangedEvent() {
        this.unitPlayerChangedListener.forEach((listener) -> {
            listener.unitPlayerChanged(new UnitPlayerChangedEvent(this));
        });
    }

    /**
     * Gets the accessible terrains.
     *
     * @return the accessible terrains
     */
    public HashMap<Enum<?>, Double> getAccessibleTerrains() {
        return accessibleTerrains;
    }

    /**
     * Gets the access modifier.
     *
     * @param abstractField the abstract field
     * @return the access modifier
     */
    public double getAccessModifier(AbstractField abstractField) {
        return this.getTerrainModifier(abstractField.getTerrain());
    }

    /**
     * Gets the current health.
     *
     * @return the current health
     */
    public double getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Gets the current movement points.
     *
     * @return the current movement points
     */
    public double getCurrentMovementPoints() {
        return this.currentMovementPoints;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * Gets the edge weight. This method is used to build a graph for this unit
     * an can not be overridden.
     *
     * @param abstractField the abstract field
     * @return the weight {@link #getAccessModifier(AbstractField)}.
     */
    public final double getEdgeWeight(AbstractField abstractField) {
        double weight = Double.POSITIVE_INFINITY;

        if (abstractField != null) {
            // check if the field can be passed by the unit
            if (this.canPass(abstractField)) {
                weight = this.getAccessModifier(abstractField);
            }
        }

        return weight;
    }

    /**
     * Gets the game this unit belongs to.
     *
     * @return the game
     */
    public AbstractGame getGame() {
        return game;
    }

    /**
     * Gets the max health.
     *
     * @return the max health
     */
    public double getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the max movement points.
     *
     * @return the max movement points
     */
    public double getMaxMovementPoints() {
        return this.maxMovementPoints;
    }

    /**
     * Gets the player this unit belongs to.
     *
     * @return the player
     */
    public AbstractPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public AbstractField getPosition() {
        return this.position;
    }

    /**
     * Gets the terrain modifier. This modifier will be used for pathfinding
     * operations.
     *
     * @param terrain the terrain
     * @return the terrain modifier
     */
    public double getTerrainModifier(Enum<?> terrain) {
        double requiredMovementPoints = Double.POSITIVE_INFINITY;

        if (this.getAccessibleTerrains().containsKey(terrain)) {
            requiredMovementPoints = this.getAccessibleTerrains().get(terrain);
        }

        return requiredMovementPoints;
    }

    /**
     * Gets the view of this unit.
     *
     * @return the view
     */
    public AbstractUnitView getView() {
        return view;
    }

    /**
     * Checks if this unit has a graph already.
     *
     * @return true, if successful
     */
    public boolean hasGraph() {
        return hasGraph;
    }

    /**
     * Sets the flag, which indicates if the unit has a graph.
     *
     * @param hasGraph the has graph
     */
    public void hasGraph(boolean hasGraph) {
        this.hasGraph = hasGraph;
    }

    /**
     * Checks if the position is not null.
     *
     * @return true, if is placed
     */
    public boolean hasPosition() {
        return this.position != null;
    }

    /**
     * Checks if is immobile.
     *
     * @return true, if is immobile
     */
    public boolean isImmobile() {
        return this.mobilityRole.isImmobile();
    }

    /**
     * Checks if is mobile.
     *
     * @return true, if is mobile
     */
    public boolean isMobile() {
        return this.mobilityRole.isMobile();
    }

    /**
     * Move forward considering the current direction.
     *
     * @return true, if successful
     */
    public boolean moveForward() {
        return this.moveTo(this.position.getNeighbor(this.direction));
    }

    /**
     * Move from current position to the given field.
     *
     * @param targetField the target field
     * @return true, if successful
     */
    public boolean moveTo(AbstractField targetField) {
        boolean success = false;
        double distanceTraveled = 0;
        int newDirection;
        ArrayList<GraphNode> path;

        // if the unit has a position on the map AND if it is mobile
        if (this.hasPosition() && this.isMobile()) {
            path = this.pathfinder.findPath(this, this.getPosition(), targetField, this.getCurrentMovementPoints());

            if (!path.isEmpty()) {
                path.remove(0);
            }

            // for every field in path
            for (GraphNode node : path) {
                AbstractField nextField = (AbstractField) node;
                double distanceToNextNode = this.pathfinder.getDistanceForNode(nextField) - distanceTraveled;

                // set direction to the next field
                newDirection = this.getPosition().getDirectionForNeighbor(nextField);
                this.setDirection(newDirection);

                // set position to the next field
                if (this.setPosition(nextField, true)) {
                    this.setCurrentMovementPoints(this.getCurrentMovementPoints() - distanceToNextNode);
                    distanceTraveled += distanceToNextNode;
                }
            }

            if (!path.isEmpty() && this.getPosition() == path.get(path.size() - 1)) {
                success = true;
            }
        }

        return success;
    }

    /**
     * Removes the unit direction changed listener.
     *
     * @param listener the listener
     */
    public void removeUnitDirectionChangedListener(UnitDirectionChangedListener listener) {
        this.unitDirectionChangedListener.remove(listener);
    }

    /**
     * Removes the unit moved listener.
     *
     * @param listener the listener
     */
    public void removeUnitMovedListener(UnitMovedListener listener) {
        this.unitMovedListener.remove(listener);
    }

    /**
     * Removes the unit player changed listener.
     *
     * @param listener the listener
     */
    public void removeUnitPlayerChangedListener(
            UnitPlayerChangedListener listener) {
        this.unitPlayerChangedListener.remove(listener);
    }

    /**
     * Restore some health.
     *
     * @param health the health
     */
    public void repair(int health) {
        if (this.getCurrentHealth() + health > this.getMaxHealth()) {
            this.setCurrentHealth(this.getMaxHealth());
        } else {
            this.setCurrentHealth(this.getCurrentHealth() + health);
        }
    }

    /**
     * Sets the current health.
     *
     * @param currentHealth the new current health
     */
    public void setCurrentHealth(double currentHealth) {
        if (currentHealth <= this.maxHealth) {
            this.currentHealth = currentHealth;
        } else {
            this.currentHealth = this.maxHealth;
        }
    }

    /**
     * Sets the current movement points.
     *
     * @param currentMovementPoints the new current movement points
     */
    public void setCurrentMovementPoints(double currentMovementPoints) {
        if (currentMovementPoints <= this.maxMovementPoints) {
            this.currentMovementPoints = currentMovementPoints;
        } else {
            this.currentMovementPoints = this.maxMovementPoints;
        }
    }

    /**
     * Sets the direction.
     *
     * @param newDirection the new direction
     */
    public void setDirection(int newDirection) {
        int previousDirection;

        if (newDirection >= 0
                && newDirection < this.position.getDirections()
                && newDirection != this.getDirection()) {
            previousDirection = this.getDirection();
            this.direction = newDirection;
            this.fireUnitDirectionChangedEvent(previousDirection);
        }
    }

    /**
     * Sets the game the unit belongs to.
     *
     * @param game the new game
     */
    public final void setGame(AbstractGame game) {
        this.game = game;

        if (!this.game.getUnits().contains(this)) {
            this.game.addUnit(this);
        }
    }

    /**
     * Sets the max health.
     *
     * @param maxHealth the new max health
     */
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Sets the max movement points.
     *
     * @param maxMovementPoints the new max movement points
     */
    public void setMaxMovementPoints(double maxMovementPoints) {
        this.maxMovementPoints = maxMovementPoints;
    }

    /**
     * Sets the mobility role.
     *
     * @param mobilityRole the new mobility role
     */
    public void setMobilityRole(AbstractMobilityRole mobilityRole) {
        this.mobilityRole = mobilityRole;
    }

    /**
     * Sets the player the unit belongs to.
     *
     * @param player the new player
     */
    public final void setPlayer(AbstractPlayer player) {
        if (this.player != player) {
            // remember the old player and set the new one
            AbstractPlayer previousPlayer = this.player;
            this.player = player;

            // tell the play to add this unit
            if (player != null && !player.getUnits().contains(this)) {
                player.addUnit(this);
            }

            // tell the previous player to remove this unit
            if (previousPlayer != null && previousPlayer.getUnits().contains(this)) {
                previousPlayer.removeUnit(this);
            }

            this.fireUnitPlayerChangedEvent();
        }
    }

    /**
     * Sets the position.
     *
     * @param abstractField the abstract field
     * @return true, if successful
     */
    public boolean setPosition(AbstractField abstractField) {
        return this.setPosition(abstractField, false);
    }

    /**
     * Sets the position. If passThrough is true, the unit can access the field
     * even if the field can't carry the unit because it will not remain. This
     * method is for private use only.
     *
     * @param abstractField the abstractField
     * @param passThrough the pass through
     * @return true, if successful
     */
    private boolean setPosition(AbstractField abstractField, boolean passThrough) {
        boolean success = false;
        boolean canAccess = abstractField == null
                || (this.canAccess(abstractField) && (abstractField.canCarry(this) || passThrough));
        AbstractField oldPosition = this.getPosition();

        // lazy initialization...the graph is build the first time the unit is placed on the map
        if (abstractField != null && !this.hasGraph()) {
            this.game.getMap().buildGraph(this);
        }

        if (canAccess) {

            if (!this.hasPosition() || !this.getPosition().equals(abstractField)) {
                AbstractField currentPosition;

                // remember the old position
                currentPosition = this.position;
                // set the new one
                this.position = abstractField;

                // remove the unit from the old position if there is still a
                // reference
                if (currentPosition != null && currentPosition.containsUnit(this)) {
                    currentPosition.removeUnit(this);
                }

                // add the unit to the new field if there is no reference
                if (abstractField != null && !abstractField.containsUnit(this)) {
                    abstractField.addUnit(this);
                }

                success = true;
            }
        }

        // if the units position changed fire an event
        if (oldPosition != this.getPosition()) {
            this.fireUnitMovedEvent();
        }

        return success;
    }

    /**
     * Sets the terrain modifier.
     *
     * @param terrain the terrain
     * @param modifier the modifier
     */
    public void setTerrainModifier(Enum<?> terrain, double modifier) {
        this.getAccessibleTerrains().put(terrain, modifier);
    }

    /**
     * Sets the terrain modifier.
     *
     * @param terrain the terrain
     * @param modifier the modifier
     */
    public void setTerrainModifier(Enum<?> terrain, int modifier) {
        this.setTerrainModifier(terrain, (double) modifier);
    }

    /**
     * Sets the view.
     *
     * @param view the new view
     */
    public void setView(AbstractUnitView view) {
        AbstractUnitView oldView = this.view;
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
        return this.getClass().getName() + this.position;
    }

    /**
     * Turn left.
     */
    public void turnLeft() {
        if (this.hasPosition() && this.isMobile()) {
            this.setDirection((this.getDirection() + 1)
                    % this.position.getDirections());
        }
    }

    /**
     * Turn right.
     */
    public void turnRight() {
        if (this.hasPosition() && this.isMobile()) {
            this.setDirection((this.direction - 1 + this.position.getDirections()) % this.position.getDirections());
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitAddedToFieldListener#unitAdded(util.game.events
	 * .fieldevents.UnitAddedToFieldEvent)
     */
    @Override
    public void unitAddedToField(UnitAddedToFieldEvent event) {
        event.getSource().setNeighborEdges(this);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitAddedToGameListener#unitAddedToGame(util.game
	 * .events.gameevents.UnitAddedToGameEvent)
     */
    @Override
    public void unitAddedToGame(UnitAddedToGameEvent event) {
        this.addUnitPlayerChangedListener(event.getUnit());
        event.getUnit().addUnitPlayerChangedListener(this);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitPlayerChangedListener#playerChanged(util.game
	 * .events.unitevents.UnitPlayerChangedEvent)
     */
    @Override
    public void unitPlayerChanged(UnitPlayerChangedEvent event) {
        if (this.hasPosition() && this.hasGraph() && event.getSource().hasPosition()) {
            event.getSource().getPosition().setNeighborEdges(this);
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitRemovedFromFieldListener#unitRemoved(util.game
	 * .events.fieldevents.UnitRemovedFromFieldEvent)
     */
    @Override
    public void unitRemovedFromField(UnitRemovedFromFieldEvent event) {
        event.getSource().setNeighborEdges(this);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.game.interfaces.UnitRemovedFromGameListener#unitRemovedFromGame(
	 * util.game.events.gameevents.UnitRemovedFromGameEvent)
     */
    @Override
    public void unitRemovedFromGame(UnitRemovedFromGameEvent event) {
        this.removeUnitPlayerChangedListener(event.getUnit());
        event.getUnit().removeUnitPlayerChangedListener(this);
    }
}
