package jo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoostMeulenkamp
 */
public class IO {

    /**
     * @param file *.csv to read
     * @return a list of lists. Each list represents a line.
     */
    public static List<List<String>> readCommaSeperatedFile(boolean removeQuotations, File file) {
        return readDelimiterSeperatedFile(",", removeQuotations, file);
    }

    /**
     * @param file *.csv to read
     * @param delimiter to seperate the strings by
     * @return a list of lists. Each list represents a line.
     */
    public static List<List<String>> readDelimiterSeperatedFile(String delimiter, boolean removeQuotations, File file) {
        List<List<String>> lol = new ArrayList<>();
        List<String> list = readFile(file);
        for (String s : list) {
            String[] raw = s.split(delimiter);
            List<String> values = new ArrayList<>();
            for (int i = 0; i < raw.length; i++) {
                String val = raw[i];
                values.add(val);
            }
            lol.add(values);
        }
        return lol;
    }

    /**
     * @param file *.csv to read
     * @return a list of lists. Each array represents a line.
     */
    public static List<String[]> readCommaSeperatedFileAsArray(File file) {
        List<String[]> lol = new ArrayList<>();
        List<String> list = readFile(file);
        for (String s : list) {
            String[] raw = s.split(",");
            lol.add(raw);
        }
        return lol;
    }

    /**
     * @param file to read from, e.g. a *.csv or *.txt
     * @return a list of strings. Each string represents a line.
     */
    public static List<String> readFile(File file) {
        List<String> stringList = new ArrayList<>();

        try (FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr)) {

            String line = null;
            while ((line = bf.readLine()) != null) {
                stringList.add(line);
            }

        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stringList;
    }

    /**
     * @param list containing strings. Each string represents a line.
     * @param file to write to, e.g. a *.txt
     */
    public static void writeFile(List<String> list, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {

            for (String s : list) {
                writer.append(s);
                writer.newLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param lists list of lists containing strings to join. Each sub list
     * represents a line.
     * @param file to write to, likely a *.csv or *.txt
     */
    public static void writeCommaSeperatedFile(List<List<String>> lists, File file) {
        writeDelimiterSeperatedFile(lists, ",", file);
    }

    /**
     * @param lists list of lists containing strings to join
     * @param delimiter to seperate the strings by
     * @param file to write to, e.g. *.csv or *.txt
     */
    public static void writeDelimiterSeperatedFile(List<List<String>> lists, String delimiter, File file) {

        List<String> strings = new ArrayList<>();
        for (List list : lists) {
            strings.add(getDelimiterSeperatedLine(list, delimiter));
        }

        try (FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter writer = new BufferedWriter(osw);) {

            for (String s : strings) {
                writer.append(s);
                writer.newLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param list with strings to join
     * @param delimiter to seperate the strings by
     * @return a token seperated line
     */
    public static String getDelimiterSeperatedLine(List<String> list, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (String s : list) {
            b.append(s);
            b.append(delimiter);
        }
        b.deleteCharAt(b.length() - delimiter.length());

        return b.toString();
    }

    /**
     * Get the absolute path to the directory of the .jar
     *
     * @param any object within the project
     * @return the parent directory of the .jar
     */
    public static String getPathOfJAR(Object any) {
        String jarPath = any.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return jarPath.substring(0, jarPath.lastIndexOf('/') + 1);
    }

    /**
     * Get the extension of a file. <br><br>
     * Also works for e.g. path/to.a/file and config/.htaccess
     *
     * @param file in question
     * @return an empty string if none is found
     */
    public static String getExtension(File file) {
        String extension = "";
        String path = file.getPath();

        int dot = path.lastIndexOf('.');
        int slash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

        if (dot > slash + 1) {
            extension = path.substring(dot + 1);
        }
        return extension;
    }
}
