/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.objects;

import eric.JZirkelCanvas;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.expression.Expression;

/**
 *
 * @author PM
 */
public class VectorObject extends SegmentObject {
    private Expression EX = null;
    private Expression EY = null;
    private Expression EX3D = null;
    private Expression EY3D = null;
    private Expression EZ3D = null;
    //private double X3D = 0;
    //private double Y3D = 0;
    //private double Z3D = 0;
    static Count N = new Count();

    public VectorObject(final Construction c, final PointObject p1, final PointObject p2) {
	super(c, p1, p2);
	Arrow = true;

//	int n = Global.name("name.short.Vector").length();
//	String name = Name.substring(0, n)+"_"+Name.substring(n);
//	this.setAlias("$\\overrightarrow{"+name+"}");
    }


    @Override
    public Boolean is2DObject(){
        return !Is3D;
    }

    @Override
    public Boolean isTellsSon(){
        return true;
        //the vector object, like William Tell's son, has an arrow over it
    }


    @Override
    public void setName() {
	Name=Global.name("name.short.Vector")+getN();
    }

    @Override
    public int getN() {
	return N.next();
    }

    public double getDeltaX(){
	return P2.getX()-P1.getX();
    }

    public double getDeltaY(){
	return P2.getY()-P1.getY();
    }
    
    public double getDeltaX3D(){
    	return P2.getX3D()-P1.getX3D();
        }

     public double getDeltaY3D(){
    	return P2.getY3D()-P1.getY3D();
        }
     
     public double getDeltaZ3D(){
     	return P2.getZ3D()-P1.getZ3D();
         }

    @Override
    public boolean fixedCoord(){
	boolean b = P2.getEX().equals("x("+P1.getName()+")"+"+"+getEX());
	b &= P2.getEY().equals("y("+P1.getName()+")"+"+"+getEY());

	return b;
    }
    
    @Override
    public boolean fixedCoord3D(){
    	boolean b = P2.getEX3D().equals("x3D("+P1.getName()+")"+"+"+getEX3D());
    	b &= P2.getEY3D().equals("y3D("+P1.getName()+")"+"+"+getEY3D());
    	b &= P2.getEZ3D().equals("z3D("+P1.getName()+")"+"+"+getEZ3D());
    	return b;
    }

    @Override
    public void move(double x, double y){
	P2.setColorType(THIN);
	//P2.setHidden(true);
	P2.move(P1.getX()+x, P1.getY()+y);
    }
    
    @Override
    public void move3D(double x, double y, double z){
	P2.setColorType(THIN);
	//P2.setHidden(true);
	P2.move3D(P1.getX3D()+x, P1.getY3D()+y, P1.getZ3D()+z);
    }

    @Override
    public String getCDPDisplayValue(){
	return "("+Global.getCDPLocaleNumber(this.getDeltaX(), 2)+" "+(Global.getParameter("options.germanpoints", false)?"|":";")+Global.getCDPLocaleNumber(this.getDeltaY(), 2)+")";
    }

    @Override
    public String getDisplayValue() {
	/*
        String rep = "";
	if(!this.showName()) {
	    rep += "$";
	}*/
        if (P1.is3D()&&P2.is3D()) return "\\left(\\begin{array}{r}"+Global.getLocaleNumber(getDeltaX3D(), "lengths")+"\\\\"+Global.getLocaleNumber(getDeltaY3D(), "lengths")+"\\\\"+Global.getLocaleNumber(getDeltaZ3D(), "lengths")+"\\end{array}\\right)";
        else return "\\left(\\begin{array}{r}"+Global.getLocaleNumber(getDeltaX(), "lengths")+"\\\\"+Global.getLocaleNumber(getDeltaY(), "lengths")+"\\end{array}\\right)";
	//return rep+"\\left(\\begin{array}{r}"+Global.getLocaleNumber(getDeltaX(), "lengths")+"\\\\"+Global.getLocaleNumber(getDeltaY(), "lengths")+"\\end{array}\\right)$";
	//return "("+Global.getLocaleNumber(getDeltaX(), "lengths")+(Global.getParameter("options.germanpoints", false)?"|":";")+Global.getLocaleNumber(getDeltaY(), "lengths")+")";
    }

    @Override
    public void updateText() {
	if (!Fixed && !this.fixedCoord()) {
	    setText(text2(Global.name("text.vector"), P1.getName(), P2.getName()));
	} else if (Fixed) {
	    if (E == null) {
		setText(text3(Global.name("text.vector.fixed.length"), P1.getName(), P2.getName(), "" + round(R)));
	    } else {
		setText(text3(Global.name("text.vector.fixed.length"), P1.getName(), P2.getName(), "\"" + E.toString() + "\""));
	    }
	} else {
	    setText(text3(Global.name("text.vector.fixed.coord"), P1.getName(), P2.getName(), "(" + round(this.getDeltaX())+" ; " + round(this.getDeltaY()) + ")"));
	}
    }

