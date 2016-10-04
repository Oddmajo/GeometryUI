/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import ui.latex.Box;
import ui.latex.TeXIcon;

/**
 *
 * @author erichake
 */
public class MyTeXIcon extends TeXIcon {
    Box box;
    private final float size;
    private Insets insets = new Insets(0, 0, 0, 0);
    private Color fg = new Color(0, 0, 0);

    protected MyTeXIcon(Box b, float size) {
        super(b,size,true);
        box = b;
	if (defaultSize != -1) {
	    size = defaultSize;
	}

	if (magFactor != 0) {
	    this.size = size * Math.abs(magFactor);
	} else {
	    this.size = size;
	}

    }

    public void paintIcon(Component c, Graphics g, float x, float y) {
	Graphics2D g2 = (Graphics2D) g;
	// copy graphics settings
	RenderingHints oldHints = g2.getRenderingHints();
	AffineTransform oldAt = g2.getTransform();
	Color oldColor = g2.getColor();

	// new settings
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setRenderingHint(RenderingHints.KEY_RENDERING,
			    RenderingHints.VALUE_RENDER_QUALITY);
	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	g2.scale(size, size); // the point size
	g2.setColor(c != null ? c.getForeground() : fg); // foreground will be used as default painting color

	// draw formula box
	box.draw(g2,x/size, y /size+box.getHeight());

	// restore graphics settings
	g2.setRenderingHints(oldHints);
	g2.setTransform(oldAt);
	g2.setColor(oldColor);
    }

    @Override
    public float getBaseLine() {
	return ( box.getHeight() /(box.getHeight()+box.getDepth()));
    }

}
