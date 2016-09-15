/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.pm.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author PM
 */
public class Communication implements Runnable {

    private Socket socket;
    private String login, ip;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Reception R = null;
    private ServerControlPanel scp = null;

    public Communication(ServerControlPanel scp, Socket socket, String login, String ip){
	this.scp =  scp;
        this.login = login;
	this.socket = socket;
        this.ip = ip;
    }

    @Override
    public void run() {
	try {
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream());
            
            R = new Reception(scp, in, login, ip);
            Thread t = new Thread(R);
            t.start();
	} catch (IOException e) {
	    System.out.println("Le client "+login+" s'est déconnecté");
	}
    }

    public void send(String msg){
	out.println(msg+"END_MESSAGE");
	out.flush();
    }
    
    public void kill(){
        //send("<End>");
        R = null;
        try {
            socket.close();
        } catch (IOException ex) {}
        out.close();
    }
}