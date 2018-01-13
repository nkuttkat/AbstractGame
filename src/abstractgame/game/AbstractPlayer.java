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
import java.util.HashSet;

import abstractgame.map.AbstractField;
import abstractgame.unit.AbstractUnit;

/**
 * The Class AbstractPlayer represents a player in this game. The player is a
 * 'filter'...everything a player does is checked by an {@link AbstractArbiter}.
 * A player starts and ends his turn. The {@link AbstractArbiter} will prevent a
 * player from moving units which doesn't belong to him.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractPlayer implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 8644947296247215404L;

    /**
     * The game the player plays.
     */
    private AbstractGame game;

    /**
     * The player name.
     */
    private String name;

    /**
     * The units belong to the player.
     */
    private final HashSet<AbstractUnit> units;

    /**
     * Instantiates a new abstract player.
     *
     * @param name the name
     * @param game the game
     */
    public AbstractPlayer(String name, AbstractGame game) {
        super();
        this.units = new HashSet<AbstractUnit>();
        this.name = name;
        this.setGame(game);
    }

    /**
     * Adds the unit.
     *
     * @param unit the unit
     */
    public void addUnit(AbstractUnit unit) {
        if (unit != null) {
            this.units.add(unit);

            // tell the unit to reference this player if it doesn't already
            if (!this.equals(unit.getPlayer())) {
                unit.setPlayer(this);
            }
        }
    }

    /**
     * Check move.
     *
     * @param unit the unit
     * @return true, if successful
     */
    public boolean checkMove(AbstractUnit unit) {
        if (this.getGame().getArbiter() != null) {
            return this.getGame().getArbiter().checkMove(unit);
        }

        return false;
    }

    /**
     * End turn.
     */
    public void endTurn() {
        // allow the player to end _his_ turn only
        if (game.getArbiter().getCurrentPlayer() == this) {
            this.game.getArbiter().switchToNextPlayer();
        }
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Move unit.
     *
     * @param unit the unit
     * @param field the field
     */
    public void moveUnit(AbstractUnit unit, AbstractField field) {
        if (this.checkMove(unit)) {
            unit.moveTo(field);
        }
    }

    /**
     * Put unit.
     *
     * @param unit the unit
     * @param field the field
     */
    public void putUnit(AbstractUnit unit, AbstractField field) {
        if (this.checkMove(unit)) {
            unit.setPosition(field);
        }
    }

    /**
     * Removes the unit.
     *
     * @param unit the unit
     */
    public void removeUnit(AbstractUnit unit) {
        this.getUnits().remove(unit);

        if (this.equals(unit.getPlayer())) {
            unit.setPlayer(null);
        }
    }

    /**
     * Sets the game.
     *
     * @param game the new game
     */
    public void setGame(AbstractGame game) {
        AbstractGame previousGame = this.game;
        this.game = game;

        if (game != null && !game.getPlayers().containsKey(this.name)) {
            game.addPlayer(this);
        }

        if (previousGame != null) {
            previousGame.removePlayer(this);
        }
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Start turn.
     */
    public void startTurn() {
        for (AbstractUnit unit : this.getUnits()) {
            unit.setCurrentMovementPoints(unit.getMaxMovementPoints());
        }
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
     */
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
