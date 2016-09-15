/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.pm.Client;

import eric.GUI.palette.PaletteManager;
import eric.JZirkelCanvas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author PM
 */
public class Communication implements Runnable {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private ClientNetworkTools cnt = null;

    public Communication (Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try {
            cnt = new ClientNetworkTools(this);
            ZirkelCanvas zc = JZirkelCanvas.getCurrentZC();
            zc.add(cnt);
            zc.set_cnt(cnt);
            zc.repaint();
            zc.init_cnt();
            PaletteManager.setSelected_with_clic("move", true);

	    out = new PrintWriter(socket.getOutputStream());
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new Reception(in, cnt, this)).start();
	} catch (IOException e) {
            cnt.doClose();
            close();
            System.out.println("Erreur de communication (com_client)");
	}
    }

    public void send(String msg){
        out.println(msg+"END_MESSAGE");
        out.flush();
    }
    
    public void close(){
        send("<End>\n");
        out.close();
        try {
            //socket.close();
            in.close();
        } catch (IOException ex) {}
    }
}