/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.windowComponent;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class PaletteZone extends windowComponent {

    protected ArrayList<JIcon> icons=new ArrayList<JIcon>();
    protected PaletteZoneTitle zone_title;
    protected PaletteZoneContent zone_content;
    private String zone_name;
    private PaletteZone me;

//    @Override
//    public void paintComponent(Graphics g) {
//    }
    public PaletteZone(String name) {
        super();
        me=this;
        zone_name=name;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(0.0f);
        zone_title=new PaletteZoneTitle(name) {

            @Override
            public void mouseClicked(MouseEvent e) {
                ToggleHideOrShow();
                PaletteManager.FixPaletteHeight(me);
            }
        };
        add(zone_title);
        zone_content=new PaletteZoneContent(this);
        add(zone_content);
//        HideOrShow();
    }

    private void HideOrShow() {
        hide_zone(Global.getParameter("hidepalette."+zone_name, false));
    }

    private void ToggleHideOrShow() {
        hide_zone(!Global.getParameter("hidepalette."+zone_name, false));
    }

    private void hide_zone(boolean b) {
        Global.setParameter("hidepalette."+zone_name, b);
        zone_title.setHide(b);
        zone_content.setHide(b);
    }

    public void init() {
        validate();
        HideOrShow();
        zone_title.init();
        for (JIcon element : icons) {
            element.init();
        }
        zone_content.init();
    }

    public void setHideContent(boolean b) {
        hide_zone(b);
    }

    public void setVisibleContentHeight() {
        zone_content.setVisibleHeight();
    }

    public void clearContent() {
        zone_content.removeAll();
    }

    public JIcon createToggleIcon(String name) {
        JIcon ji=new JIcon(name, name);
        addIcon(ji);
        return ji;
    }

    public JIcon createSimpleIcon(String name) {
        JIcon ji=new JIcon(name, null);
        addIcon(ji);
        return ji;
    }

    public JIcon createIcon(String name, String group) {
        JIcon ji=new JIcon(name, group);
        addIcon(ji);
        return ji;
    }

    public void createIcons(String myname[], String group) {
        for (String element : myname) {
            createIcon(element, group);
        }
    }

    // Seulement pour la zone Construction, et seulement dans
    // le cas où il s'agit d'un modeDP :
    public void insertEuclidianCheckBox(){

    }

    // names est une chaine du type " nom1 nom2 nom3 "
    public void insertIcons(String names, String group) {
        String myname[]=names.trim().split(" ");
        for (String element : myname) {
            if ((element.equals("blank"))||(findIcon(element)==null)) {
//            if (findIcon(element)==null) {
                JIcon ji=createIcon(element, group);
                PaletteManager.fixsize(ji, ji.getIconWidth(), ji.getIconWidth());
            }
        }
    }

    public JIcon findIcon(String name) {
        for (JIcon ji : icons) {
            if (name.equals(ji.getIconName())) {
                return ji;
            }
        }
        return null;
    }

    public void removeBlankIcons() {
        ArrayList<JIcon> blanks=new ArrayList<JIcon>();
        for (JIcon ji : icons) {
            if (ji.getIconName().equals("blank")) {
                blanks.add(ji);
            }
        }
        for (JIcon ji : blanks) {
            removeIcon(ji);
            PaletteManager.removeIcon(ji);
        }
    }

    // names est une chaine du type " nom1 nom2 nom3 "
    public void removeIcons(String names) {
        ArrayList<JIcon> icns=new ArrayList<JIcon>();
        String myname[]=names.trim().split(" ");
        for (String element : myname) {
            JIcon ji=findIcon(element);
            if (ji!=null) {
                icns.add(ji);
            }
        }
        for (JIcon ji : icns) {
            removeIcon(ji);
            PaletteManager.removeIcon(ji);
        }
    }

    public void removeIcon(JIcon ji) {
        icons.remove(ji);
        zone_content.remove(ji);
    }

    public void addIcon(JIcon ji) {
        icons.add(ji);
        zone_content.add(ji);
    }

    public void addComponent(JComponent jc) {
        zone_content.add(jc);
    }
}
