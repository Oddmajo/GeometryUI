/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import eric.GUI.window.myJMenuItem;
import eric.controls.JCanvasPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JMenu;
import eric.JEricPanel;
import java.awt.event.MouseAdapter;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import rene.gui.Global;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake, modified by PM Mazat
 */
public class ScriptPanel extends JEricPanel {

    private ArrayList<String> BACKUPS=new ArrayList<String>();
    private ZirkelCanvas ZC;
    private ScriptItemsArray items=new ScriptItemsArray();
    private ArrayList<ScriptItem> itemsbackup;
    private static Image icon=themes.getImage("scripts.png");
    private int W=32, H=32, X=10, Y=10;
    private ScriptsManager ScriptsManagerPanel=null;
    private static int ScriptsManagerPanel_X=3, ScriptsManagerPanel_Y=45, ScriptsManagerPanel_WIDTH=3*12+180+90;

    public ScriptPanel(ZirkelCanvas zc) {
        ZC=zc;
        setBounds(X, Y, W, H);

	MouseListener ml = new MouseAdapter(){

	    @Override
	    public void mousePressed(MouseEvent e){
		showPopup();
	    }
	};

	this.addMouseListener(ml);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(icon, 0, 0, W, H, this);
    }

    public void Backup() {
        try {
            BACKUPS.add(FileTools.getCurrentFileSource());
            fixConsoleBackBtn();
        } catch (Exception ex) {
        }
    }

    public boolean isBackup() {
        return (BACKUPS.size()>0);
    }

