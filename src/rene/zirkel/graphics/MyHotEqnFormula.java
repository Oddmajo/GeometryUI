/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.graphics;

import java.awt.Graphics2D;

import rene.zirkel.ZirkelCanvas;
import ui.atp.sHotEqn;

/**
 *
 * @author erichake
 */
public class MyHotEqnFormula extends MyFormula {

    private sHotEqn HE=null;
    private String EQ="";
    private ZirkelCanvas ZC;
    private Graphics2D G=null;

    public MyHotEqnFormula(ZirkelCanvas zc, Graphics2D g) {
        ZC=zc;
        G=g;
        HE=new sHotEqn(ZC);
    }

    @Override
    public void setEquation(String s) {
        EQ=s;
        HE.setEquation(s);
        EQwidth=HE.getSizeof(HE.getEquation(), G).width;
        EQheight=HE.getSizeof(HE.getEquation(), G).height;
        EQbaseLine=HE.getAscent(HE.getEquation(), G);
    }

    public double getEquationWidth() {
        return EQwidth;
    }

    public double getEquationHeight() {
        return EQheight;
    }

    public double getEquationAscent() {
        double d=EQbaseLine;
        return d;
    }

    public String getEquation() {
        return EQ;
    }

    @Override
    public double paint(double c, double r, Graphics2D g) {
        return HE.paint((int)c, (int)r, G);
    }
}
