package com.yitiankeji.ide.core.runtime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {

    private final Plugin plugin;

    public Log(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void trace(String msg) {
        log.trace(msg);
    }

    public void trace(String format, Object arg) {
        log.trace(format, arg);
    }

    public void trace(String format, Object... args) {
        log.trace(format, args);
    }

    public void trace(String msg, Throwable throwable) {
        log.trace(msg, throwable);
    }

    public void debug(String message) {
        log.debug(message);
    }

    public void debug(String format, Object arg) {
        log.debug(format, arg);
    }

    public void debug(String format, Object... args) {
        log.debug(format, args);
    }

    public void debug(String message, Throwable throwable) {
        log.debug(message, throwable);
    }

    public void info(String message) {
        log.info(message);
    }

    public void info(String message, Throwable throwable) {
        log.info(message, throwable);
    }

    public void info(String format, Object arg) {
        log.info(format, arg);
    }

    public void info(String format, Object... args) {
        log.info(format, args);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void warn(String format, Object arg) {
        log.warn(format, arg);
    }

    public void warn(String format, Object... args) {
        log.warn(format, args);
    }

    public void warn(String message, Throwable throwable) {
        log.warn(message, throwable);
    }

    public void error(String message) {
        log.error(message);
    }

    public void error(String format, Object arg) {
        log.error(format, arg);
    }

    public void error(String format, Object... args) {
        log.error(format, args);
    }

    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
