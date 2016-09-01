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

import java.awt.EventQueue;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

public class SliderSnap extends BasicSliderUI {
	/**
	 * The UI class implements the current slider Look and Feel.
	 */
	private static Class sliderClass;
	private static Method xForVal, yForVal;
	private static ReinitListener reinitListener = new ReinitListener();

	public SliderSnap() {
		super(null);
	}

	/**
	 * Returns the UI as normal, but intercepts the call, so a listener can be
	 * attached.
	 */
	public static ComponentUI createUI(final JComponent c) {
		if (c == null || sliderClass == null)
			return null;
		final UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		try {
			Method m = (Method) defaults.get(sliderClass);
			if (m == null) {
				m = sliderClass.getMethod("createUI",
						new Class[] { JComponent.class });
				defaults.put(sliderClass, m);
			}
			final ComponentUI uiObject = (ComponentUI) m.invoke(null,
					new Object[] { c });
			if (uiObject instanceof BasicSliderUI)
				c.addHierarchyListener(new MouseAttacher());
			return uiObject;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
		// check we don't initialise twice
		if (sliderClass != null)
			return;
		final Init init = new Init();
		if (EventQueue.isDispatchThread()) {
			init.run();
		} else {
			// This code must run on the EDT for data visibility
			try {
				EventQueue.invokeAndWait(init);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Listeners for when the JSlider becomes visible then attaches the mouse
	 * listeners, then removes itself.
	 */
	private static class MouseAttacher implements HierarchyListener {
		public void hierarchyChanged(final HierarchyEvent evt) {
			final long flags = evt.getChangeFlags();
			if ((flags & HierarchyEvent.DISPLAYABILITY_CHANGED) > 0
					&& evt.getComponent() instanceof JSlider) {
				final JSlider c = (JSlider) evt.getComponent();
				c.removeHierarchyListener(this);
				attachTo(c);
			}
		}
	}

	/**
	 * Listens for Look and Feel changes and re-initialises the class.
	 */
	private static class ReinitListener implements PropertyChangeListener {
		public void propertyChange(final PropertyChangeEvent evt) {
			if ("lookAndFeel".equals(evt.getPropertyName())) {
				// The look and feel was changed so we need to re-insert
				// Our hook into the new UIDefaults map
				sliderClass = null;
				xForVal = yForVal = null;
				UIManager.removePropertyChangeListener(reinitListener);
				init();
			}
		}
	}

	/**
	 * Initialises the reflective methods and adjusts the current Look and Feel.
	 */
	private static class Init implements Runnable {
		public void run() {
			try {
				final UIDefaults defaults = UIManager.getLookAndFeelDefaults();
				sliderClass = defaults.getUIClass("SliderUI");
				// Set up two reflective method calls
				xForVal = BasicSliderUI.class.getDeclaredMethod(
						"xPositionForValue", new Class[] { int.class });
				yForVal = BasicSliderUI.class.getDeclaredMethod(
						"yPositionForValue", new Class[] { int.class });
				// Allow us access to the methods
				xForVal.setAccessible(true);
				yForVal.setAccessible(true);
				// Replace UI class with ourselves
				defaults.put("SliderUI", SliderSnap.class.getName());
				UIManager.addPropertyChangeListener(reinitListener);
			} catch (final Exception e) {
				sliderClass = null;
				xForVal = yForVal = null;
			}
		}
	}

	/**
	 * Called to attach mouse listeners to the JSlider.
	 */
	private static void attachTo(final JSlider c) {
		final MouseMotionListener[] listeners = c.getMouseMotionListeners();
		for (final MouseMotionListener m : listeners) {
			if (m instanceof TrackListener) {
				c.removeMouseMotionListener(m); // remove original
				final SnapListener listen = new SnapListener(m,
						(BasicSliderUI) c.getUI(), c);
				c.addMouseMotionListener(listen);
				c.addMouseListener(listen);
				c.addPropertyChangeListener("UI", listen);
			}
		}
	}

	private static class SnapListener extends MouseInputAdapter implements
	PropertyChangeListener {
		private final MouseMotionListener delegate;
		/**
		 * Original Look and Feel implementation
		 */
		private final BasicSliderUI ui;
		/**
		 * Our slider
		 */
		private final JSlider slider;
		/**
		 * Offset of mouse click from centre of slider thumb
		 */
		private int offset;

		public SnapListener(final MouseMotionListener delegate,
				final BasicSliderUI ui, final JSlider slider) {
			this.delegate = delegate;
			this.ui = ui;
			this.slider = slider;
		}

		/**
		 * UI can change at any point, so we need to listen for these events.
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			if ("UI".equals(evt.getPropertyName())) {
				// Remove old listeners and create new ones
				slider.removeMouseMotionListener(this);
				slider.removeMouseListener(this);
				slider.removePropertyChangeListener("UI", this);
				attachTo(slider);
			}
		}

		/**
		 * Implements the actual "snap while dragging" behaviour. If snap to
		 * ticks is enabled on this slider, then the location for the nearest
		 * tick/label is calculated and the click location is translated before
		 * being passed to the delegate.
		 */
		@Override
		public void mouseDragged(final MouseEvent evt) {
			if (slider.getSnapToTicks()) { // if we are set to snap
				final int pos = getLocationForValue(getSnappedValue(evt));
				// if above call fails and returns -1, take no action
				if (pos > -1) {
					if (slider.getOrientation() == JSlider.HORIZONTAL)
						evt.translatePoint(pos - evt.getX() + offset, 0);
					else
						evt.translatePoint(0, pos - evt.getY() + offset);
				}
			}
			delegate.mouseDragged(evt);
		}

		/**
		 * When the slider is clicked we need to record the offset from thumb
		 * center.
		 */
		@Override
		public void mousePressed(final MouseEvent evt) {

			final int pos = (slider.getOrientation() == JSlider.HORIZONTAL) ? evt
					.getX()
					: evt.getY();
					final int loc = getLocationForValue(getSnappedValue(evt));
					this.offset = (loc < 0) ? 0 : pos - loc;
		}

		/* Pass straight to delegate. */
		@Override
		public void mouseMoved(final MouseEvent evt) {
			delegate.mouseMoved(evt);
		}

		/**
		 * Calculates the nearest snapable value given a MouseEvent. Code
		 * adapted from BasicSliderUI.
		 */
		public int getSnappedValue(final MouseEvent evt) {
			final int value = slider.getOrientation() == JSlider.HORIZONTAL ? ui
					.valueForXPosition(evt.getX())
					: ui.valueForYPosition(evt.getY());
					// Now calculate if we should adjust the value
					int snappedValue = value;
					int tickSpacing = 0;
					final int majorTickSpacing = slider.getMajorTickSpacing();
					final int minorTickSpacing = slider.getMinorTickSpacing();
					if (minorTickSpacing > 0)
						tickSpacing = minorTickSpacing;
					else if (majorTickSpacing > 0)
						tickSpacing = majorTickSpacing;
					// If it's not on a tick, change the value
					if (tickSpacing != 0) {
						if ((value - slider.getMinimum()) % tickSpacing != 0) {
							final float temp = (float) (value - slider.getMinimum())
							/ (float) tickSpacing;
							snappedValue = slider.getMinimum()
							+ (Math.round(temp) * tickSpacing);
						}
					}
					return snappedValue;
		}

		/**
		 * Provides the x or y co-ordinate for a slider value, depending on
		 * orientation.
		 */
		public int getLocationForValue(final int value) {
			try {
				// Reflectively call slider ui code
				final Method m = slider.getOrientation() == JSlider.HORIZONTAL ? xForVal
						: yForVal;
				final Integer result = (Integer) m.invoke(ui,
						new Object[] { new Integer(value) });
				return result.intValue();
			} catch (final InvocationTargetException e) {
				return -1;
			} catch (final IllegalAccessException e) {
				return -1;
			}
		}
	}
}