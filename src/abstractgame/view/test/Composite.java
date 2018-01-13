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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// TODO: Auto-generated Javadoc
/*
 * This applet renders an ellipse overlapping a rectangle with the compositing rule and
 * alpha value selected by the user.
 */
/**
 * The Class Composite.
 *
 * @author Nils Kuttkat
 */
public class Composite extends JApplet implements ItemListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The main method.
     *
     * @param s the arguments
     */
    public static void main(String s[]) {
        JFrame f = new JFrame("Composite");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet applet = new Composite();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(300, 300));
        f.setVisible(true);
    }

    /**
     * The alpha.
     */
    String alpha = "1.0";

    /**
     * The rules label.
     */
    JLabel alphaLabel, rulesLabel;

    /**
     * The rules.
     */
    JComboBox<String> alphas, rules;

    /**
     * The comp.
     */
    CompPanel comp;

    /**
     * The rule.
     */
    int rule = 0;

    // Initializes the layout of the components.
    /*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#init()
     */
    @Override
    public void init() {
        GridBagLayout layOut = new GridBagLayout();
        getContentPane().setLayout(layOut);

        GridBagConstraints l = new GridBagConstraints();
        l.weightx = 1.0;
        l.fill = GridBagConstraints.BOTH;
        l.gridwidth = GridBagConstraints.RELATIVE;
        alphaLabel = new JLabel();
        alphaLabel.setText("Alphas");
        Font newFont = getFont().deriveFont(1);
        alphaLabel.setFont(newFont);
        alphaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layOut.setConstraints(alphaLabel, l);
        getContentPane().add(alphaLabel);
        getContentPane().setLayout(layOut);

        l.gridwidth = GridBagConstraints.REMAINDER;
        rulesLabel = new JLabel();
        rulesLabel.setText("Rules");
        newFont = getFont().deriveFont(1);
        rulesLabel.setFont(newFont);
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layOut.setConstraints(rulesLabel, l);
        getContentPane().add(rulesLabel);

        GridBagConstraints a = new GridBagConstraints();
        a.gridwidth = GridBagConstraints.RELATIVE;
        a.weightx = 1.0;
        a.fill = GridBagConstraints.BOTH;
        alphas = new JComboBox<String>();
        layOut.setConstraints(alphas, a);
        alphas.addItem("1.0");
        alphas.addItem("0.75");
        alphas.addItem("0.50");
        alphas.addItem("0.25");
        alphas.addItem("0.0");
        alphas.addItemListener(this);
        getContentPane().add(alphas);

        a.gridwidth = GridBagConstraints.REMAINDER;
        rules = new JComboBox<String>();
        layOut.setConstraints(rules, a);
        rules.addItem("SRC");
        rules.addItem("DST_IN");
        rules.addItem("DST_OUT");
        rules.addItem("DST_OVER");
        rules.addItem("SRC_IN");
        rules.addItem("SRC_OVER");
        rules.addItem("SRC_OUT");
        rules.addItem("CLEAR");
        rules.addItemListener(this);
        getContentPane().add(rules);

        GridBagConstraints fC = new GridBagConstraints();
        fC.fill = GridBagConstraints.BOTH;
        fC.weightx = 1.0;
        fC.weighty = 1.0;
        fC.gridwidth = GridBagConstraints.REMAINDER;
        comp = new CompPanel();
        layOut.setConstraints(comp, fC);
        getContentPane().add(comp);

        validate();
    }

    /*
	 * Detects a change in either of the Choice components. Resets the variable
	 * corresponding to the Choice whose state is changed. Invokes changeRule in
	 * CompPanel with the current alpha and composite rules.
     */
 /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        Object choice = e.getSource();
        if (choice == alphas) {
            alpha = (String) alphas.getSelectedItem();
        } else {
            rule = rules.getSelectedIndex();
        }
        comp.changeRule(alpha, rule);
    }
}

class CompPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
    float alpha = 1.0f;

    public CompPanel() {
    }

    // Resets the alpha and composite rules with selected items.
    public void changeRule(String a, int rule) {
        alpha = Float.valueOf(a).floatValue();
        ac = AlphaComposite.getInstance(getRule(rule), alpha);
        repaint();
    }

    // Gets the requested compositing rule.
    public int getRule(int rule) {
        int alphaComp = 0;
        switch (rule) {
            case 0:
                alphaComp = AlphaComposite.SRC;
                break;
            case 1:
                alphaComp = AlphaComposite.DST_IN;
                break;
            case 2:
                alphaComp = AlphaComposite.DST_OUT;
                break;
            case 3:
                alphaComp = AlphaComposite.DST_OVER;
                break;
            case 4:
                alphaComp = AlphaComposite.SRC_IN;
                break;
            case 5:
                alphaComp = AlphaComposite.SRC_OVER;
                break;
            case 6:
                alphaComp = AlphaComposite.SRC_OUT;
                break;
            case 7:
                alphaComp = AlphaComposite.CLEAR;
                break;
        }
        return alphaComp;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Dimension d = getSize();
        int w = d.width;
        int h = d.height;

        // Creates the buffered image.
        BufferedImage buffImg = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = buffImg.createGraphics();

        // Clears the previously drawn image.
        g2.setColor(Color.white);
        g2.fillRect(0, 0, d.width, d.height);

        int rectx = w / 4;
        int recty = h / 4;

        // Draws the rectangle and ellipse into the buffered image.
        gbi.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
        gbi.fill(new Rectangle2D.Double(rectx, recty, 150, 100));
        gbi.setColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));
        gbi.setComposite(ac);
        gbi.fill(new Ellipse2D.Double(rectx + rectx / 2, recty + recty / 2,
                150, 100));

        // Draws the buffered image.
        g2.drawImage(buffImg, null, 0, 0);
    }
}
