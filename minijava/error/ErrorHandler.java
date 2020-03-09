/*
 * @Author       : Can Su
 * @Date         : 2020-03-05 14:03:12
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-09 19:02:31
 * @Description  : Class to handle errors
 * @FilePath     : \Compiler\minijava\error\ErrorHandler.java
 */

package minijava.error;

import minijava.symbol.*;
import java.util.*;

/**
 * Class to handle errors
 */
public class ErrorHandler {

    /** Error strings */
    protected static ArrayList<String> errs = new ArrayList<String>();
    protected static ArrayList<String> warns = new ArrayList<String>();

    /**
     * Add an error message to errs
     * 
     * @param err an error string
     */
    public static void Error(String err, int row, int col) {
        String error = "\33[31;1m[ERROR (" + row + "," + col + ")] \33[0m" + err;
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
        String error = "\33[31;1m[ERROR (" + row + "," + col + ")] \33[0m" + err;
        errs.add(error);
        if (verbose)
            System.err.println(error);
    }

    public static void Warn(String warn, int row, int col) {
        String warning = "\33[33;1m[WARNING (" + row + "," + col + ")] \33[0m" + warn;
        warns.add(warning);
    }

    /**
     * Print out all error messages
     * 
     * @return
     */
    public static void PrintAll() {
        System.out.println("\33[32;1m[INFO]\33[0m\33[32m Type check done, \33[32;1m" + errs.size()
                + "\33[0m\33[32m error(s) and \33[32;1m" + warns.size() + "\33[0m\33[32m warning(s) found\33[0m");
        for (String err : errs)
            System.err.println(err);
        System.out.print("\33[0m");
        for (String warn : warns)
            System.err.println(warn);
        System.out.println("\33[0m");
    }

    public static void PrintSymbolTable() {
        System.out.println();
        for (MType node : MTypeList.Classes().values())
            ((MClass) node).Print();
    }
}