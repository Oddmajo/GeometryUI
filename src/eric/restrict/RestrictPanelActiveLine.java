/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.JZirkelCanvas;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictPanelActiveLine extends RestrictPanelLine {

    private RestrictPanel panel;

    public RestrictPanelActiveLine(RestrictPanel parent, String label) {
        super(label);
        setHorizontalAlignment(SwingConstants.CENTER);
        panel=parent;
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            setSelected(zc.isRestricted());
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                panel.setEnabledAll(isSelected());
            }
        });
    }

    public void action() {
        panel.setEnabledAll(true);
        if (isSelected()) {
            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
            if (zc!=null) {
                zc.initStandardRestrictedHiddenItems();
            }
            panel.initAllStates();
        }else{
            panel.setSelectAll(true);
        }
        panel.setEnabledAll(isSelected());
    }
}
