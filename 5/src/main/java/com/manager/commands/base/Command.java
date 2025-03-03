package com.manager.commands.base;

import com.manager.commands.exception.CommandException;

import java.io.InputStream;
import java.io.PrintStream;

public abstract class Command {
    private final String name;

    protected Command(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public abstract void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) throws CommandException;
    public abstract String getHelp();
}