    @Override
    public void setFixed(final String x, final String y) {
	setEXY(x, y);
	P2.setFixed("x("+P1.getName()+")"+"+"+EX.toString(), "y("+P1.getName()+")"+"+"+EY.toString());
        P2.setColorType(THIN);
	P2.setBack(true);//modif proposée par Alain le 29/12/12 parce que je sais pas comment mettre un numéro de calque :-X
	updateText();
	JZirkelCanvas.getCurrentZC().repaint();
    }

    @Override
    public void setFixed(boolean b){
	P2.setFixed(b);
	P2.setColorType(b?THIN:NORMAL);
	//P2.setHidden(b);
	updateText();
	JZirkelCanvas.getCurrentZC().repaint();
    }
    
    @Override
    public void setFixed3D(boolean b){
	P2.setFixed3D(b);
	P2.setColorType(b?THIN:NORMAL);
	//P2.setHidden(b);
	updateText();
	JZirkelCanvas.getCurrentZC().repaint();
    }
    
    
    @Override
    public void setFixed(final String x, final String y, final String z) {
	setEXYZ(x, y, z);
	P2.setFixed("x3D("+P1.getName()+")"+"+"+EX3D.toString(), "y3D("+P1.getName()+")"+"+"+EY3D.toString(), "z3D("+P1.getName()+")"+"+"+EZ3D.toString());
        P2.setColorType(THIN);
	P2.setBack(true);//modif proposée par Alain le 29/12/12 parce que je sais pas comment mettre un numéro de calque :-X
	updateText();
	JZirkelCanvas.getCurrentZC().repaint();
    }

    public void setEXY(String x, String y){
	EX = new Expression(x, getConstruction(), this);
	EY = new Expression(y, getConstruction(), this);
    }
    
    public void setEXYZ(String x3D, String y3D, String z3D){
    	EX3D = new Expression(x3D, getConstruction(), this);
    	EY3D = new Expression(y3D, getConstruction(), this);
    	EZ3D = new Expression(z3D, getConstruction(), this);
        }

    @Override
    public String getEX() {
        if (EX!=null) {
            return EX.toString();
        } else {
            return ""+round(this.getDeltaX());
        }
    }

    @Override
    public String getEY() {
        if (EY!=null) {
            return EY.toString();
        } else {
            return ""+round(this.getDeltaY());
        }
    }
    
    @Override
    public String getEX3D() {
        if (EX3D!=null) {
            return EX3D.toString();
        } else {
            return ""+round(this.getDeltaX3D());
        }
    }

    @Override
    public String getEY3D() {
        if (EY3D!=null) {
            return EY3D.toString();
        } else {
            return ""+round(this.getDeltaY3D());
        }
    }
    
    @Override
    public String getEZ3D() {
        if (EZ3D!=null) {
            return EZ3D.toString();
        } else {
            return ""+round(this.getDeltaZ3D());
        }
    }

    @Override
    public void printArgs(final XmlWriter xml) {
	xml.printArg("from", P1.getName());
	xml.printArg("to", P2.getName());
	if (Fixed && E != null) {
	    xml.printArg("fixed", E.toString());
	}
	if(fixedCoord()){
	   xml.printArg("x", EX.toString());
	   xml.printArg("y", EY.toString());
	}
	if (Is3D) {
        xml.printArg("is3D", "true");
        
        if (Fixed3D && E3D != null) {
        	xml.printArg("fixed3D", E3D.toString());
        }
        
        if(fixedCoord3D()){
        	xml.printArg("x3D", EX3D.toString());
        	xml.printArg("y3D", EY3D.toString());
        	xml.printArg("z3D", EZ3D.toString());
     	}
	}
	if (Fixed3D) {
        xml.printArg("fixed3D", "true");
    }
	xml.printArg("arrow", "true");
	if (code_symbol > 0) {
	    xml.printArg("code_symbol", "" + code_symbol);
	}
	if (Partial) {
	    xml.printArg("partial", "true");
	}
    }

    @Override
    public boolean moveable() {
	if(P1.moveable()&&!is3D()){
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public ConstructionObject copy(final double x, final double y) {
        VectorObject o=null;
	try {
            o=(VectorObject) clone();
            setTranslation(o);
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            //o.setTarget(false);
        } catch (final Exception e) {}
        return o;
    }

    @Override
    public void translate() {
	super.translate();
        if (Is3D) {
            try {
        	setEXYZ(EX3D.toString(), EY3D.toString(), EZ3D.toString());
        	EX3D.translate();
                EY3D.translate();
                EZ3D.translate();
        	}
        	catch (final Exception e) {}
        }
        else {
            try {
	    setEXY(EX.toString(), EY.toString());
	    EX.translate();
	    EY.translate();
            } catch (final Exception e) {
	    //setFixed(false);
                }
        }
	
    }


	public void setIs3D(boolean b) {
	    Is3D=b;
	}
}
