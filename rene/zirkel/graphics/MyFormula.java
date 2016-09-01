/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rene.zirkel.graphics;

import java.awt.Graphics2D;

/**
 *
 * @author erichake
 */
public abstract class MyFormula {

    protected float EQwidth=0f, EQheight=0f;
    protected float EQbaseLine=0f;

    public abstract void setEquation(String s);
    public abstract double getEquationWidth();
    public abstract double getEquationHeight();
    public abstract double getEquationAscent();
    public abstract String getEquation();
    public abstract double paint(double c, double r, Graphics2D g);
    
}
