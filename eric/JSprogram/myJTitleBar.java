/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;


import eric.GUI.themes;
import eric.OS;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class myJTitleBar extends JEricPanel {

    private ImageIcon myimage1,  myimage2;
    private JEricPanel titlespacer;
    private JEricPanel macosspacer;
    private JEricPanel buttons;
    private JLabel windowtitle;
    private JButton closebtn;
    private ImageIcon myclosebtn;
    private ImageIcon myclosebtnover;

    @Override
    public void paintComponent(Graphics g) {
        final java.awt.Dimension d=this.getSize();
        final int w=d.width;
        final int h=d.height;
        final int dh=h/2+1;

        final Graphics2D g2=(Graphics2D) g;
        super.paintComponent(g);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
//                RenderingHints.VALUE_RENDER_QUALITY);
//        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
//                RenderingHints.VALUE_STROKE_PURE);


        g2.drawImage(myimage1.getImage(), 0, 0, w, dh, this);
        g2.drawImage(myimage2.getImage(), 0, dh, w, h-dh, this);

    }

    public myJTitleBar() {
        super();
        myimage1=themes.getIcon("titlebar.gif");
        myimage2=themes.getIcon("menubar.gif");
        myclosebtn=themes.getIcon("zclosebutton.png");
        myclosebtnover=themes.getIcon("zclosebuttonover.png");
        
        this.setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
//        init();
    }

    public void init() {
        this.removeAll();
        titlespacer=new JEricPanel();
        titlespacer.setOpaque(false);

        macosspacer=new JEricPanel();
        macosspacer.setOpaque(false);

        buttons=new JEricPanel();
        buttons.setLayout(new javax.swing.BoxLayout(buttons,
                javax.swing.BoxLayout.X_AXIS));
        buttons.setOpaque(false);

        windowtitle=new JLabel("");
        windowtitle.setFont(new Font(Global.GlobalFont, 0, 12));
        windowtitle.setForeground(new Color(80, 80, 80));
        windowtitle.setHorizontalAlignment(SwingConstants.LEFT);



        closebtn=new JButton();
        closebtn.setBorder(BorderFactory.createEmptyBorder());
        closebtn.setOpaque(false);
        closebtn.setContentAreaFilled(false);
        closebtn.setFocusable(false);
        closebtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(final MouseEvent e) {
//                    JMacrosTools.activate(MW);
//                    JMacrosTools.disposeCurrentJZF();
                }
        });

        closebtn.setIcon(myclosebtn);
        closebtn.setRolloverIcon(myclosebtnover);

        if ((OS.isMac())) {
            this.add(macosspacer);
            buttons.add(closebtn);
            this.add(buttons);
            this.add(titlespacer);
            this.add(windowtitle);
        } else {
            this.add(titlespacer);
            this.add(windowtitle);
            buttons.add(closebtn);
            this.add(buttons);

        }

        titlespacer.setAlignmentY(0.0f);
        macosspacer.setAlignmentY(0.0f);
        windowtitle.setAlignmentY(0.0f);
        buttons.setAlignmentY(0.0f);

        closebtn.setAlignmentY(0.5F);

        this.revalidate();
    }
}
