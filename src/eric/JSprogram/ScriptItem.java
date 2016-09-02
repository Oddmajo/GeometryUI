/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import eric.controls.JCanvasPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import rene.gui.Global;
import rene.util.FileName;
import rene.util.xml.XmlTree;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.tools.Scripts_SetMouseDrag;
//import rene.zirkel.tools.Scripts_SetMouseOver;
import rene.zirkel.tools.Scripts_SetMouseUp;

/**
 *
 * @author erichake
 */
public class ScriptItem extends JMenuItem implements ActionListener {

    private String name, source;
    private boolean executeOnLoad=false;
    private ArrayList<ConstructionObject> mouseDragEventTargets=new ArrayList<ConstructionObject>();
    private ArrayList<ConstructionObject> mouseUpEventTargets=new ArrayList<ConstructionObject>();
    private ArrayList<ConstructionObject> mouseOverEventTargets=new ArrayList<ConstructionObject>();
    private ScriptThread THREAD=null;
    private ZTextFieldAndLabel currentInputField=null, otherInputField = null;
    private String mouseDragTargetsNames="", mouseUpTargetsNames=""; // Attention, champs intermédiaire pour le load, ne reflète pas toujours la réalité
    //    private String mouseOverTargetsNames="";
    private JSEditor JSC=null;
    private String FILENAME="";
    private ScriptPanel PANEL;

    public ScriptItem(ScriptPanel panel, XmlTree tree) {
        super(tree.getTag().getValue("Name"));
        PANEL=panel;
        name=tree.getTag().getValue("Name");
        if (tree.getTag().hasParam("mousedrag")) {
            mouseDragTargetsNames=tree.getTag().getValue("mousedrag");
        }
	if (tree.getTag().hasParam("mouseup")) {
            mouseUpTargetsNames=tree.getTag().getValue("mouseup");
        }
        executeOnLoad=tree.getTag().hasTrueParam("onload");
        source=tree.getText();
        source=source.replaceAll("^[\n]*", "");
        source=source.replace("&lt;", "<");
        source=source.replace("&gt;", ">");
        addActionListener(this);
        setFont(themes.TabMenusFont);
    }

    public ScriptItem(ScriptPanel panel, String scriptname, String scriptsource) {
        super(scriptname);
        PANEL=panel;
        name=scriptname;
        source=scriptsource;
        addActionListener(this);
        setFont(themes.TabMenusFont);
    }

    public ScriptPanel getPanel() {
        return PANEL;
    }

    public void setExecuteOnLoad(boolean b) {
        executeOnLoad=b;
    }

    public boolean getExecuteOnLoad() {
        return executeOnLoad;
    }

    public void setFileName(String name) {
        FILENAME=name;
    }

    public String getFileName() {
        return FILENAME;
    }

    public String getScriptName() {
        return name;
    }

    public String getScriptSource() {
        return source;
    }

    public void setScriptName(String s) {
        name=s;
    }

    public void setScriptSource(String s) {
        source=s;
    }

    public void setEditor(JSEditor jse){
        JSC=jse;
    }

    public JSEditor getEditor() {
        return JSC;
    }

    public void openEditor() {
        if (JSC==null) {
            JSC=new JSEditor(this);
        } else {
            JSC.setVisible(true);
            JSC.toFront();
        }
    }

    public void closeEditor() {
        if (JSC!=null) {
            JSC.setVisible(false);
        }
    }

    public static boolean unique(String s, ScriptItemsArray V) {
        for (ScriptItem myItem : V) {
            if (s.equals(myItem.getScriptName())) {
                return false;
            }
        }
        return true;
    }

