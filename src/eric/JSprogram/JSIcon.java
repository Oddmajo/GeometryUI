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
package eric.JSprogram;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import eric.JEricPanel;
import javax.swing.JTextPane;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class JSIcon extends JSButton {

    /**
     *
     */
    
    String NAME;
    String CODE;
    ArrayList PATTERNS=new ArrayList();
    ArrayList STRS=new ArrayList();
    ArrayList VARS=new ArrayList();
    ArrayList CONST=new ArrayList();
    ArrayList EXEMPLES=new ArrayList();
    JSEditor JSC;
    private static final String REGEX_NUMERIC="(((?<=[-+*/(])|(?<=^))-)?\\d+(\\.\\d+)?";
    private static final String REGEX_VARIABLE="\\$[a-zA-Z][a-zA-Z0-9]*";
    public static final String REGEX_OPERATION="[a-zA-Z][a-zA-Z0-9]+|[-*/+|?:@&^<>'`=%#]";
    private static final String REGEX_PARANTHESIS="[()]";
//    private JPanel JPN=new JPanel();

    private void fixsize(final int sze) {
        final Dimension d=new Dimension(sze, sze);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setSize(d);
    }

    // Create an Icon wich belongs to group (if not null) :
//    public JSIcon(final JSConsole jsc, final String nm, String[] codes) {
    public JSIcon(JSEditor jsc, String name, String code) {
        super(name, 24, !code.equals(""));
        JSC=jsc;
        NAME=name;
        CODE=code;
        this.addMouseListener(new MouseAdapter() {

            public void mousePressed(final MouseEvent e) {
                if (!isDisabled) {
                    JSC.clearStatusBar();
                    if (e.getButton()!=MouseEvent.BUTTON3) {
                        ClicOnMe();
                    }
                    JSC.NoTypeNoClic();
                }
            }
        });
        interpret();
    }

    @Override
    public String getName() {
        return NAME;
    }
    private int[] t;
    private int[] T;

    private void interpret() {
//        exemple : <var,null>=Point(<name,null>,<nb,var,exp>,<nb,var,exp>);
        StringBuffer sb=new StringBuffer();
        Pattern p=Pattern.compile("<(\\w+[,\\w]*)>", Pattern.CASE_INSENSITIVE);
        Matcher m=p.matcher(CODE);
        while (m.find()) {
            VARS.add(m.group(1).split(","));
            m.appendReplacement(sb, "@@@@");
        }
        m.appendTail(sb);
        m.reset();
        String result=" "+sb.toString()+" ";
        String[] c=result.split("@@@@");




        for (int i=0; i<c.length; i++) {
            CONST.add(c[i].trim());
        }
        f1();
    }

    private void f1() {
        if (VARS.size()==0) {
            return;
        }
        t=new int[VARS.size()];
        T=new int[VARS.size()];
        for (int i=0; i<VARS.size(); i++) {
            T[i]=0;
            String[] col=(String[]) VARS.get(i);
            t[i]=col.length-1;
        }
        while (!arrayequal()) {

            createPatterns();
            increment();
        }
        createPatterns();
        GenerateMoreEXEMPLES();
//        generateEXEMPLES();
    }

    private void GenerateMoreEXEMPLES() {
        ArrayList moreexemple=new ArrayList();
        for (int i=0; i<EXEMPLES.size(); i++) {
            StringBuffer sb=new StringBuffer();
            Pattern p=Pattern.compile("<\"(\\w+[,\\w]*\\w+)\">", Pattern.CASE_INSENSITIVE);
            Matcher m=p.matcher((String) EXEMPLES.get(i));

            if (m.find()) {
                String[] c=m.group(1).split(",");
                m.appendReplacement(sb, "@@@@");
                m.appendTail(sb);
                String myex=sb.toString();
                for (int k=0; k<c.length; k++) {
                    if ((c[k].equals("true"))||(c[k].equals("false"))) {
                        moreexemple.add(myex.replaceFirst("@@@@", c[k]));
                    } else {

                        moreexemple.add(myex.replaceFirst("@@@@", "\""+c[k]+"\""));
                    }

                }
            } else {
                moreexemple.add(EXEMPLES.get(i));
            }
        }
        EXEMPLES=moreexemple;
    }

    private void createPatterns() {
        String st="";
        for (int m=0; m<T.length; m++) {
//            Vector col=(Vector) list.get(m);
            String[] col=(String[]) VARS.get(m);
            st+=(String) CONST.get(m)+"@"+col[T[m]]+"@";
        }

        st+=(String) CONST.get(CONST.size()-1);
        st=st.trim();
        String mystr=
                st;

        st=st.replace("(", "\\(");
        st=st.replace(")", "\\)");
        st=st.replace("@null@=", "");
        st=st.replace("@null@,", "");
        st=st.replace("@name@", "\"\\w*\"");
        st=st.replace("@exp@", "\"[^\"]+\"");
        st=st.replace("@nb@", "[a-zA-Z0-9\\.\\)\\(\\-*/+]+");
        st=st.replace("@var@", "\\w+");
        st=st.replace("@objs@", "\\w+,\\w+[,\\w+]*");
//        st="(;|\\n)\\s*"+st;

//        System.out.println(mystr);
        try {
            PATTERNS.add(Pattern.compile(st, Pattern.CASE_INSENSITIVE));
            STRS.add(mystr);
            mystr=mystr.replace("@exp@,@nb@", "@exp@,@exp@");
            mystr=mystr.replace("@exp@,@var@", "@exp@,@exp@");
            mystr=mystr.replace("@nb@,@exp@", "@nb@,@nb@");
            mystr=mystr.replace("@nb@,@var@", "@nb@,@nb@");
            mystr=mystr.replace("@var@,@exp@", "@var@,@var@");
            mystr=mystr.replace("@var@,@nb@", "@var@,@var@");
            mystr=mystr.replace("@null@=", "");
            mystr=mystr.replace("@null@,", "");

//            mystr=mystr.replace("@name@", code("name"));
//            mystr=mystr.replace("@exp@", code("exp"));
//            mystr=mystr.replace("@nb@", code("nb"));
//            mystr=mystr.replace("@var@", code("var"));
            if (!duplicateExemple(mystr)) {
                EXEMPLES.add(mystr);
            }

        } catch (Exception e) {
//            System.out.println("incorrect pattern syntax");
        }

    }

    private boolean duplicateExemple(String s) {
        for (int i=0; i<EXEMPLES.size(); i++) {
            String st=(String) EXEMPLES.get(i);
            if (st.equals(s)) {
                return true;
            }

        }
        return false;
    }

    static void fixsize(final JComponent cp,
            final int w, final int h) {
        final Dimension d=
                new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    static JEricPanel margin(final int w) {
        final JEricPanel mypan=
                new JEricPanel();
        fixsize(mypan, w, 1);
        mypan.setLayout(new javax.swing.BoxLayout(mypan,
                javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0F);
        mypan.setAlignmentY(0F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    class ItemAdapter implements ItemListener {

        public void itemStateChanged(final ItemEvent evt) {
            if (evt.getStateChange()==ItemEvent.SELECTED) {
                JSC.addOrChange((String) evt.getItem());
//                    if (doaction) {
//                        final String menuitem=(String) evt.getItem();
//                        doAction(menuitem);
//                    }


            }
        }
    }

    static class MyComboBoxUI extends MetalComboBoxUI {

        public static MetalComboBoxUI createUI(final JComponent c) {
            return new MyComboBoxUI();
        }
    }

    private boolean arrayequal() {
        for (int m=0; m<T.length; m++) {
            if (t[m]!=T[m]) {
                return false;
            }

        }
        return true;
    }

    private String code(String st) {
        if (st.equals("var")) {
            int car=(int) Math.floor(Math.random()*26);
            char a=(char) ("a".codePointAt(0)+car);
            return String.valueOf(a);
        } else if (st.equals("name")) {
            int car=(int) Math.floor(Math.random()*26);
            char a=(char) ("A".codePointAt(0)+car);
            return "\""+String.valueOf(a)+"\"";
        } else if (st.equals("nb")) {
            int i=(int) Math.round(Math.random()*10)-5;
            return ""+i;
        } else if (st.equals("exp")) {
            return "\"(x_a+x_b)/2\"";
        } else if (st.equals("objs")) {
            return "\"A,B,C\"";
        }

        return "";
    }

    private String replace(String s, String s1, String cod) {
        String mystr;
        while (!s.equals(mystr=s.replaceFirst("\\Q"+s1+"\\E", code(cod)))) {
            s=mystr;
        }

        return mystr;
    }

    public String exemple(
            int i) {
        if (i<EXEMPLES.size()) {
            String mystr=(String) EXEMPLES.get(i);
            mystr=replace(mystr, "@name@", "name");
            mystr=replace(mystr, "@exp@", "exp");
            mystr=replace(mystr, "@nb@", "nb");
            mystr=replace(mystr, "@var@", "var");
            mystr=replace(mystr, "@objs@", "objs");
            return mystr;
        } else {
            return "";
        }

    }

//    public void generateEXEMPLES() {
//        for (int k=0; k<EXEMPLES.size(); k++) {
//            String mystr=(String) EXEMPLES.get(k);
//            mystr=replace(mystr, "@name@", "name");
//            mystr=replace(mystr, "@exp@", "exp");
//            mystr=replace(mystr, "@nb@", "nb");
//            mystr=replace(mystr, "@var@", "var");
//            mystr=replace(mystr, "@objs@", "objs");
//            EXEMPLES.set(k, mystr);
//        }
//    }
    public void increment() {
        int i=T.length-1;
        while ((T[i]==t[i])&&(i>0)) {
            T[i]=0;
            i=i-1;
        }

        if (T[i]<t[i]) {
            T[i]++;
        }

    }
    private int loop=0;

    public void ClicOnMe() {
//        String st="";
//        if (EXEMPLES.size()>1) {
//            st=(String) EXEMPLES.get(0);
//        }
        JSC.addToScript(exemple(0));


//        String st="";
//
//
//
//        for (int i=0; i<VARS.size(); i++) {
//            String[] col=(String[]) VARS.get(i);
//            st+=(String) CONST.get(i)+code(col[0]);
//        }
//        ;
//        st+=(String) CONST.get(CONST.size()-1);
//        st=st.trim();
//        JSC.addToScript(st);


//        JSC.addToScript("a=Point(\"A\",5,2);");

    }

    public ArrayList matches(
            JTextPane jta) {
//        System.out.println(NAME+" : "+PATTERNS.size());
//        String reg="\\w+=Point\\(\"\\w*\",[0-9]+,[0-9]+\\);";

//        String reg="@var@=Point(@name@,@nb@,@nb@);";
//        reg=reg.replace("@var@", "\\w+");
//        reg=reg.replace("@name@", "\"\\w*\"");
//        reg=reg.replace("@nb@", "[0-9]+");
//        reg=reg.replace("(", "\\(");
//        reg=reg.replace(")", "\\)");
//        Pattern p=Pattern.compile(reg);
//        Matcher m=p.matcher(jta.getText());
//        if (m.find()) {return m.groupCount();}
//        return 0;
        ArrayList matchers=new ArrayList();

        for (int i=0; i<PATTERNS.size(); i++) {
            Pattern p=(Pattern) PATTERNS.get(i);
            Matcher m=
                    p.matcher(jta.getText());
            if (m.find()) {
                m.reset();
                matchers.add(m);
            }

        }
        return matchers;
    }
}


