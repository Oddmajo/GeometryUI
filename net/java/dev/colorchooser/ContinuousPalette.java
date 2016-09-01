/*DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2006-2008. Tim Boudreau. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  This particular file is designated
 * as subject to the "Classpath" exception as provided
 * in the GPL Version 2 section of the License file that
 * accompanied this code.
 */

package net.java.dev.colorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/*
 * SpectrumImageImage.java
 *
 * Created on January 10, 2000, 4:54 PM
 */
/**
 * This class is palette that uses an offscreen bitmap which contains a spectrum
 * of color and hue. The direction of gradients and saturation are settable by
 * arguments to the constructor.
 * 
 * @author Tim Boudreau
 */
final class ContinuousPalette extends Palette {
	public static final int SMALL_SPEC_WIDTH = 128;
	public static final int SMALL_SPEC_HEIGHT = 64;
	public static final int LARGE_SPEC_WIDTH = 200;
	public static final int LARGE_SPEC_HEIGHT = 100;
	public static final int SPEC_IMAGE_COUNT = 8;

	private BufferedImage img = null;

	/**
	 * Set true when the bitmap has been built. Bitmap is built on the first
	 * call to paintTo(), or the first call to PaintTo() subsequent to a
	 * property being changed that affects the contents of the bitmap.
	 */
	private boolean initialized = false;

	/**
	 * Holds value of property saturation. Determines the maximum saturation
	 * level present in the bitmap
	 */
	private float saturation = 1f;
	/**
	 * Holds value of property verticalHue. When set true, hue rotation is
	 * vertical
	 */
	private boolean verticalHue = true;
	/** Default value for gray strip on horizontal hue gradients */
	private float grayStripSize = 0.05f;
	private String name;

	/**
	 * Creates a ContinuousPalette object with user specifiable size and
	 * saturation and the default horizontal hue gradient direction.
	 */
	ContinuousPalette(final String name, final int width, final int height,
			final float saturation) {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.setSaturation(saturation);
		this.name = name;
	}

	/**
	 * Creates a ContinuousPalette object with user specifiable size, saturation
	 * and hue gradient direction.
	 * 
	 * @param width
	 *            The width of the image to be created
	 * @param height
	 *            The height of the image to be created
	 * @param saturation
	 *            The saturation of the image to be created
	 * @param vHue
	 *            Sets vertical or horizontal hue gradient
	 */
	private ContinuousPalette(final String name, final int width,
			final int height, final float saturation, final boolean vHue) {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.setSaturation(saturation);
		this.verticalHue = vHue;
		this.name = name;
	}