    public void Restore() {
        ZC.killAllScripts();
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
        }
        backupScriptItems();
        if (BACKUPS.size()>0) {
            ZC.getConstruction().clear();
            try {
                FileTools.setCurrentFileSource(BACKUPS.get(BACKUPS.size()-1));
            } catch (final Exception e) {
            }
            BACKUPS.remove(BACKUPS.size()-1);

        }
        restoreScriptItems();
        fixConsoleBackBtn();
	/*
	 * Si on a ajouté un script après avoir lancé d'un script de bibliothèque
	 * le panneau n'est pas affiché après l'annulation
	 */
	if(items.size()!=0)
	    ZC.add(this);
    }

    public void backupScriptItems() {
        itemsbackup=new ArrayList<ScriptItem>();
        for (ScriptItem item : items) {
            item.fixmouseDragTargetsNames();
	    item.fixmouseUpTargetsNames();
            itemsbackup.add(item);
        }
    }

    public void restoreScriptItems() {
        items.clear();
        for (ScriptItem item : itemsbackup) {
            item.reloadMouseDragTargets();
	    item.reloadMouseUpTargets();
            items.add(item);
        }
    }

    public void fixConsoleBackBtn() {
        for (ScriptItem item : items) {
            JSEditor jsc=item.getEditor();
            if (jsc!=null) {
                jsc.setBackBtnEnabled(BACKUPS.size()>0);
            }
        }
    }

    public void showPopup() {
        JPopupMenu popup = new JPopupMenu();

        // add Cancel Item :
        myJMenuItem item=new myJMenuItem(Global.Loc("JSmenu.cancel"), themes.resizeExistingIcon("/eric/GUI/icons/jswindow/restore.png", 16, 16)) {

            @Override
            public void action() {
                onlyRemoveScriptsManagerPanel();
                Restore();
            }
        };
        item.setEnabled(isBackup());
        popup.add(item);
        popup.add(new JSeparator());

        // add New Script Item :
        item=new myJMenuItem(Global.Loc("JSmenu.add")) {

            @Override
            public void action() {
                newScript();
            }
        };
        popup.add(item);
        popup.add(new JSeparator());

        // add Run script Items :
        for (int i=0; i<items.size(); i++) {
            final int I=i;
            item=new myJMenuItem(items.get(i), themes.resizeExistingIcon("/eric/GUI/icons/jswindow/run.png", 12, 16)) {

                @Override
                public void action() {
                    onlyRemoveScriptsManagerPanel();
                    items.get(I).runScript();
                }
            };
            item.setEnabled(!items.get(i).isRunning());
            popup.add(item);
        }
        popup.add(new JSeparator());

        // add Kill Item :
        item=new myJMenuItem(Global.Loc("JSmenu.killall")) {

            @Override
            public void action() {
                ZC.killAllScripts();
            }
        };
        item.setEnabled(ZC.isThereAnyScriptRunning());
        popup.add(item);

        // add Stop Item :
        item=new myJMenuItem(Global.Loc("JSmenu.stopall")) {

            @Override
            public void action() {
                ZC.stopAllScripts();
            }
        };
        item.setEnabled(ZC.isThereAnyScriptRunning()&&!ZC.isThereAnyStoppedScripts());
        popup.add(item);

        // add Restart Item :
        item=new myJMenuItem(Global.Loc("JSmenu.restartall")) {

            @Override
            public void action() {
                ZC.restartAllScripts();
            }
        };
        item.setEnabled(ZC.isThereAnyStoppedScripts());
        popup.add(item);
        popup.add(new JSeparator());

        // add Modify Menu :
        JMenu modifypopup=new JMenu(Global.Loc("JSmenu.modify"));
        for (int i=0; i<items.size(); i++) {
            final int itemnum=i;
            item=new myJMenuItem(items.get(i).getScriptName()) {

                @Override
                public void action() {
                    items.get(itemnum).openEmbeddedScript();
                }
            };
            modifypopup.add(item);
            modifypopup.setFont(themes.TabMenusFont);
        }
        popup.add(modifypopup);

        // add Delete Menu :
        JMenu deletepopup=new JMenu(Global.Loc("JSmenu.delete"));
        for (int i=0; i<items.size(); i++) {
            final int itemnum=i;
            item=new myJMenuItem(items.get(i).getScriptName()) {

                @Override
                public void action() {
                    removeScript(items.get(itemnum));
                }
            };
            deletepopup.add(item);
            deletepopup.setFont(themes.TabMenusFont);
        }
        popup.add(deletepopup);

        // add Scripts Manager Item
        item=new myJMenuItem(Global.Loc("JSmenu.ScriptsManager")) {

            @Override
            public void action() {
                addScriptsManagerPanel();
            }
        };
        popup.add(item);

        popup.show(this, W-10, H);
    }

    public void onlyRemoveScriptsManagerPanel() {
        if (ScriptsManagerPanel!=null) {
            ZC.remove(ScriptsManagerPanel);
        }
    }

    public void removeScriptsManagerPanel() {
        if (ScriptsManagerPanel!=null) {
            ScriptsManagerPanel_X=ScriptsManagerPanel.getLocation().x;
            ScriptsManagerPanel_Y=ScriptsManagerPanel.getLocation().y;
            ZC.remove(ScriptsManagerPanel);
            //ZC.repaint();
            ScriptsManagerPanel=null;
            PaletteManager.ClicOn("move");
        }
    }

    public void addScriptsManagerPanel() {
        final int h;
        removeScriptsManagerPanel();
        h=((items.size()==1?0:items.size())+(items.size()==2?1:0))*18+168; //147
        ScriptsManagerPanel=new ScriptsManager(ZC, this, items, ScriptsManagerPanel_X, ScriptsManagerPanel_Y, ScriptsManagerPanel_WIDTH, h);
        ZC.add(ScriptsManagerPanel);
        ZC.repaint();
        ScriptsManagerPanel.init();
        PaletteManager.deselectgeomgroup();
        ZC.showStatus("");
        ZC.showStatus(Global.Loc("JSmenu.ScriptsManager"));
    }

    public ScriptItemsArray getScripts() {
        return items;
    }

    public boolean isThereAnyOnLoadScript() {
        for (ScriptItem item : items) {
            if (item.getExecuteOnLoad()) {
                return true;
            }
        }
        return false;
    }

    public void runOnLoadScripts() {
        if (isThereAnyOnLoadScript()) {
            try {
                String myfile=FileTools.getCurrentFileSource();
                for (ScriptItem item : items) {
                    if (item.getExecuteOnLoad()) {
                        item.runScript();
                    }
                }
                BACKUPS.clear();
                BACKUPS.add(myfile);
                fixConsoleBackBtn();
            } catch (Exception ex) {
            }

        }
    }

    public void removeAllScripts() {
        while (items.size()>0) {
            removeScript(items.get(0));
        }
    }

    public void removeScript(ScriptItem si) {
        ZC.getConstruction().haveChanged();
        items.remove(si);
        if (items.isEmpty()) {
            ZC.remove(this);
            removeScriptsManagerPanel();
            ZC.repaint();
        }
        if (ScriptsManagerPanel!=null) {
            addScriptsManagerPanel();
        }
    }

    public void addScript(XmlTree tree) {
        items.add(new ScriptItem(this, tree));

        // at first added script, button must be drawn in the canvas :
        if (items.size()==1) {
            ZC.add(this);
        }
    }

    private ScriptItem findScript(String nme) {
        for (int i=0; i<items.size(); i++) {
            if (nme.equals(items.get(i).getScriptName())) {
                return items.get(i);
            }
        }
        return null;
    }

    public void removeScript(String nme) {
        ScriptItem si=findScript(nme);
        if (si!=null) {
            removeScript(si);
        }
    }

    public void saveScript(String nme, String source) {
        ScriptItem si=findScript(nme);
        if (si!=null) {
            si.setScriptSource(source);
        } else {
            items.add(new ScriptItem(this, nme, source));
        }
        if (ScriptsManagerPanel!=null) {
            addScriptsManagerPanel();
        }

        // at first added script, button must be drawn in the canvas :
        if (items.size()==1) {
            ZC.add(this);
            ZC.repaint();
        }
    }

    public void openScriptFile(String filename, boolean open) {
        ScriptItem item=new ScriptItem(this, "", "");
        items.add(item);
        // at first added script, button must be drawn in the canvas :
        if (items.size()==1) {
            ZC.add(this);
            ZC.repaint();
        }
        item.openScriptFile(filename, open);
    }

    public void newScript() {
        ScriptItem item=new ScriptItem(this, "", "");
        items.add(item);
        // at first added script, button must be drawn in the canvas :
        if (items.size()==1) {
            ZC.add(this);
            ZC.repaint();
        }
        item.newScriptInConstruction();
        // if Scripts Manager is visible, it must be updated
        if (ScriptsManagerPanel!=null) {
            addScriptsManagerPanel();
        }
    }

    public void runControlScripts(JCanvasPanel jp) {
        for (ScriptItem item : items) {
            item.runControlScript(jp);
        }
    }

    public void prepareDragActionScript(ConstructionObject o) {
        for (ScriptItem item : items) {
            item.prepareDragAction(o);
        }
    }

    public void runDragAction() {
        for (ScriptItem item : items) {
            item.runDragAction();
        }
    }
    public void runUpAction(ConstructionObject o) {
	for(ScriptItem item : items){
	    item.runUpAction(o);
	}
    }

    public void stopDragAction() {
        for (ScriptItem item : items) {
            item.stopDragAction();
        }
    }

    public void fixMouseTargets() {
        for (ScriptItem item : items) {
            item.fixMouseTargets();
        }
    }
}