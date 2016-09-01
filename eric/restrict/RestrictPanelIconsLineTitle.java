/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.JZirkelCanvas;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictPanelIconsLineTitle extends RestrictPanelLine {

    private String name;
    private ArrayList<RestrictPanelIcon> icons=new ArrayList<RestrictPanelIcon>();

    public RestrictPanelIconsLineTitle(String nme, String label) {
        super(label);
        name=nme;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setSelectedIcons(isSelected());
            }
        });
        initState();
    }

    public void initState() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            setSelected(!zc.isHiddenItem(name));
            if (!isSelected()) {
                setSelectedIcons(false);
            }
        }
    }

    public void initIconsState() {
        for (int i=0; i<icons.size(); i++) {
            icons.get(i).initState();
        }
    }

    public void addIcon(RestrictPanelIcon icn) {
        icons.add(icn);
    }

    public void setSelected(boolean groupSelect, boolean sel) {
        setSelected(sel);
        if (groupSelect) {
            setSelectedIcons(sel);
        }
    }

    public void uncheckIfAlone() {
        if (isSelected()) {
            boolean b=false;
            for (int i=0; i<icons.size(); i++) {
                b=b||icons.get(i).isSelected();
            }
            if (!b) {
                setSelected(false);
            }
        }
    }

    public void setSelectedIcons(boolean b) {
        for (int i=0; i<icons.size(); i++) {
            icons.get(i).setSelected(b);
        }
    }

    public void setEnabledIcons(boolean b) {
        for (int i=0; i<icons.size(); i++) {
            icons.get(i).setEnabled(b);
        }
    }

    @Override
    public void action() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            if (isSelected()) {
                zc.removeHiddenItem(name);
            } else {
                zc.addHiddenItem(name);
            }
        }
        PaletteManager.init();
    }
}
