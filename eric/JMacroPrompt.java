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
 
 
 package eric;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import eric.bar.JProperties;
import javax.swing.JPanel;
import rene.gui.Global;

/**
 * 
 * @author erichake
 */
public class JMacroPrompt extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// boolean Valid=false;
	JTextField T = new JTextField();
	// String S="";
	String InitValue = "";
	ConstructionObject OC;
	ZirkelCanvas ZC;
	int WindowEventCount = 0;
	Color TitleColor = new Color(200, 200, 200);
	Color TxtColor = new Color(250, 250, 250);
	Color DlogColor = new Color(90, 90, 90);

	private void fixsize(final JComponent cp, final int w, final int h) {
		final Dimension d = new Dimension(w, h);
		cp.setMaximumSize(d);
		cp.setMinimumSize(d);
		cp.setPreferredSize(d);
		cp.setSize(d);
	}

	private JEricPanel margin(final int w) {
		final JEricPanel mypan = new JEricPanel();
		fixsize(mypan, w, 1);
		mypan.setOpaque(false);
		mypan.setFocusable(false);
		return mypan;
	}

	private boolean isValidExpression(final String myexp) {
		boolean bool = true;
		try {
			final Expression exp = new Expression(myexp, OC.getConstruction(),
					OC);
			if (!(exp.isValid()))
				bool = false;
		} catch (final Exception ex) {
			bool = false;
		}
		return bool;
	}

	public JMacroPrompt(final Frame f, final ZirkelCanvas zc,
			final String oName, final ConstructionObject oc) {
		super(f, true);
		OC = oc;
		ZC = zc;

		try {
			InitValue = String.valueOf(OC.getValue());
		} catch (final Exception ex) {
		}
		
		final JPanel content = (JPanel) this.getContentPane();
		this.setUndecorated(true);
		content.setLayout(new javax.swing.BoxLayout(content,
				javax.swing.BoxLayout.Y_AXIS));
		content.setBackground(DlogColor);
		// this.setBackground(new Color(0.2f, 0.2f, 0.2f, 0.5f));
		// content.setOpaque(false);

		final JLabel p1 = new JLabel(Global.name("macro.prompt.prompt") + " :");
		p1.setHorizontalAlignment(SwingConstants.CENTER);
		fixsize(p1, 300, 15);
		p1.setAlignmentX(0.5f);
		p1.setOpaque(false);
		p1.setFont(new java.awt.Font(Global.GlobalFont, 1, 12));
		p1.setForeground(TitleColor);

		final JEricPanel p2 = new JEricPanel();
		p2
		.setLayout(new javax.swing.BoxLayout(p2,
				javax.swing.BoxLayout.X_AXIS));
		final JLabel p21 = new JLabel(oName);
		final JButton okbtn = new JButton();
		okbtn.setIcon(new ImageIcon(getClass().getResource(
				"/eric/GUI/icons/palette/Mvalid.png")));
		okbtn.setBorder(BorderFactory.createEmptyBorder());
		okbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				doclose();
			}
		});
		okbtn.setContentAreaFilled(false);
		final JButton cancelbtn = new JButton();
		cancelbtn.setIcon(new ImageIcon(getClass().getResource(
		"/eric/GUI/icons/palette/Mcancel.png")));
		cancelbtn.setBorder(BorderFactory.createEmptyBorder());
		cancelbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				T.setText(InitValue);
				try {
					OC.setExpression(T.getText(), OC.getConstruction());
					OC.setSlider(false);
				} catch (final Exception ex) {
				}
				ZC.recompute();
				ZC.validate();
				ZC.repaint();
				T.requestFocus();
				T.selectAll();
			}
		});
		cancelbtn.setContentAreaFilled(false);
		fixsize(p21, 120, 18);
		fixsize(T, 118, 18);
		p2.add(margin(5));
		p2.add(p21);
		p2.add(margin(10));
		p2.add(T);
		p2.add(margin(5));
		p2.add(cancelbtn);
		p2.add(margin(5));
		p2.add(okbtn);
		p2.add(margin(5));

		fixsize(p2, 300, 30);
		p2.setAlignmentX(0.5f);
		p2.setOpaque(false);
		p21.setOpaque(false);
		p21.setFont(new java.awt.Font(Global.GlobalFont, 1, 12));
		T.setFont(new java.awt.Font(Global.GlobalFont, 1, 12));
		p21.setForeground(TxtColor);
		T.setText(JProperties.Point_To_Comma(InitValue, OC.getConstruction(),
				true));

		content.add(p1);
		content.add(p2);

		T.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)
						|| (e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
					doclose();
				}
				
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				final String e1 = JProperties.Comma_To_Point(T.getText(), OC
						.getConstruction(), true);
				final String myexp = (isValidExpression(e1)) ? e1 : "invalid";
				try {
					OC.setExpression(myexp, OC.getConstruction());
					OC.setSlider(false);
				} catch (final Exception ex) {
				}
				ZC.recompute();
				ZC.validate();
				ZC.repaint();
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent e) {
				T.requestFocus();
				T.selectAll();
			}
		});

		pack();
		final Point pt = zc.getLocationOnScreen();
		pt.x += zc.getSize().width / 2 - this.getSize().width / 2;
		pt.y += zc.getSize().height - this.getSize().height;
		this.setLocation(pt);
		this.setVisible(true);
	}

	public void doclose() {
		final String e1 = JProperties.Comma_To_Point(T.getText(), OC
				.getConstruction(), true);
		final String myexp = (isValidExpression(e1)) ? e1 : InitValue;

		try {
			OC.setExpression(myexp, OC.getConstruction());
			OC.setSlider(false);
		} catch (final Exception ex) {
		}
		ZC.recompute();
		ZC.validate();
		ZC.repaint();
		setVisible(false);
		dispose();
	}

}
