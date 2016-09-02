/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import java.awt.Color;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import eric.JEricPanel;
import javax.swing.JScrollPane;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictContainer extends JEricPanel {

    private ZirkelCanvas ZC;
    private static int W=500,H=400;
    private JScrollPane scroll;
    private RestrictPanel panel;
    private RestrictContainerTitle title;

    public RestrictContainer(ZirkelCanvas zc) {
        ZC=zc;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(255,255,255,100));
        title=new RestrictContainerTitle();
        panel=new RestrictPanel();
        scroll=new JScrollPane(panel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        scroll.setOpaque(false);
        add(title);
        add(scroll);
        add(new RestrictContainerControls(panel,ZC));
    }

    public void init() {
        int x=(ZC.getSize().width-W)/2;
        int y=(ZC.getSize().height-H)/2;
        setBounds(x, y, W, H);
    }

    public void selectFromZC(){

    }

    public static int getContainerWidth(){
        return W;
    }



    public void removeRestrictItem(RestrictPanelIcon si) {
    }

    public void removeRestrictItem(String nme) {
    }

    public void addRestrictItem(XmlTree tree) {
    }

    
}
