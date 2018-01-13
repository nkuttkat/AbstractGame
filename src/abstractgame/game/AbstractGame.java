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
package abstractgame.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import abstractgame.events.gameevents.UnitAddedToGameEvent;
import abstractgame.events.gameevents.UnitRemovedFromGameEvent;
import abstractgame.interfaces.UnitAddedToGameListener;
import abstractgame.interfaces.UnitRemovedFromGameListener;
import abstractgame.map.AbstractMap;
import abstractgame.unit.AbstractUnit;

/**
 * The Class AbstractGame is some sort of superior class. It contains all
 * objects directly or indirectly.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractGame implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7961070909300985624L;

    /**
     * The arbiter.
     */
    private AbstractArbiter arbiter;

    /**
     * The map.
     */
    private AbstractMap map;

    /**
     * The players.
     */
    private final HashMap<String, AbstractPlayer> players = new HashMap<String, AbstractPlayer>();

    /**
     * The unit added to game listener.
     */
    private final HashSet<UnitAddedToGameListener> unitAddedToGameListener = new HashSet<UnitAddedToGameListener>();

    /**
     * The unit removed from game listener.
     */
    private final HashSet<UnitRemovedFromGameListener> unitRemovedFromGameListener = new HashSet<UnitRemovedFromGameListener>();

    /**
     * The units.
     */
    private final HashSet<AbstractUnit> units = new HashSet<AbstractUnit>();

    /**
     * Instantiates a new abstract game.
     *
     * @param map the map
     */
    public AbstractGame(AbstractMap map) {
        this.setMap(map);
    }

    /**
     * Adds the player.
     *
     * @param player the player
     */
    public void addPlayer(AbstractPlayer player) {
        if (player != null) {
            this.getPlayers().put(player.getName(), player);

            if (!player.getGame().equals(this)) {
                player.setGame(this);
            }
        }
    }

    /**
     * Adds the unit.
     *
     * @param abstractUnit the abstract unit
     */
    public void addUnit(AbstractUnit abstractUnit) {
        if (abstractUnit != null && !this.units.contains(abstractUnit)) {
            this.units.add(abstractUnit);
            this.fireUnitAddedEvent(abstractUnit);
            this.addUnitAddedToGameListener(abstractUnit);
            this.addUnitRemovedFromGameListener(abstractUnit);
        }
    }

    /**
     * Adds the unit added to game listener.
     *
     * @param listener the listener
     */
    public void addUnitAddedToGameListener(UnitAddedToGameListener listener) {
        this.unitAddedToGameListener.add(listener);
    }

    /**
     * Adds the unit removed from game listener.
     *
     * @param listener the listener
     */
    public void addUnitRemovedFromGameListener(
            UnitRemovedFromGameListener listener) {
        this.unitRemovedFromGameListener.add(listener);
    }

    /**
     * Fire unit added event.
     *
     * @param abstractUnit the abstract unit
     */
    protected void fireUnitAddedEvent(AbstractUnit abstractUnit) {
        for (UnitAddedToGameListener listener : this.unitAddedToGameListener) {
            listener.unitAddedToGame(new UnitAddedToGameEvent(this, abstractUnit));
        }
    }

    /**
     * Fire unit removed event.
     *
     * @param abstractUnit the abstract unit
     */
    protected void fireUnitRemovedEvent(AbstractUnit abstractUnit) {
        for (UnitRemovedFromGameListener listener : this.unitRemovedFromGameListener) {
            listener.unitRemovedFromGame(new UnitRemovedFromGameEvent(this, abstractUnit));
        }
    }

    /**
     * Gets the arbiter.
     *
     * @return the arbiter
     */
    public AbstractArbiter getArbiter() {
        return this.arbiter;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public AbstractMap getMap() {
        return this.map;
    }

    /**
     * Gets the players.
     *
     * @return the players
     */
    public HashMap<String, AbstractPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets the units.
     *
     * @return the units
     */
    public HashSet<AbstractUnit> getUnits() {
        return units;
    }

    /**
     * Removes the player.
     *
     * @param player the player
     */
    public void removePlayer(AbstractPlayer player) {
        if (player != null) {
            this.getPlayers().remove(player.getName());

            if (this.equals(player.getGame())) {
                player.setGame(null);
            }
        }
    }

    /**
     * Removes the unit.
     *
     * @param abstractUnit the abstract unit
     */
    public void removeUnit(AbstractUnit abstractUnit) {
        if (abstractUnit != null) {
            this.units.remove(abstractUnit);
            this.getMap().removeGraph(abstractUnit);

            if (abstractUnit.getGame() == this) {
                abstractUnit.destroy();
            }

            this.fireUnitRemovedEvent(abstractUnit);
        }
    }

    /**
     * Removes the unit added to game listener.
     *
     * @param listener the listener
     */
    public void removeUnitAddedToGameListener(UnitAddedToGameListener listener) {
        this.unitAddedToGameListener.remove(listener);
    }

    /**
     * Removes the unit removed from game listener.
     *
     * @param listener the listener
     */
    public void removeUnitRemovedFromGameListener(
            UnitRemovedFromGameListener listener) {
        this.unitRemovedFromGameListener.remove(listener);
    }

    /**
     * Sets the arbiter.
     *
     * @param arbiter the new arbiter
     */
    public void setArbiter(AbstractArbiter arbiter) {
        this.arbiter = arbiter;
    }

    /**
     * Sets the map.
     *
     * @param map the new map
     */
    public void setMap(AbstractMap map) {
        this.map = map;
        this.map.setGame(this);
    }
}
