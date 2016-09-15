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

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.window.LeftPanel;
import eric.GUI.window.Open_left_panel_btn;
import eric.GUI.window.Open_middle_panel_btn;
import eric.GUI.window.Open_right_panel_btn;
import eric.GUI.window.RightPanel;
import eric.JSprogram.ScriptItem;
import eric.JSprogram.ScriptPanel;
import eric.bar.JPropertiesBar;
import eric.macros.CreateMacroDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import rene.dialogs.Question;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.tools.JSmacroTool;

public class JGeneralMenuBar extends JEricPanel {

    private static final int MenuTextSize = 12;
    private static JGeneralMenuBar me;
    myJMenuBar menubar = new myJMenuBar();
    private myJMenu MacrosMenu, ObjectsMenu, JSMenu;
    JButton historybutton;
    myJMenuItem hiddenitem, griditem, restrictpaletteitem, editpaletteitem,
            macrositem, historyitem, helpitem, propertiesitem, smallitem,
            mediumitem, largeitem, definejobitem, commentitem, leftpanelitem, paletteitem;
    private static ui.pm.Server.ServerControlPanel scp = null;
    private ui.pm.Client.ClientNetworkTools cnt = null;

    @Override
    public void paintComponent(final java.awt.Graphics g) {
        // super.paintComponent(g);
        final java.awt.Dimension d = this.getSize();
        g.drawImage(themes.getImage("menubar.gif"), 0, 0, d.width, d.height,
                this);
    }

    public JGeneralMenuBar() {
        me = this;
        this.setLayout(new javax.swing.BoxLayout(this,
                javax.swing.BoxLayout.X_AXIS));
        MacrosMenu = new myJMenu(Loc("macros"));
        ObjectsMenu = new myJMenu(Global.Loc("palette.construction"));
        JSMenu = new myJMenu(Loc("js"));
        JSMenu.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                InitJSMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        init();
        InitObjectsMenu();
    }

    public void paintImmediately() {
        paintImmediately(0, 0, getWidth(), getHeight());
    }

    class myJMenuBar extends JMenuBar {

        @Override
        public void paintComponents(final Graphics g) {
        }

        myJMenuBar() {
            super();
            setUI(null);
        }

        void addMenu(final JMenu mymen) {
            if (mymen.getItemCount() > 0) {
                this.add(mymen);
            }
        }
    }

    class myJMenu extends JMenu {

        @Override
        public void paintComponents(final Graphics g) {
        }

        myJMenu(final String menuname) {
            this.setText(menuname);
            this.setFont(new java.awt.Font(Global.GlobalFont, 0, 12));
            this.setForeground(new Color(40, 40, 40));
            this.setOpaque(false);
        }

        myJMenu(final String menuname, final boolean isSubmenu) {
            this(menuname);
            if (isSubmenu) {
                setOpaque(true);
                setIcon(new myImageIcon(getClass().getResource(
                        "/eric/GUI/icons/palette/null.png"), null));
            }
        }

        // Constructor for the Objects submenus :
        myJMenu(final String menuname, final int icnw) {
            this(menuname);
            setOpaque(true);
            final myImageIcon myicn = new myImageIcon(getClass().getResource(
                    "/eric/GUI/icons/palette/null.png"), null);
            myicn.setIcnMargin(0);
            myicn.setIconWidth(icnw);
            setIcon(myicn);
        }

        void addSep() {
            // if (this.getItemCount()>0) this.addSeparator();

            this.add(new mySeparator());
        }

        void addI(final String mnu, final String icn, final int a1,
                final int a2, final myJMenuItem item) {
            item.setNames(Loc(mnu), icn);
            if (a1 != 0) {
                item.setAccelerator(KeyStroke.getKeyStroke(a1, a2));
                pipe_tools.getWindow().getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(a1, a2), mnu);
                pipe_tools.getWindow().getRootPane().getActionMap().put(mnu, new AbstractAction() {

                    @Override
                    public void actionPerformed(final ActionEvent arg0) {
                        item.action();
                    }
                });
            }
            this.add(item);

        }

