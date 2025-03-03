package com.manager.commands;

import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;
import com.manager.model.Ticket;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.PriorityQueue;

public class RemoveAnyByPrice extends Command {
    public RemoveAnyByPrice() {
        super("remove_any_by_price");
    }

    @Override
    public void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) {
        try {
            for (Ticket ticket : env.getTickets()) {
                if (ticket.getPrice() == Integer.parseInt(commandArgs[0])) {
                    env.getTickets().remove(ticket);
                    stdout.println("Удален билет с ценой: " + Integer.parseInt(commandArgs[0]));
                    return;
                }
            }
            stdout.println("Билет с ценой " + Integer.parseInt(commandArgs[0]) + " не найден.");
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Цена должена быть числом.");
        }
    }

    @Override
    public String getHelp() {
        return "Эта команда удаляет элемент с указанной ценой. Пример использования: remove_any_by_price 239";
    }
}
