/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Client;

import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.GUI.ZDialog.ZTools;
import eric.GUI.palette.PaletteManager;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import rene.gui.Global;

/**
 *
 * @author PM
 */
public class ConnectionControlPanel extends ZDialog {

    private ZTextFieldAndLabel name, ip, port;
    private ZButton connect;
    private Thread t;
    private Color color = Color.RED;
    private int PORT = 2357;

    public ConnectionControlPanel() {
	super(Global.Loc("network.client.title"), 3, 90, 220, 135, true, true);
        BWIDTH = 100;
        LWIDTH = 70;
	addContent();
    }

    private void addContent() {
        name = new ZTextFieldAndLabel(Global.Loc("network.client.Name"), Global.getParameter("network.name", Global.Loc("network.client.name")), LWIDTH, CHEIGHT){
            @Override
            public void focusGained() {
                if(this.getText().equals(Global.Loc("network.client.name"))){
                    this.setText("");
                    this.setForeground(ZTools.C_TextField);
                }
            }

            @Override
            public void focusLost(){
                if(this.getText().equals("")){
                    this.setText(Global.Loc("network.client.name"));
                    this.setForeground(ZTools.C_TextField_OFF);
                }
            }
            
            @Override
	    public void actionKey(KeyEvent k){
		if(k.getKeyCode()==KeyEvent.VK_ENTER){
		    connect.action();
		} else {
		    this.setForeground(ZTools.C_TextField);
		}
	    }
        };
        name.setForeground(name.getText().equals(Global.Loc("network.client.name"))?ZTools.C_TextField_OFF:ZTools.C_TextField);

        ip = new ZTextFieldAndLabel(Global.Loc("network.client.ip"), Global.getParameter("network.ip", "ip (xxx.xxx.xxx.xxx)"), LWIDTH, CHEIGHT){
            @Override
            public void focusGained(){
                if(this.getText().equals("ip (xxx.xxx.xxx.xxx)")){
                    this.setText("");
                }
                this.setForeground(ZTools.C_TextField);
            }

            @Override
            public void focusLost(){
                if(this.getText().equals("")){
                    this.setText("ip (xxx.xxx.xxx.xxx)");
                    this.setForeground(ZTools.C_TextField_OFF);
                }
            }

	    @Override
	    public void actionKey(KeyEvent k){
		if(k.getKeyCode()==KeyEvent.VK_ENTER){
		    connect.action();
		} else {
		    this.setForeground(ZTools.C_TextField);
		}
	    }
        };
        ip.setForeground(ip.getText().equals("ip (xxx.xxx.xxx.xxx)")?ZTools.C_TextField_OFF:ZTools.C_TextField);

	port = new ZTextFieldAndLabel(Global.Loc("network.client.port"), Integer.toString(PORT), LWIDTH, CHEIGHT){
            @Override
            public void focusGained(){
                //this.setForeground(ZTools.C_TextField);
            }

            @Override
            public void focusLost(){
            }
        };
        port.setEditable(false);
        port.setForeground(ZTools.C_TextField_OFF);

	connect = new ZButton(Global.Loc("network.client.connect")){

	    @Override
            public void action() {
                Global.setParameter("network.name", name.getText());
                if(can_connect(name, ip)){
                    Global.setParameter("network.ip", ip.getText());
                    t = new Thread(new Client(name.getText(), ip.getText(), PORT, (ConnectionControlPanel) this.getParent()));
                    t.start();
                }
            }
	};

	this.add(name);
	this.add(ip);
	this.add(port);
	this.add(connect);
    }

    private boolean can_connect(ZTextFieldAndLabel name, ZTextFieldAndLabel ip){
        boolean b = true;
        String NAME = name.getText(), IP = ip.getText();

        if(NAME.equals("") || NAME.equals(Global.Loc("network.client.name"))){
            name.setText(Global.Loc("network.client.name"));
            name.setForeground(color);
            b = false;
        }
        if(IP.equals("")){
            ip.setText("ip (xxx.xxx.xxx.xxx)");
            ip.setForeground(color);
            b = false;
        }
        if(!IP.matches("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$")){
            ip.setForeground(color);
            b = false;
        }

        return b;
    }

    @Override
    public void fixComponents(){
	name.setBounds(MARGINW, MARGINTOP1, D_WIDTH-2*MARGINW, CHEIGHT);
	ip.setBounds(MARGINW, MARGINTOP2, D_WIDTH-2*MARGINW, CHEIGHT);
	port.setBounds(MARGINW, MARGINTOP3, D_WIDTH-2*MARGINW, CHEIGHT);
	connect.setBounds(D_WIDTH/2-BWIDTH/2, MARGINTOP4, BWIDTH, CHEIGHT);
    }

    @Override
    public void doClose() {
	JZirkelCanvas.getCurrentZC().remove(this);
	JZirkelCanvas.getCurrentZC().repaint();
        PaletteManager.ClicOn("move");
    }

}
