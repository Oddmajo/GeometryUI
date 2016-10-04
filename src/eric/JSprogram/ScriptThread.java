/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.GUI.palette.JIcon;
import eric.GUI.palette.PaletteManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eric.JZirkelCanvas;
import java.util.ArrayList;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;
import ui.org.mozilla.javascript.Context;
import ui.org.mozilla.javascript.ContextFactory;
import ui.org.mozilla.javascript.ImporterTopLevel;
import ui.org.mozilla.javascript.RhinoException;
import ui.org.mozilla.javascript.Script;
import ui.org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author erichake with addons by Dibs
 */
public class ScriptThread extends Thread {

    private volatile Context CX=null;
    private volatile ScriptableObject SCOPE;
    private String SCRIPT="";
    private ScriptItem ITEM;
    private Pattern p;;
    private Matcher m;
    private boolean matchFound = false;
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
            Global.loadProperties(Global.getHomeDirectory()+"carmetal_config.txt");
            Global.initProperties();
            eric.JGlobalPreferences.initPreferences();
            PaletteManager.setSelected_with_clic("move",true);
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
        Global.saveProperties("CaR Properties");
    	SCRIPT=ITEM.getScriptSource();
        
        p = Pattern.compile("(^[\n;](?:\"[^\"]*\")*[^\"]*):=", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"=");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*):=", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"=");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
     	
        SCRIPT=SCRIPT.replace(",\"fin\")",",\"thin\")");
        SCRIPT=SCRIPT.replace(",\"épais\")",",\"thick\")"); 
        SCRIPT=SCRIPT.replace(",\"carré\")",",\"square\")");
        SCRIPT=SCRIPT.replace(",\"cercle\")",",\"circle\")");
        SCRIPT=SCRIPT.replace(",\"diamant\")",",\"diamond\")");
        SCRIPT=SCRIPT.replace(",\"croixPlus\")",",\"cross\")");
        SCRIPT=SCRIPT.replace(",\"croix\")",",\"dcross\")");
        
        SCRIPT=SCRIPT.replace(",\"vert\")",",\"green\")");
        SCRIPT=SCRIPT.replace(",\"bleu\")",",\"blue\")");
        SCRIPT=SCRIPT.replace(",\"marron\")",",\"brown\")");
        SCRIPT=SCRIPT.replace(",\"rouge\")",",\"red\")");
        SCRIPT=SCRIPT.replace(",\"noir\")",",\"black\")");
        SCRIPT=SCRIPT.replace(",\"fin\")",",\"thin\")");
        
        SCRIPT=SCRIPT.replace(",\"montrervaleur\")",",\"showvalue\")");
        SCRIPT=SCRIPT.replace(",\"montrernom\")",",\"showname\")");
        SCRIPT=SCRIPT.replace(",\"fond\")",",\"background\")");
        SCRIPT=SCRIPT.replace(",\"caché\")",",\"hidden\")");
        SCRIPT=SCRIPT.replace(",\"supercaché\")",",\"superhidden\")");
        
        SCRIPT=SCRIPT.replace(",\"Droite\")",",\"Line\")");
        SCRIPT=SCRIPT.replace(",\"Cercle\")",",\"Circle\")");
        
        p = Pattern.compile("(^[\n;](?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])vrai([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"true"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])vrai([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"true"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
                matchFound = m.find();
        }
        
        p = Pattern.compile("(^[\n;](?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])faux([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"false"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])faux([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"false"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
                matchFound = m.find();
        }
        
        
        p = Pattern.compile("^si ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        if (matchFound) {
        	SCRIPT= m.replaceAll("if ");
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])si ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"if ");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^sinon(\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("else"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])sinon([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"else"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^obliquer(\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("switch"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])obliquer([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"switch"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^cas([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("case"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])cas([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"case"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^rompre([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("break"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])rompre([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"break"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^par défaut([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("default"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])par défaut([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"default"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^pour ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        if (matchFound) {
        	SCRIPT= m.replaceAll("for ");
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])pour ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"for ");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*) allant de ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+" from ");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[^\"]*) à ", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+" to ");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^tant que (\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("while "+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^((?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])tant que([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"while"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^faire(\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("do"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])faire([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"do"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])jusqu'à ([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"until "+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])until *\\(([^;]+);", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"while (!("+String.valueOf(m.group(2))+")");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^fonction(\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("function"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])fonction([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"function"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("^retourner(\\W)", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=1) {
        	SCRIPT= m.replaceFirst("return"+String.valueOf(m.group(1)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("(^(?:\"[^\"]*\")*[^\"]*[\\W&&[^\"]])retourner([\\W&&[^\"]])", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=2) {
        	SCRIPT= m.replaceFirst(String.valueOf(m.group(1))+"return"+String.valueOf(m.group(2)));
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("for\\s+([a-zA-Z][a-zA-Z_0-9]*)\\s+from\\s+([a-zA-Z_0-9\\[\\]\\-\\+\\*/\\.\\(\\)]+)\\s+to\\s+([a-zA-Z_0-9\\[\\]\\-\\+\\*/\\.\\(\\)]+)\\s*\\{", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=3) {
        	SCRIPT= m.replaceFirst("for ("+m.group(1)+"="+String.valueOf(m.group(2))+";"+String.valueOf(m.group(1))+"<="+String.valueOf(m.group(3))+";"+String.valueOf(m.group(1))+"="+String.valueOf(m.group(1))+"+1){");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        p = Pattern.compile("for\\s+var\\s+([a-zA-Z][a-zA-Z_0-9]*)\\s+from\\s+([a-zA-Z_0-9\\[\\]\\-\\+\\*/\\.\\(\\)]+)\\s+to\\s+([a-zA-Z_0-9\\[\\]\\-\\+\\*/\\.\\(\\)]+)\\s*\\{", Pattern.MULTILINE);
        m = p.matcher(SCRIPT);
        matchFound = m.find();
        while (matchFound&&m.groupCount()>=3) {
        	SCRIPT= m.replaceFirst("for (var "+String.valueOf(m.group(1))+"="+String.valueOf(m.group(2))+";"+String.valueOf(m.group(1))+"<="+String.valueOf(m.group(3))+";"+String.valueOf(m.group(1))+"="+String.valueOf(m.group(1))+"+1){");
        	m = p.matcher(SCRIPT);
            matchFound = m.find();
        }
        System.out.println(SCRIPT);
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
