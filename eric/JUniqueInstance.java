/* 

Copyright 2006 Eric Hakenholz

This file is part of C.a.R. software.

C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package eric;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class JUniqueInstance {

    private final int port;
    private final String message;

    public JUniqueInstance(final int port, final String message) {
        assert port>0&&port<1<<16 : "Le port doit être entre 1 et 65535";
        this.port=port;
        this.message=message;
    }

    public JUniqueInstance(final int port) {
        this(port, null);
    }

    public boolean launch() {
        boolean unique;

        try {
            final ServerSocket server=new ServerSocket(port);
            unique=true;
            final Thread portListenerThread=new Thread() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            final Socket socket=server.accept();
                            new Thread() {

                                @Override
                                public void run() {
                                    receive(socket);
                                }
                            }.start();
                        } catch (final IOException e) {
                            Logger.getLogger("UniqueProgInstance").warning(
                                    "Attente de connexion échouée.");
                        }
                    }
                }
            };
            portListenerThread.setDaemon(true);
            portListenerThread.start();

        } catch (final IOException e) {
            unique=false;
            // javax.swing.JOptionPane.showMessageDialog(null,
            // "launch()->send()");
            send();
        }
        return unique;
    }

    public void send() {
        PrintWriter pw=null;
        try {
            final Socket socket=new Socket("localhost", port);
            pw=new PrintWriter(socket.getOutputStream());
            pw.write(message);
        } catch (final IOException e) {
            Logger.getLogger("UniqueProgInstance").warning(
                    "Écriture de sortie échoué.");
        } finally {
            if (pw!=null) {
                pw.close();
            }
        }
    }

    public synchronized void launchFiles(final String f) {
        if (!f.equals("")) {
            final String[] files=f.split(System.getProperty("path.separator"));
            for (final String filename : files) {
                if (FileTools.isStartup()) {
                    FileTools.addStartupFile(filename);
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            FileTools.open(filename);
                        }
                    });
                }
            }
        }
    }

    private synchronized void receive(final Socket socket) {
        Scanner sc=null;

        try {
            socket.setSoTimeout(5000);
            sc=new Scanner(socket.getInputStream());
            final String filename=sc.nextLine();

            launchFiles(filename);

        } catch (final Exception e) {
            // javax.swing.JOptionPane.showMessageDialog(null,
            // "receive()->error");
        } finally {
            if (sc!=null) {
                sc.close();
            }
        }

    }
}
