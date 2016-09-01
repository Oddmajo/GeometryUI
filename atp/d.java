/*****************************************************************************
 *                                                                            *
 *                   HotEqn Equation Viewer Basic Applet                      *
 *                                                                            *
 ******************************************************************************
 * Java Applet to view mathematical Equations provided in the LaTeX language  *
 ******************************************************************************

Copyright 2006 Stefan Müller and Christian Schmid, modified by Rene Grothmann

This file is part of the HotEqn package.

    HotEqn is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation;
    HotEqn is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package atp;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

// Referenced classes of package atp:
//            a

class d {

	private final ImageProducer _flddo[] = { null, null, null, null, null };
	private final String _fldif[] = { "8", "10", "12", "14", "18" };
	private final Hashtable _fldfor;
	private static boolean a = true;

	public d() {
		_fldfor = new Hashtable(189);
	}

	public Image a(final boolean flag, final boolean flag1, final String s,
			final Graphics2D g, final Applet applet) {
		final StringTokenizer stringtokenizer = new StringTokenizer(s, "/");
		String s1 = stringtokenizer.nextToken();
		s1 = stringtokenizer.nextToken().substring(5);
		final String s2 = stringtokenizer.nextToken();
		int i = -1;
		boolean flag2 = true;
		while (flag2) {
			if (_fldif[++i].equals(s1)) {
				flag2 = false;
			}
			if (i == 4) {
				flag2 = false;
			}
		}
		if (_flddo[i] == null) {
			_flddo[i] = a(flag, flag1, "Fonts" + s1 + ".gif", applet);
			final String s3 = "Des" + s1 + ".gif";
			BufferedInputStream bufferedinputstream = null;
			try {
				if (a) {
					getClass().getResourceAsStream(s3);
					bufferedinputstream = new BufferedInputStream(getClass()
							.getResourceAsStream(s3));
				} else if ((!flag) & (!flag1)) {
					bufferedinputstream = new BufferedInputStream((new URL(s3))
							.openStream());
				} else if (flag) {
					bufferedinputstream = new BufferedInputStream((new URL(
							applet.getCodeBase(), s3)).openStream());
				} else {
					try {
						bufferedinputstream = new BufferedInputStream(
								getClass().getResource(s3).openStream());
					} catch (final Exception exception) {
					}
				}
				final ObjectInputStream objectinputstream = new ObjectInputStream(
						bufferedinputstream);
				final int j = objectinputstream.readInt();
				for (int k = 0; k < j; k++) {
					final String s4 = (String) objectinputstream.readObject();
					_fldfor.put(s1 + s4, new Rectangle(
							(Rectangle) objectinputstream.readObject()));
				}

				bufferedinputstream.close();
			} catch (final Exception exception1) {
				// System.out.println(exception1.toString());
				_flddo[i] = null;
			}
		}
		Image image = null;
		if (_flddo[i] != null) {
			final Rectangle rectangle = (Rectangle) _fldfor.get(s1 + s2);
			image = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(new FilteredImageSource(_flddo[i],
							new CropImageFilter(rectangle.x, rectangle.y,
									rectangle.width, rectangle.height)), new a(
											g.getColor())));
		}
		return image;
	}

	public ImageProducer a(final boolean flag, final boolean flag1,
			final String s, final Applet applet) {
		ImageProducer imageproducer = null;
		if (a) {
			imageproducer = a(s);
		}
		if (imageproducer == null) {
			a = false;
			if ((!flag) & (!flag1)) {
				imageproducer = Toolkit.getDefaultToolkit().getImage(s)
				.getSource();
			} else if (flag) {
				imageproducer = applet.getImage(applet.getCodeBase(), s)
				.getSource();
			} else {
				try {
					final URL url = getClass().getResource(s);
					imageproducer = (ImageProducer) url.getContent();
				} catch (final Exception exception) {
				}
			}
		}
		return imageproducer;
	}

	ImageProducer a(final String s) {
		ImageProducer imageproducer = null;
		try {
			final InputStream inputstream = getClass().getResourceAsStream(s);
			int i = inputstream.available();
			byte abyte0[] = new byte[i];
			int j = 0;
			for (int k = 0; k != -1;) {
				k = inputstream.read(abyte0, j, i);
				if (k != -1) {
					j += k;
					i = inputstream.available();
					final int l = j + i;
					if (l > abyte0.length) {
						final byte abyte1[] = (byte[]) abyte0.clone();
						abyte0 = new byte[l];
						System.arraycopy(abyte1, 0, abyte0, 0, j);
					}
				}
				if (i == 0) {
					break;
				}
			}

			imageproducer = Toolkit.getDefaultToolkit().createImage(abyte0)
			.getSource();
		} catch (final Exception exception) {
		}
		return imageproducer;
	}

}
