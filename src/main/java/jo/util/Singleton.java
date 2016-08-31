package jo.util;

/**
 *
 * @author JoostMeulenkamp
 */
public class Singleton {

    private static Singleton singleton = null;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (singleton == null) {
            return singleton = new Singleton();
        } else {
            return singleton;
        }
    }
}
