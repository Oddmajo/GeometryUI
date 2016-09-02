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
package eric.GUI;

import eric.GUI.window.*;
import eric.OS;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import rene.gui.Global;

/**
 * 
 * @author erichake
 */
public class themes {

    public static String GRAY="gray";
    public static String BRUSHED="brushed";
    public static String CURRENT=BRUSHED;
    public static Color TabChangedColor=new Color(28,106,161);
    public static Font TabFont=new Font("Dialog", Font.PLAIN, 11);
    public static Font TabMenusFont=new Font("Dialog", Font.PLAIN, 12);
    public static Font TabSelectedMenusFont=new Font("Dialog", Font.BOLD+Font.ITALIC, 12);
    private static String PalettePath="/eric/GUI/icons/palette/";
    private static String BarPath="/eric/GUI/icons/bar/";
    private static String ThemesPath="/eric/GUI/icons/themes/";
    private static String CurrentTheme="/eric/GUI/icons/themes/gray/";
    private static String CommonTheme="/eric/GUI/icons/themes/common/";
    private static boolean MAClook=true;
    private static int palette_iconwidth=28;
    private static int palette_icon_per_row=6;
    private static int TitleBarHeight=35;
    private static int TitleBarTextHeight=25;
    private static int BoxesMarginW=5;
    private static int BoxesMarginH=4;
    private static int MenuBarHeight=20;
    private static int VertBorderWidth=7;
    private static int VertPanelBorderWidth=5;
    private static int VertSeparatorWidth=5;
    private static int ResizeBoxWidth=19;
    private static int ResizeBoxHeight=22;
    private static int MainTabPanelHeight=32;
    private static int TabBtnHeight=22;
    private static int ControlTabPanelWidth=150;
    private static Dimension CloseBoxDim, GrowBoxDim, ReduceBoxDim,OpenLeftPanelBtnDim,OpenMiddlePanelBtnDim;
    private static int OpenPanelsBtnsMarginW=5;
    private static int OpenPanelsBtnsMarginH=29;
    private static int comments_height, tab_leftborder, tab_rightborder, StatusBarHeight;
    private static int tab_corner_width=8;
    private static boolean showtabs=true;
    private static boolean showstatus=true;
    private static float opacity=0.5f;
    private static int tooliconsize=32;

    private static int palette_ZoneTitleHeight=17;



    public themes() {
    }

    public static void init() {
        ReduceBoxDim=new Dimension(getIcon("zreducebutton.png").getIconWidth(), getIcon("zreducebutton.png").getIconHeight());
        GrowBoxDim=new Dimension(getIcon("zgrowbutton.png").getIconWidth(), getIcon("zgrowbutton.png").getIconHeight());
        CloseBoxDim=new Dimension(getIcon("zclosebutton.png").getIconWidth(), getIcon("zclosebutton.png").getIconHeight());
        OpenLeftPanelBtnDim=new Dimension(getIcon("rightpanel_on.png").getIconWidth(), getIcon("rightpanel_on.png").getIconHeight());
        OpenMiddlePanelBtnDim=new Dimension(getIcon("middlepanel_on.png").getIconWidth(), getIcon("middlepanel_on.png").getIconHeight());

//        tab_topborder=getIcon("tab_top.gif").getIconHeight();
//        tab_leftborder=getIcon("tab_left.gif").getIconWidth();
//        tab_rightborder=getIcon("tab_right.gif").getIconWidth();
//        tab_bottomborder=getIcon("tab_bottom.gif").getIconHeight();
        comments_height=80;
        tab_leftborder=0;
        tab_rightborder=0;
        StatusBarHeight=25;
        setComponentsSize();
    }

    public static void setComponentsSize() {
        CurrentTheme=ThemesPath+CURRENT+"/";
        if (isApplet()) {
            VertBorderWidth=0;
            VertSeparatorWidth=0;
            TitleBarHeight=0;
            TitleBarTextHeight=0;
            MenuBarHeight=0;
            ResizeBoxWidth=0;
            ResizeBoxHeight=0;
            MAClook=true;
            return;
        }
        if (CURRENT.equals(GRAY)) {
            VertBorderWidth=5;
            VertPanelBorderWidth=5;
            VertSeparatorWidth=3;
            TitleBarHeight=35;
            TitleBarTextHeight=25;
            MenuBarHeight=20;
            ResizeBoxWidth=19;
            ResizeBoxHeight=22;
            MAClook=true;
        } else if (CURRENT.equals(BRUSHED)) {
            VertBorderWidth=7;
            VertSeparatorWidth=5;
            TitleBarHeight=25;
            TitleBarTextHeight=25;
            MenuBarHeight=20;
            ResizeBoxWidth=19;
            ResizeBoxHeight=22;
            MAClook=false;
        }
    }

    public static boolean isShowTabs(){
        return showtabs;
    }

    public static void setShowTabs(boolean b){
        showtabs=b;
    }

    public static boolean isShowStatus(){
        return showstatus;
    }

