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
import java.util.LinkedList;
import java.util.Queue;

import abstractgame.unit.AbstractUnit;

/**
 * The Class AbstractArbiter is some sort of referee. It manages the player
 * queue and checks a player's move (respectively subclasses do). It always
 * belongs to an {@link AbstractGame}.
 *
 * @author Nils Kuttkat
 */
public abstract class AbstractArbiter implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 4096266965324045020L;

    /**
     * The current player.
     */
    private AbstractPlayer currentPlayer;

    /**
     * The game.
     */
    private final AbstractGame game;

    /**
     * The player queue.
     */
    private final Queue<AbstractPlayer> playerQueue;

    /**
     * Instantiates a new abstract arbiter.
     *
     * @param game the game
     */
    public AbstractArbiter(AbstractGame game) {
        this.game = game;
        this.playerQueue = new LinkedList<>();
    }

    /**
     * Adds the player.
     *
     * @param player the player
     */
    public void addPlayer(AbstractPlayer player) {
        if (!this.playerQueue.contains(player)) {
            this.playerQueue.add(player);
        }
    }

    /**
     * Check move.
     *
     * @param abstractUnit the abstract unit
     * @return true, if successful
     */
    public abstract boolean checkMove(AbstractUnit abstractUnit);

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public AbstractPlayer getCurrentPlayer() {

        if (this.currentPlayer == null) {
            return this.switchToNextPlayer();
        } else {
            return this.currentPlayer;
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
     * Gets the player queue.
     *
     * @return the player queue
     */
    public Queue<AbstractPlayer> getPlayerQueue() {
        return this.playerQueue;
    }

    /**
     * Inits the player queue.
     */
    public void initPlayerQueue() {
        for (AbstractPlayer player : this.game.getPlayers().values()) {
            this.addPlayer(player);
        }
    }

    /**
     * Removes the player.
     *
     * @param player the player
     */
    public void removePlayer(AbstractPlayer player) {
        this.playerQueue.remove(player);
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(AbstractPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Switch to next player.
     *
     * @return the abstract player
     */
    public AbstractPlayer switchToNextPlayer() {
        // first add the currentPlayer at the tail of the queue
        if (currentPlayer != null) {
            playerQueue.offer(this.currentPlayer);
        }

        this.currentPlayer = this.playerQueue.poll();
        this.currentPlayer.startTurn();
        return this.currentPlayer;
    }
}
