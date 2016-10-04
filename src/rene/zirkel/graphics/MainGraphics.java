/* 

Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

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
package rene.zirkel.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import rene.gui.Global;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import ui.de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import ui.de.erichseifert.vectorgraphics2d.SVGGraphics2D;
import ui.de.erichseifert.vectorgraphics2d.VectorGraphics2D;

import java.awt.geom.Path2D;
import rene.zirkel.ZirkelCanvas;

public class MainGraphics extends MyGraphics {

    Graphics2D G=null;
    BasicStroke Thin, Normal, Thick, SuperThick, DCross, DCrossNormal;
//    AlphaComposite C, CO;
    ZirkelCanvas ZC=null;

    // Constructeur appelé par le paint de ZirkelCanvas ainsi que pour les exports :
    public MainGraphics(final Graphics2D g, ZirkelCanvas zc) {
        G=g;
        ZC=zc;
        G.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        G.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        G.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        G.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        G.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        if ((G instanceof EPSGraphics2D)||(G instanceof SVGGraphics2D)) {
            VectorGraphics2D vg2D=(VectorGraphics2D) G;
            vg2D.setFontRendering(VectorGraphics2D.FontRendering.VECTORS);
        }


        setDefaultLineSize(ZC.lineSize());
        setDefaultFont(ZC.fontSize(), Global.getParameter("font.large", false), Global.getParameter("font.bold", false));
    }

    @Override
    public void setColor(final Color c) {
        G.setColor(c);
    }

    @Override
    public void setColor(final ConstructionObject o) {
        G.setStroke(Normal);
        Color Col, LightCol, BrighterCol, BrighterLightCol;
        // it's a personnal color :
        if (o.getSpecialColor()!=null) {
            final int i=o.getConditionalColor();
            if (i!=-1) {
                Col=ZirkelFrame.Colors[i];
                LightCol=ZirkelFrame.LightColors[i];
                BrighterCol=ZirkelFrame.BrighterColors[i];
                BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
            } else {
                final double lambda=0.4;
                Col=o.getSpecialColor();

//                                System.out.println(o.getName()+" : "+Col);

                final int r=(int) (255*(1-lambda)+Col.getRed()*lambda);
                final int g=(int) (255*(1-lambda)+Col.getGreen()
                        *lambda);
                final int b=(int) (255*(1-lambda)+Col.getBlue()
                        *lambda);
                LightCol=new Color(r, g, b);
                BrighterCol=Col.brighter();
                BrighterLightCol=LightCol.brighter();
            }
        } else {
            final int i=o.getColorIndex();
            Col=ZirkelFrame.Colors[i];
            LightCol=ZirkelFrame.LightColors[i];
            BrighterCol=ZirkelFrame.BrighterColors[i];
            BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
        }
        if (o.isJobTarget()) {
            setColor(ZirkelFrame.TargetColor);
        } else if (o.indicated()) {
            setColor(ZirkelFrame.IndicateColor);
        } else if (o.selected()) {
            setColor(ZirkelFrame.SelectColor);
        } else {
            if (o.getColorType()==ConstructionObject.THIN) {
                if (o.isHidden()) {
                    setColor(BrighterLightCol);
                } else {
                    setColor(LightCol);
                }
            } else {
                if (o.isHidden()) {
                    setColor(BrighterCol);
                } else {
                    setColor(Col);
                }
            }
        }
        if (o.getColorType()==ConstructionObject.THIN) {
            G.setStroke(Thin);
        } else if (o.getColorType()==ConstructionObject.THICK) {
            G.setStroke(Thick);
        } else {
            G.setStroke(Normal);
        }
    }

    @Override
    public void clearRect(final int x, final int y, final int w, final int h,
            final Color c) {
        G.setColor(c);
        G.fillRect(x, y, w, h);
    }

    @Override
    public void setFillColor(final ConstructionObject o) {
        G.setStroke(Normal);
        if (o.isJobTarget()) {
            setColor(ZirkelFrame.TargetColor);
        } else if ((o instanceof PointObject)&&o.indicated()) {
            setColor(ZirkelFrame.IndicateColor);
        } else {
            Color Col, LightCol, BrighterCol, BrighterLightCol;
            // it's a personnal color :
            if (o.getSpecialColor()!=null) {
                final int i=o.getConditionalColor();
                if (i!=-1) {
                    Col=ZirkelFrame.Colors[i];
                    LightCol=ZirkelFrame.LightColors[i];
                    BrighterCol=ZirkelFrame.BrighterColors[i];
                    BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
                } else {
                    final double lambda=0.4;
                    Col=o.getSpecialColor();
                    final int r=(int) (255*(1-lambda)+Col.getRed()
                            *lambda);
                    final int g=(int) (255*(1-lambda)+Col.getGreen()
                            *lambda);
                    final int b=(int) (255*(1-lambda)+Col.getBlue()
                            *lambda);
                    LightCol=new Color(r, g, b);
                    BrighterCol=Col.brighter();
                    BrighterLightCol=LightCol.brighter();
                }
            } else {
                final int i=o.getColorIndex();
                Col=ZirkelFrame.Colors[i];
                LightCol=ZirkelFrame.LightColors[i];
                BrighterCol=ZirkelFrame.BrighterColors[i];
                BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
            }
            if (o.getColorType()!=ConstructionObject.THICK) {
                if (o.isHidden()) {
                    setColor(BrighterLightCol);
                } else {
                    setColor(LightCol);
                }
            } else {
                if (o.isHidden()) {
                    setColor(BrighterCol);
                } else {
                    setColor(Col);
                }
            }
            if (o.getColorType()==ConstructionObject.THIN) {
                G.setStroke(Thin);
            } else if (o.getColorType()==ConstructionObject.THICK) {
                G.setStroke(Thick);
            } else {
                G.setStroke(Normal);
            }
        }
    }

    @Override
    public void setLabelColor(final ConstructionObject o) {
        if (o.labelSelected()) {
            setColor(ZirkelFrame.SelectColor);
        } else if (o.isFilled()) {
            final int type=o.getColorType();
            o.setColorType(ConstructionObject.NORMAL);
            setColor(o);
            o.setColorType(type);
        } else {
            setColor(o);
        }
    }

    @Override
    public void drawRect(final double x, final double y, final double w,
            final double h) {
        if (test(x)||test(y)||test(x+w)||test(y+h)) {
            return;
        }
        G.setStroke(Normal);
        G.draw(new Rectangle2D.Double(x, y, w, h));
    }

    public void drawMarkerRect(final double x, final double y, final double w,
            final double h) {
        if (test(x)||test(y)||test(x+w)||test(y+h)) {
            return;
        }
        G.setColor(ZirkelFrame.IndicateColor);
        G.setStroke(SuperThick);
        G.draw(new Rectangle2D.Double(x, y, w, h));
        G.setStroke(Normal);
    }

    @Override
    public void drawLine(final double x, final double y, final double x1,
            final double y1) {
        if (test(x)||test(y)||test(x1)||test(y1)) {
            return;
        }
        G.setStroke(Normal);
        G.draw(new Line2D.Double(x, y, x1, y1));
    }

    @Override
    public void drawThickLine(final double x, final double y, final double x1,
            final double y1) {
        if (test(x)||test(y)||test(x1)||test(y1)) {
            return;
        }
        G.setStroke(Thick);
        G.draw(new Line2D.Double(x, y, x1, y1));
        G.setStroke(Normal);
    }

    public void drawMarkerLine(final double x, final double y, final double x1,
            final double y1) {
        if (test(x)||test(y)||test(x1)||test(y1)) {
            return;
        }
        G.setColor(ZirkelFrame.IndicateColor);
        G.setStroke(SuperThick);
        G.draw(new Line2D.Double(x, y, x1, y1));
        G.setStroke(Normal);
    }

    @Override
    public void drawLine(final double x, final double y, final double x1,
            final double y1, final ConstructionObject o) {
        if (test(x)||test(y)||test(x1)||test(y1)) {
            return;
        }
        G.draw(new Line2D.Double(x, y, x1, y1));
    }

    public boolean test(final double x) {
        return Math.abs(x)>1e5;
    }

    @Override
    public void drawArc(final double x, final double y, final double w,
            final double h, final double a, final double b) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        }
        G.setStroke(Normal);
        G.draw(new Arc2D.Double(x, y, w, h, a, b, Arc2D.OPEN));
    }

    @Override
    public void drawArc(final double x, final double y, final double w,
            final double h, final double a, final double b,
            final ConstructionObject o) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        } else {
            G.draw(new Arc2D.Double(x, y, w, h, a, b, Arc2D.OPEN));
        }
    }

    @Override
    public FontMetrics getFontMetrics() {
        return G.getFontMetrics();
    }
    private MyFormula TXF=null;

    public void setTeXEqn(final String s) {
        if (ZC==null) {
            return;
        }
        if (TXF==null) {
            try {
                // Si la librairie JLaTeXMath est présente :
                TXF=new MyTeXFormula(ZC, G);
            } catch (Error e) {
                // Sinon on passe au bon vieux HotEqn :
                TXF=new MyHotEqnFormula(ZC, G);
            }
        }
        TXF.setEquation(s);
    }

    public double paintTeXEqn(double c, double r) {
        if (TXF==null) {
            return 0;
        }
        G.setStroke(Normal);
        return TXF.paint(c, r, G);
    }

    public double drawStringExtended(String s, final double x, final double y) {
        Font myFont=G.getFont();
        double w=0;
        boolean isTeX=false;
        String[] strs=computeString(s);
        // Alternance texte-formule. La première boucle correspond à du texte,
        // la deuxième à une formule TeX, troisième à du texte, etc... :
        for (String mystr:strs){
            if (isTeX){
                setTeXEqn(mystr);
                paintTeXEqn(x+w, y-TXF.getEquationAscent()+StrAsc);
                w+=TXF.getEquationWidth();
            }else{
                G.setFont(myFont);
                drawString(mystr, x+w, y+StrAsc);
                w+=getFontMetrics().getStringBounds(mystr, G).getWidth();
            }
            isTeX=!isTeX;
        }
        return StrH;
    }

    
    private double StrH=0, StrW=0, StrAsc=0;
    private String Str="";
    private String[] Strs;

    public String[] computeString(String s){
        if (s.equals(Str)) {
            return Strs;
        }
        Str=s;
        Strs=s.split("\\$");
        Font myFont=G.getFont();
        StrAsc=0;
        StrH=0;
        StrW=0;
        boolean isTeX=false;
        // Alternance texte-formule :
        for (String mystr:Strs){
            if (isTeX){
                setTeXEqn(mystr);
                StrH=Math.max(StrH, TXF.getEquationHeight());
                StrW+=TXF.getEquationWidth();
                StrAsc= Math.max(StrAsc, TXF.getEquationAscent());
            }else{
                G.setFont(myFont);
                StrH=Math.max(StrH, getFontMetrics().getStringBounds(mystr, G).getHeight());
                StrW+=getFontMetrics().getStringBounds(mystr, G).getWidth();
                StrAsc=Math.max(StrAsc, getFontMetrics().getLineMetrics(mystr, G).getAscent());
            }
            isTeX=!isTeX;
        }
        return Strs;
    }

    public double getW() {
        return StrW;
    }

    @Override
    public double stringWidth(final String s) {
        computeString(s);
        return StrW;
    }

    @Override
    public double stringHeight(final String s) {
        computeString(s);
        return StrH;
    }


    @Override
    public double stringAscent(final String s) {
        computeString(s);
        return StrAsc;
    }

    

    @Override
    public void drawString(final String s, final double x, final double y) {
        if (test(x)||test(y)) {
            return;
        }
        G.drawString(s, (float) x, (float) y);
//        G.drawString(AngleObject.translateToUnicode("éèéééer"), (float) x, (float) y);
    }

    @Override
    public void drawOval(final double x, final double y, final double w,
            final double h) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        }
        G.setStroke(Normal);
        G.draw(new Ellipse2D.Double(x, y, w, h));
    }

    @Override
    public void drawOval(final double x, final double y, final double w,
            final double h, final ConstructionObject o) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        } else {
            G.draw(new Ellipse2D.Double(x, y, w, h));
        }
    }

    @Override
    public void drawCircle(final double x, final double y, final double r,
            final ConstructionObject o) {
        if (r>10*(W+H)) {
            drawLargeCircleArc(x, y, r, 0, 360);
        } else {
            G.draw(new Ellipse2D.Double(x-r, y-r, 2*r, 2*r));
        }
    }

    public void drawMarkerArc(final double x, final double y, final double r,
            final double a, final double b) {
        if (test(x)||test(y)||test(r)) {
            return;
        }
        G.setColor(ZirkelFrame.IndicateColor);
        G.setStroke(SuperThick);
        G.draw(new Arc2D.Double(x-r, y-r, 2*r, 2*r, a, b, Arc2D.OPEN));
    }

    @Override
    public void drawCircleArc(final double x, final double y, final double r,
            final double a, final double b, final ConstructionObject o) {
        if (r>10*(W+H)) {
            drawLargeCircleArc(x, y, r, a, b);
        } else {
            G.draw(new Arc2D.Double(x-r, y-r, 2*r, 2*r, a, b,
                    Arc2D.OPEN));
        }
    }

    void drawLargeCircleArc(final double x, final double y, final double r,
            final double a, final double b) {
        if (r>1E10) {
            return;
        }
        final double dw=Math.sqrt((W+H)/r/10);
        double w=a;
        double x0=x+r*Math.cos(w/180*Math.PI);
        double y0=y-r*Math.sin(w/180*Math.PI);
        w=w+dw;
        while (w<a+b+dw) {
            if (w>a+b) {
                w=a+b;
            }
            final double x1=x+r*Math.cos(w/180*Math.PI);
            final double y1=y-r*Math.sin(w/180*Math.PI);
            final double dx=(x0+x1)/2, dy=(y0+y1)/2;
            if (Math.sqrt(dx*dx+dy*dy)<=10*(W+H)) {
                G.draw(new Line2D.Double(x0, y0, x1, y1));
            }
            x0=x1;
            y0=y1;
            w+=dw;
        }
    }

    @Override
    public void fillRect(final double x, final double y, final double w,
            final double h, final boolean outline, final boolean transparent,
            final ConstructionObject o) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        }
        setFillColor(o);
        Color oldC=G.getColor();
        int alpha=(transparent&&!o.isSolid())?128:255;
        Color c=new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), alpha);
        G.setColor(c);
        G.fill(new Rectangle2D.Double(x, y, w, h));
        G.setColor(oldC);
        if (outline) {
            setColor(o);
            G.draw(new Rectangle2D.Double(x, y, w, h));
        }
    }

    @Override
    public void fillOval(final double x, final double y, final double w,
            final double h, final boolean outline, final boolean transparent,
            final ConstructionObject o) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        }
        if (o.getColorType()!=ConstructionObject.INVISIBLE) {
            setFillColor(o);
            Color oldC=G.getColor();
            int alpha=(transparent&&!o.isSolid())?128:255;
            Color c=new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), alpha);
            G.setColor(c);
            try {
                G.fill(new Ellipse2D.Double(x, y, w, h));
            } catch (final Exception e) {
            }
            G.setColor(oldC);
        }
        if (outline) {
            setColor(o);
            drawOval(x, y, w, h);
        }
    }

    @Override
    public void fillArc(final double x, final double y, final double w,
            final double h, final double a, final double b,
            final boolean outline, final boolean transparent,
            final boolean arcb, final ConstructionObject o) {
        if (test(x)||test(y)||test(w)||test(h)) {
            return;
        }
        setFillColor(o);
        Color oldC=G.getColor();
        int alpha=(transparent&&!o.isSolid())?128:255;
        Color c=new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), alpha);
        G.setColor(c);
        final Arc2D arc=new Arc2D.Double(x, y, w, h, a, b, arcb?Arc2D.PIE
                :Arc2D.CHORD);
        G.fill(arc);
        G.setColor(oldC);
        if (outline) {
            setColor(o);
            arc.setArcType(Arc2D.OPEN);
            G.setStroke(Normal);
            G.draw(arc);
        }
    }
    int xx[]=new int[64], yy[]=new int[64];

    @Override
    public void fillPolygon(final double x[], final double y[], final int n,
            final boolean outline, final boolean transparent,
            final ConstructionObject o) {
        if (n<1) return;

        Path2D path=new Path2D.Double();
        path.moveTo(x[0], y[0]);
        for (int i=1;i<n;i++) {
            path.lineTo(x[i], y[i]);
        }
        path.lineTo(x[0], y[0]);

        if (o.getColorType()!=ConstructionObject.INVISIBLE) {
            setFillColor(o);
            Color oldC=G.getColor();
            int alpha=(transparent&&!o.isSolid())?128:255;
            Color c=new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), alpha);
            G.setColor(c);
            G.fill(path);
            G.setColor(oldC);
        }  
        if (outline) {
            setColor(o);
            G.setStroke(Normal);
            G.draw(path);
        }
    }

    // Uniquement appelée par AreaObject :
    @Override
    public void fillPolygon(final double x[], final double y[], final int n,
            final ConstructionObject o) {
        if (n<1) return;

        Path2D path=new Path2D.Double();
        path.moveTo(x[0], y[0]);
        for (int i=1;i<n;i++) {
            path.lineTo(x[i], y[i]);
        }
        path.lineTo(x[0], y[0]);

        if (o.isFilled()) {
            // Petite modif de code. On passe aux couleurs avec transparence plutôt qu'utiliser
            // le setComposite :
            setFillColor(o);
            Color oldC=G.getColor();
            int alpha=((o.getColorType()!=ConstructionObject.THICK)&&(!o.isSolid()))?128:255;
            Color c=new Color(oldC.getRed(), oldC.getGreen(), oldC.getBlue(), alpha);
            G.setColor(c);
            G.fill(path);
            G.setColor(oldC);
        }
        if (!o.isFilled()||o.indicated()||o.selected()
                ||o.getColorType()==ConstructionObject.NORMAL) {
            setColor(o);
            if (o.indicated()||o.selected()) {
                G.setStroke(Normal);
            } else {
                if (o.getColorType()==ConstructionObject.THIN) {
                    G.setStroke(Thin);
                } else if (o.getColorType()==ConstructionObject.THICK) {
                    G.setStroke(Thick);
                } else {
                    G.setStroke(Normal);
                }
            }
            G.draw(path);
        }

    }

    @Override
    public void drawImage(final Image i, final int x, final int y,
            final ImageObserver o) {
        G.drawImage(i, x, y, o);
    }

    @Override
    public void drawImage(final Image i, final int x, final int y, final int w,
            final int h, final ImageObserver o) {
        G.drawImage(i, x, y, w, h, o);
    }


    FontStruct FS=new FontStruct();

    @Override
    public void setDefaultLineSize(double graphicsLineSize) {
        final float dash[]={(float) (graphicsLineSize*5.0), (float) (graphicsLineSize*5.0)};
        Thin=new BasicStroke((float) (graphicsLineSize), BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Normal=new BasicStroke((float) (graphicsLineSize), BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        Thick=new BasicStroke((float) (graphicsLineSize*2.0), BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        DCross=new BasicStroke((float) (graphicsLineSize*3.0),
                BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        DCrossNormal=new BasicStroke((float) (graphicsLineSize*1.5),
                BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
        SuperThick=new BasicStroke((float) (20), BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
    }

    @Override
    public void setFont(double h, final boolean bold) {
        Font f=FS.getFont(h, bold);
        if (f!=null) {
            G.setFont(f);
        } else {
            if (G instanceof VectorGraphics2D) {
                f=new Font("Arial",
                        bold?Font.BOLD:Font.PLAIN, (int)h);
                f=f.deriveFont((float)h);
            } else {
                f=new Font(Global.getParameter("font.name", "dialog"),
                        bold?Font.BOLD:Font.PLAIN, (int)h);
                f=f.deriveFont((float)h);
            }

//            f=new Font(Global.getParameter("font.name", "dialog"),
//                    bold?Font.BOLD:Font.PLAIN, h);
            FS.storeFont(h, bold, f);
            G.setFont(f);
        }
    }
    double fsize;
    boolean flarge, fbold;
    int ffactor=Global.getParameter("ffactor", 130);

    @Override
    public void setDefaultFont(double h, final boolean large,
            final boolean bold) {
        ffactor=Global.getParameter("ffactor", 130);
        fsize=h;
        flarge=large;
        fbold=bold;
        setFont(large, bold);
    }

    @Override
    public void setFont(final boolean large, final boolean bold) {

        double size=fsize;
        // int size=eric.JGlobals.FixFontSize(fsize);

        if (large) {
            size=size*ffactor/100;
        }
        if (flarge) {
            size=size*ffactor/100;
        }
        setFont(size, bold||fbold);
    }

    @Override
    public void drawImage(final Image i, final double x, final double y,
            final double x1, final double y1, final double x2, final double y2,
            final ImageObserver o) {
        try {
            final int w=i.getWidth(o), h=i.getHeight(o);
            final AffineTransform AT=new AffineTransform((x1-x)/w,
                    (y1-y)/w, (x2-x)/h, (y2-y)/h, x, y);
            G.drawImage(i, AT, o);
        } catch (final Exception e) {
        }
    }

    @Override
    public Graphics getGraphics() {
        return G;
    }


    

    
    @Override
    public void fillOval(final double x, final double y, final double w,
            final double h, final Color WithColor) {
        try {
            G.setColor(WithColor);
            G.fill(new Ellipse2D.Double(x, y, w, h));
        } catch (final Exception e) {
        }
    }

    @Override
    public void fillRect(final double x, final double y, final double w,
            final double h, final Color WithColor) {
        try {
            G.setColor(WithColor);
            G.fill(new Rectangle2D.Double(x, y, w, h));
        } catch (final Exception e) {
        }
    }

    @Override
    public void drawDiamond(final double x, final double y, final double w,
            final boolean isThick, final ConstructionObject o) {
        AffineTransform aT=G.getTransform();
        Rectangle2D r=new Rectangle2D.Double(x, y, w, w);
        AffineTransform rotate45=AffineTransform.getRotateInstance(Math.PI/4, x+w/2, y+w/2);
        G.transform(rotate45);
        if (isThick) {
            setColor(o);
        } else {
            G.setColor(new Color(250, 250, 250));
        }
        G.fill(r);
        if (!isThick) {
            setColor(o);
            G.draw(r);
        }
        G.setTransform(aT);

//        final double dw=w/2;
//        final int dx[]=new int[4], dy[]=new int[4];
//        dx[0]=(int) (x+dw);
//        dy[0]=(int) (y);
//        dx[1]=(int) (x+w);
//        dy[1]=(int) (y+dw);
//        dx[2]=(int) (x+dw);
//        dy[2]=(int) (y+w);
//        dx[3]=(int) (x);
//        dy[3]=(int) (y+dw);
//        if (isThick) {
//            setColor(o);
//        } else {
//            G.setColor(new Color(250, 250, 250));
//        }
//        G.fillPolygon(dx, dy, 4);
//        if (!isThick) {
//            setColor(o);
//            G.drawPolygon(dx, dy, 4);
//        }
    }

    @Override
    public void drawDcross(final double x, final double y, final double w,
            final boolean isThick, final ConstructionObject o) {
        final double x1=x+w, y1=y+w;
        setColor(o);
        if (isThick) {
            G.setStroke(DCross);
        } else {
            G.setStroke(DCrossNormal);
        }
        G.draw(new Line2D.Double(x, y, x1, y1));
        G.draw(new Line2D.Double(x, y1, x1, y));
        G.setStroke(Normal);
    }

    @Override
    public void setAntialiasing(final boolean bool) {
        if (bool) {
            G.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
//             G.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            G.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
//             G.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
    }

    @Override
    public void drawAxisLine(final double x, final double y, final double x1,
            final double y1, float thickness) {
        if (test(x)||test(y)||test(x1)||test(y1)) {
            return;
        }

//        G.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
//                RenderingHints.VALUE_STROKE_NORMALIZE);
//        G.setStroke(new BasicStroke(thickness));
        G.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        G.draw(new Line2D.Double(x, y, x1, y1));
        G.setStroke(Normal);
//        G.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
//                RenderingHints.VALUE_STROKE_PURE);
    }
}
