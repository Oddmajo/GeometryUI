/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.constructors;

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.VectorObject;

/**
 *
 * @author PM
 */
public class VectorConstructor extends SegmentConstructor{

    public VectorConstructor(){
	this(false);
    }

    public VectorConstructor(boolean fixed){
	Fixed = fixed;
    }

    @Override
    public ConstructionObject create(final Construction c, final PointObject p1, final PointObject p2) {
	return new VectorObject(c, p1, p2);
    }

    @Override
    public void showStatus(final ZirkelCanvas zc) {
	if (P1 == null)
	    zc.showStatus(Global.name("message.vector.first"));
	else
	    zc.showStatus(Global.name("message.vector.second"));
    }

    @Override
    public boolean construct(final XmlTree tree, final Construction c) throws ConstructionException {
	if (!testTree(tree, "Segment"))
	    return false;
	final XmlTag tag = tree.getTag();
	if (!tag.hasParam("from") || !tag.hasParam("to"))
	    throw new ConstructionException("Segment endpoints missing!");
	    try {
		final PointObject p1 = (PointObject) c.find(tag.getValue("from"));
		final PointObject p2 = (PointObject) c.find(tag.getValue("to"));
		final VectorObject o = new VectorObject(c, p1, p2);
		setName(tag, o);
		set(tree, o);
		c.add(o);
		setConditionals(tree, c, o);
		//o.setArrow(tag.hasParam("arrow"));
		if(tag.hasParam("x") && tag.hasParam("y")){
		    o.setEXY(tag.getValue("x"), tag.getValue("y"));
		}
		if (tag.hasParam("fixed")) {
		    try {
			o.setFixed(true, tag.getValue("fixed"));
		    } catch (final Exception e) {
			throw new ConstructionException("Fixed value illegal!");
		    }
		}
		if (tag.hasParam("code_symbol"))
		    o.setSegmentCode(Integer.parseInt(tag.getValue("code_symbol")));
	    } catch (final ConstructionException e) {
		throw e;
	    } catch (final Exception e) {
		throw new ConstructionException("Segment endpoints illegal!");
	    }
	return true;
    }

    @Override
    public void construct(final Construction c, final String name, final String params[], final int nparams)
	    throws ConstructionException {

	if (nparams != 2 && nparams != 3)
	    throw new ConstructionException(Global.name("exception.nparams"));
	final ConstructionObject P1 = c.find(params[0]);

	if (P1 == null)
	    throw new ConstructionException(Global.name("exception.notfound")+ " " + params[0]);

	if (!(P1 instanceof PointObject))
	    throw new ConstructionException(Global.name("exception.type") + " "+ params[0]);

	ConstructionObject P2 = c.find(params[1]);
	if (P2 == null) {
	    final Expression ex = new Expression(params[1], c, null);
	    if (!ex.isValid())
		throw new ConstructionException(Global.name("exception.expression"));

	    final double x = ex.getValue();
	    P2 = new PointObject(c, ((PointObject) P1).getX() + x, ((PointObject) P1).getY());
	    c.add(P2);
	    P2.setDefaults();
	    final VectorObject s = new VectorObject(c, (PointObject) P1, (PointObject) P2);
	    s.setDefaults();
	    s.setFixed(true, params[1]);
	    c.add(s);
	    if (!name.equals(""))
		s.setNameCheck(name);
	    return;
	}
	if (!(P2 instanceof PointObject))
	    throw new ConstructionException(Global.name("exception.type") + " "+ params[1]);

	final VectorObject s = new VectorObject(c, (PointObject) P1, (PointObject) P2);

	if (nparams == 3) {
	    if (!s.canFix())
		throw new ConstructionException(Global.name("exception.canfix"));
	    s.setFixed(true, params[2]);
	    if (!s.isValidFix())
		throw new ConstructionException(Global.name("exception.fix")+ " " + params[2]);
	    s.validate();
	}
	c.add(s);
	s.setDefaults();
//      s.setArrow(c.isVector());
	if (!name.equals(""))
	    s.setNameCheck(name);
    }
}
