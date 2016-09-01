/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Server;

import eric.GUI.window.tab_main_panel;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author PM
 */
public class Server implements Runnable {

    private ServerSocket ss = null;
    private Thread t = null;
    private int port, NB_MAX;
    private String clients[][] = null;
    private ServerControlPanel scp = null;
    private Accept_clients ac = null;

    public Server(ServerControlPanel scp, int port, int NB_MAX, String clients[][]) {
	this.port = port;
	this.NB_MAX = NB_MAX;
	this.clients = clients;
        this.scp = scp;
    }

    @Override
    public void run(){
	try {
	    ss = new ServerSocket(port);
	    //ac = new Accept_clients(scp, ss, NB_MAX, clients, Com);
	    ac = new Accept_clients(scp, ss, NB_MAX, clients);
            t = new Thread(ac);
	    tab_main_panel.getActiveBtn().setTabName("Global", "Global");
	    t.start();
	} catch (IOException e) {
            scp.setServerRunning(false);
	    System.out.println("Could not start server");
	}
    }

    public void send(String msg, int client_index){
	ac.send(msg, client_index);
    }

    public void send(String msg){
	send(msg, NB_MAX);
    }

    public void delete_client(String login, String ip){
        ac.delete_client(login, ip);
    }

    public void kill() {
        if(ac!=null){
            ac.kill();
        }
        //ac = null;
    }
}