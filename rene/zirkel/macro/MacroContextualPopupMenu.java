/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.macro;

import eric.JZirkelCanvas;
import eric.macros.CreateMacroDialog;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class MacroContextualPopupMenu extends PopupMenu {

    static MenuItem newmacro;

    public MacroContextualPopupMenu() {
        super();
        addNewItem();
    }

    public void addNewItem() {
        newmacro=new MenuItem(Global.Loc("palette.info.newmacro"));
        newmacro.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new CreateMacroDialog(JZirkelCanvas.getNewMacroPanel());
                JZirkelCanvas.ActualiseMacroPanel();
            }
        });
        add(newmacro);
        addSeparator();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        addNewItem();
    }
}


