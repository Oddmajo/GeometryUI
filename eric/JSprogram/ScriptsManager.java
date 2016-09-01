/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZCheckBox;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZLabel;
import eric.GUI.ZDialog.ZSep;
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.GUI.palette.PaletteManager;
import eric.OS;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author PM Mazat
 */
public class ScriptsManager extends ZDialog implements ListSelectionListener, FocusListener {

    private ZirkelCanvas ZC;
    private int SCRIPTS_WIDTH=180, SCRIPTS_HEIGHT, ACTION_WIDTH=110,RENAME_WIDTH=70, BTN_Y;
    private ZButton upBTN, downBTN;
    private ZTextFieldAndLabel renameFIELD, mousedragFIELD, mouseupFIELD;
    private ZCheckBox onstartCKBOX;
    private ZSep behaveSEP, renameSEP;
    private ZLabel behaveLBL;
    private DefaultListModel listModel;
    private ScriptItemsArray items=new ScriptItemsArray();
    private JList list;
    private ScriptPanel JP;

    public ScriptsManager(ZirkelCanvas zc, ScriptPanel jp, ScriptItemsArray items, int x, int y, int w, int h) {
        super(Global.Loc("JSmenu.ScriptsManager"), x, y, w, h, true, true);
        ZC=zc;
        JP=jp;
        this.items=items;
        SCRIPTS_HEIGHT=items.size()==1?0:items.size()*18;
        BTN_Y=THEIGHT+(SCRIPTS_HEIGHT+(items.size()==2?1:0)*18)/2;
        addContent();
    }

    private void addContent() {
        upBTN=new ZButton(Global.Loc("JSmenu.up")) {

            @Override
            public void action() {
                if (list.getSelectedIndex()!=0) {
                    refreshList(list.getSelectedIndex(), -1);
                }
            }
        };
        upBTN.addFocusListener(this);

        downBTN=new ZButton(Global.Loc("JSmenu.down")) {

            @Override
            public void action() {
                if (list.getSelectedIndex()!=items.size()-1) {
                    refreshList(list.getSelectedIndex(), 1);
                }
            }
        };
        downBTN.addFocusListener(this);
        if (OS.isUnix()) {
            downBTN.setFont(downBTN.getFont().deriveFont(10f));
        }

        behaveSEP=new ZSep(75);
        renameSEP=new ZSep(75);
//        behaveLBL=new ZLabel("Script behavior :");

        renameFIELD=new ZTextFieldAndLabel(Global.Loc("JSmenu.rename"), items.get(0).getScriptName(), RENAME_WIDTH, CHEIGHT) {

            @Override
            public void actionKey(KeyEvent k){
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setScriptName(renameFIELD.getText());
                    listModel.setElementAt(renameFIELD.getText(), i);
                }
            }

            @Override
            public void actionMouse() {
                closeScriptTools();
            }
        };
        renameFIELD.addFocusListener(this);


        onstartCKBOX=new ZCheckBox(Global.Loc("JSmenu.executeonstart"), false) {

	    @Override
            public void action() {
//                MAN.setHidefinals(hideFinalBox.isSelected());
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setExecuteOnLoad(onstartCKBOX.isSelected());
                }
            }
        };

        mousedragFIELD=new ZTextFieldAndLabel(Global.Loc("JSmenu.dragaction"), "", ACTION_WIDTH, CHEIGHT) {

            @Override
            public void actionMouse() {
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setMouseDragTool(mousedragFIELD, mouseupFIELD);
                }
            }

            @Override
            public void actionKey(KeyEvent k) {
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setMouseDragTargets(getText());
                }
            }
        };

	mouseupFIELD=new ZTextFieldAndLabel("On mouse up :", "", ACTION_WIDTH, CHEIGHT) {

            @Override
            public void actionMouse() {
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setMouseUpTool(mouseupFIELD, mousedragFIELD);
                }
            }

            @Override
            public void actionKey(KeyEvent k) {
                int i=list.getSelectedIndex();
                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
                    items.get(i).setMouseUpTargets(getText());
                }
            }
        };

//        mouseoverFIELD=new ZTextFieldAndLabel("Over :", "", RENAME_WIDTH, CHEIGHT) {
//
//            @Override
//            public void actionMouse() {
//                int i=list.getSelectedIndex();
//                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
//                    items.get(i).setMouseOverTool(mouseoverFIELD);
//                }
//            }
//
//            @Override
//            public void actionKey() {
//                int i=list.getSelectedIndex();
//                if (i!=-1&&!renameFIELD.getText().isEmpty()) {
//                    items.get(i).setMouseOverTargets(getText());
//                }
//            }
//        };



        listModel=new DefaultListModel();
        for (int i=0; i<items.size(); i++) {
            listModel.add(i, items.get(i).getScriptName());
        }
        list=new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(new Color(216, 216, 216));
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.addFocusListener(this);
        upBTN.setEnabled(false); //because the default SelectedIndex of "list" is 0 and it can't be shifted up

        if (items.size()!=1) {
            this.add(upBTN);
            this.add(downBTN);
            this.add(list);
        }
        this.add(renameFIELD);
        this.add(behaveSEP);
        this.add(onstartCKBOX);
        this.add(mousedragFIELD);
	this.add(mouseupFIELD);
