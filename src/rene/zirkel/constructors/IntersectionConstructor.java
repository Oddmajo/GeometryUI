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
package rene.zirkel.constructors;

import java.awt.event.MouseEvent;

import eric.JZirkelCanvas;

import rene.util.MyVector;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.CircleIntersectionObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.LineCircleIntersectionObject;
import rene.zirkel.objects.LineIntersectionObject;
import rene.zirkel.objects.LineQuadricIntersectionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObjectIntersectionObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.QuadricObject;
import rene.zirkel.objects.QuadricQuadricIntersectionObject;

public class IntersectionConstructor extends ObjectConstructor {

    ConstructionObject P1=null, P2=null;
    boolean immediate = false;
    MouseEvent ev = null;

    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }

	ev = e;
        if (P1==null) {
            final MyVector v=zc.selectPointonObjects(e.getX(), e.getY());
            if (v.size()==2) {
                P1=(ConstructionObject) v.elementAt(0);
                P2=(ConstructionObject) v.elementAt(1);
                if (P1.equals(P2)|| P2.equals(P1) || (P1.isFilled()&&P2.isFilled())) {
                    P1=P2=null;
                } else {
                    immediate=true;
                }
            }
        }

	ConstructionObject obj = null;
	if(P1==null || P2==null){
	    obj = select(e.getX(), e.getY(), zc);
	}
	setConstructionObject(obj, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc){
	if (P1==null) {
	    P1 = obj;
            if (P1!=null) {
                P1.setSelected(true);
                zc.repaint();
                showStatus(zc);
            }
        } else if(P2==null) {
	    P2 = obj;
	}

	if (P2!=null) {
            if (P2==P1) {
                P2=null;
                return;
            }

            final IntersectionObject o[]=construct(P1, P2, zc.getConstruction());
            if (o!=null) {
                IntersectionObject oc=null;
                if (immediate&&o.length>1) {
                    if (o[1].nearto(ev.getX(), ev.getY(), zc)) {
                        o[0]=null;
                        oc=o[1];
                    } else {
                        o[1]=null;
                        oc=o[0];
                    }
                }
                for (final IntersectionObject element : o) {
                    if (element!=null) {
                        element.setDefaults();
                        zc.addObject(element);
                        element.validate(zc.x(ev.getX()), zc.y(ev.getY()));
                    }
                }
                /**
                 * See, if the other intersection is visible and already a
                 * point of both objects.
                 */
                if (oc!=null) {
                    oc.autoAway();
                }
            }
	    reset(zc);
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
//        System.out.println("inter : "+e.getX()+" "+e.getY());
        zc.indicateIntersectedObjects(e.getX(), e.getY());
    }

    public static IntersectionObject[] construct(final ConstructionObject P1,
            final ConstructionObject P2, final Construction c) {
        IntersectionObject o[]=null;
        if (P1 instanceof PrimitiveLineObject) {
            if (P2 instanceof PrimitiveLineObject) {
                o=new IntersectionObject[1];
                o[0]=new LineIntersectionObject(c, (PrimitiveLineObject) P1,
                        (PrimitiveLineObject) P2);
            } else if (P2 instanceof PrimitiveCircleObject) {
                o=new IntersectionObject[2];
                o[0]=new LineCircleIntersectionObject(c,
                        (PrimitiveLineObject) P1, (PrimitiveCircleObject) P2,
                        true);
                o[1]=new LineCircleIntersectionObject(c,
                        (PrimitiveLineObject) P1, (PrimitiveCircleObject) P2,
                        false);
            } else if (P2 instanceof QuadricObject) {
                o=new IntersectionObject[2];
                o[0]=new LineQuadricIntersectionObject(c,
                        (PrimitiveLineObject) P1, (QuadricObject) P2, true);
                o[1]=new LineQuadricIntersectionObject(c,
                        (PrimitiveLineObject) P1, (QuadricObject) P2, false);
            } else {
                return construct(P2, P1, c);
            }
        } else if (P1 instanceof QuadricObject) {
            if (P2 instanceof PrimitiveLineObject) {
                o=new IntersectionObject[2];
                o[0]=new LineQuadricIntersectionObject(c,
                        (PrimitiveLineObject) P2, (QuadricObject) P1, true);
                o[1]=new LineQuadricIntersectionObject(c,
                        (PrimitiveLineObject) P2, (QuadricObject) P1, false);

            } else if (P2 instanceof QuadricObject) {
            	if (((QuadricObject) P1).getP()[0].is3D()||((QuadricObject) P1).getP()[1].is3D()||((QuadricObject) P1).getP()[2].is3D()||((QuadricObject) P1).getP()[3].is3D()||((QuadricObject) P1).getP()[4].is3D()||
                        ((QuadricObject) P2).getP()[0].is3D()||((QuadricObject) P2).getP()[1].is3D()||((QuadricObject) P2).getP()[2].is3D()||((QuadricObject) P2).getP()[3].is3D()||((QuadricObject) P2).getP()[4].is3D()) {// Dibs : truc de givré pour régler un bug avec l'intersection de cercles 3D
            		final PointObject p1=((QuadricObject) P1).getP()[1];
            		p1.setEX3D(p1.getEX3D().toString()+"+0.00001");
            		p1.setFixed("x(O)+("+p1.getEX3D()+")*(x(X)-x(O))+("+p1.getEY3D()+")*(x(Y)-x(O))+("+p1.getEZ3D()+")*(x(Z)-x(O))", "y(O)+("+p1.getEX3D()+")*(y(X)-y(O))+("+p1.getEY3D()+")*(y(Y)-y(O))+("+p1.getEZ3D()+")*(y(Z)-y(O))");
            	}
            	
                o=new IntersectionObject[4];
                o[0]=new QuadricQuadricIntersectionObject(c,
                        (QuadricObject) P2, (QuadricObject) P1, 0);
                o[1]=new QuadricQuadricIntersectionObject(c,
                        (QuadricObject) P2, (QuadricObject) P1, 1);
                o[2]=new QuadricQuadricIntersectionObject(c,
                        (QuadricObject) P2, (QuadricObject) P1, 2);
                o[3]=new QuadricQuadricIntersectionObject(c,
                        (QuadricObject) P2, (QuadricObject) P1, 3);


            } else {
                o=new PointonObjectIntersectionObject[1];
                o[0]=new PointonObjectIntersectionObject(c, P1, P2);
            }

        } else if (P1 instanceof PrimitiveCircleObject) {
            if (P2 instanceof PrimitiveCircleObject) {

                if ((P1.isDPLineOrSegmentObject())&&(P2.isDPLineOrSegmentObject())) {
                    o=new IntersectionObject[1];
                    o[0]=new CircleIntersectionObject(c,
                            (PrimitiveCircleObject) P1, (PrimitiveCircleObject) P2,
                            true);
                } else {
                    o=new IntersectionObject[2];
                    o[0]=new CircleIntersectionObject(c,
                            (PrimitiveCircleObject) P1, (PrimitiveCircleObject) P2,
                            true);
                    o[1]=new CircleIntersectionObject(c,
                            (PrimitiveCircleObject) P1, (PrimitiveCircleObject) P2,
                            false);
                }
            } else if (P2 instanceof PrimitiveLineObject) {
                o=new IntersectionObject[2];
                o[0]=new LineCircleIntersectionObject(c,
                        (PrimitiveLineObject) P2, (PrimitiveCircleObject) P1,
                        true);
                o[1]=new LineCircleIntersectionObject(c,
                        (PrimitiveLineObject) P2, (PrimitiveCircleObject) P1,
                        false);
            } else {
                return construct(P2, P1, c);
            }
        } else {
            o=new PointonObjectIntersectionObject[1];
            o[0]=new PointonObjectIntersectionObject(c, P1, P2);
        }
        return o;
    }

    public ConstructionObject select(final int x, final int y, final ZirkelCanvas zc) // select a line or circle at x,y
    {
        return zc.selectPointonObject(x, y, false);
    }

    @Override
    public void reset(final ZirkelCanvas zc) { // reset the tool
        super.reset(zc);
        if (zc.Visual) {
            P1=P2=null;
            showStatus(zc);
        } else {
            zc.setPrompt(Global.name("prompt.intersection"));
        }
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (P1==null) {
            zc.showStatus(Global.name("message.intersection.first",
                    "Intersection: Select first object!"));
        } else {
            zc.showStatus(Global.name("message.intersection.second",
                    "Intersection: Select second object!"));
        }
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Intersection")) {
            return constructOther(tree, c);
        }
        final XmlTag tag=tree.getTag();
        if (!tag.hasParam("first")||!tag.hasParam("second")) {
            throw new ConstructionException("Intersection parameters missing!");
        }
        try {
            final ConstructionObject o1=c.find(tag.getValue("first"));
            final ConstructionObject o2=c.find(tag.getValue("second"));
            final IntersectionObject o[]=construct(o1, o2, c);
            if (o==null) {
                throw new Exception("");
            }
            String name="", nameOther="";
            if (tag.hasParam("name")) {
                name=tag.getValue("name");
            }
            if (tag.hasParam("other")) {
                nameOther=tag.getValue("other");
            }





            if (o.length>1) {
                IntersectionObject oo=o[0];
                if (tag.hasParam("which")) {
                    if (tag.getValue("which").equals("first")) {
                        oo=o[0];
                    } else if (tag.getValue("which").equals("second")) {
                        oo=o[1];
                    } else if (tag.getValue("which").equals("0")) {
                        oo=o[0];
                    } else if (tag.getValue("which").equals("1")) {
                        oo=o[1];
                    } else if (tag.getValue("which").equals("2")) {
                        oo=o[2];
                    } else if (tag.getValue("which").equals("3")) {
                        oo=o[3];
                    }

                    if (!name.equals("")) {
                        oo.setName(name);
                    }
                    PointConstructor.setType(tag, oo);
                    setName(tag, oo);
                    set(tree, oo);
                    c.add(oo);
                    setConditionals(tree, c, oo);
                    if (tag.hasParam("awayfrom")) {
                        oo.setAway(tag.getValue("awayfrom"), true);
                    } else if (tag.hasParam("closeto")) {
                        oo.setAway(tag.getValue("closeto"), false);
                    }
                    if (tag.hasParam("valid")) {
                        oo.setRestricted(false);
                    }
                    if (tag.hasParam("alternate")) {
                        oo.setAlternate(true);
                    }
                } else if (tag.hasParam("other")) {
                    if (!name.equals("")) {
                        o[0].setName(name);
                    }
                    if (!nameOther.equals("")) {
                        o[1].setName(nameOther);
                    }
                    if (tag.hasParam("awayfrom")) {
                        o[0].setAway(tag.getValue("awayfrom"), true);
                        o[1].setAway(tag.getValue("awayfrom"), false);
                    } else if (tag.hasParam("closeto")) {
                        o[1].setAway(tag.getValue("awayfrom"), true);
                        o[0].setAway(tag.getValue("awayfrom"), false);
                    }
                    for (final IntersectionObject element : o) {
                        if (element==null) {
                            continue;
                        }
                        PointConstructor.setType(tag, element);
                        set(tree, element);
                        c.add(element);
                        setConditionals(tree, c, element);
                    }
                }
            } else {
                if (!name.equals("")) {
                    o[0].setName(name);
                }
                PointConstructor.setType(tag, o[0]);
                setName(tag, o[0]);
                set(tree, o[0]);
                c.add(o[0]);
                setConditionals(tree, c, o[0]);
                if (tag.hasParam("valid")) {
                    o[0].setRestricted(false);
                }
                try {
                    final double x=new Double(tag.getValue("x")).doubleValue();
                    final double y=new Double(tag.getValue("y")).doubleValue();
                    o[0].setXY(x, y);
                } catch (final Exception e) {
                }
            }
        } catch (final ConstructionException e) {
            throw e;
        } catch (final Exception e) {
            //e.printStackTrace();
            throw new ConstructionException("Intersection parameters illegal!");
        }
        return true;
    }

    public boolean constructOther(final XmlTree tree, final Construction c)
            throws ConstructionException {
        if (!testTree(tree, "OtherIntersection")) {
            return false;
        }
        final XmlTag tag=tree.getTag();
        if (tag.hasParam("name")) {
            final ConstructionObject o=c.find(tag.getValue("name"));
            if (o==null||!(o instanceof IntersectionObject)) {
                throw new ConstructionException("OtherIntersection not found!");
            }
            final IntersectionObject oo=(IntersectionObject) o;
            PointConstructor.setType(tag, oo);
            o.setDefaults();
            set(tree, o);
            final ConstructionObject ol=c.lastButN(1);
            if (tag.hasParam("awayfrom")) {
                oo.setAway(tag.getValue("awayfrom"), true);
                if (ol!=null&&(ol instanceof IntersectionObject)) {
                    ((IntersectionObject) ol).setAway(tag.getValue("awayfrom"),
                            false);
                }
            } else if (tag.hasParam("closeto")) {
                oo.setAway(tag.getValue("closeto"), false);
                if (ol!=null&&(ol instanceof IntersectionObject)) {
                    ((IntersectionObject) ol).setAway(tag.getValue("awayfrom"),
                            true);
                }
            }
            if (tag.hasParam("valid")) {
                oo.setRestricted(false);
            }
        } else {
            throw new ConstructionException(
                    "OtherIntersection must have a name!");
        }
        return true;
    }

    @Override
    public String getTag() {
        return "Intersection";
    }

    @Override
    public void construct(final Construction c, final String name,
            final String params[], final int nparams)
            throws ConstructionException {
        if (nparams!=2&&nparams!=3) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final ConstructionObject p1=c.find(params[0]);
        if (p1==null) {
            throw new ConstructionException(Global.name("exception.notfound")+" "+params[0]);
        }
        final ConstructionObject p2=c.find(params[1]);
        if (p2==null) {
            throw new ConstructionException(Global.name("exception.notfound")+" "+params[0]);
        }
        final IntersectionObject o[]=construct(p1, p2, c);
        if (o==null) {
            throw new ConstructionException(Global.name("exception.type"));
        }
        if (o.length==1) {
            c.add(o[0]);
            o[0].setDefaults();
            if (!name.equals("")) {
                o[0].setName(name);
            }
        } else {
            if (name.equals("")) {
                for (final IntersectionObject element : o) {
                    c.add(element);
                    element.setDefaults();
                }
            } else {
                final String names[]=new String[2];
                int n;
                if ((n=name.indexOf(','))>=0) {
                    names[0]=name.substring(n+1).trim();
                    names[1]=name.substring(0, n).trim();
                } else {
                    names[0]=name;
                    names[1]="";
                }
                for (int i=0; i<o.length; i++) {
                    if (names[i].equals("")) {
                        continue;
                    }
                    c.add(o[i]);
                    o[i].setDefaults();
                    o[i].setName(names[i]);
                }
            }
        }
    }
}