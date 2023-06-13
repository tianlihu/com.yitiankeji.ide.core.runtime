package com.yitiankeji.ide.core.runtime;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private final Plugin plugin;
    private final PrintStream out;

    private static final String TRACE = "TRACE";
    private static final String DEBUG = "DEBUG";
    private static final String INFO = "INFO";
    private static final String WARN = "WARN";
    private static final String ERROR = "ERROR";

    public Log(Plugin plugin) throws IOException {
        this.plugin = plugin;

        File file = new File(new File("."), ".log");
        out = new PrintStream(file);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void trace(String message) {
        logfile(TRACE, message);
    }

    public void trace(String format, Object arg) {
        logfile(TRACE, format, arg);
    }

    public void trace(String format, Object... args) {
        logfile(TRACE, format, args);
    }

    public void trace(String message, Throwable throwable) {
        logfile(TRACE, message, throwable);
    }

    public void debug(String message) {
        logfile(DEBUG, message);
    }

    public void debug(String format, Object arg) {
        logfile(DEBUG, format, arg);
    }

    public void debug(String format, Object... args) {
        logfile(DEBUG, format, args);
    }

    public void debug(String message, Throwable throwable) {
        logfile(DEBUG, message, throwable);
    }

    public void info(String message) {
        logfile(INFO, message);
    }

    public void info(String format, Object arg) {
        logfile(INFO, format, arg);
    }

    public void info(String format, Object... args) {
        logfile(INFO, format, args);
    }

    public void info(String message, Throwable throwable) {
        logfile(INFO, message, throwable);
    }

    public void warn(String message) {
        logfile(WARN, message);
    }

    public void warn(String format, Object arg) {
        logfile(WARN, format, arg);
    }

    public void warn(String format, Object... args) {
        logfile(WARN, format, args);
    }

    public void warn(String message, Throwable throwable) {
        logfile(WARN, message, throwable);
    }

    public void error(String message) {
        logfile(ERROR, message);
    }

    public void error(String format, Object arg) {
        logfile(ERROR, format, arg);
    }

    public void error(String format, Object... args) {
        logfile(ERROR, format, args);
    }

    public void error(String message, Throwable throwable) {
        logfile(ERROR, message, throwable);
    }

    private void logfile(String level, String message) {
        String prefix = level + " " + plugin.getId() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " ";
        out.println(prefix + message);
        out.flush();
    }

    private void logfile(String level, String format, Object... args) {
        String prefix = level + " " + plugin.getId() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " ";
        out.printf(prefix + (format) + "%n", args);
        out.flush();
    }

    private void logfile(String level, String message, Throwable throwable) {
        logfile(level, message);
        throwable.printStackTrace(out);
        out.flush();
    }

    public void close() {
        out.flush();
        out.close();
    }
}
