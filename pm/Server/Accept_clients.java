/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Server;

import eric.GUI.window.tab_main_panel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author PM
 */
public class Accept_clients implements Runnable {

    private ServerSocket ss = null;
    private Socket socket = null;
    private int client_nb = 0;
    private int NB_MAX;
    private BufferedReader in = null;
    private String login = null, ip = null;
    private String clients[][] = null;
    private ServerControlPanel scp = null;
    private Communication com = null;
    private Communication Com[] = null;
    private PrintWriter out = null;

    public Accept_clients(ServerControlPanel scp, ServerSocket ss, int NB_MAX, String clients[][]){
	this.ss = ss;
	this.NB_MAX = NB_MAX;
	this.clients = clients;
        this.scp = scp;
	this.Com = new Communication[NB_MAX];
    }

    @Override
    public void run() {
	while(client_nb<NB_MAX && scp.isServerRunning()){
	    try {
		socket = ss.accept();
		if(!already_logged(ip = socket.getInetAddress().toString().substring(1))){
		    try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if((login = in.readLine())!=null){

			    out = new PrintWriter(socket.getOutputStream());
			    out.println(scp.accepted_objects()+"END_MESSAGE");
			    out.flush();

			    com = new Communication(scp, socket, login, ip);
			    int i = insert_client(login, ip, com);
			    //System.out.println("# clients = "+client_nb);

			    new Thread(com).start();
                            
                            if(scp.get_collaboration()) {
                                try {
                                    out.println(eric.FileTools.getCurrentFileSource()+"END_MESSAGE");
                                    out.flush();
                                } catch (Exception e) { }
                                out.println("<Collaboration> = "+true+"\nEND_MESSAGE");
                                out.flush();
                                clients[i][2] = "true";
                            }
			}
		    } catch (IOException e) {}
		}
	    }
	    catch (IOException e) {
		System.out.println("Erreur d'acceptation Client");
	    }
	}
    }

    private boolean already_logged(String ip){

	for (int i = 0; i<NB_MAX; i++){
	    //if(clients[i][1]!=null && clients[i][1].equals(ip)){
	    if(ip.equals(clients[i][1])){
		return true;
	    }
	}
	return false;
    }

    private int insert_client(final String login, final String ip, Communication com){
        //search for an free storage index
	int i = 0;
	while(clients[i][0]!=null){
            i++;
        }

	clients[i][0] = login;
	clients[i][1] = ip;
	Com[i] = com;

	try {
	    SwingUtilities.invokeLater(new Runnable(){

		@Override
		public void run() {
		    //tab_main_panel.addBtnAndSelect(login + " ("+ip+")");
                    eric.GUI.window.tab_btn btn = tab_main_panel.getActiveBtn();
                    tab_main_panel.addBtn(login + " ("+ip+")");
                    tab_main_panel.initBTNS(null);
                    tab_main_panel.setActiveBtn(btn);
		    client_nb++;
		}
	    });
	} catch(Exception e){
	    System.err.println(e);
	}
        
        return i;
    }

    public void send(String msg, int client_index){
	if(client_index==NB_MAX){ //send to all clients
	    for(int i = 0; i<client_nb; i++){
		if(Com[i]!=null){
		    Com[i].send(msg);
		}
	    }
	} else {
	    Com[client_index].send(msg);
	}
    }

    public void delete_client(String login, String ip){
	//finding the number of current client
        int i = 0;
	while(i<clients.length && !login.equals(clients[i][0])){
	    i++;
	}

	if(i!=clients.length){
	    //finding the tab of that client an rename it
            int n = tab_main_panel.getBTNSsize();
	    int k = 0;
	    while(k<n && !tab_main_panel.getBTN(k).getTabName().equals(login + " ("+ip+")")){
		k++;
	    }
	    //tab_main_panel.removeBtnAndSelect(tab_main_panel.getBTN(k));
            tab_main_panel.getBTN(k).setTabName(login + " (log. off)", login + " (log. off)");
            
	    clients[i][0] = null;
	    clients[i][1] = null;
	    clients[i][2] = "false";
	    Com[i] = null;
	    client_nb--;
	}
    }

    public void kill(){
        for(int i = 0; i<clients.length; i++){
            if(Com[i]!=null){ // or clients[i][0], or client[i][1]
                Com[i].kill();
                delete_client((String)clients[i][0], (String)clients[i][1]);
            }
        }
        try {
            ss.close();
        } catch (IOException ex) {}
    }
}