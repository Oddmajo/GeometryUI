/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JConsole2.java
 *
 * Created on 20 avr. 2009, 08:24:41
 * by Éric Hakenholz,
 * with somme addons by Alain Busser
 */
package eric.JSprogram;

import eric.JBrowserLauncher;
import eric.JZirkelCanvas;
import eric.OS;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JSEditor extends javax.swing.JFrame {

//    final UndoManager undo=new UndoManager();
    StyledDocument doc;
    final int ctrlkey=(OS.isMac())?InputEvent.META_DOWN_MASK
            :InputEvent.CTRL_DOWN_MASK;
    private boolean NoTypeNoClic=true;
    UndoManager undo;
    private Point origin;
    private Point winloc;
    private Point current;
    private int xx;
    private int yy;
    private int w;
    private int h;
    private int JSsel=0;
    protected static int TailleTexte=16;
    private String filename=""; // script in file
    private String scriptname=""; // script in figure
    private ScriptItem ITEM;
    private boolean FlagSimp=false; // Dibs : drapeau interface simplifiée
    boolean infrench = true; // Dibs

    /** Creates new form JConsole2 */
    public JSEditor(ScriptItem item) {
    	infrench = Global.getParameter("language", "").equals("fr");
        ITEM=item;
        initComponents();
        doc=script_area.getStyledDocument();
        initCaRMetalBtns();
        initScriptArea();
        initUndoRedo();
        initJSlist();
        setWindowTitle(Loc("JSeditor.title"));
        if ((OS.isMac())) {
            fixsize(macpanel, 1, 1);
            winpanel.removeAll();
            winpanel.add(title_lbl);
            winpanel.revalidate();
        } else {
            fixsize(winpanel, 1, 1);
        }
        this.setLocationRelativeTo(JZirkelCanvas.getCurrentZC());
        errorpanel.removeAll();
        errorpanel.revalidate();

        setTabs(script_area, 3);
        setSize(760, 530);

        // Show tool tips immediately
        ToolTipManager.sharedInstance().setInitialDelay(50);
        setVisible(true);


    }

    public void setBackBtnEnabled(boolean bool) {
        backbtn.setEnabled(bool);
    }

    public void setScriptName(String s) {
        scriptname=s;
    }

    public String getScriptName() {
        return scriptname;
    }

    public void setFileName(String s) {
        filename=s;
    }

    public String getFileName() {
        return filename;
    }

    public void setWindowTitle(String s) {
        title_lbl.setText(s);
    }

    public String getWindowTitle() {
        return title_lbl.getText();
    }

    public boolean isFileOpened() {
        return !(filename.equals(""));
    }

    private void initJSlist() {
    }

    static void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void NoTypeNoClic() {
        NoTypeNoClic=true;
    }

    public void addOrChange(String st) {
        if (NoTypeNoClic) {
            undo.undo();
        } else {
            NoTypeNoClic();
        }
        addToScript(st);
    }

    public void addToScript(String st) {
        script_area.requestFocus();
        script_area.replaceSelection(st);
//        syntaxColoring();
        script_area.requestFocus();
    }

    public void clearSelection() {
        script_area.replaceSelection("");
        script_area.requestFocus();
    }

    public JSIcon addCMicon(String obj, String syntax) {
        JSIcon jsi=new JSIcon(this, obj, syntax);
        c_carmetal.add(jsi);
        return jsi;
    }

    /**
     *
     * @param obj
     * @param example
     * @return
     */
    public JSButton addJSIcon(String obj, final String example) {
        JSButton jb=new JSButton(obj, 24, true);
        jb.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                addToScript(example);
            }
        });
        js_btns.add(jb);
        return jb;
    }

    public void addBlankIcon(int nb, int size) {
        for (int i=0; i<nb; i++) {
            c_carmetal.add(new JSBlankIcon(size));
        }
    }

    public void addSeparator(int nb) {

        javax.swing.JSeparator jSeparator1=new javax.swing.JSeparator();

        jSeparator1.setPreferredSize(new java.awt.Dimension(170, nb));
        jSeparator1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c_carmetal.add(jSeparator1);

    }

    public void syntaxColoring() {
        String[] keynames={"for", "from", "allant de", "to", "à", "until", "jusqu'à", "pour", "while", "tant que", "if", "si", "else", "sinon", "do", "faire", "new", "function", "fonction",
            "true", "false", "vrai", "faux", "var", "return", "renvoyer", "switch", "obliquer", "case", "cas", "break", "rompre", "default", "par défaut", "try",
            "catch", "++", "--", "valueOf", "length", "{", "}"};
        String[] mathkeynames={"Math.PI", "Math.sin", "Math.cos", "Math.tan", "Math.asin", "Math.acos", "Math.atan",
            "Math.abs", "Math.ceil", "Math.round", "Math.min", "Math.max", "Math.floor",
            "Math.PI", "Math.E", "Math.pow", "Math.exp", "Math.log", "Math.sqrt",
            "Math.LN10", "Math.random", "Math.LN2", "Math.LOG2E", "Math.SQRT2", "Math.SQRT1_2"};

        String scp="\n"+script_area.getText()+"\n";
//        scp=scp.replace("(", "@").replace(")", "@");

        //CaRMetal javascript instructions coloring :
        String[] allnames=JSFunctions.getKeywords();
        ArrayList<Integer> carmetal_matches=new ArrayList<Integer>();
        for (int i=1; i<allnames.length; i++) {
            Matcher m=Pattern.compile("\\W{1}(\\Q"+allnames[i]+"\\E)\\W{1}", Pattern.MULTILINE).matcher(scp);
            while (m.find()) {
                carmetal_matches.add(m.start(1)-1);
                carmetal_matches.add(m.end(1)-1);
            }
        }

        //Standard javascript instructions coloring :
        ArrayList<Integer> js_matches=new ArrayList<Integer>();
        for (int i=0; i<keynames.length; i++) {
            Matcher m=Pattern.compile("\\W{1}(\\Q"+keynames[i]+"\\E)\\W{1}", Pattern.MULTILINE).matcher(scp);
            while (m.find()) {
                js_matches.add(m.start(1)-1);
                js_matches.add(m.end(1)-1);
            }
        }

        //Standard Math object javascript instructions coloring :
        ArrayList<Integer> js_maths_matches=new ArrayList<Integer>();
        for (int i=0; i<mathkeynames.length; i++) {
            Matcher m=Pattern.compile("\\W{1}(\\Q"+mathkeynames[i]+"\\E)\\W{1}", Pattern.MULTILINE).matcher(scp);
            while (m.find()) {
                js_maths_matches.add(m.start(1)-1);
                js_maths_matches.add(m.end(1)-1);
            }
        }

        //Comment  coloring :
        ArrayList<Integer> js_comments=new ArrayList<Integer>();
//        Matcher m = Pattern.compile("[\n\t]+(\\Q//\\E[^\n]*)", Pattern.MULTILINE).matcher(scp);
        Matcher m=Pattern.compile("(\\Q//\\E[^\n]*)", Pattern.MULTILINE).matcher(scp);
        while (m.find()) {
            js_comments.add(m.start(1)-1);
            js_comments.add(m.end(1)-1);
//                System.out.println("start="+m.start(1)+"  end="+m.end(1));
//        m = Pattern.compile("/\\*([^*]|\\*+[^*/])*\\*+/").matcher(scp);
//        m = Pattern.compile("(\\/\\*.*\\*\\/)").matcher(scp);
//        while (m.find()) {
//            js_comments.add(m.start(1) - 1);
//            js_comments.add(m.end(1) - 1);
//                marche pas, je sais pas pourquoi
        }
//        }

        // Text coloring :
        ArrayList<Integer> text_matches=new ArrayList<Integer>();
        m=Pattern.compile("\"([^\"]*)\"", Pattern.MULTILINE).matcher(scp);
        while (m.find()) {
            text_matches.add(m.start(1)-1);
            text_matches.add(m.end(1)-1);
//                System.out.println("start="+m.start(1)+"  end="+m.end(1));
        }

        // Number coloring :
        ArrayList<Integer> number_matches=new ArrayList<Integer>();
//        m = Pattern.compile("[\\+\\-]?\\d+(\\.\\d*)?([Ee][\\+\\-]?\\d+)?", Pattern.MULTILINE).matcher(scp);
        m=Pattern.compile("([0-9\\.]+)", Pattern.MULTILINE).matcher(scp);
//        m = Pattern.compile("([0-9\\.\\+\\-]+)", Pattern.MULTILINE).matcher(scp);
        while (m.find()) {
            number_matches.add(m.start(1)-1);
            number_matches.add(m.end(1)-1);
//                System.out.println("start="+m.start(1)+"  end="+m.end(1));
        }

        doc.setCharacterAttributes(0, script_area.getText().length(), script_area.getStyle("Normal"), true);

        for (int i=0; i<carmetal_matches.size(); i+=2) {
            int start=carmetal_matches.get(i);
            int end=carmetal_matches.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("CMkeyword"), true);
        }

        for (int i=0; i<js_matches.size(); i+=2) {
            int start=js_matches.get(i);
            int end=js_matches.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("Blue"), true);
        }

        for (int i=0; i<js_maths_matches.size(); i+=2) {
            int start=js_maths_matches.get(i);
            int end=js_maths_matches.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("Math_keyword"), true);
        }

        for (int i=0; i<number_matches.size(); i+=2) {
            int start=number_matches.get(i);
            int end=number_matches.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("Red"), true);
        }

        for (int i=0; i<text_matches.size(); i+=2) {
            int start=text_matches.get(i);
            int end=text_matches.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("Green"), true);
        }

        for (int i=0; i<js_comments.size(); i+=2) {
            int start=js_comments.get(i);
            int end=js_comments.get(i+1);
            doc.setCharacterAttributes(start, end-start, script_area.getStyle("Comment"), true);
        }

    }

    public void clearStatusBar() {
        errorpanel.removeAll();
        errorpanel.revalidate();
        errorpanel.repaint();
    }

    public void initScriptArea() {
        // unbelievable... with those two lines, windows understands what
        // symbol it must use as carriage return :
        script_area.setText("\n");
        script_area.setText("");



        Style style=script_area.addStyle("Normal", null);
        StyleConstants.setForeground(style, new Color(70, 70, 70));

        style=script_area.addStyle("CMkeyword", null);
        StyleConstants.setForeground(style, new Color(143, 69, 0));
        StyleConstants.setBold(style, true);

        style=script_area.addStyle("Math_keyword", null);
        StyleConstants.setForeground(style, new Color(194, 151, 0));
        StyleConstants.setBold(style, true);

        // Makes text green
        style=script_area.addStyle("Green", null);
        StyleConstants.setForeground(style, new Color(0, 153, 116));
        StyleConstants.setBold(style, true);

        // Makes text blue
        style=script_area.addStyle("Blue", null);
        StyleConstants.setForeground(style, new Color(0, 95, 163));
        StyleConstants.setBold(style, true);

        // Makes comment text
        style=script_area.addStyle("Comment", null);
        StyleConstants.setForeground(style, new Color(50, 150, 250));
        StyleConstants.setItalic(style, true);


        // Makes text red
        style=script_area.addStyle("Red", null);
        StyleConstants.setForeground(style, Color.red);

        // Inherits from "Red"; makes text red and underlined
        style=script_area.addStyle("Red Underline", style);
        StyleConstants.setUnderline(style, true);

        // Makes text 24pts
        style=script_area.addStyle("24pts", null);
        StyleConstants.setFontSize(style, 24);

        // Makes text 12pts
        style=script_area.addStyle("12pts", null);
        StyleConstants.setFontSize(style, 12);

        // Makes text italicized
        style=script_area.addStyle("Italic", null);
        StyleConstants.setItalic(style, true);

        // A style can have multiple attributes; this one makes text bold and italic
        style=script_area.addStyle("Bold Italic", null);
        StyleConstants.setBold(style, true);
        StyleConstants.setItalic(style, true);

        script_area.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        syntaxColoring();
                    }
                });
                ITEM.setScriptSource(script_area.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        syntaxColoring();
                    }
                });
                ITEM.setScriptSource(script_area.getText());
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });


        script_area.setFont(new Font("monospaced", Font.PLAIN, TailleTexte));//taille script
        script_area.setMargin(new Insets(0, 5, 0, 0));


        KeyStroke enter=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        script_area.getInputMap().put(enter, "enterCommand");
        script_area.getActionMap().put("enterCommand",
                new AbstractAction("enterCommand") {

                    public void actionPerformed(ActionEvent evt) {
                        FindDelimiters();
                        int t=tabIndent(script_area.getCaretPosition());
                        script_area.replaceSelection("\n"+tabs(t));
                    }
                });

        KeyStroke comment=KeyStroke.getKeyStroke(KeyEvent.VK_C, ctrlkey+KeyEvent.SHIFT_DOWN_MASK);
        script_area.getInputMap().put(comment, "commentCommand");
        script_area.getActionMap().put("commentCommand",
                new AbstractAction("commentCommand") {

                    public void actionPerformed(ActionEvent evt) {
                        commentSelection();
                    }
                });

        jScrollPane1.setRowHeaderView(new LineNumberView(script_area));


    }

    public String getScript() {
        return script_area.getText();
    }

    public void setScriptArea(String s) {
        script_area.setText(s);
        Format();
    }
    private char endline="\n".charAt(0);

    private boolean firstLineCar(int pos) {
        if (pos==0) {
            return true;
        }
        return (script_area.getText().charAt(pos-1)==endline);
    }

    public int linestartAt(int pos) {
        while (!firstLineCar(pos)) {
            pos--;
        }
        return pos;
    }

    private boolean lastLineCar(int pos) {
        if (pos==script_area.getText().length()) {
            return true;
        }
        return (script_area.getText().charAt(pos)==endline);
    }

    public int lineendAt(int pos) {
        while (!lastLineCar(pos)) {
            pos++;
        }
        return pos;
    }

    public void commentSelection() {
        int caret=script_area.getCaretPosition();
        int start=script_area.getSelectionStart();
        int end=script_area.getSelectionEnd();
        script_area.setSelectionStart(linestartAt(start));
        script_area.setSelectionEnd(lineendAt(end));
        String mystr=script_area.getSelectedText();
        int caretoffs=2;
        if (mystr==null) {
            mystr="";
        }
        mystr="\n"+mystr;
        if (Pattern.compile("\n([ \t]*)\\Q//\\E", Pattern.MULTILINE).matcher(mystr).find()) {
            mystr=Pattern.compile("\n([ \t]*)\\Q//\\E", Pattern.MULTILINE).matcher(mystr).replaceAll("\n$1");
            caretoffs=-2;
        } else {
            mystr=mystr.replaceAll("\n", "\n//");
        }
        mystr=mystr.substring(1);
        script_area.replaceSelection(mystr);
        script_area.setCaretPosition(caret+caretoffs);
    }

    public void initCaRMetalBtns() {
    	infrench = System.getProperty("user.language").equals("fr");
//        "point", "intersection", "midpoint", "bi_syma",
//				"bi_symc", "bi_trans", "line", "ray", "parallel", "plumb",
//				"bi_med", "bi_biss", "segment", "fixedsegment", "vector",
//				"area", "angle", "fixedangle", "circle", "fixedcircle",
//				"circle3", "bi_circ", "bi_arc", "quadric"
    	if (FlagSimp&&infrench) {
        addCMicon("point", "Point(<name,null>,<nb,var,exp>,<nb,var,exp>);");
        addCMicon("intersection", "Intersection(<name,null>,<name,var>,<name,var>);");
        addCMicon("midpoint", "Milieu(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_syma", "SymétrieAxiale(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_symc", "SymétrieCentrale(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_trans", "Translation(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("line", "Droite(<name,null>,<name,var>,<name,var>);");
        addCMicon("ray", "DemiDroite(<name,null>,<name,var>,<name,var>);");
        addCMicon("parallel", "Parallèle(<name,null>,<name,var>,<name,var>);");
        addCMicon("plumb", "Perpendiculaire(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_med", "Médiatrice(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_biss", "Bissectrice(<name,var>,<name,var>,<name,var>,<name,var>);");
        addCMicon("segment", "Segment(<name,null>,<name,var>,<name,var>);");
        addCMicon("fixedsegment", "SegmentFixe(<name,null>,<name,var>,<nb,var>);");
        addCMicon("vector", "Vecteur(<name,null>,<name,var>,<name,var>);");
        addCMicon("area", "Polygone(<name,null>,<objs>);");
        addCMicon("angle", "Angle(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("fixedangle", "AngleFixe(<name,null>,<name,var>,<name,var>,<nb,var,exp>);");
        addCMicon("circle", "Cercle(<name,null>,<name,var>,<name,var>);");
        addCMicon("fixedcircle", "CercleRayon(<name,null>,<name,var>,<nb,exp>);");
        addCMicon("circle3", "Cercle3(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("bi_circ", "Cercle3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("bi_arc", "Arc3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("quadric", "Quadrique(<name,null>,<name,var>,<name,var>,<name,var>,<name,var>,<name,var>);");
//        addCMicon("text", "");
        addCMicon("expression", "Expression(<name,null>,\"Votre expression\",<nb>,<nb>);");
        addCMicon("text", "Texte(<name,null>,\"Votre message\",<nb>,<nb>);");
//        addCMicon("image3", "");
//        addBlankIcon(3);
        addCMicon("function", "FonctionCartésienne(<name,null>,-5,5,\"x^2-2*x+1\");");
        addCMicon("parametricfunction", "FonctionParamétrique(<name,null>,-3.14,3.14,\"3*rcos(t)^3\",\"3*rsin(t)^3\");");
        addCMicon("equationxy", "tracéImplicite(<name,null>,\"x^3-2*x-y^2+1\");");
        addCMicon("macro", "ExécuterMacro(<name,null>,\"_x,_y,_z\");");
        
        addSeparator(1);
        addCMicon("bi_3Dcoords", "Point3D(<name,null>,<nb,var,exp>,<nb,var,exp>,<nb,var,exp>);");
        addCMicon("bi_3Dproj", "Projection3D(<name,null>,\"C,D,E\",<name,var>);");
        addCMicon("bi_3Dsymc", "SymétrieCentrale3D(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_3Dsymp", "Symétrie3DPlan(<name,null>,\"C,D,E\",<name,var>);");
        addCMicon("bi_3Dtrans", "Translation3D(<name,null>,<name,var>,<name,var>,<name,var>);");
        addCMicon("bi_3Dsphererayon", "SphèreRayon(<name,null>,<name,var>,<nb,var,exp>);");
        addCMicon("bi_3Dspherepoint", "Sphère(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_3Dcircle1", "Cercle3D(<name,null>,<name,var>,<name,var>);");
        addCMicon("bi_3Dcircle2", "CercleRayon3D(<name,null>,<name,var>,<name,var>,\"E7\");");
        addCMicon("bi_3Dcircle3pts", "Cercle3D3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
        
        addSeparator(1);
        addCMicon("interactiveinput", "<var>=EntréeInteractive(\"Message\",<\"Point,Segment,Line,Circle\">);");
        addCMicon("aimant", "MettreObjetsMagnétiques(<name,var>,<objs,var>);");
        addCMicon("hide", "MettreCaché(<objs,var>,<\"vrai,faux\">);");
        addCMicon("type2", "MettreTypePoint(<objs,var>,<\"square,circle,diamond,point,cross,dcross\">);");
        addCMicon("color4", "MettreCouleur(<objs,var>,<\"green,blue,brown,cyan,red,black\">);");
        addCMicon("thickness1", "MettreEpaisseur(<objs,var>,<\"thick,normal,thin\">);");
        addCMicon("partial", "MettrePartiel(<objs,var>,<\"vrai,faux\">);");
        addCMicon("showvalue", "MettreMontrerValeur(<objs,var>,<\"vrai,faux\">);");
        addCMicon("showname", "MettreMontrerNom(<objs,var>,<\"vrai,faux\">);");
        addCMicon("filled", "MettreRempli(<objs,var>,<\"vrai,faux\">);");
        addCMicon("monkey", "Déplacer(<name,var>,<nb,var,exp>,<nb,var,exp>);");
        addCMicon("nail", "MettreFixe(<name,var>,<\"vrai,faux\">);");
//        addCMicon("dice", "<var,null>=Math.ceil(Math.random()*6);");
    	}
    	else if (FlagSimp) {
    		addCMicon("point", "Point(<name,null>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("intersection", "Intersection(<name,null>,<name,var>,<name,var>);");
            addCMicon("midpoint", "MidPoint(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_syma", "Reflection(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_symc", "Symmetry(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_trans", "Translation(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("line", "Line(<name,null>,<name,var>,<name,var>);");
            addCMicon("ray", "Ray(<name,null>,<name,var>,<name,var>);");
            addCMicon("parallel", "Parallel(<name,null>,<name,var>,<name,var>);");
            addCMicon("plumb", "Perpendicular(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_med", "PerpendicularBisector(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_biss", "AngleBisector(,<name,var>,<name,var>,<name,var>);");
            addCMicon("segment", "Segment(<name,null>,<name,var>,<name,var>);");
            addCMicon("fixedsegment", "FixedSegment(<name,null>,<name,var>,<nb,var>);");
            addCMicon("vector", "Vector(<name,null>,<name,var>,<name,var>);");
            addCMicon("area", "Polygon(<name,null>,<objs>);");
            addCMicon("angle", "Angle(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("fixedangle", "FixedAngle(<name,null>,<name,var>,<name,var>,<nb,var,exp>);");
            addCMicon("circle", "Circle(<name,null>,<name,var>,<name,var>);");
            addCMicon("fixedcircle", "FixedCircle(<name,null>,<name,var>,<nb,exp>);");
            addCMicon("circle3", "Circle3(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_circ", "Circle3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_arc", "Arc3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("quadric", "Quadric(<name,null>,<name,var>,<name,var>,<name,var>,<name,var>,<name,var>);");
//            addCMicon("text", "");
            addCMicon("expression", "Expression(<name,null>,\"Votre expression\",<nb>,<nb>);");
            addCMicon("text", "Text(<name,null>,\"Votre message\",<nb>,<nb>);");
//            addCMicon("image3", "");
//            addBlankIcon(3);
            addCMicon("function", "CartesianFunction(<name,null>,-5,5,\"x^2-2*x+1\");");
            addCMicon("parametricfunction", "ParametricFunction(<name,null>,-3.14,3.14,\"3*rcos(t)^3\",\"3*rsin(t)^3\");");
            addCMicon("equationxy", "ImplicitPlot(<name,null>,\"x^3-2*x-y^2+1\");");
            addCMicon("macro", "ExecuteMacro(<name,null>,\"_x,_y,_z\");");
            addSeparator(1);
            
            addCMicon("bi_3Dcoords", "Point3D(<name,null>,<nb,var,exp>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("bi_3Dproj", "Projection3D(<name,null>,\"C,D,E\",<name,var>);");
            addCMicon("bi_3Dsymc", "Symmetry3D(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_3Dsymp", "Reflection3D(<name,null>,\"C,D,E\",<name,var>);");
            addCMicon("bi_3Dtrans", "Translation3D(<name,null>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_3Dsphererayon", "FixedSphere(<name,null>,<name,var>,<nb,var,exp>);");
            addCMicon("bi_3Dspherepoint", "Sphere(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_3Dcircle1", "Circle3D(<name,null>,<name,var>,<name,var>);");
            addCMicon("bi_3Dcircle2", "FixedCircle3D(<name,null>,<name,var>,<name,var>,\"E7\");");
            addCMicon("bi_3Dcircle3pts", "Circle3D3pts(<name,null>,<name,var>,<name,var>,<name,var>);");
            
            addSeparator(1);
            addCMicon("interactiveinput", "<var>=InteractiveInput(\"Message\",<\"Point,Segment,Line,Circle\">);");
            addCMicon("aimant", "SetMagneticObjects(<name,var>,<objs,var>);");
            addCMicon("hide", "SetHide(<objs,var>,<\"true,false\">);");
            addCMicon("type2", "SetPointType(<objs,var>,<\"square,circle,diamond,point,cross,dcross\">);");
            addCMicon("color4", "SetColor(<objs,var>,<\"green,blue,brown,cyan,red,black\">);");
            addCMicon("thickness1", "SetThickness(<objs,var>,<\"thick,normal,thin\">);");
            addCMicon("partial", "SetPartial(<objs,var>,<\"true,false\">);");
            addCMicon("showvalue", "SetShowValue(<objs,var>,<\"true,false\">);");
            addCMicon("showname", "SetShowName(<objs,var>,<\"true,false\">);");
            addCMicon("filled", "SetFilled(<objs,var>,<\"true,false\">);");
            addCMicon("monkey", "Move(<name,var>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("nail", "SetFixed(<name,var>,<\"true,false\">);");
//            addCMicon("dice", "<var,null>=Math.ceil(Math.random()*6);");
            
    	}
    	else {
    		addCMicon("point", "<var,null>=Point(<null,name>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("intersection", "<var,null>=Intersection(<null,name>,<name,var>,<name,var>);");
            addCMicon("midpoint", "<var,null>=MidPoint(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_syma", "<var,null>=Reflection(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_symc", "<var,null>=Symmetry(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_trans", "<var,null>=Translation(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("line", "<var,null>=Line(<null,name>,<name,var>,<name,var>);");
            addCMicon("ray", "<var,null>=Ray(<null,name>,<name,var>,<name,var>);");
            addCMicon("parallel", "<var,null>=Parallel(<null,name>,<name,var>,<name,var>);");
            addCMicon("plumb", "<var,null>=Perpendicular(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_med", "<var,null>=PerpendicularBisector(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_biss", "<var,null>=AngleBisector(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("segment", "<var,null>=Segment(<null,name>,<name,var>,<name,var>);");
            addCMicon("fixedsegment", "<var,null>=FixedSegment(<null,name>,<name,var>,<nb,var>);");
            addCMicon("vector", "<var,null>=Vector(<null,name>,<name,var>,<name,var>);");
            addCMicon("area", "<var,null>=Polygon(<null,name>,<objs>);");
            addCMicon("angle", "<var,null>=Angle(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("fixedangle", "<var,null>=FixedAngle(<null,name>,<name,var>,<name,var>,<nb,var,exp>);");
            addCMicon("circle", "<var,null>=Circle(<null,name>,<name,var>,<name,var>);");
            addCMicon("fixedcircle", "<var,null>=FixedCircle(<null,name>,<name,var>,<nb,exp>);");
            addCMicon("circle3", "<var,null>=Circle3(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_circ", "<var,null>=Circle3pts(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_arc", "<var,null>=Arc3pts(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("quadric", "<var,null>=Quadric(<null,name>,<name,var>,<name,var>,<name,var>,<name,var>,<name,var>);");
//            addCMicon("text", "");
            addCMicon("expression", "<var,null>=Expression(<null,name>,\"Votre expression\",<nb>,<nb>);");
            addCMicon("text", "<var,null>=Text(<null,name>,\"Votre message\",<nb>,<nb>);");
//            addCMicon("image3", "");
//            addBlankIcon(3);
            addCMicon("function", "<var,null>=CartesianFunction(<null,name>,-5,5,\"x^2-2*x+1\");");
            addCMicon("parametricfunction", "<var,null>=ParametricFunction(<null,name>,-3.14,3.14,\"3*rcos(t)^3\",\"3*rsin(t)^3\");");
            addCMicon("equationxy", "<var,null>=ImplicitPlot(<null,name>,\"x^3-2*x-y^2+1\");");
            addCMicon("macro", "<var,null>=ExecuteMacro(\"macroName\",\"_x,_y,_z\");");
            
            addSeparator(1);
            addCMicon("bi_3Dcoords", "<var,null>=Point3D(<null,name>,<nb,var,exp>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("bi_3Dproj", "<var,null>=Projection3D(<null,name>,\"_f,_g,_h\",<name,var>);");
            addCMicon("bi_3Dsymc", "<var,null>=Symmetry3D(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_3Dsymp", "<var,null>=Reflection3D(<null,name>,\"_f,_g,_h\",<name,var>);");
            addCMicon("bi_3Dtrans", "<var,null>=Translation3D(<null,name>,<name,var>,<name,var>,<name,var>);");
            addCMicon("bi_3Dsphererayon", "<var,null>=FixedSphere(<null,name>,<name,var>,<nb,var,exp>);");
            addCMicon("bi_3Dspherepoint", "<var,null>=Sphere(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_3Dcircle1", "<var,null>=Circle3D(<null,name>,<name,var>,<name,var>);");
            addCMicon("bi_3Dcircle2", "<var,null>=FixedCircle3D(<null,name>,<name,var>,<name,var>,\"E7\");");
            addCMicon("bi_3Dcircle3pts", "<var,null>=Circle3D3pts(<null,name>,<name,var>,<name,var>,<name,var>);");
            
            addSeparator(1);
            addCMicon("interactiveinput", "<var>=InteractiveInput(\"Message\",<\"Point,Segment,Line,Circle\">);");
            addCMicon("aimant", "SetMagneticObjects(<name,var>,<objs,var>);");
            addCMicon("hide", "SetHide(<objs,var>,<\"true,false\">);");
            addCMicon("type2", "SetPointType(<objs,var>,<\"square,circle,diamond,point,cross,dcross\">);");
            addCMicon("color4", "SetColor(<objs,var>,<\"green,blue,brown,cyan,red,black\">);");
            addCMicon("thickness1", "SetThickness(<objs,var>,<\"thick,normal,thin\">);");
            addCMicon("partial", "SetPartial(<objs,var>,<\"true,false\">);");
            addCMicon("showvalue", "SetShowValue(<objs,var>,<\"true,false\">);");
            addCMicon("showname", "SetShowName(<objs,var>,<\"true,false\">);");
            addCMicon("filled", "SetFilled(<objs,var>,<\"true,false\">);");
            addCMicon("monkey", "Move(<name,var>,<nb,var,exp>,<nb,var,exp>);");
            addCMicon("nail", "SetFixed(<name,var>,<\"true,false\">);");
//            addCMicon("dice", "<var,null>=Math.ceil(Math.random()*6);");
    	}


        addJSIcon("js_cos", "Math.cos(x)");
        addJSIcon("js_sin", "Math.sin(x)");
        addJSIcon("js_tan", "Math.tan(x)");
        addJSIcon("js_acos", "Math.acos(x)");
        addJSIcon("js_asin", "Math.asin(x)");
        addJSIcon("js_atan", "Math.atan(x)");
        addJSIcon("js_abs", "Math.abs(x)");
        addJSIcon("js_ceil", "Math.ceil(x)");
        addJSIcon("js_floor", "Math.floor(x)");
        addJSIcon("js_round", "Math.round(x)");
        addJSIcon("js_min", "Math.min(x,y)");
        addJSIcon("js_max", "Math.max(x,y)");
        addJSIcon("js_pi", "Math.PI");
        addJSIcon("js_e", "Math.E");
        addJSIcon("js_xn", "Math.pow(x,n)");
        addJSIcon("js_ex", "Math.exp(x)");
        addJSIcon("js_ln", "Math.log(x)");
        addJSIcon("js_sqrt", "Math.sqrt(x)");
        addJSIcon("js_or", "(x==2 || x>=5)");
        addJSIcon("js_and", "(x>=2 && x<=5)");
        addJSIcon("js_no", "!(x==0)");
        addJSIcon("hour", "Pause(1000);");
        addJSIcon("js_array", "tableau = [[1,2],[3,4]];");
        addJSIcon("dice", "Math.ceil(Math.random()*6)");


    }

    public void initUndoRedo() {

        undo=new UndoManager() {

            public synchronized boolean addEdit(UndoableEdit anEdit) {
                if (anEdit instanceof AbstractDocument.DefaultDocumentEvent) {
                    AbstractDocument.DefaultDocumentEvent de=(AbstractDocument.DefaultDocumentEvent) anEdit;
                    if (de.getType()==DocumentEvent.EventType.CHANGE) {
                        return false;
                    }
                }
                return super.addEdit(anEdit);
            }
        };

        script_area.getStyledDocument().addUndoableEditListener(new UndoableEditListener() {

            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
            }
        });

        script_area.getActionMap().put("Undo",
                new AbstractAction("Undo") {

                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canUndo()) {
                                undo.undo();
                            }
                        } catch (CannotUndoException e) {
                        }
                    }
                });

        script_area.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ctrlkey), "Undo");
        script_area.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ctrlkey), "Redo");
        script_area.getActionMap().put("Redo",
                new AbstractAction("Redo") {

                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canRedo()) {
                                undo.redo();
                            }
                        } catch (CannotRedoException e) {
                        }
                    }
                });
    }

    // Select a line (linenumber starts at 1)
    private void selectLine(int linenumber) {
        Element root=script_area.getDocument().getDefaultRootElement();
        Element myline=root.getElement(linenumber-1);
        int start=myline.getStartOffset();
        int end=myline.getEndOffset();
        script_area.select(start, end);
    }

    static public String Loc(final String s) {
        return Global.Loc(s);
    }

    /**
     * In case of errors, it's called at Runtime by JSRun class
     * @param errormessage
     */
    public void Error(String errormessage) {
        StringBuffer sb=new StringBuffer();
        int errorline=1;
        Pattern p=Pattern.compile("\\([^#]*#([0-9]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m=p.matcher(errormessage);
        if (m.find()) {
            errorline=Integer.parseInt(m.group(1))+1;
            m.appendReplacement(sb, "");
        } else {
            sb=sb.append(errormessage);
        }
        errorpanel.removeAll();
        errortitlelabel.setText(" "+Loc("JSerror.title")+" ("+Loc("JSerror.line")+" "+errorline+") :");
        errormessagelabel.setText(sb.toString());
        errorpanel.add(errortitlelabel);
        errorpanel.add(errormessagelabel);
        errorpanel.revalidate();
        errorpanel.repaint();
        selectLine(errorline);
        script_area.requestFocus();
    }

    public void setTabs(JTextPane textPane, int charactersPerTab) {
        FontMetrics fm=textPane.getFontMetrics(textPane.getFont());
        int charWidth=fm.charWidth('w');
        int tabWidth=charWidth*charactersPerTab;

        TabStop[] tabs=new TabStop[10];

        for (int j=0; j<tabs.length; j++) {
            int tab=j+1;
            tabs[j]=new TabStop(tab*tabWidth);
        }
        TabSet tabSet=new TabSet(tabs);
        Style style=textPane.getLogicalStyle();
        StyleConstants.setTabSet(style, tabSet);
        textPane.setLogicalStyle(style);
    }
    private ArrayList<Integer> braces;

    private int tabIndent(int carnum) {
        int priority=0;
        int i=0;
        while ((i<braces.size())&&(carnum>braces.get(i))) {
            priority=braces.get(i+1);
            i+=2;
        }
//
//
//
//        for (int i=0;i<braces.size();i+=2){
//            if (carnum>braces.get(i)) {
//                return braces.get(i+1);
//            }
//        }
        return priority;
    }

    private void shiftposition(int lg, int sh) {
        for (int i=0; i<braces.size(); i+=2) {
            if (braces.get(i)>lg) {
                braces.set(i, braces.get(i)+sh);
            }
        }
    }

    private String tabs(int t) {
        String st="";
        for (int i=0; i<t; i++) {
            st+="\t";
        }
        return st;
    }

    private void indentText() {
        String alltxt=script_area.getText();
        String myline;
        String newtxt="";
        int length=0;
        int linenum=1;
        BufferedReader reader=new BufferedReader(new StringReader(alltxt));
        try {
            while ((myline=reader.readLine())!=null) {
                int tabnb=tabIndent(length);
                if (myline.matches("^[ \t]*}.*$")) {
//                    System.out.println("line="+linenum);
                    tabnb--;
                }
                String newline=myline.replaceAll("^([ \t]*)([^ \t])", tabs(tabnb)+"$2");
                int shift=newline.length()-myline.length();
                newtxt+=newline+"\n";

//                System.out.println("line="+(linenum++)+" pos="+length+" indent="+tabs(tabIndent(length))+tabIndent(length));

                length+=newline.length()+1;
                shiftposition(myline.length(), shift);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        script_area.setText(newtxt);
    }

    private void FindDelimiters() {
        char openedbrace="{".charAt(0);
        char closedbrace="}".charAt(0);
        char quotemark="\"".charAt(0);
        char backslash="\\".charAt(0);
        braces=new ArrayList<Integer>();
        String alltxt=script_area.getText();
        boolean inquote=false;
        char oldcur=0;
        char cur=0;
        int bracesnum=0;
        int oldbracesnum=0;
        for (int i=0; i<alltxt.length(); i++) {
            oldcur=cur;
            cur=alltxt.charAt(i);
            if ((cur==quotemark)&&(oldcur!=backslash)) {
                inquote=(!inquote);
            }
            if (!inquote) {
                if (alltxt.charAt(i)==openedbrace) {
                    bracesnum++;
                    braces.add(i);
                    braces.add(bracesnum);
                    oldbracesnum=bracesnum;
                } else if (alltxt.charAt(i)==closedbrace) {
                    bracesnum--;
                    braces.add(i);
                    braces.add(bracesnum);
                    oldbracesnum=bracesnum;
                }
            }
        }


//        System.out.println(newtxt);


//        System.out.println("******");
//        for (int i=0; i<braces.size(); i+=2) {
//            int i1=braces.get(i);
//            int i2=braces.get(i+1);
//            System.out.println("rang="+i1+" priorité="+i2);
//        }
//        System.out.println("******");


    }

    private String replace(String s, String reg, String repl) {
        String st="";
        while (!s.equals(st)) {
            st=s;
//            s=s.replaceFirst(reg, repl);
            s=Pattern.compile(reg, Pattern.MULTILINE|Pattern.DOTALL).matcher(s).replaceAll(repl);
        }
        return st;
    }

    private void Format() {
        int i=script_area.getCaretPosition();

        String alltxt=script_area.getText();

        alltxt=replace(alltxt, "\\Q}\\E[ \t]*\\Q}\\E[ \t]*\n", "\n}\n}\n");
        alltxt=replace(alltxt, ";[ \t]*\\Q}\\E[ \t]*\n", ";\n}\n");

        alltxt=alltxt.replaceAll("\\Q}\\E[ \t]*\\Qelse\\E[ \t]*\\Q{\\E", "} else {");

        alltxt=alltxt.replaceAll("\n[ \t]*\\Q{\\E[ \t]*\n", "{\n");
        script_area.setText(alltxt);
        FindDelimiters();
        indentText();
        try {
            script_area.setCaretPosition(i);
        } catch (Exception e) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new myJTitleBar();
        macpanel = new javax.swing.JPanel();
        title_lbl = new javax.swing.JLabel();
        closeBTN = new javax.swing.JButton();
        winpanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        LeftBorder = new myJVerticalSeparatorPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        action_buttons = new javax.swing.JPanel();
        jTextHeight = new javax.swing.JSlider();
        jPanel18 = new javax.swing.JPanel();
        openbtnSimp = new javax.swing.JButton();
        openbtn5 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        openbtn2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        backbtn = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        openbtn3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        openbtn4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        script_area = new javax.swing.JTextPane();
        jPanel14 = new javax.swing.JPanel();
        commands = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        c_carmetal = new javax.swing.JPanel();
        c_js = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        js_btns = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JSlist = new javax.swing.JList();
        RightBorder = new myJVerticalSeparatorPanel();
        controls = new myStatusBarPanel();
        jPanel13 = new javax.swing.JPanel();
        errorpanel = new javax.swing.JPanel();
        errortitlelabel = new javax.swing.JLabel();
        errormessagelabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 25));
        jPanel3.setPreferredSize(new java.awt.Dimension(199, 25));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        macpanel.setAlignmentX(0.0F);
        macpanel.setEnabled(false);
        macpanel.setFocusable(false);
        macpanel.setMaximumSize(new java.awt.Dimension(32767, 25));
        macpanel.setMinimumSize(new java.awt.Dimension(0, 25));
        macpanel.setOpaque(false);
        macpanel.setPreferredSize(new java.awt.Dimension(523, 25));
        macpanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                macpanelMouseDragged(evt);
            }
        });
        macpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                macpanelMousePressed(evt);
            }
        });
        macpanel.setLayout(new javax.swing.BoxLayout(macpanel, javax.swing.BoxLayout.X_AXIS));

        title_lbl.setBackground(new java.awt.Color(117, 112, 104));
        title_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_lbl.setText("jLabel3");
        title_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        title_lbl.setMaximumSize(new java.awt.Dimension(32767, 32767));
        title_lbl.setMinimumSize(new java.awt.Dimension(0, 25));
        title_lbl.setPreferredSize(new java.awt.Dimension(45, 25));
        macpanel.add(title_lbl);

        jPanel3.add(macpanel);

        closeBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/themes/gray/zclosebutton.png"))); // NOI18N
        closeBTN.setBorder(null);
        closeBTN.setBorderPainted(false);
        closeBTN.setContentAreaFilled(false);
        closeBTN.setFocusPainted(false);
        closeBTN.setFocusable(false);
        closeBTN.setMaximumSize(new java.awt.Dimension(25, 30));
        closeBTN.setMinimumSize(new java.awt.Dimension(25, 30));
        closeBTN.setPreferredSize(new java.awt.Dimension(25, 30));
        closeBTN.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/themes/gray/zclosebuttonover.png"))); // NOI18N
        closeBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBTNMouseClicked(evt);
            }
        });
        jPanel3.add(closeBTN);

        winpanel.setAlignmentX(0.0F);
        winpanel.setEnabled(false);
        winpanel.setFocusable(false);
        winpanel.setMaximumSize(new java.awt.Dimension(32767, 25));
        winpanel.setMinimumSize(new java.awt.Dimension(0, 25));
        winpanel.setOpaque(false);
        winpanel.setPreferredSize(new java.awt.Dimension(523, 25));
        winpanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                winpanelMouseDragged(evt);
            }
        });
        winpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                winpanelMousePressed(evt);
            }
        });
        winpanel.setLayout(new javax.swing.BoxLayout(winpanel, javax.swing.BoxLayout.X_AXIS));
        jPanel3.add(winpanel);

        getContentPane().add(jPanel3);

        jPanel1.setPreferredSize(new java.awt.Dimension(565, 487));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        LeftBorder.setMaximumSize(new java.awt.Dimension(5, 32767));
        LeftBorder.setMinimumSize(new java.awt.Dimension(5, 30));
        LeftBorder.setOpaque(false);
        LeftBorder.setPreferredSize(new java.awt.Dimension(5, 487));
        LeftBorder.setLayout(null);
        jPanel1.add(LeftBorder);

        jPanel15.setAlignmentX(0.0F);
        jPanel15.setMaximumSize(new java.awt.Dimension(3, 1));
        jPanel15.setMinimumSize(new java.awt.Dimension(3, 1));
        jPanel15.setOpaque(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(3, 1));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(jPanel15);

        jPanel2.setFocusable(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        action_buttons.setAlignmentX(0.0F);
        action_buttons.setAlignmentY(0.0F);
        action_buttons.setMaximumSize(new java.awt.Dimension(32727, 40));
        action_buttons.setMinimumSize(new java.awt.Dimension(370, 40));
        action_buttons.setPreferredSize(new java.awt.Dimension(350, 40));
        action_buttons.setLayout(new javax.swing.BoxLayout(action_buttons, javax.swing.BoxLayout.X_AXIS));

        jTextHeight.setFont(new java.awt.Font("DejaVu Sans", 0, 8));
        jTextHeight.setForeground(new java.awt.Color(50, 50, 150));
        jTextHeight.setMaximum(36);
        jTextHeight.setMinimum(9);
        jTextHeight.setMinorTickSpacing(1);
        jTextHeight.setPaintLabels(true);
        jTextHeight.setPaintTicks(true);
        jTextHeight.setSnapToTicks(true);
        jTextHeight.setToolTipText("script height");
        jTextHeight.setValue(16);
        jTextHeight.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextHeight.setMinimumSize(new java.awt.Dimension(96, 43));
        jTextHeight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTextHeightStateChanged(evt);
            }
        });
        action_buttons.add(jTextHeight);

        jPanel18.setFocusable(false);
        jPanel18.setMaximumSize(new java.awt.Dimension(35, 1));
        jPanel18.setMinimumSize(new java.awt.Dimension(35, 1));
        jPanel18.setOpaque(false);
        jPanel18.setPreferredSize(new java.awt.Dimension(35, 1));
        action_buttons.add(jPanel18);

        openbtn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/comment.png"))); // NOI18N
        openbtn5.setToolTipText(Loc("JSEditor.comment"));
        openbtn5.setBorder(null);
        openbtn5.setContentAreaFilled(false);
        openbtn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openbtn5MouseClicked(evt);
            }
        });
        openbtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtn5ActionPerformed(evt);
            }
        });
        openbtn5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                openbtn5KeyPressed(evt);
            }
        });
        action_buttons.add(openbtn5);

        jPanel16.setFocusable(false);
        jPanel16.setMaximumSize(new java.awt.Dimension(10, 1));
        jPanel16.setMinimumSize(new java.awt.Dimension(10, 1));
        jPanel16.setOpaque(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(10, 1));
        action_buttons.add(jPanel16);

        openbtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/format.png"))); // NOI18N
        openbtn2.setToolTipText(Loc("JSeditor.format"));
        openbtn2.setBorder(null);
        openbtn2.setContentAreaFilled(false);
        openbtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openbtn2MouseClicked(evt);
            }
        });
        openbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtn2ActionPerformed(evt);
            }
        });
        openbtn2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                openbtn2KeyPressed(evt);
            }
        });
        action_buttons.add(openbtn2);

        jPanel10.setFocusable(false);
        jPanel10.setMaximumSize(new java.awt.Dimension(10, 1));
        jPanel10.setMinimumSize(new java.awt.Dimension(10, 1));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(10, 1));
        action_buttons.add(jPanel10);

        backbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/restore.png"))); // NOI18N
        backbtn.setToolTipText(Loc("JSeditor.restore"));
        backbtn.setBorder(null);
        backbtn.setContentAreaFilled(false);
        backbtn.setEnabled(false);
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });
        backbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                backbtnKeyPressed(evt);
            }
        });
        backbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backbtnMouseClicked(evt);
            }
        });
        action_buttons.add(backbtn);

        jPanel11.setFocusable(false);
        jPanel11.setMaximumSize(new java.awt.Dimension(10, 1));
        jPanel11.setMinimumSize(new java.awt.Dimension(10, 1));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(10, 1));
        action_buttons.add(jPanel11);

        openbtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/run.png"))); // NOI18N
        openbtn3.setToolTipText(Loc("JSeditor.run"));
        openbtn3.setBorder(null);
        openbtn3.setContentAreaFilled(false);
        openbtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtn3ActionPerformed(evt);
            }
        });
        openbtn3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                openbtn3KeyPressed(evt);
            }
        });
        openbtn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openbtn3MouseClicked(evt);
            }
        });
        action_buttons.add(openbtn3);

        jPanel12.setFocusable(false);
        jPanel12.setMaximumSize(new java.awt.Dimension(35, 1));
        jPanel12.setMinimumSize(new java.awt.Dimension(35, 1));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(35, 1));
        action_buttons.add(jPanel12);

        openbtnSimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/js.png"))); // NOI18N
        openbtnSimp.setToolTipText(Loc("JSeditor.choseGenerator"));
        openbtnSimp.setBorder(null);
        openbtnSimp.setContentAreaFilled(false);
        openbtnSimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtnSimpActionPerformed(evt);
            }
        });
        openbtnSimp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                openbtnSimpKeyPressed(evt);
            }
        });
        openbtnSimp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openbtnSimpMouseClicked(evt);
            }
        });
        action_buttons.add(openbtnSimp);
        
        openbtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/help.png"))); // NOI18N
        openbtn4.setToolTipText(Loc("JSeditor.help"));
        openbtn4.setBorder(null);
        openbtn4.setContentAreaFilled(false);
        openbtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtn4ActionPerformed(evt);
            }
        });
        openbtn4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                openbtn4KeyPressed(evt);
            }
        });
        openbtn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openbtn4MouseClicked(evt);
            }
        });
        action_buttons.add(openbtn4);

        jPanel2.add(action_buttons);

        jPanel4.setAlignmentX(0.0F);
        jPanel4.setAlignmentY(0.0F);
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        script_area.setFont(new java.awt.Font("Monospaced", 0, 24));
        script_area.setCaretColor(new java.awt.Color(128, 64, 0));
        script_area.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        script_area.setDisabledTextColor(new java.awt.Color(158, 150, 236));
        script_area.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                script_areaKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                script_areaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                script_areaKeyReleased(evt);
            }
        });
        script_area.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                script_areaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(script_area);

        jPanel4.add(jScrollPane1);

        jPanel2.add(jPanel4);

        jPanel14.setAlignmentX(0.0F);
        jPanel14.setMaximumSize(new java.awt.Dimension(1, 2));
        jPanel14.setMinimumSize(new java.awt.Dimension(1, 2));
        jPanel14.setOpaque(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(1, 2));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(jPanel14);

        jPanel1.add(jPanel2);

        commands.setBackground(new java.awt.Color(204, 204, 255));
        commands.setFocusable(false);
        commands.setMaximumSize(new java.awt.Dimension(182, 98397));
        commands.setMinimumSize(new java.awt.Dimension(182, 0));
        commands.setOpaque(false);
        commands.setPreferredSize(new java.awt.Dimension(182, 460));
        commands.setLayout(new javax.swing.BoxLayout(commands, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setBackground(new java.awt.Color(117, 112, 104));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CaRMetal");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setEnabled(false);
        jLabel1.setFocusable(false);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setIconTextGap(0);
        jLabel1.setMaximumSize(new java.awt.Dimension(182, 25));
        jLabel1.setMinimumSize(new java.awt.Dimension(182, 25));
        jLabel1.setPreferredSize(new java.awt.Dimension(182, 25));
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerifyInputWhenFocusTarget(false);
        commands.add(jLabel1);

        c_carmetal.setBackground(new java.awt.Color(117, 112, 104));
        c_carmetal.setMaximumSize(new java.awt.Dimension(182, 260));
        c_carmetal.setMinimumSize(new java.awt.Dimension(182, 250));
        c_carmetal.setOpaque(false);
        c_carmetal.setPreferredSize(new java.awt.Dimension(182, 250));
        c_carmetal.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 3));
        commands.add(c_carmetal);

        c_js.setBackground(new java.awt.Color(117, 112, 104));
        c_js.setMaximumSize(new java.awt.Dimension(182, 374));
        c_js.setMinimumSize(new java.awt.Dimension(182, 260));
        c_js.setOpaque(false);
        c_js.setPreferredSize(new java.awt.Dimension(182, 260));
        c_js.setLayout(new javax.swing.BoxLayout(c_js, javax.swing.BoxLayout.Y_AXIS));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Javascript");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setEnabled(false);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setIconTextGap(0);
        jLabel2.setMaximumSize(new java.awt.Dimension(182, 25));
        jLabel2.setMinimumSize(new java.awt.Dimension(182, 25));
        jLabel2.setPreferredSize(new java.awt.Dimension(182, 25));
        jLabel2.setRequestFocusEnabled(false);
        jLabel2.setVerifyInputWhenFocusTarget(false);
        c_js.add(jLabel2);

        js_btns.setMaximumSize(new java.awt.Dimension(182, 120));
        js_btns.setMinimumSize(new java.awt.Dimension(182, 120));
        js_btns.setOpaque(false);
        js_btns.setPreferredSize(new java.awt.Dimension(182, 120));
        js_btns.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 3));
        c_js.add(js_btns);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jScrollPane2.setMaximumSize(new java.awt.Dimension(160, 500));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(160, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(160, 100));

        JSlist.setBackground(new java.awt.Color(214, 221, 229));
        JSlist.setFont(new java.awt.Font("DejaVu Sans", 2, 16)); // NOI18N
        JSlist.setForeground(new java.awt.Color(150, 50, 50));
        JSlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { Global.Loc("JSFonctions.Input"), Global.Loc("JSFonctions.Print"), Global.Loc("JSFonctions.Println"), Global.Loc("JSFonctions.Alert"), Global.Loc("JSFonctions.ifelse"), Global.Loc("JSFonctions.switchcase"), Global.Loc("JSFonctions.for"), Global.Loc("JSFonctions.while"), Global.Loc("JSFonctions.dowhile"), Global.Loc("JSFonctions.dountil"), Global.Loc("JSFonctions.function") };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        JSlist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JSlist.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JSlist.setDragEnabled(true);
        JSlist.setFixedCellHeight(20);
        JSlist.setMaximumSize(new java.awt.Dimension(160, 415));
        JSlist.setMinimumSize(new java.awt.Dimension(160, 20));
        JSlist.setPreferredSize(new java.awt.Dimension(160, 220));
        JSlist.setRequestFocusEnabled(false);
        JSlist.setVisibleRowCount(-1);
        JSlist.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                JSlistValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(JSlist);

        c_js.add(jScrollPane2);

        commands.add(c_js);

        jPanel1.add(commands);

        RightBorder.setMaximumSize(new java.awt.Dimension(5, 32767));
        RightBorder.setMinimumSize(new java.awt.Dimension(5, 0));
        RightBorder.setPreferredSize(new java.awt.Dimension(5, 515));
        RightBorder.setLayout(null);
        jPanel1.add(RightBorder);

        getContentPane().add(jPanel1);

        controls.setMaximumSize(new java.awt.Dimension(32000, 22));
        controls.setMinimumSize(new java.awt.Dimension(35, 22));
        controls.setPreferredSize(new java.awt.Dimension(35, 22));
        controls.setLayout(new javax.swing.BoxLayout(controls, javax.swing.BoxLayout.X_AXIS));

        jPanel13.setAlignmentX(0.0F);
        jPanel13.setAlignmentY(0.0F);
        jPanel13.setMaximumSize(new java.awt.Dimension(10, 1));
        jPanel13.setMinimumSize(new java.awt.Dimension(10, 1));
        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(10, 1));
        controls.add(jPanel13);

        errorpanel.setAlignmentX(0.0F);
        errorpanel.setAlignmentY(0.0F);
        errorpanel.setMaximumSize(new java.awt.Dimension(32737, 32737));
        errorpanel.setMinimumSize(new java.awt.Dimension(0, 30));
        errorpanel.setOpaque(false);
        errorpanel.setPreferredSize(new java.awt.Dimension(0, 30));
        errorpanel.setLayout(new javax.swing.BoxLayout(errorpanel, javax.swing.BoxLayout.X_AXIS));

        errortitlelabel.setBackground(new java.awt.Color(117, 112, 104));
        errortitlelabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/error.png"))); // NOI18N
        errortitlelabel.setText("Error");
        errortitlelabel.setAlignmentY(0.0F);
        errortitlelabel.setIconTextGap(10);
        errortitlelabel.setMaximumSize(new java.awt.Dimension(250, 30));
        errortitlelabel.setMinimumSize(new java.awt.Dimension(250, 30));
        errortitlelabel.setPreferredSize(new java.awt.Dimension(250, 30));
        errorpanel.add(errortitlelabel);

        errormessagelabel.setBackground(new java.awt.Color(117, 112, 104));
        errormessagelabel.setFont(new java.awt.Font("DejaVu Sans", 3, 13));
        errormessagelabel.setForeground(new java.awt.Color(102, 102, 102));
        errormessagelabel.setText("Error message");
        errormessagelabel.setAlignmentY(0.0F);
        errormessagelabel.setMaximumSize(new java.awt.Dimension(10000, 30));
        errormessagelabel.setMinimumSize(new java.awt.Dimension(300, 30));
        errormessagelabel.setPreferredSize(new java.awt.Dimension(300, 30));
        errorpanel.add(errormessagelabel);

        controls.add(errorpanel);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/themes/gray/zoombox.png"))); // NOI18N
        jButton2.setAlignmentY(0.0F);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setMaximumSize(new java.awt.Dimension(25, 30));
        jButton2.setMinimumSize(new java.awt.Dimension(25, 30));
        jButton2.setPreferredSize(new java.awt.Dimension(25, 30));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton2MouseDragged(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        controls.add(jButton2);

        getContentPane().add(controls);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void script_areaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_script_areaKeyReleased
//        syntaxColoring(); // TODO add your handling code here:
//       if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
//            FindDelimiters();
//            int c=script_area.getCaretPosition();
//            int t=tabIndent(c);
//            script_area.replaceSelection(tabs(t));
//
////            script_area.setCaretPosition(c-t);
//        }
    }//GEN-LAST:event_script_areaKeyReleased

    private void script_areaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_script_areaKeyPressed
        // TODO add your handling code here:
        NoTypeNoClic=false;
