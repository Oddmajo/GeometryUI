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
 
 
 package rene.zirkel.construction;

import java.util.Vector;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.constructors.AngleConstructor;
import rene.zirkel.constructors.AreaConstructor;
import rene.zirkel.constructors.CircleConstructor;
import rene.zirkel.constructors.ExpressionConstructor;
import rene.zirkel.constructors.FunctionConstructor;
import rene.zirkel.constructors.IntersectionConstructor;
import rene.zirkel.constructors.LineConstructor;
import rene.zirkel.constructors.MidpointConstructor;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.constructors.ParallelConstructor;
import rene.zirkel.constructors.PlumbConstructor;
import rene.zirkel.constructors.PointConstructor;
import rene.zirkel.constructors.QuadricConstructor;
import rene.zirkel.constructors.RayConstructor;
import rene.zirkel.constructors.SegmentConstructor;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.CircleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.LineObject;
import rene.zirkel.objects.PlumbObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TwoPointLineObject;

public class Interpreter {
	Construction C;

	public Interpreter(final Construction c) {
		C = c;
	}

	/**
	 * This is to interpret a single line of input in descriptive mode or a
	 * single line read from a construction description in a file.
	 */
	public void interpret(final ZirkelCanvas zc, String s, final String comment)
	throws ConstructionException {
		boolean Parameter = false, Target = false, Prompt = false;
		final Vector V = C.V;
		final int VN = V.size(); // note the current construction size
		int k;

		// Look for special start tags:
		if ((k = startTest("parameter", s)) >= 0)
			// Parameter objects are set at the end of the function.
		{
			Parameter = true;
			s = s.substring(k).trim();
		} else if (s.toLowerCase().equals(Global.name("showall"))
				|| s.toLowerCase().equals("showall"))
			// This will show all objects even, if there are targets
		{
			C.ShowAll = true;
			return;
		} else if (s.toLowerCase().equals(Global.name("invisible"))
				|| s.toLowerCase().equals("invisible"))
			// This will superhide all objects
		{
			C.SuperHide = true;
			return;
		} else if ((k = startTest("target", s)) >= 0)
			// Target objects are either set immediate (name only) or
			// at the end of the function.
		{
			Target = true;
			s = s.substring(k).trim();
			final ConstructionObject o = C.find(s);
			// see, if the target ist constructable from the
			// parameters, which are already marked cosntructable.
			C.determineConstructables();
			if (o != null && o.isFlag()) {
				o.setTarget(true);
				C.addTarget(o);
				return;
			} else {
                            Target = true;
                        }
		} else if ((k = startTest("prompt", s)) >= 0) {
			Prompt = true;
			s = s.substring(k).trim();
			if (C.find(s) != null) {
				C.PromptFor.addElement(s);
				return;
			}
		}

		// Interpret s. First decompose into function and parameters:
		String name = "", function = "";
		final String params[] = new String[16];
		int NParams = 0, n;
		if ((n = s.indexOf('=')) >= 0) {
			name = s.substring(0, n).trim();
			s = s.substring(n + 1).trim();
		}
		int bracketn = 0;
		if (s.startsWith("\"")) {
			bracketn = s.indexOf("\"", 1);
		}
		if (bracketn < 0) {
                    throw new ConstructionException("exception.brackets");
                }
		if ((n = s.indexOf('(', bracketn)) >= 0) {
			function = s.substring(0, n).trim();
			if (function.startsWith("\"") && function.endsWith("\"")
					&& function.length() > 1) {
				function = function.substring(1, function.length() - 1);
			}
			s = s.substring(n + 1).trim();
			if (!s.endsWith(")")) {
                            throw new ConstructionException("exception.brackets");
                        }
			final char a[] = s.substring(0, s.length() - 1).toCharArray();
			int ia = 0;
			int BCount = 0;
			while (ia < a.length && NParams < params.length) {
				final int ia0 = ia;
				while (ia < a.length && (BCount > 0 || a[ia] != ',')) {
					if (a[ia] == '\"') {
						ia++;
						while (ia < a.length && a[ia] != '\"') {
                                                    ia++;
                                                }
						if (ia >= a.length) {
                                                    throw new ConstructionException(Global.name("exception.quotes"));
                                                }
						ia++;
					} else if (a[ia] == '(') {
						BCount++;
						ia++;
					} else if (a[ia] == ')') {
						if (BCount > 0) {
                                                    BCount--;
                                                }
						else {
                                                    throw new ConstructionException(Global.name("exception.brackets"));
                                                }
						ia++;
					} else {
                                            ia++;
                                        }
				}
				params[NParams++] = new String(a, ia0, ia - ia0).trim();
				ia++;
			}
		} else {
                    function = s;
                }
		final String f = function;

		// Interpret special functions:

		if (NParams == 3 && ptest(f, "window"))
			// window size
		{
			try {
				final double x = new Double(params[0]).doubleValue();
				final double y = new Double(params[1]).doubleValue();
				final double w = new Double(params[2]).doubleValue();
				C.setXYW(x, y, w);
				zc.recompute();
			} catch (final Exception e) {
				throw new ConstructionException(Global.name("exception.value"));
			}
			return;
		}
		if ((NParams == 1 || NParams == 2) && ptest(f, "color"))
			// color for object or default color
		{
			int i = 0;
			for (i = 0; i < ZirkelFrame.ColorStrings.length; i++) {
				if (test(params[0], "colors", ZirkelFrame.ColorStrings[i])) {
                                    break;
                                }
			}
			if (i < ZirkelFrame.ColorStrings.length) {
				if (NParams == 2) {
					final ConstructionObject o = C.find(params[1]);
					if (o == null) {
						throw new ConstructionException(Global.name("exception.notfound"));
                                        }
					o.setColor(i);
				} else {
					C.DefaultColor = i;
				}
			} else {
				throw new ConstructionException(Global.name("exception.color"));
                        }
			return;
		}
		if ((NParams == 1 || NParams == 2) && ptest(f, "thickness"))
			// thickness for objects or default thickness
		{
			int i;
			for (i = 0; i < ZirkelFrame.ColorTypes.length; i++) {
				if (test(params[0], "color.type", ZirkelFrame.ColorTypes[i])) {
					break;
                                }
			}
			if (i < ZirkelFrame.ColorTypes.length) {
				if (NParams == 2) {
					final ConstructionObject o = C.find(params[1]);
					if (o == null) {
						throw new ConstructionException(Global.name("exception.notfound"));
                                        }
					o.setColorType(i);
				} else {
					C.DefaultColorType = i;
				}
			} else {
				throw new ConstructionException(Global.name("exception.colortype"));
                        }
			return;
		}
		if ((NParams == 1 || NParams == 2) && ptest(f, "type"))
			// point type for point or default point type
		{
			int i;
			for (i = 0; i < ZirkelFrame.PointTypes.length; i++) {
				if (test(params[0], "point.type", ZirkelFrame.PointTypes[i])) {
					break;
                                }
			}
			if (i < ZirkelFrame.PointTypes.length) {
				if (NParams == 2) {
					final ConstructionObject o = C.find(params[1]);
					if (o == null || !(o instanceof PointObject)) {
						throw new ConstructionException(Global.name("exception.notfound"));
                                        }
					((PointObject) o).setType(i);
				} else {
					C.DefaultType = i;
				}
			} else {
				throw new ConstructionException(Global.name("exception.type"));
                        }
			return;
		}
		if ((NParams == 1 || NParams == 2) && ptest(f, "partial"))
			// partail view for circle or line or default partial view
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				if (o instanceof PrimitiveCircleObject) {
					((PrimitiveCircleObject) o).setPartial(true);
                                }
				if (o instanceof LineObject) {
					((LineObject) o).setPartial(true);
                                }
				return;
			}
			final boolean partial = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				if (o instanceof PrimitiveCircleObject) {
					((PrimitiveCircleObject) o).setPartial(partial);
                                }
				if (o instanceof LineObject) {
					((LineObject) o).setPartial(partial);
                                }
			} else {
				C.Partial = partial;
				C.PartialLines = partial;
			}
			return;
		}
		if ((NParams == 1 || NParams == 2) && ptest(f, "hide"))
			// hide object or toggle show hidden state
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setHidden(true);
				return;
			}
			final boolean hidden = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setHidden(hidden);
			} else {
				C.Hidden = hidden;
			}
			return;
		}
		if (NParams == 2 && ptest(f, "invisible"))
			// totally invisible for an object
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setSuperHidden(true);
				return;
			}
			final boolean hidden = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setSuperHidden(hidden);
			} else {
				throw new ConstructionException(Global.name("exception.notfound"));
                        }
			return;
		}
		if (NParams >= 1 && ptest(f, "back"))
			// push object into background
		{
			if (NParams == 1) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setBack(true);
				return;
			}
			final boolean back = truetest(params[0]);
			final ConstructionObject o = C.find(params[1]);
			if (o == null) {
				throw new ConstructionException(Global.name("exception.notfound"));
                        }
			o.setBack(back);
			return;
		}
		if (NParams >= 1 && ptest(f, "acute"))
			// set acute state of angle, or set default acute state
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setObtuse(false);
				return;
			}
			final boolean acute = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setObtuse(!acute);
			} else {
				C.Obtuse = !acute;
			}
			return;
		}
		if (NParams >= 1 && ptest(f, "obtuse"))
			// revorse of acute
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
					throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setObtuse(false);
				return;
			}
			final boolean obtuse = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setObtuse(obtuse);
			} else {
				C.Obtuse = obtuse;
			}
			return;
		}
		if (NParams >= 1 && ptest(f, "solid"))
			// set solid state of object, or default solid state
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setSolid(false);
				return;
			}
			final boolean solid = truetest(params[0]);
			if (NParams == 2) {
				final ConstructionObject o = C.find(params[1]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setSolid(solid);
			} else {
				C.Solid = solid;
			}
			return;
		}
		if (NParams == 3 && ptest(f, "restrict"))
			// set range for circle arcs
		{
			try {
				final PrimitiveCircleObject c = (PrimitiveCircleObject) C.find(params[0]);
				c.setRange(params[1], params[2]);
			} catch (final Exception e) {
				throw new ConstructionException(Global.name("exception.notfound"));
			}
			return;
		}
		if (NParams >= 1 && ptest(f, "fill"))
			// set fill state for objects: fill(o), fill(t/f,o)
		{
			if (NParams == 1) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setFilled(true);
				o.setBack(true);
				return;
			}
			final boolean fill = truetest(params[0]);
			final ConstructionObject o = C.find(params[1]);
			if (o == null) {
                            throw new ConstructionException(Global.name("exception.notfound"));
                        }
			o.setFilled(fill);
			o.setBack(fill);
			return;
		}
		if (NParams >= 1 && ptest(f, "valid"))
			// set always valid state of intersection
		{
			if (NParams == 1) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				if (o instanceof PlumbObject) {
					((PlumbObject) o).setRestricted(false);
				} else if (o instanceof IntersectionObject) {
					((IntersectionObject) o).setRestricted(false);
				}
				return;
			}
			truetest(params[0]);
			final ConstructionObject o = C.find(params[1]);
			if (o == null) {
                            throw new ConstructionException(Global.name("exception.notfound"));
                        }
			if (o instanceof PlumbObject) {
				((PlumbObject) o).setRestricted(false);
			} else if (o instanceof IntersectionObject) {
				((IntersectionObject) o).setRestricted(false);
			}
			return;
		}
		if (NParams >= 1 && (ptest(f, "rename") || ptest(f, "name")))
			// works like name
		{
			if (NParams == 1 && !truecheck(params[0])) {
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setShowName(true);
				return;
			}
			ConstructionObject o = C.find(params[0]);
			if (o == null) {
				final boolean shownames = truetest(params[0]);
				if (NParams == 2) {
					o = C.find(params[1]);
					if (o == null) {
                                            throw new ConstructionException(Global.name("exception.notfound"));
                                        }
					o.setShowName(truetest(params[0]));
				} else {
					C.ShowNames = shownames;
				}
			} else if (!params[1].equals("")) {
                            o.setName(params[1]);
                        }
			return;
		}
		if (NParams == 2 && ptest(f, "away"))
			// keep interesction away from object
		{
			final ConstructionObject o = C.find(params[0]);
			final ConstructionObject p = C.find(params[1]);
			if (!(o instanceof IntersectionObject) || !(p instanceof PointObject)) {
                            throw new ConstructionException(Global.name("exception.parameter"));
                        }
			((IntersectionObject) o).setAway(p.getName());
			return;
		}
		if (NParams == 2 && ptest(f, "close"))
			// keep interesction close to object
		{
			final ConstructionObject o = C.find(params[0]);
			final ConstructionObject p = C.find(params[1]);
			if (!(o instanceof IntersectionObject)
					|| !(p instanceof PointObject)) {
                            throw new ConstructionException(Global.name("exception.parameter"));
                        }
			((IntersectionObject) o).setAway(p.getName(), false);
			return;
		}
		if (NParams >= 1 && ptest(f, "value"))
			// set the size of an object, value of expression, position of point
			// or toggle value display
		{
			if (NParams == 1 && !truecheck(params[0])) // value(o)
			{
				final ConstructionObject o = C.find(params[0]);
				if (o == null) {
                                    throw new ConstructionException(Global.name("exception.notfound"));
                                }
				o.setShowValue(true);
				return;
			}
			ConstructionObject o = C.find(params[0]);
			if (o == null) // value(t/f), value(t/f,o)
			{
				try {
					truetest(params[0]);
				} catch (final Exception e) {
					throw new ConstructionException(Global.name("exception.notfound"));
				}
				if (NParams == 2) {
					o = C.find(params[1]);
					if (o == null) {
                                            throw new ConstructionException(Global.name("exception.notfound"));
                                        }
					o.setShowValue(truetest(params[0]));
				} else {
					C.ShowValues = true;
				}
			} else if (NParams == 2) // value(o,x)
			{
				if (o instanceof SegmentObject) {
					final SegmentObject os = (SegmentObject) o;
					if (!os.canFix()) {
                                            throw new ConstructionException(Global.name("exception.canfix"));
                                        }
					os.setFixed(true, params[1]);
					os.validate();
				} else if (o instanceof CircleObject) {
					final CircleObject os = (CircleObject) o;
					if (!os.canFix()) {
                                            throw new ConstructionException(Global.name("exception.canfix"));
                                        }
					os.setFixed(true, params[1]);
					os.validate();
				} else if (o instanceof FixedCircleObject) {
					final FixedCircleObject os = (FixedCircleObject) o;
					os.setFixed(params[1]);
					os.validate();
				} else if (o instanceof AngleObject) {
					final AngleObject os = (AngleObject) o;
					if (!os.canFix()) {
                                            throw new ConstructionException(Global.name("exception.canfix"));
                                        }
					os.setFixed(params[1]);
					os.validate();
				} else if (o instanceof FixedAngleObject) {
					final FixedAngleObject os = (FixedAngleObject) o;
					os.setFixed(params[1]);
					os.validate();
				} else if (o instanceof ExpressionObject) {
					final ExpressionObject os = (ExpressionObject) o;
					os.setFixed(params[1]);
					os.validate();
				} else {
                                    throw new ConstructionException(Global.name("exception.parameter"));
                                }
			} else if (NParams == 3) // value(P,x,y)
			{
				if (o instanceof PointObject) {
					final PointObject os = (PointObject) o;
					if (!os.moveablePoint()) {
                                            throw new ConstructionException(Global.name("exception.canfix"));
                                        }  
					os.setFixed(params[1], params[2]);
					os.validate();
				} else {
                                    throw new ConstructionException(Global.name("exception.parameter"));
                                }
			} else {
                            throw new ConstructionException(Global.name("exception.parameter"));
                        }
			return;
		}

		// Look for the normal construction functions:
		final int i = findFunction(function, zc);
		if (i >= 0) {
			for (int j = 0; j < NParams; j++) {
				params[j] = extend(params[j]);
			}
			OCs[i].construct(C, name, params, NParams);

			// the following are for macro definition in scripts
			if (Parameter) {
				if (VN >= V.size()) {
                                    throw new ConstructionException(Global.name("exception.macroparameter"));
                                }
				final ConstructionObject o = C.lastButN(0);
				o.setMainParameter(true);
				if (o.isMainParameter()) {
					C.addParameter(o);
					C.Prompts.addElement(comment);
				} else {
                                    throw new ConstructionException(Global.name("exception.macroparameter"));
                                }
			} else if (Target) {
				if (VN + 1 != V.size()) {
                                    throw new ConstructionException(Global.name("exception.macrotarget"));
                                }
				final ConstructionObject o = C.lastButN(0);
				o.setTarget(true);
				C.addTarget(o);
			} else if (Prompt) {
				final ConstructionObject o = C.lastButN(0);
				C.PromptFor.addElement(o.getName());
			}

			return;
		}

		// Finally, look for macros:
		final Macro m = zc.chooseMacro(function);
		if (m == null) {
			throw new ConstructionException(Global.name("exception.function"));
		}
		MR.setMacro(m, zc);

