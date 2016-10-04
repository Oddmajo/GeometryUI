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
package rene.zirkel.objects;

// file: ConstructionObject.java
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import rene.lister.Element;
import rene.util.FileName;
import rene.util.MyVector;
import rene.util.sort.SortObject;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.construction.DepList;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.expression.ExpressionString;
import rene.zirkel.expression.NoValueException;
import rene.zirkel.graphics.MyGraphics;

class Conditional implements Cloneable {

    String Tag;
    Expression Expr;

    public Conditional(final String t, final Expression e) {
        Tag=t;
        Expr=e;
    }

    public String getTag() {
        return Tag;
    }

    public Expression getExpression() {
        return Expr;
    }

    public void setExpression(final Expression e) {
        Expr=e;
    }
}

public class ConstructionObject implements Cloneable, SortObject, Element {

    /** object color and static default color */
    protected int ColorType=0;
    public static final int NORMAL=0, THICK=1, THIN=2, INVISIBLE=3;
    protected Color C=Color.black;
    protected ExpressionColor SpecialColor=null;
    protected int ColorIndex=0;
    protected boolean Tracked=false;
    protected boolean Selected=false; // is selected (highlight red!)
    protected boolean StrongSelected=false; // to highlight the object even
    // more
    protected boolean Indicated=false; // mouse is over it (highlight darker!)
    protected boolean ShowIndicatedName=false; // shows the name (or name and
    // value)
    protected boolean LabelSelected=false; // label is selected (highlight
    // red!)
    protected boolean Valid=true; // e.g. for intersections
    protected boolean Hidden=false; // not visible or lighter
    protected boolean SuperHidden=false; // not visible at all
    protected boolean BreakHide=false; // Hide due to hiding break
    protected boolean ShowName=false; // shows the name (or name and value)
    protected boolean ShowValue=false; // shows the value (or name and value)
    protected boolean Selectable=true; // may be selected
    protected String Name="Generic";
    protected static int Count=1; // To generate a unique name
    protected String Text="???"; // Description (human readable)
    private int XOffset=0, YOffset=0; // Deprecated INT offset (translated
    // to double)
    protected double XcOffset=0.0, YcOffset=0.0; // Offsets for the text
    // display
    protected boolean KeepClose; // Keep the text display close to the object
    protected boolean DisplaysText=false; // Does this object display text?
    protected double TX1, TY1, TX2, TY2; // text rectangle.
    protected Construction Cn; // Construction that this Object belongs to
    protected boolean MainParameter, Parameter; // for macros and jobs
    protected boolean Target; // for macros and jobs
    public boolean Flag, RekFlag, IsDrawn, HasZ; // for various processing
    // purposes
    public int Scratch; // for various processing purposes
    protected boolean Keep=false; // for job parameters.
    protected boolean JobTarget=false; // for job targets
    protected ConstructionObject Translation;
    protected boolean Back=false; // draws in the background (first round)
    protected boolean Obtuse=true; // for angles and circles between two
    // points.
    protected boolean Break=false; // Break point in front of this object?
    protected boolean HideBreak=false; // Hide construction before this break!
    protected boolean Solid=false; // Not transparent?
    static Count N=new Count();
    protected boolean inConstruction; // Is in the construction (or deleted)
    protected String Unit=""; // Use a unit display
    protected boolean Bold=false; // Use a bold font for this item
    protected boolean Large=false; // Use a large font for this item
    protected Vector Conditionals; // For conditional formatting
    protected int NCount=0; // Unique number
    protected boolean gotNCount=false; // Have already a number (from load
    // file)
    public boolean NeedsRecompute=true;
    protected boolean inMultipleSelection=false;

    final public static int NON_DP=0,DP_LINE=1,DP_SEGMENT=2,DP_CIRCLE=3;
    private int dp_mode=0; // 0 for non-dp objects 1 for lines, 2 for segments



    public void setInMultipleSelection(boolean b) {
        inMultipleSelection=b;
    }

    public boolean isInMultipleSelection() {
        return inMultipleSelection;
    }

    public void copyProperties(final ConstructionObject origin) {
        Tracked=origin.Tracked;
        Selected=origin.Selected;
        StrongSelected=origin.StrongSelected;
        Indicated=origin.Indicated;
        ShowIndicatedName=origin.ShowIndicatedName;
        LabelSelected=origin.LabelSelected;
        Valid=origin.Valid;
        Hidden=origin.Hidden;
        SuperHidden=origin.SuperHidden;
        BreakHide=origin.BreakHide;
        ShowName=origin.ShowName;
        ShowValue=origin.ShowValue;
        Selectable=origin.Selectable;
        ColorType=origin.ColorType;
        C=origin.C;
        ColorIndex=origin.ColorIndex;
        if (origin.Conditionals!=null) {
            Conditionals=(Vector) origin.Conditionals.clone();
        }
    }

    public boolean isGotNCount() {
        return gotNCount;
    }

    public void setGotNCount(final boolean gotNCount) {
        this.gotNCount=gotNCount;
    }

    public ConstructionObject(final Construction c) {
        Cn=c;
        inConstruction=true;
        setName();
    }

    public ConstructionObject(final Construction c, final String name) {
        Cn=c;
        inConstruction=true;
        setName(name);
    }

    public int getDPMode(){
        return dp_mode;
    }

    public void setDPMode(int i){
        dp_mode=i;
    }

    public boolean isDPObject(){
        return (dp_mode>NON_DP);
    }

    public boolean isDPLineObject(){
        return (dp_mode==DP_LINE);
    }

    public boolean isDPSegmentObject(){
        return (dp_mode==DP_SEGMENT);
    }

    public boolean isDPCircleObject(){
        return (dp_mode==DP_CIRCLE);
    }

    public boolean isDPLineOrSegmentObject(){
        return ((dp_mode==DP_LINE)||(dp_mode==DP_SEGMENT));
    }


    // tells if it's a main object which can make heavy objects to recompute :
    public boolean isDriverObject() {
        return false;
    }

    /**
     * Sets a unique name for the object. Should be overriden in all items.
     */
    public void setName() {

        if (Cn.LongNames) {
            Name=Global.name("name."+getTag())+" "+getN();
        } else {
            Name=Global.name("name.short."+getTag())+getN();
            // System.out.println("nom="+Name);
        }
    }

    public int getN() {
        return N.next();
    }

    public String getTag() {
        return "Generic";
    }

    /**
     * This is used to define macros and run them. The items of the construction
     * are translated to macro items.
     */
    public ConstructionObject getTranslation() {
        return Translation;
    }

    public void setTranslation(final ConstructionObject t) {
        Translation=t;
    }

