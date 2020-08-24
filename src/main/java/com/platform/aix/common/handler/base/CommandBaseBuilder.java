package com.platform.aix.common.handler.base;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 11:34
 **/

public abstract class CommandBaseBuilder {

    public static IBaseHandler commandBaseHandler() {
        return new CommandBaseHandler();
    }

    public static IBaseHandler commandSonkaExtHandler() {
        return new CommandSonkaExtHandler();
    }
}
