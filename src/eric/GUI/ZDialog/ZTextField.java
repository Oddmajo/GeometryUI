/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import eric.JEricPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

/**
 *
 * @author erichake
 */
public class ZTextField extends JEricPanel {
    JTextField field;

    @Override
    public void paint(Graphics g) {
        Dimension d=getSize();
        g.setColor(ZTools.B_TextField);
        g.fillRect(0, 0, d.width, d.height);
        paintChildren(g);
        g.setColor(ZTools.Bord_TextField);
        g.drawRect(0, 0, d.width, d.height);
    }

    public ZTextField(String s) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        field=new JTextField(s){
            @Override
            public void paintBorder(Graphics g){

            }
        };
        field.setMargin(new Insets(0, 0, 0, 0));
        field.setOpaque(false);
        field.setFont(ZTools.ZTextFieldFont);
        field.setForeground(ZTools.C_TextField);
        field.setBackground(ZTools.B_TextField);
        field.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(final KeyEvent e) {
                actionKey(e);
            }
        });
        field.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                actionMouse();
            }
        });
        field.addFocusListener(new FocusAdapter() {

            @Override
                public void focusGained(FocusEvent e) {
                    focusOn();
                }

            @Override
                public void focusLost(FocusEvent e) {
                    focusOff();
                }
            });
        JEricPanel panel=new JEricPanel();
        Dimension d=new Dimension(1, 1);
        panel.setOpaque(false);
        panel.setPreferredSize(d);
        panel.setMinimumSize(d);
        panel.setMaximumSize(d);
        panel.setSize(d);
        add(panel);
        add(field);
    }

    public void actionMouse() {
    }

    public void actionKey(KeyEvent k) {
    }

    public void focusOn(){
    }

    public void focusOff(){
    }

    public void setEditable(boolean b){
        if (field!=null) {
	    field.setEditable(b);
	}
    }

    public void setHorizontalAlignment(int align){
        field.setHorizontalAlignment(align);
    }
    public String getText(){
        return field.getText();
    }
    public void setText(String s){
        field.setText(s);
    }
    public void selectAll(){
        field.selectAll();
    }
    @Override
    public void setForeground(Color c){
        if (field!=null) {
	    field.setForeground(c);
	}
    }


}
