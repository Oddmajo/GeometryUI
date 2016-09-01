/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.JZirkelCanvas;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.ImporterTopLevel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
public class ScriptThread extends Thread {

    private volatile Context CX=null;
    private volatile ScriptableObject SCOPE;
    private String SCRIPT="";
    private ScriptItem ITEM;
    private ZirkelCanvas ZC;
    private Construction C;
    private volatile ConstructionObject JSO=null; // only for InteractiveInput
    private volatile boolean validII=true; // only for InteractiveInput
    private volatile boolean isRunning=false;
    private volatile boolean isActionScript=false;
    private volatile boolean busyActionScript=false;
    private volatile boolean executeActionScript=false;
    private volatile boolean actionScriptInProgress=false;
    private volatile boolean killme=false;
    private volatile boolean stopme=false;
    private JSOuputConsole CONSOLE=new JSOuputConsole();

    public ScriptThread(ScriptItem item) {
        super();
        ITEM=item;
        ZC=JZirkelCanvas.getCurrentZC();
        C=ZC.getConstruction();
    }

    public ZirkelCanvas getZC() {
        return ZC;
    }

    public Construction getC() {
        return C;
    }

    public ScriptableObject getSCOPE() {
        return SCOPE;
    }

    public JSOuputConsole getCONSOLE() {
        return CONSOLE;
    }

    public void setFileName(String name) {
        ITEM.setFileName(name);
    }

    public String getFileName() {
        return ITEM.getFileName();
    }

    public void openEditor() {
        ITEM.openEditor();
    }

    // setJSO,getJSO,setValidII,getValidII only for InteractiveInput :
    public void setJSO(ConstructionObject o){
        JSO=o;
    }
    public ConstructionObject getJSO(){
        return JSO;
    }
    public void invalidII(){
        stopme=false;
        validII=false;
    }
    public boolean isValidII() {
        return validII;
    }

    @Override
    public void run() {
        try {
            isRunning=true;

            CX=Context.enter();
            CX.setOptimizationLevel(9);
            SCOPE = new ImporterTopLevel(CX);
            SCOPE.defineFunctionProperties(JSFunctions.getKeywords(), JSFunctions.class, ScriptableObject.DONTENUM);

            if (isActionScript) {
                Script scp=CX.compileString(SCRIPT, ITEM.getScriptName(),0, null);
                while (busyActionScript) {
                    if (executeActionScript) {
                        executeActionScript=false;
                        try {
                            scp.exec(CX, SCOPE);
//                            CX.evaluateString(SCOPE, SCRIPT, ITEM.getScriptName(), 0, null);
                        } catch (Error er) {
                        }
                    }
                }
            } else {
                try {
                    CX.evaluateString(SCOPE, SCRIPT, ITEM.getScriptName(), 0, null);
                } catch (Error er) {
                    System.out.println("error !");
                }
            }
        } catch (RhinoException e) {
            ITEM.sendErrorToEditor(e.getMessage());
        } catch (Exception e) {
        } finally {
            Context.exit();
            ZC.dovalidate();
            ZC.repaint();
            isRunning=false;
        }
    }


    public boolean isRunning() {
        return isRunning;
    }
    //ajout PM
    public boolean isStopped(){
	return stopme;
    }

    public void stopme() {
        if (isRunning) {
            stopme=true;
        }
    }

    public void restartme() {
        stopme=false;
    }

    public void killme() {
        validII=false; // Au cas où le kill intervient en plein InteractiveInput
        stopme=false;
        killme=true;
        interrupt();
    }

    public void runme() {
        SCRIPT=ITEM.getScriptSource();
	//ITEM.getPanel().Backup();
	JZirkelCanvas.getCurrentZC().getScriptsPanel().Backup();
        setPriority(Thread.MIN_PRIORITY);
        start();
    }

    /*****************************************
     * PARTIE RESERVEE AUX ACTION-SCRIPTS :
     *****************************************/
    public void runActionScript() {
        if (actionScriptInProgress) {
            executeActionScript=true;
        }
    }

    public void stopActionScript() {
        if (actionScriptInProgress) {
            busyActionScript=false;
            actionScriptInProgress=false;
        }
    }

    public void prepareActionScript(final String pointName) {
        isActionScript=true;
        busyActionScript=true;
        actionScriptInProgress=true;
        SCRIPT=ITEM.getScriptSource().replace("$name", pointName);
	ITEM.getPanel().Backup();
        setPriority(Thread.MAX_PRIORITY);
        start();
    }

    /***************************************************
     * APPELE UNE SEULE FOIS AU LANCEMENT DE L'APPLI :
     ***************************************************/
    static public void InitContextFactory() {
        ContextFactory.initGlobal(new ContextFactory() {

            @Override
            protected Context makeContext() {
                Context cx=super.makeContext();
                cx.setInstructionObserverThreshold(100);
                return cx;
            }

            @Override
            protected void observeInstructionCount(Context cx, int instructionCount) {
                ScriptThread th=(ScriptThread) Thread.currentThread();
                while (th.stopme) {
                }
                if (th.killme) {
                    Error er=new Error() {

                        @Override
                        public String getMessage() {
                            return "Script killed...";
                        }
                    };
                    throw er;
                }
                if (th.isActionScript) {
                    if (th.executeActionScript||!th.busyActionScript) {
                        Error er=new Error() {

                            @Override
                            public String getMessage() {
                                return "Action Script killed...";
                            }
                        };
                        throw er;
                    }
                }

            }
        });
    }
}
