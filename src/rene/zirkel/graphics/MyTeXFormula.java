/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.graphics;

import java.awt.Graphics2D;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.StrutBox;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXEnvironment;
import org.scilab.forge.jlatexmath.TeXFormula;
import rene.zirkel.ZirkelCanvas;
import ui.de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import ui.de.erichseifert.vectorgraphics2d.VectorGraphics2D.FontRendering;

/**
 *
 * @author erichake
 */
public class MyTeXFormula extends MyFormula {

    private TeXFormula JTeX=null;
    private String EQ="";
    private ZirkelCanvas ZC;
    private Graphics2D G=null;
    private boolean parseError=false;

    public MyTeXFormula(ZirkelCanvas zc, Graphics2D g) {
        ZC=zc;
        G=g;
        try {
            JTeX=new TeXFormula();
        } catch (Exception e) {
            JTeX=null;
        }
    }

    public MyTeXIcon createTeXIcon(int style, float size) {
        TeXEnvironment te=new TeXEnvironment(style, new DefaultTeXFont(size));
        Box box;
        if (JTeX.root==null) {
            box=new StrutBox(0, 0, 0, 0);
        } else {
            box=JTeX.root.createBox(te);
        }
        MyTeXIcon ti=new MyTeXIcon(box, size);
        ti.isColored=te.isColored;
        return ti;
    }

    public void setEquation(String s) {
        EQ=s;
        try {
            JTeX.setLaTeX(s);
            MyTeXIcon icon=createTeXIcon(TeXConstants.STYLE_DISPLAY, (float) ZC.fontSize());
            EQwidth=icon.getTrueIconWidth();
            EQheight=icon.getTrueIconHeight();
            EQbaseLine=icon.getBaseLine();
            parseError=false;
        } catch (Exception e) {
            EQwidth=0;
            EQheight=0;
            EQbaseLine=0;
            parseError=true;
            System.out.println("jlatexmath.ParseException");
        }
    }

    public double getEquationWidth() {
        return EQwidth;
    }

    public double getEquationHeight() {
        return EQheight;
    }

    public double getEquationAscent() {
        double d=EQheight*EQbaseLine;
        return d;
    }

    public String getEquation() {
        return EQ;
    }

    public double paint(double c, double r, Graphics2D g) {
        if (parseError) {
            return 0;
        }
        float col=(float) c;
        float row=(float) r;
        JTeX.setColor(g.getColor());
        MyTeXIcon icon=createTeXIcon(org.scilab.forge.jlatexmath.TeXConstants.STYLE_DISPLAY, (float) ZC.fontSize());
        if (g instanceof VectorGraphics2D) {
            VectorGraphics2D vg2d=(VectorGraphics2D) g;
            FontRendering oldFontRendering=vg2d.getFontRendering();
            vg2d.setFontRendering(VectorGraphics2D.FontRendering.VECTORS);
            icon.paintIcon(null, g, col, row);
            vg2d.setFontRendering(oldFontRendering);
        } else {
            icon.paintIcon(null, g, col, row);
        }
        return icon.getTrueIconHeight();
    }
}
