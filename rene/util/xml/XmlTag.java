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

public class XmlTag {
	protected String Tag = "";
	String Param[];
	String Value[];
	int N = 0;

	public XmlTag(final String s) {
		int n = 0;
		int k = 0;
		n = skipBlanks(s, n);
		while (n < s.length()) {
			n = endItem(s, n);
			k++;
			n = skipBlanks(s, n);
		}
		if (k == 0)
			return;
		n = 0;
		n = skipBlanks(s, n);
		int m = endItem(s, n);
		Tag = s.substring(n, m);
		n = m;
		N = k - 1;
		Param = new String[N];
		Value = new String[N];
		for (int i = 0; i < N; i++) {
			n = skipBlanks(s, n);
			m = endItem(s, n);
			final String p = s.substring(n, m);
			n = m;
			final int kp = p.indexOf('=');
			if (kp >= 0) {
				Param[i] = p.substring(0, kp);
				Value[i] = XmlTranslator.toText(p.substring(kp + 1));
				if (Value[i].startsWith("\"") && Value[i].endsWith("\"")) {
					Value[i] = Value[i].substring(1, Value[i].length() - 1);
				} else if (Value[i].startsWith("\'") && Value[i].endsWith("\'")) {
					Value[i] = Value[i].substring(1, Value[i].length() - 1);
				}
			} else {
				Param[i] = p;
				Value[i] = "";
			}
		}
	}

	int skipBlanks(final String s, int n) {
		while (n < s.length()) {
			final char c = s.charAt(n);
			if (c == ' ' || c == '\t' || c == '\n')
				n++;
			else
				break;
		}
		return n;
	}

	int endItem(final String s, int n) {
		while (n < s.length()) {
			final char c = s.charAt(n);
			if (c == ' ' || c == '\t' || c == '\n')
				break;
			if (c == '\"') {
				n++;
				while (true) {
					if (n >= s.length())
						return n;
					if (s.charAt(n) == '\"')
						break;
					n++;
				}
			} else if (c == '\'') {
				n++;
				while (true) {
					if (n >= s.length())
						return n;
					if (s.charAt(n) == '\'')
						break;
					n++;
				}
			}
			n++;
		}
		return n;
	}

	public String name() {
		return Tag;
	}

	public int countParams() {
		return N;
	}

	public String getParam(final int i) {
		return Param[i];
	}

	public String getValue(final int i) {
		return Value[i];
	}

	public boolean hasParam(final String param) {
		for (int i = 0; i < N; i++)
			if (Param[i].equals(param))
				return true;
		return false;
	}

	public boolean hasTrueParam(final String param) {
		for (int i = 0; i < N; i++)
			if (Param[i].equals(param)) {
				if (Value[i].equals("true"))
					return true;
				return false;
			}
		return false;
	}

	public String getValue(final String param) {
		for (int i = 0; i < N; i++)
			if (Param[i].equals(param))
				return Value[i];
		return null;
	}
}
