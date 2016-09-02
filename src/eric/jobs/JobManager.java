/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import java.util.ArrayList;
import java.util.Enumeration;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.tools.DefineJobTool;

/**
 *
 * @author erichake
 */
public class JobManager {

    ZirkelCanvas ZC;
    private String target_names=null; // Only for loading process...
    private ArrayList<ConstructionObject> targets=new ArrayList<ConstructionObject>();
    private String backup=null;
    private String message_ok=Global.getParameter("job.message.ok", Global.Loc("job.message.ok"));
    private String message_failed=Global.getParameter("job.message.failed", Global.Loc("job.message.failed"));
    private boolean hidefinals=false;
    private boolean staticjob=false;
    private JobControlPanel controlPanel=null;
    private JobValidPanel validPanel=null;
    private boolean printExerciseArguments=true;
    private int ctrlX=3,ctrlY=3,ctrlW=374,ctrlH=162;
    private int validX,validY,validW=550,validH=36;

    public JobManager(ZirkelCanvas zc) {
        ZC=zc;
    }

    public void backup() {
        try {
            printExerciseArguments=false; // protect from recursively get Exercise arguments
            String file=FileTools.getCurrentFileSource();
            printExerciseArguments=true;
            // Compress the byte representation of the file :
            byte[] b=StringCompressionUtils.Compress(file.getBytes());
            // then save it into base64 format :
            backup=new String(Base64Coder.encode(b));
        } catch (Exception ex) {
            System.out.println("backup error");
        }
    }

    public void restore() {
        if (backup!=null) {
            try {
                String targets_backup=getTargetNames();
                // decode the base64 representation of the compressed file :
                byte[] b=Base64Coder.decode(backup);
                // decompress the file :
                byte[] b1=StringCompressionUtils.Decompress(b);
                // restore the construction :
                FileTools.setCurrentFileSource(new String(b1));
                setTargets(targets_backup);
                setTargetsColor(true);
                setHiddenToSuperHidden();

            } catch (Exception ex) {
            }
        }
    }

    public ArrayList<ConstructionObject> getTargets() {
        return targets;
    }

    public String getTargetNames() {
        String names="";
        for (int i=0; i<targets.size(); i++) {
            names+=";"+targets.get(i).getName();
        }
        return names.replaceFirst(";", "");
    }

    /* Two methods only for loading process :
     * 
     */
    public void setTargetNames(String t) {
        target_names=t;
    }

    public void setTargets() {
        if (target_names!=null) {
            setTargets(target_names);
            target_names=null;
            setTargetsColor(true);
            addValidPanel();
        }
    }

