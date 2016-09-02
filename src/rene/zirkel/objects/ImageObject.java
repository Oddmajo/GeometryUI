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

import eric.Media;
import java.awt.Image;
import java.util.Enumeration;

import rene.util.FileName;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;
import rene.zirkel.graphics.MyGraphics;

public class ImageObject extends ConstructionObject {

    PointObject P[];
    static Count N=new Count();
    String Filename;
    Image I;

    public ImageObject(final Construction c, final PointObject p[],
            final String filename) {
        super(c);
        P=p;
        Filename=FileName.filename(filename);
//                Filename=filename;
        updateText();
    }

    public String getFilename() {
	return Filename;
    }

    @Override
    public String getTag() {
        return "Image";
    }

    @Override
    public int getN() {
        return N.next();
    }

    @Override
    public void updateText() {
        try {
            final String Names[]=new String[P.length];
            for (int i=0; i<P.length; i++) {
                Names[i]=P[i].getName();
            }
            setText(textAny(Global.name("text.image"), Names));
        } catch (final Exception e) {
        }
    }

    @Override
    public void validate() {
    }

    @Override
    public void paint(final MyGraphics g, final ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }
        if (I==null) {

//			I = zc.doLoadImage(Filename);
            I=Media.getImage(Filename);

            if (I==null||I.getWidth(zc)==0||I.getHeight(zc)==0) {
                Valid=false;
                return;
            } else {
                Valid=true;
            }
        }
        if (P[1]==P[2]||P[0]==P[2]) {
            I.getWidth(zc);
            final double dx=P[1].getX()-P[0].getX(), dy=P[1].getY()
                    -P[0].getY();
            g.drawImage(I, zc.col(P[0].getX()), zc.row(P[0].getY()), zc.col(P[1].getX()), zc.row(P[1].getY()), zc.col(P[0].getX()
                    +dy), zc.row(P[0].getY()-dx), zc);
        } else {
            g.drawImage(I, zc.col(P[0].getX()), zc.row(P[0].getY()), zc.col(P[1].getX()), zc.row(P[1].getY()), zc.col(P[2].getX()), zc.row(P[2].getY()), zc);
        }
    }

    @Override
    public boolean nearto(final int cc, final int rr, final ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        return P[0].nearto(cc, rr, zc)||P[1].nearto(cc, rr, zc)
                ||P[2].nearto(cc, rr, zc);
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        for (int i=0; i<P.length; i++) {
            xml.printArg("point"+(i+1), P[i].getName());
        }
        xml.printArg("filename", FileName.filename(Filename));
    }

    @Override
    public Enumeration depending() {
        DL.reset();
        for (final PointObject element : P) {
            DL.add(element);
        }
        return DL.elements();
    }

    @Override
    public void translate() {
        for (int i=0; i<P.length; i++) {
            P[i]=(PointObject) P[i].getTranslation();
        }
    }

    @Override
    public ConstructionObject copy(final double x, final double y) {
        try {
            final QuadricObject o=(QuadricObject) clone();
            setTranslation(o);
            o.P=new PointObject[P.length];
            for (int i=0; i<P.length; i++) {
                o.P[i]=P[i];
            }
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            o.setTarget(false);
            return o;
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public boolean onlynearto(final int x, final int y, final ZirkelCanvas zc) {
        return nearto(x, y, zc);
    }

    @Override
    public boolean hasUnit() {
        return false;
    }
}
