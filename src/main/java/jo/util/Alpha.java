package jo.util;

import java.io.File;
import java.util.List;

/**
 * @author JoostMeulenkamp
 */
public class Alpha {

    public static void main(String[] args) {
        File pmc = new File("resources/csv/newpmc.csv");

        List<List<String>> p = jo.util.IO.readDelimiterSeperatedFile(";", false, pmc);
    }
}