    public void setTargets(String t) {
        setTargetSelected(false);
        targets.clear();
        String[] names=t.split(";");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=ZC.getConstruction().find(names[i]);
            if (o!=null) {
                targets.add(o);
            }
        }
        setTargetSelected(true);
    }

    public void addTarget(ConstructionObject o) {
        targets.add(o);
    }

    public void removeTarget(ConstructionObject o) {
        targets.remove(o);
    }

    public void setTargetSelected(boolean sel) {
        for (int i=0; i<targets.size(); i++) {
            targets.get(i).setSelected(sel);
        }
        ZC.repaint();
    }

    public void setHiddenToSuperHidden() {
        Enumeration e=ZC.getConstruction().elements();
        while (e.hasMoreElements()) {
            ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c.isHidden(true)) {
                c.setSuperHidden(true);
            }
        }
        ZC.reloadCD();
    }

    public void setSuperHiddenToHidden() {
        Enumeration e=ZC.getConstruction().elements();
        while (e.hasMoreElements()) {
            ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c.isSuperHidden(true)) {
                c.setSuperHidden(false);
                c.setHidden(true);
            }
        }
        ZC.reloadCD();
    }

    public void setTargetsColor(boolean select) {
        Enumeration e=ZC.getConstruction().elements();
        while (e.hasMoreElements()) {
            ConstructionObject c=(ConstructionObject) e.nextElement();
            c.setJobTarget(false);
        }
        for (int i=0; i<targets.size(); i++) {
            targets.get(i).setSuperHidden(false);
        }

        if (select) {
            for (int i=0; i<targets.size(); i++) {
                targets.get(i).setJobTarget(true);
                targets.get(i).setSuperHidden(hidefinals);
            }
        }
        ZC.repaint();
    }

    /**
     * @return the message_ok
     */
    public String getMessage_ok() {
        return message_ok;
    }

    public void setMessage_ok(String mess_ok) {
        message_ok=mess_ok;
        Global.setParameter("job.message.ok", message_ok);
    }

    public String getMessage_failed() {
        return message_failed;
    }

    public void setMessage_failed(String mess_failed) {
        message_failed=mess_failed;
        Global.setParameter("job.message.failed", message_failed);
    }

    public boolean isStaticJob() {
        return staticjob;
    }

    public void setStaticJob(boolean b){
        staticjob=b;
    }

    public boolean isHidefinals() {
        return hidefinals;
    }

    public void setHidefinals(boolean hidef) {
        hidefinals=hidef;
    }

    public void setBackup(String s) {
        backup=s;
    }

    public void printArgs(final XmlWriter xml) {
        if ((targets.size()>0)&&(printExerciseArguments)) {
            xml.startTagStart("Exercise");
            xml.printArg("message_ok", message_ok);
            xml.printArg("message_failed", message_failed);
            xml.printArg("hidefinals", String.valueOf(hidefinals));
            xml.printArg("staticjob", String.valueOf(staticjob));
            xml.printArg("targets", getTargetNames());
            xml.printArg("backup", backup);
            xml.finishTagNewLine();
        }
    }

    /*******************************
     * GUI PART :
     *******************************/
    public void init() {
        if (controlPanel!=null) {
            controlPanel.init();
        } else if (validPanel!=null) {
            validPanel.init(ZC.getSize().width, ZC.getSize().height);
        }
    }

    public void addControlPanel() {
        removeControlPanel();
        controlPanel=new JobControlPanel(this,ctrlX,ctrlY,ctrlW,ctrlH);
        ZC.add(controlPanel,0);
        ZC.repaint();
        init();
    }

    public void removeControlPanel() {
        if (controlPanel!=null) {
            ctrlX=controlPanel.getLocation().x;
            ctrlY=controlPanel.getLocation().y;
            ZC.remove(controlPanel);
            controlPanel=null;
            ZC.repaint();
        }
    }

    public void addValidPanel() {
        removeValidPanel();
        if (targets.size()>0) {
            validPanel=new JobValidPanel(this,0,0,validW,validH);
            ZC.add(validPanel,0);
            ZC.repaint();
            init();
        }
    }

    public void removeValidPanel() {
        if (validPanel!=null) {
            ZC.remove(validPanel);
            validPanel=null;
            ZC.repaint();
        }
    }

    public void cancelControlDialog(){
        hideControlDialog(false);
    }

    public void hideControlDialog(boolean createJob) {
        setTargetSelected(false);
        removeControlPanel();
        pipe_tools.getContent().requestFocus();
        if (createJob&&targets.size()>0) {
            backup();
            addValidPanel();
            setHiddenToSuperHidden();
        } else {
            targets.clear();
            backup=null;
            setSuperHiddenToHidden();
        }
        setTargetsColor(true);
        
        PaletteManager.ClicOn("point");
    }

    public void showControlDialog() {
        removeValidPanel();
        restore();
        setTargetsColor(false);
        setSuperHiddenToHidden();
        addControlPanel();
        setTargetsField();
        setJobTool();
    }

    public void setJobTool() {
        setTargetSelected(true);
        PaletteManager.deselectgeomgroup();
        ZC.setTool(new DefineJobTool());
    }

    public void setTargetsField() {
        if (controlPanel!=null) {
            controlPanel.setTargetslist(getTargetNames());
        }
    }

    public void validate() {
        JobValidation v=new JobValidation(ZC);
        v.checkAllsteps();
    }
}
