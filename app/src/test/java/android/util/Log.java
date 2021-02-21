package android.util;

public class Log {

    public static int d(String tag, String msg) {
        System.out.println("[" + tag + ":INFO]: " + msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        System.out.println("[" + tag + ":WARNING]: " + msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        System.out.println("[" + tag + ":ERROR]: " + msg);
        return 0;
    }
}