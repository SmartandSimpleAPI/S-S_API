package fr.dojo59.api.compile.ConsoleAPI;

import java.util.logging.Logger;

public class Console {
    public static void println(Object text) {
        System.out.println(text.toString() + ConsoleColor.RESET);
    }

    public static void log(Logger logger, Object text) {
        logger.info(text.toString() + ConsoleColor.RESET);
    }
}
