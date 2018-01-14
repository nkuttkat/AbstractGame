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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import abstractgame.map.AbstractMap;
import abstractgame.test.HexGameMap;
import abstractgame.test.SquareGameMap;
import abstractgame.view.AbstractFieldView;

/**
 * The Class EditorFrame.
 *
 * @author Nils Kuttkat
 */
public class EditorFrame extends JFrame implements MouseListener {

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
        EditorFrame ef = new EditorFrame();
        JFrame frame = new JFrame();
        JScrollPane scrollpane = new JScrollPane();
        JSplitPane splitPane = new JSplitPane();
        AbstractMap mapModel = new SquareGameMap(20, 20);
        SquareGameMapView mapView = new SquareGameMapView(mapModel);
        //AbstractMap mapModel = new HexGameMap(20, 20);
        //HexGameMapView mapView = new HexGameMapView(mapModel);
        mapView.initialize(40, ef);

        frame.setBounds(0, 0, 1000, 740);

        scrollpane.setViewportView(mapView);

        splitPane.setLeftComponent(scrollpane);
        splitPane.setRightComponent(ef.getAttributePanel());
        splitPane.setDividerLocation((int) Math.round(frame.getWidth() * 0.75));

        frame.add(splitPane);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * The attribute panel.
     */
    JPanel attributePanel = new JPanel();

    /**
     * The pic label.
     */
    JLabel coordsLabel = new JLabel(), contentLabel = new JLabel(),
            picLabel = new JLabel();

    /**
     * Instantiates a new editor frame.
     */
    public EditorFrame() {
        attributePanel.add(this.coordsLabel);
        attributePanel.add(this.contentLabel);
        attributePanel.add(this.picLabel);
    }

    /**
     * Gets the attribute panel.
     *
     * @return the attribute panel
     */
    public JPanel getAttributePanel() {
        return attributePanel;
    }

    /**
     * Gets the content label.
     *
     * @return the content label
     */
    public JLabel getContentLabel() {
        return contentLabel;
    }

    /**
     * Gets the coords label.
     *
     * @return the coords label
     */
    public JLabel getCoordsLabel() {
        return coordsLabel;
    }

    /**
     * Gets the pic label.
     *
     * @return the pic label
     */
    public JLabel getPicLabel() {
        return picLabel;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Square field = (Square) e.getComponent();

        if (e.getButton() == MouseEvent.BUTTON1) {
            Image bi = new BufferedImage(field.getWidth() - 1,
                    field.getHeight() - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = ((BufferedImage) bi).createGraphics();
            g.setColor(e.getComponent().getForeground());
            g.fill(((AbstractFieldView) e.getComponent()).getShape());
            try {
                ImageIO.write((BufferedImage) bi, "png", new File(
                        "save_pic.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            File file = new File("pics/mountain.png");
            try {
                BufferedImage bi = ImageIO.read(file);
                field.setImage(bi);
                field.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            /*
			 * Image i; i = Toolkit.getDefaultToolkit().createImage(
			 * "util/game/view/test/test.png"); //((Hex)
			 * e.getComponent()).setImage(new JLabel(new ImageIcon(i)));
			 * ((Square) e.getComponent()).setImage(new JLabel(new
			 * ImageIcon(i)));
             */
        }
        System.out.println(e.getButton());
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        coordsLabel.setText("Position: "
                + ((AbstractFieldView) e.getComponent()).getModel()
                        .getPosition().toString());
        contentLabel.setText(((AbstractFieldView) e.getComponent()).getModel()
                .getUnits().toString());
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Sets the attribute panel.
     *
     * @param attributePanel the new attribute panel
     */
    public void setAttributePanel(JPanel attributePanel) {
        this.attributePanel = attributePanel;
    }

    /**
     * Sets the content label.
     *
     * @param contentLabel the new content label
     */
    public void setContentLabel(JLabel contentLabel) {
        this.contentLabel = contentLabel;
    }

    /**
     * Sets the coords label.
     *
     * @param coordsLabel the new coords label
     */
    public void setCoordsLabel(JLabel coordsLabel) {
        this.coordsLabel = coordsLabel;
    }

    /**
     * Sets the pic label.
     *
     * @param picLabel the new pic label
     */
    public void setPicLabel(JLabel picLabel) {
        this.picLabel = picLabel;
    }

}
