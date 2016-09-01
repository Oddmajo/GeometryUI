/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictContainerControls extends JEricPanel {

    ZirkelCanvas ZC;
    private static Image offimage=themes.getImage("tab_bottom.gif");
    private ArrayList<String> originalItems;
    private RestrictPanel panel;

    @Override
    public void paintComponent(Graphics g){
        Dimension d=getSize();
        g.setColor(new Color(230,230,230));
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(new Color(130,130,130));
        g.drawLine(0, 0, d.width, 0);
    }

    public RestrictContainerControls(RestrictPanel pan,ZirkelCanvas zc) {
        ZC=zc;
        panel=pan;
        // backup of restricted hidden items :
        originalItems=new ArrayList<String>();
        ArrayList<String> items=ZC.getHiddenItems();
        for (int i=0;i<items.size();i++){
            originalItems.add(items.get(i));
        };

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        myJButton closebtn=new myJButton(80, Global.Loc("restrict.ok")){
            @Override
            public void action(){
                ZC.setStandardRestrictedItems();
                ZC.closeRestrictDialog();
            }
        };
        myJButton cancelbtn=new myJButton(80, Global.Loc("restrict.cancel")){
            @Override
            public void action(){
                ZC.setHiddenItems(originalItems);
                PaletteManager.init();
                ZC.closeRestrictDialog();
            }
        };
        myJButton factorybtn=new myJButton(150, Global.Loc("restrict.factory")){
            @Override
            public void action(){
                ZC.initRestrictedHiddenItemsFromFactorySettings();
                panel.initAllStates();
            }
        };
        PaletteManager.fixsize(this, RestrictContainer.getContainerWidth(), 30);
        JEricPanel jp=new JEricPanel();
        jp.setOpaque(false);
        jp.setAlignmentX(0.5f);
        jp.setAlignmentY(0.5f);
        
        add(RestrictPanelIconsLine.margin(5));
        add(factorybtn);
        add(jp);
        add(cancelbtn);
        add(RestrictPanelIconsLine.margin(10));
        add(closebtn);
        add(RestrictPanelIconsLine.margin(5));
    }

    public class myJButton extends JButton {

        public myJButton(int w, String label) {
            super(label);
            setFocusable(false);
            setFont(new Font(Global.GlobalFont, 0, 12));
            setAlignmentX(0.5f);
            setAlignmentY(0.5f);
            setVerticalAlignment(SwingConstants.CENTER);
            PaletteManager.fixsize(this, w, 19);
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
}
