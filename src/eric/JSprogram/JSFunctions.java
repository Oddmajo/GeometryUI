/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.*;
import eric.GUI.pipe_tools;
import java.awt.Color;
import java.awt.FileDialog;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import rene.gui.Global;
import rene.util.FileName;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.expression.Expression;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.EquationXYObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.QuadricObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TextObject;
import rene.zirkel.objects.TwoPointLineObject;
import rene.zirkel.objects.VectorObject;
import ui.org.mozilla.javascript.Context;
import ui.org.mozilla.javascript.Scriptable;
import ui.org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author erichake
 */
public class JSFunctions {

    static String[] allnames={"cm", "Input", "Prompt", "Print", "Println", "Shownames", "Hidenames", "Hide", "Show",
        "Point", "PointOn", "ImplicitPlot", "Intersection", "Intersection2", "Intersections", "MidPoint", "Symmetry", "Line", "Segment", "Circle", "Parallel",
        "Perpendicular", "FixedCircle", "Move", "X", "Y", "GetExpressionValue", "Ray", "Angle", "Polygon", "Quadric",
        "CartesianFunction", "ParametricFunction", "SetColor", "SetText", "SetThickness", "SetFixed", "Vector", "SetRGBColor",
        "SetShowName", "SetShowValue", "SetFilled", "SetPartial", "Expression", "Text", "SetAlias", "SetMagneticObjects",
        "AddMagneticObject", "SetMagneticRay", "SetPointType", "InteractiveInput", "FixedSegment", "SetHide", "Pause",
        "Delete", "SetExpressionValue", "Reflection", "Translation", "PerpendicularBisector", "AngleBisector", "Circle3pts",
        "Arc3pts", "FixedAngle", "Circle3", "ExecuteMacro", "Alert", "Conditional", "Layer", "GetRed", "GetGreen", "GetBlue", "PenDown",
        "SetRed", "SetGreen", "SetBlue", "DPPoint", "DPLine", "DPSegment", "DPPerpendicular", "DPPerpendicularBisector",
        "DPMidPoint", "DPCircle", "DPReflexion", "DPSymmetry", "DPAngleBisector", "DPCommonPerpendicular", "DPRay",
        "getC", "getZC", "refreshZC", "Load", "Origin", "Extremity", "GetText", "ReflexAngle", "Exists", "SetIncrement",
        "GetOpenFile", "OrderedIntersection", "SetMinOpen", "SetMinClosed", "SetMaxOpen", "SetMaxClosed"};

    public static String[] getKeywords() {
        return allnames;
    }

    private static void setJSO(ConstructionObject o) {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        th.setJSO(o);
    }

    private static ConstructionObject getJSO() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getJSO();
    }

    private static boolean isValidII() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.isValidII();
    }

    private static void paint() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        th.getZC().repaint();
    }

    private static void addObject(ConstructionObject o) {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        th.getC().add(o);
        th.getZC().update_distant(o, 1);
    }

    public static void refreshZC() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        th.getZC().dovalidate();
        th.getZC().repaint();
    }

    public static Construction getC() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getC();
    }

    public static ZirkelCanvas getZC() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getZC();
    }

    private static ScriptThread getTHREAD() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th;
    }

    private static ScriptableObject getSCOPE() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getSCOPE();
    }

    private static JSOuputConsole getCONSOLE() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getCONSOLE();
    }

    /**
     * Read a file an return is content as a string.<br>
     * <br>
     * This function will be useful in javascript context.<br>
     * One will be able, for instance, to write inside a CaRScript :<br>
     * <i>eval(Load("mytools.js"));</i> <br>
     * This will behave as if the file "mytools.js" had been imported.<br>
     * The objects of "mytools.js" will be accessible within the CaRScript.<br>
     *
     * Load is more versatile than just an import function.<br>
     * Indeed, you have the opportunity to make some text processing
     * after loading and before evaluating :<br>
     *<i>MyTools = Load("mytools.js");</i><br>
     *<i>MyTools = doSomeModificationsTo(MyTools); // apply some regexps or whatever</i><br>
     *<i>eval(Mytools);</i><br>
     *
     * @param  The file to be read
     * @return The read text
     */
    public static String Load(String fic) {
        String FS=System.getProperty("file.separator");
        String myfic=Global.getHomeDirectory()+"scripts"+FS+fic.replace("/", FS);
        if (!new File(myfic).exists()) {
            myfic=FileName.path(JZirkelCanvas.getFileName())+FS+fic.replace("/", FS);
        }
        String r;
        String s="";
        boolean firstLine=true;
        try {
            java.io.BufferedReader f=new java.io.BufferedReader(new java.io.FileReader(myfic));
            try {
                while ((r=f.readLine())!=null) {
                    s=(firstLine==true)?r:s+System.getProperty("line.separator")+r;
                    firstLine=false;
                }
                f.close();
                return s;
            } catch (java.io.IOException e) {
                return s;
            }
        } catch (java.io.FileNotFoundException e) {
            return "";
        }

    }

    private static String parseVariables(String s) {
        String origin="";
        String endtxt=s.replaceAll("([0-9]+)e([0-9]+)", "$1E$2");
        do {
            origin=endtxt;
            StringBuffer sb1=new StringBuffer();
            Pattern pxy=Pattern.compile("(x|y)_(\\w*)", Pattern.CASE_INSENSITIVE);
            Matcher m=pxy.matcher(origin);
            while (m.find()) {
                Object x=getSCOPE().get(m.group(2), getSCOPE());
                if (x!=Scriptable.NOT_FOUND) {
                    m.appendReplacement(sb1, m.group(1)+"("+Context.toString(x)+")");
                }
            }
            m.appendTail(sb1);
            m.reset();
            StringBuffer sb2=new StringBuffer();
            Pattern pexp=Pattern.compile("_(\\w*)", Pattern.CASE_INSENSITIVE);
            m=pexp.matcher(sb1.toString());
            while (m.find()) {
                Object x=getSCOPE().get(m.group(1), getSCOPE());
                if (x!=Scriptable.NOT_FOUND) {
                    m.appendReplacement(sb2, Context.toString(x));
                }
            }
            m.appendTail(sb2);
            m.reset();
            endtxt=sb2.toString();
        } while (!(origin.equals(endtxt)));
        return endtxt;
    }

    static public String Loc(final String s) {
        return Global.Loc("JSerror."+s);
    }
    
    static public String LastNObjectsName(int N){
        if(N==0){
            return "";
        }
        String names;
        if(getC().lastButN(0)!=null){
            names = getC().lastButN(0).getName();
        } else {
            names = "";
        }       
        
        for(int i=1; i<N; i++){
            if(getC().lastButN(i)!=null){
                names = getC().lastButN(i).getName()+","+names;
            } else {
                return "";
            }
        }
        return names;
    }
