/* 
 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

This file is part of C.a.R. software.

    C.a.R. is a free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, version 3 of the License.

    C.a.R. is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 
 */
 
 
 package rene.zirkel.graphics;

/* Graphics class supporting EPS export from plots.

 Copyright (c) 1998-2000 The Regents of the University of
 California.

 Modified, completed and extended by R. Grothmann

 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

class EpsFontMetrics extends FontMetrics {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font F;

	public EpsFontMetrics(final Font f) {
		super(f); // a dummy font.
		F = f;
	}

	@Override
	public int stringWidth(final String s) {
		return s.length() * F.getSize() / 2;
	}

	@Override
	public int getHeight() {
		return F.getSize();
	}

	@Override
	public int getAscent() {
		return F.getSize() * 4 / 5;
	}

}

class EpsPoint {
	double x, y;

	public EpsPoint(final double xx, final double yy) {
		x = xx;
		y = yy;
	}
}

public class EPSGraphics {

	// ///////////////////////////////////////////////////////////////
	//
	// // private variables
	// //

	private Color _currentColor = Color.black;
	private Font _currentFont;
	private final double _height;
	int _orientation;
	private final OutputStream _out;
	private final StringBuffer _buffer = new StringBuffer();
	private double LineWidth = 1;

	public static final int PORTRAIT = 0;
	public static final int LANDSCAPE = 1;

	private FontMetrics FM;

	public EPSGraphics(final OutputStream out, final double width,
			final double height, final int orientation, final boolean clip) {
		_height = height;
		_orientation = orientation;
		_out = out;
		_buffer.append("%!PS-Adobe-3.0 EPSF-3.0\n");
		_buffer.append("%%Creator: QCircuitBuilder\n");
		_buffer.append("%%BoundingBox: 50 50 " + (int) (50 + width) + " "
				+ (int) (50 + height) + "\n");
		// _buffer.append("%%Orientation: " + (_orientation == PORTRAIT ?
		// "Portrait" : "Landscape"));
		// _buffer.append("%%PageOrientation: " + (_orientation == PORTRAIT ?
		// "Portrait" : "Landscape"));
		_buffer.append("%%Pages: 1\n");
		_buffer.append("%%Page: 1 1\n");
		_buffer.append("%%LanguageLevel: 2\n");
		if (clip)
			clipRect(0, 0, width, height);
		_buffer.append("/Helvetica findfont 10 scalefont setfont\n");
	}

	public void clearRect(final int x, final int y, final int width,
			final int height) {
	}

	// Clip
	public void clipRect(final double x, final double y, final double width,
			final double height) {
		final EpsPoint start = _convert(x, y);
		// _fillPattern();
		_buffer.append("newpath " + round(start.x) + " " + round(start.y)
				+ " moveto\n");
		_buffer.append("0 " + round(-height) + " rlineto\n");
		_buffer.append("" + round(width) + " 0 rlineto\n");
		_buffer.append("0 " + round(height) + " rlineto\n");
		_buffer.append("" + round(-width) + " 0 rlineto\n");
		_buffer.append("closepath clip\n");
	}

	private double round(final double x) {
		return Math.round(x * 1000.0) / 1000.0;
	}

	/**
	 * Draw a line, using the current color, between the points (x1, y1) and
	 * (x2, y2) in this graphics context's coordinate system.
	 * 
	 * @param x1
	 *            the x coordinate of the first point.
	 * @param y1
	 *            the y coordinate of the first point.
	 * @param x2
	 *            the x coordinate of the second point.
	 * @param y2
	 *            the y coordinate of the second point.
	 */
	public void drawLine(final double x1, final double y1, final double x2,
			final double y2) {
		final EpsPoint start = _convert(x1, y1);
		final EpsPoint end = _convert(x2, y2);
		_buffer.append("newpath " + round(start.x) + " " + round(start.y)
				+ " moveto\n");
		_buffer.append("" + round(end.x) + " " + round(end.y) + " lineto\n");
		_buffer.append("stroke\n");
	}

	/**
	 * Draw a closed polygon defined by arrays of x and y coordinates. Each pair
	 * of (x, y) coordinates defines a vertex. The third argument gives the
	 * number of vertices. If the arrays are not long enough to define this many
	 * vertices, or if the third argument is less than three, then nothing is
	 * drawn.
	 * 
	 * @param xPoints
	 *            An array of x coordinates.
	 * @param yPoints
	 *            An array of y coordinates.
	 * @param nPoints
	 *            The total number of vertices.
	 */
	public void drawPolygon(final double xPoints[], final double yPoints[],
			final int nPoints) {
		if (!_polygon(xPoints, yPoints, nPoints)) {
			return;
		} else {
			_buffer.append("closepath stroke\n");
		}
	}

	/**
	 * Draw an oval bounded by the specified rectangle with the current color.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner
	 * @param y
	 *            The y coordinate of the upper left corner
	 * @param width
	 *            The width of the oval to be filled.
	 * @param height
	 *            The height of the oval to be filled.
	 */
	// FIXME: Currently, this ignores the fourth argument and
	// draws a circle with diameter given by the third argument.
	public void drawOval(final double x, final double y, final double width,
			final double height) {
		final double radius = width / 2.0;
		_buffer.append("newpath " + _convertX(x + radius) + " "
				+ _convertY(y + radius) + " " + round(radius)
				+ " 0 360 arc closepath stroke\n");
	}

	public void drawRect(final double x, final double y, final double width,
			final double height) {
		final EpsPoint start = _convert(x, y);
		_buffer.append("newpath " + round(start.x) + " " + round(start.y)
				+ " moveto\n");
		_buffer.append("0 " + round(-height) + " rlineto\n");
		_buffer.append("" + round(width) + " 0 rlineto\n");
		_buffer.append("0 " + round(height) + " rlineto\n");
		_buffer.append("" + round(-width) + " 0 rlineto\n");
		_buffer.append("closepath stroke\n");
	}

	public void drawRoundRect(final double x, final double y,
			final double width, final double height, final int arcWidth,
			final int arcHeight) {
	}

	public void drawString(
			final java.text.AttributedCharacterIterator iterator, final int x,
			final int y) {
	}

	public void drawString(final String str, final double x, final double y) {
		getFontMetrics();
		final EpsPoint start = _convert(x, y);
		_buffer.append("" + start.x + " " + start.y + " moveto\n");
		_buffer.append("(" + str + ") show\n");
	}

	public void drawArc(final double x, final double y, final double width,
			final double height, final double startAngle, final double arcAngle) {
		final double radius = width / 2.0;
		_buffer.append("newpath " + _convertX(x + radius) + " "
				+ _convertY(y + radius) + " " + round(radius) + " "
				+ round(startAngle) + " " + " " + round(startAngle + arcAngle)
				+ " arc stroke\n");
	}

	public void fillArc(final double x, final double y, final double width,
			final double height, final double startAngle, final double arcAngle) {
		final double radius = width / 2.0;
		_buffer.append("newpath " + _convertX(x + radius) + " "
				+ _convertY(y + radius) + " " + " moveto "
				+ _convertX(x + radius) + " " + _convertY(y + radius) + " "
				+ radius + " " + round(startAngle) + " "
				+ round(startAngle + arcAngle) + " arc closepath fill\n");
	}

	public void fillChord(final double x, final double y, final double width,
			final double height, final double startAngle, final double arcAngle) {
		final double radius = width / 2.0;
		_buffer.append("newpath " + _convertX(x + radius) + " "
				+ _convertY(y + radius) + " " + round(radius) + " "
				+ round(startAngle) + " " + round(startAngle + arcAngle)
				+ " arc fill\n");
	}

	public void fillPolygon(final double xPoints[], final double yPoints[],
			final int nPoints) {
		if (!_polygon(xPoints, yPoints, nPoints)) {
			return;
		} else {
			_buffer.append("closepath fill\n");
		}
	}

	/**
	 * Fill an oval bounded by the specified rectangle with the current color.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner
	 * @param y
	 *            The y coordinate of the upper left corner
	 * @param width
	 *            The width of the oval to be filled.
	 * @param height
	 *            The height of the oval to be filled.
	 */
	// FIXME: Currently, this ignores the fourth argument and draws a circle
	// with diameter given by the third argument.
	public void fillOval(final double x, final double y, final double width,
			final double height) {
		final double radius = width / 2.0;
		_buffer.append("newpath " + _convertX(x + radius) + " "
				+ _convertY(y + radius) + " " + radius
				+ " 0 360 arc closepath fill\n");
	}

	/**
	 * Fill the specified rectangle and draw a thin outline around it. The left
	 * and right edges of the rectangle are at x and x + width - 1. The top and
	 * bottom edges are at y and y + height - 1. The resulting rectangle covers
	 * an area width pixels wide by height pixels tall. The rectangle is filled
	 * using the brightness of the current color to set the level of gray.
	 * 
	 * @param x
	 *            The x coordinate of the top left corner.
	 * @param y
	 *            The y coordinate of the top left corner.
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 */
	public void fillRect(final double x, final double y, final double width,
			final double height) {
		final EpsPoint start = _convert(x, y);
		// _fillPattern();
		_buffer.append("newpath " + start.x + " " + start.y + " moveto\n");
		_buffer.append("0 " + round(-height) + " rlineto\n");
		_buffer.append("" + round(width) + " 0 rlineto\n");
		_buffer.append("0 " + round(height) + " rlineto\n");
		_buffer.append("" + round(-width) + " 0 rlineto\n");
		_buffer.append("closepath gsave fill grestore\n");
		_buffer.append("0.5 setlinewidth 0 setgray [] 0 setdash stroke\n");
		// reset the gray scale to black
		_buffer.append(round(LineWidth) + " setlinewidth\n");
	}

	public void fillRoundRect(final double x, final double y,
			final double width, final double height, final int arcWidth,
			final int arcHeight) {
	}

	public Shape getClip() {
		return null;
	}

	public Rectangle getClipBounds() {
		return null;
	}

	public Color getColor() {
		return _currentColor;
	}

	public Font getFont() {
		return _currentFont;
	}

	public FontMetrics getFontMetrics(final Font f) {
		if (FM == null)
			FM = new EpsFontMetrics(new Font("dialog", Font.PLAIN, 20));
		return FM;
	}

	public FontMetrics getFontMetrics() {
		return getFontMetrics(_currentFont);
	}

	public void setFont(final Font font) {
		final int size = font.getSize();
		final boolean bold = font.isBold();
		if (bold) {
			_buffer.append("/Helvetica-Bold findfont\n");
		} else {
			_buffer.append("/Helvetica findfont\n");
		}
		_buffer.append("" + size + " scalefont setfont\n");
		_currentFont = font;
		FM = new EpsFontMetrics(font);
	}

	public void setClip(final Shape clip) {
	}

	public void setClip(final int x, final int y, final int width,
			final int height) {
	}

	/**
	 * Set the current color.
	 * 
	 * @param c
	 *            The desired current color.
	 */
	public void setColor(final Color c) {
		_buffer.append(c.getRed() / 255.0);
		_buffer.append(" ");
		_buffer.append(c.getGreen() / 255.0);

		_buffer.append(" ");
		_buffer.append(c.getBlue() / 255.0);

		_buffer.append(" setrgbcolor\n");
		// _buffer.append("[] 0 setdash\n");
		// _buffer.append("1 setlinewidth\n");

		_currentColor = c;
	}

	public void setLineWidth(final double w) {
		_buffer.append(round(w) + " setlinewidth\n");
		LineWidth = w;
	}

	public void setDash(final double a, final double b) {
		_buffer.append("[" + round(a) + " " + round(b) + " ] 0 setdash\n");
	}

	public void clearDash() {
		_buffer.append("[ ] 0 setdash\n");
	}

	public void setPaintMode() {
	}

	public void setXORMode(final Color c1) {
	}

	/**
	 * Issue the PostScript showpage command, then write and flush the output.
	 */
	public void showpage(final String name) {
		try {
			// _buffer.append("showpage\n");
			_buffer.append("%%EOF");

			final PrintWriter output = new PrintWriter(new java.io.FileWriter(
					name));

			output.println(_buffer.toString());
			output.flush();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Issue the PostScript showpage command, then write and flush the output.
	 */
	public void close() throws IOException {
		_buffer.append("showpage\n");
		_buffer.append("%%EOF");

		final PrintWriter output = new PrintWriter(_out);

		output.println(_buffer.toString());
		output.flush();

	}

	// ///////////////////////////////////////////////////////////////
	//
	// // private methods
	// //

	// Convert the screen coordinate system to that of postscript.
	private EpsPoint _convert(final double x, final double y) {
		return new EpsPoint(round(x + 50), round(_height + 50 - y));
	}

	private double _convertX(final double x) {
		return round(x + 50);
	}

	private double _convertY(final double y) {
		return round(_height + 50 - y);
	}

	// Draw a closed polygon defined by arrays of x and y coordinates.
	// Return false if arguments are misformed.
	private boolean _polygon(final double xPoints[], final double yPoints[],
			final int nPoints) {
		if (nPoints < 3 || xPoints.length < nPoints || yPoints.length < nPoints)
			return false;
		final EpsPoint start = _convert(xPoints[0], yPoints[0]);
		_buffer.append("newpath " + round(start.x) + " " + round(start.y)
				+ " moveto\n");
		for (int i = 1; i < nPoints; i++) {
			final EpsPoint vertex = _convert(xPoints[i], yPoints[i]);
			_buffer.append("" + round(vertex.x) + " " + round(vertex.y)
					+ " lineto\n");
		}
		return true;
	}

}
