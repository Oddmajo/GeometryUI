/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.pm.Server;

import eric.FileTools;
import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZCheckBox;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZSep;
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.GUI.ZDialog.ZTools;
import eric.GUI.window.tab_main_panel;
import eric.GUI.windowComponent;
import eric.JZirkelCanvas;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import rene.gui.Global;

/**
 *
 * @author PM
 */
public class ServerControlPanel extends ZDialog {

    private JLabel Jip, Jreceive, Jsend_to;
    private ZTextFieldAndLabel Zport;
    private ZButton Zconnect, Zsend, Zsend_all;
    private ZCheckBox Zpoint, Zline, Zcircle, Zfunction, Zreal_time_view, Zcollaborative;
    private boolean Point = true, Line = true, Circle = true, Function = true;
    private InetAddress LocaleAdresse ;
    private int PORT = 2357, NB_MAX = 20, H;
    private ZSep sep = new ZSep(75), sep2 = new ZSep(75), sep3 = new ZSep(75);
    private Server server = null;
    private boolean reduced = false, isServerRunning = false;
    private String clients[][] = null; //clients[i][0] = login, clients[i][1] = ip, clients[i][2] = real_time_view
    private boolean refresh = false;  //to avoid an infinite loop un paint
    private boolean collaboration = false;

    public ServerControlPanel(){
        super(Global.Loc("network.server.title"), 3, 90, 200, 300, true, true);
        LWIDTH = 40;
        BWIDTH = 80;
	H = D_HEIGHT;
	clients = new String[NB_MAX][3];
	for(int i=0; i<NB_MAX; i++){
	    clients[i][2] = "false";
	}

        addContent();
    }

    @Override
    public void paint(Graphics g) {
        if (JZirkelCanvas.isPaintCalled()) {

            Graphics2D g2d = windowComponent.getGraphics2D(g);

            if (isTitleVisible()) {
                // draw the title background :
                g2d.setColor(ZTools.backTitleColor);
                g2d.setClip(0, 0, D_WIDTH, THEIGHT);
                g2d.fill(roundRect);
            }

            if (isCloseBoxVisible()) {
                // draw the close box :
                g2d.setColor(ZTools.TitleTextColor);
                if (boxEnter) {
                    g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                } else {
                    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                }
                g2d.drawOval(cx, cy, cw, cw);
		int e = 3;
                g2d.drawLine(cx+e, cy+cw/2, cx+cw-e, cy+cw/2);
		if(reduced){
		    g2d.drawLine(cx+cw/2, cy+e, cx+cw/2, cy+cw-e);
		    D_HEIGHT = 23;
		    roundRect=new RoundRectangle2D.Double(2, 2, D_WIDTH-4, D_HEIGHT-4, ARCCORNER, ARCCORNER);
		    this.remove();
		} else {
		    D_HEIGHT = H;
		    roundRect=new RoundRectangle2D.Double(2, 2, D_WIDTH-4, D_HEIGHT-4, ARCCORNER, ARCCORNER);
		    this.add();
		}
                if(refresh){
                    JZirkelCanvas.getCurrentZC().repaint();
                    refresh = false;
                }
            }

            // draw the content background :
            g2d.setColor(ZTools.backMainColor);
            g2d.setClip(0, THEIGHT, D_WIDTH, D_HEIGHT);
            g2d.fill(roundRect);

            g2d.setClip(0, 0, D_WIDTH, D_HEIGHT);

            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g2d.draw(roundRect);
            g2d.setStroke(new BasicStroke(1f));

            paintChildren(g);
        }
    }

