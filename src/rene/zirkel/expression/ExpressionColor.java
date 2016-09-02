/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.expression;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
public class ExpressionColor {

    private Construction C;
    private ConstructionObject O;
    private Expression R_EX=null, G_EX=null, B_EX=null;

    public ExpressionColor(Construction c, ConstructionObject o) {
        C=c;
        O=o;
    }

    public Color getColor() {
        if (R_EX==null) {
            return null;
        }
        try {
            int r=(int) R_EX.getValue();
            int g=(int) G_EX.getValue();
            int b=(int) B_EX.getValue();
            return new Color(r, g, b);
        } catch (Exception ex) {
            return null;
        }
    }

    public void setColor(Color col) {
        R_EX=null;
        G_EX=null;
        B_EX=null;
        if (col!=null) {
            R_EX=new Expression(""+col.getRed(), C, O);
            G_EX=new Expression(""+col.getGreen(), C, O);
            B_EX=new Expression(""+col.getBlue(), C, O);
        }
    }

    public void setColor(int red, int green, int blue) {
        R_EX=null;
        G_EX=null;
        B_EX=null;
        R_EX=new Expression(""+red, C, O);
        G_EX=new Expression(""+green, C, O);
        B_EX=new Expression(""+blue, C, O);
    }

    public void setColor(String red, String green, String blue) {
        R_EX=null;
        G_EX=null;
        B_EX=null;
        R_EX=new Expression(red, C, O);
        G_EX=new Expression(green, C, O);
        B_EX=new Expression(blue, C, O);
    }

    public String getRedExpression() {
        if (R_EX==null) {
            return "";
        }
        return R_EX.toString();
    }
    public String getGreenExpression() {
        if (G_EX==null) {
            return "";
        }
        return G_EX.toString();
    }
    public String getBlueExpression() {
        if (B_EX==null) {
            return "";
        }
        return B_EX.toString();
    }

    public void setRed(String s) throws ConstructionException {
        Expression red=new Expression(s, C, O);
        if (red.isValid()) {
            R_EX=null;
            R_EX=red;
            if (G_EX==null) G_EX=new Expression("0", C, O);
            if (B_EX==null) B_EX=new Expression("0", C, O);
        } else {
            throw new ConstructionException(red.getErrorText());
        }
    }

    public void setGreen(String s) throws ConstructionException {
        Expression green=new Expression(s, C, O);
        if (green.isValid()) {
            G_EX=null;
            G_EX=green;
            if (R_EX==null) R_EX=new Expression("0", C, O);
            if (B_EX==null) B_EX=new Expression("0", C, O);
        } else {
            throw new ConstructionException(green.getErrorText());
        }
    }

    public void setBlue(String s) throws ConstructionException {
        Expression blue=new Expression(s, C, O);
        if (blue.isValid()) {
            B_EX=null;
            B_EX=blue;
            if (G_EX==null) G_EX=new Expression("0", C, O);
            if (R_EX==null) R_EX=new Expression("0", C, O);
        } else {
            throw new ConstructionException(blue.getErrorText());
        }
    }

    public int getRed() {
        if (R_EX==null) {
            return 0;
        }
        try {
            return (int) R_EX.getValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    public int getGreen() {
        if (G_EX==null) {
            return 0;
        }
        try {
            return (int) G_EX.getValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    public int getBlue() {
        if (B_EX==null) {
            return 0;
        }
        try {
            return (int) B_EX.getValue();
        } catch (Exception ex) {
            return 0;
        }
    }
}
