/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.pm.Client;

import eric.FileTools;
import eric.GUI.ZDialog.ZButton;
import eric.GUI.ZDialog.ZDialog;
import eric.GUI.ZDialog.ZTextFieldAndLabel;
import eric.GUI.palette.PaletteManager;
import eric.JZirkelCanvas;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import rene.gui.Global;
import rene.util.MyVector;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.PlumbObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

/**
 *
 * @author PM
 */
public class ClientNetworkTools extends ZDialog{

    private ZTextFieldAndLabel targetslist;
    private ZButton send_work, send_objects;
    private Communication com;
    private int FWIDTH = 240;
    private boolean point, line, circle, function, real_time;

    public ClientNetworkTools(Communication com){
        super("Network Tools", 0, 0, 366, 70, false, false);
        //BWIDTH = 80;
        this.com = com;
        addContent();
    }

    public void init(int w, int h) {
        int x = (w-D_WIDTH)/2;
        int y = h-D_HEIGHT-4;
        setBounds(x, y, D_WIDTH, D_HEIGHT);
    }

    private void addContent(){
        send_work = new ZButton(Global.Loc("network.client.sendwork")) {

            @Override
            public void action(){
                try {
                    String str = PaletteManager.geomSelectedIcon(); //(*)
                    PaletteManager.deselectgeomgroup(); //(*)
                    com.send(FileTools.getCurrentFileSource());
                    this.pressed(this);
                    PaletteManager.setSelected_with_clic(str, true); //(*)
                    
                    /* these three lines are a trick to prevent
                     * the cursor to be placed in the next component,
                     * which is the field targetslist
                     * A more elegant solution have to be found..
                     */
                } catch (Exception ex) {
                    System.out.println("Erreur d'envoi");
                }
            }
        };

	targetslist = new ZTextFieldAndLabel(Global.Loc("job.gui.targets"), "", 0, CHEIGHT) {

            @Override
            public void actionMouse() {
		PaletteManager.deselectgeomgroup();
		ZirkelCanvas zc = JZirkelCanvas.getCurrentZC();

		zc.setTool(new SelectionTool((ClientNetworkTools) this.getParent(), this));
		zc.showStatus("Select Objects");

		if(!this.getText().equals("")){
		    String[] names = this.getText().split(";");
		    for(String name : names){
			ConstructionObject o = zc.getConstruction().find(name);
			if(o!=null){
			    o.setSelected(true);
			}
		    }
		}
		zc.repaint();
            }

            @Override
            public void actionKey(KeyEvent k) {
		//A programmer
            }
        };

        send_objects = new ZButton(Global.Loc("network.client.share")) {

            @Override
            public void action(){
		if(!targetslist.getText().equals("")){
		    String[] names = targetslist.getText().split(";");
		    final ByteArrayOutputStream bout = new ByteArrayOutputStream();

		    XmlWriter xml = new XmlWriter(new PrintWriter(new OutputStreamWriter(bout), true));
		    xml.printXml();

		    xml.startTagNewLine("Objects");
		    for(String name : names){
			ConstructionObject o = JZirkelCanvas.getCurrentZC().getConstruction().find(name);
			/*
                        Enumeration e = o.depending();
			while(e.hasMoreElements()){
			    ((ConstructionObject) e.nextElement()).save(xml);
			}
			o.save(xml);
                        */
                        
                        ArrayList<ConstructionObject> list = Collections.list(o.depending());
                        find_all_depending(list, xml);
                        o.save(xml);
			o.setSelected(false);
		    }
		    JZirkelCanvas.getCurrentZC().repaint();
		    xml.endTagNewLine("Objects");
		    //cc.send(out.toString("utf-8"));
		    com.send("<Global>\n"+bout.toString());
		    targetslist.setText("");
                    this.pressed(this);
		    //System.out.println(out.toString());
		}
            }
        };

        this.add(send_work);
        this.add(targetslist);
        this.add(send_objects);
        
        fixComponents();
    }
    
    public XmlWriter find_all_depending(ArrayList<ConstructionObject> list, XmlWriter xml) {
        ArrayList<ConstructionObject> l ;
        
        for(int i = 0; i<list.size(); i++) {
            l = Collections.list(list.get(i).depending());
            xml = find_all_depending(l, xml);
            
            list.get(i).save(xml);
        }
        
        return xml;
    }

    @Override
    public void fixComponents(){
        send_work.setBounds((D_WIDTH-2*BWIDTH)/2, MARGINTOP1, 2*BWIDTH, CHEIGHT);
        targetslist.setBounds(MARGINW, MARGINTOP2, FWIDTH, CHEIGHT);
        send_objects.setBounds(FWIDTH+2*MARGINW, MARGINTOP2, BWIDTH, CHEIGHT);
    }

    @Override
    public void doClose() {
        com.close();
	JZirkelCanvas.getCurrentZC().remove(this);
	JZirkelCanvas.getCurrentZC().repaint();
        JZirkelCanvas.getCurrentZC().set_cnt(null);
    }

    public void set_accepted_object(String obj, boolean bol){
	if(obj.equals("point")){
	    point = bol;
	} else if(obj.equals("line")){
	    line = bol;
	} else if(obj.equals("circle")){
	    circle = bol;
	} else if(obj.equals("function")){
	    function = bol;
	}
    }

    public boolean get_accepted_object(String obj){
	if(obj.equals("point")){
	    return point;
	} else if(obj.equals("line")){
	    return line;
	} else if(obj.equals("circle")){
	    return circle;
	} else if(obj.equals("function")){
	    return function;
	}
	return false;
    }

    public void set_real_time(boolean bol){
	real_time = bol;
    }

    public boolean get_real_time(){
	return real_time;
    }

    @Override
    public void send(String s){
        com.send(s);
    }
}

class SelectionTool extends ObjectConstructor{
    private ZTextFieldAndLabel targetslist;
    private ClientNetworkTools cnt = null;

    public SelectionTool(ClientNetworkTools cnt, ZTextFieldAndLabel targetslist){
	this.targetslist = targetslist;
	this.cnt = cnt;
    }

    @Override
    public void mousePressed(MouseEvent e, ZirkelCanvas zc){
	final ConstructionObject o = zc.selectObject(e.getX(), e.getY());
	if(o==null){
	    return;
	}
	if((cnt.get_accepted_object("point") && o instanceof PointObject)
			|| (cnt.get_accepted_object("line") && o instanceof PrimitiveLineObject)
			|| (cnt.get_accepted_object("circle") && o instanceof PrimitiveCircleObject)
			|| (cnt.get_accepted_object("function") && o instanceof FunctionObject)){
	    if(o.selected()){
		o.setSelected(false);
		targetslist.setText(targetslist.getText().replaceAll(o.getName()+";", ""));
	    } else {
		o.setSelected(true);
		targetslist.setText(targetslist.getText()+o.getName()+";");
	    }
	    zc.repaint();
	}
    }

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc, final boolean simple) {
	Enumeration en = zc.getConstruction().elements();
	MyVector V = new MyVector();
	while(en.hasMoreElements()){
	    ConstructionObject o = (ConstructionObject) en.nextElement();
	    if(o.nearto(e.getX(), e.getY(), zc)){
		if((cnt.get_accepted_object("point") && o instanceof PointObject)
			|| (cnt.get_accepted_object("line") && o instanceof PrimitiveLineObject)
			|| (cnt.get_accepted_object("circle") && o instanceof PrimitiveCircleObject)
			|| (cnt.get_accepted_object("function") && o instanceof FunctionObject)){
		    V.addElement(o);
		    break;
		}
	    }
	}
	zc.indicate(V);
    }
}