/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pm.Server;

import eric.GUI.window.tab_main_panel;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.SwingUtilities;

/**
 *
 * @author PM
 */
public class Reception implements Runnable {

    private BufferedReader in = null;
    private String login = null, message = null, ip = null;
    private ServerControlPanel scp = null;

    public Reception(ServerControlPanel scp, BufferedReader in, String login, String ip){
	this.scp = scp;
        this.in = in;
	this.login = login;
        this.ip = ip;
    }
    
    private Runnable delete = new Runnable(){

        @Override
	public void run() {
            scp.delete_client(login, ip);
            try {
                in.close();
            } catch (Exception e) {}
        }
    };

    @Override
    public void run() {
	try {
            //while(true){
            while(scp.isServerRunning()){
                String src = "";
		/*
		while((message = in.readLine())!=null){
		    src += message+"\n";
		}
		*/
                message = in.readLine();
                while(scp.isServerRunning() && message!=null && !message.equals("END_MESSAGE")){
                    src += message+"\n";
                    message = in.readLine();
                }
		//System.out.println(src);
                try {
                    int n = tab_main_panel.getBTNSsize();
                    int i = 0;

		    if(src.startsWith("<End>")) {
                        SwingUtilities.invokeLater(new Thread(delete));
                    } else if(src.contains("<CaR>")){ //receiving the whole figure
			while(i<n && !tab_main_panel.getBTN(i).getTabName().equals(login + " ("+ip+")")){
			    i++;
			}
			tab_main_panel.getPanel(i).getZC().setFileSource(src);
		    } else {
			String tab_name = ((src.startsWith("<To add>")
				|| src.startsWith("<To delete>")
				|| src.startsWith("<To update>")
				|| src.startsWith("<To change name>")) && !scp.get_collaboration())
				? login + " ("+ip+")" : "Global";
			while(i<n && !tab_main_panel.getBTN(i).getTabName().equals(tab_name)) {
			    i++;
			}

			if(scp.get_collaboration()) {
			    scp.send_minus(src, ip); //transmettre aux autres clients
			}

			tab_main_panel.getPanel(i).getZC().update_local(src);
		    }
                } catch (Exception e) {
		    System.out.println("Read error");
                }
            }
	} catch (IOException e) {
	    System.err.println(login+" Offline");
            SwingUtilities.invokeLater(new Thread(delete));
	}
    }
}