    private void addContent(){
        Jip = new JLabel();
        try {
	    LocaleAdresse = InetAddress.getLocalHost();
	    Jip.setText(Global.Loc("network.server.ip") + " : " + LocaleAdresse.toString().substring(LocaleAdresse.toString().lastIndexOf("/")+1));
	} catch(UnknownHostException e){
	    System.out.println("Err = "+e);
	}

        Zport = new ZTextFieldAndLabel("Port : ", Integer.toString(PORT), LWIDTH, CHEIGHT){
        };
        Zport.setEditable(false);
	Zport.setForeground(ZTools.C_TextField_OFF);

        Zconnect = new ZButton(Global.Loc("network.server.launch")){

            @Override
            public void action(){
		if(this.isEnabled()){
		    server = new Server((ServerControlPanel) this.getParent(), PORT, NB_MAX, clients);
		    new Thread(server).start();
		    this.setEnabled(false);
		    Zsend_all.setEnabled(true);
		    Zcollaborative.setEnabled(true);
                    isServerRunning = true;
		}
            }
        };

	Jreceive = new JLabel();
	Jreceive.setText(Global.Loc("network.server.receive"));

	Zpoint = new ZCheckBox(Global.Loc("palette.sizes.point"), true){

	    @Override
	    public void action(){
		set_accepted_object("point", this.isSelected());
	    }
	};
	Zline = new ZCheckBox(Global.Loc("palette.sizes.line"), true){

	    @Override
	    public void action(){
		set_accepted_object("line", this.isSelected());
	    }
	};
	Zcircle = new ZCheckBox(Global.Loc("network.server.circles"), true){

	    @Override
	    public void action(){
		set_accepted_object("circle", this.isSelected());
	    }
	};
	Zfunction = new ZCheckBox(Global.Loc("network.server.functions"), true) {

	    @Override
	    public void action() {
		set_accepted_object("function", this.isSelected());
	    }
	};

	Zreal_time_view = new ZCheckBox(Global.Loc("network.server.rtw"), false) {

	    @Override
	    public synchronized void action() {
		String tab_name = tab_main_panel.getActiveBtn().getTabName();
                if(!tab_name.equals("Global") && server!=null && !collaboration) {
                    int i = get_client_index(tab_name);
		    if(i!=NB_MAX) {
			clients[i][2] = String.valueOf(this.isSelected());
			server.send("<Real time> = "+clients[i][2]+"\n", i);
		    }
                } else {
                    this.setSelected(collaboration);
                }
                
	    }
	};
	Zreal_time_view.setEnabled(false);

	Jsend_to = new JLabel();
	Jsend_to.setText(Global.Loc("network.server.send"));

        Zsend = new ZButton(Global.Loc("network.client.name")) {

            @Override
            public void action() {
		if(server!=null) {
		    String tab_name = tab_main_panel.getActiveBtn().getTabName();
		    int i = get_client_index(tab_name);
		    try {
			server.send(FileTools.getCurrentFileSource(), i);
                        this.pressed(this);
		    } catch(Exception ex){}
		}
            }
        };
	Zsend.setEnabled(false);

	Zsend_all = new ZButton(Global.Loc("network.server.all")) {

	    @Override
	    public void action() {
		try {
		    server.send(FileTools.getCurrentFileSource());
                    this.pressed(this);
		} catch(Exception ex){}
	    }
	};
	Zsend_all.setEnabled(false);

	Zcollaborative = new ZCheckBox("Travail collaboratif (β)", false) {

	    @Override
	    public synchronized void action(){
		int i = 0;
		if(server==null){
		   this.setSelected(false);
		} else {
		    if(collaboration = this.isSelected()) { //it is an =
			try {
			    String src = FileTools.getCurrentFileSource();
			    server.send(src); //the construction is sent to everyone
			    //on se positionne sur l'onglet Global
			    int n = tab_main_panel.getBTNSsize();
			    while(i<n && !tab_main_panel.getBTN(i).getTabName().equals("Global")) {
				i++;
			    }
			    tab_main_panel.getPanel(i).getZC().setFileSource(src);
			    tab_main_panel.setActiveBtn(i);
			} catch(Exception e){}
		    }
		    server.send("<Collaboration> = "+collaboration+"\n");
		    Zreal_time_view.setEnabled(!collaboration || !tab_main_panel.getActiveBtn().getTabName().equals("Global"));
		    Zreal_time_view.setSelected(collaboration);
		    for(i=0; i<NB_MAX && clients[i][0]!=null; i++) {
			clients[i][2] = Boolean.toString(collaboration);
		    }
		}
	    }
	};
	Zcollaborative.setEnabled(false);

        this.add();
    }

    public void add() {
	this.add(Jip);
        this.add(Zport);
        this.add(Zconnect);

	this.add(sep);

	this.add(Jreceive);
	this.add(Zpoint);
	this.add(Zline);
	this.add(Zcircle);
	this.add(Zfunction);

	this.add(sep2);

	this.add(Zreal_time_view);

	this.add(sep3);

	this.add(Jsend_to);
        this.add(Zsend);
	this.add(Zsend_all);
	this.add(Zcollaborative);
        //this.fixComponents();
    }

    public void remove() {
	this.remove(Jip);
        this.remove(Zport);
        this.remove(Zconnect);

	this.remove(sep);

	this.remove(Jreceive);
	this.remove(Zpoint);
	this.remove(Zline);
	this.remove(Zcircle);
	this.remove(Zfunction);

	this.remove(sep2);

	this.remove(Zreal_time_view);

	this.remove(sep3);

	this.remove(Jsend_to);
	this.remove(Zsend);
	this.remove(Zsend_all);
	this.remove(Zcollaborative);
    }

