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
package abstractgame.test;

import abstractgame.map.AbstractField;

/**
 * The Class TestOctagonField.
 *
 * @author Nils Kuttkat
 */
public class TestOctagonField {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        OctagonGameMap map = new OctagonGameMap(9, 10);
        int height, width;

        AbstractField testField, testField1;
        testField = map.getField(6, 4);

        width = map.getFields().length;
        height = map.getFields()[0].length;
        System.out.println("Testkarte 9x10:");
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                System.out.print(map.getField(x, y) + "  ");
            }
            System.out.println();
        }

        // Test
        System.out.println();
        System.out.println("Test der 'Nachbarschaftsbeziehungen' "
                + "anhand des Feldes " + testField);
        System.out.println("Nachbar in Richtung 0 : "
                + testField.getNeighbor(0));
        System.out.println("Nachbar in Richtung 1 : "
                + testField.getNeighbor(1));
        System.out.println("Nachbar in Richtung 2 : "
                + testField.getNeighbor(2));
        System.out.println("Nachbar in Richtung 3 : "
                + testField.getNeighbor(3));
        System.out.println("Nachbar in Richtung 4 : "
                + testField.getNeighbor(4));
        System.out.println("Nachbar in Richtung 5 : "
                + testField.getNeighbor(5));
        System.out.println("Nachbar in Richtung 6 : "
                + testField.getNeighbor(6));
        System.out.println("Nachbar in Richtung 7 : "
                + testField.getNeighbor(7));
        System.out.println();

        // Test 1
        testField = map.getField(0, 0);
        System.out.println("Test der 'Nachbarschaftsbeziehungen' "
                + "anhand des Feldes " + testField);
        System.out.println("Nachbar in Richtung 0 : "
                + testField.getNeighbor(0));
        System.out.println("Nachbar in Richtung 1 : "
                + testField.getNeighbor(1));
        System.out.println("Nachbar in Richtung 2 : "
                + testField.getNeighbor(2));
        System.out.println("Nachbar in Richtung 3 : "
                + testField.getNeighbor(3));
        System.out.println("Nachbar in Richtung 4 : "
                + testField.getNeighbor(4));
        System.out.println("Nachbar in Richtung 5 : "
                + testField.getNeighbor(5));
        System.out.println("Nachbar in Richtung 6 : "
                + testField.getNeighbor(6));
        System.out.println("Nachbar in Richtung 7 : "
                + testField.getNeighbor(7));
        System.out.println();

        // Test 2
        testField = map.getField(2, 3);
        testField1 = map.getField(7, 5);
        System.out.println("Entfernung zwischen Feld " + testField
                + " und Feld " + testField1 + ": "
                + testField.getDistance(testField1));

        testField = map.getField(5, 4);
        testField1 = map.getField(2, 6);
        System.out.println("Entfernung zwischen Feld " + testField
                + " und Feld " + testField1 + ": "
                + testField.getDistance(testField1));
    }

    /**
     * Instantiates a new test octagon field.
     */
    public TestOctagonField() {
        super();
    }
}