    public static String uniqueScriptName(String base) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return base;
        }
        ScriptItemsArray V=zc.getScripts();
        if (!unique(base, V)) {
            int num=0;
            do {
                num++;
                base=base.replaceAll("[\\s0-9]+$", "")+" "+num;
            } while (!unique(base, V));
        }
        return base;
    }

    public void newScriptInConstruction() {
        openEditor();
        String s="";
        do {
            s=(String) JOptionPane.showInputDialog(
                    JSC,
                    Global.Loc("JSeditor.saveinfig.question"),
                    Global.Loc("JSeditor.saveinfig.title"),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
        } while ("".equals(s));
        s=uniqueScriptName(s);
        JSC.setWindowTitle(Global.Loc("JSeditor.infig")+s);
        JSC.setScriptName(s);
        setScriptName(s);
    }

    public void openScriptFile(final String myname, boolean open) {
        String str="";
        String mystr="";
        try {
            InputStream input=new FileInputStream(myname);
            BufferedReader in=new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while ((str=in.readLine())!=null) {
                str=str.trim();
                mystr+=str+"\n";
            }
            source=mystr;
            name=FileName.filename(myname);
	    if(open)
		openEditor();
            JSC.setScriptArea(source);
            JSC.setWindowTitle(Global.Loc("JSeditor.infig")+name);
            JSC.setScriptName(name);
        } catch (Exception ex) {
        }
    }

    public void openEmbeddedScript() {
        openEditor();
        JSC.setScriptArea(source);
        JSC.setWindowTitle(Global.Loc("JSeditor.infig")+name);
        JSC.setScriptName(name);
    }

    public void sendErrorToEditor(String message) {
        openEditor();
        JSC.setScriptArea(source);
        JSC.setWindowTitle(FILENAME);
        JSC.Error(message);
    }

    public void fixMouseTargets() {
        setMouseDragTargets(mouseDragTargetsNames);
	setMouseUpTargets(mouseUpTargetsNames);
//        setMouseOverTargets(mouseOverTargetsNames);
    }

    public void saveScript(final XmlWriter xml) {
        xml.startTagStart("Script");
        xml.printArg("Name", name);
        if(mouseDragEventTargets.size()>0) {
            xml.printArg("mousedrag", getMouseDragTargetNames());
        }
	if(mouseUpEventTargets.size()>0) {
            xml.printArg("mouseup", getMouseUpTargetNames());
        }
        xml.printArg("onload", ""+executeOnLoad);
        xml.startTagEndNewLine();
        xml.print(source);
        //xml.println();
        xml.endTagNewLine("Script");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runScript();
    }

    public void stopme() {
        if (THREAD!=null) {
            THREAD.stopme();
        }
    }

    public void restartme() {
        if (THREAD!=null) {
            THREAD.restartme();
        }
    }

    public void killme() {
        if (THREAD!=null) {
            THREAD.killme();
            THREAD=null;
        }
    }

    public boolean isRunning() {
        if (THREAD!=null) {
            return THREAD.isRunning();
        }
        return false;
    }

    public boolean isStopped() {
        if (THREAD!=null) {
            return THREAD.isStopped();
        }
        return false;
    }

    public void runScript() {
        killme();
        THREAD=new ScriptThread(this);
        THREAD.runme();
    }

    public void runControlScript(JCanvasPanel jp) {
        if (isRunning()) {
            return;
        }
        for (ConstructionObject obj : mouseDragEventTargets) {
            if (obj==jp.O) {
                THREAD=new ScriptThread(this);
                THREAD.runme();
                return;
            }
        }
    }

    public void prepareDragAction(ConstructionObject o) {
        if (isRunning()) {
            return;
        }
        for (ConstructionObject obj : mouseDragEventTargets) {
            if (obj==o) {
                THREAD=new ScriptThread(this);
                THREAD.prepareActionScript(o.getName());
                return;
            }
        }
    }

    public void runDragAction() {
        if (THREAD!=null) {
            THREAD.runActionScript();
        }
    }
    public void runUpAction(ConstructionObject o){
	for(ConstructionObject obj : mouseUpEventTargets){
	    if(obj==o){
		runScript();
	    }
	}
    }

    public void stopDragAction() {
        if (THREAD!=null) {
            THREAD.stopActionScript();
        }
    }

    public void refreshMouseDragInputField() {
        if (currentInputField!=null) {
            currentInputField.setText(getMouseDragTargetNames());
	    otherInputField.setText(getMouseUpTargetNames());
        }
    }
    public void refreshMouseUpInputField() {
        if (currentInputField!=null) {
            currentInputField.setText(getMouseUpTargetNames());
	    otherInputField.setText(getMouseDragTargetNames());
        }
    }

    public void setMouseDragTool(ZTextFieldAndLabel current, ZTextFieldAndLabel other) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            currentInputField=current;
	    otherInputField = other;
            PaletteManager.deselectgeomgroup();
            zc.setTool(new Scripts_SetMouseDrag(this));
            setTargetSelected(zc, mouseDragEventTargets, true);
        }
    }
    public void setMouseUpTool(ZTextFieldAndLabel current, ZTextFieldAndLabel other) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            currentInputField=current;
	    otherInputField = other;
            PaletteManager.deselectgeomgroup();
            zc.setTool(new Scripts_SetMouseUp(this));
            setTargetSelected(zc, mouseUpEventTargets, true);
        }
    }

    public String getMouseDragTargetNames() {
        return getTargetNames(mouseDragEventTargets);
    }
    public String getMouseUpTargetNames() {
        return getTargetNames(mouseUpEventTargets);
    }

    public void fixmouseDragTargetsNames(){
        mouseDragTargetsNames=getTargetNames(mouseDragEventTargets);
    }
    public void fixmouseUpTargetsNames(){
        mouseUpTargetsNames=getTargetNames(mouseUpEventTargets);
    }

    public void reloadMouseDragTargets(){
        loadTargets(mouseDragEventTargets, mouseDragTargetsNames);
    }
    public void reloadMouseUpTargets(){
        loadTargets(mouseUpEventTargets, mouseUpTargetsNames);
    }

    public void setMouseDragTargets(String t) {
        setTargets(mouseDragEventTargets, t);
    }
    public void setMouseUpTargets(String t) {
        setTargets(mouseUpEventTargets, t);
    }

    public void addMouseDragTarget(ConstructionObject o) {
        addTarget(mouseDragEventTargets, o);
    }
    public void addMouseUpTarget(ConstructionObject o) {
        addTarget(mouseUpEventTargets, o);
    }

    public void removeMouseDragTarget(ConstructionObject o) {
        removeTarget(mouseDragEventTargets, o);
    }
    public void removeMouseUpTarget(ConstructionObject o) {
        removeTarget(mouseUpEventTargets, o);
    }

    /*
     * Mouse Over Input Field (not implemented)
     */
    public void refreshMouseOverInputField() {
        if (currentInputField!=null) {
            currentInputField.setText(getMouseOverTargetNames());
        }
    }

