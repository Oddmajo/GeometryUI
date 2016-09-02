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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import rene.gui.Global;

/**
 * Info class. Reads a file "info.txt" or "de_info.txt" etc. that has the
 * structure
 *
 * .subject1 substitute1 substitute2 ... .related subject subject ... Header ...
 *
 * .subject2 ...
 *
 * and displays the text, starting from header, searching for a subject or any
 * of its substitutes. The headers of the related subjects are presented in a
 * choice list. The user can switch to any of it.
 *
 * There is a history and a back button.
 *
 * Moroever, there is a search button, that displays the first subject
 * containing a string and presents all other subjects containing the string in
 * the choice list.
 */
public class JHelpPanel extends JEricPanel {

    private static final long serialVersionUID = 1L;
    MyJTextPane T;
    public static String Subject = "start";
    String Search = null;
    // MyChoice2 L;
    Vector Other = null;
    Vector History = new Vector();
    Vector Related;
    JEricPanel north;
    MyJTextSearch TSearch;

    public JHelpPanel() {
	T = new MyJTextPane(this);
	final JScrollPane scrollPane = new JScrollPane(T);
	scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setAlignmentX(0.0F);
	setAlignmentY(0.0F);

	setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
	setFocusable(false);

	north = new JEricPanel();
	north.setLayout(new javax.swing.BoxLayout(north, javax.swing.BoxLayout.Y_AXIS));
	north.setOpaque(false);
	TSearch = new MyJTextSearch(this);
	Subject = "start";
	fill(true);
	north.add(TSearch);
	north.add(vmargin(2));

	add(north);
	add(scrollPane);
    }