//        errorpanel.removeAll();


    }//GEN-LAST:event_script_areaKeyPressed

    private void script_areaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_script_areaKeyTyped
//       errorpanel.removeAll(); // TODO add your handling code here:
        clearStatusBar();
    }//GEN-LAST:event_script_areaKeyTyped

    private void script_areaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_script_areaMouseClicked
        NoTypeNoClic=false;        // TODO add your handling code here:
        clearStatusBar();
    }//GEN-LAST:event_script_areaMouseClicked

    private void closeBTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBTNMouseClicked
        dispose();
    }//GEN-LAST:event_closeBTNMouseClicked

    private void macpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_macpanelMousePressed
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=getLocation();
        // TODO add your handling code here:
}//GEN-LAST:event_macpanelMousePressed

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=getLocation();
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        current=MouseInfo.getPointerInfo().getLocation();
        xx=winloc.x+current.x-origin.x;
        yy=winloc.y+current.y-origin.y;

        setLocation(xx, yy);
        Toolkit.getDefaultToolkit().sync();
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseDragged

    private void macpanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_macpanelMouseDragged
        current=MouseInfo.getPointerInfo().getLocation();
        xx=winloc.x+current.x-origin.x;
        yy=winloc.y+current.y-origin.y;

        setLocation(xx, yy);
        Toolkit.getDefaultToolkit().sync();
        // TODO add your handling code here:
}//GEN-LAST:event_macpanelMouseDragged

    private void winpanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winpanelMouseDragged
        current=MouseInfo.getPointerInfo().getLocation();
        xx=winloc.x+current.x-origin.x;
        yy=winloc.y+current.y-origin.y;

        setLocation(xx, yy);
        Toolkit.getDefaultToolkit().sync();
        // TODO add your handling code here:
}//GEN-LAST:event_winpanelMouseDragged

    private void winpanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winpanelMousePressed
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=getLocation();
        // TODO add your handling code here:
}//GEN-LAST:event_winpanelMousePressed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=getLocation();
        w=this.getWidth();
        h=this.getHeight();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseDragged
        current=MouseInfo.getPointerInfo().getLocation();

        setSize(Math.max(current.x-origin.x+w, 40), Math.max(current.y-origin.y+h, 120));
        Toolkit.getDefaultToolkit().sync();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseDragged

    private void JSlistValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_JSlistValueChanged
    	infrench = System.getProperty("user.language").equals("fr");
        if (evt.getValueIsAdjusting()==false) {
            int sel=JSlist.getSelectedIndex();
            JSlist.removeSelectionInterval(sel, sel);
            if (sel==0&&FlagSimp&&infrench) {
                script_area.replaceSelection("a:=Entrée(\""+Loc("JScmd.inputmessage")+"\");");
            } else if (sel==0) {
            	script_area.replaceSelection("a=Input(\""+Loc("JScmd.inputmessage")+"\");");
            } else if (sel==1&&FlagSimp&&infrench) {
                script_area.replaceSelection("Afficher(\""+Loc("JScmd.promptmessage")+"\");");
            } else if (sel==1) {
            	script_area.replaceSelection("Print(\""+Loc("JScmd.promptmessage")+"\");");
            } else if (sel==2&&FlagSimp&&infrench) {
                script_area.replaceSelection("Afficherligne(\""+Loc("JScmd.promptmessage")+"\");");
            } else if (sel==2) {
            	script_area.replaceSelection("Println(\""+Loc("JScmd.promptmessage")+"\");");
            } else if (sel==3&&FlagSimp&&infrench) {
            	script_area.replaceSelection("Alerte(\""+Loc("JScmd.promptmessage")+"\");");
            } else if (sel==3) {
                script_area.replaceSelection("Alert(\""+Loc("JScmd.promptmessage")+"\");");
//            } else if (sel == 4) {
//                script_area.replaceSelection("Pause(1000);");
            } else if (sel==4&&FlagSimp&&infrench) {
                script_area.replaceSelection("si (i<20){\n\n} sinon {\n\n}");
            } else if (sel==4) {
            	script_area.replaceSelection("if (i<20){\n\n} else {\n\n}");
            } else if (sel==5&&FlagSimp&&infrench) {
                script_area.replaceSelection("obliquer (d){\n "
                        +"cas 1 : {\n\n rompre; }\n cas 2 : {\n\n rompre; }\n"
                        +"cas 3 : {\n\n rompre;} \n par défaut : {\n\n } \n} ");
            } else if (sel==5) {
            	script_area.replaceSelection("switch (d){\n "
                        +"case 1 : {\n\n break; }\n case 2 : {\n\n break; }\n"
                        +"case 3 : {\n\n break;} \n par default : {\n\n } \n} ");
            } else if (sel==6&&FlagSimp&&infrench) {
                script_area.replaceSelection("pour i allant de 1 à 20 {\n\n}");
            } else if (sel==6&&FlagSimp) {
                script_area.replaceSelection("for i from 1 to 20 {\n\n}");
            } else if (sel==6) {
            	script_area.replaceSelection("for (i=0; i<20; i++){\n\n}");
            } else if (sel==7&&FlagSimp&&infrench) {
                script_area.replaceSelection("tant que (i<20){\ni=i+1;\n}\n");
            } else if (sel==7) {
            	script_area.replaceSelection("while (i<20){\ni=i+1;\n}\n");
            } else if(sel==8&&FlagSimp&&infrench) {
            	script_area.replaceSelection("faire{\ni=i+1;\n\n}tant que (i<20)\n");
            } else if (sel==8) {
                script_area.replaceSelection("do{\ni=i+1;\n\n}while (i<20)\n");
            } else if(sel==9&&FlagSimp&&infrench) {
            	script_area.replaceSelection("faire{\ni=i+1;\n\n}jusqu'à (i>20);\n");
            } else if (sel==9) {
                script_area.replaceSelection("do{\ni=i+1;\n\n}until (i>20);\n");
            } else if (sel==10&&FlagSimp&&infrench) {
                script_area.replaceSelection("fonction myfunction(x){\n\n}");
            } else if (sel==10) {
            	script_area.replaceSelection("function myfunction(x){\n\n}");
            }
            Format();
            script_area.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_JSlistValueChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed




        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//        System.out.println("activated");        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
//        System.out.println("déactivated");        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowDeactivated

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_backbtnActionPerformed

    private void backbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_backbtnKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_backbtnKeyPressed

    private void backbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbtnMouseClicked
        // TODO add your handling code here:
        clearStatusBar();
        ITEM.getPanel().Restore();
}//GEN-LAST:event_backbtnMouseClicked

    private void openbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn2ActionPerformed

    private void openbtn2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openbtn2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn2KeyPressed

    private void openbtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openbtn2MouseClicked
        // TODO add your handling code here:
        clearStatusBar();
        Format();
        syntaxColoring();
    }//GEN-LAST:event_openbtn2MouseClicked

    private void openbtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn3ActionPerformed

    private void openbtn3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openbtn3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn3KeyPressed

    private void openbtn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openbtn3MouseClicked
        // TODO add your handling code here:
        clearStatusBar();
        ITEM.runScript();
    }//GEN-LAST:event_openbtn3MouseClicked

    private void openbtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtn4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn4ActionPerformed

    private void openbtn4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openbtn4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn4KeyPressed

    private void openbtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openbtn4MouseClicked
        // TODO add your handling code here:
