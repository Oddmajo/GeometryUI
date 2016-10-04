/*
Copyright 2006 Eric Hakenholz
This file is part of C.a.R. software.
C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.
C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eric.bar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.text.JTextComponent;

import eric.JColorPicker;
import eric.JEricPanel;
import eric.JZirkelCanvas;
import eric.GUI.palette.PaletteManager;
import eric.textfieldpopup.JTextFieldPopup;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.EquationXYObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.InsideObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.MidpointObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TrackObject;
import rene.zirkel.objects.UserFunctionObject;
import rene.zirkel.objects.VectorObject;

/**
 *
 * @author erichake with addons by Dibs for 3D
 */
public class JProperties extends JTabPanel {

    Construction Cn=null;
    private static ConstructionObject O=null;
    ZirkelCanvas ZC=null;
    ZirkelFrame ZF=null;
    private static String typecode;
    Color C_TextField=new Color(50, 50, 50);
    Color C_TextField_OFF=new Color(150, 150, 150);
    Color C_Comment=new Color(0, 0, 0);
    int HRubSeparatorHeight=40;
    int HRubSeparatorWidth=40;
    int TextFieldHeight=17;
    int TextFontSize=12;
//    String GlobalFontName=JGlobals.GlobalFont;
    String GlobalFontName="System";
    Font F_TextField=new Font(GlobalFontName, 0, TextFontSize);
    Font F_NameField=new Font(GlobalFontName, 1, 12);
    Font F_ConditionalField=new Font(GlobalFontName, 0, 10);
    Font F_Label=new Font(GlobalFontName, 0, TextFontSize);
    Font F_CheckBox=new Font(GlobalFontName, 0, TextFontSize);
    Font F_Button=new Font(GlobalFontName, 0, 10);
    Font F_ComboBox=new Font(GlobalFontName, 0, TextFontSize);
    Font F_TextArea=new Font(GlobalFontName, 0, TextFontSize);
    myJName name;
    myJAlias alias;
    myJRed redcolor;
    myJGreen greencolor;
    myJBlue bluecolor;
    myJMagnetObj magnetobj;
    myJMagnetPix magnetpix;
    myJUnit unit;
    myTRK track;
    myJConditional czvalue; //Layer num
    private static myJX X;
    private static myJY Y;
    private static myJX3D X3D;
    private static myJY3D Y3D;
    private static myJZ3D Z3D;
    myXYlink XYlink;
    myXYZlink XYZlink;
    myJAbsolutePos AbsPos;
    myJFx Fx;
    myJFx3D Fx3D;
    myJGrid Grid;
    myJInside Inside;
    private static myJR ray;
    private static myJR3D ray3D;
    myJRFx RFx;
    myJRFx3D RFx3D;
    private static myJA angle;
    myJAFx aFx;
    myJTextArea text;
    myJExpression Exp;
    myJPrompt prompt;
    myJSMin min;
    myJSMax max;
    myJMinBoundary bound_left;
    myJMaxBoundary bound_right;
    myJSSlider slider;
    ContentLine sliderline;
    myJUserFunction userfunc;
    myJUserF_Y fuey;
    myJEqXY eqxy;
    myJEqXYDHorChooser eqxychooser;
    ContentLine btnline;
    myMagnetBtn magnetbtn;
    myArcBtn arcbtn;
    myPtBindBtn ptbindbtn;
    myPtAwayBtn ptawaybtn;
    myPtCloseBtn ptclosebtn;
    myJFunction func;
    myJF_X fex;
    myJF_Y fey;
    myJF_d fd;
    myJF_DMin fdmin;
    myJF_Min fmin;
    myJF_Max fmax;
    myJF_pt fpt;
    myJF_Discrete ftr;
    myJColorLine color;
    myJSegmentCodeLine segmentcode;
    myJColorTypeLine colortype;
    myJTypeLine type;
    myJCircleLine circle;
    myJShowLine show;
    myJBoldLine boldlarge;
    myJAreaLine area;
    myJAngleLine0 angle0;
    myJAngleLine1 angle1;
    myJFilledLine filled;
    myJLineLine line;
    myJTrackDMinChooser dmintrack;
    myJConditional csolid, chidden, cnormal, cthick, cthin, cblack, cgreen, cblue, ccyan, cred, cbrown, cshowvalue, cshowname, cbackground, csuperhidden;
    // Text and checkbox items :
    static String APoint=",PointObject,IntersectionObject,PointonObject,PointonObjectIntersectionObject,LineCircleIntersectionObject,LineQuadricIntersectionObject,QuadricQuadricIntersectionObject,LineIntersectionObject,CircleIntersectionObject,MidpointObject,";
    static String ACircle=",PrimitiveCircleObject,CircleObject,Circle3Object,FixedCircleObject,";
    String ALine=",PrimitiveLineObject,LineObject,TwoPointLineObject,RayObject,ParallelObject,PlumbObject,";
    static String AAngle=",AngleObject,FixedAngleObject,";
    static String AExpression=",ExpressionObject,";
    static String AText=",TextObject,";
    String AFunction=",FunctionObject,";
    String AEquationXY=",EquationXYObject,";
    static String AUserFunction=",UserFunctionObject,";
    String ATrack=",TrackObject,JLocusTrackObject,ObjectTracker,JLocusObjectTracker,";
    static String ASegment=",SegmentObject,VectorObject,";
    String AArea=",AreaObject,";
    ArrayList<ConstructionObject> MultipleObjects=null;

