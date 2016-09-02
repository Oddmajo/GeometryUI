/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Client;

import eric.FileTools;
import eric.JZirkelCanvas;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author PM
 */
public class Reception implements Runnable {

    private BufferedReader in = null;
    private String message = null;
    private ClientNetworkTools cnt = null;
    private Communication Com = null;
    private ZirkelCanvas ZC;

    public Reception(BufferedReader in, ClientNetworkTools cnt, Communication Com){
	this.in = in;
        this.cnt = cnt;
	this.Com = Com;
	ZC = JZirkelCanvas.getCurrentZC();
    }

    @Override
    public void run() {
	try {
	    while(true){
		String src = "";
		/*
                while((message = in.readLine())!=null){
		    src += message+"\n";
                }
		 *
		 */
		message = in.readLine();
		while(message!=null && !message.equals("END_MESSAGE")) {
		    src += message+"\n";
		    message = in.readLine();
		}
		//System.out.println("src =\n"+src);
		if(src.startsWith("<Accepted objects>")) {
		    String objs[] = src.split("\n");
		    String obj[];
		    for(int i = 1; i<objs.length-1; i++) {
			obj = objs[i].split("=");
			cnt.set_accepted_object(obj[0], Boolean.parseBoolean(obj[1]));
		    }
		} else if(src.startsWith("<Real time>")) {
		    if(src.endsWith("true\n"+"")) {
			cnt.set_real_time(true);
			try {
			    Com.send(FileTools.getCurrentFileSource());
			} catch(Exception e){
			    System.err.println("Erreur d'envoi (Client/Reception)");
			}
		    } else {
			cnt.set_real_time(false);
		    }
		} else if(src.startsWith("<End>")) {
                    cnt.doClose();
                } else if(src.contains("<CaR>")) { //receiving the whole figure
		    try {
			//tab_main_panel.getActivePanel().getZC().setFileSource(src);
			ZC.setFileSource(src);
		    } catch (Exception ex){}
		} else if(src.startsWith("<To")) { //add, update, delete, change name
		    ZC.update_local(src);
		} else if(src.startsWith("<Collaboration>")) {
		    cnt.set_real_time(src.endsWith("true\n"+""));
		} else {
		    System.err.println("Not implemented yet");
		    System.out.println(src);
		    System.out.println("-------------------");
		}
	    }
	} catch (IOException e) {
            cnt.doClose();
	    System.err.println("Serveur OffLine");
	}
    }
}