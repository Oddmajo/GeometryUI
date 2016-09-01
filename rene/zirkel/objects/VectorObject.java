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
        return true;
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

    @Override
    public boolean fixedCoord(){
	boolean b = P2.getEX().equals("x("+P1.getName()+")"+"+"+getEX());
	b &= P2.getEY().equals("y("+P1.getName()+")"+"+"+getEY());

	return b;
    }

    @Override
    public void move(double x, double y){
	P2.setColorType(THIN);
	//P2.setHidden(true);
	P2.move(P1.getX()+x, P1.getY()+y);
    }

    @Override
    public String getCDPDisplayValue(){
	return "("+Global.getCDPLocaleNumber(this.getDeltaX(), 2)+" "+(Global.getParameter("options.germanpoints", false)?"|":";")+Global.getCDPLocaleNumber(this.getDeltaY(), 2)+")";
    }

    @Override
    public String getDisplayValue() {
	String rep = "";
	if(!this.showName()) {
	    rep += "$";
	}
	return rep+"\\left(\\begin{array}{r}"+Global.getLocaleNumber(getDeltaX(), "lengths")+"\\\\"+Global.getLocaleNumber(getDeltaY(), "lengths")+"\\end{array}\\right)$";

	//return "("+Global.getLocaleNumber(getDeltaX(), "lengths")+(Global.getParameter("options.germanpoints", false)?"|":";")+Global.getLocaleNumber(getDeltaY(), "lengths")+")";
    }

    @Override
    public void updateText() {
	if (!Fixed && !this.fixedCoord()) {
	    setText(text2(Global.name("text.vector"), P1.getName(), P2.getName()));
	} else if(Fixed) {
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

    public void setEXY(String x, String y){
	EX = new Expression(x, getConstruction(), this);
	EY = new Expression(y, getConstruction(), this);
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
	if(P1.moveable()){
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
	try {
	    setEXY(EX.toString(), EY.toString());
	    EX.translate();
	    EY.translate();
	} catch (final Exception e) {
	    //setFixed(false);
	}
    }
}
