
package uja.meta.utils;

import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Carlos
 */
public class Log {

    public static final Logger LOGGER = Logger.getLogger(Log.class.getName());

    public Log() {
        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            Handler fileHandler = new FileHandler("./archivolog.log", false);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(new SimpleFormatter() {                            //para quitar el mensaje por defecto
                private static final String format = "[%1$tF %1$tT] %2$-7s %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(format,
                            new Date(lr.getMillis()),
                            lr.getSourceMethodName() + " ------",
                            lr.getMessage()
                    );
                }
            });

            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error de IO");
        } catch (SecurityException ex) {
            LOGGER.log(Level.SEVERE, "Error de Seguridad");
        }
    }
}