    /**
     * Override in children! This translates the mother items.
     */
    public void translate() {
    }

    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
    }

    /**
     * Test, if this object is chosen with coordinates x,y
     */
    public boolean nearto(final int x, final int y, final ZirkelCanvas zc) {
        return false;
    }

    /**
     * Test, if this object is chosen with coordinates x,y
     */
    public boolean nearto(final int x, final int y, final boolean ignorefill,
            final ZirkelCanvas zc) {
        return nearto(x, y, zc);
    }

    /**
     * Test, if this object must be chosen with coordinates x,y, assuming it is
     * near to these coordinates.
     */
    public boolean onlynearto(final int x, final int y, final ZirkelCanvas zc) {
        return true;
    }

    public boolean isInRect(Rectangle r, ZirkelCanvas zc) {
        return false;
    }

    public boolean textcontains(final int x, final int y, final ZirkelCanvas zc) {
        if (!DisplaysText||!Valid||(isHidden()&&!zc.showHidden())) {
            return false;
        }
        return x>TX1&&x<TX2&&y>TY1&&y<TY2;
    }

    public void setTracked(final boolean flag) {
        Tracked=flag;
    }

    public void setSelected(final boolean flag) {
        Selected=flag;
        StrongSelected=false;
    }

    public void setStrongSelected(final boolean flag) {
        StrongSelected=flag;
    }

    public boolean isStrongSelected() {
        return StrongSelected;
    }

    public boolean selected() {
        return Selected||StrongSelected;
    }

    public boolean tracked() {
        return Tracked;
    }

    public void setLabelSelected(final boolean flag) {
        LabelSelected=flag;
    }

    public boolean isSelectable() {
        return valid()&&!JobTarget&&Selectable;
    }

    public void validate() {
        Valid=true;
    }

    public boolean valid() {
        return Valid;
    }

    public boolean displays(final ZirkelCanvas zc) {
        if (SuperHidden) {
            return false;
        }
        if (!zc.showHidden()
                &&(testConditional("hidden")||testConditional("superhidden"))) {
            return false;
        }
        return Valid&&!BreakHide&&(!Hidden||zc.showHidden());
    }

    public boolean indicated() {
        return Indicated;
    }

    public void setIndicated(final boolean f) {
        Indicated=f;
    }

    public void setIndicated(final boolean f, final boolean showname) {
        Indicated=f;
        ShowIndicatedName=showname;
    }

    /**
     * @return The change of the last validation for points bound to lines,
     *         which are defined later.
     */
    public double changedBy() {
        return 0.0;
    }

    /**
     * Routines to parse descriptive text in various languages.
     */
    static public String text1(final String format, final String s1) {
        final StringTokenizer t=tokenize(format);
        String s="Illegal Format";
        try {
            s=t.nextToken()+s1;
            s=s+t.nextToken();
        } catch (final Exception e) {
        }
        return s.trim();
    }

    static public String text2(final String format, final String s1,
            final String s2) {
        final StringTokenizer t=tokenize(format);
        String s="Illegal Format";
        try {
            s=t.nextToken()+s1+t.nextToken()+s2;
            s=s+t.nextToken();
        } catch (final Exception e) {
        }
        return s.trim();
    }

    static public String text3(final String format, final String s1,
            final String s2, final String s3) {
        final StringTokenizer t=tokenize(format);
        String s="Illegal Format";
        try {
            s=t.nextToken()+s1+t.nextToken()+s2+t.nextToken()+s3;
            s=s+t.nextToken();
        } catch (final Exception e) {
        }
        return s.trim();
    }

    static public String text4(final String format, final String s1,
            final String s2, final String s3, final String s4) {
        final StringTokenizer t=tokenize(format);
        String s="Illegal Format";
        try {
            s=t.nextToken()+s1+t.nextToken()+s2+t.nextToken()+s3
                    +t.nextToken()+s4;
            s=s+t.nextToken();
        } catch (final Exception e) {
        }
        return s;
    }

    static public String textAny(final String format, final String ss[]) {
        final StringTokenizer t=tokenize(format);
        String s="Illegal Format";
        try {
            s=t.nextToken();
            for (int i=0; i<ss.length; i++) {
                s=s+ss[i];
                if (i<ss.length-1) {
                    s=s+", ";
                }
            }
            s=s+t.nextToken();
        } catch (final Exception e) {
        }
	//return s;
        return s.trim();
    }

    static private StringTokenizer tokenize(String format) {
        if (format.startsWith("%")) {
            format=" "+format;
        }
        if (format.endsWith("%")) {
            format=format+" ";
        }
        return new StringTokenizer(format, "%");
    }

    public void setText(final String s, final boolean user) {
        if (!user&&Text.endsWith(" ")) {
            return;
        }
        Text=s;
    }

    public void setText(final String s) {
        setText(s, false);
    }

    public String getText() {
        return Text;
    }

    public boolean labelSelected() {
        return LabelSelected;
    }

    // public void setColor(Color c){
    // C=c;
    // }
    /**
     * Set the object color by index.
     */
    public void setColor(final int index) {
        SpecialColor=null;
        if (index<0) {
            C=Color.pink;
        } else {
            if (ColorType==THIN) {
                C=ZirkelFrame.LightColors[index];
            } else {
                C=ZirkelFrame.Colors[index];
            }
            ColorIndex=index;
        }
    }

    public String getRed() {
        if (SpecialColor==null) {
            return "";
        } else {
            return SpecialColor.getRedExpression();
        }
    }

    public String getGreen() {
        if (SpecialColor==null) {
            return "";
        } else {
            return SpecialColor.getGreenExpression();
        }
    }

    public String getBlue() {
        if (SpecialColor==null) {
            return "";
        } else {
            return SpecialColor.getBlueExpression();
        }
    }

    public void setRed(String col) throws ConstructionException {
        Expression red=new Expression(col, getConstruction(), this);
        if (red.isValid()) {
            if (SpecialColor==null) {
                SpecialColor=new ExpressionColor(Cn, this);
            }
            SpecialColor.setRed(col);
        } else {
            throw new ConstructionException(red.getErrorText());
        }
    }

    public void setGreen(String col) throws ConstructionException {
        Expression green=new Expression(col, getConstruction(), this);
        if (green.isValid()) {
            if (SpecialColor==null) {
                SpecialColor=new ExpressionColor(Cn, this);
            }
            SpecialColor.setGreen(col);
        } else {
            throw new ConstructionException(green.getErrorText());
        }
    }

    public void setBlue(String col) throws ConstructionException {
        Expression blue=new Expression(col, getConstruction(), this);
        if (blue.isValid()) {
            if (SpecialColor==null) {
                SpecialColor=new ExpressionColor(Cn, this);
            }
            SpecialColor.setBlue(col);
        } else {
            throw new ConstructionException(blue.getErrorText());
        }
    }

    /**
     * Set the object color by index.
     *
     * @param index
     * @param col
     */
    public void setColor(final int index, final ExpressionColor col) {

        if (col==null) {
            if (ColorType==THIN) {
                C=ZirkelFrame.LightColors[index];
            } else {
                C=ZirkelFrame.Colors[index];
            }
            ColorIndex=index;
            SpecialColor=null;
        } else {
            SpecialColor=col;
            C=SpecialColor.getColor();
        }
    }

    public void setColor(final int index, final Color col) {
        if (col==null) {
            if (ColorType==THIN) {
                C=ZirkelFrame.LightColors[index];
            } else {
                C=ZirkelFrame.Colors[index];
            }
            ColorIndex=index;
            SpecialColor=null;
        } else {
            SpecialColor=new ExpressionColor(Cn, this);
            SpecialColor.setColor(col);
            C=SpecialColor.getColor();
        }
    }

    /**
     * Get the current color index. If there is a conditional for the color,
     * test it and use it.
     */
    public int getColorIndex(final boolean original) {
        if (!original&&haveConditionals()) {
            for (int i=0; i<ZirkelFrame.ColorStrings.length; i++) {
                if (testConditional(ZirkelFrame.ColorStrings[i])) {
                    return i;
                }
            }
        }
        return ColorIndex;
    }

    public int getConditionalColor() {
        if (haveConditionals()) {
            for (int i=0; i<ZirkelFrame.ColorStrings.length; i++) {
                if (testConditional(ZirkelFrame.ColorStrings[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public synchronized Color getSpecialColor() {
        if (SpecialColor==null) {
            return null;
        }
        return SpecialColor.getColor();
    }

    public synchronized void setSpecialColor(final Color c) {
        if (c!=null) {
            SpecialColor=null;
            SpecialColor=new ExpressionColor(Cn, this);
            SpecialColor.setColor(c);
        }
    }

    public void setSpecialColor(String red, String green, String blue) {
        SpecialColor=null;
        SpecialColor=new ExpressionColor(Cn, this);
        SpecialColor.setColor(red, green, blue);
    }

    public int getColorIndex() {
        return getColorIndex(false);
    }

    public Color getColor() {
        Color Col, LightCol, BrighterCol, BrighterLightCol;
        // it's a personnal color :
        if (SpecialColor!=null) {
            final int i=getConditionalColor();
            if (i!=-1) {
                Col=ZirkelFrame.Colors[i];
                LightCol=ZirkelFrame.LightColors[i];
                BrighterCol=ZirkelFrame.BrighterColors[i];
                BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
            } else {
                final double lambda=0.4;
                Col=SpecialColor.getColor();
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
            final int i=getColorIndex();
            Col=ZirkelFrame.Colors[i];
            LightCol=ZirkelFrame.LightColors[i];
            BrighterCol=ZirkelFrame.BrighterColors[i];
            BrighterLightCol=ZirkelFrame.BrighterLightColors[i];
        }
        if (isJobTarget()) {
            return ZirkelFrame.TargetColor;
        } else if (selected()) {
            return ZirkelFrame.SelectColor;
        } else if (getColorType()==ConstructionObject.THIN) {
            if (isHidden()) {
                return BrighterLightCol;
            } else {
                return LightCol;
            }
        } else {
            if (isHidden()) {
                return BrighterCol;
            } else {
                return Col;
            }
        }
    }

    public boolean mustHide(final ZirkelCanvas zc) {
        if (!Valid) {
            return true;
        }
        if (ColorIndex>0&&zc.getShowColor()!=0
                &&zc.getShowColor()!=ColorIndex) {
            return true;
        }
        if (JobTarget&&Hidden) {
            return true;
        }
        return SuperHidden||testConditional("superhidden")||BreakHide
                ||zc.hides(this)||(isHidden()&&!zc.showHidden());
    }

    public void setHidden(final boolean flag) {
        Hidden=flag;
        if (!flag) {
            SuperHidden=false;
        }
    }

    public void setSuperHidden(final boolean flag) {
        SuperHidden=flag;
    }

    public void toggleHidden() {
        Hidden=!Hidden;
        if (!Hidden) {
            SuperHidden=false;
        }
    }

    public boolean isHidden(final boolean original) {
        if (!original&&testConditional("visible")) {
            return false;
        }
        if (!original
                &&(testConditional("hidden")||testConditional("superhidden"))) {
            return true;
        }
        return Hidden||SuperHidden;
    }

    public boolean isHidden() {
        return isHidden(false);
    }

    public boolean isSuperHidden(final boolean original) {
        if (!original&&testConditional("superhidden")) {
            return true;
        }
        return SuperHidden;
    }

    public boolean isSuperHidden() {
        return isSuperHidden(false);
    }

    public String getName() {
        return Name;
    }

    public void setName(String n) {
        Name="";
        if (Cn!=null) {
            ConstructionObject o=Cn.find(n);
            while (o!=null&&o!=this) {
                n=n+"*";
                o=Cn.find(n);
            }
        }
        Name=n;
    }

    public void setNameCheck(String n) throws ConstructionException {
        boolean extend=false;
        Name="";
        if (Cn!=null) {
            ConstructionObject o=Cn.find(n);
            if (o!=null&&o!=this) {
                n=n+"*";
                o=Cn.find(n);
                extend=true;
            }
        }
        Name=n;
        if (extend) {
            throw new ConstructionException(Global.name("exception.double"));
        }
    }

    public void setNameWithNumber(String n) {
        Name="";
        if (Cn!=null) {
            ConstructionObject o=Cn.find(n);
            if (o!=null) {
                while (o!=null&&o!=this) {
                    setName();
                    n=Name;
                    Name="";
                    o=Cn.find(n);
                }
                Name=n;
            } else {
                Name=n;
            }
        } else {
            Name=n;
        }
    }

    public void edit(final ZirkelCanvas zc, boolean forcevisible, boolean forcefocus) {
        eric.bar.JPropertiesBar.EditObject(this, forcevisible, forcefocus);
    }

    /**
     * Service routine to draw a string from upper left coordinates. Position is
     * at TX1, TX2.
     */
    public void drawLabel(final MyGraphics g, final String s) {
        g.drawStringExtended(s, TX1, TY1);
        TX2=TX1+g.stringWidth(s);
        TY2=TY1+g.stringHeight(s);
    }

    public void setFont(final MyGraphics g) {
        g.setFont(Large, Bold);
    }

    /**
     * Service routine to draw a string centered at x,y
     */
    public void drawCenteredLabel(final MyGraphics g, final String s,
            final ZirkelCanvas zc, final double x, final double y,
            final double xo, final double yo) {
        double ws=g.stringWidth(s), hs=g.stringHeight(s);
        TX1=zc.col(x+xo)-ws/2;
        TY1=zc.row(y+yo)-hs/2;
        g.drawStringExtended(s, TX1, TY1);
        TX2=TX1+ws;
        TY2=TY1+hs;
    }

    /**
     * Service routine to draw a string at x,y but offset into direction -vy,vx
     * so that it does not intersect the direction vx,vy. r=(vx,vy) is assumed
     * to be normalized.
     */
    public void drawLabel(final MyGraphics g, final String s,
            final ZirkelCanvas zc, final double x, final double y,
            final double vx, final double vy, final double xo, final double yo) {
        double ws=g.stringWidth(s), hs= g.stringHeight(s);
        final double w=zc.dx(ws), h=zc.dy(hs);
        final double d1=Math.abs(-w*vy+h*vx);
        final double d2=Math.abs(w*vy+h*vx);
        double d=d1;
        if (d2>d) {
            d=d2;
        }
        final double dd=zc.dy(2*zc.pointSize());
        TX1=zc.col(x-vy*(d+3*dd)/2+xo)-ws/2;
        TY1=zc.row(y+vx*(d+3*dd)/2+yo)-hs/2;
        g.drawStringExtended(s, TX1, TY1);
        TX2=TX1+ws;
        TY2=TY1+hs;
    }

    /**
     * Service routine to draw a string at x,y but offset into direction -vy,vx
     * so that it does not intersect the direction vx,vy. r=(vx,vy) is assumed
     * to be normalized.
     */
    public void drawPointLabel(final MyGraphics g, final String s,
            final ZirkelCanvas zc, final double x, final double y,
            final double vx, final double vy, final double xo, final double yo) {
        double ws=g.stringWidth(s), hs=g.stringHeight(s);
        final double w=zc.dx(ws), h=zc.dy(hs);
        final double d1=Math.abs(-w*vy+h*vx);
        final double d2=Math.abs(w*vy+h*vx);
        double d=d1;
        if (d2>d) {
            d=d2;
        }
        final double dd=zc.dy(2*zc.pointSize());
        if (Math.abs(d*vx)>h+2*dd) {
            d=Math.abs((h+2*dd)/vx);
        }
        final double xp=x-vy*d/2, yp=y+vx*d/2;
        TX1=zc.col(xp-vy*dd*1.5+xo)-ws/2;
        TY1=zc.row(yp+vx*dd*1.5+yo)-hs/2;

        g.drawStringExtended(s, TX1, TY1);
        TX2=TX1+ws;
        TY2=TY1+hs;
    }

    /**
     * Save the object in XML form.
     */
    public void save(final XmlWriter xml) {
        xml.startTagStart(getTag());
        xml.printArg("name", Name);
        if (AliasES!=null) {
            xml.printArg("alias", AliasES.toString());
        }
        xml.printArg("n", ""+NCount);
        if (ColorIndex!=0) {
            xml.printArg("color", ""+ColorIndex);
        }
        if (SpecialColor!=null) {
            xml.printArg("scolor", SpecialColor.getRedExpression()+",,"
                    +SpecialColor.getGreenExpression()+",,"+SpecialColor.getBlueExpression());
        }
        if (ColorType==THICK) {
            xml.printArg("type", "thick");
        }
        if (ColorType==THIN) {
            xml.printArg("type", "thin");
        }
        if (ColorType==INVISIBLE) {
            xml.printArg("type", "invisible");
        }
        if (SuperHidden) {
            xml.printArg("hidden", "super");
        } else if (Hidden) {
            xml.printArg("hidden", "true");
        }
        if (ShowName) {
            xml.printArg("showname", "true");
        }
        if (ShowValue) {
            xml.printArg("showvalue", "true");
        }
        if (Back) {
            xml.printArg("background", "true");
        }
        if (XcOffset!=0.0) {
            xml.printArg("xcoffset", ""+XcOffset);
        }
        if (YcOffset!=0.0) {
            xml.printArg("ycoffset", ""+YcOffset);
        }
        if (KeepClose) {
            xml.printArg("keepclose", "true");
        }
        if (Parameter) {
            xml.printArg("parameter", "true");
        }
        if (MainParameter) {
            xml.printArg("mainparameter", "true");
        }
        if (Target) {
            xml.printArg("target", "true");
        }
        if (Tracked) {
            xml.printArg("tracked", "true");
        }
        if (Break) {
            if (HideBreak) {
                xml.printArg("hidebreak", "true");
            } else {
                xml.printArg("break", "true");
            }
        }
        if (Solid&&maybeTransparent()&&isFilled()) {
            xml.printArg("solid", "true");
        }
        if (!Unit.equals("")) {
            xml.printArg("unit", Unit);
        }
        if (Bold) {
            xml.printArg("bold", "true");
        }
        if (Large) {
            xml.printArg("large", "true");
        }
        if (isDPObject()) {
            xml.printArg("dp_mode", ""+dp_mode);
        }
        if (haveConditionals()) {
            int i=0;
            final Enumeration e=Conditionals.elements();
            while (e.hasMoreElements()) {
                final Conditional c=(Conditional) e.nextElement();
                xml.printArg("ctag"+i, c.getTag());
                xml.printArg("cexpr"+i, c.getExpression().toString());
                i++;
            }
        }
        printArgs(xml);
        xml.startTagEnd();
        xml.print(Text);
        xml.endTag(getTag());
        xml.println();
    }

    public void printArgs(final XmlWriter xml) {
    }

    public void updateText() {
    }

    public void setConstruction(final Construction c) {
        Cn=c;
        inConstruction=true;
        setNameWithNumber(Name); // make sure the name is not double
    }

    public boolean showName(final boolean original) {
        if (!original&&testConditional("showname")) {
            return true;
        }
        return ShowName||(Indicated&&ShowIndicatedName);
    }

    public boolean showName() {
        return showName(false);
    }

    public void setShowName(final boolean f) {
        ShowName=f;
    }

    public boolean showValue(final boolean original) {
        if (!original&&testConditional("showvalue")) {
            return true;
        }
        return ShowValue;
    }

    public boolean showValue() {
        return showValue(false);
    }

    public void setShowValue(final boolean f) {
        ShowValue=f;
    }

    /**
     * Determine the string to be displayed onscreen.
     *
     * @return "", if there should be no display.
     */
    static String findIndice(final String s) {
        return "";
    }


    public Boolean is2DObject() {
        return false;
    }

    public Boolean isTellsSon() {
        //William Tell's son has an arrow over him
        //Only a Vector is supposed to have an arrow over him...
        return false;
    }


    public String getDisplayText() {

        // If the name of the object starts with simple letters only
        // and ends with a number, then place this number in subscript
        // and use the $-character around to send the string to HotEqn
        String name=Name.replaceAll("^([a-zA-Z]+)([0-9]+)$", "$1_{$2}");
        
        if (AliasES!=null) {
            name=AliasES.evaluate();
        } else if (name.indexOf("~")>0) {
            name=name.substring(0, name.indexOf("~"));
        }
        
        if(!name.startsWith("$")) {
            name="$"+name;
        }
        
        if (showName()) {
            if (showValue()) {
                final String value=getDisplayValue();
                if (value.equals("")) {
                    return name+"$";
                }
                
                if (name.endsWith("$") && name.length()!=1) {
                    name=name.substring(0, name.length()-1);
                }
                
                if (name.endsWith("_")&&name.length()>1) {
                    name=name.substring(0, name.length()-1);
                //} else if (!this.is2DObject()) {
                //   name=name+" = ";
                } else if (this.isTellsSon() && AliasES==null && name.length()!=1){
                    name="$\\overrightarrow{"+name.substring(1,name.length())+"}";
                }
                
                if (Unit.equals("")) {
                    return name+value+"$";
                } else {
                    return name+value+Unit+"$";
                }
            } else {
                if (name.indexOf("~")>0&&AliasES==null) {
                    name=name.substring(0, name.indexOf("~"));
                }
                if (this.isTellsSon() && AliasES==null && name.length()!=1) {
                    //System.out.println(name.length());
                    name="$\\overrightarrow{"+name.substring(1,name.length())+"}";
                }
                return name+"$";
            }
        } else if (showValue()) {
            if (Unit.equals("")) {
                return "$"+getDisplayValue()+"$";
            } else {
                return "$"+getDisplayValue()+Unit+"$";
            }
        }
        return "";
    }

    /**
     * Replace the expression in %...% by their values
     *
     * @param s
     * @param zc
     *            may be null (no direct search of object)
     * @return
     */
    public String replaceExpressions(String s) { // System.out.println(s);
        final boolean latex=s.startsWith("$");
        int searchpos=0;
        while (s.indexOf("%", searchpos)>=0) {
            final int h1=s.indexOf("%", searchpos);
            final String s1=s.substring(h1+1);
            final int h2=s1.indexOf("%");
            if (h2==0) {
                s=s.substring(0, h1)+"%"+s1.substring(1);
                searchpos+=1;
            } else if (h2>=0) {
                String var=s1.substring(0, h2);
                boolean showeq=false, forceplus=false;
                if (var.startsWith("~")) // show equation
                {
                    var=var.substring(1);
                    showeq=true;
                } else if (var.startsWith("+")) // force +
                {
                    var=var.substring(1);
                    forceplus=true;
                }
                final ConstructionObject o=getConstruction().find(var);
                // System.out.println(o+" "+var);
                String repl="";
                if (o!=null) {
                    DL.add(o);
                }
                try {
                    if (showeq) {
                        repl=o.getEquation();
                    } else {
                        if (o!=null) {
                            final double x=round(o.getValue());
                            if (forceplus&&x>=0) {
                                repl="+";
                            }
                            if (x==Math.floor(x+0.1)) {
                                repl=repl+(int) (x);
                            } else {
                                repl=repl+x;
                            }
                        } else {
                            final Expression ex=new Expression(var,
                                    getConstruction(), this);
                            if (ex.isValid()) {
                                final Enumeration e=ex.getDepList().elements();
                                while (e.hasMoreElements()) {
                                    DL.add((ConstructionObject) e.nextElement());
                                }
                            }
                            final double x=round(ex.getValue());
                            if (forceplus&&x>=0) {
                                repl="+";
                            }
                            if (x==Math.floor(x+0.1)) {
                                repl=repl+(int) (x);
                            } else {
                                repl=repl+x;
                            }
                        }
                    }
                } catch (final Exception ex) {
                    if (latex) {
                        repl="\\circ";
                    } else {
                        repl="???";
                        // ex.printStackTrace();
                    }
                }
                s=s.substring(0, h1)+repl+s1.substring(h2+1);
                searchpos=h1+repl.length();
            } else {
                break;
            }
        }
        return s;
    }

    /**
     * Add the dependencies in expressions in %...% in the string.
     *
     * @param s
     * @param zc
     *            may be null (no direct search of object)
     * @return
     */
    public void addDepInExpressions(final String s) {
        replaceExpressions(s);
    }

    /**
     * Replace the expression in %...% by their translated epressions.
     *
     * @param s
     * @param zc
     *            may be null (no direct search of object)
     * @return
     */
    public String translateExpressions(String s) { // System.out.println(s);
        s.startsWith("$");
        int searchpos=0;
        while (s.indexOf("%", searchpos)>=0) { // System.out.println(searchpos);
            final int h1=s.indexOf("%", searchpos);
            final String s1=s.substring(h1+1);
            final int h2=s1.indexOf("%");
            if (h2==0) {
                s=s.substring(0, h1)+"%"+s1.substring(1);
                searchpos+=1;
            } else if (h2>=0) {
                String var=s1.substring(0, h2);
                boolean showeq=false;
                String repl="";
                if (var.startsWith("~")) // show equation
                {
                    var=var.substring(1);
                    showeq=true;
                    repl="+";
                } else if (var.startsWith("+")) // force +
                {
                    var=var.substring(1);
                    repl="~";
                }
                ConstructionObject o=getConstruction().find(var);
                if (o!=null) {
                    o=o.getTranslation();
                    // System.out.println(o+" "+var);
                }
                if (showeq) {
                    repl=repl+o.getName();
                } else {
                    if (o!=null) {
                        repl=repl+o.getName();
                    } else {
                        final Expression ex=new Expression(var,
                                getConstruction(), this);
                        if (ex.isValid()) {
                            ex.translate();
                            repl=repl+ex.toString();
                        } else {
                            return s;
                        }
                    }
                }
                // System.out.println(repl);
                s=s.substring(0, h1)+"%"+repl+"%"
                        +s1.substring(h2+1);
                searchpos=h1+repl.length()+2;
                // System.out.println(s);
            } else {
                break;
            }
        }
        return s;
    }

    /**
     * To be overridden by the objects.
     *
     * @return The string to display the value (length, coordinates) of the
     *         object.
     */
    public String getDisplayValue() {
        return "";
    }

    public String getCDPDisplayValue() {
        return getDisplayValue();
    }

    // Only for Construction Display Panel :
    public String getCDPSizeDisplay() {
        return getCDPDisplayValue()+getUnit();
    }

    /**
     * Service function to round a value.
     */
    public double round(final double x) {
        return round(x, ZirkelCanvas.EditFactor);
    }

    public double roundDisplay(final double x) {
        return round(x, ZirkelCanvas.LengthsFactor);
    }

    public double round(final double x, final double Rounder) {
        return Math.round(x*Rounder)/Rounder;
    }

    // The following procedures are for label offset
    public void setcOffset(final double x, final double y) {
        XcOffset=x;
        YcOffset=y;
    }

    public double xcOffset() {
        return XcOffset;
    }

    public double ycOffset() {
        return YcOffset;
    }

    public void setOffset(final int x, final int y) // deprecated
    {
        XOffset=x;
        YOffset=y;
    }

    public boolean isKeepClose() {
        return KeepClose;
    }

    public void setKeepClose(final double x, final double y) // Override!
    {
        KeepClose=true;
        XcOffset=x;
        YcOffset=y;
    }

    public void setKeepClose(final boolean flag) {
        if (KeepClose&&!flag) {
            XcOffset=0;
            YcOffset=0;
        }
        KeepClose=flag;
    }

    public boolean canKeepClose() // Override!
    {
        return false;
    }

    public void translateOffset(final ZirkelCanvas zc) {
        if (XOffset!=0||YOffset!=0) {
            XcOffset=zc.dx(XOffset);
            YcOffset=zc.dy(-YOffset);
            XOffset=YOffset=0;
        }
    }

    public int getColorType(final boolean original) {
        if (!original&&testConditional("thin")) {
            return THIN;
        }
        if (!original&&testConditional("thick")) {
            return THICK;
        }
        if (!original&&testConditional("normal")) {
            return NORMAL;
        }
        return ColorType;
    }

    public int getColorType() {
        return getColorType(false);
    }

    public void setColorType(final int type) {
        ColorType=type;
        setColor(ColorIndex, SpecialColor);
    }

    public boolean isParameter() {
        return Parameter||MainParameter;
    }

    public boolean isMainParameter() {
        return MainParameter;
    }

    public void clearParameter() {
        Parameter=false;
        MainParameter=false;
    }

    public void setMainParameter(boolean b) {
        final Enumeration e=secondaryParams();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setParameter(b);
        }
        MainParameter=b;
    }

    public void setParameter(boolean b) {
        Parameter=b;
    }
    public static DepList DL=new DepList();

    /**
     * Return an enumeration of objects, that this object depends on. This
     * should usually called from children via super.
     */
    public Enumeration depending() {
        DL.reset();
        if (haveConditionals()) {
            final Enumeration e=Conditionals.elements();
            while (e.hasMoreElements()) {
                final Conditional c=(Conditional) e.nextElement();
                final Enumeration ex=c.getExpression().getDepList().elements();
                while (ex.hasMoreElements()) {
                    DL.add((ConstructionObject) ex.nextElement());
                }
            }
        }
        if (AliasES!=null) {
            AliasES.addDep(this);
        }
        return DL.elements();
    }

    /**
     * Test, if the object directly depends on another object.
     */
    public boolean dependsOn(final ConstructionObject o) {
        final Enumeration e=depending();
        while (e.hasMoreElements()) {
            final ConstructionObject o1=(ConstructionObject) e.nextElement();
            if (o1==o) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return an enumeration of objects, which can be set as secondary
     * parameters, whenever this object is selected as main parameter.
     */
    public Enumeration secondaryParams() {
        DL.reset();
        return DL.elements();
    }

    public ConstructionObject[] getDepArray() {
        depending();
        return DL.getArray();
    }

    public Enumeration depset(final ConstructionObject o1) {
        DL.add(o1);
        return DL.elements();
    }

    public Enumeration depset(final ConstructionObject o1,
            final ConstructionObject o2) {
        DL.add(o1);
        DL.add(o2);
        return DL.elements();
    }

    public Enumeration depset(final ConstructionObject o1,
            final ConstructionObject o2, final ConstructionObject o3) {
        DL.add(o1);
        DL.add(o2);
        DL.add(o3);
        return DL.elements();
    }

    public void setFlag(final boolean flag) {
        Flag=flag;
    }

    public boolean isFlag() {
        return Flag;
    }

    public void setRekFlag(final boolean flag) {
        RekFlag=flag;
    }

    public boolean isRekFlag() {
        return RekFlag;
    }

    public void setTarget(final boolean flag) {
        Target=flag;
    }

    public boolean isTarget() {
        return Target;
    }

    public void setKeep(final boolean flag) {
        Keep=flag;
    }

    public boolean isOwnedByControl() {
        return false;
    }

    public void setOwnedByControl(boolean b) {
    }

    public boolean isKeep() {
        return Keep;
    }

    public boolean equalsTo(final ConstructionObject o) {
        return super.equals(o);
    }

    public boolean equals(final ConstructionObject o) {
        return false;
    }

    public boolean equals(final double x, final double y) {
        return Math.abs(x-y)<1e-8;
    }
    int OldColorIndex;

    public void setJobTarget(final boolean flag) {
        if (flag) {
            OldColorIndex=ColorIndex;
            setColor(-1);
        } else if (JobTarget) {
            setColor(OldColorIndex, SpecialColor);
        }
        JobTarget=flag;
    }

    public boolean isJobTarget() {
        return JobTarget;
    }

    public void updateCircleDep() {
    }

    public void clearCircleDep() {
    }

    /**
     * This clones the object for macro construction. NOTE: This must be
     * overriden in all items to translate the mother objects.
     */
    public ConstructionObject copy(final double x, final double y) {
        ConstructionObject o=null;
        try {
            o=(ConstructionObject) clone();
            setTranslation(o);
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            Cn.getH();
            // o.setTarget(false);
        } catch (final Exception e) {
        }
        return o;
    }

    // public ConstructionObject copy ()
    // { ConstructionObject o=null;
    // try
    // { o=(ConstructionObject)clone();
    // setTranslation(o);
    // o.translateConditionals();
    // o.translate();
    // o.setName();
    // o.updateText();
    // o.setBreak(false);
    // Cn.getH();
    // // o.setTarget(false);
    // }
    // catch (Exception e) {}
    // return o;
    // }
    public Construction getConstruction() {
        return Cn;
    }

    /**
     * Get the value of an object or throw an exception (e.g. if invalid). Not
     * all objects have a value.
     */
    public double getValue() throws ConstructionException {
        throw new NoValueException(Valid);
    }

    public void applyDefaults(ConstructionObject o) {
        ConstructionObject cpy=o.copy(0, 0);
        cpy.setTargetDefaults();
        setShowName(cpy.showName());
        setShowValue(cpy.showValue());
        setColor(cpy.getColorIndex());
        setColorType(cpy.getColorType());
        setFilled(cpy.isFilled());
        setSolid(cpy.isSolid());
        setHidden(cpy.isHidden());
        setObtuse(cpy.getObtuse());
        setLarge(cpy.isLarge());
        setBold(cpy.isBold());
        setPartial(cpy.isPartial());
        Cn.updateCircleDep();
    }

    public void setDefaults() {
        setShowName(Cn.ShowNames);
        setShowValue(Cn.ShowValues);
        setColor(Cn.DefaultColor, SpecialColor);
        setColorType(Cn.DefaultColorType);
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
    }

    public void setTargetDefaults() {
        setShowName(Cn.ShowNames);
        setShowValue(Cn.ShowValues);
        setColor(Cn.DefaultColor, SpecialColor);
        setColorType(Cn.DefaultColorType);
    }

    public boolean contains(final PointObject p) {
        return false;
    }

    public boolean canFix() {
        return false;
    }

    public void setFixed(final String o) {
    }

    public boolean isBack(final boolean original) {
        if (!original&&testConditional("background")) {
            return true;
        }
        return Back;
    }

    public boolean isBack() {
        return isBack(false);
    }

    public void setBack(final boolean flag) {
        Back=flag;
    }

    public void setInConstruction(final boolean flag) {
        inConstruction=flag;
    }

    public boolean isInConstruction() {
        return inConstruction;
    }

    /**
     * This is called after the loading of a construction has been finished.
     * PointObject uses this to bind to a line or circle, which is defined AFTER
     * it.
     *
     * @param c
     *            The construction this object is in.
     */
    public void laterBind(final Construction c) {
    }

    /**
     * This is called after macro definition or execution is complete.
     * PrimitiveCircleObject uses it to translate the restriction points in its
     * copy.
     *
     * @param c
     *            The construction this object is in.
     */
    public void laterTranslate(final Construction c) {
    }

    public void setFilled(final boolean flag) {
    }

    public boolean isFilled() {
        return false;
    }

    public boolean isFilledForSelect() {
        return isFilled();
    }

    public boolean getObtuse() {
        return Obtuse;
    }

    public void setObtuse(final boolean flag) {
        Obtuse=flag;
    }

    public String getDescription() {
        return "";
    }

    public void round() {
    }

    public boolean isFixed() {
        return false;
    }

    public boolean isBreak() {
        return Break;
    }

    public void setBreak(final boolean flag) {
        Break=flag;
        HideBreak=false;
    }

    public boolean isHideBreak() {
        return HideBreak;
    }

    public void setHideBreak(final boolean flag) {
        HideBreak=flag;
        Break=flag;
    }

    public boolean isSolid(final boolean original) {
        if (!original&&testConditional("solid")) {
            return true;
        }
        return Solid;
    }

    public boolean isSolid() {
        return isSolid(false);
    }

    public void setSolid(final boolean solid) {
        Solid=solid;
    }

    public boolean maybeTransparent() {
        return false;
    }

    public boolean locallyLike(final ConstructionObject o) {
        return false;
    }
    public double Value=1000;

    public int compare(final SortObject o) {
        final ConstructionObject ob=(ConstructionObject) o;
        if (ob.Value<Value) {
            return 1;
        } else if (ob.Value==Value) {
            return 0;
        } else {
            return -1;
        }
    }

    public void setSelectable(final boolean flag) {
        Selectable=flag;
    }

    public void snap(final ZirkelCanvas zc) {
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(final String s) {
        Unit=s;
    }

    public boolean hasUnit() {
        return true;
    }

    public boolean isBreakHide() {
        return BreakHide;
    }

    public void setBreakHide(final boolean flag) {
        BreakHide=flag;
    }

    public String getEquation() {
        return getDisplayValue();
    }

    public String helpDisplayValue(final boolean start, double x,
            final String tag) {
        String s="";
        if (Math.abs(x)<1e-10) {
            return s;
        }
        if (x<0) {
            s="-";
        } else if (!start) {
            s="+";
        }
        x=Math.abs(x);
        if (Math.abs(x-1)>1e-10) {
            s=s+roundDisplay(x);
        }
        if (tag.equals("")) {
            return s;
        } else if (s.equals("")||s.equals("-")) {
            return s+tag;
        } else {
            return s+"*"+tag;
        }
    }

    public String helpDisplayNumber(final boolean start, double x) {
        String s="";
        if (Math.abs(x)<1e-10) {
            return s;
        }
        if (x<0) {
            s="-";
        } else if (!start) {
            s="+";
        }
        x=Math.abs(x);
        return s+roundDisplay(x);
    }

    public boolean visible(final ZirkelCanvas zc) {
        return selected()||ColorType!=INVISIBLE||zc.showHidden();
    }

    public boolean isBold() {
        return Bold;
    }

    public void setBold(final boolean bold) {
        Bold=bold;
    }

    public boolean isLarge() {
        return Large;
    }

    public void setLarge(final boolean large) {
        Large=large;
    }

    // Routines for conditional formatting
    public void addConditional(final String tag, final Expression expr) {
        final Conditional c=new Conditional(tag, expr);
        if (Conditionals==null) {
            Conditionals=new Vector();
        }
        Conditionals.addElement(c);
    }

    public Expression getConditional(final String tag) {
        if (Conditionals==null) {
            return null;
        }
        final Enumeration e=Conditionals.elements();
        while (e.hasMoreElements()) {
            final Conditional c=(Conditional) e.nextElement();
            if (c.getTag().equals(tag)) {
                return c.getExpression();
            }
        }
        return null;
    }

    public boolean haveConditional(final String tag) {
        final Expression ex=getConditional(tag);
        return (ex!=null);
    }

    /**
     * Test if the conditional "tag" evaluates to true.
     *
     * @param tag
     */
    public boolean testConditional(final String tag) {
        final Expression ex=getConditional(tag);
        if (ex==null) {
            return false;
        }
        try {
            if (ex.getValue()!=0) {
                return true;
            }
        } catch (final Exception e) {
        }
        return false;
    }

    public void clearConditionals() {
        Conditionals=null;
    }

    public void translateConditionals() {
        if (Conditionals!=null) {
            final Enumeration e=Conditionals.elements();
            clearConditionals();
            while (e.hasMoreElements()) {
                final Conditional c=(Conditional) e.nextElement();
                final String expr=c.getExpression().toString();
                final Expression enew=new Expression(expr, getConstruction(),
                        this);
                enew.translate();
                addConditional(c.getTag(), enew);
            }
        }
        if (AliasES!=null) {
            setAlias(AliasES.toString());
            AliasES.translate();
        }
    }

    public boolean haveConditionals() {
        return Conditionals!=null;
    }

    public void checkConditionals() throws ConstructionException {
        if (Conditionals==null) {
            return;
        }
        final Enumeration e=Conditionals.elements();
        while (e.hasMoreElements()) {
            final Conditional c=(Conditional) e.nextElement();
            if (!c.getExpression().isValid()) {
                throw new ConstructionException(c.getExpression().getErrorText());
            }
            c.getExpression().getValue();
        }
    }

    /**
     * Get the z-buffer for this object.
     *
     * @return double value z
     * @throws ConstructionException
     */
    public double getZ() throws ConstructionException {
        final Expression e=getConditional("z");
        if (e==null) {
            throw new ConstructionException("");
        }
        return e.getValue();
    }

    public boolean canDisplayName() {
        return true;
    }

    public int getNCount() {
        return NCount;
    }

    public void setNCount(final int count) {
        NCount=count;
    }
    ExpressionString AliasES=null;

    public String getAlias() {
        if (AliasES==null) {
            return null;
        } else {
            return AliasES.toString();
        }
    }

    public void setAlias(final String s) {
        if ((s==null)||("".equals(s))) {
            AliasES=null;
        } else {
            AliasES=new ExpressionString(s, this);
        }
    }
    /**
     * A general workplace for rekursive validation. VRek gets the elements that
     * need to be updated.
     */
    public MyVector VRek;
    public boolean RekValidating=false;
    public static final int DescriptionState=0, FormulaState=1,
            SizeState=2;

    /**
     * Get a display string for the Lister class.
     */
    public String getElementString(final int state) {
        String s="";
        switch (state) {
            case DescriptionState:
                s=getName()+" : "+FileName.chop(getText(), 80);
                break;
            case SizeState:
                s=getName()+" : "+FileName.chop(getCDPSizeDisplay(), 80);
                break;
            case FormulaState:
                s=getName()+" : "+FileName.chop(getEquation(), 80);
                break;
        }
        if (isSuperHidden()) {
            s="( "+s+" )";
        }
        if (isHideBreak()) {
            s="** "+s;
        } else if (isBreak()) {
            s="* "+s;
        }
        if (!valid()) {
            s="? "+s;
        }
        if (this instanceof MoveableObject
                &&((MoveableObject) this).moveable()) {
            s="> "+s;
        }
        if (s.startsWith(getName())) {
            s="  "+s;
        }
        return s;
    }

    public String getElementString() {
        return getElementString(0);
    }

    /**
     * Get a color for the element in the display color.
     */
    public Color getElementColor() {
        if (isJobTarget()) {
            return ZirkelFrame.TargetColor;
        } else if (indicated()) {
            return ZirkelFrame.IndicateColor;
        } else if (selected()) {
            return ZirkelFrame.SelectColor;
        }
        return getColor();
    }
    public boolean SpecialParameter=false;

    public boolean isSpecialParameter() {
        return SpecialParameter;
    }

    public void setSpecialParameter(final boolean flag) {
        SpecialParameter=flag;
    }

    /**
     * Service function to extract a point from an expression
     *
     * @param E
     * @return point object or null
     */
    static public PointObject getPointObject(final Expression E) {
        if (E==null) {
            return null;
        }
        final ConstructionObject o=E.getObject();
        if (o==null||!(o instanceof PointObject)) {
            return null;
        }
        return (PointObject) o;
    } // provide a reusable array for each construction object
    MyVector MV;
    boolean MVInUse=false;

    /**
     * Get an empty MyVector.
     *
     * @return MyVector instance
     */
    public MyVector getVector() {
        if (MVInUse) {
            return new MyVector();
        }
        if (MV==null) {
            MV=new MyVector();
        }
        MVInUse=true;
        return MV;
    }

    /**
     * MyVector no longer needed.
     */
    public void discardMV() {
        MVInUse=false;
        MV.removeAllElements();
    }

    public double getX() {
        return 0.0;
    }

    public double getY() {
        return 0.0;
    }
    
    public double getX3D() {
        return 0.0;
    }

    public double getY3D() {
        return 0.0;
    }
    
    public double getZ3D() {
        return 0.0;
    }

    public double getR() {
        return 0.0;
    }
    
    public double getR3D() {
        return 0.0;
    }

    public void setType(final int i) {
    }

    public int getType() {
        return 0;
    }

    public boolean isPartial() {
        return false;
    }

    public void setPartial(final boolean bool) {
    }

    public void setDisplaySize(final int i) {
    }

    public int getDisplaySize() {
        return 0;
    }

    public void setExpression(final String expr, final Construction c)
            throws ConstructionException {
    }

    public String getExpression() {
        return "";
    }

    public boolean fixed() {
        return true;
    }
    
    public boolean fixed3D() {
        return true;
    }

    public boolean fixedCoord(){
	return true;
    }
    
    public boolean fixedCoord3D(){
    	return true;
        }

    public String getEX() {
        return "";
    }

    public String getEY() {
        return "";
    }
    
    public String getEX3D() {
        return "";
    }

    public String getEY3D() {
        return "";
    }
    
    public String getEZ3D() {
        return "";
    }

    public String getEXpos() {
        return getEX();
    }

    public String getEYpos() {
        return getEY();
    }
    
    public String getEX3Dpos() {
        return getEX3D();
    }

    public String getEY3Dpos() {
        return getEY3D();
    }
    
    public String getEZ3Dpos() {
        return getEZ3D();
    }

    public void setFixed(final boolean bool) {
    }
    
    public void setFixed3D(final boolean bool) {
    }

    public void setFixed(final String x, final String y) {
    }

    public void setFixed(final boolean bool, final String r)
            throws ConstructionException {
    }
    
    public void setFixed3D(final boolean bool, final String r)
            throws ConstructionException {
    }
    
    public void setFixed(final String x3D, final String y3D, final String z3D) {
    }

    public void move(final double x, final double y) {
    }
    
    public void move3D(final double x, final double y, final double z) {
    }

    public String getPrompt() {
        return "";
    }

    public void setPrompt(final String prompt) {
    }

    public String getMin() {
        return "";
    }

    public String getMax() {
        return "";
    }

    public int getDistance(final PointObject P) {
        return Integer.MAX_VALUE;
    }

    public void setSlider(final String smin, final String smax) {
    }

    public void setSlider(final boolean bool) {
    }

    public boolean isSlider() {
        return false;
    }

    public String getStringLength() {
        return "";
    }
    
    public String getStringLength3D() {
        return "";
    }

    public void setDragable(final boolean f) {
    }

    public boolean isDragable() {
        return false;
    }

    public String getE() {
        return "";
    }

    public String getLines() {
        return "";
    }

    public void setLines(final String o) {
    }

    public double getIncrement() {
        return 0.0;
    }

    public void setIncrement(final double inc) {
    }

    public boolean isInside() {
        return false;
    }

    public void setInside(final boolean flag) {
    }

    public boolean isSpecial() {
        return false;
    }

    public void setSpecial(final boolean f) {
    }

    public void clearConditional(final String tag) {
        if (haveConditional(tag)) {
            if (Conditionals.size()==1) {
                Conditionals=null;
                return;
            }
            for (int i=0; i<Conditionals.size(); i++) {
                final Conditional c=(Conditional) Conditionals.get(i);
                if (c.getTag().equals(tag)) {
                    Conditionals.remove(i);
                    return;
                }
            }
        }
    }
}
