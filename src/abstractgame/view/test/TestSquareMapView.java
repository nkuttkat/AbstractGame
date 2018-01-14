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
package abstractgame.view.test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import abstractgame.test.SquareGameMap;
import abstractgame.view.SquareMapView;

/**
 * The Class TestSquareMapView.
 *
 * @author Nils Kuttkat
 */
public class TestSquareMapView extends JFrame {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SquareGameMap mapModel = new SquareGameMap(50, 50);
        JScrollPane scrollPane = new JScrollPane();
        SquareGameMapView map = new SquareGameMapView(mapModel);
        map.initialize(70, null);
        scrollPane.setViewportView(map);
        frame.add(scrollPane);
        frame.setBounds(0, 0, 1000, 740);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