//
//    static public String LastObjectName() {
//        if (getC().last()!=null) {
//            return getC().last().getName();
//        } else {
//            return "";
//        }
//    }
//
//    static public String Last2ObjectsName() {
//        if (getC().last()!=null&&getC().lastButOne()!=null) {
//            return getC().lastButOne().getName()+","+getC().last().getName();
//        } else {
//            return "";
//        }
//    }
//
//    static public String Last4ObjectsName() {
//        if (getC().last()!=null&&getC().lastButOne()!=null&&getC().lastButTwo()!=null&&getC().lastButThree()!=null) {
//            return getC().lastButThree().getName()+","+getC().lastButTwo().getName()+","+getC().lastButOne().getName()+","+getC().last().getName();
//        } else {
//            return "";
//        }
//    }

    synchronized static public void NormalizeLast() {
        ConstructionObject o=getC().lastButN(0);
        if (o!=null) {
            o.setColorType(ConstructionObject.NORMAL);
            o.setShowName(false);
            o.setShowValue(false);
            o.setFilled(false);
            o.setPartial(false);
        }
    }

    /**
     * Pause in milliseconds
     * @param pause time, in milliseconds
     */
    static public void Pause(int millis) {
        try {
            refreshZC();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print a string in a getCONSOLE(). Print calls won't generate carriage return
     * at the end of the output. If the getCONSOLE() is not visible, it will be
     * bring to front automatically.
     * @param a String to be displayed
     */
    static public void Print(String a) {
        if (!getCONSOLE().isVisible()) {
            getCONSOLE().setVisible(true);
        }
        getCONSOLE().print(parseVariables(a));
    }

    /**
     * Print a string in a getCONSOLE(). Println calls will generate carriage return
     * at the end of the output. If the getCONSOLE() is not visible, it will be
     * bring to front automatically.
     * @param a String to be displayed
     */
    static public void Println(String a) {
        if (!getCONSOLE().isVisible()) {
            getCONSOLE().setVisible(true);
        }
        getCONSOLE().println(parseVariables(a));
    }

    /**
     * Ask and wait for a data input. This will bring a modal dialog to front
     * and the script will be interrupted until the user presses the ok button.
     * @param msg Question to ask the user
     * @return String which was typed in the box (typically : answer to the question)
     */
    static public Object Input(String msg) {
        Object s=JOptionPane.showInputDialog(
                getZC(),
                msg,
                "Input dialog",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null, // c'est ouvert !!!
                ""); // valeur initiale
        return s;
    }

    /**
     * Ask for an OpenDialogBox. This will return the name of selected file if any.
     * Otherwise this will return "null" */
    static public Object GetOpenFile(String msg) {

        if (OS.isUnix()) {
            String name=null;
            final JFileChooser jfc=new JFileChooser(Global.getOpenSaveDirectory());
            jfc.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
            final int rep=jfc.showOpenDialog(pipe_tools.getFrame());
            if (rep==JFileChooser.APPROVE_OPTION) {
                name=jfc.getSelectedFile().getAbsolutePath();
            }
            return name;
        } else {
            String name="";
            FileDialog fd=new FileDialog(pipe_tools.getFrame(),
                    Global.Loc("filedialog.open"),
                    FileDialog.LOAD);

            fd.setDirectory(Global.getOpenSaveDirectory());
            fd.setSize(500, 400);
            fd.pack();
            fd.setVisible(true);
            if (fd.getFile()==null) {
                return null;
            }
            String path=(fd.getDirectory().endsWith(System.getProperty("file.separator")))?fd.getDirectory():fd.getDirectory()+System.getProperty("file.separator");
            path+=fd.getFile();
            return path;
        }
    }

    /**
     * This will stop the script execution until the user shows an object of
     * a given type in the current CaRMetal window. The message msg will be
     * displayed in the status bar (bottom of the window) and the name of the
     * object you clicked on will be return. If the user selects a tool in the
     * palette, the script execution will ends.
     * @param msg Message to be displayed in the status bar
     * @param type Type of the object. This can be : Point, Line,
     * Segment, Circle and Expression
     * @return Name of the selected object
     */
    static public Object InteractiveInput(String msg, String type) throws Error {
        synchronized (getC()) {
            setJSO(null);
            ObjectConstructor oc=getZC().getTool();
            getZC().setJSTool(getTHREAD(), msg, type);
            while (getJSO()==null) {
                if (!isValidII()) {
//                    /* JSmacroTool is running, but when user set an other tool,
//                     * we do not have to set the tool which has been saved some
//                     * lines above
//                     */
//                    oc=getZC().getTool();
//                    if (oc instanceof JSmacroTool) {
//                        oc=((JSmacroTool) oc).getPreviousTool();
//                    }
                    getZC().setTool(oc);
                    throw new Error();
                }
// v1 not working
//                if (!isValidII()) {
//                    //getZC().setTool(oc);
//
//		    if(getZC().getTool() instanceof JSmacroTool){
//			getZC().setTool(oc);
//		    } //else {
//			//getZC().setTool(getZC().getTool());
//		    //}
//                    throw new Error();
//                }
            }
            getZC().clearSelected();
            getZC().setTool(oc);
            return getJSO().getName();
        }
    }

    /**
     * Shows an alert message. This will stop the
     * script execution until the user presses on the ok button.
     * @param msg Message to be displayed
     */
    static public void Prompt(String msg) {
        JOptionPane.showMessageDialog(
                getZC(),
                parseVariables(msg),
                "Prompt dialog",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows an alert message. This will stop the
     * script execution until the user presses on the ok button.
     * @param msg Message to be displayed
     */
    static public void Alert(String msg) {
        JOptionPane.showMessageDialog(
                getZC(),
                parseVariables(msg),
                "Prompt dialog",
                JOptionPane.WARNING_MESSAGE);
    }

    static public void c(String a) {
        getZC().JSsend(a);
        getZC().update_distant(a);
    }

    static public String cm(String a) {
        getZC().JSsend(a);
        return LastNObjectsName(1);
    }

    /**
     * Moves a given object to an (x;y) position in the current CaRMetal window.
     * @param name Name of the object you want to move.
     * @param i x-coordinate
     * @param j y-coordinate
     */
    static public void Move(String name, String i, String j) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        if (!(o instanceof PointObject||o instanceof VectorObject)) {
            throw new Exception(Loc("notgoodtype"));
        }
        try {
            double x=Math.round(Double.valueOf(parseVariables(i))*1E13)/1E13;
            double y=Math.round(Double.valueOf(parseVariables(j))*1E13)/1E13;
            o.move(x, y);
            getZC().update_distant(o, 3);
        } catch (Exception e) {
            Expression expX=new Expression(parseVariables(i), o.getConstruction(), o);
            Expression expY=new Expression(parseVariables(j), o.getConstruction(), o);
            //o.move(expX.getValue(), expY.getValue());
            //pose problème: Si on essaye de mettre des coordonnées
            //algébriques (des expressions), on a NaN. Donc remplacé par
            o.setFixed(parseVariables(expX.toString()), parseVariables(expY.toString()));
        }

        refreshZC();
    }

    /**
     * Delete a given object in the current CaRMetal window.
     * @param name Name of the object you want to delete.
     */
    static public void Delete(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        getZC().delete(o);
    }

//  *******************************************************
//    Méthode retournant des informations sur les objets :
//  *******************************************************
    /**
     * Returns the value of a given expression which is in the construction.
     * Remember that "controls" are expressions too, so you can capture a
     * value of a control by this way.
     * @param name Name of an existing expression
     * @return Expression's numeric value.
     */
    static public double GetExpressionValue(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        if (!(o instanceof ExpressionObject)) {
            throw new Exception(Loc("notgoodtype"));
        }
        ExpressionObject oc=(ExpressionObject) o;
        double res=Double.NaN;
        try {
            res=oc.getValue();
        } catch (Exception ex) {
        }
        return res;
    }

    /**
     * Set the value of a given expression which is in the construction.
     * @param name Name of an existing expression
     * @param value Value you want to give to the expression
     */
    static public void SetExpressionValue(String name, String value) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        if (!(o instanceof ExpressionObject)) {
            throw new Exception(Loc("notgoodtype"));
        }
        ExpressionObject oc=(ExpressionObject) o;
        oc.setFixed(parseVariables(value));
        refreshZC();
    }

    /**
     * @author Alain
     * Tells if an object exists (and is finite).
     * @param name Name of the object
     * @return true or false depending on the object being valid.
     */
    static public Boolean Exists(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            return false;
        } else {
            return o.valid();
        }
    }

    /**
     * @author Alain
     * Returns the text value of a text object.
     * @param name Name of an existing text
     * @return the content of the text object.
     */
    static public String GetText(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        if (!(o instanceof TextObject)) {
            throw new Exception(Loc("notgoodtype"));
        }
        TextObject oc=(TextObject) o;
        String res="";
        try {
            res=oc.getText();
        } catch (Exception ex) {
        }
        return res;
    }

    /**
     * Set the value of a given text object which is in the construction.
     * @param name Name of a text object
     * @param value the new text
     */
    static public void SetText(String name, String value) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        if (!(o instanceof TextObject)) {
            throw new Exception(Loc("notgoodtype"));
        }
        o.setLines(parseVariables(value));
        refreshZC();
    }

    /**
     *Sets the track of an object.
     * @param name the name of the object
     * @param state the state of the track (true for a visible track)
     */
    static public void PenDown(String name, boolean state) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setTracked(state);
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Sets the red component of an object.
     * @param name the name of the object
     * @param amount the amount of red
     */
    static public void SetRed(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setRed(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Sets the green component of an object.
     * @param name the name of the object
     * @param amount the amount of green
     */
    static public void SetGreen(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setGreen(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Sets the blue component of an object.
     * @param name the name of the object
     * @param amount the amount of blue
     */
    static public void SetBlue(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setBlue(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Return the red component of an object.
     * @param name the name of the object
     * @return the red quantity, as an integer from 0 (no red) to 255
     */
    static public int GetRed(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getRed();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Return the green component of an object.
     * @param name the name of the object
     * @return the green quantity, as an integer from 0 (no green) to 255
     */
    static public int GetGreen(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getGreen();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Return the blue component of an object.
     * @param name the name of the object
     * @return the blue quantity, as an integer from 0 (no blue) to 255
     */
    static public int GetBlue(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getBlue();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Return the x-coordinate of an object. If no object is found, then it returns NaN.
     * @param name the name of the object
     * @return the numeric value of the x-coordinate
     */
    static public double X(String name) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o!=null) {
            if (o instanceof PrimitiveLineObject) {
                if (o instanceof VectorObject) {
                    final VectorObject toutDroit=(VectorObject) o;
                    return toutDroit.getDeltaX();
                } else {
                    final PrimitiveLineObject toutDroit=(PrimitiveLineObject) o;
                    return toutDroit.getDX();
                }
            } else {
                return o.getX();
            }
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     *Return the y-coordinate of an object. If no object is found, then it returns NaN.
     * @param name the name of the object
     * @return the numeric value of the y-coordinate
     */
    static public double Y(String name) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o!=null) {
            if (o instanceof PrimitiveLineObject) {
                if (o instanceof VectorObject) {
                    final VectorObject toutDroit=(VectorObject) o;
                    return toutDroit.getDeltaY();
                } else {
                    final PrimitiveLineObject toutDroit=(PrimitiveLineObject) o;
                    return toutDroit.getDY();
                }
            } else {
                return o.getY();
            }
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     * Return the origin of a vector. Works with a segment too
     * @param name the name of the vector
     * @return the name of the origin
     */
    static public String Origin(String name) throws Exception {
        ConstructionObject v=getC().find(name);
        if (v!=null) {
            if (v instanceof TwoPointLineObject) {
                final TwoPointLineObject toutDroit=(TwoPointLineObject) v;
                return toutDroit.getP1().getName();
            } else {
                throw new Exception(Loc("notgoodtype"));
            }
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

    /**
     * Return the extremity of a vector. Works with a segment too
     * @param name the name of the vector
     * @return the name of the extremity
     */
    static public String Extremity(String name) throws Exception {
        ConstructionObject v=getC().find(name);
        if (v!=null) {
            if (v instanceof TwoPointLineObject) {
                final TwoPointLineObject toutDroit=(TwoPointLineObject) v;
                return toutDroit.getP2().getName();
            } else {
                throw new Exception(Loc("notgoodtype"));
            }
        } else {
            throw new Exception(Loc("notfound"));
        }
    }

//  **********************************************
//    Modification des attributs des objets
//  **********************************************
    /**
     * Set the color of an object using one of the 6 predifined colors
     * of CaRMetal.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple object.
     * <br><b> Example</b> : <i><b>SetColor("A,B,c1,E1","cyan")</b></i> will give the "cyan" color
     * to the objects A, B, c1 and E1
     * @param name Name of the object you want to change color
     * @param col Name of the color. It can be "green", "blue", "brown", "cyan", "red" or "black".
     */
    static public void SetColor(String name, String col) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("color("+col+","+names[i]+")");
        }
    }

    /**
     *Set the shape of a point. It has no effect if the object is not a point.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple points.
     * <br><b> Example</b> : <i><b>SetPointType("A,B,E,F","square")</b></i> will give the "square" shape
     * to the points A, B, E and F
     * @param name Name of the point you want to change shape
     * @param type Name of the shape. It can be "square", "circle", "diamond", "point", "cross", "dcross".
     */
    static public void SetPointType(String name, String type) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("type("+type+","+names[i]+")");
        }
    }

    /**
     * Set the RGB color of an object using 3 integer numbers in [0..255].
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple object.
     * <br><b> Example</b> : <i><b>SetColor("A,B,c1",120,40,245)</b></i> will give the same color
     * to the objects A, B and c1. This color is defined by r=120, g=40 and b=245.
     * @param name Name of the object you want to change color
     * @param r Red value (integer in [0..255]
     * @param g Green value (integer in [0..255]
     * @param b Blue value (integer in [0..255]
     */
    static public void SetRGBColor(String name, int r, int g, int b) {
        Color mycolor=new Color(r, g, b);
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
//                o.setColor(0, mycolor);
                o.setSpecialColor(mycolor);
            }
        }
//        getZC().repaint();
//        validate();
//            paint()

    }

    /**
     * Set the thickness of an object.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple objects.
     * <br><b> Example</b> : <i><b>SetThickness("A,B,c1,l1","thick")</b></i> will give the "thick" aspect
     * to the objects A, B, c1 and l1
     * @param name Name of the point you want to change shape
     * @param thc Name of the thickness. It can be "thick", "normal" and "thin".
     */
    static public void SetThickness(String name, String thc) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("thickness("+thc+","+names[i]+")");
        }
    }

    /**
     * Set the alias name of an object.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple objects.
     * <br><b>Example</b> : <i><b>SetAlias("A,B,c1,l1","My beautiful object")</b></i>
     * will give the "My beautiful object" alias name to the objects A, B, c1 and l1
     * @param name Name of the point you want to change alias name.
     * @param alias Alias name.
     */
    static public void SetAlias(String name, String alias) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setAlias(parseVariables(alias));
            }
        }
    }

    /**
     * Set the increment of a point object.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple points.
     * <br><b>Example</b> : <i><b>SetIncrement("A,B,C",0.1)</b></i>
     * will set the increment of A, B, and C
     * @param name Name of the point you want to set the increment.
     * @param inc the increment.
     */
    static public void SetIncrement(String name, double delta) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o instanceof PointObject) {
                o.setIncrement(delta);
            }
        }
    }

    /**
     * Set the standard magnetic attraction lenght of a point
     * @param name Name of a point
     * @param ray Attraction lenght, in pixels (number or expression)
     * @see #SetMagneticObjects
     * @see #AddMagneticObject
     */
    static public void SetMagneticRay(String name, String ray) {
        ConstructionObject o=getC().find(name);
        if ((o==null)||(!(o instanceof PointObject))) {
            return;
        }
        PointObject pt=(PointObject) o;
        pt.setMagnetRayExp(parseVariables(ray));
        refreshZC();
    }

    /**
     * Set the objects that will magnetize a given point
     * <br><b>Example</b> : <i><b>SetMagneticObjects("P","c1,d1,A")</b></i> will
     * make the point P attracted by the objects c1, d1 and A.
     * <br> It's possible to define exceptions using the ":" separator.
     * If, for instance, objectlist contains "A,E,c1:20,l1" and the attraction
     * field is defined by 50 pixels, objects "A", "E" and "l1" have an attraction
     * radius of 50 pixels, but the object c1 will have an attraction radius of 20 pixels.
     * @param name Name of a point
     * @param objectlist List of objects
     * @see #AddMagneticObject
     * @see #SetMagneticRay
     */
    static public void SetMagneticObjects(String name, String objectlist) {
        ConstructionObject o=getC().find(name);
        if ((o==null)||(!(o instanceof PointObject))) {
            return;
        }
        PointObject pt=(PointObject) o;
        pt.setMagnetObjects(parseVariables(objectlist));
    }

    /**
     * Add an object to the list of magnetic objects of a given point.
     * @param name Name of the point
     * @param object Object to add to the magnetic point list
     * @see #SetMagneticObjects
     * @see #SetMagneticRay
     */
    static public void AddMagneticObject(String name, String object) {
        ConstructionObject o=getC().find(name);
        if ((o==null)||(!(o instanceof PointObject))) {
            return;
        }
        PointObject pt=(PointObject) o;
        pt.addMagnetObject(parseVariables(object));
    }

    /**
     * Fix or unfix an object in the CaRMetal current window. A fixed object
     * can't be moved with the mouse.
     * <br><b>Note</b> : It's possible to change this property in one step for
     * multiple objects.
     * <br><b>Example</b> : <i><b>SetFixed("A,B,c1,l1",true)</b></i>
     * will fix the objects A, B, c1 and l1 in the CaRMetal current window.
     * @param name Name of the object
     * @param bool "true" value to fix the object, "false" to unfix it.
     */
    static public void SetFixed(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setFixed(bool);
            }
        }
    }

    /**
     * Shows or hide the name of an object (or multiple objects).
     * <br><b>Example</b> : <i><b>SetShowName("A,B,c1,l1",true)</b></i>
     * will shows the names of objects A, B, c1 and l1.
     * @param name Name(s) of object.
     * @param bool "true" to show name, and "false" to hide it.
     */
    static public void SetShowName(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setShowName(bool);
            }
        }
    }

    /**
     * Shows or hide the value of an object (or multiple objects).
     * <br><b>Example</b> : <i><b>SetShowValue("A,B,c1,l1",true)</b></i>
     * will shows the value of objects A, B, c1 and l1.
     * @param name Name(s) of object.
     * @param bool "true" to show value, and "false" to hide it.
     */
    static public void SetShowValue(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setShowValue(bool);
            }
        }
    }

    /**
     * Some objects can be filled (circles, polygons, etc...). This will
     * fill or unfill an object (or multiple objects).
     * <br><b>Example</b> : <i><b>SetFilled("A,B,c1,l1",true)</b></i>
     * will fill objects A, B, c1 and l1.
     * @param name Name(s) of object.
     * @param bool "true" to fill objects, and "false" to unfill it.
     */
    static public void SetFilled(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setFilled(bool);
            }
        }
    }

    /**
     * Some objects can be drawn partially (circles, lines). This will
     * draw partially or not an object (or multiple objects).
     * <br><b>Example</b> : <i><b>SetPartial("A,B,c1,l1",true)</b></i>
     * will draw partially objects A, B, c1 and l1.
     * @param name Name(s) of object.
     * @param bool "true" to draw partially, and "false" to draw completly.
     */
    static public void SetPartial(String name, boolean bool) {

        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setPartial(bool);
                getC().updateCircleDep();
            }
        }
    }

    /**
     *After calling this command, names of new objects will be displayed.
     */
    static public void Shownames() {
        cm("shownames");
    }

    /**
     *After calling this command, names of new objects will not be displayed.
     */
    static public void Hidenames() {
        cm("hidenames");
    }

    /**
     * Hides an object (or multiple objects).
     * <br><b>Example</b> : <i><b>Hide("A,B,c1,l1",true)</b></i>
     * will hide objects A, B, c1 and l1.
     * @param name Name(s) of object(s).
     */
    static public void Hide(String name) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("hide(true,"+names[i]+")");
        }
        paint();
    }

    static public void SetHide(String name, boolean b) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("hide("+b+","+names[i]+")");
        }
        paint();
    }

    /**
     * Shows an object (or multiple objects).
     * <br><b>Example</b> : <i><b>Show("A,B,c1,l1",true)</b></i>
     * will show objects A, B, c1 and l1.
     * @param name Name(s) of object(s).
     */
    static public void Show(String name) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            c("hide(false,"+names[i]+")");
        }
        paint();
    }