    public void focusTxt() {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		TSearch.JTF.requestFocus();
	    }
	});
    }

    static JEricPanel margin(final int w) {
	final JEricPanel mypan = new JEricPanel();
	fixsize(mypan, w, 1);
	mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
	mypan.setAlignmentX(0F);
	mypan.setAlignmentY(0F);
	mypan.setOpaque(false);
	mypan.setFocusable(false);
	return mypan;
    }

    static JEricPanel vmargin(final int h) {
	final JEricPanel mypan = new JEricPanel();
	fixsize(mypan, 1, h);
	mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
	mypan.setAlignmentX(0F);
	mypan.setAlignmentY(0F);
	mypan.setOpaque(false);
	mypan.setFocusable(false);
	return mypan;
    }

    // Only called by LeftPanelContent init method :
    public void fixPanelSize(final int w, final int h) {
	fixsize(north, w, 24);
	north.revalidate();
	fixsize(TSearch, w, 24);
	fixsize(TSearch.wholepanel, w - 20, 18);
    }

    public static void fixsize(final JComponent cp, final int w, final int h) {
	final Dimension d = new Dimension(w, h);
	cp.setMaximumSize(d);
	cp.setMinimumSize(d);
	cp.setPreferredSize(d);
	cp.setSize(d);
    }

    public void clearSearchTxtField() {
	TSearch.JTF.setText("");
    }

    public final void fill(final boolean WithTextFocus) {
	final boolean vis = TSearch.ispopupvisible();
	if (vis) {
	    TSearch.hidepopup();
	}
	TSearch.L = new MyJPopupMenu(this);
	T.setText("");

	String TXT = "";

	boolean Found = false, Appending = false;
	Related = null;
	Other = new Vector();
	String pair[] = null, lastpair[] = null;
	String lang = Global.name("language", "");
	Vector SearchResults = new Vector();
	String SearchResult = "";
	String FoundTopic = null;
	boolean FirstRun = true, FoundHeader = false;

	String Search1 = Search;
	if (Search != null && Search.length() > 0) {
	    Search1 = Search.substring(0, 1).toUpperCase() + Search.substring(1);
	}

	read: while (true) {
	    try {
		Global.name("codepage.help", "");
		BufferedReader in = null;
		try {
		    in = new BufferedReader(new InputStreamReader(
		    new FileInputStream(Global.getHomeDirectory() + "docs/" + lang + "info.txt"), "UTF8"));
		} catch (final Exception ex) {
		    in = new BufferedReader(new InputStreamReader(
		    new FileInputStream(Global.getHomeDirectory() + "docs/" + lang + "info.txt")));
		}

		boolean newline = false;
		newline: while (true) {
		    String s = in.readLine();
		    if (s == null) {
			break newline;
		    }
		    if (s.startsWith("//")) {
			continue;
		    }
		    s = clear(s);
		    if (!s.startsWith(".") && Search != null && (s.indexOf(Search) >= 0 || s.indexOf(Search1) >= 0)) {
			if (lastpair != null && pair == null && !SearchResult.equals(lastpair[0])) {
			    SearchResults.addElement(lastpair);

			    SearchResult = lastpair[0];
			    if (FoundTopic == null) {
				FoundTopic = lastpair[0];
			    }
			}
		    }
		    interpret: while (true) {
			if (!Appending && s.startsWith(".") && !s.startsWith(".related")) {
			    if (!Found) {
				if (s.startsWith("." + Subject)) {
				    Found = true;
				    Appending = true;
				    continue newline;
				}
				final StringTokenizer t = new StringTokenizer(s);
				while (t.hasMoreElements()) {
				    final String name = t.nextToken();
				    if (name.equals(Subject)) {
					Found = true;
					Appending = true;
					continue newline;
				    }
				}
			    }
			    pair = new String[2];
			    s = s.substring(1);
			    final int n = s.indexOf(' ');
			    if (n > 0) {
				s = s.substring(0, n);
			    }
			    pair[0] = s;
			    continue newline;
			}
			if (Appending) {
			    if (s.startsWith(".related")) {
				s = s.substring(".related".length());
				Related = new Vector();
				final StringTokenizer t = new StringTokenizer(s);
				while (t.hasMoreElements()) {
				    Related.addElement(t.nextToken());
				}
				continue newline;
			    }
			    if (s.startsWith(".")) {
				Appending = false;
				continue interpret;
			    }
			    if (s.trim().equals("")) {
				if (!newline) {
				    // T.append("<br><br>");
				    TXT += "<br><br>";
				}
				newline = true;
			    } else {
				newline = false;
				if (s.startsWith(" ")) {
				    TXT += "<br>";
				    // T.append("<br>");
				}
				// T.append(s+" ");
				TXT += s + " ";
			    }
			} else if (pair != null && !s.startsWith(".")) {
			    pair[1] = s;
			    Other.addElement(pair);
			    lastpair = pair;
			    pair = null;
			    if (Search != null && (s.indexOf(Search) >= 0 || s.indexOf(Search1) >= 0)) {
				if (!SearchResult.equals(lastpair[0])) {
				    SearchResults.addElement(lastpair);
				    SearchResult = lastpair[0];
				    if (!FoundHeader) {
					FoundTopic = lastpair[0];
				    }
				    FoundHeader = true;
				}
			    }
			}
			continue newline;
		    }
		}
		// T.append("<br>");
		TXT += "<br>";
		in.close();
	    } catch (final Exception e) {
		if (!lang.equals("")) {
		    lang = "";
		    continue read;
		} else {
		    // V.appendLine(
		    // Global.name("help.error","Could not find the help file!"));
		    // T.append(Global.name("help.error",
		    // "Could not find the help file!"));
		    TXT += Global.name("help.error", "Could not find the help file!");
		}
	    }
	    if (FoundTopic != null && FirstRun) {
		Subject = FoundTopic;
		SearchResults = new Vector();
		SearchResult = "";
		pair = null;
		lastpair = null;
		Found = false;
		TXT = "";
		// T.setText("");
		// V.setText("");
		FirstRun = false;
		continue read;
	    } else {
		break read;
	    }
	}

	// if (!Found)
	// V.appendLine(Global.name("info.notfound","Topic not found!"));
	if (!Found) {
	    // T.append(Global.name("info.notfound", "Topic not found!"));
	    TXT += Global.name("info.notfound", "Topic not found!");
	}
	if (Search != null) {
	    if (SearchResults.size() > 0) {
		TSearch.L.addI(Global.name("info.searchresults"));
	    } else {
		TSearch.L.addI(Global.name("info.noresults"));
	    }
	} else {
	    TSearch.L.addI(Global.name("info.select"));
	}
	if (Search == null && Related != null) {
	    final Enumeration e = Related.elements();
	    while (e.hasMoreElements()) {
		final String topic = (String) e.nextElement();
		final Enumeration ev = Other.elements();
		while (ev.hasMoreElements()) {
		    final String s[] = (String[]) ev.nextElement();
		    if (s[0].equals(topic)) {
			TSearch.L.addI(s[1]);
			break;
		    }
		}
	    }
	}

	if (Search != null) {
	    final Enumeration e = SearchResults.elements();
	    while (e.hasMoreElements()) {
		final String s[] = (String[]) e.nextElement();
		TSearch.L.addI(s[1]);
	    }
	}
	T.setContent(TXT);
	if (WithTextFocus) {
	    focusTxt();
	}
	T.setCaretPosition(0);
	History.addElement(Subject);
	TSearch.setCount();
	if (vis) {
	    TSearch.showpopup();
	}
    }

    public String clear(String s) {
	/*s = s.replace('ß', ' ');*/
	s = s.replaceAll("__", "");
	return s;
    }
}

