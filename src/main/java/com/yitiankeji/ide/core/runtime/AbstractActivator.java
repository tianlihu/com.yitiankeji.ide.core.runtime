package com.yitiankeji.ide.core.runtime;

public abstract class AbstractActivator {

    protected Plugin plugin;

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;
}
