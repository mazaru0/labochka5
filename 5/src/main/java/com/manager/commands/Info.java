package com.manager.commands;

import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;

public class Info extends Command {
    public Info() {
        super("info");
    }

    @Override
    public void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) {
        stdout.println("Тип коллекции: " + env.getTickets().getClass().getSimpleName());
        stdout.println("Дата инициализации: " + new Date());
        stdout.println("Количество элементов: " + env.getTickets().size());
    }

    @Override
    public String getHelp() {
        return "Эта команда покажет основную информацию о коллекции.";
    }
}
