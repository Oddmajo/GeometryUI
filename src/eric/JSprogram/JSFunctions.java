/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.*;
import eric.GUI.palette.PaletteManager;
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
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.expression.Expression;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.EquationXYObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedCircleObject;
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
 * @author erichake with addons by Dibs
 */
public class JSFunctions {

    static String[] allnames={"cm", "Input", "Entree", "Prompt", "Signaler", "Print", "Afficher", "Println", "Afficherligne", "Shownames", "MontrerNoms", "Hidenames", "CacherNoms", "Hide", "Cacher", "Show",
        "Montrer", "Point", "PointOn", "PointSur", "ImplicitPlot", "TraceImplicite", "Intersection", "Intersection2", "Intersections", "MidPoint", "Milieu", "Symmetry", "SymetrieCentrale", "Line", "Droite", "Segment", "Circle", "Cercle", "Parallel",
        "Parallele", "Perpendicular", "Perpendiculaire", "FixedCircle", "CercleRayon", "Move", "Deplacer", "X", "Y", "GetExpressionValue", "PrendreValeurExpression", "Ray", "DemiDroite", "Angle", "Polygon", "Polygone", "Quadric", "Quadrique",
        "CartesianFunction", "FonctionCartesienne", "ParametricFunction", "FonctionParametrique", "SetColor", "MettreCouleur", "SetText", "MettreTexte", "SetThickness", "MettreEpaisseur", "SetFixed", "MettreFixe", "Vector", "Vecteur", "SetRGBColor",
        "MettreCouleurRVB", "SetShowName", "MettreMontrerNom", "SetShowValue", "MettreMontrerValeur", "SetFilled", "MettreRempli", "SetSolid", "MettreOpaque", "SetPartial", "MettrePartiel", "Expression", "Text", "Texte", "SetAlias", "MettreAlias", "SetMagneticObjects",
        "MettreObjetsMagnetiques", "AddMagneticObject", "AjouterObjetMagnetique", "SetMagneticRay", "MettreRayonMagetique", "SetPointType", "MettreTypePoint", "InteractiveInput", "EntreeInteractive", "FixedSegment", "SegmentFixe", "SetHide", "MettreCache", "Pause",
        "Delete", "Supprimer", "SetExpressionValue", "MettreValeurExpression", "Reflection", "SymetrieAxiale", "Translation", "PerpendicularBisector", "Mediatrice", "AngleBisector", "Bissectrice", "Circle3pts", "Cercle3pts",
        "Arc3pts", "FixedAngle", "AngleFixe", "Circle3", "Cercle3", "ExecuteMacro", "ExecuterMacro", "Alert", "Alerte", "Conditional", "Conditionnel", "Layer", "Calque", "GetRed", "PrendreRouge", "GetGreen", "PrendreVert", "GetBlue", "PrendreBleu", "PenDown", "CrayonBaisse",
        "SetRed", "MettreRouge", "SetGreen", "MettreVert", "SetBlue", "MettreBleu", "DPPoint", "DPLine", "DPSegment", "DPPerpendicular", "DPPerpendicularBisector",
        "DPMidPoint", "DPCircle", "DPReflexion", "DPSymmetry", "DPAngleBisector", "DPCommonPerpendicular", "DPRay",
        "getC", "getZC", "refreshZC", "rafraichirZC", "Load", "Origin", "Origine", "Extremity", "Extremite", "GetText", "PrendreTexte", "ReflexAngle", "AngleRentrant", "Exists", "Existe", "SetIncrement", "MettreIncrement",
        "GetOpenFile", "PrendreFichierOuvert", "OrderedIntersection", "IntersectionOrdonnee", "SetMinOpen", "SetMinClosed", "SetMaxOpen", "SetMaxClosed","Distance", "Point3D", "X3D", "Y3D", "Z3D", "Distance3D", 
         "getCONSOLE", "prendreCONSOLE", "Sphere", "SphEre", "FixedSphere", "SphEreRayon", "Projection3D", "Reflection3D", "Symetrie3DPlan", "Symmetry3D", "SymetrieCentrale3D", "Translation3D", "Circle3D", "Cercle3D", "FixedCircle3D", "CercleRayon3D", "Circle3D3pts", "Cercle3D3pts","SetIconSelection","IsIconSelected","AttacherTortue",
        "BaisserStylo","LeverStylo","Avancer","TournerGauche","TournerDroite","MontrerTortue","CacherTortue","OrienterTortue","VitesseTortue","Reculer","PivoterGauche","PivoterDroite","PivoterHaut","PivoterBas","Viser","Triangle","Quadrilatere","Quadrangle","FixTurtle","TurtleDown","TurtleUp","MoveForward","MoveBackward","TurnLeft",
        "TurnRight","RollLeft","RollRight","PitchUp","PitchDown","ShowTurtle","HideTurtle","OrientateTurtle","OrientateTowards","TurtleSpeed","Liberate","Liberer"};

    static PointObject ptTortue, pt3DTortue;
    static PointObject ptTortue2, pt3DTortue2;
    static PointObject ptSupport;
    static PointObject ptSupport2;
    static PointObject ptO;
    static PointObject ptO2;
    static PointObject ptO3;
    static VectorObject dirTortue;
    static VectorObject dirTortueVisu;
    static ExpressionObject longueurTortue, longueurTortue3D;
    static boolean styloBaisse, turtleIs3D;
    static double vitesseTortue = 100;
    static double nbSteps;
    static PointObject t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24;
    static Vector p1, p2, tete, p3, p4, cara;
    static AreaObject pp1, pp2, pp3, pp4, ttete, ccara;
    static PointObject pt3D0, pt3D1, pt3D2, pt3D3, pt3D1b, pt3D2b, pt3D3b, pt3DSupport, pt3DSupport01, pt3DSupport02, pt3DSupport03, t3D1, t3D2, t3D3, t3D4;
    static VectorObject dirTortue3D1, dirTortue3D2, dirTortue3D3, dirTortueVisu3D1, dirTortueVisu3D2, dirTortueVisu3D3;
    static Vector p3D;
    static AreaObject pp3D;
    static PointObject oldpt3D2, oldpt3D3;
    static int iTG, iTD, iTG3D, iTD3D, iPG, iPD, iPH, iPB, iOr, iVi, iOr3D, iVi3D;
    
    
    public static String[] getKeywords() {
        return allnames;
    }
    
    public static String AttacherTortue(String name) {
       pt3DTortue=(PointObject) getC().find(name);
        if (pt3DTortue!=null) {
            turtleIs3D=pt3DTortue.is3D();
         synchronized (getC()) {
         if (turtleIs3D) { 
                        try {  if (pt3D0==null) {
                        longueurTortue3D=new ExpressionObject(getC(), 0, 0);
                        longueurTortue3D.setDefaults();
                        longueurTortue3D.setHidden(true);
                        longueurTortue3D.setName("kTortue3D");
                        longueurTortue3D.setPrompt(longueurTortue3D.getName());
                        iTG3D=1; iTD3D=1; iPG=1; iPD=1; iPH=1; iPB=1; iOr3D=1; iVi3D=1;
                        styloBaisse = true;
                        
                        pt3D0=new PointObject(getC(),0,0);
                        pt3D0.setName("turtleVpt3D0");
                        
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleVpt3D1");
                        
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleVpt3D2");
                        
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleVpt3D3");
                        
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleVpt3D1b");
                        
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleVpt3D2b");
                        
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtleVpt3D3b");
                        
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleVdirTortue3D1");
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleVdirTortue3D2");
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleVdirTortue3D3");
                        
                        pt3DSupport=new PointObject(getC(),0,0);
                        pt3DSupport.setName("turtleVpt3DSupport");
                        pt3DSupport01=new PointObject(getC(),0,0);
                        pt3DSupport01.setName("turtleVpt3DSupport01");
                        pt3DSupport02=new PointObject(getC(),0,0);
                        pt3DSupport02.setName("turtleVpt3DSupport02");
                        pt3DSupport03=new PointObject(getC(),0,0);
                        pt3DSupport03.setName("turtleVpt3DSupport03");
                        
                        dirTortueVisu3D1= new VectorObject(getC(),pt3DSupport,pt3DSupport01);
                        dirTortueVisu3D1.setName("turtleVdirTortueVisu3D1");
                        dirTortueVisu3D2= new VectorObject(getC(),pt3DSupport,pt3DSupport02);
                        dirTortueVisu3D2.setName("turtleVdirTortueVisu3D2");
                        dirTortueVisu3D3= new VectorObject(getC(),pt3DSupport,pt3DSupport03);
                        dirTortueVisu3D3.setName("turtleVdirTortueVisu3D3");
                        
                        p3D= new Vector();
                        t3D1=new PointObject(getC(),0,0);
                        t3D1.setName("turtleVt3D1");
                        t3D2=new PointObject(getC(),0,0);
                        t3D2.setName("turtleVt3D2");
                        t3D3=new PointObject(getC(),0,0);
                        t3D3.setName("turtleVt3D3");
                        t3D4=new PointObject(getC(),0,0);
                        t3D4.setName("turtleVt3D4");
                        pp3D = new AreaObject(getC(),p3D);
                        pp3D.setName("turtleVpp3D");
                        pp3D.setDefaults();
                        pp3D.setFilled(true);
                        pp3D.setSpecialColor(new Color(138,74,0));

                    }
                    try {
                        longueurTortue3D.setExpression("60/pixel", getC());
                        } catch (final ConstructionException e) {
                           return "";
                        }
                    addObject(longueurTortue3D);
                    addObject(pt3D0);
                    addObject(pt3D1);
                    addObject(pt3D2);
                    addObject(pt3D3);
                    addObject(pt3D1b);
                    addObject(pt3D2b);
                    addObject(pt3D3b);
                    addObject(dirTortue3D1);
                    addObject(dirTortue3D2);
                    addObject(dirTortue3D3);
                    addObject(pt3DSupport);
                    addObject(pt3DSupport01);
                    addObject(pt3DSupport02);
                    addObject(pt3DSupport03);
                    addObject(dirTortueVisu3D1);
                    addObject(dirTortueVisu3D2);
                    addObject(dirTortueVisu3D3);
                    addObject(t3D1);
                    addObject(t3D2);
                    addObject(t3D3);
                    addObject(pp3D);
                    pt3D0.setIs3D(true);
                    pt3D1.setIs3D(true);
                    pt3D2.setIs3D(true);
                    pt3D3.setIs3D(true);
                    pt3D0.setFixed("0","0","0");
                    pt3D0.validate();
                    pt3D1.setFixed("1","0","0");
                    pt3D1.validate();
                    pt3D2.setFixed("0","1","0");
                    pt3D3.validate();
                    pt3D3.setFixed("0","0","1");
                    pt3D0.setHidden(true);
                    pt3D1.setSuperHidden(true);
                    pt3D2.setSuperHidden(true);
                    pt3D3.setSuperHidden(true);
                    pt3D1b.setSuperHidden(true);
                    pt3D2b.setSuperHidden(true);
                    pt3D3b.setSuperHidden(true);
                   
                   pt3D1b.setIs3D(true);
                    pt3D2b.setIs3D(true);
                    pt3D3b.setIs3D(true);
                    pt3D1b.setFixed("1","0","0");
                    pt3D2b.setFixed("0","1","0");
                    pt3D3b.setFixed("0","0","1");
                    
                    pt3DSupport.setIs3D(true);
                    pt3DSupport01.setIs3D(true);
                    pt3DSupport02.setIs3D(true);
                    pt3DSupport03.setIs3D(true);
                   
                    t3D1.setIs3D(true);
                    t3D2.setIs3D(true);
                    t3D3.setIs3D(true);
                    t3D4.setIs3D(true);
                    
                   dirTortue3D1.setDefaults();
                   dirTortue3D1.setHidden(true);
                   
                   dirTortue3D2.setDefaults();
                   dirTortue3D2.setHidden(true);
                   
                   dirTortue3D3.setDefaults();
                   dirTortue3D3.setHidden(true);
                   
                   dirTortueVisu3D1.setDefaults();
                   dirTortueVisu3D1.setHidden(true);
                   
                   dirTortueVisu3D2.setDefaults();
                   dirTortueVisu3D2.setHidden(true);
                   
                   dirTortueVisu3D3.setDefaults();
                   dirTortueVisu3D3.setHidden(true);

                   pt3DTortue=(PointObject) getC().find(name);
                   
                   pt3DSupport.setSuperHidden(true);
                   pt3DSupport.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                   
                   pt3DSupport01.setSuperHidden(true);
                   pt3DSupport01.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D1.getName()+")");
                  
                   pt3DSupport02.setSuperHidden(true);
                   pt3DSupport02.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D2.getName()+")");
                   
                   pt3DSupport03.setSuperHidden(true);
                   pt3DSupport03.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D3.getName()+")");
                   
                   pt3DSupport.validate();
                   pt3DSupport01.validate();
                   pt3DSupport02.validate();
                   pt3DSupport03.validate();
                   dirTortueVisu3D1.validate();
                   dirTortueVisu3D2.validate();
                   dirTortueVisu3D3.validate();
                 
                   p3D.add(pt3DSupport);
                   
                   t3D1.setDefaults();
                   t3D1.setSuperHidden(true);
                   t3D1.setFixed("x3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D2.getName()+")","y3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D2.getName()+")","z3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D2.getName()+")");
                   p3D.add(t3D1);
                   
                   t3D2.setDefaults();
                   t3D2.setSuperHidden(true);
                   t3D2.setFixed("x3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")","y3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")","z3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")");
                   p3D.add(t3D2);
                   
