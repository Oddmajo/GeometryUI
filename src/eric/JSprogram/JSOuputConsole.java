/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.JSprogram;

import java.awt.*;
    import java.io.*;
    import javax.swing.*;

    public class JSOuputConsole extends JFrame {
        JTextArea textArea = new JTextArea();

        public JSOuputConsole() {
            Font font = new Font("Verdana", Font.PLAIN, 16);
            textArea.setFont(font);
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);

            // Add a scrolling text area
            textArea.setEditable(false);
            textArea.setRows(10);
            textArea.setColumns(25);
            getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
            pack();
            setVisible(false);

            // Create reader threads
//            new ReaderThread(piOut).start();
        }

        public void print(String msg){
            textArea.append(msg);
        }

        public void println(String msg){
            textArea.append(msg+"\n");//plus classique d'aller à la ligne après
        }

        

    }