        void addI(final String mnu, final String icn, final int a1,
                final int a2, final boolean sel, final myJMenuItem item) {
            item.setNames(Loc(mnu), icn);
            item.setSelected(sel);
            if (a1 != 0) {
                item.setAccelerator(KeyStroke.getKeyStroke(a1, a2));
                pipe_tools.getWindow().getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(a1, a2), mnu);
                pipe_tools.getWindow().getRootPane().getActionMap().put(mnu, new AbstractAction() {

		    @Override
                    public void actionPerformed(final ActionEvent arg0) {
                        item.action();
                    }
                });
            }
            this.add(item);

        }

        // only for objects submenus :
        void addI(final String icn, final myJMenuItem item) {
            final String mnuName = PaletteManager.ToolTip(icn);
            item.setNames(mnuName, icn);
            item.setText("<html>" + item.getText().replaceAll("\\+", "<br>") + "</html>");
            if (!(item.myimage == null)) {
                item.myimage.setIconWidth(28);
                item.myimage.setIconHeight(28);
            }
            this.add(item);
        }

        // only for language submenu :
        void addI(final String lang, final String country,
                final myJMenuItem item) {
            final String suffix = (country.equals("")) ? lang : lang + "_" + country;
            final String icn = "lg_" + suffix;
            final boolean good = Global.isLanguage(lang, country);
            if ((good) && (!(icn.equals("")))) {
                this.setIcon(new ImageIcon(getClass().getResource(
                        "/eric/GUI/icons/palette/" + icn + ".png")));
            }
            addI("language." + suffix, icn, 0, 0, item);
            item.setEnabled(!good);
        }

        class mySeparator extends JEricPanel {

            @Override
            public void paintComponent(final java.awt.Graphics g) {
                super.paintComponent(g);

                final java.awt.Dimension d = this.getSize();
                g.drawImage(themes.getImage("sep.png"), 2, 0, d.width - 4, 12,
                        this);
            }

            mySeparator() {
                this.setOpaque(false);
            }
        }
    }

    class mySimpleJMenuItem extends JMenuItem {

        int ID = 0;

        mySimpleJMenuItem(final String name, final int i) {
            super(name);
            ID = i;
            this.setOpaque(true);
            this.setFont(new java.awt.Font(Global.GlobalFont, 0, MenuTextSize));
            this.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent event) {
                    action();
                }
            });
        }

        void action() {
        }
    }

    public class myJMenuItem extends JMenuItem {

        String ICname;
        boolean selected = false;
        myImageIcon myimage = null;

        myJMenuItem() {
            this.setOpaque(true);
            this.setFont(new java.awt.Font(Global.GlobalFont, 0, MenuTextSize));
            this.addActionListener(new ActionListener() {

		@Override
                public void actionPerformed(final ActionEvent event) {
                    ZirkelCanvas ZC = JZirkelCanvas.getCurrentZC();
                    if (ZC != null && ZC.getTool() instanceof JSmacroTool) {
                        ((JSmacroTool) ZC.getTool()).invalidate(ZC);
                    }
                    action();
                }
            });
            this.setIcon(themes.getIcon("null.png"));
            // this.setIconTextGap(0);
        }

        myJMenuItem(final String itemname, final String iconname) {
            this();
            setNames(itemname, iconname);
        }

        @Override
        public void setSelected(final boolean sel) {
            selected = sel;
            final int fontstyle = (selected) ? 1 : 0;
            this.setFont(new java.awt.Font(Global.GlobalFont, fontstyle, MenuTextSize));
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        void setNames(final String itemname, final String iconname) {
            ICname = iconname;
            setText(itemname);
            setIcn(iconname);
            // setPreferredSize(new Dimension(getPreferredSize().width+50,22));
        }

        void setIcn(String iconname) {
            if (iconname.equals("")) {
                iconname = "null";
            }
            URL myurl = getClass().getResource(
                    "/eric/GUI/icons/palette/" + iconname + ".png");
            if (myurl == null) {
                myurl = getClass().getResource(
                        "/eric/GUI/icons/palette/" + iconname + ".gif");
            }
            if (myurl == null) {
                myurl = getClass().getResource(
                        "/eric/GUI/icons/jswindow/" + iconname + ".png");
            }
            myimage = new myImageIcon(myurl, this);

            setIcon(myimage);
        }

        void action() {
            PaletteManager.ClicOn(ICname);
        }
    }

    class myImageIcon extends ImageIcon {

        int IcnHeight = 22;
        int IcnWidth = 24;
        int IcnMargin = 6;
        myJMenuItem JM;

        @Override
        public void paintIcon(final Component c, final Graphics g, final int x,
                final int y) {
            final Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);

            if ((!(JM == null)) && (JM.isSelected())) {
                final ImageIcon mysel = new ImageIcon(getClass().getResource(
                        "/eric/GUI/icons/palette/selmark.png"));
                g2.drawImage(mysel.getImage(), 3, 0, 8, IcnWidth, JM);
            }
            g2.drawImage(getImage(), IcnMargin, 0, IcnWidth, IcnWidth, JM);
        }

        public void setIcnMargin(final int i) {
            IcnMargin = i;
        }

        public void setIconHeight(final int i) {
            IcnHeight = i;
        }

        public void setIconWidth(final int i) {
            IcnWidth = i;
        }

        @Override
        public int getIconHeight() {
            return IcnHeight;
        }

        @Override
        public int getIconWidth() {
            return IcnWidth;
        }

        myImageIcon(final URL myurl, final myJMenuItem jm) {
            super(myurl);
            JM = jm;
        }
    }

    private String Loc(final String s) {
        String loc = Global.Loc("menu." + s);
        if (loc == null) {
            loc = Global.Loc(s);
        }
        if (loc == null) {
            loc = s;
        }
        return loc;
    }

    public void InitMacrosMenu() {
        if (MacrosMenu != null) {
            MacrosMenu.removeAll();
            MacrosMenu.addI("palette.info.newmacro", "newmacro", 0, 0, new myJMenuItem() {

		@Override
                public void action() {
                    new CreateMacroDialog(JZirkelCanvas.getNewMacroPanel());
                    JZirkelCanvas.ActualiseMacroPanel();
                }
            });
            MacrosMenu.addSep();
        }
    }

    public static void s_InitMacrosMenu() {
        if (me != null) {
            me.InitMacrosMenu();
        }
    }

    public static void addMacrosMenu(JMenuItem item) {
        if ((me != null) && (me.MacrosMenu != null)) {
            me.MacrosMenu.add(item);
        }
    }

    public static void s_InitJSMenu() {
        if (me != null) {
            me.InitJSMenu();
        }
    }

    public void InitJSMenu() {
        JSMenu.removeAll();
        final ZirkelCanvas ZC = JZirkelCanvas.getCurrentZC();
        if (ZC == null) {
            return;
        }
        final ScriptPanel panel = ZC.getScriptsPanel();
        if (panel == null) {
            return;
        }

        myJMenuItem item = new myJMenuItem() {

	    @Override
            void action() {
                panel.onlyRemoveScriptsManagerPanel();
                panel.Restore();
            }
        };
        JSMenu.addI("JSmenu.cancel", "", 0, 0, item);
        item.setEnabled(panel.isBackup());
        item.setIcon(themes.resizeExistingIcon("/eric/GUI/icons/jswindow/restore.png", 16, 16));

        JSMenu.addSep();

        JSMenu.addI("js.newscriptinconstruction", "", 0, 0, item = new myJMenuItem() {

	    @Override
            void action() {
                panel.newScript();
            }
        });


        JSMenu.addSep();

        for (final ScriptItem myscriptitem : panel.getScripts()) {
            item = new myJMenuItem() {

		@Override
                public void action() {
                    panel.onlyRemoveScriptsManagerPanel();
                    myscriptitem.runScript();
                }
            };
            item.setEnabled(!myscriptitem.isRunning());
            JSMenu.addI(myscriptitem.getScriptName(), "", 0, 0, item);
            //item.myimage.setIconWidth(20);
            //item.myimage.setIconHeight(20);
            item.setIcon(themes.resizeExistingIcon("/eric/GUI/icons/jswindow/run.png", 16, 21));
        }


        if (panel.getScripts().size() > 0) {
            JSMenu.addSep();
        }

        JSMenu.addI("JSmenu.killall", "", 0, 0, item = new myJMenuItem() {

	    @Override
            void action() {
                ZC.killAllScripts();
            }
        });
        item.setEnabled(ZC.isThereAnyScriptRunning());

        JSMenu.addI("JSmenu.stopall", "", 0, 0, item = new myJMenuItem() {

	    @Override
            void action() {
                //panel.stopAllScripts();
                ZC.stopAllScripts();
            }
        });
        item.setEnabled(ZC.isThereAnyScriptRunning() && !ZC.isThereAnyStoppedScripts());

        JSMenu.addI("JSmenu.restartall", "", 0, 0, item = new myJMenuItem() {

	    @Override
            void action() {
                //panel.restartAllScripts();
                ZC.restartAllScripts();
            }
        });
        item.setEnabled(ZC.isThereAnyStoppedScripts());
        JSMenu.addSep();

        // add Modify Menu :
        myJMenu modifypopup = new myJMenu(Global.Loc("JSmenu.modify"), true);
        for (final ScriptItem scpitem : panel.getScripts()) {
            modifypopup.addI(scpitem.getScriptName(), "", 0, 0, item = new myJMenuItem() {

		@Override
                void action() {
                    scpitem.openEmbeddedScript();
                }
            });
        }
        modifypopup.setEnabled(panel.getScripts().size() > 0);
        JSMenu.add(modifypopup);

        // add Delete Menu :
        myJMenu deletepopup = new myJMenu(Global.Loc("JSmenu.delete"), true);
        for (final ScriptItem scpitem : panel.getScripts()) {
            deletepopup.addI(scpitem.getScriptName(), "", 0, 0, item = new myJMenuItem() {

		@Override
                void action() {
                    panel.removeScript(scpitem);
                }
            });
        }
        deletepopup.setEnabled(panel.getScripts().size() > 0);
        JSMenu.add(deletepopup);


        JSMenu.addI("JSmenu.ScriptsManager", "", 0, 0, item = new myJMenuItem() {

	    @Override
            void action() {
                panel.addScriptsManagerPanel();
            }
        });
        item.setEnabled(panel.getScripts().size() > 0);
    }

    public static void initToggleItems() {
        if (me != null) {
            me.commentitem.setSelected(Global.getParameter("comment", false));
            me.leftpanelitem.setSelected(LeftPanel.isPanelVisible());
            me.paletteitem.setSelected(RightPanel.isPanelVisible());
        }
    }

    public void InitObjectsMenu() {
        ObjectsMenu.removeAll();
        final myJMenu m1 = new myJMenu(Loc("objects.points"), 0);
        m1.addI("point", new myJMenuItem());
        m1.addI("intersection", new myJMenuItem());
        m1.addI("midpoint", new myJMenuItem());
        m1.addI("bi_syma", new myJMenuItem());
        m1.addI("bi_symc", new myJMenuItem());
        m1.addI("bi_trans", new myJMenuItem());
        ObjectsMenu.add(m1);
        final myJMenu m2 = new myJMenu(Loc("objects.lines"), 0);
        m2.addI("line", new myJMenuItem());
        m2.addI("ray", new myJMenuItem());
        m2.addI("parallel", new myJMenuItem());
        m2.addI("plumb", new myJMenuItem());
        m2.addI("bi_med", new myJMenuItem());
        m2.addI("bi_biss", new myJMenuItem());
        ObjectsMenu.add(m2);
        final myJMenu m3 = new myJMenu(Loc("objects.segments"), 0);
        m3.addI("segment", new myJMenuItem());
        m3.addI("fixedsegment", new myJMenuItem());
        m3.addI("vector", new myJMenuItem());
        m3.addI("area", new myJMenuItem());
        ObjectsMenu.add(m3);
        final myJMenu m4 = new myJMenu(Loc("objects.angles"), 0);
        m4.addI("angle", new myJMenuItem());
        m4.addI("fixedangle", new myJMenuItem());
        ObjectsMenu.add(m4);
        final myJMenu m5 = new myJMenu(Loc("objects.circles"), 0);
        m5.addI("circle", new myJMenuItem());
        m5.addI("fixedcircle", new myJMenuItem());
        m5.addI("circle3", new myJMenuItem());
        m5.addI("bi_circ", new myJMenuItem());
        m5.addI("bi_arc", new myJMenuItem());
        m5.addI("quadric", new myJMenuItem());
        ObjectsMenu.add(m5);
        final myJMenu m7 = new myJMenu(Loc("objects.functions"), 0);
        m7.addI("text", new myJMenuItem());
        m7.addI("expression", new myJMenuItem());
        m7.addI("bi_function_u", new myJMenuItem());
        m7.addI("function", new myJMenuItem());
        m7.addI("equationxy", new myJMenuItem());
        ObjectsMenu.add(m7);
        final myJMenu m6 = new myJMenu(Loc("objects.tracks"), 0);
        m6.addI("objecttracker", new myJMenuItem());
        m6.addI("tracker", new myJMenuItem());
        m6.addI("locus", new myJMenuItem());
        ObjectsMenu.add(m6);

    }

    private void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d = new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    private JEricPanel margintop(final int h) {
        final JEricPanel mypan = new JEricPanel();
        fixsize(mypan, 1, h);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    private void showrestrictedmessage() {
        if (Global.getParameter("showrestrictmessage", true)) {
            final JEricPanel mypan = new JEricPanel();
            mypan.setLayout(new BoxLayout(mypan, BoxLayout.Y_AXIS));
            final JLabel mylabel = new JLabel(Global.Loc("menu.display.restrictmessage"));
            mylabel.setFont(new Font("System", 0, 12));
            final JCheckBox myjcb = new JCheckBox(Global.Loc("menu.display.restrictmessage.dontdisplay"));
            mypan.add(mylabel);
            mypan.add(margintop(10));
            mypan.add(myjcb);
            JOptionPane.showMessageDialog(null, mypan, "",
                    JOptionPane.PLAIN_MESSAGE, null);
            Global.setParameter("showrestrictmessage", !myjcb.isSelected());
        }
    }

    public void init() {
        myJMenu menu;
        menubar.removeAll();
        this.removeAll();
        final int ctrlkey = (OS.isMac()) ? InputEvent.META_DOWN_MASK
                : InputEvent.CTRL_DOWN_MASK;

        menubar.setOpaque(false);
        menubar.setBorder(BorderFactory.createEmptyBorder());
        menubar.setAlignmentY(0.5F);

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        /**
         * **************************
         * FILE MENU
         ***************************
         */
        menu = new myJMenu(Loc("file"));

        menu.addI("file.new", "new", KeyEvent.VK_N, ctrlkey, new myJMenuItem());
        menu.addI("file.new3D", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.New3DWindow();
            }
        });
        menu.addI("file.newDP", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.NewDPWindow();
            }
        });


        menu.addSep();
        menu.addI("file.load", "load", KeyEvent.VK_O, ctrlkey,
                new myJMenuItem());
        menu.addI("file.save", "save", KeyEvent.VK_S, ctrlkey,
                new myJMenuItem());
        menu.addI("tab.popup.savefileonly", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.saveFileAs();
            }
        });
        menu.addI("workbook.saveas", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.saveWorkBookAs();
            }
        });

        menu.addSep();


        menu.addI("export.workbook", "", KeyEvent.VK_E, ctrlkey, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.HTMLWorkBookExport();
                FileTools.SaveJarAndLaunchBrowser();
            }
        });

        menu.addI("export.embedworkbook", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.HTMLWorkBookExtExport();
            }
        });

        menu.addSep();

        menu.addI("file.exportpng", "exportpng", 0, 0, new myJMenuItem());
        menu.addI("file.exporteps", "exporteps", 0, 0, new myJMenuItem());
        menu.addI("file.exportsvg", "exportsvg", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.exportGraphicFile(FileTools.SVG);
            }
        });
        menu.addI("file.exportpdf", "exportpdf", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                FileTools.exportGraphicFile(FileTools.PDF);
            }
        });
        menu.addSep();
        menu.addI("file.close", "", KeyEvent.VK_W, ctrlkey, new myJMenuItem() {

	    @Override
            void action() {
                pipe_tools.closeCurrent();
            }
        });
        menu.addSep();
        menu.addI("file.quit", "", KeyEvent.VK_Q, ctrlkey, new myJMenuItem() {

            @Override
            void action() {
                pipe_tools.quitAll();
            }
        });

        menubar.add(menu);
        /**
         * **************************
         * EDIT MENU
         ***************************
         */
        menu = new myJMenu(Loc("edit"));
        menu.addI("edit.copy", "copy", KeyEvent.VK_C, ctrlkey,
                new myJMenuItem());

        myJMenu submenu = new myJMenu(Loc("edit.copyto"), true);


        submenu.addI("PDF", "", 0, 0, new myJMenuItem() {

	    @Override
            public void action() {
                FileTools.exportGraphicFile(FileTools.PDF, null);
            }
        });
        submenu.addI("SVG", "", 0, 0, new myJMenuItem() {

	    @Override
            public void action() {
                FileTools.exportGraphicFile(FileTools.SVG, null);
            }
        });
        submenu.addI("EPS", "", 0, 0, new myJMenuItem() {

	    @Override
            public void action() {
                FileTools.exportGraphicFile(FileTools.EPS, null);
            }
        });
        menu.add(submenu);

        menu.addI("edit.copyapplettag", "", 0, 0,
                new myJMenuItem(){
            public void action() {
                FileTools.copyAppletTag();
            }
        });

        menu.addSep();
        menu.addI("edit.move", "move", 0, 0, new myJMenuItem());
        menu.addI("edit.rename", "rename", 0, 0, new myJMenuItem() {

	    @Override
            public void action() {
                ZirkelFrame ZF = JZirkelCanvas.getCurrentZF();
                if (ZF != null) {
                    ZF.settool("rename");
                }
            }
        });
        menu.addI("edit.edit", "edit", 0, 0, new myJMenuItem());
        menu.addI("edit.zoom", "zoom", 0, 0, new myJMenuItem() {

	    @Override
            public void action() {
                ZirkelFrame ZF = JZirkelCanvas.getCurrentZF();
                if (ZF != null) {
                    ZF.settool("zoom");
                }
            }
        });
        menu.addI("edit.hide", "hide", 0, 0, new myJMenuItem());
        menu.addI("edit.delete", "delete", 0, 0, new myJMenuItem());
        menu.addSep();
        menu.addI("edit.deactivatealltracks", "", 0, 0, new myJMenuItem() {

	    @Override
            void action() {
                ZirkelCanvas ZC = JZirkelCanvas.getCurrentZC();
                ZC.UniversalTrack.clearTrackImage();
                ZC.UniversalTrack.clearTrackObjects();
                ZC.repaint();
            }
        });
        menu.addI("edit.deletealltracks", "", KeyEvent.VK_T, ctrlkey, new myJMenuItem() {

	    @Override
            void action() {
                ZirkelCanvas ZC = JZirkelCanvas.getCurrentZC();
                ZC.UniversalTrack.clearTrackImage();
                ZC.repaint();
            }
        });
        menu.addSep();
        menu.addI("edit.deleteall", "", KeyEvent.VK_DELETE, 0, new myJMenuItem() {

	    @Override
            void action() {
                ZirkelFrame ZF = JZirkelCanvas.getCurrentZF();
                if (ZF.ZC.changed()) {
                    final Question q = new Question(pipe_tools.getFrame(), Global.Loc("savequestion.qsave"), Global.Loc("savequestion.title"), true);
                    q.center(null);
                    q.setVisible(true);
                    if (q.isAborted()) {
                        return;
                    }
                    if (q.yes()) {
                        FileTools.saveFile();
                    }
                }
                ZF.clear(false);
                ZF.Filename = "";
            }
        });
        menubar.add(menu);

        /**
         * **************************
         * OBJECTS MENU
         ***************************
         */
        menubar.add(ObjectsMenu);

        /**
         * **************************
         * DISPLAY MENU
         ***************************
         */
        menu = new myJMenu(Loc("display"));

        menu.addI("display.restrictedenvironment", "", KeyEvent.VK_R, ctrlkey, new myJMenuItem() {

	    @Override
            void action() {
                pipe_tools.showRestrictedEnvironmentManager();
            }
        });
        menu.addSep();
        hiddenitem = new myJMenuItem();
        menu.addI("display.hidden", "hidden", 0, 0, false, hiddenitem);
        griditem = new myJMenuItem();
        menu.addI("display.grid", "grid", 0, 0, false, griditem);
        menu.addSep();
        menu.addI("display.smartboard", "", 0, 0, Global.getParameter(
                "smartboard", false), new myJMenuItem() {

	    @Override
            void action() {
                setSelected(!isSelected());
                Global.setParameter("smartboard", isSelected());
            }
        });
        menu.addSep();

        menu.addI("display.leftpanel", "", 0, 0, LeftPanel.isPanelVisible(), leftpanelitem = new myJMenuItem() {

	    @Override
            void action() {
                Open_left_panel_btn.toggle();
            }
        });
        menu.addI("display.comment", "", 0, 0, Global.getParameter(
                "comment", false), commentitem = new myJMenuItem() {

	    @Override
            void action() {
                Open_middle_panel_btn.toggle();
            }
        });
        menu.addI("display.palette", "", 0, 0, RightPanel.isPanelVisible(), paletteitem = new myJMenuItem() {

	    @Override
            void action() {
                Open_right_panel_btn.toggle();
            }
        });
        menu.addSep();
        menu.addI("display.properties_panel", "properties_panel",
                KeyEvent.VK_P, ctrlkey, false, new myJMenuItem() {

	    @Override
            void action() {
                JPropertiesBar.ShowHideBar();
            }
        });
        menu.addSep();
        largeitem = new myJMenuItem() {

	    @Override
            void action() {
                if (!isSelected()) {
                    themes.setPaletteIconWidth(32);
                    setSelected(true);
                    mediumitem.setSelected(false);
                    smallitem.setSelected(false);
                    pipe_tools.getContent().rebuiltRightPanel();
                }
            }
        };
        menu.addI("display.large", "", 0, 0, false, largeitem);

        mediumitem = new myJMenuItem() {

	    @Override
            void action() {
                if (!isSelected()) {
                    themes.setPaletteIconWidth(28);
                    setSelected(true);
                    largeitem.setSelected(false);
                    smallitem.setSelected(false);
                    pipe_tools.getContent().rebuiltRightPanel();
                }
            }
        };
        menu.addI("display.medium", "", 0, 0, false, mediumitem);

        smallitem = new myJMenuItem() {

	    @Override
            void action() {
                if (!isSelected()) {
                    themes.setPaletteIconWidth(24);
                    setSelected(true);
                    mediumitem.setSelected(false);
                    largeitem.setSelected(false);
                    pipe_tools.getContent().rebuiltRightPanel();
                }
            }
        };
        menu.addI("display.small", "", 0, 0, false, smallitem);

        switch (Global.getParameter("options.iconsize", 1)) {
            case 0:
                largeitem.setSelected(true);
                break;
            case 1:
                mediumitem.setSelected(true);
                break;
            case 2:
                smallitem.setSelected(true);
                break;
        }

        menu.addSep();

        submenu = new myJMenu(Loc("language"), true);

        submenu.addI("zh", "TW", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("zh", "TW");
            }
        });
        submenu.addI("ar", "TN", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("ar", "TN");
            }
        });
        submenu.addI("pt", "BR", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("pt", "BR");
            }
        });
        submenu.addI("de", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("de", "");
            }
        });
        submenu.addI("en", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("en", "");
            }
        });
        submenu.addI("es", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("es", "");
            }
        });
        submenu.addI("fr", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("fr", "");
            }
        });
        submenu.addI("gl", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("gl", "");
            }
        });
        submenu.addI("it", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("it", "");
            }
        });
        submenu.addI("nl", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("nl", "");
            }
        });
        submenu.addI("no", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("no", "");
            }
        });
        submenu.addI("pl", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("pl", "");
            }
        });
        submenu.addI("pt", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("pt", "");
            }
        });
        submenu.addI("ru", "RU", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("ru", "RU");
            }
        });
        submenu.addI("sl", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("sl", "");
            }
        });
        submenu.addI("sv", "", new myJMenuItem() {

            @Override
            void action() {
                Global.setLanguage("sv", "");
            }
        });

        menu.add(submenu);

        menubar.add(menu);

        /**
         * **************************
         * MACROS MENU : Initialised by
         ***************************
         */
        menubar.add(MacrosMenu);

        /**
         * **************************
         * JAVASCRIPT MENU
         ***************************
         */
        InitJSMenu();
        menubar.add(JSMenu);

        /**
         * **************************
         * SPECIAL MENU
         ***************************
         */
        menu = new myJMenu(Loc("assigment"));


        definejobitem = new myJMenuItem() {

            @Override
            void action() {
                pipe_tools.showExerciseManager();
            }
        };
        menu.addI("special.definejob", "", KeyEvent.VK_J, ctrlkey, false, definejobitem);

        menubar.add(menu);

	/**
         * **************************
         * NETWORK MENU
         ***************************
         */
	menu = new myJMenu(Loc("network"));
        menu.addI(Global.Loc("network.menu.launch"), "", 0, 0, new myJMenuItem(){

            @Override
            void action(){
                if(scp==null){
                    scp = new ui.pm.Server.ServerControlPanel();
                    JZirkelCanvas.getCurrentZC().add(scp);
                    JZirkelCanvas.getCurrentZC().repaint();
                    scp.init();
                    PaletteManager.deselectgeomgroup();
                    JZirkelCanvas.getCurrentZC().showStatus("");
                } else {
                    scp.close_and_kill_server();
                    JZirkelCanvas.getCurrentZC().remove(scp);
                    JZirkelCanvas.getCurrentZC().repaint();
                    PaletteManager.setSelected_with_clic("move", true);
                    scp = null;
                }
            }
        });
	menu.addI(Global.Loc("network.menu.connect"), "", 0, 0, new myJMenuItem(){

	    @Override
	    void action(){
                cnt = JZirkelCanvas.getCurrentZC().get_cnt();
                if(cnt==null) { //then display the ConnectionControlPanel
                    ui.pm.Client.ConnectionControlPanel client = new ui.pm.Client.ConnectionControlPanel();
                    JZirkelCanvas.getCurrentZC().add(client);
                    JZirkelCanvas.getCurrentZC().repaint();
                    client.init();
                    PaletteManager.deselectgeomgroup();
                    JZirkelCanvas.getCurrentZC().showStatus("");
                } else { //cut the connection
                    cnt.doClose();
                }
	    }
	});

	menubar.add(menu);

	/**
         * **************************
         * HELP MENU
         ***************************
         */
        menu = new myJMenu(Loc("help"));
        menu.addI("help.about", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                JLogoWindow.ShowLogoWindow(true);
//                new JAboutDialog(JZirkelCanvas.getCurrentJZF());
            }
        });
        menu.addI("help.licence", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                new JLicence(JZirkelCanvas.getCurrentJZF());
            }
        });
        menu.addI("help.info", "help_panel", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                Open_left_panel_btn.open();
                LeftPanel.selectHelp();
            }
        });
        menu.addSep();
        menu.addI("help.url0", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CaRMetal/");
            }
        });
        menu.addI("help.url1", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CARzine/");
            }
        });
        menu.addI("help.url2", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                JBrowserLauncher.openURL("http://zirkel.sourceforge.net/doc_en/index.html");
            }
        });
        menu.addSep();
        menu.addI("help.url3", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CaRMetal/index_translate.html");
            }
        });

        menu.addSep();

        menu.addI("help.opendefaultfolder", "", 0, 0, new myJMenuItem() {

            @Override
            void action() {
                Global.openHomeDirectoryInDesktop();
            }
        });
        menubar.add(menu);
        this.add(menubar);
        this.validate();
        this.repaint();
    }

    public static ui.pm.Server.ServerControlPanel get_scp(){
	return scp;
    }
}
