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
 
 
 package eric;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JFileFilter extends FileFilter {
	// Description et extension acceptée par le filtre
	public String description;
	public String[] extensions;

	// Constructeur à partir de la description et de l'extension acceptée
	public JFileFilter(final String description, final String ext) {
		if (description == null || ext == null) {
			throw new NullPointerException(
			"La description (ou extension) ne peut être null.");
		}
		this.description = description;
		this.extensions = ext.split(",");

	}

	// Implémentation de FileFilter
	@Override
	public boolean accept(final File file) {
		if (file.isDirectory()) {
			return true;
		}
		final String nomFichier = file.getName().toLowerCase();
                for (int i=0;i<extensions.length;i++){
                    if (nomFichier.endsWith(extensions[i])) return true;
                }
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

}