/*
 * @Author       : Can Su
 * @Date         : 2020-03-05 14:03:12
 * @LastEditors  : Can Su
 * @LastEditTime : 2020-03-05 21:58:51
 * @Description  : Class to handle errors
 * @FilePath     : \Compiler\minijava\error\ErrorHandler.java
 */

package error;

import java.util.*;
import symbol.*;

/**
 * Class to handle errors
 */
public class ErrorHandler {

    /** Error strings */
    protected static ArrayList<String> errs = new ArrayList<String>();

    /**
     * Add an error message to errs
     * 
     * @param err an error string
     * @return:
     */
    public static void Error(String err, int row, int col) {
        String error = "\33[31;1m[ERROR (" + row + "," + col + ")]: \33[0m" + err;
        errs.add(error);
        System.out.println(error);
    }

    /**
     * Add an error message to errs
     * 
     * @param err     an error string
     * @param verbose true to print out the error message
     * @return:
     */
    public static void Error(String err, int row, int col, boolean verbose) {
        String error = "\33[31;1m[ERROR (" + row + "," + col + ")]: \33[0m" + err;
        errs.add(error);
        if (verbose)
            System.out.println(error);
    }

    /**
     * Print out all error messages
     * 
     * @return:
     */
    public static void PrintAll() {
        for (String err : errs)
            System.out.println(err);
    }

    public static void PrintSymbolTable() {
        System.out.println();
        for (MType node : MTypeList.Classes().values())
            ((MClass) node).Print();
    }
}