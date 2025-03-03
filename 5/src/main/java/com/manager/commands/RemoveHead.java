package com.manager.commands;

import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;
import com.manager.model.Ticket;

import java.io.InputStream;
import java.io.PrintStream;

public class RemoveHead extends Command {
    public RemoveHead() {
        super("remove_head");
    }

    @Override
    public void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) {
        if (env.getTickets().isEmpty()) {
            stdout.println("Коллекция пуста, удалить элемент невозможно.");
            return;
        }

        Ticket head = env.getTickets().poll();
        stdout.println("Удален первый элемент: " + head);
    }

    @Override
    public String getHelp() {
        return "Эта команда выводит и удаляет первый элемент коллекции.";
    }
}
