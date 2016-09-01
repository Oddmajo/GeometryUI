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
 
 
 package eric.macros;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class CTree extends JTree implements DragSourceListener,
DragGestureListener, Autoscroll, TreeSelectionListener, MouseListener {
	// Constants...

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields...
	private TreePath _pathSource; // The path being dragged
	private BufferedImage _imgGhost; // The 'drag image'
	private final Point _ptOffset = new Point(); // Where, in the drag image,
	// the mouse was clicked
	private boolean dropped = false;
	public MacrosList JML;
	public NodePopupMenu nodepopup;

	// Constructors...
	public CTree(final MacrosList jml) // Use the default JTree constructor so
	// that we get a sample TreeModel built
	// for us
	{
		JML = jml;
		putClientProperty("JTree.lineStyle", "none");

		// this.addTreeSelectionListener(this);
		// this.getModel().addTreeModelListener(this);

		this.addMouseListener(this);
		// Make this JTree a drag source
		final DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_COPY_OR_MOVE, this);

		// Also, make this JTree a drag target
		final DropTarget dropTarget = new DropTarget(this,
				new CDropTargetListener());
		dropTarget.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);

		nodepopup = new NodePopupMenu(this);

	}



	private DefaultMutableTreeNode cloneAll(final DefaultMutableTreeNode Ndfrom) {
		final DefaultMutableTreeNode Ndto = (DefaultMutableTreeNode) Ndfrom
		.clone();
		if (!(Ndfrom.isLeaf())) {
			for (int i = 0; i < Ndfrom.getChildCount(); i++) {
				final DefaultMutableTreeNode mynode = cloneAll((DefaultMutableTreeNode) Ndfrom
						.getChildAt(i));
				Ndto.add(mynode);
			}
		}
		return Ndto;
	}

	// Interface: DragGestureListener
	public void dragGestureRecognized(final DragGestureEvent e) {

		final Point ptDragOrigin = e.getDragOrigin();
		final TreePath path = getPathForLocation(ptDragOrigin.x, ptDragOrigin.y);
		if (path == null)
			return;
		if (isRootPath(path))
			return; // Ignore user trying to drag the root node

		// Work out the offset of the drag point from the TreePath bounding
		// rectangle origin
		final Rectangle raPath = getPathBounds(path);
		_ptOffset.setLocation(ptDragOrigin.x - raPath.x, ptDragOrigin.y
				- raPath.y);

		// Get the cell renderer (which is a JLabel) for the path being dragged
		final JLabel lbl = (JLabel) getCellRenderer()
		.getTreeCellRendererComponent(this, // tree
				path.getLastPathComponent(), // value
				false, // isSelected (dont want a colored background)
				isExpanded(path), // isExpanded
				getModel().isLeaf(path.getLastPathComponent()), // isLeaf
				0, // row (not important for rendering)
				false // hasFocus (dont want a focus rectangle)
		);
		lbl.setSize((int) raPath.getWidth(), (int) raPath.getHeight()); // <--
		// The
		// layout
		// manager
		// would
		// normally
		// do
		// this

		// Get a buffered image of the selection for dragging a ghost image
		_imgGhost = new BufferedImage((int) raPath.getWidth(), (int) raPath
				.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		final Graphics2D g2 = _imgGhost.createGraphics();

		// Ask the cell renderer to paint itself into the BufferedImage
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f)); // Make
		// the
		// image
		// ghostlike
		lbl.paint(g2);

		// Now paint a gradient UNDER the ghosted JLabel text (but not under the
		// icon if any)
		// Note: this will need tweaking if your icon is not positioned to the
		// left of the text
		final Icon icon = lbl.getIcon();
		final int nStartOfText = (icon == null) ? 0 : icon.getIconWidth()
				+ lbl.getIconTextGap();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER,
				0.5f)); // Make the gradient ghostlike
		g2.setPaint(new GradientPaint(nStartOfText, 0,
				SystemColor.controlShadow, getWidth(), 0, new Color(255, 255,
						255, 0)));
		g2.fillRect(nStartOfText, 0, getWidth(), _imgGhost.getHeight());

		g2.dispose();

		setSelectionPath(path); // Select this path in the tree

		// System.out.println("DRAGGING: "+path.getLastPathComponent());

		// DefaultMutableTreeNode
		// mynode=(DefaultMutableTreeNode)path.getLastPathComponent();
		// Transferable transferable = (Transferable) mynode.getUserObject();
		// Wrap the path being transferred into a Transferable object
		final Transferable transferable = new CTransferableTreePath(path);

		// Remember the path being dragged (because if it is being moved, we
		// will have to delete it later)
		_pathSource = path;

		// We pass our drag image just in case it IS supported by the platform

		try {
			this.setEditable(false);
			// e.startDrag(new Cursor(Cursor.HAND_CURSOR),transferable,this);
			e.startDrag(null, _imgGhost, new Point(5, 5), transferable, this);

		} catch (final InvalidDnDOperationException dnde) {
			// JOptionPane.showMessageDialog(null, "coucou2");
		}

		// e.startDrag(null, _imgGhost, new Point(5,5), transferable, this);

	}

	// Interface: DragSourceListener
	public void dragEnter(final DragSourceDragEvent e) {
	}

	public void dragOver(final DragSourceDragEvent e) {
	}

	public void dragExit(final DragSourceEvent e) {
	}

	public void dropActionChanged(final DragSourceDragEvent e) {
	}

	public void dragDropEnd(final DragSourceDropEvent e) {
		if (e.getDropSuccess()) {
			final int nAction = e.getDropAction();
			if (nAction == DnDConstants.ACTION_MOVE) { // The dragged item
				// (_pathSource) has
				// been inserted at the
				// target selected by
				// the user.
				// Now it is time to delete it from its original location.
				// System.out.println("REMOVING: " +
				// _pathSource.getLastPathComponent());

				if (dropped) {
					final DefaultTreeModel model = (DefaultTreeModel) getModel();
					final MutableTreeNode node = (MutableTreeNode) _pathSource
					.getLastPathComponent();
					MutableTreeNode parent = (MutableTreeNode) node.getParent();
					model.removeNodeFromParent(node);
					while (parent.isLeaf()) {
						final MutableTreeNode grandparent = (MutableTreeNode) parent
						.getParent();
						model.removeNodeFromParent(parent);
						parent = grandparent;
					}

					dropped = false;
				}

				_pathSource = null;
			}
		}
	}

	// DropTargetListener interface object...
	class CDropTargetListener implements DropTargetListener {
		// Fields...
		private TreePath _pathLast = null;
		private final Rectangle2D _raCueLine = new Rectangle2D.Float();
		private Rectangle2D _raGhost = new Rectangle2D.Float();
		private final Color _colorCueLine;
		private Point _ptLast = new Point();
		private final Timer _timerHover;
		private int _nLeftRight = 0; // Cumulative left/right mouse movement
		// Constructor...

		public CDropTargetListener() {
			_colorCueLine = new Color(SystemColor.controlShadow.getRed(),
					SystemColor.controlShadow.getGreen(),
					SystemColor.controlShadow.getBlue(), 64);

			// Set up a hover timer, so that a node will be automatically
			// expanded or collapsed
			// if the user lingers on it for more than a short time
			_timerHover = new Timer(1000, new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					_nLeftRight = 0; // Reset left/right movement trend
					if (isRootPath(_pathLast))
						return; // Do nothing if we are hovering over the root
					// node
					if (isExpanded(_pathLast))
						collapsePath(_pathLast);
					else
						expandPath(_pathLast);
				}
			});
			_timerHover.setRepeats(false); // Set timer to one-shot mode
		}

		// DropTargetListener interface
		public void dragEnter(final DropTargetDragEvent e) {
			if (!isDragAcceptable(e))
				e.rejectDrag();
			else
				e.acceptDrag(e.getDropAction());
		}

		public void dragExit(final DropTargetEvent e) {
			if (!DragSource.isDragImageSupported()) {
				repaint(_raGhost.getBounds());
			}
		}

		/**
		 * This is where the ghost image is drawn
		 */
		public void dragOver(final DropTargetDragEvent e) {
			// Even if the mouse is not moving, this method is still invoked 10
			// times per second
			final Point pt = e.getLocation();
			if (pt.equals(_ptLast))
				return;

			// Try to determine whether the user is flicking the cursor right or
			// left
			final int nDeltaLeftRight = pt.x - _ptLast.x;
			if ((_nLeftRight > 0 && nDeltaLeftRight < 0)
					|| (_nLeftRight < 0 && nDeltaLeftRight > 0))
				_nLeftRight = 0;
			_nLeftRight += nDeltaLeftRight;

			_ptLast = pt;

			final Graphics2D g2 = (Graphics2D) getGraphics();

			// If a drag image is not supported by the platform, then draw my
			// own drag image
			if (!DragSource.isDragImageSupported()) {
				paintImmediately(_raGhost.getBounds()); // Rub out the last
				// ghost image and cue
				// line
				// And remember where we are about to draw the new ghost image
				_raGhost.setRect(pt.x - _ptOffset.x, pt.y - _ptOffset.y,
						_imgGhost.getWidth(), _imgGhost.getHeight());
				g2.drawImage(_imgGhost, AffineTransform.getTranslateInstance(
						_raGhost.getX(), _raGhost.getY()), null);
			} else
				// Just rub out the last cue line
				paintImmediately(_raCueLine.getBounds());

			final TreePath path = getClosestPathForLocation(pt.x, pt.y);
			if (!(path == _pathLast)) {
				_nLeftRight = 0; // We've moved up or down, so reset left/right
				// movement trend
				_pathLast = path;
				_timerHover.restart();
			}

			// In any case draw (over the ghost image if necessary) a cue line
			// indicating where a drop will occur
			final Rectangle raPath = getPathBounds(path);
			_raCueLine.setRect(0, raPath.y + (int) raPath.getHeight(),
					getWidth(), 2);

			g2.setColor(_colorCueLine);
			g2.fill(_raCueLine);

			// Now superimpose the left/right movement indicator if necessary
			if (_nLeftRight > 20) {
			} else if (_nLeftRight < -20) {
			} else {
			}

			// And include the cue line in the area to be rubbed out next time
			_raGhost = _raGhost.createUnion(_raCueLine);

			/*
			 * // Do this if you want to prohibit dropping onto the drag source
			 * if (path.equals(_pathSource)) e.rejectDrag(); else
			 * e.acceptDrag(e.getDropAction());
			 */
		}

		public void dropActionChanged(final DropTargetDragEvent e) {
			if (!isDragAcceptable(e))
				e.rejectDrag();
			else
				e.acceptDrag(e.getDropAction());
		}

		public void drop(final DropTargetDropEvent e) {
			_timerHover.stop(); // Prevent hover timer from doing an unwanted
			// expandPath or collapsePath

			if (!isDropAcceptable(e)) {
				e.rejectDrop();
				return;
			}

			e.acceptDrop(e.getDropAction());

			final Transferable transferable = e.getTransferable();

			final DataFlavor[] flavors = transferable.getTransferDataFlavors();
			for (final DataFlavor flavor : flavors) {
				if (flavor
						.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
					try {
						final Point pt = e.getLocation();
						final TreePath pathTarget = getClosestPathForLocation(
								pt.x, pt.y);
						final TreePath pathSource = (TreePath) transferable
						.getTransferData(flavor);

						// System.out.println("DROPPING: "+pathSource.getLastPathComponent());
						// TreeModel model = getModel();

						final DefaultTreeModel model = (DefaultTreeModel) getModel();
						final TreePath pathNewChild = null;

						final DefaultMutableTreeNode nodeorg = (DefaultMutableTreeNode) pathSource
						.getLastPathComponent();
						DefaultMutableTreeNode nodeto = (DefaultMutableTreeNode) pathTarget
						.getLastPathComponent();

						// avoid a folder to be drag inside its own hierarchy
						dropped = true;
						if (!(nodeorg.isLeaf())) {
							DefaultMutableTreeNode parent = nodeto;
							while (parent != null) {
								if (parent.equals(nodeorg)) {
									dropped = false;
									JML.repaint();
									return;
								}
								parent = (DefaultMutableTreeNode) parent
								.getParent();
							}
						}

						if (dropped) {
							DefaultMutableTreeNode nodeparentto = (nodeto
									.isLeaf()) ? (DefaultMutableTreeNode) pathTarget
											.getParentPath().getLastPathComponent()
											: nodeto;
											final TreePath tp = new TreePath(nodeparentto
													.getPath());
											if (!(isExpanded(tp))) {
												nodeto = nodeparentto;
												nodeparentto = (DefaultMutableTreeNode) nodeto
												.getParent();
											}
											int h = 0;
											for (h = 0; h < nodeparentto.getChildCount(); h++) {
												if (nodeparentto.getChildAt(h).equals(nodeto))
													break;
											}
											if (h == nodeparentto.getChildCount())
												h = -1;
											model.insertNodeInto(cloneAll(nodeorg),
													nodeparentto, h + 1);
						}

						// model.insertNodeInto()
						// .
						// .. Add your code here to ask your TreeModel to copy
						// the node and act on the mouse gestures...
						// .

						// For example:

						// If pathTarget is an expanded BRANCH,
						// then insert source UNDER it (before the first child
						// if any)
						// If pathTarget is a collapsed BRANCH (or a LEAF),
						// then insert source AFTER it
						// Note: a leaf node is always marked as collapsed
						// You ask the model to do the copying...
						// ...and you supply the copyNode method in the model as
						// well of course.
						// if (_nShift == 0)
						// pathNewChild = model.copyNode(pathSource, pathTarget,
						// isExpanded(pathTarget));
						// else if (_nShift > 0) // The mouse is being flicked
						// to the right (so move the node right)
						// pathNewChild = model.copyNodeRight(pathSource,
						// pathTarget);
						// else // The mouse is being flicked to the left (so
						// move the node left)
						// pathNewChild = model.copyNodeLeft(pathSource);

						if (pathNewChild != null)
							setSelectionPath(pathNewChild); // Mark this as the
						// selected path in
						// the tree
						break; // No need to check remaining flavors
					} catch (final UnsupportedFlavorException ufe) {
						// System.out.println(ufe);
						e.dropComplete(false);
						return;
					} catch (final IOException ioe) {
						// System.out.println(ioe);
						e.dropComplete(false);
						return;
					}
				}
			}

			e.dropComplete(true);
		}

		// Helpers...
		public boolean isDragAcceptable(final DropTargetDragEvent e) {
			// Only accept COPY or MOVE gestures (ie LINK is not supported)
			if ((e.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) == 0)
				return false;

			// Only accept this particular flavor
			if (!e.isDataFlavorSupported(CTransferableTreePath.TREEPATH_FLAVOR))
				return false;

			/*
			 * // Do this if you want to prohibit dropping onto the drag
			 * source... Point pt = e.getLocation(); TreePath path =
			 * getClosestPathForLocation(pt.x, pt.y); if
			 * (path.equals(_pathSource)) return false;
			 */

			/*
			 * // Do this if you want to select the best flavor on offer...
			 * DataFlavor[] flavors = e.getCurrentDataFlavors(); for (int i = 0;
			 * i < flavors.length; i++ ) { DataFlavor flavor = flavors[i]; if
			 * (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType))
			 * return true; }
			 */
			return true;
		}

		public boolean isDropAcceptable(final DropTargetDropEvent e) {
			// Only accept COPY or MOVE gestures (ie LINK is not supported)
			if ((e.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) == 0)
				return false;

			// Only accept this particular flavor
			if (!e.isDataFlavorSupported(CTransferableTreePath.TREEPATH_FLAVOR))
				return false;

			/*
			 * // Do this if you want to prohibit dropping onto the drag
			 * source... Point pt = e.getLocation(); TreePath path =
			 * getClosestPathForLocation(pt.x, pt.y); if
			 * (path.equals(_pathSource)) return false;
			 */

			/*
			 * // Do this if you want to select the best flavor on offer...
			 * DataFlavor[] flavors = e.getCurrentDataFlavors(); for (int i = 0;
			 * i < flavors.length; i++ ) { DataFlavor flavor = flavors[i]; if
			 * (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType))
			 * return true; }
			 */
			return true;
		}

	}

	// Autoscroll Interface...
	// The following code was borrowed from the book:
	// Java Swing
	// By Robert Eckstein, Marc Loy & Dave Wood
	// Paperback - 1221 pages 1 Ed edition (September 1998)
	// O'Reilly & Associates; ISBN: 156592455X
	//
	// The relevant chapter of which can be found at:
	// http://www.oreilly.com/catalog/jswing/chapter/dnd.beta.pdf

	private static final int AUTOSCROLL_MARGIN = 12;

	// Ok, weíve been told to scroll because the mouse cursor is in our
	// scroll zone.
	public void autoscroll(final Point pt) {
		// Figure out which row weíre on.
		int nRow = getRowForLocation(pt.x, pt.y);

		// If we are not on a row then ignore this autoscroll request
		if (nRow < 0)
			return;

		final Rectangle raOuter = getBounds();
		// Now decide if the row is at the top of the screen or at the
		// bottom. We do this to make the previous row (or the next
		// row) visible as appropriate. If weíre at the absolute top or
		// bottom, just return the first or last row respectively.

		nRow = (pt.y + raOuter.y <= AUTOSCROLL_MARGIN) // Is row at top of
		// screen?
		? (nRow <= 0 ? 0 : nRow - 1) // Yes, scroll up one row
				: (nRow < getRowCount() - 1 ? nRow + 1 : nRow); // No, scroll
		// down one row

		scrollRowToVisible(nRow);
	}

	// Calculate the insets for the *JTREE*, not the viewport
	// the tree is in. This makes it a bit messy.
	public Insets getAutoscrollInsets() {
		final Rectangle raOuter = getBounds();
		final Rectangle raInner = getParent().getBounds();
		return new Insets(raInner.y - raOuter.y + AUTOSCROLL_MARGIN, raInner.x
				- raOuter.x + AUTOSCROLL_MARGIN, raOuter.height
				- raInner.height - raInner.y + raOuter.y + AUTOSCROLL_MARGIN,
				raOuter.width - raInner.width - raInner.x + raOuter.x
				+ AUTOSCROLL_MARGIN);
	}

	/*
	 * // Use this method if you want to see the boundaries of the // autoscroll
	 * active region. Toss it out, otherwise. public void
	 * paintComponent(Graphics g) { super.paintComponent(g); Rectangle raOuter =
	 * getBounds(); Rectangle raInner = getParent().getBounds();
	 * g.setColor(Color.red); g.drawRect(-raOuter.x + 12, -raOuter.y + 12,
	 * raInner.width - 24, raInner.height - 24); }
	 */

	private boolean isRootPath(final TreePath path) {
		return isRootVisible() && getRowForPath(path) == 0;
	}

	// Ready to go : execute macro on click
	public void valueChanged(final TreeSelectionEvent e) {
		// JDefaultMutableTreeNode node =
		// (JDefaultMutableTreeNode)this.getLastSelectedPathComponent();
		// if (node.isLeaf()) {
		// node.runZmacro();
		// };

	}

	// MouseListener interface
	public void mouseClicked(final MouseEvent e) {
		nodepopup.handleMouseClick(e);
	}

	public void mousePressed(final MouseEvent e) {
		nodepopup.actualiseproperties();
		nodepopup.handlePopup(e);
	}

	public void mouseReleased(final MouseEvent e) {
		nodepopup.handlePopup(e);
	}

	public void mouseEntered(final MouseEvent e) {
		// ShowToolTip(e);
	}

	public void mouseExited(final MouseEvent e) {
		// HideToolTip();
	}


}