class MyComboBoxUI extends MetalComboBoxUI {

    public static ComponentUI createUI(final JComponent c) {
	return new MyComboBoxUI();
    }
}

class MyJTextSearch extends JEricPanel {

    private static final long serialVersionUID = 1L;
    JLabel cntLBL = new JLabel();
    JTextField JTF;
    JHelpPanel JP;
    JButton popBTN, clearBTN;
    JEricPanel txtpanel;
    JEricPanel wholepanel;
    MyJPopupMenu L;
    String old = "";

    @Override
    public void paintComponent(final java.awt.Graphics g) {
	super.paintComponent(g);
	final ImageIcon OffImage = new ImageIcon(getClass().getResource("/eric/GUI/icons/themes/common/helpsearchback.png"));
	final java.awt.Dimension d = this.getSize();
	g.drawImage(OffImage.getImage(), 0, 0, d.width, d.height, this);
    }

    public MyJTextSearch(final JHelpPanel jp) {
	super();
	setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
	setOpaque(false);
	JP = jp;

	L = new MyJPopupMenu(JP);

	txtpanel = new JEricPanel();
	txtpanel.setLayout(new javax.swing.BoxLayout(txtpanel, javax.swing.BoxLayout.X_AXIS));
	txtpanel.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
	txtpanel.setOpaque(true);
	txtpanel.setBackground(Color.WHITE);
	wholepanel = new JEricPanel();
	wholepanel.setLayout(new javax.swing.BoxLayout(wholepanel, javax.swing.BoxLayout.X_AXIS));
	wholepanel.setBorder(BorderFactory.createEmptyBorder());
	wholepanel.setOpaque(false);
	cntLBL.setOpaque(false);
	cntLBL.setText("");
	cntLBL.setFont(new Font("System", 0, 9));
	cntLBL.setHorizontalAlignment(SwingConstants.CENTER);
	cntLBL.setVerticalAlignment(SwingConstants.CENTER);
	JHelpPanel.fixsize(cntLBL, 20, 18);

	JTF = new JTextField();
	JTF.setFont(new Font("System", 0, 11));
	JTF.setForeground(new Color(50, 50, 50));
	JTF.setBackground(new Color(255, 255, 255));
	JTF.setBorder(BorderFactory.createEmptyBorder());
	JTF.setMargin(new java.awt.Insets(0, 0, 1, 0));
	JTF.addKeyListener(new KeyAdapter() {

	    @Override
	    public void keyReleased(final KeyEvent e) {
		if (JTF.getText().equals("")) {
		    clearBTN.setVisible(false);
		} else {
		    clearBTN.setVisible(true);
		}
		if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_UP)) {
		    hidepopup();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    showpopup();
		} else {
		    doSearch(JTF.getText());
		}
	    }
	});
	// JTF.setFocusable(true);
	JTF.addFocusListener(new FocusAdapter() {

	    @Override
	    public void focusGained(final FocusEvent e) {
		JTF.selectAll();
	    }
	});

	final ImageIcon carimg = new ImageIcon(getClass().getResource("/eric/GUI/icons/themes/common/helpsearch1.gif"));
	popBTN = new JButton(carimg);
	popBTN.setBorder(BorderFactory.createEmptyBorder());
	popBTN.setOpaque(true);
	popBTN.setContentAreaFilled(true);
	popBTN.setFocusable(false);
	popBTN.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mousePressed(final MouseEvent e) {
		showpopup();
	    }
	});

	final ImageIcon carimg2 = new ImageIcon(getClass().getResource("/eric/GUI/icons/themes/common/helpsearch2.png"));
	final JButton backBTN = new JButton(carimg2);
	backBTN.setBorder(BorderFactory.createEmptyBorder());
	backBTN.setOpaque(false);
	backBTN.setContentAreaFilled(false);
	backBTN.setFocusable(false);
	backBTN.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mousePressed(final MouseEvent e) {
		final int n = JP.History.size();
		if (n < 2) {
		    return;
		}
		JP.History.removeElementAt(n - 1);
		JHelpPanel.Subject = (String) JP.History.elementAt(n - 2);
		JP.History.removeElementAt(n - 2);
		JP.fill(true);
	    }
	});

	final ImageIcon carimg3 = new ImageIcon(getClass().getResource("/eric/GUI/icons/themes/common/helpsearch3.gif"));
	clearBTN = new JButton(carimg3);
	clearBTN.setBorder(BorderFactory.createEmptyBorder());
	clearBTN.setOpaque(true);
	clearBTN.setContentAreaFilled(true);
	clearBTN.setFocusable(false);
	clearBTN.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(final MouseEvent e) {
		JTF.setText("");
		clearBTN.setVisible(false);
		doSearch("");
	    }
	});
	clearBTN.setVisible(false);
	JHelpPanel.fixsize(popBTN, carimg.getIconWidth(), carimg.getIconHeight());
	JHelpPanel.fixsize(clearBTN, carimg3.getIconWidth(), carimg3.getIconHeight());
	txtpanel.add(cntLBL);
	txtpanel.add(popBTN);
	txtpanel.add(JHelpPanel.margin(3));
	txtpanel.add(JTF);
	txtpanel.add(clearBTN);
	wholepanel.add(JHelpPanel.margin(5));
	wholepanel.add(txtpanel);
	wholepanel.add(JHelpPanel.margin(3));
	wholepanel.add(backBTN);
	this.add(JHelpPanel.vmargin(3));
	this.add(wholepanel);
    }

    void showpopup() {
	if (L.Count > 1) {
	    L.show(popBTN, 10, 20);
	}
    }

    void hidepopup() {
	L.setVisible(false);
    }

    boolean ispopupvisible() {
	return L.isVisible();
    }

    void doSearch(final String s) {
	if (s.equals("")) {
	    JP.Search = null;
	} else {
	    JP.Search = s;
	}
	JHelpPanel.Subject = "start";
	JP.fill(true);
	JP.Search = null;
    }

    void setCount() {
	if (L.Count > 1) {
	    cntLBL.setText("(" + (L.Count - 1) + ")");
	} else {
	    cntLBL.setText("");
	}
    }
}

