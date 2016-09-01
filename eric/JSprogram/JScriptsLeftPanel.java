/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.GUI.themes;
import eric.GUI.window.myJMenuItem;
import eric.JEricPanel;
import eric.JZirkelCanvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import rene.gui.Global;

/**
 *
 * @author PM
 */
public class JScriptsLeftPanel extends JEricPanel {
    private String path, SP = System.getProperty("file.separator");
    private File ScriptsDirectory = new File(Global.getHomeDirectory()+"scripts");
    private DefaultMutableTreeNode myRoot;
    private JTree ScriptsTree = null;
    private ArrayList<String> ToolTip = new ArrayList<String>(), FileName = new ArrayList<String>();
    private ScriptItemsArray items=new ScriptItemsArray();
    private JScrollPane jsp;

    public JScriptsLeftPanel(){
	this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

	myRoot = new DefaultMutableTreeNode("CaRScripts");
	explore(ScriptsDirectory, myRoot);

	ScriptsTree = new JTree(myRoot);
	ToolTipManager.sharedInstance().registerComponent(ScriptsTree);
	ScriptsTree.setCellRenderer(new MyRenderer());

	MouseListener ml = new MouseAdapter() {

	    @Override
	    public void mousePressed(MouseEvent e) {
		int row = ScriptsTree.getRowForLocation(e.getX(), e.getY());
		TreePath TPath = ScriptsTree.getPathForLocation(e.getX(), e.getY());
		ScriptsTree.setSelectionRow(row);
		if(row != -1 && e.isMetaDown()) {
		    path = Global.getHomeDirectory()+"scripts";
		    for(int i=1; i<TPath.getPathCount(); i++){
			path += SP;
			path += TPath.getPathComponent(i);
		    }
		    myPopUpMenu(path, e);
		}
	    }
	};

	ScriptsTree.addMouseListener(ml);

	jsp = new JScrollPane(ScriptsTree);
	jsp.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	this.add(jsp);
    }

    public void fixPanelSize(final int w, final int h) {
	Dimension d = new Dimension(w, h);

	this.setMaximumSize(d);
	this.setMinimumSize(d);
	this.setPreferredSize(d);
	this.setSize(d);

	jsp.setSize(d);
	jsp.revalidate();
    }

    class MyRenderer extends DefaultTreeCellRenderer {
	private int j;
	public MyRenderer() {
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
							boolean leaf, int row, boolean hasFocus) {

	    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	    if(row !=-1){
		if (leaf) {
		    for(j=0; j<FileName.size() && !FileName.get(j).equals(value.toString()); j++){}
		    if(j<ToolTip.size()){
			setToolTipText(ToolTip.get(j));
		    }
		} else {
		    setToolTipText(null);
		}
	    }

	    return this;
	}
    }

    final public void explore(File folder, DefaultMutableTreeNode myRoot){
	File[] f = folder.listFiles();
	ToolTip.add("");
	FileName.add("Folder");

	for(File file :f){
	    DefaultMutableTreeNode childNode;
	    if(file.isDirectory() && file.listFiles().length!=0){
		childNode = new DefaultMutableTreeNode(file.toString().substring(file.toString().lastIndexOf(SP)+1));
		myRoot.add(childNode);
		explore(file, childNode);
	    } else if(file.toString().endsWith(".js")) {
		childNode = new DefaultMutableTreeNode(file.toString().substring(file.toString().lastIndexOf(SP)+1));
		myRoot.add(childNode);
		ToolTip.add(getToolTip(file, childNode.toString()));
		FileName.add(childNode.toString());
	    }
	}
    }

    final String getToolTip(File file, String ScriptName){
	String str="";
	String mystr="<html><b>"+ScriptName+"</b><br/>";
        try {
            InputStream input = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while((str=in.readLine())!=null && !(str=str.trim()).endsWith("//end")) {
                //str=str.trim();
		mystr += str.replace("//", "");
		mystr += "<br/>";
            }
	} catch(Exception ex){}
	return mystr+"</html>";
    }

    /*
     * menu popup et les méthodes attachées
     * aux différents items
     */
    private void myPopUpMenu(final String path, final MouseEvent e){
	JMenuItem item, runitem, includeFileItem, includeFolderItem;
	JPopupMenu myPopUpMenu = new JPopupMenu();

	// add Cancel Item :
	item = new myJMenuItem(Global.Loc("JSmenu.cancel"), themes.resizeExistingIcon("/eric/GUI/icons/jswindow/restore.png", 16, 16));
	item.addActionListener(new ActionListener() {

	    @Override
            public void actionPerformed(final ActionEvent event) {
                JZirkelCanvas.getCurrentZC().getScriptsPanel().Restore();
            }
        });
	item.setEnabled(JZirkelCanvas.getCurrentZC().getScriptsPanel().isBackup());
	myPopUpMenu.add(item);

	// add Run Item
        runitem = new myJMenuItem("Exécuter", themes.resizeExistingIcon("/eric/GUI/icons/jswindow/run.png", 12, 16));
        runitem.addActionListener(new ActionListener() {

	    @Override
            public void actionPerformed(final ActionEvent event) {
                run(new File(path));
            }
        });

	// to include this file
	includeFileItem=new myJMenuItem("Inclure dans la figure");
	includeFileItem.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		include(path);
	    }
	});

	//to include all scripts in this folder
	includeFolderItem = new myJMenuItem("Inclure le dossier dans la figure");
	includeFolderItem.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		includeFolder(new File(path));
	    }
	});

	if((new File(path)).isDirectory()){
	    myPopUpMenu.add(includeFolderItem);
	} else {
	    myPopUpMenu.add(runitem);
	    myPopUpMenu.add(includeFileItem);
	}

	myPopUpMenu.show(e.getComponent(), e.getX()+15, e.getY());
    }

    public void run(File file){
	String str="";
        String mystr="";
        try {
            InputStream input=new FileInputStream(file);
            BufferedReader in=new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while ((str=in.readLine())!=null) {
                str=str.trim();
                mystr+=str+"\n";
            }
	} catch(Exception ex){}

	ScriptItem si = new ScriptItem(null, "anonymous", mystr);
	items.add(si);
	si.runScript();
    }

    public void include(String path){
	JZirkelCanvas.getCurrentZC().openScriptFile(path, false);
    }

    public void includeFolder(File folder){
	File[] f = folder.listFiles();
	for(File file : f){
	    if(file.toString().endsWith(".js")){
		include(file.toString());
	    } else if(file.isDirectory()){
		includeFolder(file);
	    }
	}
    }

    /*
     * Scripts lancés depuis le panneau de gauche
     */

    public ScriptItemsArray getScripts(){
	return items;
    }
}