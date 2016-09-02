/* 
 
Copyright 2006 Eric Hakenholz

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
 
 
 package eric.controls;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 * 
 * @author erichake
 */
public class JCanvasSlider extends JCanvasPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int PREFEREDVMAX = 10000;
	int VMAX = PREFEREDVMAX;
	int TICKS = 1000;
	double xMIN, xMAX, xTICKS;
	MyJSlider JCS;

	public JCanvasSlider(final ZirkelCanvas zc, final ExpressionObject o,
			final double min, final double max, final double val) {
		super(zc, o);
		xMIN = min;
		xMAX = max;
		xTICKS = getCurrentTicks();
		setVal(val);
		JSL = new MyJSlider(0, VMAX, TICKS, (int) Math.round((val - xMIN)
				* VMAX / (xMAX - xMIN)));
                JSL.putClientProperty("JComponent.sizeVariant", "regular");
		JCS = (MyJSlider) JSL;
		JCS.addMouseListener(this);
		JCS.addMouseMotionListener(this);
		setComment(O.getName() + "=");
		this.add(JCS);
		this.add(JCPlabel);
		this.add(JCPresize);
		zc.add(this);
	}

	public void setGoodKnobPos(final double x) {
		final int i = (int) Math.round((x - xMIN) * (VMAX / (xMAX - xMIN)));
		JCS.setValue(i);
	}

	public double getCurrentTicks() {
		return TICKS * (xMAX - xMIN) / (VMAX);
	}

	public void setTicks(final String s) {
		setTicks(Double.parseDouble(s));
	}

	public void setTicks(final double x) {
		xTICKS = x;
		adjustVirtualMax();
		TICKS = (int) Math.round(x * VMAX / (xMAX - xMIN));
		JCS.setMinorTickSpacing(TICKS);
		this.revalidate();
		this.repaint();
	}

	public String getTicks() {
		return String.valueOf(xTICKS);
	}

	public void adjustVirtualMax() {
		if ((xMAX - xMIN) < 1) {
			return;
		}
		final int mySQRT = (int) Math.round(Math.sqrt(PREFEREDVMAX));

		// VMAX/(xMAX-xMIN) must be an integer :
		VMAX = (int) Math.round(Math.ceil(mySQRT / (xMAX - xMIN))
				* (xMAX - xMIN));
		// VMAX/xTICKS must also be an integer :
		VMAX *= (int) Math.round(Math.ceil(mySQRT / xTICKS) * xTICKS);
		JCS.setMaximum(VMAX);
	}

	public void setMax(final String s) {
		setMax(Double.parseDouble(s));
	}

	public void setMax(final double x) {
		xMAX = x;
		if (xMIN > xMAX) {
			xMIN = xMAX - 10;
		}
		adjustVirtualMax();
		double newval = (getVal() > xMAX) ? xMAX : getVal();
		newval = (getVal() < xMIN) ? xMIN : getVal();
		setVal(newval);
		JCS.setValue((int) Math.round((newval - xMIN) * VMAX / (xMAX - xMIN)));
		setTicks(xTICKS);
		this.revalidate();
		this.repaint();
	}

	public String getMax() {
		return String.valueOf(xMAX);
	}

	public void setMin(final String s) {
		setMin(Double.parseDouble(s));
	}

	public void setMin(final double x) {
		xMIN = x;
		if (xMIN > xMAX) {
			xMAX = xMIN + 10;
		}
		adjustVirtualMax();
		double newval = (getVal() > xMAX) ? xMAX : getVal();
		newval = (getVal() < xMIN) ? xMIN : getVal();
		setVal(newval);
		JCS.setValue((int) Math.round((newval - xMIN) * VMAX / (xMAX - xMIN)));
		setTicks(xTICKS);
		this.revalidate();
		this.repaint();
	}

	public String getMin() {
		return String.valueOf(xMIN);
	}

	public void setSnap(final boolean b) {
		JCS.setSnapToTicks(b);
		this.revalidate();
		this.repaint();
	}

	public boolean getSnap() {
		return JCS.getSnapToTicks();
	}

	public void setShowTicks(final boolean b) {
		JCS.setPaintTicks(b);
		this.revalidate();
		this.repaint();
	}

	public boolean getShowTicks() {
		return JCS.getPaintTicks();
	}

	@Override
	public double getVal() {
		return xMIN + JCS.getValue() * (xMAX - xMIN) / VMAX;
	}

	class MyJSlider extends JSlider implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int oldvalue;

		MyJSlider(final int min, final int max, final int ticks, final int val) {
			super(min, max, val);
			oldvalue = val;
			this.setOpaque(false);
			this.setFocusable(false);

			// this.setMajorTickSpacing((max-min)/5);
			this.setMinorTickSpacing(ticks);
			this.setPaintTicks(true);
			this.setSnapToTicks(true);
			this.setOpaque(false);

			this.addChangeListener(this);
		}

		@Override
		public int getValue() {

			if (isEditMode()) {
				return oldvalue;
			}
			oldvalue = super.getValue();
			return oldvalue;

		}

		public void stateChanged(final ChangeEvent e) {
			try {
                            if (!hidden()){
				double val = getValue();
				val = xMIN + ((xMAX - xMIN) / VMAX) * val;
				if (getSnapToTicks()) {
					// snap to good value :
					final int i = (int) Math.round((val - xMIN) / xTICKS);
					val = xMIN + xTICKS * i;
					// eliminate side effects :
					final double ex = Math.pow(10, 5 - Math.round(Math
							.log10(val)));
					val = Math.round(val * ex) / ex;
					if (Double.isNaN(val)) {
						val = 0;
					}
				}

				setVal(val);
                            }
			} catch (final Exception ex) {
			}
		}
	}

	@Override
	public void PrintXmlTags(final XmlWriter xml) {
		xml.startTagStart("CTRLslider");
		super.PrintXmlTags(xml);
		xml.printArg("min", "" + xMIN);
		xml.printArg("max", "" + xMAX);
		xml.printArg("T", "" + xTICKS);
		xml.printArg("fixT", "" + JCS.getSnapToTicks());
		xml.printArg("showT", "" + JCS.getPaintTicks());
		xml.finishTagNewLine();
	}
}
