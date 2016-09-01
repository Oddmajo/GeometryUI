/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.themes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.Icon;
import eric.JSprogram.ScriptItem;

/**
 *
 * @author erichake modified by PM Mazat
 */
public class myJMenuItem extends JMenuItem implements ActionListener {
    private boolean selected;

    public void action() {
    }
    public myJMenuItem(String s) {
        super(s);
        addActionListener(this);
        setFont(themes.TabMenusFont);
    }

    public myJMenuItem(String s, Icon i){
        super(s, i);
        addActionListener(this);
        setFont(themes.TabMenusFont);
    }
    
    public myJMenuItem(ScriptItem si, Icon i){
        super(si.getScriptName(), i);
        addActionListener(this);
        setFont(themes.TabMenusFont);
    }

    public void actionPerformed(ActionEvent e) {
        action();
    }

    @Override
    public void setSelected(final boolean sel) {
        selected=sel;
        final int fontstyle=(selected)?1:0;
        setFont(new java.awt.Font(themes.TabMenusFont.getFontName(), fontstyle, themes.TabMenusFont.getSize()));
        repaint();
    }
}