class MyJPopupMenuItem extends JMenuItem {

    private static final long serialVersionUID = 1L;
    String STR;
    JHelpPanel JP;

    public MyJPopupMenuItem(final JHelpPanel jp, final String s) {
	super(s.replaceAll("<p[^/]*/p>", "").replace("<br >", ", "));
	STR = s;
	JP = jp;

	setFont(new Font("System", 0, 11));
	addMouseListener(new MouseAdapter() {

	    @Override
	    public void mousePressed(final MouseEvent arg0) {
		JP.TSearch.L.setVisible(false);
		JP.TSearch.JTF.setText("");
		JP.TSearch.clearBTN.setVisible(false);
		final String s = STR;
		final Enumeration ev = JP.Other.elements();
		while (ev.hasMoreElements()) {
		    final String p[] = (String[]) ev.nextElement();
		    if (p[1].equals(s)) {
			JHelpPanel.Subject = p[0];
			JP.fill(true);
			break;
		    }
		}
	    }
	});
    }
}

class MyJPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = 1L;
    int Count = 0;
    JHelpPanel JP;

    public MyJPopupMenu(final JHelpPanel jp) {
	super();
	JP = jp;
	setFont(new Font("System", 0, 11));
	setFocusable(false);
    }

    public void addI(final String s) {
	Count++;
	add(new MyJPopupMenuItem(JP, s));
    }
}

