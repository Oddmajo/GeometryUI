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
 
 
 package rene.util;

import java.io.File;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import rene.util.sort.SortObject;
import rene.util.sort.Sorter;

class SortFile extends File implements SortObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String S;
	static int SortBy = 0;
	final public static int NAME = 0, DATE = 1;

	public SortFile(final File dir, final String name) {
		super(dir, name);
		try {
			S = getCanonicalPath().toUpperCase();
		} catch (final Exception e) {
			S = "";
		}
	}

	public int compare(final SortObject o) {
		final SortFile f = (SortFile) o;
		if (SortBy == DATE) {
			final long n = f.lastModified();
			final long m = lastModified();
			if (n < m)
				return -1;
			if (n > m)
				return 1;
			return 0;
		}
		return -f.S.compareTo(S);
	}
}

class FileFilter {
	char F[][];

	public FileFilter(final String s) {
		final StringTokenizer t = new StringTokenizer(s);
		final int n = t.countTokens();
		F = new char[n][];
		for (int i = 0; i < n; i++) {
			F[i] = t.nextToken().toCharArray();
		}
	}

	public char[] filter(final int i) {
		return F[i];
	}

	public int filterCount() {
		return F.length;
	}
}

/**
 * This class parses a subtree for files that match a pattern. The pattern may
 * contain one or more * and ? as usual. The class delivers an enumerator for
 * the files, or may be subclassed to handle the files directly. The routines
 * directory and file can be used to return, if more scanning is necessary.
 */

public class FileList {
	Vector V = new Vector(), Vdir = new Vector();
	boolean Stop;
	boolean Recurse;
	String Dir, Filter;
	boolean UseCase = false;

	public FileList(final String dir, final String filter, final boolean recurse) {
		Stop = false;
		Recurse = recurse;
		Dir = dir;
		Filter = filter;
		if (Dir.equals("-")) {
			Dir = ".";
			Recurse = false;
		} else if (Dir.startsWith("-")) {
			Dir = Dir.substring(1);
			Recurse = false;
		}
	}

	public FileList(final String dir, final String filter) {
		this(dir, filter, true);
	}

	public FileList(final String dir) {
		this(dir, "*", true);
	}

	public void setCase(final boolean usecase) {
		UseCase = usecase;
	}

	public void search() {
		Stop = false;
		final File file = new File(Dir);
		if (!UseCase)
			Filter = Filter.toLowerCase();
		if (file.isDirectory())
			find(file, new FileFilter(Filter));
	}

	void find(final File dir, final FileFilter filter) {
		if (!directory(dir))
			return;
		final String list[] = dir.list();
		loop: for (int i = 0; i < list.length; i++) {
			final SortFile file = new SortFile(dir, list[i]);
			if (file.isDirectory()) {
				Vdir.addElement(file);
				if (Recurse)
					find(file, filter);
			} else {
				String filename = file.getName();
				if (!UseCase)
					filename = filename.toLowerCase();
				final char fn[] = filename.toCharArray();
				for (int j = 0; j < filter.filterCount(); j++) {
					if (match(fn, 0, filter.filter(j), 0)) {
						Stop = !file(file);
						if (Stop)
							break loop;
						V.addElement(file);
					}
				}
			}
			if (Stop)
				break;
		}
		parsed(dir);
	}

	boolean match(final char filename[], final int n, final char filter[],
			final int m) {
		if (filter == null)
			return true;
		if (m >= filter.length)
			return n >= filename.length;
			if (n >= filename.length)
				return m == filter.length - 1 && filter[m] == '*';
			if (filter[m] == '?') {
				return match(filename, n + 1, filter, m + 1);
			}
			if (filter[m] == '*') {
				if (m == filter.length - 1)
					return true;
				for (int i = n; i < filename.length; i++) {
					if (match(filename, i, filter, m + 1))
						return true;
				}
				return false;
			}
			if (filter[m] == filename[n])
				return match(filename, n + 1, filter, m + 1);
			return false;
	}

	/**
	 * Return an Enumeration with the files.
	 */
	public Enumeration files() {
		return V.elements();
	}

	/**
	 * Return an Enumeration with the directories.
	 */
	public Enumeration dirs() {
		return Vdir.elements();
	}

	/**
	 * @return The number of files found.
	 */
	public int size() {
		return V.size();
	}

	/**
	 * Sort the result.
	 */
	public void sort() {
		int i, n = V.size();
		SortObject v[] = new SortObject[n];
		for (i = 0; i < n; i++)
			v[i] = (SortFile) V.elementAt(i);
		Sorter.sort(v);
		for (i = 0; i < n; i++)
			V.setElementAt(v[i], i);
		n = Vdir.size();
		v = new SortObject[n];
		for (i = 0; i < n; i++)
			v[i] = (SortFile) Vdir.elementAt(i);
		Sorter.sort(v);
		for (i = 0; i < n; i++)
			Vdir.setElementAt(v[i], i);
	}

	public void sort(final int type) {
		SortFile.SortBy = type;
		sort();
		SortFile.SortBy = SortFile.NAME;
	}

	/**
	 * @param file
	 *            The directory that has been found.
	 * @return false if recursion should stop here. (i.e. that directory needs
	 *         not be parsed).
	 */
	protected boolean directory(final File dir) {
		return true;
	}

	/**
	 * @param file
	 *            The file that has been found.
	 * @return false if you need no more file at all.
	 */
	protected boolean file(final File file) {
		return true;
	}

	/**
	 * @param parsed
	 *            The directory that has been parsed.
	 */
	protected void parsed(final File dir) {
	}

	/**
	 * This stops the search from other threads.
	 */
	public void stopIt() {
		Stop = true;
	}

	/**
	 * Returns a canonical version of the directory
	 */
	public String getDir() {
		final File dir = new File(Dir);
		try {
			return (dir.getCanonicalPath());
		} catch (final Exception e) {
			return "Dir does not exist!";
		}
	}
}
