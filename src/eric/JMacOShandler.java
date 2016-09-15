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

import javax.swing.SwingUtilities;

import eric.GUI.pipe_tools;
import ui.com.apple.eawt.Application;
import ui.com.apple.eawt.ApplicationAdapter;

/**
 * 
 * @author erichake
 */
public class JMacOShandler extends Application {

    /** Creates a new instance of JMacOShandler */
    public JMacOShandler() {
        addApplicationListener(new OpenHandler());

    }

    class OpenHandler extends ApplicationAdapter {

        @Override
        public void handleOpenFile(final ui.com.apple.eawt.ApplicationEvent evt) {
            final String filename=evt.getFilename();
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

        @Override
        public void handleQuit(final ui.com.apple.eawt.ApplicationEvent e) {
            pipe_tools.quitAll();
        }


    }
}
