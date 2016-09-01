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
 
 
 package rene.util.xml;

import java.io.PrintWriter;
import java.util.Vector;

import rene.util.parser.StringParser;

public class XmlWriter {
	PrintWriter Out;

	public XmlWriter(final PrintWriter o) {
		Out = o;
	}

	public void printTag(final String tag, final String content) {
		startTag(tag);
		print(content);
		endTag(tag);
	}

	public void printTagNewLine(final String tag, final String content) {
		printTag(tag, content);
		Out.println();
	}

	public void printTag(final String tag, final String arg,
			final String value, final String content) {
		startTag(tag, arg, value);
		print(content);
		endTag(tag);
	}

	public void printTagNewLine(final String tag, final String arg,
			final String value, final String content) {
		printTag(tag, arg, value, content);
		Out.println();
	}

	public void startTag(final String tag) {
		Out.print("<");
		Out.print(tag);
		Out.print(">");
	}

	public void startTag(final String tag, final String arg, final String value) {
		Out.print("<");
		Out.print(tag);
		printArg(arg, value);
		Out.print(">");
	}

	public void finishTag(final String tag, final String arg, final String value) {
		Out.print("<");
		Out.print(tag);
		printArg(arg, value);
		Out.println("/>");
	}

	public void finishTag(final String tag) {
		Out.print("<");
		Out.print(tag);
		Out.print("/>");
	}

	public void finishTagNewLine(final String tag) {
		Out.print("<");
		Out.print(tag);
		Out.println("/>");
	}

	public void startTagStart(final String tag) {
		Out.print("<");
		Out.print(tag);
	}

	public void startTagEnd() {
		Out.print(">");
	}

	public void finishTag() {
		Out.print("/>");
	}

	public void finishTagNewLine() {
		Out.println("/>");
	}

	public void startTagEndNewLine() {
		Out.println(">");
	}

	public void printArg(final String arg, final String value) {
		Out.print(" ");
		print(arg);
		Out.print("=\"");
		print(value);
		Out.print("\"");
	}

	public void startTagNewLine(final String tag, final String arg,
			final String value) {
		startTag(tag, arg, value);
		Out.println();
	}

	public void startTagNewLine(final String tag) {
		startTag(tag);
		Out.println();
	}

	public void endTag(final String tag) {
		Out.print("</");
		Out.print(tag);
		Out.print(">");
	}

	public void endTagNewLine(final String tag) {
		endTag(tag);
		Out.println();
	}

	public void println() {
		Out.println();
	}

	public void print(final String s) {
		Out.print(XmlTranslator.toXml(s));
	}

	public void println(final String s) {
		Out.println(XmlTranslator.toXml(s));
	}

	public void printEncoding(final String s) {
		if (s.equals(""))
			Out.println("<?xml version=\"1.0\"?>");
		else
			Out.println("<?xml version=\"1.0\" encoding=\"" + s + "\"?>");
	}

	public void printXml() {
		printEncoding("");
	}

	public void printEncoding() {
		printEncoding("utf-8");
	}

	public void printXls(final String s) {
		Out.println("<?xml-stylesheet href=\"" + s + "\" type=\"text/xsl\"?>");
	}

	public void printParagraphs(String s, final int linelength) {
		final StringParser p = new StringParser(s);
		final Vector v = p.wrapwords(linelength);
		for (int i = 0; i < v.size(); i++) {
			startTag("P");
			s = (String) v.elementAt(i);
			final StringParser q = new StringParser(s);
			final Vector w = q.wraplines(linelength);
			for (int j = 0; j < w.size(); j++) {
				if (j > 0)
					println();
				s = (String) w.elementAt(j);
				print(s);
			}
			endTagNewLine("P");
		}
	}

	public void printDoctype(final String top, final String dtd) {
		Out.print("<!DOCTYPE ");
		Out.print(top);
		Out.print(" SYSTEM \"");
		Out.print(dtd);
		Out.println("\">");
	}

	public void close() {
		Out.close();
	}
}
