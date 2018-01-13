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

import abstractgame.unit.AbstractUnit;

/**
 * The Class StandardArbiter checks if the the unit belongs to the current
 * player.
 *
 * @author Nils Kuttkat
 */
public class StandardArbiter extends AbstractArbiter {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -4489053198925063801L;

    /**
     * Instantiates a new standard arbiter.
     *
     * @param game the game
     */
    public StandardArbiter(AbstractGame game) {
        super(game);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractArbiter#checkMove(util.game.AbstractUnit)
     */
    @Override
    public boolean checkMove(AbstractUnit abstractUnit) {
        boolean isValid = false;

        if (this.getCurrentPlayer() == abstractUnit.getPlayer()) {
            isValid = true;
        }

        return isValid;
    }

}