    public JProperties(int w, int h) {
        super(w, h);

        name=new myJName(Loc("name"), "", 40, 110, 20);
        name.JTF.setFont(F_NameField);
        alias=new myJAlias(Loc("alias"), "", 0, 90, 20);
        unit=new myJUnit(Loc("unit"), "", 70, 150, TextFieldHeight);

        magnetobj=new myJMagnetObj(Loc("magnetobj"), "", 0, 150, TextFieldHeight);
        magnetpix=new myJMagnetPix(Loc("magnetpix"), "", 0, 150, TextFieldHeight);


        redcolor=new myJRed("R", "", 0, 78, TextFieldHeight);
        greencolor=new myJGreen("G", "", 0, 78, TextFieldHeight);
        bluecolor=new myJBlue("B", "", 0, 78, TextFieldHeight);


        X=new myJX("X :", "", 25, 300, TextFieldHeight);
        Y=new myJY("Y :", "", 25, 300, TextFieldHeight);
        
        X3D=new myJX3D("X :", "", 25, 300, TextFieldHeight);
        Y3D=new myJY3D("Y :", "", 25, 300, TextFieldHeight);
        Z3D=new myJZ3D("Z :", "", 25, 300, TextFieldHeight);
        XYlink=new myXYlink();
        XYZlink=new myXYZlink();
        Fx=new myJFx(Loc("fix"), false, 130, TextFieldHeight);
        Fx3D=new myJFx3D(Loc("fix"), false, 130, TextFieldHeight);
        AbsPos=new myJAbsolutePos(Loc("fixedinwindow"), false, 150, TextFieldHeight);
        ray=new myJR(Loc("fixedray"), "", 75, 300+XYlink.W, TextFieldHeight);
        ray3D=new myJR3D(Loc("fixedray"), "", 80, 300+XYlink.W, TextFieldHeight);
        RFx=new myJRFx(Loc("fix"), false, 300+XYlink.W, TextFieldHeight);
        RFx3D=new myJRFx3D(Loc("fix"), false, 300+XYlink.W, TextFieldHeight);
        angle=new myJA(Loc("fixedangle"), "", 75, 300+XYlink.W, TextFieldHeight);
        aFx=new myJAFx(Loc("fix"), false, 300+XYlink.W, TextFieldHeight);

        czvalue=new myJConditional("z", Loc("belongto"), "", 70, 150, TextFieldHeight);
        track=new myTRK(Loc("tracks"), false, 200, TextFieldHeight);
        Grid=new myJGrid(Loc("grid"), "", 80, 170, TextFieldHeight);

        Inside=new myJInside(Loc("inside"), false, 130, TextFieldHeight);

        text=new myJTextArea("", 330, 55);
        Exp=new myJExpression("Exp :", "", 40, 330, TextFieldHeight);
        prompt=new myJPrompt(Loc("expl"), "", 100, 330, TextFieldHeight);
        min=new myJSMin("min", "", 0, 113, TextFieldHeight);
        max=new myJSMax("max", "", 0, 113, TextFieldHeight);
        slider=new myJSSlider(Loc("showasslider"), false, 100, TextFieldHeight);
        slider.JCBX.setText(slider.JCBX.getText()+" :");
        sliderline=new ContentLine(330, TextFieldHeight);
        sliderline.add(slider);
        sliderline.add(min);
        sliderline.add(margin(4));
        sliderline.add(max);
        userfunc=new myJUserFunction(Loc("vars"), "", 90, 330, TextFieldHeight);
        fuey=new myJUserF_Y("f(x) =", "0", 90, 331, TextFieldHeight);
        eqxy=new myJEqXY(Loc("equationxy"), "0", 90, 470, TextFieldHeight);
        eqxychooser=new myJEqXYDHorChooser(Loc("equationxychooser"), 90, 250, TextFieldHeight);

//        btnline = new ContentLine(300, TextFieldHeight);

        magnetbtn=new myMagnetBtn(30, 30);
        arcbtn=new myArcBtn(100, TextFieldHeight);
        ptbindbtn=new myPtBindBtn(100, TextFieldHeight);
//        ptawaybtn=new myPtAwayBtn(100, TextFieldHeight);
        ptawaybtn=new myPtAwayBtn(Loc("setaway")+" :", "", 70, 150, TextFieldHeight);
        ptclosebtn=new myPtCloseBtn(Loc("setclose")+" :", "", 70, 150, TextFieldHeight);

        func=new myJFunction("", "", 0, 470, TextFieldHeight);
        fex=new myJF_X("x(t)=", "", 40, 470, TextFieldHeight);
        fey=new myJF_Y("", "", 40, 470, TextFieldHeight);
        fmin=new myJF_Min("Min :", "", 40, 176, TextFieldHeight);
        fmax=new myJF_Max("Max :", "", 40, 176, TextFieldHeight);
        fd=new myJF_d(Loc("step"), "", 40, 176, TextFieldHeight);
        fdmin=new myJF_DMin(Loc("step"), "", 50, 300, TextFieldHeight);
        fpt=new myJF_pt(Loc("pointsonly"), false, 200, TextFieldHeight);
        ftr=new myJF_Discrete(Loc("pointsonly"), false, 300, TextFieldHeight);

	bound_left=new myJMinBoundary(20, 2);
	bound_right=new myJMaxBoundary(20, 2);

        color=new myJColorLine(26, 2);
        colortype=new myJColorTypeLine(32, 3);
        segmentcode=new myJSegmentCodeLine(30, 3);
        type=new myJTypeLine(26, 2);
        circle=new myJCircleLine(24, 1);
        show=new myJShowLine(28, 1);
        boldlarge=new myJBoldLine(26, 2);
        area=new myJAreaLine(26, 1);
        angle0=new myJAngleLine0(26, 1);
        angle1=new myJAngleLine1(26, 2);
        filled=new myJFilledLine(26, 1);
        line=new myJLineLine(26, 2);

        dmintrack=new myJTrackDMinChooser(Loc("trackdmin"), 150, 250, TextFieldHeight);

        int cdw=115;
        csolid=new myJConditional("solid", cdw, TextFieldHeight);
        chidden=new myJConditional("hidden", cdw, TextFieldHeight);
        cnormal=new myJConditional("normal", cdw, TextFieldHeight);
        cthick=new myJConditional("thick", cdw, TextFieldHeight);
        cthin=new myJConditional("thin", cdw, TextFieldHeight);
        cblack=new myJConditional("black", cdw, TextFieldHeight);
        cgreen=new myJConditional("green", cdw, TextFieldHeight);
        cblue=new myJConditional("blue", cdw, TextFieldHeight);
        cbrown=new myJConditional("brown", cdw, TextFieldHeight);
        ccyan=new myJConditional("cyan", cdw, TextFieldHeight);
        cred=new myJConditional("red", cdw, TextFieldHeight);
//        cinvisible=new myJConditional("invisible",Loc("invisible"),"",70,true);
        csuperhidden=new myJConditional("superhidden", cdw, TextFieldHeight);
        cshowname=new myJConditional("showname", cdw, TextFieldHeight);
        cshowvalue=new myJConditional("showvalue", cdw, TextFieldHeight);
        cbackground=new myJConditional("background", cdw, TextFieldHeight);


        HashSet forwardKeys=new HashSet();
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
//        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        HashSet backwardKeys=new HashSet();
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK));
        this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
    }

    public static ConstructionObject getObject(){
	return O;
    }
    public static void setObject(ConstructionObject o){
	O = o;
    }

    public void multiple_setType(int k) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setType(k);
        }
    }

    public void multiple_setColorType(int k) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setColorType(k);
        }
    }

    public void multiple_setColor(int k) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setColor(k);
        }
    }

    public void multiple_setColor(int k, Color col) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setColor(k, col);
        }
    }

    public void multiple_setHidden(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setHidden(b);
        }
    }

    public void multiple_setShowValue(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setShowValue(b);
        }
    }

    public void multiple_setShowName(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setShowName(b);
        }
    }

    public void multiple_setBold(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setBold(b);
        }
    }

    public void multiple_setLarge(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setLarge(b);
        }
    }

    public void multiple_addConditional(String tag, String expr) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            Expression myE=new Expression(expr, MultipleObjects.get(i).getConstruction(), MultipleObjects.get(i), null, false);
            MultipleObjects.get(i).addConditional(tag, myE);
        }
    }

    public void multiple_clearConditional(String tag) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).clearConditional(tag);
        }
    }

    public void multiple_setUnit(String str) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setUnit(str);
            MultipleObjects.get(i).setShowValue(true);
        }
    }

    public void multiple_setTracked(boolean b) {
        for (int i=0; i<MultipleObjects.size(); i++) {
            MultipleObjects.get(i).setTracked(b);
        }
    }

    public void setObjects(ArrayList<ConstructionObject> v) {

        if (JZirkelCanvas.getCurrentZF()==null) {
            return;
        }

        MultipleObjects=v;
        ZF=JZirkelCanvas.getCurrentZF();
        ZC=JZirkelCanvas.getCurrentZC();
        Cn=ZC.getConstruction();

        // Ici on crée un objet virtuel (point) dont chaque changement
        // de propriété sera retranscrit dans les objets réels de la
        // sélection multiple :
        PointObject p=new PointObject(Cn, "MULTIPLE");
        O=p;
        String[] tab=O.getClass().getName().split("\\.");
        typecode=","+tab[tab.length-1]+",";
        this.clearAll();
        addAspectIcons();
        addConditionals();
        color.deselectAll();
        colortype.deselectAll();
        type.deselectAll();
        this.revalidate();
        this.repaint();
    }

    public void setObject(ConstructionObject o, boolean forcevisible, boolean forcefocus) {

        if (JZirkelCanvas.getCurrentZF()==null) {
            return;
        }
        MultipleObjects=null;
        ZF=JZirkelCanvas.getCurrentZF();
        ZC=JZirkelCanvas.getCurrentZC();
        O=o;



        Cn=ZC.getConstruction();
        String[] tab=O.getClass().getName().split("\\.");
        typecode=","+tab[tab.length-1]+",";
        this.clearAll();

        addTopStandardLine();

//        addToNum(new myRubSep());

        addCoords();
        if (O instanceof VectorObject &&((VectorObject) O).is3D()) {
        	JEricPanel rub=new myRub();
            rub.add(margintop(2));
            Fx3D.init();
            rub.add(Fx3D);
            rub.add(margintop(Fx3D.H+2));
            addToNum(rub);
            rub.add(margintop(2*Fx3D.H+4));
            addToNum(new myRubSep());
        }
        addPointsGoodies();
        addRadius();
        addAngle();

        addText();
        addExpressionLine();
        addUserFunction();
        addEquationXY();
        addButtons();
        addFunction();
        addTrack();
        addAspectIcons();

        addConditionals();

        this.revalidate();
        this.repaint();



        if (forcefocus) {
            if (isExpression()) {
                if (O.isSlider()) {
                    FocusAndSelect(min.JTF, forcevisible);
                } else {
                    FocusAndSelect(Exp.JTF, forcevisible);
                }
            } else if (isText()) {
                FocusAndSelect(text.JTX, forcevisible);
            } else if (isUserFunction()) {
                FocusAndSelect(fuey.JTF, forcevisible);
            } else if (isFunction()) {
                FocusAndSelect(fey.JTF, forcevisible);
            } else if ((ACircle.indexOf(typecode)!=-1)) {
                FocusAndSelect(ray.JTF, forcevisible);
            } else if ((ASegment.indexOf(typecode)!=-1)) {
                FocusAndSelect(ray.JTF, forcevisible);
            } else if ((ASegment.indexOf(typecode)!=-1)&&((SegmentObject)O).is3D()) {
                FocusAndSelect(ray3D.JTF, forcevisible);
            } else if ((AAngle.indexOf(typecode)!=-1)) {
                FocusAndSelect(angle.JTF, forcevisible);
            } else {
                FocusAndSelect(name.JTF, forcevisible);
            }
            return;
        }
        FocusAndSelect(name.JTF, false);
    }

    public void refresh() {
        if (O instanceof PointObject) {
            magnetobj.init();
            if (!O.isStrongSelected()) {
                magnetbtn.setSelected(false);
            }
            ptawaybtn.init();
            ptclosebtn.init();
        }

    }

    public void clearme() {
        O=null;
        Cn=null;
        this.clearAll();
	//System.out.println(Panes.size());

//	for (int i = 0; i < Panes.size(); i++) {
//	    final JEricPanel mypane = (JEricPanel) Panes.get(i);
//	    mypane.removeAll();
//	    String str = Global.Loc("props.help");
//	    str = str.replace("<br>", " ");
//	    final JLabel hlp = new JLabel(str);
//	    hlp.setOpaque(false);
//	    hlp.setFont(new Font("System", 0, 12));
//	    hlp.setHorizontalAlignment(SwingConstants.CENTER);
//	    hlp.setVerticalAlignment(SwingConstants.CENTER);
//	    fixsize(hlp, 600, RightPanel.getSize().height);
//	    hlp.setForeground(new Color(100, 100, 100));
//	    mypane.add(hlp);
//	    mypane.revalidate();
//	}


//	final JEricPanel mypane = new JEricPanel();
//
//        String str = Global.Loc("props.help");
//        str = str.replace("<br>", " ");
//
//        JLabel hlp = new JLabel(str);
//        hlp.setOpaque(false);
//        hlp.setFont(new Font("System", 0, 12));
//        hlp.setHorizontalAlignment(SwingConstants.CENTER);
//        hlp.setVerticalAlignment(SwingConstants.CENTER);
//        fixsize(hlp, 600, RightPanel.getSize().height);
//        hlp.setForeground(new Color(100,100,100));
//	this.add(hlp) ;
//	mypane.add(hlp);
//	mypane.revalidate();

        repaint();
    }

    private void FocusAndSelect(final JTextComponent cp, final boolean forcevisible) {
        SwingUtilities.invokeLater(new Runnable() {

	    @Override
            public void run() {
                if (forcevisible) {
                    selectTab(1);
                }
                cp.requestFocus();
                cp.selectAll();
            }
        });
    }

    private void addTopStandardLine() {
        JEricPanel rubname=new myRub();
        name.init();
        rubname.add(name);
        addMain(margin(5));
        addMain(rubname);
        addMain(margin(5));
        JEricPanel rub=new myRub();
        alias.init();
        rub.add(alias);

        addMain(rub);
        if (JZirkelCanvas.getCurrentZC().is3D()&&(O.getName().equals("O")||O.getName().equals("X")||O.getName().equals("Y")||O.getName().equals("Z")||O.getName().equals("s6")||O.getName().equals("s7")||O.getName().equals("s8"))) { // Dibs
                name.setEditable(false);
                alias.setEditable(false);
            }
    }

    public static void refreshCoords(){
	if(O==null)
	    return;
	if(!isNotObjectWithCoords()){
	    X.init();
	    Y.init();
	    X3D.init();
	    Y3D.init();
	    Z3D.init();
	    if(ASegment.indexOf(typecode)==14) {
		if (((SegmentObject) O).is3D())   ray3D.init();
		else ray.init();
	    }
	} else if(ASegment.indexOf(typecode)!=-1 || ACircle.indexOf(typecode)!=-1){
		if (O instanceof SegmentObject&&((SegmentObject) O).is3D())   ray3D.init();
		else ray.init();
	} else if(AAngle.indexOf(typecode)!=-1){
	    angle.init();
	}
    }


    private void addCoords() {
        JEricPanel rub=new myRub();
        if (isNotObjectWithCoords()) {
            return;
        }
	if(O instanceof SegmentObject && !((SegmentObject) O).isArrow())
	    return;
	rub.add(margintop(2));
    if (((APoint.indexOf(typecode)!=-1)&&((PointObject) O).is3D())&&O!=ZC.getConstruction().find("O")||(O instanceof VectorObject &&((VectorObject) O).is3D())) {
    	X3D.init();
        Y3D.init();
        Z3D.init();
        if (O instanceof PointObject&&((PointObject) O).isPointOn()){
            X3D.setEditable(false);
            Y3D.setEditable(false);
            Z3D.setEditable(false);
        }
        else {
            X3D.setEditable(true);
            Y3D.setEditable(true);
            Z3D.setEditable(true);
        }
    	JEricPanel coordsCOL=new JEricPanel();
        coordsCOL.setOpaque(false);
        coordsCOL.setLayout(new BoxLayout(coordsCOL, BoxLayout.Y_AXIS));
        JEricPanel coordsLNE=new JEricPanel();
        coordsLNE.setOpaque(false);
        coordsLNE.setLayout(new BoxLayout(coordsLNE, BoxLayout.X_AXIS));
        coordsLNE.setAlignmentX(0F);
        coordsCOL.add(X3D);
        coordsCOL.add(margintop(1));
        coordsCOL.add(Y3D);
        coordsCOL.add(margintop(1));
        coordsCOL.add(Z3D);
        coordsLNE.add(coordsCOL);
        coordsLNE.add(XYZlink);
        rub.add(coordsLNE);     
    }
    else {
    X.init();
    Y.init();
    JEricPanel coordsCOL=new JEricPanel();
    coordsCOL.setOpaque(false);
    coordsCOL.setLayout(new BoxLayout(coordsCOL, BoxLayout.Y_AXIS));
    JEricPanel coordsLNE=new JEricPanel();
    coordsLNE.setOpaque(false);
    coordsLNE.setLayout(new BoxLayout(coordsLNE, BoxLayout.X_AXIS));
    coordsLNE.setAlignmentX(0F);
    coordsCOL.add(X);
    coordsCOL.add(margintop(1));
    coordsCOL.add(Y);
    coordsLNE.add(coordsCOL);
    coordsLNE.add(XYlink);
    rub.add(coordsLNE);
 //	rub.add(margintop(1));
 //	rub.add(AbsPos);
    rub.add(margintop(1));

    ContentLine myline=new ContentLine(X.W+XYlink.W, Fx.H);
    rub.add(myline);

    // if it's a Point : (not moveable) or (a PointOn) or a Vector
    if (!(O.fixed())) {
    if (((APoint.indexOf(typecode)!=-1)&&((!((PointObject) O).moveable())||((PointObject) O).isPointOn()))) {
	X.setEditable(false);
            Y.setEditable(false);
        } else {
	X.setEditable(true);
            Y.setEditable(true);
            Fx.init();
            myline.add(Fx);
        }
    } else {
        X.setEditable(true);
        Y.setEditable(true);
    //un vecteur ou tout autre objet mais pas un segment
        if(ASegment.indexOf(typecode)==14 || ASegment.indexOf(typecode)==-1){
	Fx.init();
	myline.add(Fx);
    }
    }

    //if it's a point inside a polygon or circle :
    if (((APoint.indexOf(typecode)!=-1))&&((PointObject) O).isPointOn()) {
        X.setEditable(false);
        Y.setEditable(false);
        if (((PointObject) O).getBound() instanceof InsideObject) {
            Inside.init();
            myline.add(Inside);
        }
    }


    if (((APoint.indexOf(typecode)!=-1))&&((PointObject) O).moveable()&&(!(((PointObject) O).isPointOn()))) {
        Grid.setEditable(true);
        X.setEditable(true);
        Y.setEditable(true);
        Grid.init();

        myline.add(Grid);
    }

    if (((APoint.indexOf(typecode)!=-1))&&(((PointObject) O).isPointOn())) {
        Grid.setEditable(true);
        Grid.init();
//        myline.add(margin(Fx.W));
        myline.add(Grid);
    }

    }
    addToNum(rub);
    addToNum(new myRubSep());
    
}
    private void addPointsGoodies() {
        if ((APoint.indexOf(typecode)==-1)) {
            return;
        }
        if ((!O.fixed())&&(!((PointObject) O).moveable())) {
            return;
        }
        JEricPanel rub=new myRub();
        rub.add(margintop(2));
        if (((O instanceof PointObject&&((PointObject) O).is3D()&&((PointObject) O).getBound()==null)&&O!=ZC.getConstruction().find("O"))||(O instanceof MidpointObject&&((MidpointObject) O).is3D())) { //Dibs
        	Fx3D.init();
            rub.add(Fx3D);
            rub.add(margintop(Fx3D.H+2));
          //if it's a point inside a polygon or circle :
            if (((APoint.indexOf(typecode)!=-1))&&((PointObject) O).isPointOn()) {
                X.setEditable(false);
                Y.setEditable(false);
                if (((PointObject) O).getBound() instanceof InsideObject) {
                    Inside.init();
                    rub.add(Inside);
                }
            }
            else {
            	PointObject p=(PointObject) O;
            	if (p.moveablePoint()) {
            	ptbindbtn.init();
                rub.add(ptbindbtn);
            	}
            }
            addToNum(rub);
            rub.add(margintop(2*Fx3D.H+4));
            addToNum(new myRubSep());
            return;
        }
        
        if (!((PointObject) O).isPointOn()) {
            AbsPos.init();
            rub.add(AbsPos);
            rub.add(margintop(AbsPos.H+2));
        } else {
            rub.add(margintop(2*AbsPos.H+2));
        }

        PointObject p=(PointObject) O;
        if (p.moveablePoint()) {
            ptbindbtn.init();
            rub.add(ptbindbtn);
        }

        addToNum(rub);
        rub.add(margintop(2*AbsPos.H+4));
        addToNum(new myRubSep());
    }

    private void addRadius() {
        if ((ACircle.indexOf(typecode)==-1)&&(ASegment.indexOf(typecode)==-1)) {
            return;
        }
        if (O instanceof SegmentObject&&((SegmentObject) O).is3D()) {
            JEricPanel rub=new myRub(200,40);
        	ray3D.init();
        	RFx3D.init();
        	String mytxt=Loc("fixedsegment3D");
        	ray3D.setLabelTxt(mytxt);
        	rub.add(ray3D);
        	//rub.add(margintop(ray3D.H+2));
        	rub.add(margintop(2));
        	// rub.add(RFx3D); ! Dibs : à gérer plus tard... déplacement en mode longueur fixe
        	addToNum(rub);
            addToNum(new myRubSep());
        }
        else if (O.canFix()) {
            JEricPanel rub=new myRub();
    		ray.init();
    		RFx.init();
    		String mytxt=(ACircle.indexOf(typecode)!=-1)?Loc("fixedray"):Loc("fixedsegment");
    		ray.setLabelTxt(mytxt);
    		rub.add(ray);
    		rub.add(margintop(ray.H+2));
    		rub.add(RFx);
            addToNum(rub);
            addToNum(new myRubSep());
        }

    }

    private void addAngle() {
        if (AAngle.indexOf(typecode)==-1) {
            return;
        }
        if (O.canFix()) {
            JEricPanel rub=new myRub();
            angle.init();
            aFx.init();
            rub.add(margintop(2));
            rub.add(angle);
            rub.add(margintop(angle.H+2));
            if (O instanceof AngleObject && ((AngleObject) O).getP1().is3D()&&((AngleObject) O).getP2().is3D()) {
                angle.setEditable(false);
            	}
            else {
            	rub.add(aFx);
            	angle.setEditable(true);
            	}
            addToNum(rub);
            addToNum(new myRubSep());
        }

    }

    public void addText() {
        if (AText.indexOf(typecode)==-1) {
            return;
        }
        JEricPanel rub=new myRub();
        text.init();
        rub.add(text);
        addToNum(rub);
    }

    private void addExpressionLine() {
        if (AExpression.indexOf(typecode)==-1) {
            return;
        }
        JEricPanel rub=new myRub();
        rub.add(margintop(2));
        Exp.init();
        prompt.init();
        slider.init();
        max.init();
        min.init();
        rub.add(Exp);
        rub.add(margintop(1));
        rub.add(sliderline);

        rub.add(margintop(1));
        rub.add(prompt);

        addToNum(rub);
    }

    private void addTrack() {
        if (ATrack.indexOf(typecode)==-1) {
            return;
        }
        JEricPanel rub=new myRub();
        rub.add(margintop(2));
        dmintrack.init();
        rub.add(dmintrack);
        rub.add(margintop(dmintrack.H*2+4));
        addToNum(rub);
    }

    private void addUserFunction() {
        if (AUserFunction.indexOf(typecode)==-1) {
            return;
        }

        userfunc.init();
        addToNum(userfunc);
    }

    private void addEquationXY() {
        if (AEquationXY.indexOf(typecode)==-1) {
            return;
        }
        JEricPanel rub=new myRub();
        rub.add(margintop(2));

        eqxy.init();
        eqxychooser.init();
        rub.add(eqxy);
        rub.add(margintop(eqxy.H+2));
        rub.add(eqxychooser);
        addToNum(rub);
    }

    private void addFunction() {
        if (AFunction.indexOf(typecode)==-1) {
            return;
        }

        func.init();
        addToNum(func);

        addToNum(new myRubSep());

        JEricPanel rub=new myRub();
        fmin.init();
        fmax.init();
        fd.init();

        rub.add(margintop(2));
        rub.add(fmin);
        rub.add(margintop(1));
        rub.add(fmax);
        rub.add(margintop(1));
        rub.add(fd);
        rub.add(margintop(1));

        addToNum(rub);

	addToNum(new myRubSep());

	rub=new myRub();
	rub.setLayout(null);

	JLabel title = new JLabel("Bornes :");
	title.setFont(F_Label);
	bound_left.init();
	JLabel img = new JLabel();
	URL myurl=getClass().getResource("/eric/GUI/icons/bar/fnct.png");
	img.setIcon(new ImageIcon(myurl));
	bound_right.init();

	rub.add(title);
	rub.add(bound_left);
	rub.add(img);
	rub.add(bound_right);

	title.setBounds(0, 2, 50, 15);
	bound_left.setBounds(0, 17, 20, 40);
	img.setBounds(25, 21, 32, 32);
	bound_right.setBounds(62, 17, 20, 40);

	addToNum(rub);

//        func.init();


//        content.add(func);
//        content.add(new mySep());
    }

    public void addButtons() {

        JEricPanel rub=new myRub();
        rub.add(margintop(2));

        if (ACircle.indexOf(typecode)!=-1) {
            arcbtn.init();
            rub.add(arcbtn);
            rub.add(margintop(1));
        }
        if (APoint.indexOf(typecode)!=-1) {
            PointObject p=(PointObject) O;
            if ((p.moveablePoint())&&(!p.isPointOn())) {
//                ptbindbtn.init();
                magnetobj.init();
                magnetpix.init();
                magnetbtn.init();
//                rub.add(ptbindbtn);
//                rub.add(margintop(1));

                JEricPanel jp=new JEricPanel();
                jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                jp.setOpaque(false);
                jp.setAlignmentX(0F);
                jp.add(magnetbtn);
                jp.add(margin(15));
                JEricPanel rub2=new myRub();
                rub2.add(magnetobj);
                rub2.add(margintop(5));
                rub2.add(magnetpix);
                jp.add(rub2);

                rub.add(jp);

            } else if (p instanceof IntersectionObject&&((IntersectionObject) p).isSwitchable()) {
                ptawaybtn.init();
                ptclosebtn.init();
                rub.add(ptawaybtn);
                rub.add(margintop(1));
                rub.add(ptclosebtn);
            }
        }
        if (rub.getComponentCount()>1) {
            JEricPanel myspacer=new JEricPanel();
            myspacer.setOpaque(false);
//            addToNum(myspacer);
            addToNum(rub);
            addToNum(margin(3));
        }

    }

    private void addMultipleAspectIcons() {
        addAspectIcons();
        addPointsGoodies();
    }

    private void addAspectIcons() {


        JEricPanel rub=new myRub();
        rub.add(margintop(2));
        redcolor.init();
        greencolor.init();
        bluecolor.init();
        rub.add(redcolor);
        rub.add(margintop(1));
        rub.add(greencolor);
        rub.add(margintop(1));
        rub.add(bluecolor);
        addToAspect(margin(2));
        addToAspect(rub);
        addToAspect(margin(5));

        color.init();
        addToAspect(color);


        addToAspect(new myRubSep());
        colortype.setSelect(O.getColorType(true));
        addToAspect(colortype);
        addToAspect(new myRubSep());
        show.init();
        addToAspect(show);

        if (O.canDisplayName()) {
            boldlarge.init();
            addToAspect(new myRubSep());
            addToAspect(boldlarge);
        }

        if ((ASegment.indexOf(typecode))!=-1) {
            segmentcode.init();
            addToAspect(new myRubSep());
            addToAspect(segmentcode);
        }


        if ((APoint.indexOf(typecode)!=-1)||(AFunction.indexOf(typecode)!=-1)||(ATrack.indexOf(typecode)!=-1)) {
            type.setSelect(O.getType());
            addToAspect(new myRubSep());
            addToAspect(type);
        } else if (AAngle.indexOf(typecode)!=-1) {
            angle1.setSelect(O.getDisplaySize());
            addToAspect(new myRubSep());
            addToAspect(angle1);
        }

        if (ACircle.indexOf(typecode)!=-1) {
            circle.init();
            addToAspect(new myRubSep());
            addToAspect(circle);
        } else if (ALine.indexOf(typecode)!=-1) {
            line.init();
            addToAspect(new myRubSep());
            addToAspect(line);
        } else if (AAngle.indexOf(typecode)!=-1) {
            angle0.init();
            addToAspect(new myRubSep());
            addToAspect(angle0);
        } else if (AFunction.indexOf(typecode)!=-1) {
            filled.init();
            addToAspect(new myRubSep());
            addToAspect(filled);

        } else if (ATrack.indexOf(typecode)!=-1) {
            filled.init();
            addToAspect(new myRubSep());
            addToAspect(filled);
        } else if (AArea.indexOf(typecode)!=-1) {
            area.init();
            addToAspect(new myRubSep());
            addToAspect(area);
        }

        JEricPanel myspacer=new JEricPanel();
        myspacer.setOpaque(false);

        rub=new myRub();
        rub.add(margintop(2));

        czvalue.init();
        rub.add(czvalue);
        if (O.hasUnit()) {
            rub.add(margintop(1));
            unit.init();
            rub.add(unit);
        }
        if ((AFunction.indexOf(typecode)!=-1)||(ATrack.indexOf(typecode)!=-1)) {
            rub.add(margintop(1));
            fpt.init();
            rub.add(fpt);
        }
        rub.add(margintop(1));
        track.init();
    	rub.add(track);

//        if (APoint.indexOf(typecode)!=-1) {
//            track.init();
//            rub.add(track);
//        }

//        addToAspect(new myRubSep());
        addToAspect(myspacer);
        addToAspect(rub);
        addToAspect(margin(3));
    }

    private void addConditionals() {
        JEricPanel rub=new myRub();
        chidden.init();
        csuperhidden.init();
        csolid.init();
        rub.add(margintop(1));
        rub.add(chidden);
        rub.add(margintop(1));
        rub.add(csuperhidden);
        rub.add(margintop(1));
        rub.add(csolid);
        addToConditional(rub);
        addToConditional(new myRubSep(14));
        rub=new myRub();
        cnormal.init();
        cthick.init();
        cthin.init();
        rub.add(margintop(1));
        rub.add(cnormal);
        rub.add(margintop(1));
        rub.add(cthick);
        rub.add(margintop(1));
        rub.add(cthin);
        addToConditional(rub);
        addToConditional(new myRubSep(14));
        rub=new myRub();
        cshowname.init();
        cshowvalue.init();
//        cbackground.init();
        rub.add(margintop(1));
        rub.add(margintop(1));
        rub.add(cshowname);
        rub.add(margintop(1));
        rub.add(cshowvalue);
//        rub.add(cbackground);
        rub.add(margintop(cshowname.H+1));
        addToConditional(rub);
        addToConditional(new myRubSep(14));
        rub=new myRub();
        cblack.init();
        cgreen.init();
        cblue.init();
        rub.add(margintop(1));
        rub.add(cblack);
        rub.add(margintop(1));
        rub.add(cgreen);
        rub.add(margintop(1));
        rub.add(cblue);
        addToConditional(rub);
        addToConditional(new myRubSep(14));
        rub=new myRub();
        cbrown.init();
        ccyan.init();
        cred.init();
        rub.add(margintop(1));
        rub.add(cbrown);
        rub.add(margintop(1));
        rub.add(ccyan);
        rub.add(margintop(1));
        rub.add(cred);
        addToConditional(rub);

    }

    public void addToNum(JComponent cp) {
        add(cp, 1);
    }

    public void addToAspect(JComponent cp) {
        add(cp, 0);
    }

    public void addToConditional(JComponent cp) {
        add(cp, 2);
    }
    // A transférer dans JTabPanel pour éviter les "Overridable method call in construtor"

    public String Loc(String s) {
        return Global.Loc("props."+s);
    }

    static void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    static JEricPanel margin(int w) {
        JEricPanel mypan=new JEricPanel();
        fixsize(mypan, w, 1);
        mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0F);
        mypan.setAlignmentY(0F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    JEricPanel margintop(int h) {
        JEricPanel mypan=new JEricPanel();
        fixsize(mypan, 1, h);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    private boolean isExpression() {
        boolean bool=(AExpression.indexOf(typecode)!=-1);
        return (bool);
    }

    private boolean isText() {
        boolean bool=(AText.indexOf(typecode)!=-1);
        return (bool);
    }

    private boolean isUserFunction() {
        boolean bool=(AUserFunction.indexOf(typecode)!=-1);
        return (bool);
    }

    private boolean isFunction() {
        boolean bool=(AFunction.indexOf(typecode)!=-1);
        return (bool);
    }

    private static boolean isNotObjectWithCoords() {
        boolean bad=(APoint.indexOf(typecode)==-1);
        //bad&=(ASegment.indexOf(typecode)==-1);
	bad&=(ASegment.indexOf(typecode)!=14);
        bad&=(AExpression.indexOf(typecode)==-1);
        bad&=(AText.indexOf(typecode)==-1);
        bad&=(AUserFunction.indexOf(typecode)==-1);
        return (bad);
    }

    private boolean isAbsolutePoint() {
        boolean b=O.fixed();
        if (b) {
            String x=O.getEXpos(), y=O.getEY();
            b=b&&x.startsWith("(windoww/(windoww-d(windoww)))*(");
            b=b&&x.endsWith("-windowcx)+windowcx+d(windowcx)");
            b=b&&y.startsWith("(windoww/(windoww-d(windoww)))*(");
            b=b&&y.endsWith("-windowcy)+windowcy+d(windowcy)");
        }
        return b;
    }

    boolean isValidExpression(String myexp) {
        boolean bool=true;
        try {
            Expression exp=new Expression(myexp, O.getConstruction(), O);
            if (!(exp.isValid())) {
                bool=false;
            }
        } catch (Exception ex) {
            bool=false;
        }
        return bool;
    }

    double ValueOf(String myexp) {
        double rep;
        try {
            Expression exp=new Expression(myexp, O.getConstruction(), O);
            rep=exp.getValue();
        } catch (Exception ex) {
            rep=0.0;
        }
        return rep;
    }
//    static Construction V_CONST=new Construction();
//    static UserFunctionObject V_FONC=new UserFunctionObject(V_CONST);
    static double VARS[]={1, 2, 3, 4};

    // set text :
    public static String Point_To_Comma(String mynum, Construction C, boolean check) {
        if (C==null) {
            return mynum;
        }
        if (check) {
            try {
                UserFunctionObject V_FONC=new UserFunctionObject(C);
                V_FONC.setExpressions("x y z t", mynum.replace("invalid", "1"));
                double d=V_FONC.evaluateF(VARS);

            } catch (Exception ex) {
                return mynum;
            }
        }
        if (Global.isDecimalWithComma()) {
            String s=mynum.replace(",", ";");
            return s.replace(".", ",");
        } else {
            return mynum;
        }
    }

    // get text :
    public static String Comma_To_Point(String mynum, Construction C, boolean check) {
        if (C==null) {
            return mynum;
        }
        String s=mynum.replace(",", ".");
        s=s.replace(";", ",");
        if (check) {
            try {
                UserFunctionObject V_FONC=new UserFunctionObject(C);
                V_FONC.setExpressions("x y z t", s.replace("invalid", "1"));
                double d=V_FONC.evaluateF(VARS);
            } catch (Exception ex) {
                return mynum;
            }
        }
        if (Global.isDecimalWithComma()) {
            return s;
        } else {
            return mynum;
        }
    }

    static class MyComboBoxUI extends MetalComboBoxUI {

        public static ComponentUI createUI(JComponent c) {
            return new MyComboBoxUI();
        }
    }

    class myRub extends JEricPanel {

        public myRub() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setAlignmentX(0F);
            this.setOpaque(false);
        }

        public myRub(String rubname) {
            this();
        }

        public myRub(int width, int height) {
            this("");
            fixsize(this, width, height);
        }
    }

    class myRubSep extends JEricPanel {

	@Override
        public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            ImageIcon myicon=new ImageIcon(getClass().getResource("gui/sep.png"));
            java.awt.Dimension d=this.getSize();
            int x=(d.width+myicon.getIconWidth())/2;
            g.drawImage(myicon.getImage(), x, 0, myicon.getIconWidth(), d.height, this);
        }

        public myRubSep() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setAlignmentX(0f);
            this.setAlignmentY(0.5f);
            this.setOpaque(false);
            fixsize(this, HRubSeparatorWidth, HRubSeparatorHeight);
        }

        public myRubSep(int width) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setAlignmentX(0f);
            this.setAlignmentY(0.5f);
            this.setOpaque(false);
            fixsize(this, width, HRubSeparatorHeight);
        }
    }

    class ContentLine extends JEricPanel {

        int W, H;

        public ContentLine() {
            setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            setAlignmentX(0F);
            setAlignmentY(0F);
            setFocusable(false);
            setOpaque(false);
        }

        public ContentLine(int width, int height) {
            this();
            W=width;
            H=height;
            fixsize(this, width, height);
        }
    }

    class myJLine extends ContentLine {

        String contextHelp="";
        ArrayList carPopupMenuItemAllowed=new ArrayList();
        JTextFieldPopup carPopup=null;
        JButton carBTN=null;
        JLabel myTXT;
        myJTextField JTF;
        JCheckBox JCBX;
        JButton JBTN;
        int CW;
        boolean Cinside;
        String InitValue="";

        // With text field :
        public myJLine(String comment, String txt, int comwidth, int width, int height) {
            super(width, height);
            CW=comwidth;
            Cinside=((!comment.equals(""))&&(comwidth==0)); //comment must be inside the JTF as an init value
            if (Cinside) {
                InitValue=comment;
                InitValue=InitValue.trim();
                InitValue=InitValue.replace(":", "");
                InitValue=InitValue.trim();
                InitValue="<"+InitValue+">";
            }

            addnewlabel(comment, comwidth, H);
            JTF=new myJTextField(txt);
            JTF.setFont(F_TextField);
            JTF.setForeground(C_TextField);
            JTF.setBackground(new Color(245, 246, 255));
            JTF.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));

            JTF.addMouseListener(new MouseAdapter() {

		@Override
                public void mousePressed(MouseEvent arg0) {
                    doHelp();
                    doEnter();
                }
            });
            JTF.addKeyListener(new KeyAdapter() {

		@Override
                public void keyPressed(KeyEvent e) {
                    if ((Cinside)&&(JTF.getText().equals(InitValue))) {
                        JTF.setForeground(C_TextField_OFF);
                    } else {
                        JTF.setForeground(C_TextField);
                    }
                }

		@Override
                public void keyReleased(KeyEvent e) {
                    doAction(e.getComponent());
                }
            });
            JTF.addFocusListener(new FocusAdapter() {

		@Override
                public void focusGained(FocusEvent e) {
                    if ((carPopup!=null)&&(carPopup.isVisible())) {
                        return;
                    }
                    if ((Cinside)&&(JTF.getText().equals(InitValue))) {
                        JTF.setText("");
                        JTF.setForeground(C_TextField);
                    }
                    if (carBTN!=null) {
                        carBTN.setEnabled(true);
                    }
                    JTF.selectAll();

                }

		@Override
                public void focusLost(FocusEvent e) {
                    if ((carPopup!=null)&&(carPopup.isVisible())) {
                        return;
                    }
                    if ((Cinside)&&(JTF.getText().equals(""))) {
                        JTF.setText(InitValue);
                        JTF.setForeground(C_TextField_OFF);
                    }
                    if (carBTN!=null) {
                        carBTN.setEnabled(false);
                    }
                    doQuitMe(e.getComponent());
                }
            });



            this.add(JTF);
        }

        public myJLine(String comment, String txt, int comwidth, int width, int height, boolean withhelp) {
            this(comment, txt, comwidth, width, height);
            carPopup=new JTextFieldPopup(JTF);
            carPopup.addPopupMenuListener(new PopupMenuListener() {

		@Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
                }

		@Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
                    doAction(JTF);
                }

		@Override
                public void popupMenuCanceled(PopupMenuEvent arg0) {
                }
            });
            ImageIcon carimg=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/carbtn.png"));
            ImageIcon carimg_dis=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/carbtn_dis.png"));
            carBTN=new JButton(carimg);
            carBTN.setDisabledIcon(carimg_dis);
