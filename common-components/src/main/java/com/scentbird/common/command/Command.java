package com.scentbird.common.command;

//here we apply the Command design pattern to provide loose coupling between invokers and targets
//deliberately named the package "command" and not "commands" as we might extend this and have classes beyond
//just command implementations
public interface Command {
    void execute();
}