//        setAlwaysOnTop(true);
        JBrowserLauncher.openURL("http://carmetal.org/index.php/fr/tutoriels/les-scripts/syntaxe");
//setAlwaysOnTop(false);
        toFront();

    }//GEN-LAST:event_openbtn4MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void openbtn5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void openbtn5KeyPressed(java.awt.event.KeyEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void openbtn5MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        commentSelection();
    }                                     

    private void openbtnSimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtn5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn5ActionPerformed

    private void openbtnSimpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openbtn5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_openbtn5KeyPressed

    private void openbtnSimpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openbtn5MouseClicked
        // TODO add your handling code here:
    	FlagSimp=!FlagSimp;
    	if (FlagSimp) openbtnSimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/js2.png")));
    	else openbtnSimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/jswindow/js.png")));
    	openbtnSimp.setBorder(null);
    	openbtnSimp.setContentAreaFilled(false);
    	c_carmetal.removeAll();
    	initCaRMetalBtns();
    	openbtnSimp.revalidate();
    	c_carmetal.revalidate();
    }//GEN-LAST:event_openbtn5MouseClicked

    private void jTextHeightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTextHeightStateChanged
        // TODO add your handling code here:
        clearStatusBar();
        script_area.setFont(new Font("monospaced", Font.PLAIN, TailleTexte));//taille script
        TailleTexte=jTextHeight.getValue();
    }//GEN-LAST:event_jTextHeightStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList JSlist;
    private javax.swing.JPanel LeftBorder;
    private javax.swing.JPanel RightBorder;
    private javax.swing.JPanel action_buttons;
    private javax.swing.JButton backbtn;
    private javax.swing.JPanel c_carmetal;
    private javax.swing.JPanel c_js;
    private javax.swing.JButton closeBTN;
    private javax.swing.JPanel commands;
    private javax.swing.JPanel controls;
    private javax.swing.JLabel errormessagelabel;
    private javax.swing.JPanel errorpanel;
    private javax.swing.JLabel errortitlelabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jTextHeight;
    private javax.swing.JPanel js_btns;
    private javax.swing.JPanel macpanel;
    private javax.swing.JButton openbtn2;
    private javax.swing.JButton openbtn3;
    private javax.swing.JButton openbtn4;
    private javax.swing.JButton openbtn5;
    private javax.swing.JButton openbtnSimp;
    private javax.swing.JTextPane script_area;
    private javax.swing.JLabel title_lbl;
    private javax.swing.JPanel winpanel;
    // End of variables declaration//GEN-END:variables
}
