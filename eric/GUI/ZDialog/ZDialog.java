/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import eric.GUI.windowComponent;
import eric.JEricPanel;
import eric.JZirkelCanvas;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake, lightly modified by PM Mazat
 */
public class ZDialog extends JEricPanel {

    private ZDialog me;
    //private RoundRectangle2D roundRect;
    protected RoundRectangle2D roundRect;
    //private int cx=0, cy=0, cw=0;
    protected int cx=0, cy=0, cw=0;
    private Point origin, winloc;
    public int D_X, D_Y, D_WIDTH, D_HEIGHT;      // Dialog width and height
    private boolean withTitle=false;
    private ZDialogTitle title;
    private boolean withCloseBox=false;
    private ZDialogCloseBox closebox;
    //private boolean boxEnter=false;
    protected boolean boxEnter=false;
    protected int THEIGHT=22;
    protected int MARGINTOP1=THEIGHT+8;           // Margin top for first component line
    protected int MARGINTOP2=MARGINTOP1+26;      // Margin top for second component line
    protected int MARGINTOP3=MARGINTOP2+26;      // Margin top for third component line
    protected int MARGINTOP4=MARGINTOP3+26;      // Margin top for forth component line
    protected int MARGINTOP5=MARGINTOP4+26;      // Margin top for forth component line
    protected int ARCCORNER=20;	    // Round corner size
    protected int LWIDTH=150;     // Label width (for textfields)
    protected int BWIDTH=90;      // Button width
    protected int CWIDTH=350;     // Component width
    protected int CHEIGHT=19;     // Component height
    protected int MARGINW=12;     // Margin left and right
    protected int NOT_EXIT = 28;    //at least NOT_EXIT pixels will be visible

    @Override
    public void paint(Graphics g) {
        if (JZirkelCanvas.isPaintCalled()) {

            Graphics2D g2d=windowComponent.getGraphics2D(g);

            if (isTitleVisible()) {
                // draw the title background :
                g2d.setColor(ZTools.backTitleColor);
                g2d.setClip(0, 0, D_WIDTH, THEIGHT);
                g2d.fill(roundRect);
            }

            if (isCloseBoxVisible()) {
                // draw the close box :
                g2d.setColor(ZTools.TitleTextColor);
                if (boxEnter) {
                    g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                } else {
                    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                }
                g2d.drawOval(cx, cy, cw, cw);
                int dx=(int) (cw*(1-Math.cos(Math.PI/4)));
                g2d.drawLine(cx+dx, cy+dx, cx+cw-dx, cy+cw-dx);
                g2d.drawLine(cx+dx, cy+cw-dx, cx+cw-dx, cy+dx);
            }

            // draw the content background :
            g2d.setColor(ZTools.backMainColor);
            g2d.setClip(0, THEIGHT, D_WIDTH, D_HEIGHT);
            g2d.fill(roundRect);

            g2d.setClip(0, 0, D_WIDTH, D_HEIGHT);

            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g2d.draw(roundRect);
            g2d.setStroke(new BasicStroke(1f));

            paintChildren(g);
        }
    }

    public boolean isCloseBoxVisible() {
        return withCloseBox;
    }

    public void setCloseBoxVisible(boolean b) {
        withCloseBox=b;
    }

    public boolean isTitleVisible() {
        return withTitle;
    }

    public void setTitleVisible(boolean b) {
        withTitle=b;
    }

    public ZDialog(String ttle, int x, int y, int w, int h, boolean withtitle, boolean withclose) {
        me=this;
        add(closebox=new ZDialogCloseBox());
        add(title=new ZDialogTitle(ttle));
        withTitle=withtitle;
        withCloseBox=withclose;
        if (!withtitle) {
            MARGINTOP1=MARGINTOP1-THEIGHT;
            MARGINTOP2=MARGINTOP2-THEIGHT;
            MARGINTOP3=MARGINTOP3-THEIGHT;
            MARGINTOP4=MARGINTOP4-THEIGHT;
            THEIGHT=0;
        }
        D_X=x;
        D_Y=y;
        D_WIDTH=w;
        D_HEIGHT=h;
        roundRect=new RoundRectangle2D.Double(2, 2, D_WIDTH-4, D_HEIGHT-4, ARCCORNER, ARCCORNER);

        setLayout(null);
        setOpaque(false);
    }

    public void init() {
        setBounds(D_X, D_Y, D_WIDTH, D_HEIGHT);
        title.setBounds(0, 0, D_WIDTH, THEIGHT);
        cw=THEIGHT-8;
        cx=D_WIDTH-cw-MARGINW;
        cy=4;
        closebox.setBounds(cx, cy, cw, cw);
        fixComponents();
    }

    public void fixComponents() {
    }

    public void doClose() {
    }

    public class ZDialogCloseBox extends JEricPanel {

	@Override
        public void paint(Graphics g){
            // CloseBox is painted in the ZDialog paint method,
            // because it needs backgound in order to render
            // the antialisasing of strokes.
        }

        public ZDialogCloseBox() {
            setOpaque(false);
            addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent e) {
                    if (isCloseBoxVisible()) {
                        doClose();
                    }
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                    if (isCloseBoxVisible()) {
                        boxEnter=true;
                        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
                        if (zc!=null) {
                            zc.repaint();
                        }
                    }
                }

		@Override
                public void mouseExited(final MouseEvent e) {
                    if (isCloseBoxVisible()) {
                        boxEnter=false;
                        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
                        if (zc!=null) {
                            zc.repaint();
                        }
                    }
                }
            });
        }
    }

    public class ZDialogTitle extends JLabel {

	@Override
        public void paint(Graphics g) {
            if (isTitleVisible()) {
                super.paint(g);
            }
        }

        public ZDialogTitle(String ttle) {
            super(ttle);
            setFont(ZTools.ZDialogTitleFont);
            setForeground(ZTools.TitleTextColor);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setOpaque(false);

            addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseDragged(MouseEvent arg0) {
                    if (isTitleVisible()) {
                        Point current=MouseInfo.getPointerInfo().getLocation();
                        me.setLocation(winloc.x+current.x-origin.x, winloc.y+current.y-origin.y);
			/*
			 * the ZDialog window doesn't have to exit the main window
			 */
			ZirkelCanvas zc = JZirkelCanvas.getCurrentZC();
			if(me.getLocation().x+me.getWidth()-NOT_EXIT<=0){
			    me.setLocation(-me.getWidth()+NOT_EXIT, me.getLocation().y);
			}
			if(me.getLocation().x+NOT_EXIT>=zc.getWidth()){
			    me.setLocation(zc.getWidth()-NOT_EXIT, me.getLocation().y);
			}
			if(me.getLocation().y<=0){
			    me.setLocation(me.getLocation().x, 0);
			}
			if(me.getLocation().y+NOT_EXIT>=zc.getHeight()){
			    me.setLocation(me.getLocation().x, zc.getHeight()-NOT_EXIT);
			}
			/*
			 * end of code
			 */
		    }
                }
            });


            addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isTitleVisible()) {
                        origin=MouseInfo.getPointerInfo().getLocation();
                        winloc=me.getLocation();
                    }
                }
            });
        }
    }
    
    public void send(String msg) {
    }
}
