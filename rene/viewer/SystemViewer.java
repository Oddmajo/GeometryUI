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
 
 
 package rene.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.io.PrintWriter;

public class SystemViewer extends Viewer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextArea T;

	public SystemViewer() {
		super("dummy");
		setLayout(new BorderLayout());
		add("Center", T = new TextArea());
	}

	@Override
	public void appendLine(final String s) {
		T.append(s + "\n");
	}

	@Override
	public void appendLine(final String s, final Color c) {
		appendLine(s);
	}

	@Override
	public void append(final String s) {
		T.append(s);
	}

	@Override
	public void append(final String s, final Color c) {
		append(s);
	}

	@Override
	public void setText(final String s) {
		T.setText(s);
	}

	@Override
	public void doUpdate(final boolean showlast) {
		T.repaint();
	}

	@Override
	public void setFont(final Font s) {
		T.setFont(s);
	}

	@Override
	public void save(final PrintWriter fo) {
		fo.print(T.getText());
		fo.flush();
	}
}
