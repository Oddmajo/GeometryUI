/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric;

import java.awt.LayoutManager;



/**
 *
 * @author erichake
 */
public class JEricPanel extends javax.swing.JPanel{

    public JEricPanel(LayoutManager lm){
        super(lm);
    }
    public JEricPanel(){
        super();
        JLogoWindow.progress("JPanel");
    }
}
