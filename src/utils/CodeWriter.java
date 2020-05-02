package utils;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CodeWriter {
    protected static PrintWriter file;

    public static void open(String name) {
        try {
            file = new PrintWriter(name, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        file.close();
    }

    public static void emit(String code) {
        emit(code + "\n", "\t");
    }

    public static void emit(String code, String indent) {
        file.print(indent + code);
    }
}