//            carbtn.setRolloverIcon(closeoverimg);
            carBTN.setBorder(BorderFactory.createEmptyBorder());
            carBTN.setOpaque(false);
            carBTN.setContentAreaFilled(false);
            carBTN.setFocusable(false);
            carBTN.addMouseListener(new MouseAdapter() {

		@Override
                public void mousePressed(MouseEvent e) {
                    doShowPopup(e);
                }
            });
            carBTN.setEnabled(false);
            fixsize(carBTN, carimg.getIconWidth(), carimg.getIconHeight());
            this.add(margin(2));
            this.add(carBTN);
        }

        // with checkbox :
        public myJLine(String comment, boolean checked, int width, int height) {
            super(width, height);
            this.setFocusable(false);
            comment=comment.replace(":", "");
            comment=comment.trim();
            JCBX=new JCheckBox("");
            JCBX.setOpaque(false);
            JCBX.setFocusable(false);
            JCBX.setFont(F_CheckBox);
            fixsize(JCBX, W, H);
            JCBX.addMouseListener(new MouseAdapter() {

		@Override
                public void mousePressed(MouseEvent e) {
                    doAction(e.getComponent());
                }
            });
            JCBX.setIcon(new ImageIcon(getClass().getResource("gui/chkboxOFF.png")));
            JCBX.setSelectedIcon(new ImageIcon(getClass().getResource("gui/chkboxON.png")));
            JCBX.setText(comment);
            this.add(JCBX);
        }

        // with button :
        public myJLine(String comment, int btnwidth, int width, int height) {
            super(width, height);
            JBTN=new JButton();
            JBTN.setFont(F_Button);
//            JBTN.setBorder(BorderFactory.createEtchedBorder());
            fixsize(JBTN, btnwidth, H);
            JBTN.addMouseListener(new MouseAdapter() {

		@Override
                public void mousePressed(MouseEvent e) {
                    doAction(e.getComponent());
                }
            });
            JBTN.setText(comment);
            this.add(JBTN);
        }

        // TxtField with caracter palette btn
        public void addnewlabel(String comment, int w, int h) {
            myTXT=new JLabel(comment);
            myTXT.setFocusable(false);
            myTXT.setFont(F_Label);
            fixsize(myTXT, w, h);
            myTXT.setHorizontalAlignment(SwingConstants.LEFT);
            myTXT.setVerticalAlignment(SwingConstants.CENTER);
            this.add(myTXT);
        }

        public void setLabelTxt(String lbltxt) {
            myTXT.setText(lbltxt);
        }

        public void setSelected(boolean on) {
            JCBX.setSelected(on);
        }

        public boolean isSelected() {
            return JCBX.isSelected();
        }

        public void setText(String txt) {
            JTF.setText(Point_To_Comma(txt, Cn, true));
        }

        public String getText() {
            return Comma_To_Point(JTF.getText(), Cn, true);
        }

        public void setEditable(boolean bool) {
            if (bool) {
                JTF.setForeground(C_TextField);
            } else {
                JTF.setForeground(C_TextField_OFF);
            }
            JTF.setFocusable(bool);
            JTF.setEditable(bool);
        }

        public void setInitValue() {
            if ((Cinside)&&(JTF.getText().equals(""))) {
                JTF.setText(InitValue);
                JTF.setForeground(C_TextField_OFF);
                JTF.setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                JTF.setForeground(C_TextField);
            }
        }

        public void doAction(Component cp) {
        }

        public void doQuitMe(Component cp) {
        }

        public void doEnter() {
        }

        public void doShowPopup(MouseEvent e) {
            if (carBTN.isEnabled()) {
                carPopup.openMenu(e);
            }
        }

        public void doHelp() {
            if (!contextHelp.equals("")) {
                ZF.setinfo(contextHelp, false);
            }
        }
    }

    class myJName extends myJLine {
	String old_name;

        public myJName(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
            contextHelp="prop_name";
            this.JTF.setHorizontalAlignment(SwingConstants.CENTER);
        }

	@Override
        public void doAction(Component e) {
            JTextField jtf=(JTextField) e;
            if (O.getName().equals(jtf.getText())) {
                return;
            }
	    old_name=O.getName();
            O.setName(jtf.getText());
            O.setShowName(true);
            show.forceSelect(2); // Force the ShowName icon to be selected
            if (ZC!=null) {
                ZC.repaint();
		ZC.updateTexts(O, "");

		ZC.update_distant(old_name, O.getName());
		ZC.update_distant(O, 3);
            }
        }

        public void init() {
            setText(O.getName());
        }
    }

    class myJAlias extends myJLine {

        public myJAlias(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_alias";
            carPopup.setDisabled(","+JTextFieldPopup.FUNCTIONMENU+",");
        }

	@Override
        public void doAction(Component e) {
            JTextField jtf=(JTextField) e;
            if ((O.getAlias()!=null)&&(O.getAlias().equals(jtf.getText()))) {
                return;
            }
            if (jtf.getText().equals("")) {
                O.setAlias(null);
            } else {
                O.setAlias(jtf.getText());
//                jtf.setText(O.getAlias());
            }
            O.setShowName(true);
            show.forceSelect(2);
            ZC.repaint();
        }

        public void init() {
            setText(O.getAlias());
            setInitValue();
        }
    }

    class myJRed extends myJLine {

        public myJRed(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            contextHelp="prop_magnetic";
        }

	@Override
        public void doAction(Component e) {
            try {
                O.setRed(getText());
                greencolor.init();
                bluecolor.init();
                color.init();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }
        }

        public void init() {
            setText(O.getRed());
            setInitValue();
        }

	@Override
        public void doQuitMe(Component cp) {
            redcolor.init();
            greencolor.init();
            bluecolor.init();
            color.init();
        }
    }

    class myJGreen extends myJLine {

        public myJGreen(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            contextHelp="prop_magnetic";
        }

	@Override
        public void doAction(Component e) {
            try {
                O.setGreen(getText());
                redcolor.init();
                bluecolor.init();
                color.init();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }
        }

        public void init() {
            setText(O.getGreen());
            setInitValue();
        }

	@Override
        public void doQuitMe(Component cp) {
            redcolor.init();
            greencolor.init();
            bluecolor.init();
            color.init();
        }
    }

    class myJBlue extends myJLine {

        public myJBlue(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            contextHelp="prop_magnetic";
        }

	@Override
        public void doAction(Component e) {
            try {
                O.setBlue(getText());
                redcolor.init();
                greencolor.init();
                color.init();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }
        }

        public void init() {
            setText(O.getBlue());
            setInitValue();
        }

	@Override
        public void doQuitMe(Component cp) {
            redcolor.init();
            greencolor.init();
            bluecolor.init();
            color.init();
        }
    }

    class myJMagnetObj extends myJLine {

        public myJMagnetObj(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
            contextHelp="prop_magnetic";
        }

	@Override
        public void doEnter() {
            PointObject P=(PointObject) O;
            if (P==null) {
                return;
            }
            magnetbtn.setSelected(true);
            ZC.setMagnetTool(P);
        }

	@Override
        public void doAction(Component e) {
            JTextField jtf=(JTextField) e;

            PointObject P=(PointObject) O;
            P.selectMagnetObjects(false);
            P.setMagnetObjects(Comma_To_Point(JTF.getText(), Cn, false));
            P.selectMagnetObjects(true);
            ZC.repaint();
        }

        public void init() {
            PointObject P=(PointObject) O;
            JTF.setText(Point_To_Comma(P.getMagnetObjectsString(), Cn, false));
            setInitValue();
        }
    }

    class myJMagnetPix extends myJLine {

        public myJMagnetPix(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
            contextHelp="prop_magnetic";
        }

	@Override
        public void doEnter() {
            PointObject P=(PointObject) O;
            if (P==null) {
                return;
            }
            magnetbtn.setSelected(true);
            ZC.setMagnetTool(P);
        }

	@Override
        public void doAction(Component e) {
            PointObject P=(PointObject) O;
            P.setMagnetRayExp(getText());
            P.magnet();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {
            PointObject P=(PointObject) O;
//            setText("" + P.getMagnetRayExp());
            if (P.getMagnetRayExp().equals("20")) {
                setText("");
            } else {
                setText(P.getMagnetRayExp());
            }
            setInitValue();
        }
    }

    class myJUnit extends myJLine {

        public myJUnit(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_unit";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (O.hasUnit()) {
                JTextField jtf=(JTextField) e;
                if (O.getUnit().equals(jtf.getText())) {
                    return;
                }

                if (MultipleObjects!=null) {
                    multiple_setUnit(jtf.getText());
                }

                O.setUnit(jtf.getText());
                O.setShowValue(true);
                show.forceSelect(1);
                ZC.repaint();
            }
        }

        public void init() {
            setText(O.getUnit());
            setInitValue();
        }
    }

    class myJX extends myJLine {
        String origin="";
        String current="";

        public myJX(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_coordinates";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            fixsize(this.JTF,this.JTF.getSize().width-18,this.JTF.getSize().height);
//            fixsize(this,this.getSize().width-30,this.getSize().height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            if (XYlink.isSelected()) {
                String myX=X.getText();
                myX=myX.replace("x(", "y(");
                Y.setText(myX);
            }
            O.setFixed(X.getText(), Y.getText());
            Fx.setSelected(true);
            current=getText();
            O.move(O.getX(), O.getY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);

                if (XYlink.isSelected()) {
                    Y.setText(Y.origin);
                }
                Fx.setSelected(true);
                O.setFixed(X.getText(), Y.getText());
                O.move(O.getX(), O.getY());
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                if (XYlink.isSelected()) {
                    Y.origin=Y.getText();
                }
            }
        }

        public void init() {
	    boolean fixed = (O instanceof VectorObject || O.fixed()) && O.fixedCoord();

	    if (fixed) {
                if (isAbsolutePoint()) {
                    current=Loc("fixedinwindow");
                } else {
                    current=(isValidExpression(O.getEXpos()))?O.getEXpos():"????";
                }
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject v = (VectorObject) O;
		    current=String.valueOf(v.getDeltaX());
		} else {
		    current=String.valueOf(O.getX());
		}
            }
            setText(current);
            origin=current;
        }
    }

    class myJY extends myJLine {
        String origin="";
        String current="";

        public myJY(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_coordinates";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            fixsize(this.JTF,this.JTF.getSize().width-18,this.JTF.getSize().height);
//            fixsize(this,this.getSize().width-30,this.getSize().height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            O.setFixed(X.getText(), Y.getText());
            Fx.setSelected(true);
            current=getText();
            O.move(O.getX(), O.getY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                O.setFixed(X.getText(), Y.getText());
                Fx.setSelected(true);
                O.move(O.getX(), O.getY());
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }

	public void init() {
	    boolean fixed = (O instanceof VectorObject || O.fixed()) && O.fixedCoord();

	    if (fixed) {
                if (isAbsolutePoint()) {
                    current=Loc("fixedinwindow");
                } else {
                    current=(isValidExpression(O.getEYpos()))?O.getEYpos():"????";
                }
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject v = (VectorObject) O;
		    current=String.valueOf(v.getDeltaY());
		} else {
		    current=String.valueOf(O.getY());
		}
            }
            setText(current);
            origin=current;
        }
    }
    
    class myJX3D extends myJLine {
        String origin="";
        String current="";

        public myJX3D(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_coordinates";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            fixsize(this.JTF,this.JTF.getSize().width-18,this.JTF.getSize().height);
//            fixsize(this,this.getSize().width-30,this.getSize().height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            if (XYZlink.isSelected()) {
            		String myX3D=X3D.getText();
            		String myY3D, myZ3D;
            		myY3D=myX3D.replace("x3D(", "y3D(");
            		myZ3D=myX3D.replace("x3D(", "z3D(");
                   Y3D.setText(myY3D);
                   Z3D.setText(myZ3D);
            }
            Fx3D.setSelected(true);
            O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
            current=getText();
           // Fx3D.init();
          //O.move(O.getX(), O.getY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                if (XYZlink.isSelected()) {
                    Y3D.setText(Y3D.origin);
                    Z3D.setText(Z3D.origin);
                }
                O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
	            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
                //O.move(O.getX(), O.getY());
                Fx3D.setSelected(true);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                if (XYZlink.isSelected()) {
                    Y3D.origin=Y.getText();
                    Z3D.origin=Z3D.getText();
                }
            }
        }
	
	 public void init() {
		    boolean fixed3D = (O instanceof VectorObject || O.fixed3D()) && O.fixedCoord3D();

		    if (fixed3D) {
	                if (isAbsolutePoint()) {
	                    current=Loc("fixedinwindow");
	                } else {
	                    current=(isValidExpression(O.getEX3Dpos()))?O.getEX3Dpos():"????";
	                }
	            } else {
			if(ASegment.indexOf(typecode)==14){
			    VectorObject v = (VectorObject) O;
			    current=String.valueOf(v.getDeltaX3D());
			} else {
			    current=String.valueOf(O.getX3D());
			}
	            }
	            setText(current);
	            origin=current;
	        }
	 
 }
	
	class myJY3D extends myJLine {
        String origin="";
        String current="";

        public myJY3D(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_coordinates";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            fixsize(this.JTF,this.JTF.getSize().width-18,this.JTF.getSize().height);
//            fixsize(this,this.getSize().width-30,this.getSize().height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            Fx3D.setSelected(true);
            O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
            current=getText();
            //Fx3D.init();
            //O.move(O.getX(), O.getY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                if (XYZlink.isSelected()) {
                    X3D.setText(X3D.origin);
                    Z3D.setText(Z3D.origin);
                }
                O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
	            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
                //O.move(O.getX(), O.getY());
                Fx3D.setSelected(true);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }
	
	public void init() {
	    boolean fixed3D = (O instanceof VectorObject || O.fixed3D()) && O.fixedCoord3D();

	    if (fixed3D) {
                if (isAbsolutePoint()) {
                    current=Loc("fixedinwindow");
                } else {
                    current=(isValidExpression(O.getEY3Dpos()))?O.getEY3Dpos():"????";
                }
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject v = (VectorObject) O;
		    current=String.valueOf(v.getDeltaY3D());
		} else {
		    current=String.valueOf(O.getY3D());
		}
            }
            setText(current);
            origin=current;
        }
	
	}
	
	class myJZ3D extends myJLine {
        String origin="";
        String current="";

        public myJZ3D(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_coordinates";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
//            fixsize(this.JTF,this.JTF.getSize().width-18,this.JTF.getSize().height);
//            fixsize(this,this.getSize().width-30,this.getSize().height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            Fx3D.setSelected(true);
            O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
            current=getText();
            //Fx3D.init();
            //O.move(O.getX(), O.getY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                if (XYZlink.isSelected()) {
                    Y3D.setText(Y3D.origin);
                    X3D.setText(X3D.origin);
                }
                O.setFixed(X3D.getText(), Y3D.getText(), Z3D.getText());
	            //O.setFixed("x(O)+("+X3D.getText()+")*(x(X)-x(O))+("+Y3D.getText()+")*(x(Y)-x(O))+("+Z3D.getText()+")*(x(Z)-x(O))", "y(O)+("+X3D.getText()+")*(y(X)-y(O))+("+Y3D.getText()+")*(y(Y)-y(O))+("+Z3D.getText()+")*(y(Z)-y(O))");
                //O.move(O.getX(), O.getY());
                Fx3D.setSelected(true);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }
	
	public void init() {
	    boolean fixed3D = (O instanceof VectorObject || O.fixed3D()) && O.fixedCoord3D();

	    if (fixed3D) {
                if (isAbsolutePoint()) {
                    current=Loc("fixedinwindow");
                } else {
                    current=(isValidExpression(O.getEZ3Dpos()))?O.getEZ3Dpos():"????";
                }
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject v = (VectorObject) O;
		    current=String.valueOf(v.getDeltaZ3D());
		} else {
		    current=String.valueOf(O.getZ3D());
		}
            }
            setText(current);
            origin=current;
        }
	
	}

    class myJR extends myJLine {

        String origin="";
        String current="";

        public myJR(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_length";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            try {
                O.setFixed(true, getText());
                O.setFixed(getText());
                O.setDragable(false);
                RFx.setSelected(true);
                current=getText();
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }

        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }

                try {
                    O.setFixed(true, origin);
                    setText(origin);
                    RFx.setSelected(true);
                    ZC.recompute();
                    ZC.validate();
                    ZC.repaint();
                } catch (Exception ex) {
                }
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }

        public void init() {
            current=O.getStringLength();
            setText(current);
            origin=current;
        }
    }
    
    class myJR3D extends myJLine {

        String origin="";
        String current="";

        public myJR3D(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_length";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            try {
                O.setFixed3D(true, getText());
                O.setFixed(getText());
                O.setDragable(false);
                RFx3D.setSelected(false);
                current=getText();
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }

        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }

                try {
                    O.setFixed3D(true, origin);
                    setText(origin);
                    RFx3D.setSelected(true);
                    ZC.recompute();
                    ZC.validate();
                    ZC.repaint();
                } catch (Exception ex) {
                }
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }

        public void init() {
            current=((SegmentObject)O).getStringLength3D();
            setText(current);
            origin=current;
            setEditable(false);
        }
    }

    class myJA extends myJLine {

        String origin="";
        String current="";

        public myJA(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_angle";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            try {
                O.setFixed(getText());
                O.setDragable(false);
                aFx.setSelected(true);
                current=getText();
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
            } catch (Exception ex) {
            }

        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }

                try {
                    O.setFixed(origin);
                    setText(origin);
                    aFx.setSelected(true);
                    ZC.recompute();
                    ZC.validate();
                    ZC.repaint();
                } catch (Exception ex) {
                }
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }

        public void init() {
            current=O.getE();
            setText(current);
            origin=current;
        }
    }

    class myJRFx extends myJLine {

        public myJRFx(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);

	    JCBX.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
		    if(ASegment.indexOf(typecode)!=-1) {
			setObject(O, false, false);
		    }
                }
            });
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                try {
                    String val=String.valueOf(ValueOf(O.getStringLength()));
                    ray.setText(val);
                    O.setDragable(true);
                    O.setFixed(false, val);
                    O.setFixed(false);
                } catch (Exception ex) {
                }

            } else {
                try {
                    String val=String.valueOf(ValueOf(O.getStringLength()));
                    ray.setText(val);
                    O.setDragable(false);
                    O.setFixed(true, val);
                    O.setFixed(true);
		    if(ASegment.indexOf(typecode)==14 && O.fixedCoord()){
			O.setFixed(false);
		    }
                } catch (Exception ex) {
                }
            }
        }

        public void init() {
            setSelected((!(O.isDragable()))&&(O.fixed()));
        }
    }
    
    class myJRFx3D extends myJLine {

        public myJRFx3D(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);

	    JCBX.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
		    if(ASegment.indexOf(typecode)!=-1) {
			setObject(O, false, false);
		    }
                }
            });
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                try {
                    String val=String.valueOf(ValueOf(O.getStringLength3D()));
                    O.setFixed3D(false, val);
                    O.setFixed3D(false);
                    ray3D.setText(val);
                    O.setDragable(true);   
                } catch (Exception ex) {
                }

            } else {
                try {
                    String val=String.valueOf(ValueOf(O.getStringLength3D()));
                    ray3D.setText(val);
                    O.setDragable(false);
                    O.setFixed3D(true, val);
                    O.setFixed3D(true);
		    if(ASegment.indexOf(typecode)==14 && O.fixedCoord3D()){
			O.setFixed3D(false);
		    }
                } catch (Exception ex) {
                }
            }
        }

        public void init() {
            setSelected((!(O.isDragable()))&&(O.fixed3D()));
        }
    }

    class myJAFx extends myJLine {

        public myJAFx(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                try {
                    String val=String.valueOf(ValueOf(O.getE()));
                    angle.setText(val);
                    O.setDragable(true);
                    O.setFixed(false);
                } catch (Exception ex) {
                }

            } else {
                try {
                    String val=String.valueOf(ValueOf(O.getE()));
                    angle.setText(val);
                    O.setDragable(false);
                    O.setFixed(val);
                } catch (Exception ex) {
                }
            }
        }

        public void init() {
            setSelected((!(O.isDragable()))&&(O.fixed()));
        }
    }

    class myJAbsolutePos extends myJLine {

        public myJAbsolutePos(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                O.setFixed(false);
                X.setText(String.valueOf(O.getX()));
                Y.setText(String.valueOf(O.getY()));
                X.setEditable(true);
                Y.setEditable(true);
                Fx.setSelected(false);
                Fx.JCBX.setEnabled(true);
            } else {
                String x="(windoww/(windoww-d(windoww)))*(x("+O.getName()+")-windowcx)+windowcx+d(windowcx)";
                String y="(windoww/(windoww-d(windoww)))*(y("+O.getName()+")-windowcy)+windowcy+d(windowcy)";
                O.setFixed(x, y);
                X.setEditable(false);
                Y.setEditable(false);
                X.setText(Loc("fixedinwindow"));
                Y.setText(Loc("fixedinwindow"));
                Fx.setSelected(true);
                Fx.JCBX.setEnabled(false);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
            }
        }

        public void init() {
            boolean b=isAbsolutePoint();
            setSelected(b);
            if (b) {
                X.setEditable(false);
                Y.setEditable(false);
                Fx.JCBX.setEnabled(false);
            }
        }
    }

    class myXYlink extends JEricPanel {

        int H, W;
        JButton btn;
//        ImageIcon myimage;

        public myXYlink() {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setOpaque(false);
            btn=new JButton();
            ImageIcon imgOFF=new ImageIcon(getClass().getResource("/eric/GUI/icons/palette/chaineOFF.png"));
            btn.setIcon(imgOFF);
            btn.setSelectedIcon(new ImageIcon(getClass().getResource("/eric/GUI/icons/palette/chaine.png")));
            btn.setSelected(true);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {

		@Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    btn.setSelected(!btn.isSelected());
                }
            });
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setFocusable(false);
            W=imgOFF.getIconWidth();
            H=imgOFF.getIconHeight();
            fixsize(btn, W, H);
            btn.setBorder(BorderFactory.createEmptyBorder());
            this.add(btn);
        }

        public boolean isSelected() {
            return btn.isSelected();
        }
    }
    
    class myXYZlink extends JEricPanel {

        int H, W;
        JButton btn;
//        ImageIcon myimage;

        public myXYZlink() {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setOpaque(false);
            btn=new JButton();
            ImageIcon imgOFF=new ImageIcon(getClass().getResource("/eric/GUI/icons/palette/chaineOFF.png"));
            btn.setIcon(imgOFF);
            btn.setSelectedIcon(new ImageIcon(getClass().getResource("/eric/GUI/icons/palette/chaine.png")));
            btn.setSelected(true);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {

		@Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    btn.setSelected(!btn.isSelected());
                }
            });
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setFocusable(false);
            W=imgOFF.getIconWidth();
            H=imgOFF.getIconHeight();
            fixsize(btn, W, H);
            btn.setBorder(BorderFactory.createEmptyBorder());
            this.add(btn);
        }

        public boolean isSelected() {
            return btn.isSelected();
        }
    }

    class myJGrid extends myJLine {

        String origin="";
        String current="";

        public myJGrid(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            contextHelp="prop_grid";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
            if (isValidExpression(current)) {
                O.setIncrement(ValueOf(current));
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                current=origin;
                setText(current);
                O.setIncrement(ValueOf(current));
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                setText(String.valueOf(ValueOf(origin)));
            }
        }

        public void init() {
            current=String.valueOf(O.getIncrement());
            setText(current);
            origin=current;
        }
    }

    class myJFx extends myJLine {

        public myJFx(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);

	    JCBX.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    setObject(O, false, false);
                }
            });
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                O.setFixed(false);
		if(ASegment.indexOf(typecode)==14){
		    VectorObject o = (VectorObject) O;
		    X.setText(String.valueOf(o.getDeltaX()));
		    Y.setText(String.valueOf(o.getDeltaY()));
		} else {
		    X.setText(String.valueOf(O.getX()));
		    Y.setText(String.valueOf(O.getY()));
		}
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject o = (VectorObject) O;
		    O.setFixed(String.valueOf(o.getDeltaX()), String.valueOf(o.getDeltaY()));
		    if(o.fixed()){
			try{
			    o.setFixed(false, O.getStringLength());
			} catch (Exception ee){}
			//RFx.init();
		    }
		} else {
		    O.setFixed(String.valueOf(O.getX()), String.valueOf(O.getY()));
		}
            }
        }

        public void init() {
            JCBX.setEnabled(true);
	    boolean fixed = (O instanceof VectorObject || O.fixed()) && O.fixedCoord();
            setSelected(fixed);

        }
    }
    
    class myJFx3D extends myJLine {

        public myJFx3D(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);

	    JCBX.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    setObject(O, false, false);
                }
            });
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                if (O instanceof PointObject) ((PointObject) O).setFixed3D(false);
		if(ASegment.indexOf(typecode)==14){
		    VectorObject o = (VectorObject) O;
		    X3D.setText(String.valueOf(o.getDeltaX3D()));
		    Y3D.setText(String.valueOf(o.getDeltaY3D()));
		    Z3D.setText(String.valueOf(o.getDeltaZ3D()));
		} else {
		    X3D.setText(String.valueOf(((PointObject)O).getX3D()));
		    Y3D.setText(String.valueOf(((PointObject)O).getY3D()));
		    Z3D.setText(String.valueOf(((PointObject)O).getZ3D()));
		}
            } else {
		if(ASegment.indexOf(typecode)==14){
		    VectorObject o = (VectorObject) O;
		    ((VectorObject)O).setFixed(String.valueOf(o.getDeltaX3D()), String.valueOf(o.getDeltaY3D()), String.valueOf(o.getDeltaZ3D()));
		} else {
		   ((PointObject) O).setFixed(String.valueOf(((PointObject)O).getX3D()), String.valueOf(((PointObject)O).getY3D()),String.valueOf(((PointObject)O).getZ3D()));
		   setSelected(true);
		}
            }
        }

        public void init() {
            JCBX.setEnabled(true);
	    boolean fixed = (O instanceof VectorObject || (O instanceof PointObject&&((PointObject)O).fixed3D())) && O.fixedCoord3D();
            setSelected(fixed);
        }
    }

    class myTRK extends myJLine {

        public myTRK(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                if (MultipleObjects!=null) {
                    multiple_setTracked(false);
                }
                O.setTracked(false);
            } else {
                if (MultipleObjects!=null) {
                    multiple_setTracked(true);
                }
                O.setTracked(true);
            }
	    ZC.update_distant(O, 3);
        }

        public void init() {
            JCBX.setEnabled(true);
            setSelected(O.tracked());

        }
    }

    class myJInside extends myJLine {

        public myJInside(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            O.setInside(!isSelected());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {
            setSelected(O.isInside());
        }
    }

    class myJTextArea extends ContentLine {

        JButton carBTN=null;
        JTextArea JTX;

        public myJTextArea(String mytxt, int width, int height) {
            super(width, height);
            this.setFocusable(false);
            this.add(margin(5));
            JTX=new JTextArea(mytxt);
            JTX.setFont(F_TextArea);
            JTX.setBackground(new Color(245, 246, 255));
//            JTX.setBorder(BorderFactory.createEtchedBorder());

            JTX.addKeyListener(new KeyAdapter() {

		@Override
                public void keyReleased(KeyEvent e) {
                    doAction(e.getComponent());
                }
            });
            JTX.addFocusListener(new FocusAdapter() {

		@Override
                public void focusGained(FocusEvent e) {
//                    JTX.selectAll();
                    carBTN.setEnabled(true);
                }

		@Override
                public void focusLost(FocusEvent e) {
                    carBTN.setEnabled(false);
                    doQuitMe(e.getComponent());
                }
            });
            JTX.setLineWrap(true);

//
            JScrollPane jstxt=new JScrollPane(JTX);
            jstxt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jstxt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//            jstxt.setViewportView(JTX);
//            fixsize(jstxt,PW-11,h);
            this.add(jstxt);
            ImageIcon carimg=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/carbtn.png"));
            ImageIcon carimg_dis=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/carbtn_dis.png"));
            carBTN=new JButton(carimg);
            carBTN.setDisabledIcon(carimg_dis);
//            carbtn.setRolloverIcon(closeoverimg);
            carBTN.setBorder(BorderFactory.createEmptyBorder());
            carBTN.setOpaque(false);
            carBTN.setContentAreaFilled(false);
            carBTN.setFocusable(false);
            carBTN.addMouseListener(new MouseAdapter() {

		@Override
                public void mousePressed(MouseEvent e) {
                    doShowPopup(e);
                }
            });
            carBTN.setEnabled(false);
            this.add(margin(2));
            this.add(carBTN);
        }

        public void doShowPopup(MouseEvent e) {
            if (carBTN.isEnabled()) {
                JTextFieldPopup mypop=new JTextFieldPopup(JTX);
                mypop.addPopupMenuListener(new PopupMenuListener() {

		    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
                    }

		    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
                        doAction(JTX);
                    }

		    @Override
                    public void popupMenuCanceled(PopupMenuEvent arg0) {
                    }
                });
                mypop.openMenu(e);
            }
        }

        public void setText(String txt) {
            JTX.setText(txt);
        }

        public String getText() {
            return JTX.getText();
        }

        public void doAction(Component cp) {
            if (O.getText().equals(getText())) {
                return;
            }
            O.setLines(getText());
            O.setText(getText(), true);
//            setText(O.getText());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
            ZC.update_distant(O, 3);
        }

        public void doQuitMe(Component cp) {
        }

        public void init() {
            setText(O.getLines());
        }

        ;
    }

    class myJExpression extends myJLine {

        String origin="";

        public myJExpression(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

        public void init() {
            origin=O.getExpression();
            if (O.isSlider()) {
                setText("");
            } else {
                setText(origin);
            }
        }

	@Override
        public void doAction(Component e) {
            if (getText().equals("")) {
                O.setSlider(min.getText(), max.getText());
                slider.setSelected(true);
                try {
                    O.setExpression(min.getText(), O.getConstruction());
                } catch (Exception ex) {
                }
            } else {
                try {
                    O.setExpression(getText(), O.getConstruction());
                    O.setSlider(false);
                    slider.setSelected(false);
                } catch (Exception ex) {
                }
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (getText().equals("")) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                try {
                    O.setExpression(origin, O.getConstruction());
                } catch (ConstructionException ex) {
                }
                this.JTF.requestFocus();
            } else {
                origin=getText();
                try {
                    O.setExpression(origin, O.getConstruction());
                    O.setSlider(false);
                    slider.setSelected(false);
                } catch (ConstructionException ex) {
                }
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();

        }
    }

    class myJPrompt extends myJLine {

        public myJPrompt(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.FUNCTIONMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if ((O.getPrompt()!=null)&&(O.getPrompt().equals(getText()))) {
                return;
            }
            O.setPrompt(getText());
            O.setShowName(true);
            show.forceSelect(2);
            ZC.repaint();
        }

        public void init() {
            setText(O.getPrompt());
        }
    }

    class myJSMin extends myJLine {

        String origin="";
        String current="";

        public myJSMin(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }

            O.setSlider(getText(), max.getText());
            slider.setSelected(true);
            current=getText();
            Exp.setText("");
            if (isValidExpression(current)) {
                try {
                    O.setExpression(current, O.getConstruction());
                } catch (ConstructionException ex) {
                }
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                O.setSlider(getText(), max.getText());
                try {
                    O.setExpression(origin, O.getConstruction());
                } catch (ConstructionException ex) {
                }
//                slider.setSelected(true);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                current=origin;
                if (ValueOf(current)>=ValueOf(max.getText())) {
                    String goodmax=String.valueOf(ValueOf(getText())+10);
                    max.setText(goodmax);
                    if (O.isSlider()) {
                        O.setSlider(getText(), goodmax);
                        try {
                            O.setExpression(current, O.getConstruction());
                        } catch (ConstructionException ex) {
                        }
                        ZC.recompute();
                        ZC.validate();
                        ZC.repaint();
                    }
                }
            }
        }

        public void init() {
            if (O.isSlider()) {
                setText(O.getMin());
            } else {
                setText("-5");
            }
            origin=getText();
        }
    }

    class myJSMax extends myJLine {

        String origin="";
        String current="";

        public myJSMax(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            O.setSlider(min.getText(), getText());
            slider.setSelected(true);
            current=getText();
            Exp.setText("");
            if (isValidExpression(current)) {
                try {
                    O.setExpression(min.getText(), O.getConstruction());
                } catch (ConstructionException ex) {
                }
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                O.setSlider(min.getText(), getText());
                slider.setSelected(true);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                current=origin;
                if (ValueOf(current)<=ValueOf(min.getText())) {
                    String goodmin=String.valueOf(ValueOf(getText())-10);
                    O.setSlider(goodmin, getText());
                    min.setText(goodmin);
                    try {
                        O.setExpression(goodmin, O.getConstruction());
                    } catch (ConstructionException ex) {
                    }
                    ZC.recompute();
                    ZC.validate();
                    ZC.repaint();
                }
            }
        }

        public void init() {
            if (O.isSlider()) {
                setText(O.getMax());
            } else {
                setText("5");
            }
            origin=getText();
        }
    }

    class myJSSlider extends myJLine {

        public myJSSlider(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            if (isSelected()) {
                O.setSlider(false);
                Exp.setText(Exp.origin);
                try {
                    O.setExpression(Exp.origin, O.getConstruction());
                } catch (ConstructionException ex) {
                }
                Exp.JTF.selectAll();
                Exp.JTF.requestFocus();
            } else {
                Exp.setText("");
                O.setSlider(min.getText(), max.getText());
                try {
                    O.setExpression(min.getText(), O.getConstruction());
                } catch (ConstructionException ex) {
                }
                min.JTF.selectAll();
                min.JTF.requestFocus();
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {
            setSelected(O.isSlider());
        }
    }

    class myJUserFunction extends myRub {

        JComboBox JCB;
        ContentLine comboline;
        UserFunctionObject f;
        boolean doaction=true;

        public myJUserFunction(String comment, String txt, int comwidth, int linewidth, int lineheight) {
            super("user fonction");
            comboline=new ContentLine(linewidth, lineheight);
            comboline.setFocusable(false);
            addnewlabel(comment, comwidth, lineheight);

            JCB=new JComboBox();
            JCB.setUI((ComboBoxUI) MyComboBoxUI.createUI(JCB));
            JCB.setFont(F_ComboBox);
            JCB.addItem("x");
            JCB.addItem("x y");
            JCB.addItem("x y z");
            JCB.addItem("x y z t");
            JCB.setMaximumRowCount(5);
            JCB.setOpaque(false);
            JCB.setFocusable(false);
            JCB.setEditable(false);
            fixsize(JCB, linewidth-comwidth, lineheight);
            JCB.addItemListener(new ItemAdapter());
            comboline.add(JCB);
        }

        class ItemAdapter implements ItemListener {

	    @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange()==ItemEvent.SELECTED) {
                    if (doaction) {
                        String menuitem=(String) evt.getItem();
                        doAction(menuitem);
                    }
                }
            }
        }

        public void addnewlabel(String comment, int w, int h) {
            JLabel myTXT=new JLabel(comment);
            myTXT.setFocusable(false);
            myTXT.setFont(F_Label);
            fixsize(myTXT, w, h);
            comboline.add(myTXT);
        }

        public void doAction(Object item) {
            fuey.setVar((String) item);
            fuey.JTF.requestFocus();
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
            this.validate();
        }

        public void init() {
            f=(UserFunctionObject) O;
            this.removeAll();
            this.add(margintop(2));
            fuey.init();
            this.add(fuey);
            this.add(margintop(fuey.H+1));
            this.add(comboline);

            this.revalidate();
            JCB.setSelectedItem(f.getVar());
            doaction=true;
        }
    }

    class myJEqXY extends myJLine {

        String origin="";
        String current="0";
        EquationXYObject f;

        public myJEqXY(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            remove(carBTN);
            addnewlabel(" = 0", 35, H);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
            add(carBTN);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            setEquation(getText());
        }

        public void setEquation(String cf) {
            current=cf;
            f=(EquationXYObject) O;
            f.setEquation(cf, ZC);
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
            ZC.reloadCD();
            f.compute();
//            }
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            origin=getText();
        }

        public void init() {
            f=(EquationXYObject) O;
            current=f.getEY();
            setText(current);
            origin=current;
        }
    }

    class myJUserF_Y extends myJLine {

        String origin="";
        String current="0";
        String currentvars="x";
        UserFunctionObject f;

        public myJUserF_Y(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            setExpression(currentvars, getText());
        }

        public void setExpression(String v, String cf) {
//            if (isValidExpression(cf)){
            currentvars=v;
            current=cf;
            f=(UserFunctionObject) O;
            f.setExpressions(currentvars, current);
            myTXT.setText("f("+currentvars.replaceAll(" ", ",")+")=");
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
            ZC.reloadCD();
//            }
        }

        public void setVar(String v) {
            setExpression(v, current);
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            origin=getText();
        }

        public void init() {
            f=(UserFunctionObject) O;
            current=f.getEY();
            currentvars=f.getVar();
            setText(current);
            origin=current;
        }
    }

    // Tells if the function or track must be plot
    // with points only or segments :
    class myJF_pt extends myJLine {

        public myJF_pt(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {

            O.setSpecial((!isSelected()));
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {
            setSelected(O.isSpecial());
        }
    }

    class myJF_Discrete extends myJLine {

        TrackObject t;

        public myJF_Discrete(String comment, boolean bool, int width, int height) {
            super(comment, bool, width, height);
        }

	@Override
        public void doAction(Component e) {
            t.setDiscrete((!isSelected()));
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {
            t=(TrackObject) O;
            setSelected(t.isDiscrete());
        }
    }

    class myJF_d extends myJLine {

        String origin="";
        String current="";
        FunctionObject f;

        public myJF_d(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
            if (current.equals("")) {
                current="0";
            }
//            if (isValidExpression(current)){
            f.setRange(f.VarMin.toString(), f.VarMax.toString(), current);
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
//            }
        }

	@Override
        public void doQuitMe(Component e) {
//            origin=getText();
            if (O==null) {
                return;
            }
            if (!(isValidExpression(current))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                current=origin;
                f.setRange(f.VarMin.toString(), f.VarMax.toString(), current);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=current;
            }
        }

        public void init() {
            f=(FunctionObject) O;
            current=f.DVar.toString();
            if (current.equals("0")) {
                setText("");
            } else {
                setText(current);
            }
            origin=current;
        }
    }

    class myJF_DMin extends myJLine {

        String origin="";
        String current="";
        TrackObject t;

        public myJF_DMin(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
            if (isValidExpression(current)) {
                t.setDMin(ValueOf(current));
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                current=origin;
                setText(current);
                t.setDMin(ValueOf(current));
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
                setText(String.valueOf(ValueOf(origin)));
            }
        }

        public void init() {
            t=(TrackObject) O;
            current=String.valueOf(t.getDMin());
            setText(current);
            origin=current;
        }
    }

    class myJF_Min extends myJLine {

        String origin="";
        String current="";
        FunctionObject f;

        public myJF_Min(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
            if (current.equals("")) {
                current="windowcx-windoww";
            }
            f.setRange(current, f.VarMax.toString(), f.DVar.toString());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(current))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                current=origin;
                f.setRange(current, f.VarMax.toString(), f.DVar.toString());
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=current;
            }
        }

        public void init() {
            f=(FunctionObject) O;
            current=f.VarMin.toString();
            if (current.equals("windowcx-windoww")) {
                setText("");
            } else {
                setText(current);
            }
            origin=current;
        }
    }

    class myJF_Max extends myJLine {

        String origin="";
        String current="";
        FunctionObject f;

        public myJF_Max(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
            if (current.equals("")) {
                current="windowcx+windoww";
            }
            f.setRange(f.VarMin.toString(), current, f.DVar.toString());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if (!(isValidExpression(current))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="0";
                }
                setText(origin);
                current=origin;
                f.setRange(f.VarMin.toString(), current, f.DVar.toString());
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=current;
            }
        }

        public void init() {
            f=(FunctionObject) O;
            current=f.VarMax.toString();
            if (current.equals("windowcx+windoww")) {
                setText("");
            } else {
                setText(current);
            }
            origin=current;
        }
    }

    class myJF_X extends myJLine {

        String origin="";
        String current="";
        FunctionObject f;

        public myJF_X(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
//            if (isValidExpression(current)){
            f.setExpressions(f.Var[0], current, f.getEY());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(f, 3);
//            }
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            origin=getText();
//            if (!(isValidExpression(getText()))) {
//                JOptionPane.showMessageDialog(null, Loc("error"));
//                if (!(isValidExpression(origin))) origin="0";
//                setText(origin);
//                current=origin;
//                this.JTF.requestFocus();
//            }else{
//                origin=getText();
//            }
        }

        public void init() {
            f=(FunctionObject) O;
            current=f.getEX();
            setText(current);
            origin=current;
        }
    }

    class myJF_Y extends myJLine {

        String origin="";
        String current="";
        FunctionObject f;

        public myJF_Y(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
        }

	@Override
        public void doAction(Component e) {
            if (current.equals(getText())) {
                return;
            }
            current=getText();
//            if (isValidExpression(current)){
            f.setExpressions(f.Var[0], f.getEX(), current);
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
	    ZC.update_distant(f, 3);
//            }
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            origin=getText();
//            if (!(isValidExpression(getText()))) {
//                JOptionPane.showMessageDialog(null, Loc("error"));
//                if (!(isValidExpression(origin))) origin="0";
//                setText(origin);
//                current=origin;
//                this.JTF.requestFocus();
//            }else{
//                origin=getText();
//            }
        }

        public void init() {
            f=(FunctionObject) O;
            current=f.getEY();
            setText(current);
            if (func.cartesian) {
                this.setLabelTxt("f(x)=");
            } else {
                this.setLabelTxt("y(t)=");
            }
            origin=current;
        }
    }

    class myJFunction extends myRub {

        myJFunctionselector myJsel;
        myJFunction ME;
        FunctionObject f;
        public boolean cartesian;
        boolean doaction=true;

        public myJFunction(String comment, String txt, int comwidth, int linewidth, int lineheight) {
            super("fonction");
            myJsel=new myJFunctionselector(Loc("parametric"), true, linewidth, lineheight);
            ME=this;
        }

        public void init() {
            f=(FunctionObject) O;
            cartesian=(f.Var[0].equals(f.getEX()));
            this.removeAll();
            this.add(margintop(2));
            doaction=false;
//            f.setRange("-5","5","0.1");

            if (cartesian) {

                fey.init();
                this.add(fey);
                myJsel.setSelected(false);
                this.add(margintop(fex.H+2));
            } else {

                fex.init();
                fey.init();
                this.add(fex);
                this.add(margintop(1));
                this.add(fey);
                this.add(margintop(1));
                myJsel.setSelected(true);
            }
            this.add(myJsel);
            this.revalidate();
            this.repaint();
            doaction=true;
        }

        class myJFunctionselector extends myJLine {

            public myJFunctionselector(String comment, boolean bool, int width, int height) {
                super(comment, bool, width, height);
            }

	    @Override
            public void doAction(Component e) {
                ME.removeAll();
                ME.add(margintop(2));
                cartesian=isSelected();
                if (cartesian) {
                    f.setRange("windowcx-windoww", "windowcx+windoww", "0");
                    f.setExpressions("x", "", "0");

                    fmin.init();
                    fmax.init();
                    fd.init();
                    fey.init();
                    ME.add(fey);
                    fey.JTF.requestFocus();
                    ME.add(margintop(fex.H+2));
                } else {
                    f.setRange("-pi", "pi", "0");
                    f.setExpressions("t", "rsin(t)", "rcos(t)");
                    fmin.init();
                    fmax.init();
                    fd.init();
                    fex.init();
                    fey.init();
                    ME.add(fex);
                    ME.add(margintop(1));
                    ME.add(fey);
                    ME.add(margintop(1));
                    fex.JTF.requestFocus();
                }
                ME.add(this);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                ME.revalidate();
                ME.repaint();
            }
        }
    }

    class myBtn extends JButton {

        int H, W;

        public myBtn(String label, int width, int height) {
            H=height;
            W=width;
            this.setText(label);
            this.setFont(F_Button);
            fixsize(this, width, height);
            this.setFocusable(false);
            this.addMouseListener(new MouseAdapter() {

		@Override
                public void mouseClicked(MouseEvent evt) {
                    doClick();
                }
            });
        }

	@Override
        public void doClick() {
        }
    }

    class myMagnetBtn extends myBtn {

        public myMagnetBtn(int width, int height) {
            super("", width, height);
            ImageIcon img=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/aimant.png"));
            ImageIcon selimg=new ImageIcon(getClass().getResource("/eric/GUI/icons/bar/aimantON.png"));
            setIcon(img);
            setSelectedIcon(selimg);
            setBorder(BorderFactory.createEtchedBorder());
            this.setFocusable(true);
        }

        public void init() {
//            PointObject p = (PointObject) O;
//            if (p.isPointOn()) {
//                this.setText(Loc("release"));
//            } else {
//                this.setText(Loc("bind"));
//            }
            setSelected(false);
        }

	@Override
        public void doClick() {
            if (!isSelected()) {
                ZC.setMagnetTool((PointObject) O);
                setSelected(true);
            } else {
                setSelected(false);
                ZC.reset();
            }
        }
    }

    class myArcBtn extends myBtn {

        public myArcBtn(int width, int height) {
            super("", width, height);
        }

        public void init() {
            PrimitiveCircleObject o=(PrimitiveCircleObject) O;
            if (o.hasRange()) {
                this.setText(Loc("killarc"));
            } else {
                this.setText(Loc("arc"));
            }
        }

	@Override
        public void doClick() {
            PrimitiveCircleObject o=(PrimitiveCircleObject) O;
            if (o.hasRange()) {
                o.clearRange();
                this.setText(Loc("arc"));
            } else {
                ZC.range(o);
                this.setText(Loc("killarc"));
            }
            ZC.validate();
            ZC.repaint();
        }
    }

    class myPtBindBtn extends myBtn {

        public myPtBindBtn(int width, int height) {
            super("", width, height);
        }

        public void init() {
            PointObject p=(PointObject) O;
            if (p.isPointOn()) {
                this.setText(Loc("release"));
            } else {
                this.setText(Loc("bind"));
            }
        }

	@Override
        public void doClick() {
            PointObject p=(PointObject) O;
            if (p.isPointOn()) {
                p.setBound("");
                O.getConstruction().updateCircleDep();
                this.setText(Loc("bind"));
                setObject(O, false, false);
            } else {
                ZC.bind(p);
                this.setText(Loc("release"));
            }
            ZC.validate();
            ZC.repaint();
        }
    }

    class myPtAwayBtn extends myJLine {

        public myPtAwayBtn(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
            JTF.setHorizontalAlignment(JTextField.CENTER);
        }

        public void clear(){
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if ((P.getAway()!=null)&&(P.stayAway())) {
                    P.setAway("");
                    ZC.validate();
                    ZC.repaint();
                }
            }
            JTF.setText("");
        }

	@Override
        public void doEnter() {
            ptclosebtn.clear();
            ZC.setAway((IntersectionObject) O, true);
            O.setStrongSelected(true);
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if (P.getAway()!=null) {
                    P.getAway().setSelected(true);
                }
            }
        }

        public void init() {
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if ((P.getAway()!=null)&&(P.stayAway())) {
                    JTF.setText(P.getAway().getName());
                } else {
                    JTF.setText("");
                }
            } else {
                JTF.setText("");
            }
            setInitValue();
        }

	@Override
        public void doAction(Component e) {
            ZC.clearSelected();
            O.setStrongSelected(true);
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                P.setAway(JTF.getText());
                if (P.getAway()!=null) {
                    P.getAway().setSelected(true);
                }
            }
            ZC.validate();
            ZC.repaint();
        }
    }

    class myPtCloseBtn extends myJLine {

        public myPtCloseBtn(String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height);
            JTF.setHorizontalAlignment(JTextField.CENTER);
        }

        public void clear(){
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if ((P.getAway()!=null)&&(!P.stayAway())) {
                    P.setAway("");
                    ZC.validate();
                    ZC.repaint();
                }
            }
            JTF.setText("");
        }

	@Override
        public void doEnter() {
            ptawaybtn.clear();
            ZC.setAway((IntersectionObject) O, false);
            O.setStrongSelected(true);
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if (P.getAway()!=null) {
                    P.getAway().setSelected(true);
                }
            }
        }

        public void init() {
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                if ((P.getAway()!=null)&&(!P.stayAway())) {
                    JTF.setText(P.getAway().getName());
                } else {
                    JTF.setText("");
                }
            } else {
                JTF.setText("");
            }
            setInitValue();
        }

	@Override
        public void doAction(Component e) {
            ZC.clearSelected();
            O.setStrongSelected(true);
            if (O instanceof IntersectionObject) {
                IntersectionObject P=(IntersectionObject) O;
                P.setAway(JTF.getText(),false);
                if (P.getAway()!=null) {
                    P.getAway().setSelected(true);
                }
            }
            ZC.validate();
            ZC.repaint();
        }
    }



    class myJIcon extends JButton implements MouseListener {

        String name;
        Vector V;// contain 1 elt-> togglebutton , contain more elts-> group member
        public int code;//
        boolean isSelected; // icon state
        boolean isGrouped;
        boolean isEntered=false; // Mouseover ?
        private ImageIcon myimage;
        int iconsize;

	@Override
        public void paintComponent(java.awt.Graphics g) {
            /* I learned things from this pages :
            http://java.sun.com/developer/technicalArticles/GUI/java2d/java2dpart1.html
            http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
             */
            super.paintComponent(g);
            java.awt.Dimension d=this.getSize();
            int w=d.width;
            int h=d.height;

            Graphics2D g2=(Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);
            g2.drawImage(myimage.getImage(), 0, 0, w, h, this);

            if (isSelected) {
                AlphaComposite ac=
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
                g2.setComposite(ac);
                g2.setColor(new Color(0, 0, 100));
                g2.fillRect(1, 1, w-2, h-2);
            }
            if (isEntered) {
                AlphaComposite ac=
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
                g2.setComposite(ac);
                g2.setColor(new Color(0, 0, 80));
                Stroke stroke=new BasicStroke(3f);
                g2.setStroke(stroke);
                g2.drawRect(2, 2, w-4, h-4);

            }

        }

        // Create an Icon wich belongs to a group
        // If Vector is null, then it's a simple toggle button
        public myJIcon(String nm, Vector v, int sze, boolean isgrouped) {
            name=nm;
            this.isSelected=false;
            this.isGrouped=isgrouped;
            this.V=v;
            this.iconsize=sze;
            URL myurl=getClass().getResource("/eric/GUI/icons/bar/"+name+".png");
            myimage=new ImageIcon(myurl);
//            this.setIcon(new ImageIcon(myurl));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createEmptyBorder());

            float k=(1.0f*sze)/myimage.getIconWidth();
            int w=Math.round(k*myimage.getIconWidth());
            int h=Math.round(k*myimage.getIconHeight());
            fixsize(this, w, h);
            this.setContentAreaFilled(false);
            this.setFocusable(false);
            this.addMouseListener(this);
            code=V.size();
            V.add(this);
        }

	@Override
        public void setSelected(boolean bool) {
            isSelected=bool;
            repaint();
        }

	@Override
        public boolean isSelected() {
            return isSelected;
        }

        public void select() {
            if (isGrouped) {
                for (int i=0; i<V.size(); i++) {
                    JButton myicn=(JButton) V.get(i);
                    if (myicn.isSelected()) {
                        myicn.setSelected(false);
                        myicn.repaint();
                    }
                }
                isSelected=true;
                isEntered=false;
            } else {
                if (isSelected) {
                    isSelected=false;
                } else {
                    isSelected=true;
                    isEntered=false;
                }
            }
            repaint();
        }

	@Override
        public void mouseClicked(MouseEvent e) {
        }

	@Override
        public void mousePressed(MouseEvent e) {
            select();
        }

	@Override
        public void mouseReleased(MouseEvent e) {
            if (!(isSelected)) {
                repaint();
            }

        }

	@Override
        public void mouseEntered(MouseEvent e) {
            if (!(isSelected)) {
                isEntered=true;
                repaint();
            }
        }

	@Override
        public void mouseExited(MouseEvent e) {
            if (!(isSelected)) {
                isEntered=false;
                repaint();
            }
        }
    }

    class myJIconLine extends ContentLine {

        String contextHelp="";
        Vector V;
        int maxpercol=2;

//        public void paintComponent(java.awt.Graphics g){
//            javax.swing.ImageIcon myicon=new javax.swing.ImageIcon(getClass().getResource("/eric/GUI/icons/palette/palbackground.gif"));
//            java.awt.Dimension d = this.getSize();
//            g.drawImage(myicon.getImage(),0,0,d.width, d.height,this);
//        }
        public myJIconLine(String icons, int iconsize, int IconsPerCols, boolean isgrouped) {
            super();
            setAlignmentY(0.5f);
            maxpercol=IconsPerCols;
            this.setFocusable(false);
            String[] myname=icons.split(",");

            V=new Vector();
            JEricPanel col=null;
            for (int i=0; i<myname.length; i++) {
                if ((myname[i].equals(""))||(i%maxpercol==0)) {
                    if (col!=null) {
//                        this.add(margin(1));
                        this.add(col);
                    }
                    col=new JEricPanel();
                    col.setOpaque(false);
                    col.setLayout(new javax.swing.BoxLayout(col, javax.swing.BoxLayout.Y_AXIS));
                }
                if (!(myname[i].equals(""))) {
                    myJIcon myicon=new myJIcon(myname[i], V, iconsize, isgrouped);
                    myicon.addMouseListener(new MouseAdapter() {

			@Override
                        public void mousePressed(MouseEvent e) {
                            myJIcon icn=(myJIcon) e.getComponent();
                            doaction(icn);
                            doHelp();
                        }
                    });
                    col.add(myicon);
                }
            }
            this.add(col);
        }

        public void setSelect(int i) {
            myJIcon myicon=(myJIcon) V.elementAt(i);
            myicon.select();

        }

        public void deselectAll() {
            myJIcon myicon=(myJIcon) V.elementAt(0);
            myicon.select();
            myicon.setSelected(false);
//            for (int i=0; i<V.size()-1; i++) {
//                myJIcon myicon=(myJIcon) V.elementAt(i);
//                myicon.setSelected(false);
//            }
        }

        public void forceSelect(int i) {
            myJIcon myicon=(myJIcon) V.elementAt(i);
            myicon.setSelected(true);
        }

        public void doaction(myJIcon icn) {
        }

        public void doHelp() {
            if (!contextHelp.equals("")) {
                ZF.setinfo(contextHelp, false);
            }
        }

        public void doHelp(String contexth) {
            ZF.setinfo(contexth, false);
        }
    }

    class myJColorLine extends myJIconLine {

        JColorPicker CPK;

        public myJColorLine(int iconsize, int IconsPerCols) {
            super("color0,color1,color2,color3,color4,color5", iconsize, IconsPerCols, true);
            contextHelp="prop_color";
            CPK=new JColorPicker(23, 6, 3, V) {

		@Override
                public void doChange() {
                    if (MultipleObjects!=null) {
                        multiple_setColor(0, getCurrentColor());
                    }
                    O.setColor(0, getCurrentColor());
                    redcolor.init();
                    greencolor.init();
                    bluecolor.init();
                    ZC.repaint();
                }

		@Override
                public void afterSelect() {
                    O.setColor(0, getCurrentColor());
                    doHelp("prop_scolor");
                    ZC.repaint();
                }

		@Override
                public void setPalettes() {
                    setUsedColors(ZC.getConstruction().getSpecialColors());
                }
            };
            this.add(CPK);

        }

	@Override
        public void doaction(myJIcon icn) {
            if (MultipleObjects!=null) {
                multiple_setColor(icn.code);
            }
            O.setColor(icn.code);
            redcolor.init();
            greencolor.init();
            bluecolor.init();
            ZC.repaint();
	    ZC.update_distant(O, 3);
        }

        public void init() {
            if (O==null) {
                return;
            }
            if (O.getSpecialColor()!=null) {
                CPK.setCurrentColor(O.getSpecialColor());
                CPK.Select();
            } else {
                setSelect(O.getColorIndex(true));
            }
            repaint();
        }
    }

    class myJColorTypeLine extends myJIconLine {

        public myJColorTypeLine(int lh, int IconsPerCols) {
            super("thickness0,thickness1,thickness2", lh, IconsPerCols, true);
            contextHelp="prop_thickness";
        }

	@Override
        public void doaction(myJIcon icn) {
            if (MultipleObjects!=null) {
                multiple_setColorType(icn.code);
            }
            O.setColorType(icn.code);
            ZC.repaint();
        }
    }

    class myJSegmentCodeLine extends myJIconLine {

        public myJSegmentCodeLine(int lh, int IconsPerCols) {
            super("cod0,cod1,cod2,cod3,cod4,cod5", lh, IconsPerCols, true);
            contextHelp="prop_thickness";

        }

	@Override
        public void doaction(myJIcon icn) {
//            O.setColorType(icn.code);
            rene.zirkel.objects.SegmentObject S=(rene.zirkel.objects.SegmentObject) O;
            S.setSegmentCode(icn.code);
            ZC.repaint();
        }

        public void init() {
            rene.zirkel.objects.SegmentObject S=(rene.zirkel.objects.SegmentObject) O;
            setSelect(S.getSegmentCode());
        }
    }

    class myJTypeLine extends myJIconLine {

        public myJTypeLine(int lh, int IconsPerCols) {
            super("type0,type1,type2,type3,type4,type5", lh, IconsPerCols, true);
            contextHelp="prop_type";
        }

	@Override
        public void doaction(myJIcon icn) {
            if (MultipleObjects!=null) {
                multiple_setType(icn.code);
            }
            O.setType(icn.code);

            ZC.repaint();
        }
    }

    class myJCircleLine extends myJIconLine {

        public myJCircleLine(int lh, int IconsPerCols) {
            super("partial,filled,obtuse,solid", lh, IconsPerCols, false);
            contextHelp="prop_circleline";
        }

        public void init() {
            myJIcon icn;
            icn=(myJIcon) (V.get(0));
            icn.setSelected(O.isPartial());
            icn=(myJIcon) V.get(1);
            icn.setSelected(O.isFilled());
            icn=(myJIcon) V.get(2);
            icn.setSelected(O.getObtuse());
            icn=(myJIcon) V.get(3);
            icn.setSelected(O.isSolid());
        }

	@Override
        public void doaction(myJIcon icn) {
            switch (icn.code) {
                case 0:
                    O.setPartial(icn.isSelected);
                    O.getConstruction().updateCircleDep();
                    break;
                case 1:
                    O.setFilled(icn.isSelected);
                    break;
                case 2:
                    O.setObtuse(icn.isSelected);
                    break;
                case 3:
                    O.setSolid(icn.isSelected);
                    break;
                default:
                    break;
            }
            ZC.repaint();
        }
    }

    class myJShowLine extends myJIconLine {

        public myJShowLine(int lh, int IconsPerCols) {
            super("hide,showvalue,showname", lh, IconsPerCols, false);
            contextHelp="prop_showline";
        }

        public void init() {
            myJIcon icn;
            for (int i=1; i<V.size(); i++) {
                icn=(myJIcon) (V.get(i));
                icn.setVisible(false);
            }
            icn=(myJIcon) (V.get(0));
            icn.setSelected(O.isHidden());

            if (!O.canDisplayName()) {
                return;
            }
            icn=(myJIcon) V.get(2);
            icn.setSelected(O.showName());
            icn.setVisible(true);
            icn=(myJIcon) V.get(1);
            icn.setSelected(O.showValue());
            icn.setVisible(true);
        }

	@Override
        public void doaction(myJIcon icn) {
            switch (icn.code) {
                case 0:
                    if (MultipleObjects!=null) {
                        multiple_setHidden(icn.isSelected);
                    }
                    O.setHidden(icn.isSelected);
                    break;
                case 1:
                    if (MultipleObjects!=null) {
                        multiple_setShowValue(icn.isSelected);
                    }
                    O.setShowValue(icn.isSelected);
                    break;
                case 2:
                    if (MultipleObjects!=null) {
                        multiple_setShowName(icn.isSelected);
                    }
                    O.setShowName(icn.isSelected);
                    break;
                default:
                    ;
                    break;
            }
            ZC.update_distant(O, 3);
            ZC.repaint();
        }
    }

    class myJBoldLine extends myJIconLine {

        public myJBoldLine(int lh, int IconsPerCols) {
            super("bold,large", lh, IconsPerCols, false);
            contextHelp="prop_boldline";
        }

        public void init() {
            myJIcon icn;
            for (int i=1; i<V.size(); i++) {
                icn=(myJIcon) (V.get(i));
                icn.setVisible(false);
            }
            icn=(myJIcon) V.get(0);
            icn.setSelected(O.isBold());
            icn.setVisible(true);
            icn=(myJIcon) V.get(1);
            icn.setSelected(O.isLarge());
            icn.setVisible(true);
        }

	@Override
        public void doaction(myJIcon icn) {
            switch (icn.code) {
                case 0:
                    if (MultipleObjects!=null) {
                        multiple_setBold(icn.isSelected);
                    }
                    O.setBold(icn.isSelected);
                    break;
                case 1:
                    if (MultipleObjects!=null) {
                        multiple_setLarge(icn.isSelected);
                    }
                    O.setLarge(icn.isSelected);
                    break;
                default:
                    ;
                    break;
            }
            ZC.repaint();
        }
    }

    class myJAreaLine extends myJIconLine {

        public myJAreaLine(int lh, int IconsPerCols) {
            super("filled,solid", lh, IconsPerCols, false);
            contextHelp="prop_polyline";
        }

        public void init() {
            myJIcon icn;
            icn=(myJIcon) V.get(0);
            icn.setSelected(O.isFilled());
            icn=(myJIcon) V.get(1);
            icn.setSelected(O.isSolid());
        }

	@Override
        public void doaction(myJIcon icn) {
            switch (icn.code) {
                case 0:
                    O.setFilled(icn.isSelected);
                    break;
                case 1:
                    O.setSolid(icn.isSelected);
                    break;
                default:
                    break;
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }
    }

    class myJAngleLine0 extends myJIconLine {

        public myJAngleLine0(int lh, int IconsPerCols) {
            super("filled,obtuse,solid", lh, IconsPerCols, false);
        }

        public void init() {
            myJIcon icn;
            icn=(myJIcon) V.get(0);
            icn.setSelected(O.isFilled());
            icn=(myJIcon) V.get(1);
            icn.setSelected(O.getObtuse());
            icn=(myJIcon) V.get(2);
            icn.setSelected(O.isSolid());
        }

	@Override
        public void doaction(myJIcon icn) {
            switch (icn.code) {
                case 0:
                    O.setFilled(icn.isSelected);
                    break;
                case 1:
                    O.setObtuse(icn.isSelected);
                    break;
                case 2:
                    O.setSolid(icn.isSelected);
                    break;
                default:
                    break;
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }
    }

    class myJAngleLine1 extends myJIconLine {

        public myJAngleLine1(int lh, int IconsPerCols) {
            super("angle0,angle1,angle2,angle3", lh, IconsPerCols, true);
        }

	@Override
        public void doaction(myJIcon icn) {
            O.setDisplaySize(icn.code);
            ZC.repaint();
        }
    }

    class myJLineLine extends myJIconLine {

        public myJLineLine(int lh, int IconsPerCols) {
            super("plines", lh, IconsPerCols, false);
        }

        public void init() {
            myJIcon icn;
            icn=(myJIcon) (V.get(0));
            icn.setSelected(O.isPartial());
        }

	@Override
        public void doaction(myJIcon icn) {
            O.setPartial(icn.isSelected);
            ZC.repaint();
        }
    }

    class myJFilledLine extends myJIconLine {

        public myJFilledLine(int lh, int IconsPerCols) {
            super("filled", lh, IconsPerCols, false);
        }

        public void init() {
            myJIcon icn;
            icn=(myJIcon) V.get(0);
            icn.setSelected(O.isFilled());
        }

	@Override
        public void doaction(myJIcon icn) {
            O.setFilled(icn.isSelected);
            ZC.repaint();
        }
    }

    class myJConditional extends myJLine {

        String origin="";
        String TAG;
        String COMMENT;
        boolean displayed=false;
        private ImageIcon myimage;

        public myJConditional(String tag, String comment, String txt, int comwidth, int width, int height) {
            super(comment, txt, comwidth, width, height, true);
            if (tag.equals("z")) {
                contextHelp="prop_zindex";
            }
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
            TAG=tag;
        }

        public myJConditional(String tag, int width, int height) {
            super(Loc(tag), "", 0, width, height, true);
            contextHelp="prop_conditionals";
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+",");
            this.JTF.setFont(F_ConditionalField);
            TAG=tag;
            URL myurl=getClass().getResource("/eric/GUI/icons/bar/c"+TAG+".png");
            if (myurl!=null) {
                myimage=new ImageIcon(myurl);

                JButton mybtn=new JButton();
                mybtn.setOpaque(false);
                mybtn.setFocusable(false);
                mybtn.setToolTipText(Loc(TAG));
                mybtn.setContentAreaFilled(false);
                mybtn.setBorder(BorderFactory.createEmptyBorder());
                mybtn.setIcon(myimage);
                this.add(mybtn, 0);
                this.add(margin(2), 1);
                fixsize(this, this.W+myimage.getIconWidth()+2, this.H);
                this.revalidate();
            }

        }

        public void init() {
            displayed=(O.getConditional(TAG)!=null);
            origin=(displayed)?O.getConditional(TAG).toString():"";
            setText(origin);
            setInitValue();
        }

        public void setDisplayed(boolean bool) {
            displayed=bool;
        }

        public boolean isDisplayed() {
            return displayed;
        }

        public boolean haveConditional() {
            return (O.getConditional(TAG)!=null);
        }

	@Override
        public void doAction(Component e) {
            if (MultipleObjects!=null) {
                multiple_clearConditional(TAG);
            }
            O.clearConditional(TAG);
            if (isValidExpression(getText())) {

                if (MultipleObjects!=null) {
                    multiple_addConditional(TAG, getText().trim());
                }
                Expression myE=new Expression(getText().trim(), O.getConstruction(), O, null, false);
                O.addConditional(TAG, myE);
                ZC.update_distant(O, 3);
            }
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

	@Override
        public void doQuitMe(Component e) {
            if (O==null) {
                return;
            }
            if ((getText().trim().equals(""))||((Cinside)&&(JTF.getText().equals(InitValue)))) {
                O.clearConditional(TAG);
                origin="";
                return;
            }
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                if (!(isValidExpression(origin))) {
                    origin="";
                    O.clearConditional(TAG);
                } else {
                    O.addConditional(TAG, new Expression(origin, O.getConstruction(), O, null, false));
                }


                setText(origin);
                ZC.recompute();
                ZC.validate();
                ZC.repaint();
                this.JTF.requestFocus();
            } else {
                origin=getText();
            }
        }
    }

    class myJTextField extends JTextField implements KeyListener {

        private myJTextField(String txt) {
            super(txt);
            this.addKeyListener(this);
        }

	@Override
        public void keyTyped(KeyEvent e) {
        }

	@Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                PaletteManager.setSelected_with_clic("move", true);
            }
        }

	@Override
        public void keyReleased(KeyEvent arg0) {
        }
    }

    class myJTrackDMinChooser extends ContentLine {

        TrackObject t;
        JComboBox JCB;

        public myJTrackDMinChooser(String comment, int comwidth, int linewidth, int lineheight) {
            super(linewidth, lineheight);
            setFocusable(false);
            addnewlabel(comment, comwidth, lineheight);

            JCB=new JComboBox();
            JCB.setUI((ComboBoxUI) MyComboBoxUI.createUI(JCB));
            JCB.setFont(F_ComboBox);
            JCB.addItem("10");
            JCB.addItem("50");
            JCB.addItem("100");
            JCB.addItem("200");
            JCB.addItem("500");
            JCB.addItem("1000");
            JCB.addItem("2000");
            JCB.addItem("5000");
            JCB.addItem("50000");
            JCB.setMaximumRowCount(5);
            JCB.setOpaque(false);
            JCB.setFocusable(false);
            JCB.setEditable(false);
            fixsize(JCB, linewidth-comwidth, lineheight);
            JCB.addItemListener(new ItemAdapter());
            add(JCB);
        }

        class ItemAdapter implements ItemListener {

	    @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange()==ItemEvent.SELECTED) {
                    doAction();
                }
            }
        }

        public void addnewlabel(String comment, int w, int h) {
            JLabel myTXT=new JLabel(comment);
            myTXT.setFocusable(false);
            myTXT.setFont(F_Label);
            fixsize(myTXT, w, h);
            add(myTXT);
        }

        public void doAction() {
            String nb=(String) JCB.getSelectedItem();
            t.setDMin(1/Double.valueOf(nb).doubleValue());
            t.NeedsRecompute=true;
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {

            t=(TrackObject) O;
            int i=(int) Math.round(1/t.getDMin());
            int j;
            if (i<25) {
                j=0;
            } else if (i<75) {
                j=1;
            } else if (i<150) {
                j=2;
            } else if (i<350) {
                j=3;
            } else if (i<750) {
                j=4;
            } else if (i<1500) {
                j=5;
            } else if (i<3500) {
                j=6;
            } else if (i<7500) {
                j=7;
            } else {
                j=8;
            }
            JCB.setSelectedIndex(j);
            String nb=(String) JCB.getSelectedItem();
            t.setDMin(1/Double.valueOf(nb).doubleValue());
            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }
    }

    class myJEqXYDHorChooser extends ContentLine {

        EquationXYObject t;
        JComboBox JCB;

        public myJEqXYDHorChooser(String comment, int comwidth, int linewidth, int lineheight) {
            super(linewidth, lineheight);
            setFocusable(false);
            addnewlabel(comment, comwidth, lineheight);

            JCB=new JComboBox();
            JCB.setUI((ComboBoxUI) MyComboBoxUI.createUI(JCB));
            JCB.setFont(F_ComboBox);
            JCB.addItem("10");
            JCB.addItem("20");
            JCB.addItem("50");
            JCB.addItem("100");
            JCB.addItem("200");
            JCB.addItem("500");
            JCB.addItem("1000");
            JCB.setMaximumRowCount(5);
            JCB.setOpaque(false);
            JCB.setFocusable(false);
            JCB.setEditable(false);
            fixsize(JCB, linewidth-comwidth, lineheight);
            JCB.addItemListener(new ItemAdapter());
            add(JCB);
        }

        class ItemAdapter implements ItemListener {

	    @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange()==ItemEvent.SELECTED) {
                    doAction();
                }
            }
        }

        public void addnewlabel(String comment, int w, int h) {
            JLabel myTXT=new JLabel(comment);
            myTXT.setFocusable(false);
            myTXT.setFont(F_Label);
            fixsize(myTXT, w, h);
            add(myTXT);
        }

        public void doAction() {
            String nb=(String) JCB.getSelectedItem();
            t.setDhor(Integer.parseInt(nb));

            ZC.recompute();
            ZC.validate();
            ZC.repaint();
        }

        public void init() {

            t=(EquationXYObject) O;
            int i=t.getDhor();
            int j;
            if (i<15) {
                j=0;
            } else if (i<25) {
                j=1;
            } else if (i<55) {
                j=2;
            } else if (i<150) {
                j=3;
            } else if (i<250) {
                j=4;
            } else if (i<550) {
                j=5;
            } else {
                j=6;
            }
            JCB.setSelectedIndex(j);

        }
    }

    class myJBoundary extends myJIconLine {
	protected boolean isClosedtAlreadySelected = false;
	protected boolean isOpenAlreadySelected = false;
	protected FunctionObject f;

	public myJBoundary(String name, int lh, int IconsPerCols) {
	    super(name, lh, IconsPerCols, true);
        }

	public void init() {
	}

	@Override
        public void doaction(myJIcon icn) {
	    switch(icn.code) {
		case 0:
		    if(isClosedtAlreadySelected) {
			icn.setSelected(false);
		    }
		    isOpenAlreadySelected = false;
		    isClosedtAlreadySelected=!isClosedtAlreadySelected;
		    if(this instanceof myJMinBoundary) {
			f.setMinClosed(icn.isSelected);
			f.setMinOpen(false);
		    } else {
			f.setMaxClosed(icn.isSelected);
			f.setMaxOpen(false);
		    }
		    break;
		case 1:
		    if(isOpenAlreadySelected) {
			icn.setSelected(false);
		    }
		    isClosedtAlreadySelected = false;
		    isOpenAlreadySelected=!isOpenAlreadySelected;
		    if(this instanceof myJMinBoundary) {
			f.setMinOpen(icn.isSelected);
			f.setMinClosed(false);
		    } else {
			f.setMaxOpen(icn.isSelected);
			f.setMaxClosed(false);
		    }
		    break;
		default:
		    break;
	    }
	    ZC.repaint();
        }
    }

    class myJMinBoundary extends myJBoundary {

	public myJMinBoundary(int lh, int IconsPerCols) {
	    super("type2,open_left", lh, IconsPerCols);
        }

	@Override
	public void init() {
	    f = (FunctionObject) O;
	    ((myJIcon) V.get(0)).setSelected(isClosedtAlreadySelected = f.getMinClosed());
	    ((myJIcon) V.get(1)).setSelected(isOpenAlreadySelected = f.getMinOpen());
	}
    }
    class myJMaxBoundary extends myJBoundary {

	public myJMaxBoundary(int lh, int IconsPerCols) {
	    super("type2,open_right", lh, IconsPerCols);
        }

	@Override
	public void init() {
	    f = (FunctionObject) O;
	    ((myJIcon) V.get(0)).setSelected(isClosedtAlreadySelected = f.getMaxClosed());
	    ((myJIcon) V.get(1)).setSelected(isOpenAlreadySelected = f.getMaxOpen());
	}
    }
}
