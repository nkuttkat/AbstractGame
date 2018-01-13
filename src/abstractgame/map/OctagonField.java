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
 *   1___0___7 
 *   |       | 
 *  2|       |6
 *   |       |
 *   |_______|
 *   3   4   5
 */
/**
 * Actually 'octagon' fields are squares using the corners as edges.
 *
 * @author Nils Kuttkat
 */
public class OctagonField extends AbstractField {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -8617192801284851474L;

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDirections()
     */
    @Override
    public int getDirections() {
        return 8;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDistance(util.game.AbstractField)
     */
    @Override
    public double getDistance(AbstractField af) {
        int deltaX, deltaY;
        double distance;

        deltaX = Math.abs(this.getPosition().getX() - af.getPosition().getX());
        deltaY = Math.abs(this.getPosition().getY() - af.getPosition().getY());

        if (deltaX >= deltaY) {
            distance = Math.abs(deltaX - deltaY) + (Math.sqrt(2) * deltaY);
        } else {
            distance = Math.abs(deltaY - deltaX) + (Math.sqrt(2) * deltaX);
        }

        return distance;
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
                neighbor = this.getMap().getField(this.getPosition().translate(1, 1));
                break;
            case 2:
                neighbor = this.getMap().getField(this.getPosition().translate(1, 0));
                break;
            case 3:
                neighbor = this.getMap().getField(this.getPosition().translate(1, -1));
                break;
            case 4:
                neighbor = this.getMap().getField(this.getPosition().translate(0, -1));
                break;
            case 5:
                neighbor = this.getMap().getField(this.getPosition().translate(-1, -1));
                break;
            case 6:
                neighbor = this.getMap().getField(this.getPosition().translate(-1, 0));
                break;
            case 7:
                neighbor = this.getMap().getField(this.getPosition().translate(-1, 1));
                break;
        }

        return neighbor;
    }
}