//        this.add(mouseoverFIELD);
        this.add(renameSEP);
        this.fixComponents();
        refreshFields();
    }

    private void refreshFields() {
        int i=list.getSelectedIndex();
        if (i!=-1) {
            renameFIELD.setText(items.get(i).getScriptName());
            mousedragFIELD.setText(items.get(i).getMouseDragTargetNames());
	    mouseupFIELD.setText(items.get(i).getMouseUpTargetNames());
            onstartCKBOX.setSelected(items.get(i).getExecuteOnLoad());
//            mouseoverFIELD.setText(items.get(i).getMouseOverTargetNames());
        }
    }

    private void refreshList(int i, int e) {
        ScriptItem si=items.get(i+e);
        Object o=listModel.elementAt(i+e);

        items.set(i+e, items.get(i));
        listModel.setElementAt(listModel.elementAt(i), i+e);
        items.set(i, si);
        listModel.setElementAt(o, i);
        list.setSelectedIndex(i+e);
    }

    @Override
    public void fixComponents() {
        upBTN.setBounds(D_WIDTH-MARGINW-BWIDTH, BTN_Y-MARGINW/4-CHEIGHT, BWIDTH, CHEIGHT);
        downBTN.setBounds(D_WIDTH-MARGINW-BWIDTH, BTN_Y+MARGINW/4, BWIDTH, CHEIGHT);
        list.setBounds(MARGINW, THEIGHT+2, SCRIPTS_WIDTH, SCRIPTS_HEIGHT);
        int top1=D_HEIGHT-141;//120

        behaveSEP.setBounds(0, top1+5, D_WIDTH, 1);
        top1+=16;
        mousedragFIELD.setBounds(MARGINW, top1, D_WIDTH-2*MARGINW, CHEIGHT);
	mouseupFIELD.setBounds(MARGINW, top1+CHEIGHT+3, D_WIDTH-2*MARGINW, CHEIGHT);
//        mouseoverFIELD.setBounds(MARGINW, top1+CHEIGHT+3, PANEL_WIDTH-2*MARGINW, CHEIGHT);
        onstartCKBOX.setBounds(MARGINW, top1+2*CHEIGHT+9, D_WIDTH-2*MARGINW, CHEIGHT);

        renameSEP.setBounds(0, top1+3*CHEIGHT+20, D_WIDTH, 1);
        renameFIELD.setBounds(MARGINW, top1+4*CHEIGHT+20, D_WIDTH-2*MARGINW, CHEIGHT);
    }

    @Override
    public void doClose() {
        JP.removeScriptsManagerPanel();
    }

    public void closeScriptTools() {
        ZC.clearSelected();
        PaletteManager.setSelected_with_clic("point", true);
    }

    //required by ListSelectionListener
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int i=list.getSelectedIndex();
        if (i==-1) {
            //No selection, disable buttons
            upBTN.setEnabled(false);
            downBTN.setEnabled(false);
            renameFIELD.setText("");
            mousedragFIELD.setText("");
	    mouseupFIELD.setText("");
            onstartCKBOX.setSelected(false);
//            mouseoverFIELD.setText("");
        } else {
            if (i==0) {
                upBTN.setEnabled(false);
                downBTN.setEnabled(true);
            } else {
                if (i==items.size()-1) {
                    upBTN.setEnabled(true);
                    downBTN.setEnabled(false);
                } else {
                    upBTN.setEnabled(true);
                    downBTN.setEnabled(true);
                }
            }
            renameFIELD.setText(items.get(i).getScriptName());
            mousedragFIELD.setText(items.get(i).getMouseDragTargetNames());
	    mouseupFIELD.setText(items.get(i).getMouseUpTargetNames());
            onstartCKBOX.setSelected(items.get(i).getExecuteOnLoad());
//            mouseoverFIELD.setText(items.get(i).getMouseOverTargetNames());
        }
        closeScriptTools();
    }

    //required by FocusListener
    @Override
    public void focusGained(FocusEvent e) {
        PaletteManager.deselectgeomgroup();
        //ZC.showStatus("");
        ZC.showStatus(Global.Loc("JSmenu.ScriptsManager"));
    }

    @Override
    public void focusLost(FocusEvent e) {
        ZC.showStatus();
    }
}
