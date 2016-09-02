/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZCheckBox;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import java.awt.event.KeyEvent;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JobControlPanel extends ZDialog {


    private ZButton createBTN, removeBTN;
    private ZCheckBox hideFinalBox,staticJobBox;
    private ZTextFieldAndLabel okMessage, failedMessage, targetslist;
    private JobManager MAN;

    public JobControlPanel(JobManager man,int x,int y,int w,int h) {
        super(Global.Loc("job.gui.title"), x,y,w,h,true,true);
        MAN=man;

        createBTN=new ZButton(Global.Loc("job.gui.close")) {

            @Override
            public void action() {
                MAN.hideControlDialog(true);
            }
        };
        removeBTN=new ZButton(Global.Loc("job.gui.delete")) {

            @Override
            public void action() {
                MAN.hideControlDialog(false);
            }
        };
        okMessage=new ZTextFieldAndLabel(Global.Loc("job.gui.ok"), man.getMessage_ok(), LWIDTH,CHEIGHT) {

            @Override
            public void actionKey(KeyEvent k) {
                MAN.setMessage_ok(okMessage.getText());
            }
        };
        failedMessage=new ZTextFieldAndLabel(Global.Loc("job.gui.failed"), man.getMessage_failed(), LWIDTH,CHEIGHT) {

            @Override
            public void actionKey(KeyEvent k) {
                MAN.setMessage_failed(failedMessage.getText());
            }
        };
        targetslist=new ZTextFieldAndLabel(Global.Loc("job.gui.targets"), man.getTargetNames(), 0,CHEIGHT) {

            @Override
            public void actionMouse() {
                MAN.setJobTool();
            }

            @Override
            public void actionKey(KeyEvent k) {
                MAN.setTargets(targetslist.getText());
            }
        };
        hideFinalBox=new ZCheckBox(Global.Loc("job.gui.hidebox"), man.isHidefinals()) {

	    @Override
            public void action() {
                MAN.setHidefinals(hideFinalBox.isSelected());
            }
        };

        staticJobBox=new ZCheckBox(Global.Loc("job.gui.staticjob"), man.isStaticJob()) {

	    @Override
            public void action() {
                MAN.setStaticJob(staticJobBox.isSelected());
            }
        };
//        add(title);
//
        add(createBTN);
        add(removeBTN);
        add(staticJobBox);
        add(hideFinalBox);
        add(okMessage);
        add(failedMessage);
        add(targetslist);
        fixComponents();

    }

    @Override
    public void fixComponents() {
        targetslist.setBounds(MARGINW, MARGINTOP1, CWIDTH, CHEIGHT);
        okMessage.setBounds(MARGINW, MARGINTOP3, CWIDTH, CHEIGHT);
        failedMessage.setBounds(MARGINW, MARGINTOP4, CWIDTH, CHEIGHT);
        hideFinalBox.setBounds(MARGINW, MARGINTOP2, CWIDTH, CHEIGHT);
        staticJobBox.setBounds(D_WIDTH-MARGINW-CWIDTH+LWIDTH, MARGINTOP2, CWIDTH, CHEIGHT);
        createBTN.setBounds(D_WIDTH-BWIDTH-MARGINW, MARGINTOP5, BWIDTH, CHEIGHT);
        removeBTN.setBounds(D_WIDTH-MARGINW-CWIDTH+LWIDTH, MARGINTOP5, BWIDTH, CHEIGHT);
    }


    @Override
    public void doClose() {
        MAN.cancelControlDialog();
    }

    public String getTargetslist() {
        return targetslist.getText();
    }

    public void setTargetslist(String targets) {
        targetslist.setText(targets);
    }
}
