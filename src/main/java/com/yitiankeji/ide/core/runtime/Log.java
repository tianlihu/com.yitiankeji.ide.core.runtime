package com.yitiankeji.ide.core.runtime;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 平台日志 **/
public class Log {

    /** 平台日志所在的插件 **/
    private final Plugin plugin;
    /** 平台日志文件输出流 **/
    private final PrintStream out;

    /** 日志级别：跟踪级别 **/
    private static final String TRACE = "TRACE";
    /** 日志级别：调试级别 **/
    private static final String DEBUG = "DEBUG";
    /** 日志级别：信息级别 **/
    private static final String INFO = "INFO";
    /** 日志级别：警告级别 **/
    private static final String WARN = "WARN";
    /** 日志级别：错误级别 **/
    private static final String ERROR = "ERROR";

    /**
     * 平台日志构造器
     * 创建日志文件流
     **/
    public Log(Plugin plugin) throws IOException {
        this.plugin = plugin;

        File file = new File(Platform.getLocation(), ".log");
        out = new PrintStream(file);
    }

    /** 获取平台日志所在的插件 **/
    public Plugin getPlugin() {
        return plugin;
    }

    /** 记录跟踪级别日志 **/
    public void trace(String message) {
        logfile(TRACE, message);
    }

    /** 记录跟踪级别日志 **/
    public void trace(String format, Object arg) {
        logfile(TRACE, format, arg);
    }

    /** 记录跟踪级别日志 **/
    public void trace(String format, Object... args) {
        logfile(TRACE, format, args);
    }

    /** 记录跟踪级别日志 **/
    public void trace(String message, Throwable throwable) {
        logfile(TRACE, message, throwable);
    }

    /** 记录调试级别日志 **/
    public void debug(String message) {
        logfile(DEBUG, message);
    }

    /** 记录调试级别日志 **/
    public void debug(String format, Object arg) {
        logfile(DEBUG, format, arg);
    }

    /** 记录调试级别日志 **/
    public void debug(String format, Object... args) {
        logfile(DEBUG, format, args);
    }

    /** 记录调试级别日志 **/
    public void debug(String message, Throwable throwable) {
        logfile(DEBUG, message, throwable);
    }

    /** 记录信息级别日志 **/
    public void info(String message) {
        logfile(INFO, message);
    }

    /** 记录信息级别日志 **/
    public void info(String format, Object arg) {
        logfile(INFO, format, arg);
    }

    /** 记录信息级别日志 **/
    public void info(String format, Object... args) {
        logfile(INFO, format, args);
    }

    /** 记录信息级别日志 **/
    public void info(String message, Throwable throwable) {
        logfile(INFO, message, throwable);
    }

    /** 记录警告级别日志 **/
    public void warn(String message) {
        logfile(WARN, message);
    }

    /** 记录警告级别日志 **/
    public void warn(String format, Object arg) {
        logfile(WARN, format, arg);
    }

    /** 记录警告级别日志 **/
    public void warn(String format, Object... args) {
        logfile(WARN, format, args);
    }

    /** 记录警告级别日志 **/
    public void warn(String message, Throwable throwable) {
        logfile(WARN, message, throwable);
    }

    /** 记录错误级别日志 **/
    public void error(String message) {
        logfile(ERROR, message);
    }

    /** 记录错误级别日志 **/
    public void error(String format, Object arg) {
        logfile(ERROR, format, arg);
    }

    /** 记录错误级别日志 **/
    public void error(String format, Object... args) {
        logfile(ERROR, format, args);
    }

    /** 记录错误级别日志 **/
    public void error(String message, Throwable throwable) {
        logfile(ERROR, message, throwable);
    }

    /** 记录日志到文件中 **/
    private void logfile(String level, String message) {
        String prefix = level + " " + plugin.getId() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " ";
        out.println(prefix + message);
        out.flush();
    }

    /** 记录日志到文件中 **/
    private void logfile(String level, String format, Object... args) {
        String prefix = level + " " + plugin.getId() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " ";
        out.printf(prefix + (format) + "%n", args);
        out.flush();
    }

    /** 记录日志到文件中 **/
    private void logfile(String level, String message, Throwable throwable) {
        logfile(level, message);
        throwable.printStackTrace(out);
        out.flush();
    }

    /** 关闭日志文件流 **/
    public void close() {
        out.flush();
        out.close();
    }
}
