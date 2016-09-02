/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

/**
 *
 * @author erichake
 */
public class OS {

    public static boolean isWindows() {
        String os=System.getProperty("os.name").toLowerCase();
        //windows
        return (os.contains("win"));
    }

    public static boolean isMac() {
        String os=System.getProperty("os.name").toLowerCase();
        //Mac
        return (os.contains("mac"));
    }

    public static boolean isUnix() {
        String os=System.getProperty("os.name").toLowerCase();
        //linux or unix
        return (os.contains("nix") || os.contains("nux"));
    }


    /**
     *
     * @param ver : 1 for java 1.1 , 2 for java 1.2 , 3 for java 1.3 etc....
     * @return true if java version is older than the one you asked for
     */
    public static boolean isJavaOlderThan(int ver){
        String version=System.getProperty("java.version");
        for (int i=0;i<ver;i++){
            if (version.startsWith("1."+i)){
                return true;
            }
        }
        return false;
    }
}
