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
 *       0
 *     _____
 *    /     \
 * 1 /       \ 5
 *  /         \
 *  \         /
 * 2 \       / 4
 *    \_____/
 *       3
 */
/**
 * The Class HexField represents a classical hexagonal field.
 *
 * @author Nils Kuttkat
 */
public class HexField extends AbstractField {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -6326943570564475489L;

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDirections()
     */
    @Override
    public int getDirections() {
        return 6;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see util.game.AbstractField#getDistance(util.game.AbstractField)
     */
    @Override
    public double getDistance(AbstractField abstractField) {
        double deltaX, deltaY, x1, x2, y1, y2, yPotentially, distance;

        x1 = this.getPosition().getX();
        y1 = this.getPosition().getY();
        x2 = abstractField.getPosition().getX();
        y2 = abstractField.getPosition().getY();
        deltaX = Math.abs(x2 - x1);
        deltaY = Math.abs(y2 - y1);
        yPotentially = 0;

        // If deltaX is > 0 distance calculation is tricky because a move in
        // x-direction potentially is a move in y-direction too
        if (deltaX > 0) {
            if (y2 < y1) {
                // x1 is even
                if (x1 % 2 == 0) {
                    yPotentially = Math.ceil(deltaX / 2);
                } // x1 is uneven
                else {
                    yPotentially = Math.floor(deltaX / 2);
                }
            } else if (y2 > y1) {
                // x1 is even
                if (x1 % 2 == 0) {
                    yPotentially = Math.floor(deltaX / 2);
                } // x1 is uneven
                else {
                    yPotentially = Math.ceil(deltaX / 2);
                }
            }

            // if the potentially move in y-direction is bigger or equal the
            // real deltaY we can ignore the whole deltaY and the distance is
            // simply deltaX
            if (yPotentially >= deltaY) {
                distance = deltaX;
            } // if the amount of the potential y-move is smaller than the real
            // deltaY the difference has to be added to deltaX
            else {
                distance = deltaX + deltaY - yPotentially;
            }
        } // if there is no deltaX distance is simply deltaY
        else {
            distance = deltaY;
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
        int x1, x2, y1, y2;

        x1 = this.getPosition().getX();
        y1 = this.getPosition().getY();
        x2 = -1;
        y2 = -1;

        switch (direction) {
            case 0:
                y2 = y1 + 1;
                x2 = x1;
                break;
            case 1:
                if (x1 % 2 == 0) {
                    y2 = y1;
                } else {
                    y2 = y1 + 1;
                }
                x2 = x1 + 1;
                break;
            case 2:
                if (x1 % 2 == 0) {
                    y2 = y1 - 1;
                } else {
                    y2 = y1;
                }
                x2 = x1 + 1;
                break;
            case 3:
                y2 = y1 - 1;
                x2 = x1;
                break;
            case 4:
                if (x1 % 2 == 0) {
                    y2 = y1 - 1;
                } else {
                    y2 = y1;
                }
                x2 = x1 - 1;
                break;
            case 5:
                if (x1 % 2 == 0) {
                    y2 = y1;
                } else {
                    y2 = y1 + 1;
                }
                x2 = x1 - 1;
                break;
        }

        return this.getMap().getField(x2, y2);
    }
}
