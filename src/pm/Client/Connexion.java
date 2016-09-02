/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author PM
 */
public class Connexion implements Runnable {

    private Socket socket = null;
    private PrintWriter out = null;
    private String login = null;

    public Connexion(Socket socket, String login){
	this.socket = socket;
	this.login = login;
    }

    @Override
    public void run() {
	try {
	    out = new PrintWriter(socket.getOutputStream());

	    out.println(login);
	    out.flush();

            new Thread(new Communication(socket)).start();
	} catch (IOException e) {
            out.close();
            try {
                socket.close();
            } catch (Exception ee){}
            System.out.println("Erreur de connexion (Connexion)");
        }
    }
}