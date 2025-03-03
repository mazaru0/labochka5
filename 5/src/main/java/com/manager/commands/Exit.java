package com.manager.commands;

import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;

import java.io.InputStream;
import java.io.PrintStream;

public class Exit extends Command {
    public Exit() {
        super("exit");
    }

    @Override
    public void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) {
        System.exit(0);
    }

    @Override
    public String getHelp() {
        return "Эта команда завершает программу.";
    }
}
