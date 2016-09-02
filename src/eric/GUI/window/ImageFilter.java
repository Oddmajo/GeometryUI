/* 
 
Copyright 2006 Eric Hakenholz

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
 
 
 package eric.GUI.window;

import eric.*;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {

	@Override
	public boolean accept(final File f) {
		if (f.isDirectory()) {
			return true;
		}
		final String extension = this.getExtension(f);
		if (extension != null) {
			if (extension.equals("tiff") || extension.equals("tif")
					|| extension.equals("svg") || extension.equals("gif")
					|| extension.equals("jpeg") || extension.equals("jpg")
					|| extension.equals("eps") || extension.equals("png")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "Images";
	}

	public String getExtension(final File f) {
		String ext = null;
		final String s = f.getName();
		final int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}