//                System.out.println("nom:"+m.getName());

                MR.run(zc, C, name, params, NParams);

		// Only for macro definition in scripts
		if (Target) {
			final ConstructionObject o = C.find(name);
			if (o == null) {
                            throw new ConstructionException(Global.name("exception.macrotarget"));
                        }
			o.setTarget(true);
			C.addTarget(o);
		} else if (Prompt) {
			final ConstructionObject o = C.find(name);
			if (o != null) {
                            C.PromptFor.addElement(o.getName());
                        }
		}

	}

	public void interpret(final ZirkelCanvas zc, final String s)
	throws ConstructionException {
		interpret(zc, s, "");
	}

	static public boolean truetest(String s) throws ConstructionException {
		s = s.toLowerCase();
		if (s.equals("true") || s.equals(Global.name("true"))) {
                    return true;
                }
		if (s.equals("false") || s.equals(Global.name("false"))) {
                    return false;
                }
		throw new ConstructionException(Global.name("exception.boolean"));
	}

	static public boolean truecheck(String s) {
		s = s.toLowerCase();
		if (s.equals("true") || s.equals(Global.name("true"))) {
                    return true;
                }
		if (s.equals("false") || s.equals(Global.name("false"))) {
                    return true;
                }
		return false;
	}

	static public boolean test(final String f, final String tag, final String s) {
		return f.equalsIgnoreCase(Global.name(tag + "." + s, ""))
		|| f.equals(Global.name(tag + ".short." + s, ""))
		|| f.equals(Global.name(tag + "." + s + ".short", ""))
		|| f.equalsIgnoreCase(s);
	}

	static public int startTest(final String tag, final String s) {
		int i = startTest0(Global.name(tag, tag).toLowerCase(), s.toLowerCase());
		if (i > 0) {
                    return i;
                }
		i = startTest0(Global.name(tag + ".short", tag), s);
		if (i > 0) {
                    return i;
                }
		return startTest0(tag, s.toLowerCase());
	}

	static public int startTest0(final String tag, final String s) {
		if (s.startsWith(tag + " ")) {
                    return tag.length() + 1;
                }
		return -1;
	}

	static public boolean ptest(final String f, final String s) {
		return test(f, "function", s);
	}

	static public boolean nametest(final String f, final String s) {
		return test(f, "name", s);
	}

	/**
	 * Works like find, but can interpret c(k), p1(s), p2(s) etc.
	 */
	public String extend(String s) {
		if (s.startsWith("c(") && s.endsWith(")")) {
			s = s.substring(2, s.length() - 1);
			final ConstructionObject o = C.find(s);
			if (o instanceof PrimitiveCircleObject) {
                            return ((PrimitiveCircleObject) o).getP1().getName();
                        }
		} else if (s.startsWith("a(") && s.endsWith(")")) {
			s = s.substring(2, s.length() - 1);
			final ConstructionObject o = C.find(s);
			if (o instanceof TwoPointLineObject) {
                            return ((TwoPointLineObject) o).getP1().getName();
                        }
		} else if (s.startsWith("b(") && s.endsWith(")")) {
			s = s.substring(2, s.length() - 1);
			final ConstructionObject o = C.find(s);
			if (o instanceof TwoPointLineObject) {
                            return ((TwoPointLineObject) o).getP2().getName();
                        }
		}
		return s;
	}

	MacroRunner MR = new MacroRunner();

	static ObjectConstructor OCs[] = { new PointConstructor(),
		new SegmentConstructor(), new LineConstructor(),
		new RayConstructor(), new CircleConstructor(),
		new IntersectionConstructor(), new ParallelConstructor(),
		new PlumbConstructor(), new MidpointConstructor(),
		new AngleConstructor(), new AreaConstructor(),
		new QuadricConstructor(), new ExpressionConstructor(),
		new FunctionConstructor() };

	static public String ONs[] = { "point", "segment", "line", "ray", "circle",
		"intersection", "parallel", "plumb", "midpoint", "angle", "area",
		"quadric", "expression", "function","text" };

	static public int findFunction(final String function, final ZirkelCanvas zc) {
		for (int i = 0; i < OCs.length; i++) {
			if (nametest(function, OCs[i].getTag()) && zc.enabled(ONs[i])) {
                            return i;
                        }
		}
		return -1;
	}

}