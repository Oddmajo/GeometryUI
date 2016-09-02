/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZLabel;
import eric.GUI.ZDialog.ZTools;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import eric.JEricPanel;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JobValidPanel extends ZDialog {

    private JobManager MAN;
    private ZButton validBTN, restoreBTN;
    private ZLabel label;
    protected int BWIDTH=180;      // Button width



    public JobValidPanel(JobManager man,int x,int y,int w,int h) {
        super("", x,y,w,h,false,false);
        MAN=man;
        setLayout(null);
        validBTN=new ZButton(Global.Loc("job.gui.valid")) {

            @Override
            public void action() {
                MAN.validate();
            }
        };
        restoreBTN=new ZButton(Global.Loc("job.gui.restore")) {
          
            @Override
            public void action() {
                MAN.restore();
            }
        };

        label=new ZLabel(Global.Loc("job.gui.exercise"));

        
        add(label);
        add(validBTN);
        add(restoreBTN);
        increaseFonts(1);
        fixComponents();
    }

    public void increaseFonts(int size){
        for (int i=0;i<getComponentCount();i++){
            Component c=getComponent(i);
            Font f=c.getFont();
            Font nf=new Font(f.getFontName(),f.getStyle(),f.getSize()+size);
            c.setFont(nf);
        }
    }

    public void fixComponents() {
        label.setBounds(MARGINW, MARGINTOP1, CWIDTH, CHEIGHT);
        validBTN.setBounds(D_WIDTH-MARGINW-BWIDTH, MARGINTOP1, BWIDTH, CHEIGHT);
        restoreBTN.setBounds(D_WIDTH-2*MARGINW-2*BWIDTH, MARGINTOP1, BWIDTH, CHEIGHT);
        revalidate();
    }

    public void init(int w, int h) {
        int x=(w-D_WIDTH)/2;
        int y=h-D_HEIGHT-4;
        setBounds(x, y, D_WIDTH, D_HEIGHT);
        fixComponents();
    }

    private class myButton extends JButton {

        public myButton(String lbl) {
            super(lbl);
            setFont(new Font(Global.GlobalFont, 0, 12));
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    action();
                }
            });
        }

        public void action() {
        }
    }

    private class myLabel extends JLabel {

        public myLabel(String lbl) {
            super(lbl);
            setFont(new Font(Global.GlobalFont, 0, 12));
        }
    }
}