	/**
	 * Builds the spectrum bitmap in memory by iterating through all of the
	 * pixels and calling getColorAt for each.
	 */
	protected void initImage() {
		int currColor;
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				currColor = getColorAt(x, y).getRGB();
				img.setRGB(x, y, currColor);
			}
		}
	}

	/**
	 * Public implementation of InitImage() - tests whether spectrum image has
	 * already been built and only builds it if needed.
	 */
	public final void initializeImage() {
		if (!initialized)
			initImage();
	}

	/**
	 * Paints the image at Coordinates 0,0 on the graphics context passed to it.
	 * 
	 * @param g
	 *            A graphics context to be painted into
	 */
	@Override
	public void paintTo(final java.awt.Graphics g) {
		if (g != null) {
			initializeImage();
			((Graphics2D) g).drawRenderedImage(img, AffineTransform
					.getTranslateInstance(0, 0));
			initialized = true;
		}
	}

	/**
	 * Returns the color a pixel at a given point should be as an RGB int. Color
	 * is calculated as follows - Saturation is constant up to the halfway point
	 * in the brightness gradient direction, and brightness rises. At 100%
	 * brightness, you have a fully saturated color. From this point on,
	 * Brightness is constant at 1.0 and saturation drops linearly from the
	 * saturation property value down to 0.
	 * 
	 * @param x
	 *            The X coordinate for which a color value is desired
	 * @param y
	 *            The Y coordinate for which a color value is desired
	 */
	@Override
	public java.awt.Color getColorAt(final int x, final int y) {
		float hue;
		float brightness;
		float workingSaturation;
		final boolean inGrayStrip = ((float) y) / img.getHeight() > (1 - grayStripSize);
		if (verticalHue) {
			hue = ((float) y) / img.getHeight(); // Hue value from 0-1 based on
			// y position
			brightness = ((float) x) / img.getWidth(); // base brightness value
			// 0-1 based on x
			// location
		} else {
			if (inGrayStrip)
				return grayValueFromX(x);
			hue = 1 - (((float) x) / img.getWidth()); // subtract from 1 so
			// lightest color are at
			// top
			brightness = 1 - ((float) y) / img.getHeight();
		}
		brightness = brightness * 2; // brightness increases to the halfway
		// point along the brightening axis.
		if (brightness > 1) { // beyond that point, saturation goes down so
			// color moves toward white
			workingSaturation = saturation - ((brightness - 1) * saturation); // if
			// we're
			// past
			// the
			// halfway
			// point,
			brightness = 1; // we're decrementing saturation
		} else {
			workingSaturation = saturation;
		}
		final java.awt.Color newColor = java.awt.Color.getHSBColor(hue,
				workingSaturation, brightness);
		return newColor;
	}

	public java.awt.Color colorFromPoint(final Point p) {
		final int x = new Double(p.getX()).intValue();
		final int y = new Double(p.getY()).intValue();
		return getColorAt(x, y);
	}

	/**
	 * Called by InitImage() to draw the grayscale strip at the bottom of
	 * horizontal-hue spectrum images. Returns a grayscale value based on the
	 * width of the image, when called with a horizontal coordinate.
	 * 
	 * @param x
	 *            The x coordinate for which a grayscale value is desired
	 * @return The grayscale value (expressed as an RGB integer value)
	 */
	protected java.awt.Color grayValueFromX(final int x) {
		final java.awt.Color newColor = java.awt.Color.getHSBColor(0, 0,
				((float) x) / img.getWidth());
		return newColor;
	}

	/**
	 * Getter for property saturation. Saturation defines the base saturation
	 * for all colors in the image. Setting it to less that 1 causes the colors
	 * to be more desaturated in the resulting bufferedImage.
	 * 
	 * @return Returns the maximum saturation value for any colors to be
	 *         generated in the bufferedImage.
	 */
	public float getSaturation() {
		return saturation;
	}

	/**
	 * Sets the maximum saturation for any colors in the image. Constrained from
	 * 0-1. Changing this property will cause the image to be regenerated the
	 * next time it is needed, or the next time initializeImage() is called.
	 * 
	 * @param saturation
	 *            New value of property saturation.
	 */
	public void setSaturation(final float saturation) {
		if (this.saturation != saturation) {
			this.saturation = saturation;
			doChange();
		}
	}

	/**
	 * Getter for property verticalHue.
	 * 
	 * @return Value of property verticalHue.
	 */
	public boolean isVerticalHue() {
		return verticalHue;
	}

	/**
	 * Setter for property verticalHue.
	 * 
	 * @param verticalHue
	 *            New value of property verticalHue.
	 */
	public void setVerticalHue(final boolean verticalHue) {
		if (this.verticalHue != verticalHue) {
			this.verticalHue = verticalHue;
			doChange();
		}
	}

	/**
	 * Called when a parameter is changed that requires that the spectrum image
	 * be rebuilt, so that the next time the image is asked for, it will be.
	 */
	protected void doChange() {
		initialized = false;
	}

	/**
	 * Setter for the grayStripSize property, which determines the percentage
	 * (expressed as a float between 0 and 1) of the image height taken up by
	 * the grayscale strip.
	 */
	public void setGrayStripSize(final float grayStripSize) {
		float workingGrayStripSize = grayStripSize;
		if (workingGrayStripSize > 1)
			workingGrayStripSize = 1; // handle screwy > 1 values just in case
		if (workingGrayStripSize != grayStripSize) {
			this.grayStripSize = grayStripSize;
			doChange();
		}
	}

	/**
	 * Getter for the GrayStripSize property, which returns the percentage
	 * (expressed as a float between 0 and 1) of the height of the image taken
	 * up by the gray scale strip for selecting non-colored pixels.
	 */
	public float getGrayStripSize() {
		return this.grayStripSize;
	}

	/**
	 * Returns the size of the image as a java.awt.Dimension object.
	 */
	@Override
	public Dimension getSize() {
		return new Dimension(img.getWidth(), img.getHeight());
	}

	@Override
	public String getNameAt(final int x, final int y) {
		final Color c = getColorAt(x, y);
		final StringBuffer sb = new StringBuffer();
		sb.append(c.getRed());
		sb.append(','); // NOI18N
		sb.append(c.getGreen());
		sb.append(','); // NOI18N
		sb.append(c.getBlue());
		return sb.toString();
	}

	private static Palette[] defaultPalettes = null;

	/** Create a default set of continuous palettes to use */
	public static Palette[] createDefaultPalettes() {
		if (defaultPalettes == null) {
			defaultPalettes = new Palette[] {
					new ContinuousPalette("satLarge", LARGE_SPEC_WIDTH,
							LARGE_SPEC_HEIGHT, 1f, false), // NOI18N
							new ContinuousPalette("unsatLarge", LARGE_SPEC_WIDTH,
									LARGE_SPEC_HEIGHT, 0.4f, false), // NOI18N
									new ContinuousPalette("satLargeHoriz", LARGE_SPEC_WIDTH,
											LARGE_SPEC_HEIGHT, 1f, true), // NOI18N
											new ContinuousPalette("unsatLargeHoriz", LARGE_SPEC_WIDTH,
													LARGE_SPEC_HEIGHT, 0.4f, true) // NOI18N
			};
		}
		return defaultPalettes;
	}

	@Override
	public String getDisplayName() {
		return ColorChooser.getString(name);
	}

	@Override
	public void setSize(final int w, final int h) {
	}
}