//    static public void Delete(String name) {
//        String[] names=parseVariables(name).split(",");
//        for (int i=0; i<names.length; i++) {
//            ConstructionObject c=getC().find(names[i]);
//            if (c!=)
//
//            c("hide(false,"+names[i]+")");
//        }
//        paint()
//    }
//  **********************************************
//    Création des objets :
//  **********************************************
    /**
     * set a layer number for an object or list of objects
     * @param name Name(s) of object(s)
     * @param exp layer number (an expression); the lowest numbers are over the other ones
     */
    static public void Layer(String name, String exp) throws Exception {
        Conditional(name, "z", parseVariables(exp));//parseVariablé par Alain le 24 janvier 2012, à tester!
    }

    /**
     * Create a condition for an object or a list of objects
     * @param name Name(s) of object(s)
     * @param TAG type of condition this can be equal to :
     * solid, hidden, normal, thick, thin, black, green, blue,
     * cyan, red, brown, showvalue, showname, background, and superhidden
     * @param expTXT conditional expression to be apply
     * @return Name of the created point
     */
    static public void Conditional(String name, String TAG, String expTXT) throws Exception {
        try {
            String[] names=parseVariables(name).split(",");
            for (int i=0; i<names.length; i++) {
                ConstructionObject O=getC().find(names[i]);
                if (O!=null) {
                    O.clearConditional(TAG);
                    Expression e=new Expression(parseVariables(expTXT), getC(), O);
                    if (!e.isValid()) {
                        throw new Exception(Loc("condition"));
                    }
                    O.addConditional(TAG, e);
                }
            }
            getZC().recompute();
            getZC().validate();
            getZC().repaint();
        } catch (Exception ex) {
            throw new Exception(Loc("condition"));
        }
    }

    /**
     * Create a point at given coordinates. coordinates can be defined by numerical
     * values (e.g. Point("A",-1,5.2)) or using expressions (e.g. Point("C","x(B)+1","y(B)+1").
     * If the name is not given (e.g. Point("",2,-2.5)), the point will be created as well.
     * <br>From javascript, you can also do something like this :
     * <ol>
     * <li>a=Point("",1,2)</li>
     * <li>b=Point("",-2,3)</li>
     * <li>m=4</li>
     * <li>c=Point("","(x_a+x_b)/_m","(y_a+y_b)/_m")</li>
     * </ol>
     * The "_" symbol means that javascript must use the content of variables.
     * For example The string "_m" will be replaced by "4" (line 3).<br>Another
     * "shortcut" : if, for example, a contains "P1" (the real name of the point
     * created by step 1), the string x_a is equivalent to the string "x(P1)".
     * @param name Name of the point (suggestion)
     * @param x x-coordinate (number or expression)
     * @param y y-coordinate (number or expression)
     * @return Name of the created point
     */
    static public String Point(String name, String x, String y) throws Exception {
        PointObject pt=null;
        synchronized (getC()) {
            if (name.equals("undefined")) {
                double xx=getC().getX()+2*Math.random()*getC().getW()-getC().getW();
                double yy=getC().getY()-Math.random()*getC().getH()+getC().getH()/2;
                x=""+xx;
                y=""+yy;
                name="";
            } else if (y.equals("undefined")) {
                y=x;
                x=name;
                name="";
            }
            try {
                pt=new PointObject(getC(), Math.round(Double.valueOf(x)*1E13)/1E13, Math.round(Double.valueOf(y)*1E13)/1E13);
            } catch (Exception e) {
                try {
                    pt=new PointObject(getC(), 0, 0);
                    pt.setFixed(parseVariables(x), parseVariables(y));
                    pt.validCoordinates();


                } catch (Exception ex) {

                    throw new Exception(Loc("pointcoords"));
                }
            }
            pt.setDefaults();
            if (!name.equals("")) {
                pt.setName(parseVariables(name));
            }
            pt.setColorType(ConstructionObject.NORMAL);
            pt.setShowName(false);
            pt.setShowValue(false);
            pt.validate();
            addObject(pt);
            return pt.getName();
        }
    }

    /**
     * Set or Create a bound point
     * @param name Name of the bound point
     * @param obj Name of the object
     * @return Name of the set or created point
     */
    static public String PointOn(String name, String obj) {
        PointObject pt;
        synchronized (getC()) {
            if (obj.equals("undefined")) {
                obj=parseVariables(name);//à tester...
                pt=new PointObject(getC(), 0, 0);
                pt.setDefaults();
                addObject(pt);
            } else {
                ConstructionObject co=getC().find(name);
                if (co==null) {
                    pt=new PointObject(getC(), name);
                    pt.setDefaults();
                    addObject(pt);
                } else {
                    pt=(PointObject) co;
                }
            }
            pt.setBound(obj);
            pt.setUseAlpha(true); //pt will keep his barycentric coordinates relative to obj

            //to avoid the bound point being too near of an existing point
            final Enumeration e=getC().elements();
            while (e.hasMoreElements()) {
                ConstructionObject c=(ConstructionObject) e.nextElement();
                if (c instanceof PointObject) {
                    if ((pt.getX()-c.getX()<0.1)&&(pt.getY()-c.getY()<0.1)) {
                        pt.setXY(pt.getX()+Math.random()*2-1, pt.getY()+Math.random()*2-1);
                        break;
                    }
                }
            }
            return pt.getName();
        }
    }

    /**
     * Create the midpoint of two existing points
     * @param name Name of the midpoint
     * @param a Name of first point
     * @param b Name of second point
     * @return the midpoint name
     */
    static public String MidPoint(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("M("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"M("+a+","+b+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Reflect the point p with line d
     * @param name Name of the Symmetric
     * @param d Reflection line
     * @param p Point to relect
     * @return the reflection point name
     */
    static public String Reflection(String name, String d, String p) throws Exception {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=d;
                d=name;
                name="";
            }
            d=parseVariables(d);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/syma("+d+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/syma("+d+","+p+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Translate the point p with vector ab
     * @param name Name of the Translated point
     * @param a first point of the vector
     * @param b second point of the vector
     * @param p Point to translate
     * @return the translated point name
     */
    static public String Translation(String name, String a, String b, String p) throws Exception {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/trans("+a+","+b+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/trans("+a+","+b+","+p+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Reflect the point b with center a
     * @param name Name of the Reflect point
     * @param a Symmetry center
     * @param b Point to relect
     * @return the symmetric point name
     */
    static public String Symmetry(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("P(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"P(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Create the perpendicular bisector between two points a and b
     * @param name Name of the perpendicular bisector
     * @param a first point
     * @param b second point
     * @return the perpendicular bisector name
     */
    static public String PerpendicularBisector(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/med("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/med("+a+","+b+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Create the angle bisector between 3 points a, b and c
     * @param name Name of the perpendicular bisector
     * @param a first point
     * @param b second point
     * @param c third point
     * @return the angle bisector name
     */
    static public String AngleBisector(String name, String a, String b, String c) throws Exception {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("@builtin@/biss("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/biss("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Create the circumcircular circle between 3 points a, b and c
     * @param name Name of the perpendicular bisector
     * @param a first point
     * @param b second point
     * @param c third point
     * @return the circumcircular circle name
     */
    static public String Circle3pts(String name, String a, String b, String c) throws Exception {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("@builtin@/circ("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/circ("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Create the circle around a with radius bc
     * @param name Name of the circle
     * @param a center point
     * @param b first point
     * @param c second point
     * @return the circle name
     */
    static public String Circle3(String name, String a, String b, String c) throws Exception {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("c("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=c("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Create the circumcircular arc between 3 points a, b and c
     * @param name Name of the perpendicular bisector
     * @param a first point
     * @param b second point
     * @param c third point
     * @return the circumcircular arc name
     */
    static public String Arc3pts(String name, String a, String b, String c) throws Exception {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("@builtin@/arc("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/arc("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the intersection between two objects. If you provide a name
     * for the intersection, only one intersection point will be created.
     * If you give the empty string "" as <i>name</i> parameter, two intersection
     * points will be created, if the <i>a</i> and <i>b</i> objects are two circles
     * or line/circle.
     * @param name Name of the intersection point
     * @param a Name of first object
     * @param b Name of second object
     * @param order Number which gives the order of intersection : 0 means first
     * @return Name of intersection point
     * @see #Intersection2
     */
    static public String OrderedIntersection(String name, String a, String b, String order) throws Exception {
        synchronized (getC()) {
            if (order.equals("undefined")) {
                order=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            order=parseVariables(order);
            if (name.equals("")) {
                c("I("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"I("+a+","+b+")");
            }
            IntersectionObject i=(IntersectionObject) getC().lastButN(0);
            i.setFirst(Integer.valueOf(order)==0);
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the intersection between two objects. If you provide a name
     * for the intersection, only one intersection point will be created.
     * If you give the empty string "" as <i>name</i> parameter, two intersection
     * points will be created, if the <i>a</i> and <i>b</i> objects are two circles
     * or line/circle.
     * @param name Name of the intersection point
     * @param a Name of first object
     * @param b Name of second object
     * @return Name of intersection point
     * @see #Intersection2
     */
    static public String Intersection(String name, String a, String b) throws Exception {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("I("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"I("+a+","+b+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * When two objects have two intersection points (two circles or one line/one circle)
     * you may use this command to create them both.
     * @param name1 Name of first intersection point
     * @param name2 Name of second intersection point
     * @param a Name of first object
     * @param b Name of second object
     * @return Name of intersection point
     * @see #Intersection
     */
    static public String Intersection2(String name1, String name2, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name2;
                name2=name1;
                name1="";
                if (b.equals("undefined")) {
                    b=a;
                    a=name2;
                    name2="";
                }
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name1.equals("")) {
                c("I2,I1=I("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                c(name2+","+name1+"="+"I("+a+","+b+")");
            }
            return LastNObjectsName(2);
        }
    }

    /**
     * General case: The number of the intersection points depends
     * on the nature of the lines intersected
     * @param name of the first inetersection point
     * @param a Name of first object
     * @param b Name of second object
     * @return list of the intersection points in a String
     * @see #Intersection
     */
    static public String Intersections(String name, String a, String b) throws Exception {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("I("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"I("+a+","+b+")");
            }
            if (getC().find(a) instanceof PrimitiveLineObject&&getC().find(b) instanceof PrimitiveLineObject) {
                return LastNObjectsName(1);
            } else {
                if ((getC().find(a) instanceof PrimitiveCircleObject||getC().find(b) instanceof PrimitiveCircleObject)
                        ||(getC().find(a) instanceof PrimitiveLineObject&&getC().find(b) instanceof QuadricObject)
                        ||(getC().find(a) instanceof QuadricObject&&getC().find(b) instanceof PrimitiveLineObject)) {
                    return LastNObjectsName(2);
                } else {
                    if (getC().find(a) instanceof QuadricObject&&getC().find(b) instanceof QuadricObject) {
                        return LastNObjectsName(4);
                    } else {
                        if (getC().find(a) instanceof FunctionObject||getC().find(b) instanceof FunctionObject) {
                            return LastNObjectsName(1);
                        } else {
                            throw new Exception(Loc("notgoodtype"));
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a line between two points
     * @param name Name of the line (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created line
     */
    static public String Line(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("l("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"l("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a ray from point a to point b
     * @param name Name of the ray (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created ray
     */
    static public String Ray(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("r("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=r("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an angle defined by 3 points a,b and c (b is the vertex).
     * @param name Name of the angle (suggestion)
     * @param a Name of first point
     * @param b Name of the vertex
     * @param c Name of the third point
     * @return Name of the created angle
     */
    static public String Angle(String name, String a, String b, String c) {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("a("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=a("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an angle defined by 2 points a,b and an expression c (b is the vertex).
     * @param name Name of the angle (suggestion)
     * @param a Name of first point
     * @param b Name of the vertex
     * @param c angle in degree
     * @return Name of the created angle
     */
    static public String FixedAngle(String name, String a, String b, String c) {
        synchronized (getC()) {
            if (c.equals("undefined")) {
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            if (name.equals("")) {
                c("a("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=a("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Set angles as reflex or not.
     * <br><b>Example</b> : <i><b>ReflexAngle("a1,a2",true)</b></i>
     * @param name Name(s) of object.
     * @param bool "true" to set as reflex, "false" to have only angles less than 180°.
     */
    static public void ReflexAngle(String name, boolean bool) throws Exception {

        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (!(o instanceof AngleObject)) {
                throw new Exception(Loc("notgoodtype"));
            }
            if (o!=null&&true) {
                o.setObtuse(bool);
            }
        }
    }

    /**
     * Execute a macro-construction m with parameters params
     * @param lastObjName Name of last created object(s)
     * @param macroname Name of macro
     * @param params list of objects (e.g. "A,E,F,G")
     * @return Name of the last created object or the String array with all targets name
     */
    static public Object ExecuteMacro(String lastObjName, String macroname, String params) {
        synchronized (getC()) {
            if (params.equals("undefined")) {
                params=macroname;
                macroname=lastObjName;
                lastObjName="";
            }
            if (lastObjName.equals("")) {
                c(macroname+"("+parseVariables(params)+")");
                //getC().lastButN(0).setShowName(false);
            } else {
                c(parseVariables(lastObjName)+"="+macroname+"("+parseVariables(params)+")");
            }
            
            String[] TargetsNamesArray = MacroRunner.TargetsNameList.toArray(new String[MacroRunner.TargetsNameList.size()]);
            for (int i=0; i<TargetsNamesArray.length; i++) {
                getC().lastButN(i).setShowName(false);
            }
            
            if(TargetsNamesArray.length<2) {
                return TargetsNamesArray[0];
            } else {
                return TargetsNamesArray;
            }
        }
    }

    /**
     * Creates a polygon define by a list of points. e.g. Polygon("","A,B,C")
     * will create the triangle ABgetC().
     * @param name Name of the polygon (suggestion)
     * @param params list of objects
     * @return Name of the created polygon
     */
    static public String Polygon(String name, String params) {
        synchronized (getC()) {
            if (params.equals("undefined")) {
                params=name;
                name="";
            }
            String[] c=parseVariables(params).split(",");
            Vector V=new Vector();
            for (int i=0; i<c.length; i++) {
                ConstructionObject o=getC().find(c[i]);
                if (o!=null) {
                    V.add(o);
                }
            }
            AreaObject poly=new AreaObject(getC(), V);
            poly.setDefaults();
            if (!name.equals("")) {
                poly.setName(name);
            }
            poly.validate();
            addObject(poly);
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a quadric defined by five points
     * @param name Name of the quadric (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @param c Name of third point
     * @param d Name of fourth point
     * @param e Name of fifth point
     * @return Name of the created quadric
     */
    static public String Quadric(String name, String a, String b, String c, String d, String e) {
        synchronized (getC()) {
            if (e.equals("undefined")) {
                e=d;
                d=c;
                c=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            c=parseVariables(c);
            d=parseVariables(d);
            e=parseVariables(e);
            if (name.equals("")) {
                c("quadric("+a+","+b+","+c+","+d+","+e+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=quadric("+a+","+b+","+c+","+d+","+e+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the graph of a cartesian function with x variable in [a,b]
     * @param name Name of the function (suggestion)
     * @param a Minimum (number or expression)
     * @param b Maximum (number or expression)
     * @param fx expression of the function (variable x)
     * @return Name of the function
     */
    static public String CartesianFunction(String name, String a, String b, String fx) {
        synchronized (getC()) {
            if (fx.equals("undefined")) {
                fx=b;
                b=a;
                a=name;
                name="";
            }
            final FunctionObject f=new FunctionObject(getC());
            f.setDefaults();
            if (!name.equals("")) {
                f.setName(name);
            }
            f.setExpressions("x", "", parseVariables(fx));
            if ((!a.equals(""))&&(!b.equals(""))) {
                f.setRange(a, b, "0");
            }
            addObject(f);
            refreshZC();
            NormalizeLast();
            return f.getName();
        }
    }

    /**
     * Creates the graph of a parmetric function with t variable in [a,b]
     * @param name Name of the function (suggestion)
     * @param a Minimum (number or expression)
     * @param b Maximum (number or expression)
     * @param xt expression of x(t) (variable t)
     * @param yt expression of y(t) (variable t)
     * @return Name of the function
     */
    static public String ParametricFunction(String name, String a, String b, String xt, String yt) {
        synchronized (getC()) {
            if (yt.equals("undefined")) {
                yt=xt;
                xt=b;
                b=a;
                a=name;
                name="";
            }
            final FunctionObject f=new FunctionObject(getC());
            f.setDefaults();
            if (!name.equals("")) {
                f.setName(name);
            }
            f.setExpressions("t", parseVariables(xt), parseVariables(yt));
            if ((!a.equals(""))&&(!b.equals(""))) {
                f.setRange(a, b, "0");
            }
            addObject(f);
            refreshZC();
            NormalizeLast();
            return f.getName();
        }
    }

    static public void SetMinOpen(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMinOpen(bool);
            }
        }
    }

    static public void SetMinClosed(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMinClosed(bool);
            }
        }
    }

    static public void SetMaxOpen(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMaxOpen(bool);
            }
        }
    }

    static public void SetMaxClosed(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMaxClosed(bool);
            }
        }
    }

    /**
     * Creates an implicit curve as the set of zeroes of f (function of x and y)
     * @param name Name of the function (suggestion)
     * @param f expression (should depend on x and y)
     * @return Name of the function
     */
    static public String ImplicitPlot(String name, String f) {
        synchronized (getC()) {
            if (f.equals("undefined")) {
                f=name;
                name="";
            }
            final EquationXYObject e=new EquationXYObject(getC(), parseVariables(f));
            e.setDefaults();
            if (!name.equals("")) {
                e.setName(name);
            }
            addObject(e);
            refreshZC();
            NormalizeLast();
            return e.getName();
        }
    }

    /**
     * Creates a segment with center a and length r.
     * @param name Name of the segment (suggestion)
     * @param a Center point
     * @param r Length (number or expression)
     * @return Name of the segment
     */
    static public String FixedSegment(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            if (name.equals("")) {
                c("s("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"s("+a+","+r+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a segment between two points.
     * @param name Name of the segment (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created segment
     */
    static public String Segment(String name, String a, String b) {
        synchronized (getC()) {
            SegmentObject so=null;
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);

            try {
                so=new SegmentObject(getC(), (PointObject) getC().find(a), (PointObject) getC().find(b));
            } catch (Exception e) {
                return "";
            }
            so.setDefaults();
            if (!name.equals("")) {
                so.setName(parseVariables(name));
            }
            so.setArrow(false);
            so.setColorType(ConstructionObject.NORMAL);
            so.setShowName(false);
            so.setShowValue(false);
            so.validate();
            addObject(so);
            return so.getName();
        }

    }

    /**
     * Creates a vector between two points.
     * @param name Name of the vector (suggestion)
     * @param a Name of origin point
     * @param b Name of second point
     * @return Name of the created vector
     */
    static public String Vector(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            VectorObject vo=null;
            try {
                vo=new VectorObject(getC(), (PointObject) getC().find(a), (PointObject) getC().find(b));
            } catch (Exception e) {
                return "";
            }
            vo.setDefaults();
            if (!name.equals("")) {
                vo.setName(parseVariables(name));
            }
            vo.setColorType(ConstructionObject.NORMAL);
            vo.setShowName(false);
            vo.setShowValue(false);
            addObject(vo);
            return vo.getName();
        }
    }

    /**
     * Creates a circle with center a to point b.
     * @param name Name of the circle (suggestion)
     * @param a center point
     * @param b point of the circle
     * @return Name of the circle
     */
    static public String Circle(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("c("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"c("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a circle with center a and radius r.
     * @param name Name of the circle (suggestion)
     * @param a Center point
     * @param r Radius (number or expression)
     * @return Name of the circle
     */
    static public String FixedCircle(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            if (name.equals("")) {
                c("c("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"c("+a+","+r+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a parallel line through point pt to line lne.
     * @param name Name of the parallel line (suggestion)
     * @param lne Name of the line
     * @param pt Name of the point
     * @return Name of the parallel line
     */
    static public String Parallel(String name, String lne, String pt) {
        synchronized (getC()) {
            if (pt.equals("undefined")) {
                pt=lne;
                lne=name;
                name="";
            }
            lne=parseVariables(lne);
            pt=parseVariables(pt);
            if (name.equals("")) {
                c("par("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"par("+lne+","+pt+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates a perpendicular line through point pt to line lne.
     * @param name Name of the parallel line (suggestion)
     * @param lne Name of the line
     * @param pt Name of the point
     * @return Name of the perpendicular line
     */
    static public String Perpendicular(String name, String lne, String pt) {
        synchronized (getC()) {
            if (pt.equals("undefined")) {
                pt=lne;
                lne=name;
                name="";
            }
            lne=parseVariables(lne);
            pt=parseVariables(pt);
            if (name.equals("")) {
                c("perp("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+"perp("+lne+","+pt+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an expression.
     * @param name Name of expression (suggestion)
     * @param exp Expression
     * @param x x-coordinate of expression (number or expression)
     * @param y y-coordinate of expression (number or expression)
     * @return Name of expression
     */
    static public String Expression(String name, String exp, String x, String y) {
        synchronized (getC()) {
            if (y.equals("undefined")) {
                y=x;
                x=exp;
                exp=name;
                name="";
            }
            exp=parseVariables(exp);
            final ExpressionObject p=new ExpressionObject(getC(), 0, 0);
            p.setDefaults();
            if (!name.equals("")) {
                p.setName(name);
            }
            try {
                p.setExpression(exp, getC());
            } catch (final Exception e) {
                return "";
            }
            try {
                double xx=Math.round(Double.valueOf(x)*1E13)/1E13;
                double yy=Math.round(Double.valueOf(y)*1E13)/1E13;
                p.move(xx, yy);
            } catch (Exception e) {
                x=parseVariables(x);
                y=parseVariables(y);
                p.setFixed(x, y);
            }
            addObject(p);
            NormalizeLast();
            p.setShowValue(true);
            refreshZC();
            return p.getName();
        }
    }

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    // Complex numbers
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    // end of the complex numbers
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    // Hyperbolic Geometry in the Poincaré Disk (names beginning with "DP")
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    /**
     * Creates an hyperbolic point inside the Poincaré disk.
     * @param name Name of point (suggestion)
     * @return Name of point object
     */
    static public String DPPoint(String name) throws Exception {
        synchronized (getC()) {
            double xx=getC().getX()+2*Math.random()-1;
            double yy=getC().getY()+2*Math.random()-1;
            String x=""+xx;
            String y=""+yy;
            if (name.equals("undefined")) {
                name="";
            }
            PointObject pt=null;
            try {
                pt=new PointObject(getC(), Math.round(Double.valueOf(x)*1E13)/1E13, Math.round(Double.valueOf(y)*1E13)/1E13);
            } catch (Exception e) {
                try {
                    pt=new PointObject(getC(), 0.1, 0.01);
                    pt.setFixed(parseVariables(x), parseVariables(y));
                    pt.validCoordinates();


                } catch (Exception ex) {

                    throw new Exception(Loc("pointcoords"));
                }
            }
            pt.setDefaults();
            if (!name.equals("")) {
                pt.setName(name);
            }
            pt.setBound("HzBack");
            pt.setInside(true);
            pt.setColorType(ConstructionObject.NORMAL);
            pt.setShowName(false);
            pt.setShowValue(false);
            pt.validate();
            addObject(pt);
            return pt.getName();
        }
    }

    /**
     * Creates an hyperbolic line between two points
     * @param name Name of the line (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created hyperbolic line
     */
    static public String DPLine(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_line(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_line(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an hyperbolic segment (geodesic in the Poincaré disk) between two points
     * @param name Name of the segment (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created hyperbolic segment
     */
    static public String DPSegment(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_segment(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_segment(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an hyperbolic perpendicular line through a point
     * @param name Name of the perpendicular (suggestion)
     * @param l Name of the given hyperbolic linepoint
     * @param p Name of the point
     * @return Name of the created hyperbolic line
     */
    static public String DPPerpendicular(String name, String l, String p) {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=l;
                l=name;
                name="";
            }
            l=parseVariables(l);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/DP_plumb(Hz,"+l+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_plumb(Hz,"+l+","+p+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the perpendicular bisector of 2 points in the Poincaré disk
     * @param name Name of the line to create (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created perpendicular bisector
     */
    static public String DPPerpendicularBisector(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_bi_med(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_bi_med(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an hyperbolic midpoint
     * @param name Name of the midpoint (suggestion)
     * @param a Name of first point
     * @param b Name of second point
     * @return Name of the created midpoint
     */
    static public String DPMidPoint(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_midpoint(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_midpoint(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an hyperbolic circle in the Poincaré disk
     * @param name Name of the circle (suggestion)
     * @param a Name of center (a point)
     * @param b Name of a point on the circle
     * @return Name of the created hyperbolic circle
     */
    static public String DPCircle(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_circle(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_circle(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the image of a point in an hyperbolic mirror
     * @param name Name of the image (suggestion)
     * @param l Name of mirror (an hyperbolic line)
     * @param p Name of point
     * @return Name of the created image
     */
    static public String DPReflexion(String name, String l, String p) {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=l;
                l=name;
                name="";
            }
            l=parseVariables(l);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/DP_bi_syma(Hz,"+l+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_bi_syma(Hz,"+l+","+p+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the hyperbolic symmetric of a point behind another
     * @param name Name of the symmetric (suggestion)
     * @param a Name of center (a point)
     * @param b Name of the point
     * @return Name of the created symmetric
     */
    static public String DPSymmetry(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_bi_symc(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_bi_symc(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an angular bisector in the hyperbolic Poincaré disk
     * @param name Name of the bisector (suggestion)
     * @param a Name of first point
     * @param o Name of vertex
     * @param b Name of second point
     * @return Name of the bisector (an hyperbolic ray)
     */
    static public String DPAngleBisector(String name, String a, String o, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=o;
                o=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            o=parseVariables(o);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_bi_biss(Hz,"+a+","+o+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_bi_biss(Hz,"+a+","+o+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the hyperbolic line which cuts 2 given lines at right angle
     * @param name Name of the line (suggestion)
     * @param a Name of first line
     * @param b Name of second line
     * @return Name of the created hyperbolic common perpendicular
     */
    static public String DPCommonPerpendicular(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_bi_perp_common(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_bi_perp_common(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates an hyperbolic ray
     * @param name Name of the ray (suggestion)
     * @param a Name of vertex
     * @param b Name of a point on the ray
     * @return Name of the created hyperbolic ray
     */
    static public String DPRay(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
                c("@builtin@/DP_ray(Hz,"+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/DP_ray(Hz,"+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    // End of the hyperbolic Geometry in the Poincaré Disk
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    /**
     * Creates a text.
     * @param name Name of text object (suggestion)
     * @param txt text to create (String)
     * @param x x-coordinate of text (number or expression)
     * @param y y-coordinate of text (number or expression)
     * @return Name of text object
     */
    static public String Text(String name, String txt, String x, String y) {
        synchronized (getC()) {
            if (y.equals("undefined")) {
                y=x;
                x=txt;
                txt=name;
                name="";
            }
            final TextObject p=new TextObject(getC(), 0, 0);
            p.setDefaults();
            if (!name.equals("")) {
                p.setName(name);
            }
            p.setLines(txt);
            try {
                double xx=Math.round(Double.valueOf(x)*1E13)/1E13;
                double yy=Math.round(Double.valueOf(y)*1E13)/1E13;
                p.move(xx, yy);
            } catch (Exception e) {
                x=parseVariables(x);
                y=parseVariables(y);
                p.setFixed(x, y);
            }
            addObject(p);
            NormalizeLast();
            p.setShowValue(true);
            refreshZC();
            return p.getName();
        }
    }
}
