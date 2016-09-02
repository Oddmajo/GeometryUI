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
 
 
 package rene.dialogs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;

import eric.JEricPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.CloseFrame;
import rene.gui.DoActionListener;
import rene.gui.Global;
import rene.gui.HistoryTextField;
import rene.gui.HistoryTextFieldChoice;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.gui.TextFieldAction;
import rene.lister.Lister;
import rene.lister.ListerMouseEvent;
import rene.util.FileList;
import rene.util.FileName;
import rene.util.MyVector;

class DirFieldListener implements DoActionListener {
	MyFileDialog T;

	public DirFieldListener(final MyFileDialog t) {
		T = t;
	}

	public void doAction(final String o) {
		T.setFile(o);
	}

	public void itemAction(final String o, final boolean flag) {
	}
}

/**
 * This is a file dialog. It is easy to handle, remembers its position and size,
 * and performs pattern matching. The calls needs the rene.viewer.* class,
 * unless you replace Lister with List everywhere. Moreover it needs the
 * rene.gui.Global class to store field histories and determine the background
 * color. Finally, it uses the FileList class to get the list of files.
 * <p>
 * The dialog does never check for files to exists. This must be done by the
 * application.
 * <p>
 * There is a static main method, which demonstrates everything.
 */

public class MyFileDialog extends CloseDialog implements ItemListener,
FilenameFilter, MouseListener { // java.awt.List Dirs,Files;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Lister Dirs, Files;
	HistoryTextField DirField, FileField, PatternField;
	HistoryTextFieldChoice DirHistory, FileHistory;
	TextField Chosen;
	String CurrentDir = ".";
	boolean Aborted = true;
	String DirAppend = "", PatternAppend = "", FileAppend = "";
	Button Home;
	Frame F;

	/**
	 * @param title
	 *            The dialog title.
	 * @param action
	 *            The button string for the main action (e.g. Load)
	 * @param saving
	 *            True, if this is a saving dialog.
	 */
	public MyFileDialog(final Frame f, final String title, final String action,
			final boolean saving, final boolean help) {
		super(f, title, true);
		F = f;
		setLayout(new BorderLayout());

		// title prompt
		add("North", new Panel3D(Chosen = new TextFieldAction(this, "")));
		Chosen.setEditable(false);

		// center panels
		final JEricPanel center = new MyPanel();
		center.setLayout(new GridLayout(1, 2, 5, 0));
		Dirs = new Lister();
		if (Global.NormalFont != null)
			Dirs.setFont(Global.NormalFont);
		Dirs.addActionListener(this);
		Dirs.setMode(false, false, false, false);
		center.add(Dirs);
		Files = new Lister();
		if (Global.NormalFont != null)
			Files.setFont(Global.NormalFont);
		Files.addActionListener(this);
		Files.setMode(false, false, true, false);
		center.add(Files);
		add("Center", new Panel3D(center));

		// south panel
		final JEricPanel south = new MyPanel();
		south.setLayout(new BorderLayout());

		final JEricPanel px = new MyPanel();
		px.setLayout(new BorderLayout());

		final JEricPanel p0 = new MyPanel();
		p0.setLayout(new GridLayout(0, 1));

		final JEricPanel p1 = new MyPanel();
		p1.setLayout(new BorderLayout());
		p1.add("North", linePanel(new MyLabel(Global.name("myfiledialog.dir")),
				DirField = new HistoryTextField(this, "Dir", 32) {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			@Override
			public boolean filterHistory(final String name)
			// avoid a Windows bug with C: instead of c:
			{
				if (name.length() < 2)
					return true;
				if (name.charAt(1) == ':'
					&& Character.isUpperCase(name.charAt(0)))
					return false;
				return true;
			}
		}));
		DirField.setText(".");
		p1.add("South", linePanel(new MyLabel(Global.name(
				"myfiledialog.olddirs", "")),
				DirHistory = new HistoryTextFieldChoice(DirField)));
		p0.add(new Panel3D(p1));

		final JEricPanel p2 = new MyPanel();
		p2.setLayout(new BorderLayout());
		p2.add("North", linePanel(
				new MyLabel(Global.name("myfiledialog.file")),
				FileField = new HistoryTextField(this, "File")));
		p2.add("South", linePanel(new MyLabel(Global.name(
				"myfiledialog.oldfiles", "")),
				FileHistory = new HistoryTextFieldChoice(FileField)));
		p0.add(new Panel3D(p2));

		px.add("Center", p0);

		px.add("South", new Panel3D(linePanel(new MyLabel(Global
				.name("myfiledialog.pattern")),
				PatternField = new HistoryTextField(this, "Pattern"))));
		PatternField.setText("*");

		south.add("Center", px);

		final JEricPanel buttons = new MyPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		buttons.add(Home = new ButtonAction(this, Global.name(
//				"myfiledialog.home", "Home"), "Home"));
		buttons.add(new ButtonAction(this, Global.name("myfiledialog.mkdir",
		"Create Directory"), "Create"));
		buttons.add(new ButtonAction(this, Global.name("myfiledialog.back",
		"Back"), "Back"));
		buttons.add(new MyLabel(""));
		buttons.add(new ButtonAction(this, action, "Action"));
		buttons.add(new ButtonAction(this, Global.name("abort"), "Close"));
		if (help) {
			addHelp(buttons, "filedialog");
		}

		south.add("South", buttons);

		add("South", new Panel3D(south));

		// set sizes
		pack();
		setSize("myfiledialog");
		addKeyListener(this);
		DirField.addKeyListener(this);
		DirField.setTrigger(true);
		FileHistory.setDoActionListener(new DirFieldListener(this));
		PatternField.addKeyListener(this);
		PatternField.setTrigger(true);
		FileField.addKeyListener(this);
		FileField.setTrigger(true);
		Home.addMouseListener(this);
	}

	JEricPanel linePanel(final Component x, final Component y) {
		final JEricPanel p = new MyPanel();
		p.setLayout(new GridLayout(1, 0));
		p.add(x);
		p.add(y);
		return p;
	}

	public MyFileDialog(final Frame f, final String title, final String action,
			final boolean saving) {
		this(f, title, action, saving, false);
	}

	FileDialog FD;

	public MyFileDialog(final Frame f, final String title, final boolean saving) {
		super(f, "", true);
		FD = new FileDialog(f, title, saving ? FileDialog.SAVE
				: FileDialog.LOAD);
	}

	boolean HomeShiftControl = false;

	public void mousePressed(final MouseEvent e) {
		HomeShiftControl = e.isShiftDown() && e.isControlDown();
	}

	public void mouseReleased(final MouseEvent e) {
	}

	public void mouseClicked(final MouseEvent e) {
	}

	public void mouseEntered(final MouseEvent e) {
	}

	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void doAction(final String o) {
		if (o.equals("Dir") || o.equals("Pattern")) {
			if (updateDir())
				updateFiles();
			PatternField.remember(PatternField.getText());
		} else if (o.equals("File") || o.equals("Action")) {
			if (FileField.getText().equals(""))
				return;
			leave();
		} else if (o.equals("Home")) {
			if (HomeShiftControl) {
				final String s = Global.getParameter("myfiledialog.homdir", "");
				if (s.equals(""))
					Global.setParameter("myfiledialog.homdir", DirField
							.getText());
				else
					Global.setParameter("myfiledialog.homdir", "");
			}
			try {
				final String s = Global.getParameter("myfiledialog.homdir", "");
				if (s.equals("")) {
					final String s1 = System.getProperty("user.home");
					final String s2 = Global.name("myfiledialog.windowshome",
					"");
					final String s3 = Global.name("myfiledialog.homedir", "");
					final String sep = System.getProperty("file.separator");
					if (new File(s1 + sep + s2 + sep + s3).exists())
						DirField.setText(s1 + sep + s2 + sep + s3);
					else if (new File(s1 + sep + s2).exists())
						DirField.setText(s1 + sep + s2);
					else
						DirField.setText(s1);
				} else
					DirField.setText(s);
				updateDir();
				updateFiles();
			} catch (final Exception e) {
			}
		} else if (o.equals("Create")) {
			try {
				final File f = new File(DirField.getText());
				if (!f.exists()) {
					f.mkdir();
				}
				updateDir();
				updateFiles();
			} catch (final Exception e) {
			}
		} else if (o.equals("Back")) {
			final String dir = getUndo();
			if (!dir.equals("")) {
				DirField.setText(dir);
				updateDir();
				updateFiles();
			}
		} else
			super.doAction(o);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == Dirs) {
			final String s = Dirs.getSelectedItem();
			if (s == null)
				return;
			if (s.equals(".."))
				dirup();
			else
				dirdown(s);
		}
		if (e.getSource() == Files) {
			if (e instanceof ListerMouseEvent) {
				final ListerMouseEvent em = (ListerMouseEvent) e;
				if (em.clickCount() >= 2)
					leave();
				else {
					final String s = Files.getSelectedItem();
					if (s != null)
						FileField.setText(s);
				}
			}
		} else
			super.actionPerformed(e);
	}

	public void setFile(final String s) {
		DirField.setText(FileName.path(s));
		FileField.setText(FileName.filename(s));
		// System.out.println(s);
		update(false);
	}

	public void dirup() {
		DirField.setText(FileName.path(CurrentDir));
		if (DirField.getText().equals(""))
			DirField.setText("" + File.separatorChar);
		if (updateDir())
			updateFiles();
	}

	public void dirdown(final String subdir) {
		DirField.setText(CurrentDir + File.separatorChar + subdir);
		if (updateDir())
			updateFiles();
	}

	/**
	 * Leave the dialog and remember settings.
	 */
	void leave() {
		if (FD != null)
			return;
		if (!FileField.getText().equals(""))
			Aborted = false;
		if (!Aborted) {
			noteSize("myfiledialog");
			DirField.remember(DirField.getText());
			DirField.saveHistory("myfiledialog.dir.history" + DirAppend);
			PatternField.saveHistory("myfiledialog.pattern.history"
					+ PatternAppend);
			FileField.remember(getFilePath());
			FileField.saveHistory("myfiledialog.file.history" + FileAppend);
		}
		doclose();
	}

	/**
	 * Update the directory list.
	 * 
	 * @return if the current content of DirField is indeed a directory.
	 */
	public boolean updateDir() {
		if (FD != null)
			return true;
		final File dir = new File(DirField.getText() + File.separatorChar);
		if (!dir.isDirectory())
			return false;
		try {
			final String s = FileName.canonical(dir.getCanonicalPath());
			addUndo(s);
			DirField.setText(s);
			Chosen.setText(FileName.chop(16, DirField.getText()
					+ File.separatorChar + PatternField.getText(), 48));
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	MyVector Undo = new MyVector();

	/**
	 * Note the directory in a history list.
	 */
	public void addUndo(final String dir) {
		if (Undo.size() > 0
				&& ((String) Undo.elementAt(Undo.size() - 1)).equals(dir))
			return;
		Undo.addElement(dir);
	}

	/**
	 * Get the undo directory and remove it.
	 */
	public String getUndo() {
		if (Undo.size() < 2)
			return "";
		final String s = (String) Undo.elementAt(Undo.size() - 2);
		Undo.truncate(Undo.size() - 1);
		return s;
	}

	/**
	 * Update the file list.
	 */
	public void updateFiles() {
		if (FD != null)
			return;
		final File dir = new File(DirField.getText());
		if (!dir.isDirectory())
			return;
		CurrentDir = DirField.getText();
		if (PatternField.getText().equals(""))
			PatternField.setText("*");
		try {
			Files.clear();
			Dirs.clear();
			final FileList l = new FileList(DirField.getText(), PatternField
					.getText(), false);
			l.setCase(Global.getParameter("filedialog.usecaps", false));
			l.search();
			l.sort();
			Enumeration e = l.files();
			while (e.hasMoreElements()) {
				final File f = (File) e.nextElement();
				Files.addElement(FileName.filename(f.getCanonicalPath()));
			}
			Dirs.addElement("..");
			e = l.dirs();
			while (e.hasMoreElements()) {
				final File f = (File) e.nextElement();
				Dirs.addElement(FileName.filename(f.getCanonicalPath()));
			}
		} catch (final Exception e) {
		}
		Dirs.updateDisplay();
		Files.updateDisplay();
		Files.requestFocus();
	}

	public void setDirectory(final String dir) {
		if (FD != null)
			FD.setDirectory(dir);
		else
			DirField.setText(dir);
	}

	public void setPattern(final String pattern) {
		if (FD != null) {
			FD.setFilenameFilter(this); // does not work
			final String s = pattern.replace(' ', ';');
			FD.setFile(s);
		} else
			PatternField.setText(pattern);
	}

	public void setFilePath(final String file) {
		if (FD != null) {
			FD.setFile(file);
			return;
		}
		final String dir = FileName.path(file);
		if (!dir.equals("")) {
			DirField.setText(dir);
			FileField.setText(FileName.filename(file));
		} else
			FileField.setText(file);
	}

	/**
	 * Check, if the dialog was aborted.
	 */
	@Override
	public boolean isAborted() {
		if (FD != null)
			return FD.getFile() == null || FD.getFile().equals("");
		else
			return Aborted;
	}

	/**
	 * @return The file plus its path.
	 */
	public String getFilePath() {
		if (FD != null) {
			if (FD.getFile() != null)
				return FD.getDirectory() + FD.getFile();
			else
				return "";
		}
		final String file = FileField.getText();
		if (!FileName.path(file).equals(""))
			return file;
		else
			return CurrentDir + File.separatorChar + FileField.getText();
	}

	/**
	 * This should be called at the start.
	 */
	public void update(final boolean recent) {
		if (FD != null)
			return;
		loadHistories(recent);
		setFilePath(FileField.getText());
		if (updateDir())
			updateFiles();
		Aborted = true;
	}

	public void update() {
		update(true);
	}

	@Override
	public void setVisible(final boolean flag) {
		if (FD != null)
			FD.setVisible(flag);
		else
			super.setVisible(flag);
	}

	@Override
	public void center(final Frame f) {
		if (FD != null)
			CloseDialog.center(f, FD);
		else
			super.center(f);
	}

	public static void main(final String args[]) {
		final Frame f = new CloseFrame() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void doclose() {
				System.exit(0);
			}
		};
		f.setSize(500, 500);
		f.setLocation(400, 400);
		f.setVisible(true);
		final MyFileDialog d = new MyFileDialog(f, "Title", "Save", false);
		d.center(f);
		d.update();
		d.setVisible(true);
	}

	@Override
	public void focusGained(final FocusEvent e) {
		FileField.requestFocus();
	}

	/**
	 * Can be overwritten by instances to accept only some files.
	 */
	public boolean accept(final File dir, final String file) {
		return true;
	}

	public void loadHistories(final boolean recent) {
		if (FD != null)
			return;
		DirField.loadHistory("myfiledialog.dir.history" + DirAppend);
		DirHistory.update();
		if (recent)
			setDirectory(DirHistory.getRecent());
		if (updateDir())
			updateFiles();
		PatternField
		.loadHistory("myfiledialog.pattern.history" + PatternAppend);
		FileField.loadHistory("myfiledialog.file.history" + FileAppend);
		FileHistory.update();
	}

	public void loadHistories() {
		loadHistories(true);
	}

	/**
	 * Loads the histories from the configuration file. If you want a unique
	 * history for your instance, you need to give a string unique for your
	 * instance. There are three types of histories.
	 * 
	 * @param dir
	 * @param pattern
	 * @param file
	 * @see loadHistories
	 */
	public void loadHistories(final String dir, final String pattern,
			final String file) {
		setAppend(dir, pattern, file);
		loadHistories();
	}

	/**
	 * Histories are used for the directories, the files and the patterns. The
	 * dialog can use different histories for each instance of this class. If
	 * you want that, you need to determine the history for the instance with a
	 * string, unique for the instance. If a string is empty, "default" is used.
	 * 
	 * @param dir
	 * @param pattern
	 * @param file
	 */
	public void setAppend(final String dir, final String pattern,
			final String file) {
		if (FD != null)
			return;
		if (!dir.equals(""))
			DirAppend = "." + dir;
		else
			DirAppend = ".default";
		if (!pattern.equals(""))
			PatternAppend = "." + pattern;
		else
			PatternAppend = ".default";
		if (!file.equals(""))
			FileAppend = "." + file;
		else
			FileAppend = ".default";
	}

	public void itemStateChanged(final ItemEvent e) {
	}
}
