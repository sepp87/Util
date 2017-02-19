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
     * @param removeQuotations removes first and last character of the values if
     * true
     * @param file *.csv to read
     * @return a list of lists. Each list represents a line.
     */
    public static List<List<String>> readCommaSeperatedFile(boolean removeQuotations, File file) {
        return readDelimiterSeperatedFile(",", removeQuotations, file);
    }

    /**
     * @param delimiter to seperate the strings by
     * @param removeQuotations removes first and last character of the values if
     * true
     * @param file *.csv to read
     * @return a list of lists. Each list represents a line.
     */
    public static List<List<String>> readDelimiterSeperatedFile(String delimiter, boolean removeQuotations, File file) {
        List<List<String>> lol = new ArrayList<>();
        List<String> list = readFile(file);
        int size = list.size();
        int numOfColumns = 0;
        boolean initialized = false;
        for (int i = 0; i < size; i++) {
            String line = list.get(i);
            String[] raw = line.split(delimiter);
            if (!initialized) {
                numOfColumns = raw.length;
                initialized = true;
            }
          
            //Safeguard for when a row takes up multiple lines
            while (raw.length != numOfColumns) {
                i++;
                System.out.println(file.getPath());
                line += list.get(i);
                raw = line.split(delimiter);
            }

            List<String> values = new ArrayList<>();
            for (int j = 0; j < raw.length; j++) {
                String val = raw[j];
                if (removeQuotations) {
                    //Safeguard for when a value is not surrounded by quotations
                    if (!val.startsWith("\"")) {
                        removeQuotations = false;
                    }

                    val = val.substring(1, val.length() - 1);
                }
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
     * @param addQuotations put values between quotations "value"
     */
    public static void writeCommaSeperatedFile(List<List<String>> lists, File file, boolean addQuotations) {
        writeDelimiterSeperatedFile(lists, ",", file, addQuotations);
    }

    /**
     * @param lists list of lists containing strings to join
     * @param delimiter to seperate the strings by
     * @param file to write to, e.g. *.csv or *.txt
     * @param addQuotations put values between quotations "value"
     */
    public static void writeDelimiterSeperatedFile(List<List<String>> lists, String delimiter, File file, boolean addQuotations) {

        List<String> strings = new ArrayList<>();
        for (List list : lists) {
            strings.add(getDelimiterSeperatedLine(list, delimiter, addQuotations));
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
     * @param addQuotations put values between quotations "value"
     * @return a token seperated line
     */
    public static String getDelimiterSeperatedLine(List<String> list, String delimiter, boolean addQuotations) {
        StringBuilder b = new StringBuilder();
        for (String s : list) {
            if (addQuotations) {
                s = "\"" + s + "\"";
            }
            b.append(s);
            b.append(delimiter);
        }
        b.deleteCharAt(b.length() - delimiter.length());

        return b.toString();
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

    public static File getFile(String extension, String... path) {
        return getFile(path, extension);
    }

    public static File getFile(String[] path, String extension) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            sb.append(path[i]);
            if (i == path.length - 1) {
                continue;
            }
            sb.append(File.separatorChar);
        }
        if (extension != null) {
            sb.append(".").append(extension);
        }
        return new File(sb.toString());
    }
}