class MyJTextPane extends JEditorPane implements HyperlinkListener {

    private static final long serialVersionUID = 1L;
    String content;
    HTMLEditorKit editorkit;
    HTMLDocument doc;
    JHelpPanel JP;

    public MyJTextPane(final JHelpPanel jp) {
	super();
	JP = jp;
	this.setContentType("text/html;");
	editorkit = (HTMLEditorKit) getEditorKit();
	doc = (HTMLDocument) getDocument();
	final StyleSheet myStyleSheet = new StyleSheet();
	myStyleSheet.addRule("body {color: #333333;font-family: Verdana, Arial, Helvetica, sans-serif;"
				+ "text-align: justify;font-size: 10px;background-color: #F1F3F9;}");
	myStyleSheet.addRule("ul {text-align: left;list-style-type: disc;padding-left: 15px;list-style-position: outside;}");
	myStyleSheet.addRule(".pfooter {text-align: center;font-size: 10px;padding-bottom: 5px;"
				+ "padding-top: 5px;background-color: #eaedf3;}");
	myStyleSheet.addRule(".pfootertitle {font-weight: bold;text-align: center;font-size: 10px;padding-bottom: 5px;"
				+ "padding-top: 5px;background-color: #DBDDE3;}");
	myStyleSheet.addRule("a {color: #0a9100;text-decoration: none;}");
	myStyleSheet.addRule(".aanchor {color: #CC6633;}");
	myStyleSheet.addRule(".centered {text-align: center;}");
	myStyleSheet.addRule(".comment {text-align: center;font-size: 9px;font-style: oblique;font-weight: normal;}");
	myStyleSheet.addRule(".comment2 {text-align: justify;font-size: 9px;font-style: oblique;font-weight: normal;}");
	myStyleSheet.addRule(".ttle {color: #000000;font-family: Verdana, Arial, Helvetica, sans-serif;"
				+ "text-align: center;font-weight: bold;font-size: 11px;background-color: #DBDDE3;}");
	myStyleSheet.addRule(".tab {border-width: 1px;border-color: #909090;border-style:solid;}");
	myStyleSheet.addRule(".tds {border-width: 1px;border-color: #909090;border-style:solid;background-color: #DBDDE3;}");
	myStyleSheet.addRule(".image {border-width: 1px;border-color: #909090;border-style:solid;background-color: #F1F3F9;}");
	editorkit.setStyleSheet(myStyleSheet);
	this.setEditable(false);
	addHyperlinkListener(this);
    }

