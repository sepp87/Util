package jo.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joost.meulenkamp
 */
public class Sys {

    private static Map<String, Process> APPS;

    public static void startCMD() {
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c cd C:\\ & start");
            //Process process = new ProcessBuilder("cmd.exe", "/c", "cd c:\\ArangoDB-3.0.5\\bin\\", "&", "start", "arangod.exe").start();
        } catch (IOException ex) {
            Logger.getLogger(Sys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void startApp(String path, boolean silent) {
        startApp(Arrays.asList(path), silent);
    }

    public static void startApp(List<String> commands, boolean silent) {
        if (APPS == null) {
            APPS = new TreeMap<>();
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder().command(commands);
            Process process = processBuilder.start();
            APPS.put(commands.get(0), process);

            if (silent) {
                return;
            }

            InputStream inputStream = process.getInputStream();
            BufferedReader isr = new BufferedReader(new InputStreamReader(inputStream));
            InputStream errorStream = process.getErrorStream();
            BufferedReader esr = new BufferedReader(new InputStreamReader(errorStream));

            String line = null;
            System.out.println("Standard Output: ");
            while ((line = isr.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Standard Error (if available): ");
            while ((line = esr.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException ex) {
            Logger.getLogger(Sys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stopApp(String path) {
        if (APPS.containsKey(path)) {
            APPS.get(path).destroy();
            APPS.remove(path);
        }

        if (APPS.isEmpty()) {
            APPS = null;
        }
    }

    public static boolean stopExternalApp(String filename, boolean force) {
        try {
            Runtime rt = Runtime.getRuntime();
            if (force) {
                rt.exec("taskkill /F /IM " + filename + ".exe");
            } else {
                rt.exec("taskkill /IM " + filename + ".exe");
            }

            return true;
        } catch (IOException ex) {
            Logger.getLogger(Sys.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void getSystemProperties() {
        System.getProperties().list(System.out);
    }

    //https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String getOperatingSystem() {

        if ((OS.indexOf("win") >= 0)) {
            return "windows";
        } else if ((OS.indexOf("mac") >= 0)) {
            return "mac";
        } else if ((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0)) {
            //This is Unix or Linux
            return "linux";
        } else if ((OS.indexOf("sunos") >= 0)) {
            return "solaris";
        } else {
            return "unknown";
        }
    }

    public static void copyStringToClipboard(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
