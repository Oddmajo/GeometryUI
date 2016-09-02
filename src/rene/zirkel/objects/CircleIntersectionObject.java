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

// file: CircleIntersectionObject.java
import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;

public class CircleIntersectionObject extends IntersectionObject {

    public CircleIntersectionObject(final Construction c,
            final PrimitiveCircleObject P1, final PrimitiveCircleObject P2,
            final boolean first) {
        super(c, P1, P2);
        First=first;
        validate();
    }

    @Override
    public void updateCircleDep() {
        ((PrimitiveCircleObject) P1).addDep(this);
        ((PrimitiveCircleObject) P2).addDep(this);
    }

    @Override
    public void validate() {

//        System.out.println(getName()+":"+First);


        final boolean oldvalid=Valid;
        if (!P1.valid()||!P2.valid()) {
            Valid=false;
        } else {
            Valid=true;
        }
        if (!Valid) {
            return;
        }
        final Coordinates c=PrimitiveCircleObject.intersect(
                (PrimitiveCircleObject) P1, (PrimitiveCircleObject) P2);
        if (c==null) {
            if (oldvalid&&getConstruction().shouldSwitch()) {
                doSwitch();
                if (!getConstruction().noteSwitch()) {
                    Switched=false;
                }
            } else if (oldvalid&&Alternate&&Away==null
                    &&getConstruction().canAlternate()) {
                First=!First;
            }
            Valid=false;
            return;
        }


        // Gestion des intersections droite/cercle (droite au sens large) en mode DP :
        if (((P1.isDPLineOrSegmentObject())&&(P2.isDPCircleObject()))
                ||(((P2.isDPLineOrSegmentObject())&&(P1.isDPCircleObject())))) {

            // arc doit être la droite ou le segment hyperbolique :
            PrimitiveCircleObject arc=(PrimitiveCircleObject) ((P1.isDPLineOrSegmentObject())?P1:P2);

            // Calcul de la coord z du produit vectoriel OStart ^ OEnd :
            Double prod1=(arc.getStart().getX()-arc.getX())*(arc.getEnd().getY()-arc.getY())
                    -(arc.getStart().getY()-arc.getY())*(arc.getEnd().getX()-arc.getX());

            // Calcul de la coord z du produit vectoriel OI ^ OJ (I et J sont les deux pts
            // d'intersection calculés) :
            Double prod2=(c.X-arc.getX())*(c.Y1-arc.getY())-(c.Y-arc.getY())*(c.X1-arc.getX());


            if (prod1*prod2>0) {
                if (First) {
                    setXY(c.X, c.Y);
                } else {
                    setXY(c.X1, c.Y1);
                }
            } else {
                if (First) {
                    setXY(c.X1, c.Y1);
                } else {
                    setXY(c.X, c.Y);
                }
            }

            if (Restricted) {
                if (!(((PrimitiveCircleObject) P1).getStart()==this||((PrimitiveCircleObject) P1).getEnd()==this)
                        &&!((PrimitiveCircleObject) P1).contains(X, Y)) {
                    Valid=false;
                }
                if (!(((PrimitiveCircleObject) P2).getStart()==this||((PrimitiveCircleObject) P2).getEnd()==this)
                        &&!((PrimitiveCircleObject) P2).contains(X, Y)) {
                    Valid=false;
                }
            }
            return;
        }

        // Gestion des intersections droite/droite (droite au sens large) en mode DP :
        else if((P1.isDPLineOrSegmentObject())&&(P2.isDPLineOrSegmentObject())) {
            PrimitiveCircleObject Hz=Cn.getHZ();
            if ((Hz!=P1)&&(Hz!=P2)) {
                final double r=(c.X)*(c.X)+(c.Y)*(c.Y);
                final double r1=(c.X1)*(c.X1)+(c.Y1)*(c.Y1);
                final double R=Hz.getR()*Hz.getR();
                if ((r>R)||(r1>R)) {
                    if (r<R) {
                        setXY(c.X, c.Y);
                    } else {
                        setXY(c.X1, c.Y1);
                    }
                    if (Restricted) {
                        if (!(((PrimitiveCircleObject) P1).getStart()==this||((PrimitiveCircleObject) P1).getEnd()==this)
                                &&!((PrimitiveCircleObject) P1).contains(X, Y)) {
                            Valid=false;
                        }
                        if (!(((PrimitiveCircleObject) P2).getStart()==this||((PrimitiveCircleObject) P2).getEnd()==this)
                                &&!((PrimitiveCircleObject) P2).contains(X, Y)) {
                            Valid=false;
                        }
                    }
                    return;
                }
            }
        }



        final PointObject oa=getAway();
        if (oa!=null) {
            final double x=oa.getX(), y=oa.getY();
            final double r=(x-c.X)*(x-c.X)+(y-c.Y)*(y-c.Y);
            final double r1=(x-c.X1)*(x-c.X1)+(y-c.Y1)*(y-c.Y1);
            boolean flag=(r>r1);
            if (!StayAway) {
                flag=!flag;
            }
            if (flag) {

                setXY(c.X, c.Y);
            } else {

                setXY(c.X1, c.Y1);
            }
        } else {
            if (First) {
                setXY(c.X, c.Y);
            } else {
                setXY(c.X1, c.Y1);
            }
        }
        if (Restricted) {
            if (!(((PrimitiveCircleObject) P1).getStart()==this||((PrimitiveCircleObject) P1).getEnd()==this)
                    &&!((PrimitiveCircleObject) P1).contains(X, Y)) {
                Valid=false;
            }
            if (!(((PrimitiveCircleObject) P2).getStart()==this||((PrimitiveCircleObject) P2).getEnd()==this)
                    &&!((PrimitiveCircleObject) P2).contains(X, Y)) {
                Valid=false;
            }
        }
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        super.printArgs(xml);
        if (First) {
            xml.printArg("which", "first");
        } else {
            xml.printArg("which", "second");
        }
    }

    @Override
    public boolean isSwitchable() {
        return true;
    }

    @Override
    public boolean canAlternate() {
        return true;
    }
}
