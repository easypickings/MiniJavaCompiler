package utils;

import java.util.ArrayList;

public class ErrorHandler {
    protected static ArrayList<String> errs = new ArrayList<>();
    protected static ArrayList<String> warns = new ArrayList<>();

    public static boolean Error() {
        if (errs.size() > 0) {
            PrintAll();
            return false;
        }
        System.out.println("\33[32mDone\33[0m");
        return true;
    }

    public static void Error(String err, int row, int col) {
        String error = "[ERROR] " + err + " (" + row + "," + col + ")";
        errs.add(error);
        // System.err.println(error);
    }

    public static void Warn(String warn, int row, int col) {
        String warning = "[WARN] " + warn + " (" + row + "," + col + ")";
        warns.add(warning);
    }

    public static void PrintAll() {
        System.out.println("\33[31m" + errs.size() + " error(s) \33[32mand " +
                "\33[33m" + warns.size() + " warning(s) \33[32mfound\33[31m");
        for (String err : errs) System.err.println(err);
        System.out.print("\33[33m");
        for (String warn : warns) System.err.println(warn);
        System.out.print("\33[0m");
    }
}