    void fixImagePath() {
	final String SP = System.getProperty("file.separator");

	// Check if there are external images (inside the docs folder of
	// carmetal_config directory
	Pattern p = Pattern.compile("(<img [^>]*src=\")(images/)([^\"]+)(\"[^>]*>)", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(content);
	StringBuffer sb = new StringBuffer();

	while (m.find()) {
	    String myst = "<p class=\"centered\">" + m.group(1) + "file:///";
	    myst += Global.getHomeDirectory().replace("\\", "\\\\");
	    myst += "docs/images/".replace("/", SP).replace("\\", "\\\\");
	    myst += m.group(3).replace("/", SP).replace("\\", "\\\\");

	    // on windows, the img tag seems to need the width and height
	    // attribute :
	    final ImageIcon myimg = new ImageIcon(Global.getHomeDirectory() + "docs/images/" + m.group(3));
		if (myimg != null) {
		    myst += "\" width=\"" + myimg.getIconWidth() + "\" height=\"" + myimg.getIconHeight() + "\"";
		}

	    myst += m.group(4);
	    myst += "</p>";
	    m.appendReplacement(sb, myst);
	}
	m.appendTail(sb);
	m.reset();
	content = sb.toString();

	// Check if there are internal images (inside the jar archive with root
	// eric/GUI/icons/ :
	p = Pattern.compile("(<img [^>]*src=\")(#)([^\"]+)(\"[^>]*>)", Pattern.CASE_INSENSITIVE);
	m = p.matcher(content);
	sb = new StringBuffer();
	while (m.find()) {
	    final URL myIMG = Global.getPath("eric/GUI/icons/" + m.group(3));
	    if (myIMG == null)
		continue;
	    String myst = m.group(1);
	    myst += myIMG.toString();
	    myst += m.group(4);
	    m.appendReplacement(sb, myst);
    	}
	m.appendTail(sb);
	m.reset();
	content = sb.toString();
    }

    void setTitle() {
	if (content.equals("")) {
	    return;
	}
	content = content.replace("href=\"#", "class=\"aanchor\" href=\"#");
	final URL myIMG = Global.getPath("eric/GUI/icons/palette/" + JHelpPanel.Subject + ".png");
	final String mytitle = content.substring(0, content.indexOf("<br>"));
	String tag = "";
	if (myIMG == null) {
	    tag = "<table class=\"tab\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\"><tr border=\"1\">";
	    tag += "<td class=\"ttle\">" + mytitle + "</td>";
	    tag += "</tr></table>";
	} else {
	    tag = "<table class=\"tab\" width=\"100%\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\"><tr>";
	    tag += "<td class=\"ttle\">" + mytitle + "</td>";
	    tag += "<td class=\"ttle\" width=\"40\"><p class=\"image\"><img src=\""+ myIMG.toString() + "\"></p></td>";
	    tag += "</tr></table>";
	}
	content = content.replaceFirst(mytitle, tag);
    }

    void setFooter() {
	final Enumeration e = JP.Related.elements();
	String tag = "";
	while (e.hasMoreElements()) {
	    final String topic = (String) e.nextElement();
	    String topicTitle = "";
	    final Enumeration ev = JP.Other.elements();

	    while (ev.hasMoreElements()) {
		final String s[] = (String[]) ev.nextElement();
		if (s[0].equals(topic)) {
		    topicTitle = s[1];
		    break;
		}
	    }

	    tag += "<tr><td class=\"pfooter\"><a class=\"aanchor\" href=\"#" + topic + "\">" + topicTitle + "</a></td></tr>";
	}

	if (!tag.equals("")) {
	    content += "<table  class=\"tab\" width=\"100%\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\">";
	    content += "<tr><td class=\"pfootertitle\">" + Global.Loc("contexthelp.seealso") + "</tr></td>";
	    content += tag;
	    content += "</table>";
	}
    }

    void setContent(final String s) {
	try {
	    content = s;
	    fixImagePath();
	    setTitle();
	    setFooter();
	    editorkit.insertHTML(doc, doc.getLength(), content, 0, 0, null);
	} catch (final Exception ex) {
	}
	// JP.focusTxt();
    }

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent r) {
	if (r.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
	    String lnk = "";
	    if (r.getDescription().startsWith("#")) {
		lnk = r.getDescription().substring(1);
		JHelpPanel.Subject = lnk;
		JP.clearSearchTxtField();
		JP.fill(true);
		return;
	    } else {
		lnk = r.getDescription();
		if (!lnk.startsWith("http://")) {
		    lnk = "http://" + lnk;
		}
		JBrowserLauncher.openURL(lnk);
		return;
	    }
	}
    }
}