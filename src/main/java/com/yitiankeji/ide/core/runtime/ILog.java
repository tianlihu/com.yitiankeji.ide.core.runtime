package com.yitiankeji.ide.core.runtime;

public interface ILog {

    Plugin getPlugin();

    void debug(String message);

    void debug(String message, Throwable throwable);

    void info(String message);

    void info(String message, Throwable throwable);

    void warn(String message);

    void warn(String message, Throwable throwable);

    void error(String message);

    void error(String message, Throwable throwable);
}