//    public void setMouseOverTool(ZTextFieldAndLabel current) {
//        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
//        if (zc!=null) {
//            currentInputField=current;
//            PaletteManager.deselectgeomgroup();
//            zc.setTool(new Scripts_SetMouseOver(this));
//            setTargetSelected(zc, mouseOverEventTargets, true);
//        }
//    }

    public String getMouseOverTargetNames() {
        return getTargetNames(mouseOverEventTargets);
    }

//    public void setMouseOverTargets(String t) {
//        setTargets(mouseOverEventTargets, t);
//    }
//
    public void addMouseOverTarget(ConstructionObject o) {
        addTarget(mouseOverEventTargets, o);
    }

    public void removeMouseOverTarget(ConstructionObject o) {
        removeTarget(mouseOverEventTargets, o);
    }
    /*
     * End
     */

    private String getTargetNames(ArrayList<ConstructionObject> targets) {
        String names="";
        for (int i=0; i<targets.size(); i++) {
            names+=";"+targets.get(i).getName();
        }
        return names.replaceFirst(";", "");
    }

    private void loadTargets(ArrayList<ConstructionObject> targets, String t) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            targets.clear();
            String[] names=t.split(";");
            for (int i=0; i<names.length; i++) {
                ConstructionObject o=zc.getConstruction().find(names[i]);
                if (o!=null) {
                    targets.add(o);
                }
            }
        }
    }

    private void setTargets(ArrayList<ConstructionObject> targets, String t) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            setTargetSelected(zc, targets, false);
            loadTargets(targets,t);
            setTargetSelected(zc, targets, true);
        }
    }

    private void addTarget(ArrayList<ConstructionObject> targets, ConstructionObject o) {
        targets.add(o);
    }

    private void removeTarget(ArrayList<ConstructionObject> targets, ConstructionObject o) {
        targets.remove(o);
    }

    private void setTargetSelected(ZirkelCanvas zc, ArrayList<ConstructionObject> targets, boolean sel) {
        zc.clearSelected();
        for (int i=0; i<targets.size(); i++) {
            targets.get(i).setSelected(sel);
        }
        zc.repaint();
    }
}