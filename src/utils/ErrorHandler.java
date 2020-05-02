<<<<<<< HEAD
package utils;

import minijava.symbol.MTypeList;

import java.util.ArrayList;

/**
 * Class to handle errors
 */
public class ErrorHandler {

    /**
     * Error strings
     */
    protected static ArrayList<String> errs = new ArrayList<>();
    protected static ArrayList<String> warns = new ArrayList<>();

    /**
     * Add an error message to errs
     *
     * @param err an error string
     */
    public static void Error(String err, int row, int col) {
        String error =
                "\33[31;1m[ERROR (" + row + "," + col + ")] \33[0m" + err;
        errs.add(error);
        // System.err.println(error);
    }

    /**
     * Add an error message to errs
     *
     * @param err     an error string
     * @param verbose true to print out the error message
     */
    public static void Error(String err, int row, int col, boolean verbose) {
        String error =
                "\33[31;1m[ERROR (" + row + "," + col + ")] \33[0m" + err;
        errs.add(error);
        if (verbose) System.err.println(error);
    }

    public static void Warn(String warn, int row, int col) {
        String warning =
                "\33[33;1m[WARNING (" + row + "," + col + ")] \33[0m" + warn;
        warns.add(warning);
    }

    /**
     * Print out all error messages
     */
    public static void PrintAll() {
        System.out.println("\33[32;1m[INFO]\33[0m\33[32m Type check done, "
                + "\33[32;1m" + errs.size()
                + "\33[0m\33[32m error(s) and \33[32;1m" + warns.size()
                + "\33[0m\33[32m warning(s) found\33[0m");
        for (String err : errs) System.err.println(err);
        System.out.print("\33[0m");
        for (String warn : warns) System.err.println(warn);
        System.out.println("\33[0m");
    }

    public static void Print() {
        if (errs.size() > 0) System.err.println("\33[31;1mERROR\33[0m");
        else System.err.println("\33[32;1mOK\33[0m");
    }

    public static void PrintSymbolTable() {
        MTypeList.Print();
    }
=======
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
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
}