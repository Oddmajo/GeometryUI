/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.pm.Client;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author PM
 */
public class Client implements Runnable {

    private String IP = null, login = null;
    private int PORT;
    private Socket socket = null;
    private ConnectionControlPanel ccp = null;

    public Client(String name, String IP, int PORT, ConnectionControlPanel ccp) {
	this.login = name;
	this.IP = IP;
	this.PORT = PORT;
        this.ccp = ccp;
    }

    @Override
    public void run() {
	try {
	    socket = new Socket(IP, PORT);
            new Thread(new Connexion(socket, login)).start();
            ccp.doClose();
	} catch (IOException e){
            System.out.println("Erreur de connexion (Client)");
	}
    }
}