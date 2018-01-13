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

/*
 *    ___0___ 
 *   |       | 
 *  1|       |3
 *   |       |
 *   |_______|
 *       2   
 */
/**
 * The Class SquareField...a classic
 *
 * @author Nils Kuttkat
 */
public class SquareField extends AbstractField {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -641946286682753472L;

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDirections()
     */
    @Override
    public int getDirections() {
        return 4;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDistance(util.game.AbstractField)
     */
    @Override
    public double getDistance(AbstractField abstractField) {
        int deltaX, deltaY;
        deltaX = this.getPosition().getX() - abstractField.getPosition().getX();
        deltaY = this.getPosition().getY() - abstractField.getPosition().getY();
        return Math.abs(deltaX) + Math.abs(deltaY);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getNeighbor(int)
     */
    @Override
    public AbstractField getNeighbor(int direction) {
        AbstractField neighbor = null;

        switch (direction) {
            case 0:
                neighbor = this.getMap().getField(this.getPosition().translate(0, 1));
                break;
            case 1:
                neighbor = this.getMap().getField(this.getPosition().translate(1, 0));
                break;
            case 2:
                neighbor = this.getMap().getField(this.getPosition().translate(0, -1));
                break;
            case 3:
                neighbor = this.getMap().getField(this.getPosition().translate(-1, 0));
                break;
        }

        return neighbor;
    }
}