                   t3D3.setDefaults();
                   t3D3.setSuperHidden(true);
                   t3D3.setFixed("x3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D2.getName()+")","y3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D2.getName()+")","z3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D2.getName()+")");
                   p3D.add(t3D3);
                   
                   t3D1.validate();
                   t3D2.validate();
                   t3D3.validate();
                   pp3D.validate();
                   pp3D.addConditional("solid",new Expression(pp3D.getName()+"<0",getC(),pp3D));
                   pp3D.addConditional("thick",new Expression(pp3D.getName()+"<0",getC(),pp3D));

                } catch (Exception e) {
                    return "";
                }
            return name;
            }
        
        else {
        try {  if (ptO==null) {
                    longueurTortue=new ExpressionObject(getC(), 0, 0);
                    longueurTortue.setDefaults();
                    longueurTortue.setHidden(true);
                    longueurTortue.setName("kTortue");
                    longueurTortue.setPrompt(longueurTortue.getName());
                    iTG=1; iTD=1; iPG=1; iPD=1; iPH=1; iPB=1; iOr=1; iVi=1;
                    styloBaisse = true;
                    ptO=new PointObject(getC(),0,0);
                    ptO.setName("turtleVptO");
                    ptO2=new PointObject(getC(),0,1);
                    ptO2.setName("turtleVptO2");
                    dirTortue= new VectorObject(getC(),ptO,ptO2);
                    dirTortue.setName("turtleVdirTortue");
                    ptSupport=new PointObject(getC(),0,0);
                    ptSupport.setName("turtleVptSupport");
                    ptSupport2=new PointObject(getC(),0,0);
                    ptSupport2.setName("turtleVptSupport2");
                    ptTortue2=new PointObject(getC(),0,0);
                    ptTortue2.setName("turtleVptTortue2");
                    dirTortueVisu= new VectorObject(getC(),ptSupport,ptSupport2);
                    dirTortueVisu.setName("turtleVdirTortueVisu");
                    p1= new Vector();
                    t1=new PointObject(getC(),0,0);
                    t1.setName("turtleVt1");
                    t2=new PointObject(getC(),0,0);
                    t2.setName("turtleVt2");
                    t3=new PointObject(getC(),0,0);
                    t3.setName("turtleVt3");
                    t4=new PointObject(getC(),0,0);
                    t4.setName("turtleVt4");
                    pp1 = new AreaObject(getC(),p1);
                    pp1.setName("turtleVpp1");
                    p2= new Vector();
                    t5=new PointObject(getC(),0,0);
                    t5.setName("turtleVt5");
                    t6=new PointObject(getC(),0,0);
                    t6.setName("turtleVt6");
                    pp2 = new AreaObject(getC(),p2);
                    pp2.setName("turtleVpp2");
                    
                    tete= new Vector();
                    t7=new PointObject(getC(),0,0);
                    t7.setName("turtleVt7");
                    t8=new PointObject(getC(),0,0);
                    t8.setName("turtleVt8");
                    t9=new PointObject(getC(),0,0);
                    t9.setName("turtleVt9");
                    t10=new PointObject(getC(),0,0);
                    t10.setName("turtleVt10");
                    t11=new PointObject(getC(),0,0);
                    t11.setName("turtleVt11");
                    ttete = new AreaObject(getC(),tete);
                    ttete.setName("turtleVttete");
                    p3= new Vector();
                    t12=new PointObject(getC(),0,0);
                    t12.setName("turtleVt12");
                    t13=new PointObject(getC(),0,0);
                    t13.setName("turtleVt13");
                    t14=new PointObject(getC(),0,0);
                    t14.setName("turtleVt14");
                    pp3 = new AreaObject(getC(),p3);
                    pp3.setName("turtleVpp3");
                    p4= new Vector();
                    t15=new PointObject(getC(),0,0);
                    t15.setName("turtleVt15");
                    t16=new PointObject(getC(),0,0);
                    t16.setName("turtleVt16");
                    t17=new PointObject(getC(),0,0);
                    t17.setName("turtleVt17");
                    pp4 = new AreaObject(getC(),p4);
                    pp4.setName("turtleVpp4");
                    cara= new Vector();
                    t18=new PointObject(getC(),0,0);
                    t18.setName("turtleVt18");
                    t19=new PointObject(getC(),0,0);
                    t19.setName("turtleVt19");
                    t20=new PointObject(getC(),0,0);
                    t20.setName("turtleVt20");
                    t21=new PointObject(getC(),0,0);
                    t21.setName("turtleVt21");
                    t22=new PointObject(getC(),0,0);
                    t22.setName("turtleVt22");
                    t23=new PointObject(getC(),0,0);
                    t23.setName("turtleVt23");
                    t24=new PointObject(getC(),0,0);
                    t24.setName("turtleVt24");
                    ccara = new AreaObject(getC(),cara);
                    ccara.setName("turtleVccara");
                    pp1.setDefaults();
                    pp1.setFilled(true);
                    pp1.setSpecialColor(new Color(138,102,66));
                    pp2.setDefaults();
                    pp2.setFilled(true);
                    pp2.setSpecialColor(new Color(138,102,66));
                    ttete.setDefaults();
                    ttete.setFilled(true);
                    ttete.setSpecialColor(new Color(138,102,66));
                    pp3.setDefaults();
                    pp3.setFilled(true);
                    pp3.setSpecialColor(new Color(138,102,66));
                    pp4.setDefaults();
                    pp4.setFilled(true);
                    pp4.setSpecialColor(new Color(138,102,66));
                    ccara.setDefaults();
                    ccara.setFilled(true);
                    ccara.setSpecialColor(new Color(82,139,139));
                }
              try {
                    longueurTortue.setExpression("60/pixel", getC());
                    } catch (final ConstructionException e) {
                        return "";
                    }
               addObject(longueurTortue);
               ptO.setHidden(true);
               addObject(ptO);
               ptO2.setSuperHidden(true);
               addObject(ptO2);
               dirTortue.validate();
               dirTortue.setDefaults();
               dirTortue.setHidden(true);
               addObject(dirTortue);
               ptTortue=(PointObject) getC().find(name);
               ptSupport.setSuperHidden(true);
               addObject(ptSupport);
               ptSupport.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
               ptSupport2.setSuperHidden(true);
               addObject(ptSupport2);
               ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
               ptTortue2.setDefaults();
               ptTortue2.setSuperHidden(true);
               addObject(ptTortue2);
               ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
               ptTortue2.validate();
               dirTortueVisu.validate();
               dirTortueVisu.setDefaults();
               dirTortueVisu.setHidden(true);
               addObject(dirTortueVisu);
               t1.setDefaults();
               t1.setSuperHidden(true);
               addObject(t1);
               t1.setFixed("x("+ptSupport.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t1);
               t2.setDefaults();
               t2.setSuperHidden(true);
               addObject(t2);
               t2.setFixed("x("+ptSupport.getName()+")-0.4*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.4*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t2);
               t3.setDefaults();
               t3.setSuperHidden(true);
               addObject(t3);
               t3.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t3);
               addObject(pp1);
               t4.setDefaults();
               t4.setSuperHidden(true);
               addObject(t4);
               t4.setFixed("x("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t4);
               t5.setDefaults();
               t5.setSuperHidden(true);
               addObject(t5);
               t5.setFixed("x("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t5);
               t6.setDefaults();
               t6.setSuperHidden(true);
               addObject(t6);
               t6.setFixed("x("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t6);
               addObject(pp2);
               t7.setDefaults();
               t7.setSuperHidden(true);
               addObject(t7);
               t7.setFixed("x("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.1*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.1*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t7);
               t8.setDefaults();
               t8.setSuperHidden(true);
               addObject(t8);
               t8.setFixed("x("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.15*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.15*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t8);
               t9.setDefaults();
               t9.setSuperHidden(true);
               addObject(t9);
               t9.setFixed("x("+ptSupport.getName()+")+1.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+1.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")");
               tete.add(t9);
               t10.setDefaults();
               t10.setSuperHidden(true);
               addObject(t10);
               t10.setFixed("x("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.15*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.15*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t10);
               t11.setDefaults();
               t11.setSuperHidden(true);
               addObject(t11);
               t11.setFixed("x("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.1*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.1*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t11);
               addObject(ttete);
               t12.setDefaults();
               t12.setSuperHidden(true);
               addObject(t12);
               t12.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t12);
               t13.setDefaults();
               t13.setSuperHidden(true);
               addObject(t13);
               t13.setFixed("x("+ptSupport.getName()+")+0.4*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")-0.4*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t13);
               t14.setDefaults();
               t14.setSuperHidden(true);
               addObject(t14);
               t14.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t14);
               addObject(pp3);
               t15.setDefaults();
               t15.setSuperHidden(true);
               addObject(t15);
               t15.setFixed("x("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t15);
               t16.setDefaults();
               t16.setSuperHidden(true);
               addObject(t16);
               t16.setFixed("x("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t16);
               t17.setDefaults();
               t17.setSuperHidden(true);
               addObject(t17);
               t17.setFixed("x("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t17);
               addObject(pp4);
               t18.setDefaults();
               t18.setSuperHidden(true);
               addObject(t18);
               t18.setFixed("x("+ptSupport.getName()+")","y("+ptSupport.getName()+")");
               cara.add(t18);
               t19.setDefaults();
               t19.setSuperHidden(true);
               addObject(t19);
               t19.setFixed("x("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t19);
               t20.setDefaults();
               t20.setSuperHidden(true);
               addObject(t20);
               t20.setFixed("x("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t20);
               t21.setDefaults();
               t21.setSuperHidden(true);
               addObject(t21);
               t21.setFixed("x("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t21);
               t22.setDefaults();
               t22.setSuperHidden(true);
               addObject(t22);
               t22.setFixed("x("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t22);
               t23.setDefaults();
               t23.setSuperHidden(true);
               addObject(t23);
               t23.setFixed("x("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t23);
               t24.setDefaults();
               t24.setSuperHidden(true);
               addObject(t24);
               t24.setFixed("x("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t24);
               addObject(ccara);

            } catch (Exception e) {
                return "";
            }
        return name;
        
                }
            }
         }
        else {
            return("");
        }
    }
    
    public static String FixTurtle(String name) {
        pt3DTortue=(PointObject) getC().find(name);
        if (pt3DTortue!=null) {
            turtleIs3D=pt3DTortue.is3D();
         synchronized (getC()) {
         if (turtleIs3D) { 
                        try {  if (pt3D0==null) {
                        longueurTortue3D=new ExpressionObject(getC(), 0, 0);
                        longueurTortue3D.setDefaults();
                        longueurTortue3D.setHidden(true);
                        longueurTortue3D.setName("kTortue3D");
                        longueurTortue3D.setPrompt(longueurTortue3D.getName());
                        iTG3D=1; iTD3D=1; iPG=1; iPD=1; iPH=1; iPB=1; iOr3D=1; iVi3D=1;
                        styloBaisse = true;
                        
                        pt3D0=new PointObject(getC(),0,0);
                        pt3D0.setName("turtleVpt3D0");
                        
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleVpt3D1");
                        
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleVpt3D2");
                        
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleVpt3D3");
                        
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleVpt3D1b");
                        
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleVpt3D2b");
                        
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtleVpt3D3b");
                        
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleVdirTortue3D1");
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleVdirTortue3D2");
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleVdirTortue3D3");
                        
                        pt3DSupport=new PointObject(getC(),0,0);
                        pt3DSupport.setName("turtleVpt3DSupport");
                        pt3DSupport01=new PointObject(getC(),0,0);
                        pt3DSupport01.setName("turtleVpt3DSupport01");
                        pt3DSupport02=new PointObject(getC(),0,0);
                        pt3DSupport02.setName("turtleVpt3DSupport02");
                        pt3DSupport03=new PointObject(getC(),0,0);
                        pt3DSupport03.setName("turtleVpt3DSupport03");
                        
                        dirTortueVisu3D1= new VectorObject(getC(),pt3DSupport,pt3DSupport01);
                        dirTortueVisu3D1.setName("turtleVdirTortueVisu3D1");
                        dirTortueVisu3D2= new VectorObject(getC(),pt3DSupport,pt3DSupport02);
                        dirTortueVisu3D2.setName("turtleVdirTortueVisu3D2");
                        dirTortueVisu3D3= new VectorObject(getC(),pt3DSupport,pt3DSupport03);
                        dirTortueVisu3D3.setName("turtleVdirTortueVisu3D3");
                        
                        p3D= new Vector();
                        t3D1=new PointObject(getC(),0,0);
                        t3D1.setName("turtleVt3D1");
                        t3D2=new PointObject(getC(),0,0);
                        t3D2.setName("turtleVt3D2");
                        t3D3=new PointObject(getC(),0,0);
                        t3D3.setName("turtleVt3D3");
                        t3D4=new PointObject(getC(),0,0);
                        t3D4.setName("turtleVt3D4");
                        pp3D = new AreaObject(getC(),p3D);
                        pp3D.setName("turtleVpp3D");
                        pp3D.setDefaults();
                        pp3D.setFilled(true);
                        pp3D.setSpecialColor(new Color(138,74,0));

                    }
                    try {
                        longueurTortue3D.setExpression("60/pixel", getC());
                        } catch (final ConstructionException e) {
                           return "";
                        }
                    addObject(longueurTortue3D);
                    addObject(pt3D0);
                    addObject(pt3D1);
                    addObject(pt3D2);
                    addObject(pt3D3);
                    addObject(pt3D1b);
                    addObject(pt3D2b);
                    addObject(pt3D3b);
                    addObject(dirTortue3D1);
                    addObject(dirTortue3D2);
                    addObject(dirTortue3D3);
                    addObject(pt3DSupport);
                    addObject(pt3DSupport01);
                    addObject(pt3DSupport02);
                    addObject(pt3DSupport03);
                    addObject(dirTortueVisu3D1);
                    addObject(dirTortueVisu3D2);
                    addObject(dirTortueVisu3D3);
                    addObject(t3D1);
                    addObject(t3D2);
                    addObject(t3D3);
                    addObject(pp3D);
                    pt3D0.setIs3D(true);
                    pt3D1.setIs3D(true);
                    pt3D2.setIs3D(true);
                    pt3D3.setIs3D(true);
                    pt3D0.setFixed("0","0","0");
                    pt3D0.validate();
                    pt3D1.setFixed("1","0","0");
                    pt3D1.validate();
                    pt3D2.setFixed("0","1","0");
                    pt3D3.validate();
                    pt3D3.setFixed("0","0","1");
                    pt3D0.setHidden(true);
                    pt3D1.setSuperHidden(true);
                    pt3D2.setSuperHidden(true);
                    pt3D3.setSuperHidden(true);
                    pt3D1b.setSuperHidden(true);
                    pt3D2b.setSuperHidden(true);
                    pt3D3b.setSuperHidden(true);
                   
                   pt3D1b.setIs3D(true);
                    pt3D2b.setIs3D(true);
                    pt3D3b.setIs3D(true);
                    pt3D1b.setFixed("1","0","0");
                    pt3D2b.setFixed("0","1","0");
                    pt3D3b.setFixed("0","0","1");
                    
                    pt3DSupport.setIs3D(true);
                    pt3DSupport01.setIs3D(true);
                    pt3DSupport02.setIs3D(true);
                    pt3DSupport03.setIs3D(true);
                   
                    t3D1.setIs3D(true);
                    t3D2.setIs3D(true);
                    t3D3.setIs3D(true);
                    t3D4.setIs3D(true);
                    
                   dirTortue3D1.setDefaults();
                   dirTortue3D1.setHidden(true);
                   
                   dirTortue3D2.setDefaults();
                   dirTortue3D2.setHidden(true);
                   
                   dirTortue3D3.setDefaults();
                   dirTortue3D3.setHidden(true);
                   
                   dirTortueVisu3D1.setDefaults();
                   dirTortueVisu3D1.setHidden(true);
                   
                   dirTortueVisu3D2.setDefaults();
                   dirTortueVisu3D2.setHidden(true);
                   
                   dirTortueVisu3D3.setDefaults();
                   dirTortueVisu3D3.setHidden(true);

                   pt3DTortue=(PointObject) getC().find(name);
                   
                   pt3DSupport.setSuperHidden(true);
                   pt3DSupport.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                   
                   pt3DSupport01.setSuperHidden(true);
                   pt3DSupport01.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D1.getName()+")");
                  
                   pt3DSupport02.setSuperHidden(true);
                   pt3DSupport02.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D2.getName()+")");
                   
                   pt3DSupport03.setSuperHidden(true);
                   pt3DSupport03.setFixed("x3D("+pt3DTortue.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DTortue.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DTortue.getName()+")+z3D("+dirTortue3D3.getName()+")");
                   
                   pt3DSupport.validate();
                   pt3DSupport01.validate();
                   pt3DSupport02.validate();
                   pt3DSupport03.validate();
                   dirTortueVisu3D1.validate();
                   dirTortueVisu3D2.validate();
                   dirTortueVisu3D3.validate();
                 
                   p3D.add(pt3DSupport);
                   
                   t3D1.setDefaults();
                   t3D1.setSuperHidden(true);
                   t3D1.setFixed("x3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D2.getName()+")","y3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D2.getName()+")","z3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")+0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D2.getName()+")");
                   p3D.add(t3D1);
                   
                   t3D2.setDefaults();
                   t3D2.setSuperHidden(true);
                   t3D2.setFixed("x3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")","y3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")","z3D("+pt3DSupport.getName()+")+0.8*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")");
                   p3D.add(t3D2);
                   
                   t3D3.setDefaults();
                   t3D3.setSuperHidden(true);
                   t3D3.setFixed("x3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*x3D("+dirTortueVisu3D2.getName()+")","y3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*y3D("+dirTortueVisu3D2.getName()+")","z3D("+pt3DSupport.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D1.getName()+")-0.4*"+longueurTortue3D.getName()+"*z3D("+dirTortueVisu3D2.getName()+")");
                   p3D.add(t3D3);
                   
                   t3D1.validate();
                   t3D2.validate();
                   t3D3.validate();
                   pp3D.validate();
                   pp3D.addConditional("solid",new Expression(pp3D.getName()+"<0",getC(),pp3D));
                   pp3D.addConditional("thick",new Expression(pp3D.getName()+"<0",getC(),pp3D));

                } catch (Exception e) {
                    return "";
                }
            return name;
            }
        
        else {
        try {  if (ptO==null) {
                    longueurTortue=new ExpressionObject(getC(), 0, 0);
                    longueurTortue.setDefaults();
                    longueurTortue.setHidden(true);
                    longueurTortue.setName("kTortue");
                    longueurTortue.setPrompt(longueurTortue.getName());
                    iTG=1; iTD=1; iPG=1; iPD=1; iPH=1; iPB=1; iOr=1; iVi=1;
                    styloBaisse = true;
                    ptO=new PointObject(getC(),0,0);
                    ptO.setName("turtleVptO");
                    ptO2=new PointObject(getC(),0,1);
                    ptO2.setName("turtleVptO2");
                    dirTortue= new VectorObject(getC(),ptO,ptO2);
                    dirTortue.setName("turtleVdirTortue");
                    ptSupport=new PointObject(getC(),0,0);
                    ptSupport.setName("turtleVptSupport");
                    ptSupport2=new PointObject(getC(),0,0);
                    ptSupport2.setName("turtleVptSupport2");
                    ptTortue2=new PointObject(getC(),0,0);
                    ptTortue2.setName("turtleVptTortue2");
                    dirTortueVisu= new VectorObject(getC(),ptSupport,ptSupport2);
                    dirTortueVisu.setName("turtleVdirTortueVisu");
                    p1= new Vector();
                    t1=new PointObject(getC(),0,0);
                    t1.setName("turtleVt1");
                    t2=new PointObject(getC(),0,0);
                    t2.setName("turtleVt2");
                    t3=new PointObject(getC(),0,0);
                    t3.setName("turtleVt3");
                    t4=new PointObject(getC(),0,0);
                    t4.setName("turtleVt4");
                    pp1 = new AreaObject(getC(),p1);
                    pp1.setName("turtleVpp1");
                    p2= new Vector();
                    t5=new PointObject(getC(),0,0);
                    t5.setName("turtleVt5");
                    t6=new PointObject(getC(),0,0);
                    t6.setName("turtleVt6");
                    pp2 = new AreaObject(getC(),p2);
                    pp2.setName("turtleVpp2");
                    
                    tete= new Vector();
                    t7=new PointObject(getC(),0,0);
                    t7.setName("turtleVt7");
                    t8=new PointObject(getC(),0,0);
                    t8.setName("turtleVt8");
                    t9=new PointObject(getC(),0,0);
                    t9.setName("turtleVt9");
                    t10=new PointObject(getC(),0,0);
                    t10.setName("turtleVt10");
                    t11=new PointObject(getC(),0,0);
                    t11.setName("turtleVt11");
                    ttete = new AreaObject(getC(),tete);
                    ttete.setName("turtleVttete");
                    p3= new Vector();
                    t12=new PointObject(getC(),0,0);
                    t12.setName("turtleVt12");
                    t13=new PointObject(getC(),0,0);
                    t13.setName("turtleVt13");
                    t14=new PointObject(getC(),0,0);
                    t14.setName("turtleVt14");
                    pp3 = new AreaObject(getC(),p3);
                    pp3.setName("turtleVpp3");
                    p4= new Vector();
                    t15=new PointObject(getC(),0,0);
                    t15.setName("turtleVt15");
                    t16=new PointObject(getC(),0,0);
                    t16.setName("turtleVt16");
                    t17=new PointObject(getC(),0,0);
                    t17.setName("turtleVt17");
                    pp4 = new AreaObject(getC(),p4);
                    pp4.setName("turtleVpp4");
                    cara= new Vector();
                    t18=new PointObject(getC(),0,0);
                    t18.setName("turtleVt18");
                    t19=new PointObject(getC(),0,0);
                    t19.setName("turtleVt19");
                    t20=new PointObject(getC(),0,0);
                    t20.setName("turtleVt20");
                    t21=new PointObject(getC(),0,0);
                    t21.setName("turtleVt21");
                    t22=new PointObject(getC(),0,0);
                    t22.setName("turtleVt22");
                    t23=new PointObject(getC(),0,0);
                    t23.setName("turtleVt23");
                    t24=new PointObject(getC(),0,0);
                    t24.setName("turtleVt24");
                    ccara = new AreaObject(getC(),cara);
                    ccara.setName("turtleVccara");
                    pp1.setDefaults();
                    pp1.setFilled(true);
                    pp1.setSpecialColor(new Color(138,102,66));
                    pp2.setDefaults();
                    pp2.setFilled(true);
                    pp2.setSpecialColor(new Color(138,102,66));
                    ttete.setDefaults();
                    ttete.setFilled(true);
                    ttete.setSpecialColor(new Color(138,102,66));
                    pp3.setDefaults();
                    pp3.setFilled(true);
                    pp3.setSpecialColor(new Color(138,102,66));
                    pp4.setDefaults();
                    pp4.setFilled(true);
                    pp4.setSpecialColor(new Color(138,102,66));
                    ccara.setDefaults();
                    ccara.setFilled(true);
                    ccara.setSpecialColor(new Color(82,139,139));
                }
              try {
                    longueurTortue.setExpression("60/pixel", getC());
                    } catch (final ConstructionException e) {
                        return "";
                    }
               addObject(longueurTortue);
               ptO.setHidden(true);
               addObject(ptO);
               ptO2.setSuperHidden(true);
               addObject(ptO2);
               dirTortue.validate();
               dirTortue.setDefaults();
               dirTortue.setHidden(true);
               addObject(dirTortue);
               ptTortue=(PointObject) getC().find(name);
               ptSupport.setSuperHidden(true);
               addObject(ptSupport);
               ptSupport.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
               ptSupport2.setSuperHidden(true);
               addObject(ptSupport2);
               ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
               ptTortue2.setDefaults();
               ptTortue2.setSuperHidden(true);
               addObject(ptTortue2);
               ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
               ptTortue2.validate();
               dirTortueVisu.validate();
               dirTortueVisu.setDefaults();
               dirTortueVisu.setHidden(true);
               addObject(dirTortueVisu);
               t1.setDefaults();
               t1.setSuperHidden(true);
               addObject(t1);
               t1.setFixed("x("+ptSupport.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t1);
               t2.setDefaults();
               t2.setSuperHidden(true);
               addObject(t2);
               t2.setFixed("x("+ptSupport.getName()+")-0.4*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.4*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t2);
               t3.setDefaults();
               t3.setSuperHidden(true);
               addObject(t3);
               t3.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p1.add(t3);
               addObject(pp1);
               t4.setDefaults();
               t4.setSuperHidden(true);
               addObject(t4);
               t4.setFixed("x("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t4);
               t5.setDefaults();
               t5.setSuperHidden(true);
               addObject(t5);
               t5.setFixed("x("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t5);
               t6.setDefaults();
               t6.setSuperHidden(true);
               addObject(t6);
               t6.setFixed("x("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p2.add(t6);
               addObject(pp2);
               t7.setDefaults();
               t7.setSuperHidden(true);
               addObject(t7);
               t7.setFixed("x("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.1*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.1*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t7);
               t8.setDefaults();
               t8.setSuperHidden(true);
               addObject(t8);
               t8.setFixed("x("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.15*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.15*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t8);
               t9.setDefaults();
               t9.setSuperHidden(true);
               addObject(t9);
               t9.setFixed("x("+ptSupport.getName()+")+1.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+1.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")");
               tete.add(t9);
               t10.setDefaults();
               t10.setSuperHidden(true);
               addObject(t10);
               t10.setFixed("x("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.15*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.9*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.15*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t10);
               t11.setDefaults();
               t11.setSuperHidden(true);
               addObject(t11);
               t11.setFixed("x("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.1*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.75*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.1*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               tete.add(t11);
               addObject(ttete);
               t12.setDefaults();
               t12.setSuperHidden(true);
               addObject(t12);
               t12.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t12);
               t13.setDefaults();
               t13.setSuperHidden(true);
               addObject(t13);
               t13.setFixed("x("+ptSupport.getName()+")+0.4*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")-0.4*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t13);
               t14.setDefaults();
               t14.setSuperHidden(true);
               addObject(t14);
               t14.setFixed("x("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p3.add(t14);
               addObject(pp3);
               t15.setDefaults();
               t15.setSuperHidden(true);
               addObject(t15);
               t15.setFixed("x("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t15);
               t16.setDefaults();
               t16.setSuperHidden(true);
               addObject(t16);
               t16.setFixed("x("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.5*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.7*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.5*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t16);
               t17.setDefaults();
               t17.setSuperHidden(true);
               addObject(t17);
               t17.setFixed("x("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.2*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.65*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.2*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               p4.add(t17);
               addObject(pp4);
               t18.setDefaults();
               t18.setSuperHidden(true);
               addObject(t18);
               t18.setFixed("x("+ptSupport.getName()+")","y("+ptSupport.getName()+")");
               cara.add(t18);
               t19.setDefaults();
               t19.setSuperHidden(true);
               addObject(t19);
               t19.setFixed("x("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t19);
               t20.setDefaults();
               t20.setSuperHidden(true);
               addObject(t20);
               t20.setFixed("x("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t20);
               t21.setDefaults();
               t21.setSuperHidden(true);
               addObject(t21);
               t21.setFixed("x("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")+0.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")-0.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t21);
               t22.setDefaults();
               t22.setSuperHidden(true);
               addObject(t22);
               t22.setFixed("x("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.05*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.8*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.05*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t22);
               t23.setDefaults();
               t23.setSuperHidden(true);
               addObject(t23);
               t23.setFixed("x("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.6*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t23);
               t24.setDefaults();
               t24.setSuperHidden(true);
               addObject(t24);
               t24.setFixed("x("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")-0.35*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")","y("+ptSupport.getName()+")+0.3*"+longueurTortue.getName()+"*y("+dirTortueVisu.getName()+")+0.35*"+longueurTortue.getName()+"*x("+dirTortueVisu.getName()+")");
               cara.add(t24);
               addObject(ccara);

            } catch (Exception e) {
                return "";
            }
        return name;
        
                }
            }
         }
        else {
            return("");
        }
    }
    
    public static boolean BaisserStylo() {  
        styloBaisse = true;
        return true;
    }
    
    public static boolean TurtleDown() {  
        styloBaisse = true;
        return true;
    }
    
    public static boolean LeverStylo() {  
        styloBaisse = false;
        return false;
    }
    
    public static boolean TurtleUp() {  
        styloBaisse = false;
        return false;
    }
    
    public static String[] Avancer(String ptName, String segtName, String di) throws Exception {
          PointObject pt=null;
         SegmentObject so=null;
         String[] ptSegmt=new String[2];
         synchronized (getC()) {
         if (segtName.equals("undefined")) {
                        di=ptName;
                        segtName="";
                        ptName="";
                    } else if (di.equals("undefined")) {
                        di=segtName;    
                        segtName="";
                    }  
         if (turtleIs3D) {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setIs3D(true);
                        pt.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        pt3DSupport.setFixed("x3D("+pt.getName()+")","y3D("+pt.getName()+")","z3D("+pt.getName()+")");
                        pt3DSupport.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),pt3DTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                        pt.validCoordinates();
                        pt3DTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
             
         }
         else {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setDefaults();
                        pt.setType(3);
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        pt.validate();
                        addObject(pt);
                        ptSupport.setFixed("x("+pt.getName()+")","y("+pt.getName()+")");
                        ptSupport.validCoordinates();
                        ptSupport2.setFixed("x("+pt.getName()+")+x("+dirTortue.getName()+")","y("+pt.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates(); 
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),ptTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x("+ptTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x("+ptTortue.getName()+")+("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                        pt.validCoordinates();
                        ptTortue=pt;       
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
            }
        }
    }
    
    public static String[] MoveForward(String ptName, String segtName, String di) throws Exception {
         PointObject pt=null;
         SegmentObject so=null;
         String[] ptSegmt=new String[2];
         synchronized (getC()) {
         if (segtName.equals("undefined")) {
                        di=ptName;
                        segtName="";
                        ptName="";
                    } else if (di.equals("undefined")) {
                        di=segtName;    
                        segtName="";
                    }  
         if (turtleIs3D) {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setIs3D(true);
                        pt.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        pt3DSupport.setFixed("x3D("+pt.getName()+")","y3D("+pt.getName()+")","z3D("+pt.getName()+")");
                        pt3DSupport.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),pt3DTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")+("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                        pt.validCoordinates();
                        pt3DTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
             
         }
         else {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setDefaults();
                        pt.setType(3);
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        pt.validate();
                        addObject(pt);
                        ptSupport.setFixed("x("+pt.getName()+")","y("+pt.getName()+")");
                        ptSupport.validCoordinates();
                        ptSupport2.setFixed("x("+pt.getName()+")+x("+dirTortue.getName()+")","y("+pt.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates(); 
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),ptTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x("+ptTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+"+(l/nbSteps)+"*("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x("+ptTortue.getName()+")+("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                        pt.validCoordinates();
                        ptTortue=pt;       
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
            }
        }
    }
    
    public static String[] Reculer(String ptName, String segtName, String di) throws Exception {
        PointObject pt=null;
         SegmentObject so=null;
         String[] ptSegmt=new String[2];
         synchronized (getC()) {
             if (segtName.equals("undefined")) {
                        di=ptName;
                        segtName=""; ptName="";
                    } else if (di.equals("undefined")) {
                        di=segtName;    
                        segtName="";
                    }      
         if (turtleIs3D) {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setIs3D(true);
                        pt.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        pt3DSupport.setFixed("x3D("+pt.getName()+")","y3D("+pt.getName()+")","z3D("+pt.getName()+")");
                        pt3DSupport.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),pt3DTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                        pt3DTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
             
         }
         else {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        ptSupport.setFixed("x("+pt.getName()+")","y("+pt.getName()+")");
                        ptSupport.validCoordinates();
                        ptSupport2.setFixed("x("+pt.getName()+")+x("+dirTortue.getName()+")","y("+pt.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),ptTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x("+ptTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x("+ptTortue.getName()+")-("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")-("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                        ptTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
            }
        }
    }
    
    public static String[] MoveBackward(String ptName, String segtName, String di) throws Exception {
          PointObject pt=null;
         SegmentObject so=null;
         String[] ptSegmt=new String[2];
         synchronized (getC()) {
             if (segtName.equals("undefined")) {
                        di=ptName;
                        segtName=""; ptName="";
                    } else if (di.equals("undefined")) {
                        di=segtName;    
                        segtName="";
                    }      
         if (turtleIs3D) {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setIs3D(true);
                        pt.setFixed("x3D("+pt3DTortue.getName()+")","y3D("+pt3DTortue.getName()+")","z3D("+pt3DTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        pt3DSupport.setFixed("x3D("+pt.getName()+")","y3D("+pt.getName()+")","z3D("+pt.getName()+")");
                        pt3DSupport.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),pt3DTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*x3D("+dirTortue3D1.getName()+")","y3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*y3D("+dirTortue3D1.getName()+")","z3D("+pt3DTortue.getName()+")-("+parseVariables(di)+")*z3D("+dirTortue3D1.getName()+")");
                        pt3DTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
             
         }
         else {
                    try {
                        pt=new PointObject(getC(), 0, 0);
                        pt.setFixed("x("+ptTortue.getName()+")","y("+ptTortue.getName()+")");
                        pt.validCoordinates();
                        pt.setDefaults();
                        pt.setShowValue(false);
                        pt.setShowName(false);
                        pt.setType(3);
                        if (!ptName.equals("")) {
                            pt.setName(parseVariables(ptName));
                            }
                        addObject(pt);
                        ptSupport.setFixed("x("+pt.getName()+")","y("+pt.getName()+")");
                        ptSupport.validCoordinates();
                        ptSupport2.setFixed("x("+pt.getName()+")+x("+dirTortue.getName()+")","y("+pt.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        if (styloBaisse) {
                            so=new SegmentObject(getC(),ptTortue,pt);
                            so.validate();
                            so.setDefaults();
                            if (!segtName.equals("")) {
                            so.setName(parseVariables(segtName));
                            }
                            addObject(so);
                        }
                        else {
                            pt.setHidden(true);
                        }
                        for (int l=1;l<nbSteps;l++) { 
                            pt.setFixed("x("+ptTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")-"+(l/nbSteps)+"*("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                            refreshZC();
                            }
                        pt.setFixed("x("+ptTortue.getName()+")-("+parseVariables(di)+")*x("+dirTortue.getName()+")","y("+ptTortue.getName()+")-("+parseVariables(di)+")*y("+dirTortue.getName()+")");
                        ptTortue=pt;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
                    ptSegmt[0]=pt.getName();
                    if (so==null) {
                        ptSegmt[1]="";
                    }
                    else {ptSegmt[1]=so.getName();}
                    return ptSegmt;
            }
        }
    }
    
     public static void TournerGauche(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleTGpt3D1b"+iTG3D);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        addObject(pt3D1b);
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleTGpt3D2b"+iTG3D);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        addObject(pt3D2b);
                        pt3D1b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtleTGdirT3D1"+iTG3D);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtleTGdirT3D2"+iTG3D);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("-x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("-x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iTG3D++;
         }
        else {
            synchronized (getC()) {  
                    try {
                        ptO3=new PointObject(getC(),0,0);
                        ptO3.setName("turtleTGptO3"+iTG);
                        ptO3.setHidden(true);
                        ptO3.setShowValue(false);
                        ptO3.setShowName(false);
                        addObject(ptO3);
                        ptO3.setFixed("x("+ptO2.getName()+")","x("+ptO2.getName()+")");
                        ptO3.validCoordinates();
                        dirTortue= new VectorObject(getC(),ptO,ptO3);
                        dirTortue.setName("turtleTGdirT"+iTG);
                        dirTortue.validate();
                        dirTortue.setDefaults();
                        dirTortue.setHidden(true);
                        addObject(dirTortue);
                        ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            ptO3.setFixed("x("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","x("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        ptO3.setFixed("x("+ptO2.getName()+")*cos("+parseVariables(ang)+")-y("+ptO2.getName()+")*sin("+parseVariables(ang)+")","x("+ptO2.getName()+")*sin("+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+parseVariables(ang)+")");
                        ptO2=ptO3;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
            iTG++;
        }
        
    }
     
     public static void TurnLeft(String ang) throws Exception {
       if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleTGpt3D1b"+iTG3D);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        addObject(pt3D1b);
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleTGpt3D2b"+iTG3D);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        addObject(pt3D2b);
                        pt3D1b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtleTGdirT3D1"+iTG3D);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtleTGdirT3D2"+iTG3D);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("-x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("-x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iTG3D++;
         }
        else {
            synchronized (getC()) {  
                    try {
                        ptO3=new PointObject(getC(),0,0);
                        ptO3.setName("turtleTGptO3"+iTG);
                        ptO3.setHidden(true);
                        ptO3.setShowValue(false);
                        ptO3.setShowName(false);
                        addObject(ptO3);
                        ptO3.setFixed("x("+ptO2.getName()+")","x("+ptO2.getName()+")");
                        ptO3.validCoordinates();
                        dirTortue= new VectorObject(getC(),ptO,ptO3);
                        dirTortue.setName("turtleTGdirT"+iTG);
                        dirTortue.validate();
                        dirTortue.setDefaults();
                        dirTortue.setHidden(true);
                        addObject(dirTortue);
                        ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            ptO3.setFixed("x("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","x("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        ptO3.setFixed("x("+ptO2.getName()+")*cos("+parseVariables(ang)+")-y("+ptO2.getName()+")*sin("+parseVariables(ang)+")","x("+ptO2.getName()+")*sin("+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+parseVariables(ang)+")");
                        ptO2=ptO3;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
            iTG++;
        }
        
    }
     
     public static void TournerDroite(String ang) throws Exception {
         if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleTDpt3D1b"+iTD3D);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleTDpt3D2b"+iTD3D);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtleTDdirT3D1"+iTD3D);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtleTDdirT3D2"+iTD3D);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iTD3D++;
         }
        else {
            synchronized (getC()) {  
                    try {
                        ptO3=new PointObject(getC(),0,0);
                        ptO3.setName("turtleTDptO3"+iTD);
                        ptO3.setHidden(true);
                        addObject(ptO3);
                        ptO3.setFixed("x("+ptO2.getName()+")","y("+ptO2.getName()+")");
                        ptO3.validCoordinates();
                        dirTortue= new VectorObject(getC(),ptO,ptO3);
                        dirTortue.setName("turtleTDdirT"+iTD);
                        dirTortue.validate();
                        dirTortue.setDefaults();
                        dirTortue.setHidden(true);
                        addObject(dirTortue);
                        ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            ptO3.setFixed("x("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","-x("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        ptO3.setFixed("x("+ptO2.getName()+")*cos("+parseVariables(ang)+")+y("+ptO2.getName()+")*sin("+parseVariables(ang)+")","-x("+ptO2.getName()+")*sin("+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+parseVariables(ang)+")");
                        ptO2=ptO3;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
            iTD++;
         }
    }
     
     public static void TurnRight(String ang) throws Exception {
         if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtleTDpt3D1b"+iTD3D);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtleTDpt3D2b"+iTD3D);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtleTDdirT3D1"+iTD3D);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtleTDdirT3D2"+iTD3D);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iTD3D++;
         }
        else {
            synchronized (getC()) {  
                    try {
                        ptO3=new PointObject(getC(),0,0);
                        ptO3.setName("turtleTDptO3"+iTD);
                        ptO3.setHidden(true);
                        addObject(ptO3);
                        ptO3.setFixed("x("+ptO2.getName()+")","y("+ptO2.getName()+")");
                        ptO3.validCoordinates();
                        dirTortue= new VectorObject(getC(),ptO,ptO3);
                        dirTortue.setName("turtleTDdirT"+iTD);
                        dirTortue.validate();
                        dirTortue.setDefaults();
                        dirTortue.setHidden(true);
                        addObject(dirTortue);
                        ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
                        ptSupport2.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            ptO3.setFixed("x("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","-x("+ptO2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        ptO3.setFixed("x("+ptO2.getName()+")*cos("+parseVariables(ang)+")+y("+ptO2.getName()+")*sin("+parseVariables(ang)+")","-x("+ptO2.getName()+")*sin("+parseVariables(ang)+")+y("+ptO2.getName()+")*cos("+parseVariables(ang)+")");
                        ptO2=ptO3;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
            iTD++;
         }
    }
     
      public static void PivoterGauche(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePGpt3D3b"+iPG);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtlePGpt3D2b"+iPG);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D3b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePGdirT3D3"+iPG);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtlePGdirT3D2"+iPG);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("-x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("-x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D3=pt3D3b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iPG++;
         }
        else {
            return;
        }
    }
      
      public static void RollLeft(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePGpt3D3b"+iPG);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtlePGpt3D2b"+iPG);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D3b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePGdirT3D3"+iPG);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtlePGdirT3D2"+iPG);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        for (int l=1;l<nbSteps;l++) { 
                            pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("-x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("-x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D3=pt3D3b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iPG++;
         }
        else {
            return;
        }
    }
      
      public static void PivoterDroite(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePDpt3D3b"+iPD);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtlePDpt3D2b"+iPD);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D3b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePDdirT3D3"+iPD);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtlePDdirT3D2"+iPD);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {    
                            pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D3=pt3D3b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
             iPD++;
         }
        else {
            return;
        }
    }
      
      public static void RollRight(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePDpt3D3b"+iPD);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D2b=new PointObject(getC(),0,0);
                        pt3D2b.setName("turtlePDpt3D2b"+iPD);
                        pt3D2b.setIs3D(true);
                        pt3D2b.setHidden(true);
                        pt3D2b.setShowValue(false);
                        pt3D2b.setShowName(false);
                        addObject(pt3D2b);
                        pt3D2b.setFixed("x3D("+pt3D2.getName()+")","y3D("+pt3D2.getName()+")","z3D("+pt3D2.getName()+")");
                        pt3D3b.validCoordinates();
                        pt3D2b.validCoordinates();
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePDdirT3D3"+iPD);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2b);
                        dirTortue3D2.setName("turtlePDdirT3D2"+iPD);
                        dirTortue3D2.validate();
                        dirTortue3D2.setDefaults();
                        dirTortue3D2.setHidden(true);
                        addObject(dirTortue3D2);
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {    
                            pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D2b.setFixed("x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D2.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D2b.setFixed("x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D2.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D3=pt3D3b;
                        pt3D2=pt3D2b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
           iPD++;
         }
        else {
            return;
        }
    }
      
      public static void PivoterHaut(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtlePHpt3D1b"+iPH);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePHpt3D3b"+iPH);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D3b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtlePHdirT3D1"+iPH);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePHdirT3D3"+iPH);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {    
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D3b.setFixed("-x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D3b.setFixed("-x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D3=pt3D3b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
          iPH++;
         }
        else {
            return;
        }
    }
      
      public static void PitchUp(String ang) throws Exception {
         if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtlePHpt3D1b"+iPH);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePHpt3D3b"+iPH);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D3b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtlePHdirT3D1"+iPH);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePHdirT3D3"+iPH);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {    
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D3b.setFixed("-x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D3b.setFixed("-x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","-y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","-z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D3=pt3D3b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
            iPH++;
         }
        else {
            return;
        }
    }
      
      public static void PivoterBas(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtlePBpt3D1b"+iPB);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePBpt3D3b"+iPB);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D3b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtlePBdirT3D1"+iPB);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePBdirT3D3"+iPB);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {     
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D3b.setFixed("x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D3b.setFixed("x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D3=pt3D3b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
         iPB++;
         }
        else {
            return;
        }
    }
      
      public static void PitchDown(String ang) throws Exception {
        if (turtleIs3D) {
             synchronized (getC()) {  
                    try {
                        pt3D1b=new PointObject(getC(),0,0);
                        pt3D1b.setName("turtlePBpt3D1b"+iPB);
                        pt3D1b.setIs3D(true);
                        pt3D1b.setHidden(true);
                        pt3D1b.setShowValue(false);
                        pt3D1b.setShowName(false);
                        addObject(pt3D1b);
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")","y3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")");
                        pt3D3b=new PointObject(getC(),0,0);
                        pt3D3b.setName("turtlePBpt3D3b"+iPB);
                        pt3D3b.setIs3D(true);
                        pt3D3b.setHidden(true);
                        pt3D3b.setShowValue(false);
                        pt3D3b.setShowName(false);
                        addObject(pt3D3b);
                        pt3D3b.setFixed("x3D("+pt3D3.getName()+")","y3D("+pt3D3.getName()+")","z3D("+pt3D3.getName()+")");
                        pt3D1b.validCoordinates();
                        pt3D3b.validCoordinates();
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1b);
                        dirTortue3D1.setName("turtlePBdirT3D1"+iPB);
                        dirTortue3D1.validate();
                        dirTortue3D1.setDefaults();
                        dirTortue3D1.setHidden(true);
                        addObject(dirTortue3D1);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3b);
                        dirTortue3D3.setName("turtlePBdirT3D3"+iPB);
                        dirTortue3D3.validate();
                        dirTortue3D3.setDefaults();
                        dirTortue3D3.setHidden(true);
                        addObject(dirTortue3D3);
                        pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");
                        pt3DSupport01.validCoordinates();
                        pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");
                        pt3DSupport02.validCoordinates();
                        pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");
                        pt3DSupport03.validCoordinates();
                        for (int l=1;l<nbSteps;l++) {     
                            pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-x3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-y3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")-z3D("+pt3D3.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            pt3D3b.setFixed("x3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+(l/nbSteps)+"*"+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+(l/nbSteps)+"*"+parseVariables(ang)+")");
                            refreshZC();
                            }
                        pt3D1b.setFixed("x3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-x3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-y3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*cos("+parseVariables(ang)+")-z3D("+pt3D3.getName()+")*sin("+parseVariables(ang)+")");
                        pt3D3b.setFixed("x3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+x3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","y3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+y3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")","z3D("+pt3D1.getName()+")*sin("+parseVariables(ang)+")+z3D("+pt3D3.getName()+")*cos("+parseVariables(ang)+")");
                        pt3D1=pt3D1b;
                        pt3D3=pt3D3b;
                    } catch (Exception ex) {
                        throw new Exception(Loc("pointcoords"));
                    }
            }
          iPB++;
         }
        else {
            return;
        }
    }
     
     public static void MontrerTortue(){
         if (turtleIs3D) {
             pp3D.setHidden(false);
         }
         else {
            pp1.setHidden(false);
            pp2.setHidden(false);
            pp3.setHidden(false);
            pp4.setHidden(false);
            ttete.setHidden(false);
            ccara.setHidden(false);
         }
    }
     
     public static void ShowTurtle(){
         if (turtleIs3D) {
             pp3D.setHidden(false);
         }
         else {
            pp1.setHidden(false);
            pp2.setHidden(false);
            pp3.setHidden(false);
            pp4.setHidden(false);
            ttete.setHidden(false);
            ccara.setHidden(false);
         }
    }
     
     public static void CacherTortue(){
         if (turtleIs3D) {
             pp3D.setHidden(true);
         }
         else {
            pp1.setHidden(true);
            pp2.setHidden(true);
            pp3.setHidden(true);
            pp4.setHidden(true);
            ttete.setHidden(true);
            ccara.setHidden(true);
         }
    }
     
     public static void HideTurtle(){
         if (turtleIs3D) {
             pp3D.setHidden(true);
         }
         else {
            pp1.setHidden(true);
            pp2.setHidden(true);
            pp3.setHidden(true);
            pp4.setHidden(true);
            ttete.setHidden(true);
            ccara.setHidden(true);
         }
    }
     
     public static void OrienterTortue(String ang){
         if (turtleIs3D) {
             synchronized (getC()) {
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleOript3D1"+iOr3D);
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleOript3D2"+iOr3D);
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleOript3D3"+iOr3D);
                        addObject(pt3D1);
                        addObject(pt3D2);
                        addObject(pt3D3);
                        pt3D1.setIs3D(true);
                        pt3D2.setIs3D(true);
                        pt3D3.setIs3D(true);
                        
                        pt3D1.setSuperHidden(true);
                        pt3D2.setSuperHidden(true);
                        pt3D3.setSuperHidden(true);
                                
                        pt3D1.setFixed("cos("+parseVariables(ang)+")","sin("+parseVariables(ang)+")","0");
                        pt3D2.setFixed("-sin("+parseVariables(ang)+")","cos("+parseVariables(ang)+")","0");
                        pt3D3.setFixed("0","0","1");
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleOridirT1"+iOr3D);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleOridirT2"+iOr3D);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleOridirT3"+iOr3D);
                        addObject(dirTortue3D1);
                        addObject(dirTortue3D2);
                        addObject(dirTortue3D3);
                        
                        dirTortue3D1.setDefaults();
                        dirTortue3D2.setDefaults();
                        dirTortue3D3.setDefaults();
                        
                       dirTortue3D1.setHidden(true);
                       dirTortue3D2.setHidden(true);
                       dirTortue3D3.setHidden(true);

                       pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");

                       pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");

                       pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");

                       pt3DSupport.validate();
                       pt3DSupport01.validate();
                       pt3DSupport02.validate();
                       pt3DSupport03.validate();
                       dirTortueVisu3D1.validate();
                       dirTortueVisu3D2.validate();
                       dirTortueVisu3D3.validate();
                        
             }
             iOr3D++;
         }
         else {
            synchronized (getC()) {
              ptO2=new PointObject(getC(),0,1);
              ptO2.setName("turtleOriptO2"+iOr);
              addObject(ptO2);
              ptO2.setSuperHidden(true);
              ptO2.setFixed("cos("+parseVariables(ang)+")","sin("+parseVariables(ang)+")");
              dirTortue= new VectorObject(getC(),ptO,ptO2);
              dirTortue.setName("turtleOridirT"+iOr);
              dirTortue.validate();
              dirTortue.setDefaults();
              dirTortue.setHidden(true);
              addObject(dirTortue);
              ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.validate();
              dirTortueVisu.validate();

            }
            iOr++;
         } 
    }
     
     public static void OrientateTurtle(String ang){
          if (turtleIs3D) {
             synchronized (getC()) {
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleOript3D1"+iOr3D);
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleOript3D2"+iOr3D);
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleOript3D3"+iOr3D);
                        addObject(pt3D1);
                        addObject(pt3D2);
                        addObject(pt3D3);
                        pt3D1.setIs3D(true);
                        pt3D2.setIs3D(true);
                        pt3D3.setIs3D(true);
                        
                        pt3D1.setSuperHidden(true);
                        pt3D2.setSuperHidden(true);
                        pt3D3.setSuperHidden(true);
                                
                        pt3D1.setFixed("cos("+parseVariables(ang)+")","sin("+parseVariables(ang)+")","0");
                        pt3D2.setFixed("-sin("+parseVariables(ang)+")","cos("+parseVariables(ang)+")","0");
                        pt3D3.setFixed("0","0","1");
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleOridirT1"+iOr3D);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleOridirT2"+iOr3D);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleOridirT3"+iOr3D);
                        addObject(dirTortue3D1);
                        addObject(dirTortue3D2);
                        addObject(dirTortue3D3);
                        
                        dirTortue3D1.setDefaults();
                        dirTortue3D2.setDefaults();
                        dirTortue3D3.setDefaults();
                        
                       dirTortue3D1.setHidden(true);
                       dirTortue3D2.setHidden(true);
                       dirTortue3D3.setHidden(true);

                       pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");

                       pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");

                       pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");

                       pt3DSupport.validate();
                       pt3DSupport01.validate();
                       pt3DSupport02.validate();
                       pt3DSupport03.validate();
                       dirTortueVisu3D1.validate();
                       dirTortueVisu3D2.validate();
                       dirTortueVisu3D3.validate();
                        
             }
             iOr3D++;
         }
         else {
            synchronized (getC()) {
              ptO2=new PointObject(getC(),0,1);
              ptO2.setName("turtleOriptO2"+iOr);
              addObject(ptO2);
              ptO2.setSuperHidden(true);
              ptO2.setFixed("cos("+parseVariables(ang)+")","sin("+parseVariables(ang)+")");
              dirTortue= new VectorObject(getC(),ptO,ptO2);
              dirTortue.setName("turtleOridirT"+iOr);
              dirTortue.validate();
              dirTortue.setDefaults();
              dirTortue.setHidden(true);
              addObject(dirTortue);
              ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.validate();
              dirTortueVisu.validate();

            }
            iOr++;
         } 
    }
     
     public static void Viser(String pointName) throws Exception{
         ConstructionObject o=getC().find(parseVariables(pointName));
            if (o==null) {
                throw new Exception(Loc("notfound"));
             }
            if (!(o instanceof PointObject)) {
              throw new Exception(Loc("notgoodtype"));
            }
           if (turtleIs3D) {
             synchronized (getC()) {
                        oldpt3D2=pt3D2;
                        oldpt3D3=pt3D3;
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleVipt3D1"+iVi3D);
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleVipt3D2"+iVi3D);
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleVipt3D3"+iVi3D);
                        addObject(pt3D1);
                        addObject(pt3D2);
                        addObject(pt3D3);
                        pt3D1.setIs3D(true);
                        pt3D2.setIs3D(true);
                        pt3D3.setIs3D(true);
                        
                        pt3D1.setSuperHidden(true);
                        pt3D2.setSuperHidden(true);
                        pt3D3.setSuperHidden(true);
                                
                        pt3D1.setFixed("(x3D("+pointName+")-x3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")","(y3D("+pointName+")-y3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")","(z3D("+pointName+")-z3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")");
                        pt3D2.setFixed("y3D("+oldpt3D3.getName()+")*z3D("+pt3D1.getName()+")-y3D("+pt3D1.getName()+")*z3D("+oldpt3D3.getName()+")","z3D("+oldpt3D3.getName()+")*x3D("+pt3D1.getName()+")-z3D("+pt3D1.getName()+")*x3D("+oldpt3D3.getName()+")","x3D("+oldpt3D3.getName()+")*y3D("+pt3D1.getName()+")-x3D("+pt3D1.getName()+")*y3D("+oldpt3D3.getName()+")");
                        pt3D3.setFixed("y3D("+pt3D1.getName()+")*z3D("+oldpt3D2.getName()+")-y3D("+oldpt3D2.getName()+")*z3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")*x3D("+oldpt3D2.getName()+")-z3D("+oldpt3D2.getName()+")*x3D("+pt3D1.getName()+")","x3D("+pt3D1.getName()+")*y3D("+oldpt3D2.getName()+")-x3D("+oldpt3D2.getName()+")*y3D("+pt3D1.getName()+")");
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleVidirT3D1"+iVi3D);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleVidirT3D2"+iVi3D);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleVidirT3D3"+iVi3D);
                        
                        addObject(dirTortue3D1);
                        addObject(dirTortue3D2);
                        addObject(dirTortue3D3);
                        
                        dirTortue3D1.setDefaults();
                        dirTortue3D2.setDefaults();
                        dirTortue3D3.setDefaults();
                        
                       dirTortue3D1.setHidden(true);
                       dirTortue3D2.setHidden(true);
                       dirTortue3D3.setHidden(true);

                       pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");

                       pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");

                       pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");

                       pt3DSupport.validate();
                       pt3DSupport01.validate();
                       pt3DSupport02.validate();
                       pt3DSupport03.validate();
                       dirTortueVisu3D1.validate();
                       dirTortueVisu3D2.validate();
                       dirTortueVisu3D3.validate();
                        
             }
             iVi3D++;
         }
         else {
            synchronized (getC()) {
              ptO2=new PointObject(getC(),0,1);
              ptO2.setName("turtleViptO2"+iVi);
              addObject(ptO2);
              ptO2.setSuperHidden(true);
              ptO2.setFixed("(x("+pointName+")-x("+ptTortue.getName()+"))/d("+pointName+","+ptTortue.getName()+")","(y("+pointName+")-y("+ptTortue.getName()+"))/d("+pointName+","+ptTortue.getName()+")");
              dirTortue= new VectorObject(getC(),ptO,ptO2);
              dirTortue.setName("turtleVidirT"+iVi);
              dirTortue.validate();
              dirTortue.setDefaults();
              dirTortue.setHidden(true);
              addObject(dirTortue);
              ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.validate();
              dirTortueVisu.validate();

            }
            iVi++;
         }   
    }
     
     public static void OrientateTowards(String pointName) throws Exception{
          ConstructionObject o=getC().find(parseVariables(pointName));
            if (o==null) {
                throw new Exception(Loc("notfound"));
             }
            if (!(o instanceof PointObject)) {
              throw new Exception(Loc("notgoodtype"));
            }
           if (turtleIs3D) {
             synchronized (getC()) {
                        oldpt3D2=pt3D2;
                        oldpt3D3=pt3D3;
                        pt3D1=new PointObject(getC(),0,0);
                        pt3D1.setName("turtleVipt3D1"+iVi3D);
                        pt3D2=new PointObject(getC(),0,0);
                        pt3D2.setName("turtleVipt3D2"+iVi3D);
                        pt3D3=new PointObject(getC(),0,0);
                        pt3D3.setName("turtleVipt3D3"+iVi3D);
                        addObject(pt3D1);
                        addObject(pt3D2);
                        addObject(pt3D3);
                        pt3D1.setIs3D(true);
                        pt3D2.setIs3D(true);
                        pt3D3.setIs3D(true);
                        
                        pt3D1.setSuperHidden(true);
                        pt3D2.setSuperHidden(true);
                        pt3D3.setSuperHidden(true);
                                
                        pt3D1.setFixed("(x3D("+pointName+")-x3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")","(y3D("+pointName+")-y3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")","(z3D("+pointName+")-z3D("+pt3DTortue.getName()+"))/d3D("+pointName+","+pt3DTortue.getName()+")");
                        pt3D2.setFixed("y3D("+oldpt3D3.getName()+")*z3D("+pt3D1.getName()+")-y3D("+pt3D1.getName()+")*z3D("+oldpt3D3.getName()+")","z3D("+oldpt3D3.getName()+")*x3D("+pt3D1.getName()+")-z3D("+pt3D1.getName()+")*x3D("+oldpt3D3.getName()+")","x3D("+oldpt3D3.getName()+")*y3D("+pt3D1.getName()+")-x3D("+pt3D1.getName()+")*y3D("+oldpt3D3.getName()+")");
                        pt3D3.setFixed("y3D("+pt3D1.getName()+")*z3D("+oldpt3D2.getName()+")-y3D("+oldpt3D2.getName()+")*z3D("+pt3D1.getName()+")","z3D("+pt3D1.getName()+")*x3D("+oldpt3D2.getName()+")-z3D("+oldpt3D2.getName()+")*x3D("+pt3D1.getName()+")","x3D("+pt3D1.getName()+")*y3D("+oldpt3D2.getName()+")-x3D("+oldpt3D2.getName()+")*y3D("+pt3D1.getName()+")");
                        dirTortue3D1= new VectorObject(getC(),pt3D0,pt3D1);
                        dirTortue3D1.setName("turtleVidirT3D1"+iVi3D);
                        dirTortue3D2= new VectorObject(getC(),pt3D0,pt3D2);
                        dirTortue3D2.setName("turtleVidirT3D2"+iVi3D);
                        dirTortue3D3= new VectorObject(getC(),pt3D0,pt3D3);
                        dirTortue3D3.setName("turtleVidirT3D3"+iVi3D);
                        
                        addObject(dirTortue3D1);
                        addObject(dirTortue3D2);
                        addObject(dirTortue3D3);
                        
                        dirTortue3D1.setDefaults();
                        dirTortue3D2.setDefaults();
                        dirTortue3D3.setDefaults();
                        
                       dirTortue3D1.setHidden(true);
                       dirTortue3D2.setHidden(true);
                       dirTortue3D3.setHidden(true);

                       pt3DSupport01.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D1.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D1.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D1.getName()+")");

                       pt3DSupport02.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D2.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D2.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D2.getName()+")");

                       pt3DSupport03.setFixed("x3D("+pt3DSupport.getName()+")+x3D("+dirTortue3D3.getName()+")","y3D("+pt3DSupport.getName()+")+y3D("+dirTortue3D3.getName()+")","z3D("+pt3DSupport.getName()+")+z3D("+dirTortue3D3.getName()+")");

                       pt3DSupport.validate();
                       pt3DSupport01.validate();
                       pt3DSupport02.validate();
                       pt3DSupport03.validate();
                       dirTortueVisu3D1.validate();
                       dirTortueVisu3D2.validate();
                       dirTortueVisu3D3.validate();
                        
             }
             iVi3D++;
         }
         else {
            synchronized (getC()) {
              ptO2=new PointObject(getC(),0,1);
              ptO2.setName("turtleViptO2"+iVi);
              addObject(ptO2);
              ptO2.setSuperHidden(true);
              ptO2.setFixed("(x("+pointName+")-x("+ptTortue.getName()+"))/d("+pointName+","+ptTortue.getName()+")","(y("+pointName+")-y("+ptTortue.getName()+"))/d("+pointName+","+ptTortue.getName()+")");
              dirTortue= new VectorObject(getC(),ptO,ptO2);
              dirTortue.setName("turtleVidirT"+iVi);
              dirTortue.validate();
              dirTortue.setDefaults();
              dirTortue.setHidden(true);
              addObject(dirTortue);
              ptSupport2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.setFixed("x("+ptTortue.getName()+")+x("+dirTortue.getName()+")","y("+ptTortue.getName()+")+y("+dirTortue.getName()+")");
              ptTortue2.validate();
              dirTortueVisu.validate();

            }
            iVi++;
         }   
    }
     
     public static void VitesseTortue(double vitesse){
     synchronized (getC()) {
        if (vitesse!=0) {
              vitesseTortue=Math.abs(vitesse);
        } else {
            vitesseTortue=0.01;
        }
        nbSteps=  Math.floor(99/vitesse)+1;
     }
    }
     
     public static void TurtleSpeed(double vitesse){
        synchronized (getC()) {
        if (vitesse!=0) {
              vitesseTortue=Math.abs(vitesse);
        } else {
            vitesseTortue=0.01;
        }
        nbSteps= Math.floor(99/vitesse)+1;
         }
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
    
    public static void rafraichirZC() {
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

    public static JSOuputConsole getCONSOLE() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getCONSOLE();
    }
    
    public static JSOuputConsole prendreCONSOLE() {
        ScriptThread th=(ScriptThread) Thread.currentThread();
        return th.getCONSOLE();
    }
    
    public static boolean SetIconSelection(String iconname, boolean sel) {
        return PaletteManager.setSelected_with_clic2(iconname, sel);
    }
    
    
    public static boolean IsIconSelected(String iconname) {
        return PaletteManager.isSelected(iconname);
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
     * @param fic
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
        String origin;
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
    
    synchronized static public void Normalize(int i) {
        ConstructionObject o=getC().lastButN(i);
        if (o!=null) {
            o.setColorType(ConstructionObject.NORMAL);
            o.setShowName(false);
            o.setShowValue(false);
            o.setFilled(false);
            o.setPartial(false);
        }
    }
    
    synchronized static public void Normalize(ConstructionObject o) {
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
     * @param millis
     */
    static public void Pause(int millis) {
        try {
            refreshZC();
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.err.println(e);
            //e.printStackTrace();
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
    static public void Afficher(String a) {
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
    static public void Afficherligne(String a) {
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
    static public Object Entree(String msg) {     
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
     * Otherwise this will return "null"
     * @param msg
     * @return 
    */
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
    
    static public Object PrendreFichierOuvert(String msg) {
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
    
    static public Object EntreeInteractive(String msg, String type) throws Error {
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
    static public void Signaler(String msg) {
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
    
    static public void Alerte(String msg) {
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
     * @throws java.lang.Exception
     */
    static public void Move(String name, String i, String j, String k) throws Exception {
        if (k.equals("undefined")) {
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
             } catch (NumberFormatException e) {
                Expression expX=new Expression(parseVariables(i), o.getConstruction(), o);
                Expression expY=new Expression(parseVariables(j), o.getConstruction(), o);
                //o.move(expX.getValue(), expY.getValue());
                 //pose problème: Si on essaye de mettre des coordonnées
                //algébriques (des expressions), on a NaN. Donc remplacé par
                o.setFixed(parseVariables(expX.toString()), parseVariables(expY.toString()));
            }
             refreshZC();
        }
        else {
            ConstructionObject o=getC().find(parseVariables(name));
            if (o==null) {
                throw new Exception(Loc("notfound"));
            }
            if (!((o instanceof PointObject&& ((PointObject) o).is3D())||(o instanceof VectorObject&& ((VectorObject) o).is3D()))) {
                throw new Exception(Loc("notgoodtype"));
            }
        
            try {
                double x=Math.round(Double.valueOf(parseVariables(i))*1E13)/1E13;
                double y=Math.round(Double.valueOf(parseVariables(j))*1E13)/1E13;
                double z=Math.round(Double.valueOf(parseVariables(k))*1E13)/1E13;
                o.move3D(x, y, z);
                getZC().update_distant(o, 3);
             } catch (NumberFormatException e) {
                 Expression expX=new Expression(parseVariables(i), o.getConstruction(), o);
                 Expression expY=new Expression(parseVariables(j), o.getConstruction(), o);
                 Expression expZ=new Expression(parseVariables(j), o.getConstruction(), o);
                 //o.move(expX.getValue(), expY.getValue());
                 //pose problème: Si on essaye de mettre des coordonnées
                 //algébriques (des expressions), on a NaN. Donc remplacé par
                o.setFixed(parseVariables(expX.toString()), parseVariables(expY.toString()), parseVariables(expZ.toString()));
            }

            refreshZC();
        }
    }
    
    static public void Deplacer(String name, String i, String j, String k) throws Exception {
        if (k.equals("undefined")) {
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
             } catch (NumberFormatException e) {
                Expression expX=new Expression(parseVariables(i), o.getConstruction(), o);
                Expression expY=new Expression(parseVariables(j), o.getConstruction(), o);
                //o.move(expX.getValue(), expY.getValue());
                 //pose problème: Si on essaye de mettre des coordonnées
                //algébriques (des expressions), on a NaN. Donc remplacé par
                o.setFixed(parseVariables(expX.toString()), parseVariables(expY.toString()));
            }
             refreshZC();
        }
        else {
            ConstructionObject o=getC().find(parseVariables(name));
            if (o==null) {
                throw new Exception(Loc("notfound"));
            }
            if (!((o instanceof PointObject&& ((PointObject) o).is3D())||(o instanceof VectorObject&& ((VectorObject) o).is3D()))) {
                throw new Exception(Loc("notgoodtype"));
            }
        
            try {
                double x=Math.round(Double.valueOf(parseVariables(i))*1E13)/1E13;
                double y=Math.round(Double.valueOf(parseVariables(j))*1E13)/1E13;
                double z=Math.round(Double.valueOf(parseVariables(k))*1E13)/1E13;
                o.move3D(x, y, z);
                getZC().update_distant(o, 3);
             } catch (NumberFormatException e) {
                 Expression expX=new Expression(parseVariables(i), o.getConstruction(), o);
                 Expression expY=new Expression(parseVariables(j), o.getConstruction(), o);
                 Expression expZ=new Expression(parseVariables(j), o.getConstruction(), o);
                 //o.move(expX.getValue(), expY.getValue());
                 //pose problème: Si on essaye de mettre des coordonnées
                 //algébriques (des expressions), on a NaN. Donc remplacé par
                o.setFixed(parseVariables(expX.toString()), parseVariables(expY.toString()), parseVariables(expZ.toString()));
            }

            refreshZC();
        }
    }
    

    /**
     * Delete a given object in the current CaRMetal window.
     * @param name Name of the object you wan
     * @throws java.lang.Exception
     */
    static public void Delete(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            throw new Exception(Loc("notfound"));
        }
        getZC().delete(o);
    }
    
    static public void Supprimer(String name) throws Exception {
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
     * @throws java.lang.Exception
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
        } catch (ConstructionException ex) {
        }
        return res;
    }
    
    static public double PrendreValeurExpression(String name) throws Exception {
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
        } catch (ConstructionException ex) {
        }
        return res;
    }

    /**
     * Set the value of a given expression which is in the construction.
     * @param name Name of an existing expression
     * @param value Value you want to give to the expression
     * @throws java.lang.Exception
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
    
    static public void MettreValeurExpression(String name, String value) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public Boolean Exists(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o==null) {
            return false;
        } else {
            return o.valid();
        }
    }
    
    static public Boolean Existe(String name) throws Exception {
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
     * @throws java.lang.Exception
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
    
    static public String PrendreTexte(String name) throws Exception {
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
     * @throws java.lang.Exception
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
    
    static public void MettreTexte(String name, String value) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public void PenDown(String name, boolean state) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setTracked(state);
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public void CrayonBaisse(String name, boolean state) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public void SetRed(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setRed(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public void MettreRouge(String name, String amount) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public void SetGreen(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setGreen(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public void MettreVert(String name, String amount) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public void SetBlue(String name, String amount) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            o.setBlue(parseVariables(amount));
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public void MettreBleu(String name, String amount) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public int GetRed(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getRed();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public int PrendreRouge(String name) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public int GetGreen(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getGreen();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public int PrendreVert(String name) throws Exception {
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
     * @throws java.lang.Exception
     */
    static public int GetBlue(String name) throws Exception {
        ConstructionObject o=getC().find(name);
        if (o!=null) {
            return o.getColor().getBlue();
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public int PrendreBleu(String name) throws Exception {
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
     *Return the x3D-coordinate of an object. If no object is found, then it returns NaN.
     * @param name the name of the object
     * @return the numeric value of the x3D-coordinate
     */
    
    static public double X3D(String name) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o!=null) { 
            if (o instanceof PointObject) {
                return ((PointObject)o).getX3D();
            } 
            if (o instanceof VectorObject) {
                  return ((VectorObject)o).getDeltaX3D();
            }
            else { throw new Exception(Loc("notgoodtype"));}
        }    
        else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    /**
     *Return the y3D-coordinate of an object. If no object is found, then it returns NaN.
     * @param name the name of the object
     * @return the numeric value of the x3D-coordinate
     */
    
    static public double Y3D(String name) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o!=null) { 
            if (o instanceof PointObject) {
                return ((PointObject)o).getY3D(); } 
                if (o instanceof VectorObject) {
                    return ((VectorObject)o).getDeltaY3D();
                } else { throw new Exception(Loc("notgoodtype"));}
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    /**
     *Return the z3D-coordinate of an object. If no object is found, then it returns NaN.
     * @param name the name of the object
     * @return the numeric value of the x3D-coordinate
     */
    
    static public double Z3D(String name) throws Exception {
        ConstructionObject o=getC().find(parseVariables(name));
        if (o!=null) { 
            if (o instanceof PointObject) {
                return ((PointObject)o).getZ3D(); } 
                if (o instanceof VectorObject) {
                    return ((VectorObject)o).getDeltaZ3D();
                } else { throw new Exception(Loc("notgoodtype"));}
        } else {
            throw new Exception(Loc("notfound"));
        }
    }
    
    static public double Distance(String dep, String fin) throws Exception {
        ConstructionObject o=getC().find(parseVariables(dep));
        ConstructionObject p=getC().find(parseVariables(fin));
        if (o!=null) {
        		if (p!=null) {
        			return Math.sqrt((X(fin)-X(dep))*(X(fin)-X(dep))+(Y(fin)-Y(dep))*(Y(fin)-Y(dep)));
        		}
        		else {
                    throw new Exception(Loc("notfound"));
            	}
            }
        else {
            throw new Exception(Loc("notfound"));
        	}
    }
    
    static public double Distance3D(String dep, String fin) throws Exception {
        ConstructionObject o=getC().find(parseVariables(dep));
        ConstructionObject p=getC().find(parseVariables(fin));
        if (o!=null) {
        		if (p!=null) {
        			return Math.sqrt((X3D(fin)-X3D(dep))*(X3D(fin)-X3D(dep))+(Y3D(fin)-Y3D(dep))*(Y3D(fin)-Y3D(dep))+(Z3D(fin)-Z3D(dep))*(Z3D(fin)-Z3D(dep)));
        		}
        		else {
                    throw new Exception(Loc("notfound"));
            	}
            }
        else {
            throw new Exception(Loc("notfound"));
        	}
    }

    /**
     * Return the origin of a vector. Works with a segment too
     * @param name the name of the vector
     * @return the name of the origin
     * @throws java.lang.Exception
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
    
    static public String Origine(String name) throws Exception {
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
     * @throws java.lang.Exception
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
    
    static public String Extremite(String name) throws Exception {
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
        for(String name1 : names) {
            c("color("+col+"," + name1 + ")");
        }
    }
    
    static public void MettreCouleur(String name, String col) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            c("color("+col+"," + name1 + ")");
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
        for(String name1 : names) {
            c("type("+type+"," + name1 + ")");
        }
    }
    
    static public void MettreTypePoint(String name, String type) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            c("type("+type+"," + name1 + ")");
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
    static public void SetRGBColor(String name, String r, String g, String b) {
     //   Color mycolor=new Color(r, g, b);
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
               // o.setSpecialColor(mycolor);
                o.setSpecialColor(r,g,b);
            }
        }
//        getZC().repaint();
//        validate();
//            paint()

    }
    
    static public void MettreCouleurRVB(String name, String r, String g, String b) {
     //   Color mycolor=new Color(r, g, b);
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
               // o.setSpecialColor(mycolor);
                o.setSpecialColor(r,g,b);
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
        for (String name1 : names) {
            c("thickness("+thc+"," + name1 + ")");
        }
    }
    
    static public void MettreEpaisseur(String name, String thc) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            c("thickness("+thc+"," + name1 + ")");
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
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setAlias(parseVariables(alias));
            }
        }
    }
    
    static public void MettreAlias(String name, String alias) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
     * @param delta
     */
    static public void SetIncrement(String name, double delta) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o instanceof PointObject) {
                o.setIncrement(delta);
            }
        }
    }
    
    static public void MettreIncrement(String name, double delta) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
    
    static public void MettreRayonMagetique(String name, String ray) {
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
    
    static public void MettreObjetsMagnetiques(String name, String objectlist) {
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
    
    static public void AjouterObjetMagnetique(String name, String object) {
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
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFixed(bool);
            }
        }
    }
    
    static public void MettreFixe(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFixed(bool);
            }
        }
    }
    
    static public void Liberate(String name) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFixed(false);
            }
        }
    }
    
    static public void Liberer(String name) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFixed(false);
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
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setShowName(bool);
            }
        }
    }
    
    static public void MettreMontrerNom(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setShowValue(bool);
            }
        }
    }
    
    static public void MettreMontrerValeur(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFilled(bool);
            }
        }
    }
    
    static public void MettreRempli(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setFilled(bool);
            }
        }
    }
    
    static public void SetSolid(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setSolid(bool);
            }
        }
    }
    
    static public void MettreOpaque(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (int i=0; i<names.length; i++) {
            ConstructionObject o=getC().find(names[i]);
            if (o!=null) {
                o.setSolid(bool);
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
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null) {
                o.setPartial(bool);
                getC().updateCircleDep();
            }
        }
    }
    
    static public void MettrePartiel(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
    
    static public void MontrerNoms() {
        cm("shownames");
    }

    /**
     *After calling this command, names of new objects will not be displayed.
     */
    static public void Hidenames() {
        cm("hidenames");
    }
    
    static public void CacherNoms() {
        cm("hidenames");
    }

    /**
     * Hides an object (or multiple objects).
     * <br><b>Example</b> : <i><b>Hide("A,B,c1,l1")</b></i>
     * will hide objects A, B, c1 and l1.
     * @param name Name(s) of object(s).
     */
    static public void Hide(String name) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            c("hide(true," + name1 + ")");
        }
        paint();
    }
    
    static public void Cacher(String name) {
        String[] names=parseVariables(name).split(",");
        for(String name1 : names) {
            c("hide(true," + name1 + ")");
        }
        paint();
    }

    static public void SetHide(String name, boolean b) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            c("hide("+b+"," + name1 + ")");
        }
        paint();
    }
    
    static public void MettreCache(String name, boolean b) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            c("hide("+b+"," + name1 + ")");
        }
        paint();
    }

    /**
     * Shows an object (or multiple objects).
     * <br><b>Example</b> : <i><b>Show("A,B,c1,l1")</b></i>
     * will show objects A, B, c1 and l1.
     * @param name Name(s) of object(s).
     */
    static public void Show(String name) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            c("hide(false," + name1 + ")");
        }
        paint();
    }
    
    static public void Montrer(String name) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            c("hide(false," + name1 + ")");
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
     * @throws java.lang.Exception
     */
    static public void Layer(String name, String exp) throws Exception {
        Conditional(name, "z", parseVariables(exp));//parseVariablé par Alain le 24 janvier 2012, à tester!
    }
    
    static public void Calque(String name, String exp) throws Exception {
        Conditional(name, "z", parseVariables(exp));//parseVariablé par Alain le 24 janvier 2012, à tester!
    }

    /**
     * Create a condition for an object or a list of objects
     * @param name Name(s) of object(s)
     * @param TAG type of condition this can be equal to :
     * solid, hidden, normal, thick, thin, black, green, blue,
     * cyan, red, brown, showvalue, showname, background, and superhidden
     * @param expTXT conditional expression to be apply
     * @throws java.lang.Exception
     */
    static public void Conditional(String name, String TAG, String expTXT) throws Exception {
        try {
            String[] names=parseVariables(name).split(",");
            for (String name1 : names) {
                ConstructionObject O = getC().find(name1);
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
    
    static public void Conditionnel(String name, String TAG, String expTXT) throws Exception {
        try {
            String[] names=parseVariables(name).split(",");
            for (String name1 : names) {
                ConstructionObject O = getC().find(name1);
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
     * @throws java.lang.Exception
     */
    static public String Point(String name, String x, String y) throws Exception {
        PointObject pt=null;
        synchronized (getC()) {
            if (x.equals("undefined")) {
                double xx=getC().getX()+2*Math.random()*getC().getW()-getC().getW();
                double yy=getC().getY()-Math.random()*getC().getH()+getC().getH()/2;
                x=""+xx;
                y=""+yy;
                if (name.equals("undefined")) {
                    name="";
                }
            } else if (y.equals("undefined")) {
                y=x;
                x=name;
                name="";
            }
            try {
                pt=new PointObject(getC(), Math.round(Double.valueOf(x)*1E13)/1E13, Math.round(Double.valueOf(y)*1E13)/1E13);
            } catch (NumberFormatException e) {
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
            pt.validate();
            addObject(pt);
            return pt.getName();
        }
    }
    
    /**
     * @author Dibs
     * 
     */
    static public String Point3D(String name, String x, String y, String z) throws Exception {
    	if (!getZC().is3D()) return "";
        PointObject pt=null;
        synchronized (getC()) {
            if (x.equals("undefined")) {
                double xx=getC().getX()+2*Math.random()*getC().getW()-getC().getW();
                double yy=getC().getY()-Math.random()*getC().getH()+getC().getH()/2;
                try {
        		double xO=getC().find("O").getX(), yO = getC().find("O").getY();
        		double x3DO=getC().find("O").getX();
        		double y3DO=getC().find("O").getY();
        		double xx3D = Math.sin(Math.toRadians(getC().find("E10").getValue()))*(xx-xO)-Math.sin(Math.toRadians(getC().find("E11").getValue()))*Math.cos(Math.toRadians(getC().find("E10").getValue()))*(yy-yO);
    			double yy3D = Math.cos(Math.toRadians(getC().find("E10").getValue()))*(xx-xO)+Math.sin(Math.toRadians(getC().find("E11").getValue()))*Math.sin(Math.toRadians(getC().find("E10").getValue()))*(yy-yO);
    			double zz3D = Math.cos(Math.toRadians(getC().find("E11").getValue()))*(yy-yO);
                        if (Math.abs(xx3D)<1e-16) {
                            xx3D=0;
                        }
                        if (Math.abs(yy3D)<1e-16) {
                            yy3D=0;
                        }
                        if (Math.abs(zz3D)<1e-16) {
                            zz3D=0;
                        }
    			x=""+xx3D;
                        y=""+yy3D;
                        z=""+zz3D;
        	}
        	catch (final Exception f) {
                    x=""+Math.random();
                    y=""+Math.random();
                    z=""+Math.random();
                }
                if (name.equals("undefined")) {
                    name="";
                }
                
            } else if (z.equals("undefined")) {
                z=y;
                y=x;
                x=name;
                name="";
            }
            try {
            	double x1 = Math.round(Double.valueOf(x)*1E13)/1E13;
            	double y1 =Math.round(Double.valueOf(y)*1E13)/1E13;
            	double z1 =Math.round(Double.valueOf(z)*1E13)/1E13;
                pt=new PointObject(getC(), 0, 0);
                pt.setFixed("x(O)+("+x1+")*(x(X)-x(O))+("+y1+")*(x(Y)-x(O))+("+z1+")*(x(Z)-x(O))", "y(O)+("+x1+")*(y(X)-y(O))+("+y1+")*(y(Y)-y(O))+("+z1+")*(y(Z)-y(O))");
                pt.setIs3D(true);
                pt.setX3D(x1);
                pt.setY3D(y1);
                pt.setZ3D(z1);
            } catch (Exception e) {
                try {
                    pt=new PointObject(getC(), 0, 0);
                    pt.setFixed(x,y,z);
                    pt.setIs3D(true);
                    pt.validCoordinates3D();
                    pt.setFixed("x(O)+("+parseVariables(x)+")*(x(X)-x(O))+("+parseVariables(y)+")*(x(Y)-x(O))+("+parseVariables(z)+")*(x(Z)-x(O))", "y(O)+("+parseVariables(x)+")*(y(X)-y(O))+("+parseVariables(y)+")*(y(Y)-y(O))+("+parseVariables(z)+")*(y(Z)-y(O))");
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
     * Set or Create a bounded point
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
    
    static public String PointSur(String name, String obj) {
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
            String sn = Global.name("name.short.Midpoint");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            return LastNObjectsName(1);
        }
    }
    
    static public String Milieu(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            String sn = Global.name("name.short.Midpoint");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Reflects the point p with line d
     * @param name Name of the Symmetric
     * @param l Reflection line
     * @param p Point to reflect
     * @return the reflection point name
     * @throws java.lang.Exception
     */
    static public String Reflection(String name, String l, String p) throws Exception {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=l;
                l=name;
                name="";
            }
            l=parseVariables(l);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/syma("+l+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/syma("+l+","+p+")");
            }
            return LastNObjectsName(1);
        }
    }
    
    static public String SymetrieAxiale(String name, String l, String p) throws Exception {
        synchronized (getC()) {
            if (p.equals("undefined")) {
                p=l;
                l=name;
                name="";
            }
            l=parseVariables(l);
            p=parseVariables(p);
            if (name.equals("")) {
                c("@builtin@/syma("+l+","+p+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"=@builtin@/syma("+l+","+p+")");
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
     * @throws java.lang.Exception
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
     * @param b Point to reflect
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
            String sn = Global.name("name.short.Point");
            if (name.equals("")) {
                c(sn+"(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
            }
            return LastNObjectsName(1);
        }
    }
    
    static public String SymetrieCentrale(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            String sn = Global.name("name.short.Point");
            if (name.equals("")) {
                c(sn+"(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"(2*x("+a+")-x("+b+"),2*y("+a+")-y("+b+"))");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Creates the perpendicular bisector between two points a and b
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
    
    static public String Mediatrice(String name, String a, String b) {
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
     * @throws java.lang.Exception
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
    
    static public String Bissectrice(String name, String a, String b, String c) throws Exception {
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
     * Create the circumcicular circle between 3 points a, b and c
     * @param name Name of the perpendicular bisector
     * @param a first point
     * @param b second point
     * @param c third point
     * @return the circumcicular circle name
     * @throws java.lang.Exception
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
    
    static public String Cercle3pts(String name, String a, String b, String c) throws Exception {
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
     * Creates the circle around a with radius bc
     * @param name Name of the circle
     * @param a center point
     * @param b first point
     * @param c second point
     * @return the circle name
     * @throws java.lang.Exception
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
            String sn = Global.name("name.short.Circle3");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }
    
    static public String Cercle3(String name, String a, String b, String c) throws Exception {
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
            String sn = Global.name("name.short.Circle3");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+")");
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
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
            String sn = Global.name("name.short.Intersection");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            IntersectionObject i=(IntersectionObject) getC().lastButN(0);
            i.setFirst(Integer.valueOf(order)==0);
            return LastNObjectsName(1);
        }
    }
    
    static public String IntersectionOrdonnee(String name, String a, String b, String order) throws Exception {
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
            String sn = Global.name("name.short.Intersection");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
     * @throws java.lang.Exception
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
            String sn = Global.name("name.short.Intersection");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
            String sn = Global.name("name.short.Intersection");
            if (name1.equals("")) {
                c("I2,I1="+sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                c(name2+","+name1+"="+sn+"("+a+","+b+")");
            }
            return LastNObjectsName(2);
        }
    }

    /**
     * General case: The number of the intersection points depends
     * on the nature of the lines intersected
     * @param name of the first intersection point
     * @param a Name of first object
     * @param b Name of second object
     * @return list of the intersection points in a String
     * @throws java.lang.Exception
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
            String sn = Global.name("name.short.Intersection");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
            String sn = Global.name("name.short.Line");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String Droite(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            String sn = Global.name("name.short.Line");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
            String sn = Global.name("name.short.Ray");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String DemiDroite(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            String sn = Global.name("name.short.Ray");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
            String sn = Global.name("name.short.Angle");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+")");
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
            String sn = Global.name("name.short.Angle");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }
    
    static public String AngleFixe(String name, String a, String b, String c) {
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
            String sn = Global.name("name.short.Angle");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+")");
            }
            return LastNObjectsName(1);
        }
    }

    /**
     * Set angles as reflex or not.
     * <br><b>Example</b> : <i><b>ReflexAngle("a1,a2",true)</b></i>
     * @param name Name(s) of object.
     * @param bool "true" to set as reflex, "false" to have only angles less than 180°.
     * @throws java.lang.Exception
     */
    static public void ReflexAngle(String name, boolean bool) throws Exception {

        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (!(o instanceof AngleObject)) {
                throw new Exception(Loc("notgoodtype"));
            }
            if (o!=null && true) {
                o.setObtuse(bool);
            }
        }
    }
    
    static public void AngleRentrant(String name, boolean bool) throws Exception {

        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (!(o instanceof AngleObject)) {
                throw new Exception(Loc("notgoodtype"));
            }
            if (o!=null && true) {
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
    
    static public Object ExecuterMacro(String lastObjName, String macroname, String params) {
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
            for (String c1 : c) {
                ConstructionObject o = getC().find(c1);
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
    
    static public String Polygone(String name, String params) {
        synchronized (getC()) {
            if (params.equals("undefined")) {
                params=name;
                name="";
            }
            String[] c=parseVariables(params).split(",");
            Vector V=new Vector();
            for (String c1 : c) {
                ConstructionObject o = getC().find(c1);
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
    
    static public String Triangle(String name, String som1, String som2, String som3) {
        synchronized (getC()) {
            if (som3.equals("undefined")) {
                som3=som2; som2=som1; som1=name;
                name="";
            }
            Vector V=new Vector();
            ConstructionObject o = getC().find(som1);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som2);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som3);
            if (o!=null) {
                    V.add(o);
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
    
    static public String Quadrilatere(String name, String som1, String som2, String som3, String som4) {
        synchronized (getC()) {
            if (som4.equals("undefined")) {
                som4=som3; som3=som2; som2=som1; som1=name;
                name="";
            }
            Vector V=new Vector();
            ConstructionObject o = getC().find(som1);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som2);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som3);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som4);
            if (o!=null) {
                    V.add(o);
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
    
    static public String Quadrangle(String name, String som1, String som2, String som3, String som4) {
        synchronized (getC()) {
            if (som4.equals("undefined")) {
                som4=som3; som3=som2; som2=som1; som1=name;
                name="";
            }
            Vector V=new Vector();
            ConstructionObject o = getC().find(som1);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som2);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som3);
            if (o!=null) {
                    V.add(o);
                }
            o = getC().find(som4);
            if (o!=null) {
                    V.add(o);
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
            String sn = Global.name("name.short.Quadric");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+","+d+","+e+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+","+d+","+e+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String Quadrique(String name, String a, String b, String c, String d, String e) {
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
            String sn = Global.name("name.short.Quadric");
            if (name.equals("")) {
                c(sn+"("+a+","+b+","+c+","+d+","+e+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+","+c+","+d+","+e+")");
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
    
    static public String FonctionCartesienne(String name, String a, String b, String fx) {
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
    
    static public String FonctionParametrique(String name, String a, String b, String xt, String yt) {
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
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMinOpen(bool);
            }
        }
    }

    static public void SetMinClosed(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMinClosed(bool);
            }
        }
    }

    static public void SetMaxOpen(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
            if (o!=null && o instanceof FunctionObject) {
                FunctionObject of=(FunctionObject) o;
                of.setMaxOpen(bool);
            }
        }
    }

    static public void SetMaxClosed(String name, boolean bool) {
        String[] names=parseVariables(name).split(",");
        for (String name1 : names) {
            ConstructionObject o = getC().find(name1);
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
    
    static public String TraceImplicite(String name, String f) {
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
            String sn = Global.name("name.short.Segment");
            if (name.equals("")) {
                c(sn+"("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+r+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String SegmentFixe(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            String sn = Global.name("name.short.Segment");
            if (name.equals("")) {
                c(sn+"("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+r+")");
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
            SegmentObject so;
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
            //so.setColorType(ConstructionObject.NORMAL);
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
            VectorObject vo;
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
    
    static public String Vecteur(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            VectorObject vo;
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
            String sn = Global.name("name.short.Circle");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String Cercle(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            String sn = Global.name("name.short.Circle");
            if (name.equals("")) {
                c(sn+"("+a+","+b+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+b+")");
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
            String sn = Global.name("name.short.Circle");
            if (name.equals("")) {
                c(sn+"("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+r+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String CercleRayon(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            String sn = Global.name("name.short.Circle");
            if (name.equals("")) {
                c(sn+"("+a+","+r+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+a+","+r+")");
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
            String sn = Global.name("name.short.Parallel");
            if (name.equals("")) {
                c(sn+"("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+lne+","+pt+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String Parallele(String name, String lne, String pt) {
        synchronized (getC()) {
            if (pt.equals("undefined")) {
                pt=lne;
                lne=name;
                name="";
            }
            lne=parseVariables(lne);
            pt=parseVariables(pt);
            String sn = Global.name("name.short.Parallel");
            if (name.equals("")) {
                c(sn+"("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+lne+","+pt+")");
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
            String sn = Global.name("name.short.Plumb");
            if (name.equals("")) {
                c(sn+"("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+lne+","+pt+")");
            }
            NormalizeLast();
            return LastNObjectsName(1);
        }
    }
    
    static public String Perpendiculaire(String name, String lne, String pt) {
        synchronized (getC()) {
            if (pt.equals("undefined")) {
                pt=lne;
                lne=name;
                name="";
            }
            lne=parseVariables(lne);
            pt=parseVariables(pt);
            String sn = Global.name("name.short.Plumb");
            if (name.equals("")) {
                c(sn+"("+lne+","+pt+")");
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                c(name+"="+sn+"("+lne+","+pt+")");
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
            } catch (final ConstructionException e) {
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
     * @throws java.lang.Exception
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
            } catch (NumberFormatException e) {
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
            } catch (NumberFormatException e) {
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
    
    static public String Texte(String name, String txt, String x, String y) {
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
            } catch (NumberFormatException e) {
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
    // 3D
    /**
     * Creates a sphere with center a to point b.
     * @param name Name of the sphere (suggestion)
     * @param a center point
     * @param b point of the sphere
     * @return Name of the sphere
     */
    static public String Sphere(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            ConstructionObject sphere;
            if (name.equals("")) {
            	//sphere=( (ConstructionObject) ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b));
            	ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
            	sphere=getC().lastButN(13);
            	sphere.setShowName(false);
            } else {
                name=parseVariables(name);
                //sphere= (ConstructionObject) ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
                ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
                sphere=getC().lastButN(13);
                sphere.setName(name);
            }
            Normalize(sphere);
            return sphere.getName();
        }
    }
    
    static public String SphEre(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            ConstructionObject sphere;
            if (name.equals("")) {
            	//sphere=( (ConstructionObject) ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b));
            	ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
            	sphere=getC().lastButN(13);
            	sphere.setShowName(false);
            } else {
                name=parseVariables(name);
                //sphere= (ConstructionObject) ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
                ExecuteMacro("","@builtin@/3Dspherepoint","O,X,Y,Z,"+a+","+b);
                sphere=getC().lastButN(13);
                sphere.setName(name);
            }
            Normalize(sphere);
            return sphere.getName();
        }
    }
    
    static public String FixedSphere(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsphererayon","O,X,Y,Z,"+a);
                getC().lastButN(13).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsphererayon","O,X,Y,Z,"+a);
                getC().lastButN(13).setName(name);
            }
            Normalize(13);
            ( (FixedCircleObject) getC().lastButN(13)).setFixed(r);
            return getC().lastButN(13).getName();
        }
    }
    
    static public String SphEreRayon(String name, String a, String r) {
        synchronized (getC()) {
            if (r.equals("undefined")) {
                r=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            r=parseVariables(r);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsphererayon","O,X,Y,Z,"+a);
                getC().lastButN(13).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsphererayon","O,X,Y,Z,"+a);
                getC().lastButN(13).setName(name);
            }
            Normalize(13);
            ( (FixedCircleObject) getC().lastButN(13)).setFixed(r);
            return getC().lastButN(13).getName();
        }
    }
    
    /**
     * Creates the projection on a plane
     * a is "A,B,C"
     * b is the point to project
     */
    
    static public String Projection3D(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dproj","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dproj","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Reflection3D(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsymp","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsymp","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Symetrie3DPlan(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsymp","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsymp","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Symmetry3D(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsymc","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsymc","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String SymetrieCentrale3D(String name, String a, String b) {
        synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	ExecuteMacro("","@builtin@/3Dsymc","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dsymc","O,X,Y,Z,"+a+","+b);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Translation3D(String name, String a, String b, String p) throws Exception {
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
            	ExecuteMacro("","@builtin@/3Dtrans","O,X,Y,Z,"+a+","+b+","+p);
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                ExecuteMacro("","@builtin@/3Dtrans","O,X,Y,Z,"+a+","+b+","+p);
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Circle3D(String name, String a, String b) {
    	synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	try {
            	ExecuteMacro("","@builtin@/3Dcircle1","O,X,Y,Z,"+a+","+b);
            	}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try {
                	ExecuteMacro("","@builtin@/3Dcircle1","O,X,Y,Z,"+a+","+b);
                	}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Cercle3D(String name, String a, String b) {
    	synchronized (getC()) {
            if (b.equals("undefined")) {
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            if (name.equals("")) {
            	try {
            	ExecuteMacro("","@builtin@/3Dcircle1","O,X,Y,Z,"+a+","+b);
            	}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try {
                	ExecuteMacro("","@builtin@/3Dcircle1","O,X,Y,Z,"+a+","+b);
                	}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
    
    static public String FixedCircle3D(String name, String a, String b, String r) {
    	synchronized (getC()) {
            if (r.equals("undefined")) {
            	r=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            r=parseVariables(r);
            if (name.equals("")) {
            	try { ExecuteMacro("","@builtin@/3Dcircle2","O,X,Y,Z,"+a+","+b+","+r);
            		}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try { ExecuteMacro("","@builtin@/3Dcircle2","O,X,Y,Z,"+a+","+b+","+r);
        		}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
    
    static public String CercleRayon3D(String name, String a, String b, String r) {
    	synchronized (getC()) {
            if (r.equals("undefined")) {
            	r=b;
                b=a;
                a=name;
                name="";
            }
            a=parseVariables(a);
            b=parseVariables(b);
            r=parseVariables(r);
            if (name.equals("")) {
            	try { ExecuteMacro("","@builtin@/3Dcircle2","O,X,Y,Z,"+a+","+b+","+r);
            		}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try { ExecuteMacro("","@builtin@/3Dcircle2","O,X,Y,Z,"+a+","+b+","+r);
        		}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Circle3D3pts(String name, String a, String b, String c) {
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
            	try { ExecuteMacro("","@builtin@/3Dcircle3pts","O,X,Y,Z,"+a+","+b+","+c);
            		}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try { ExecuteMacro("","@builtin@/3Dcircle3pts","O,X,Y,Z,"+a+","+b+","+c);
        		}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
    
    static public String Cercle3D3pts(String name, String a, String b, String c) {
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
            	try { ExecuteMacro("","@builtin@/3Dcircle3pts","O,X,Y,Z,"+a+","+b+","+c);
            		}
            	catch (final Exception f) {}
                getC().lastButN(0).setShowName(false);
            } else {
                name=parseVariables(name);
                try { ExecuteMacro("","@builtin@/3Dcircle3pts","O,X,Y,Z,"+a+","+b+","+c);
        		}
                catch (final Exception f) {}
                getC().lastButN(0).setName(name);
            }
            Normalize(0);
            refreshZC();
            return getC().lastButN(0).getName();
        }
    }
}