    @Override
    public void fixComponents() {
        Jip.setBounds(MARGINW, MARGINTOP1, D_WIDTH-2*MARGINW, CHEIGHT);
        Zport.setBounds(MARGINW, MARGINTOP2, (D_WIDTH-2*MARGINW)/2, CHEIGHT);
	Zconnect.setBounds(2*MARGINW+(D_WIDTH-2*MARGINW)/2, MARGINTOP2, BWIDTH, CHEIGHT);

	sep.setBounds(0, MARGINTOP3, D_WIDTH, 1);

	Jreceive.setBounds(MARGINW, MARGINTOP3+10, D_WIDTH-2*MARGINW, CHEIGHT);
	Zpoint.setBounds(MARGINW, MARGINTOP4+10, (D_WIDTH-2*MARGINW)/2, CHEIGHT);
	Zline.setBounds(2*MARGINW+(D_WIDTH-2*MARGINW)/2, MARGINTOP4+10, (D_WIDTH-2*MARGINW)/2, CHEIGHT);
	Zcircle.setBounds(MARGINW, MARGINTOP5+10, (D_WIDTH-2*MARGINW)/2, CHEIGHT);
	Zfunction.setBounds(2*MARGINW+(D_WIDTH-2*MARGINW)/2, MARGINTOP5+10, (D_WIDTH-2*MARGINW)/2, CHEIGHT);

	int MARGINTOP6 = MARGINTOP5+26+10;
	sep2.setBounds(0, MARGINTOP6, D_WIDTH, 1);

	Zreal_time_view.setBounds(MARGINW, MARGINTOP6+10, D_WIDTH-2*MARGINW, CHEIGHT);
	int MARGINTOP7 = MARGINTOP6+26+10;

	sep3.setBounds(0, MARGINTOP7, D_WIDTH, 1);

	Jsend_to.setBounds(MARGINW, MARGINTOP7+10, D_WIDTH-2*MARGINW, CHEIGHT);
	Zsend.setBounds(MARGINW, MARGINTOP7+26+10, BWIDTH, CHEIGHT);
	Zsend_all.setBounds(2*MARGINW+(D_WIDTH-2*MARGINW)/2, MARGINTOP7+26+10, BWIDTH, CHEIGHT);
	Zcollaborative.setBounds(MARGINW, MARGINTOP7+2*26+10, D_WIDTH-2*MARGINW, CHEIGHT);
    }

    @Override
    public void doClose() {
	reduced = !reduced;
        refresh = true;
        JZirkelCanvas.getCurrentZC().repaint();
    }

    public void refresh() {
	String tab_name = tab_main_panel.getActiveBtn().getTabName();
	int i = get_client_index(tab_name);
	if(i!=NB_MAX) { //a client
	    Zreal_time_view.setSelected(Boolean.parseBoolean(clients[i][2]) || collaboration);
	    Zreal_time_view.setEnabled(!collaboration);
	    Zsend.setText(clients[i][0]);
	    Zsend.setEnabled(true);
	} else { //Global or other
	    Zreal_time_view.setSelected(collaboration);
	    Zreal_time_view.setEnabled(false);
	    Zsend.setText(Global.Loc("network.client.name"));
	    Zsend.setEnabled(false);
	}
    }

    private int get_client_index(final String tab_name) {
	if(!tab_name.contains("(") || !tab_name.contains(")")) {
	    return NB_MAX;
	}
	int i = 0;
	String IP = tab_name.substring(tab_name.indexOf("(")+1, tab_name.indexOf(")"));
	while(i<NB_MAX && !IP.equals(clients[i][1])) {
	    i++;
	}
	return i;
    }

    public String accepted_objects() {
	String s = "<Accepted objects>\n";
	s += "point="+Point+"\n";
	s += "line="+Line+"\n";
	s += "circle="+Circle+"\n";
	s += "function="+Function+"\n";
	s += "</Accepted objects>\n";
	//s += "";
	return s;
    }

    public void set_accepted_object(String name, boolean value) {
	if(name.equals("point")){
	    Point = value;
	} else if(name.equals("line")){
	    Line = value;
	} else if(name.equals("circle")){
	    Circle = value;
	} else if(name.equals("function")){
	    Function = value;
	}
	if(server!=null){
	    server.send(accepted_objects());
	}
    }

    public void delete_client(String login, String ip) {
        server.delete_client(login, ip);
    }

    public boolean isServerRunning() {
        return isServerRunning;
    }
    public void setServerRunning(boolean b) {
        isServerRunning = b;
    }

    public void close_and_kill_server() {
        if(server!=null){
            server.send("<End>");
            server.kill();
        }
        isServerRunning = false;
        //server = null;
    }

    public boolean get_collaboration() {
	return collaboration;
    }

    @Override
    public void send(String msg){
	server.send(msg);
    }

    public void send_minus(String msg, String ip) {
	for(int i=0; i<NB_MAX; i++){
	    if(clients[i][1]!=null && !clients[i][1].equals(ip)) {
		server.send(msg, i);
	    }
	}
    }
}