    public static void setShowStatus(boolean b){
        showstatus=b;
    }

    public static void setTheme(String theme) {
        CURRENT=theme;
    }

    public static String getTheme() {
        return CURRENT;
    }

    public static String getBarPath() {
        return BarPath;
    }

    public static String getPalettePath() {
        return PalettePath;
    }

    public static String getCurrentThemePath() {
        return CurrentTheme;
    }

    public static String getCommonThemePath() {
        return CommonTheme;
    }

    public static int getTabCornerWidth(){
        return tab_corner_width;
    }

    public static int getToolIconSize(){
        return tooliconsize;
    }

    public static int getTitleBarHeight() {
        return TitleBarHeight;
    }

    public static int getMenuBarHeight() {
        return MenuBarHeight;
    }



    public static int getResizeBoxHeight() {
        return ResizeBoxHeight;
    }

    public static int getResizeBoxWidth() {
        return ResizeBoxWidth;
    }

    public static int getVerticalBorderWidth() {
        return showtabs?VertBorderWidth:0;
    }

    public static int getVerticalPanelBorderWidth() {
        if (LeftPanel.isPanelVisible()){
            return VertPanelBorderWidth;
        };
        return 0;
    }

    public static int getBoxesMarginWidth() {
        return BoxesMarginW;
    }

    public static int getBoxesMarginHeight() {
        return BoxesMarginH;
    }

    public static Dimension getGrowBoxDim() {
        return GrowBoxDim;
    }

    public static Dimension getCloseBoxDim() {
        return CloseBoxDim;
    }

    public static Dimension getOpenLeftPanelBtnDim() {
        return OpenLeftPanelBtnDim ;
    }
    public static Dimension getOpenMiddlePanelBtnDim() {
        return OpenMiddlePanelBtnDim ;
    }
    public static int getOpenPanelsBtnsMarginW(){
        return OpenPanelsBtnsMarginW;
    }
   public static int getOpenPanelsBtnsMarginH(){
        return OpenPanelsBtnsMarginH;
    }

    public static Dimension getReduceBoxDim() {
        return ReduceBoxDim;
    }

    public static int getCommentsHeight() {
        return Global.getParameter("comment", false)?comments_height:0;
    }

    public static int getTabLeftBorderWidth() {
        return tab_leftborder;
    }

    public static int getTabRightBorderWidth() {
        return tab_rightborder;
    }

    public static int getStatusBarHeight() {
        return showstatus?StatusBarHeight:0;
    }
    public static void setStatusBarHeight(int h) {
        StatusBarHeight=h;
    }
    public static int getTabBtnHeight(){
        return TabBtnHeight;
    }

    public static int getTabControlPanelWidth() {
        return ControlTabPanelWidth;
    }

    public static int getLeftPanelWidth() {
        return LeftPanel.getPanelWidth();
    }


   public static int getRightPanelWidth(){
       int w=(RightPanel.isPanelVisible())?(palette_icon_per_row*palette_iconwidth):0;
       return w;
   }

    public static int getTotalRightPanelWidth() {
        int w=(RightPanel.isPanelVisible())?(getRightPanelWidth()+RightPanel.getSeparatorWidth()):0;
        return w;
    }

    public static int getPaletteZoneTitleHeight(){
        return palette_ZoneTitleHeight;
    }

    public static int getPaletteIconPerRow(){
        return palette_icon_per_row;
    }

    public static void setPaletteIconWidth(int i){
        palette_iconwidth=i;
    }

    public static int getPaletteIconWidth(){
        return palette_iconwidth;
    }

    public static int getMainTabPanelHeight() {
        return showtabs?MainTabPanelHeight:0;
    }

    public static void setDisable(final Graphics g, final Dimension d) {
        final Graphics2D g2=(Graphics2D) g;
        final AlphaComposite ac=AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(ac);
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, d.width, d.height);
    }

    public static boolean AllowMacLook() {
        return MAClook;
    }

    public static boolean MacLF() {
        if (isApplet()) {
            return false;
        }
        return ((OS.isMac())&&themes.AllowMacLook());
    }

    public static boolean isApplet() {
        return (pipe_tools.isApplet());
    }

    public static ImageIcon getIcon(final String s) {
        ImageIcon myicon;
        try {
            myicon=new ImageIcon(themes.class.getResource(CurrentTheme+s));
        } catch (final Exception e) {
            try {
                myicon=new ImageIcon(themes.class.getResource(CommonTheme+s));
            } catch (final Exception ex) {
                myicon=new ImageIcon(themes.class.getResource(CommonTheme+"null.gif"));
            }
        }
        return myicon;
    }

    public static Image getImage(final String s) {
        return pipe_tools.getWindow().getImage(s);
    }
    public static Image getPaletteImage(final String s) {
        return pipe_tools.getWindow().getPaletteImage(s);
    }

    public static ImageIcon resizeExistingIcon(String path_name, int w, int h){
        ImageIcon iicon = new ImageIcon(themes.class.getResource(path_name));
        return new ImageIcon(iicon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
}
