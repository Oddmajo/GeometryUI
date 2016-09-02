/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import javax.imageio.ImageIO;
import rene.util.FileName;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ImageObject;

/**
 *
 * @author erichake
 */
public class Media {

    private byte[] imagebytes;
    private String imagefilename; //short name, with extension and without path
    private static ArrayList<Media> medias=new ArrayList<Media>();

    private Media(String name, byte[] datas) {
        imagefilename=name;
        imagebytes=datas;
    }

    private Media(String name, InputStream in) {
        this(name, FileTools.copyToByteArray(in));
    }

    public String getImageFileName(){
        return imagefilename;
    }

    public byte[] getImageBytes(){
        return imagebytes;
    }

    static public ArrayList<Media> getMedias(){
        return medias;
    }

   public static void clearMedias() {
       medias=null;
        medias=new ArrayList<Media>();
    }

    static public void createMedia(String name, byte[] datas) {
        medias.add(new Media(name, datas));
    }

    static public void createMedia(String filename) {
        String shortname=FileName.filename(filename);
        for (int i=0; i<medias.size(); i++) {
            if (shortname.equals(medias.get(i).imagefilename)) {
                return;
            }
        }
        try {
            InputStream in=new FileInputStream(new File(filename));
            medias.add(new Media(shortname, in));
        } catch (Exception ex) {
        }
    }

    static int checkMedia(){
	int i, compt;
	Enumeration e;

	for(i = medias.size()-1; i>=0; i--){
	    compt = 0;
	    e = JZirkelCanvas.getCurrentZC().getConstruction().elements();
	    while(e.hasMoreElements() && compt==0){
		ConstructionObject o = (ConstructionObject) e.nextElement();
		if(o instanceof ImageObject && ((ImageObject) o).getFilename().equals(medias.get(i).getImageFileName())){
		    compt++;
		}
	    }
	    if(compt==0) {
		medias.remove(i);
	    }
	}

	return medias.size();
    }

    static public Image getImage(String name) {
        String shortname=FileName.filename(name);
        for (int i=0; i<medias.size(); i++) {
            if (shortname.equals(medias.get(i).imagefilename)) {
                try {
                    ByteArrayInputStream ba=new ByteArrayInputStream(medias.get(i).imagebytes);
                    Image img=ImageIO.read(ba);
                    return img;
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }
}
