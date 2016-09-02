/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.animations;

import eric.GUI.themes;
import eric.GUI.window.myJMenuItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import eric.JEricPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.PointObject;

/**
 *
 * @author erichake
 */
public class AnimationPanel extends JEricPanel implements MouseListener {

    private ZirkelCanvas ZC;
    private Vector<Animation> V=new Vector<Animation>();
    private static Image icon=themes.getImage("animation.png");
    private int W=32, H=32, X=10, Y=47;
    private int minspeed=0;
    private int maxspeed=100;
    private int speed=40;
    private boolean stopped=false;
    private double a, b, A1, A2, B2;

    public void paintComponent(Graphics g) {
        g.drawImage(icon, 0, 0, W, H, this);
    }

    public int getMaxSpeed() {
        return maxspeed;
    }

    public AnimationPanel(ZirkelCanvas zc) {
        ZC=zc;
        setBounds(X, Y, W, H);
        addMouseListener(this);

        a=maxspeed/10;
        b=maxspeed-10;
        A1=b/a;
        A2=(maxspeed-b)/(maxspeed-a);
        B2=maxspeed*(b-a)/(maxspeed-a);
    }

    public double getDelay() {
        int cur=speed;
        double v;
        if (cur>a) {
            v=A2*cur+B2;
        } else {
            v=A1*cur;
        }
        double d=(maxspeed-v)*6;
        return d;
    }

    public void setDelay(double delay) {
        double cur=maxspeed-delay/6;
        if (cur>b) {
            speed=(int) ((cur-B2)/A2);
        } else {
            speed=(int) (cur/A1);
        }
    }

    public void showPopup() {
        JPopupMenu popup=new JPopupMenu();
        // add Cancel Item :
        myJMenuItem item=new myJMenuItem(Global.Loc("animation.run")) {

            @Override
            public void action() {
                startAnimation();
            }
        };
        popup.add(item);

        item=new myJMenuItem(Global.Loc("animation.stop")) {

            @Override
            public void action() {
                stopAnimation();
            }
        };
        popup.add(item);
        item=new myJMenuItem(Global.Loc("animation.reverse")) {

            @Override
            public void action() {
                reverseAnimation();
            }
        };
        popup.add(item);

        popup.add(new JSeparator());

        item=new myJMenuItem(Global.Loc("animation.removeall")) {

            @Override
            public void action() {
                removeAllAnimations();
            }
        };
        popup.add(item);
        popup.add(new JSeparator());


        popup.add(new SpeedSliderMenuItem());




        popup.show(this, W-10, H);
    }

    public Vector<Animation> getAnimations() {
        return V;
    }

    public void reverseAnimation() {
        for (int i=0; i<V.size(); i++) {
            V.get(i).setNegative(!V.get(i).getNegative());
        }
    }

    public void setAnimationNegative(String objectname, boolean negative) {
        for (int i=0; i<V.size(); i++) {
            if (objectname.equals(V.get(i).getObjectName())) {
                V.get(i).setNegative(negative);
                return;
            }
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stopAnimation() {
        stopped=true;
    }

    public void startAnimation() {
        stopped=false;
        run();
    }

    public void run() {
        if (!stopped) {
            for (int i=0; i<V.size(); i++) {
                V.get(i).run();
            }
        }
    }

    public boolean isAnimated(ConstructionObject o) {
        for (int i=0; i<V.size(); i++) {
            if (V.get(i).getObject().equalsTo(o)) {
                return true;
            }
        }
        return false;
    }

    public void removeAllAnimations() {
        for (int i=0; i<V.size(); i++) {
            V.get(i).killThread();
//            V.remove(V.get(i));
        }
        V.removeAllElements();
        ZC.remove(this);
        ZC.repaint();
    }

    public void removeAnimation(ConstructionObject o) {

        for (int i=0; i<V.size(); i++) {
            if (o.equalsTo(V.get(i).getObject())) {
                V.get(i).killThread();
                V.remove(V.get(i));
            }
        }

        if (V.size()==0) {
            ZC.remove(this);
            ZC.repaint();
        }
    }

    // Added with the animation tool :
    public void addAnimation(ConstructionObject o) {
        if (o==null) {
            return;
        }
        Animation anim=new Animation(ZC, this, o);
        V.add(anim);

        if (V.size()==1) {
            ZC.add(this);
            ZC.repaint();
        }
        if (!stopped) {
            anim.run();
        }
    }

    // Only for file loading :
    public void addAnimation(String objectname) {
        V.add(new Animation(ZC, this, objectname));
        if (V.size()==1) {
            ZC.add(this);
            ZC.repaint();
        }
    }

    public void setObjectSelected(boolean sel) {
        for (int i=0; i<V.size(); i++) {
            V.get(i).getObject().setSelected(sel);
        }
    }

    public void printArgs(final XmlWriter xml) {
        for (int i=0; i<V.size(); i++) {
            ConstructionObject o=V.get(i).getObject();
            if ((o!=null)&&(o instanceof PointObject)) {
                PointObject p=(PointObject) o;
                if ((p!=null)&&(p.isPointOn())&&(p.isInConstruction())) {
                    xml.startTagStart("Animate");
                    xml.printArg("animate", p.getName());
                    xml.printArg("negative", ""+V.get(i).getNegative());
                    xml.printArg("delay", ""+getDelay());
                    xml.printArg("stopped", ""+stopped);
                    xml.finishTagNewLine();
                }
            } else if ((o!=null)&&(o instanceof ExpressionObject)) {
                xml.startTagStart("Animate");
                xml.printArg("animate", o.getName());
                xml.printArg("negative", ""+V.get(i).getNegative());
                xml.printArg("delay", ""+getDelay());
                xml.printArg("stopped", ""+stopped);
                xml.finishTagNewLine();
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        showPopup();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    class SpeedSliderMenuItem extends JSlider implements ChangeListener {

        TitledBorder ttle;

        public SpeedSliderMenuItem() {
            ttle=new TitledBorder(" "+Global.Loc("animation.speed")+"="+speed+"% ");
            ttle.setTitleFont(themes.TabMenusFont);
            setBorder(ttle);
            setMinimum(minspeed);
            setMaximum(maxspeed);
            setValue(speed);
            setMajorTickSpacing(20);
            setMinorTickSpacing(10);
            setFocusable(false);

            addChangeListener(this);
            putClientProperty("JComponent.sizeVariant", "small");
            
//            http://java.sun.com/docs/books/tutorial/uiswing/lookandfeel/size.html

        }

        public void stateChanged(ChangeEvent e) {
//            System.out.println("vitesse="+getValue());
            speed=getValue();
            ttle.setTitle(" "+Global.Loc("animation.speed")+"="+speed+"% ");
            repaint();
        }
    }
}
