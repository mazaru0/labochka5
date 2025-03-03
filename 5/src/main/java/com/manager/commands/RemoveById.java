package com.manager.commands;

import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;
import com.manager.model.Ticket;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.PriorityQueue;

public class RemoveById extends Command {
    public RemoveById() {
        super("remove_by_id");
    }

    @Override
    public void execute(Environment env, PrintStream stdout, InputStream stdin, String[] commandArgs) {
        try {
            PriorityQueue<Ticket> updatedQueue = new PriorityQueue<>(Comparator.comparingLong(Ticket::getId));
            boolean found = false;

            while (!env.getTickets().isEmpty()) {
                Ticket ticket = env.getTickets().poll();
                if (ticket.getId() == Integer.parseInt(commandArgs[0])) {
                    found = true;
                } else {
                    updatedQueue.add(ticket);
                }
            }

            env.setTickets(updatedQueue);

            if (found) {
                stdout.println("Билет с ID " + Integer.parseInt(commandArgs[0]) + " удален.");
            } else {
                stdout.println("Билет с ID " + Integer.parseInt(commandArgs[0]) + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: ID должен быть числом.");
        }
    }

    @Override
    public String getHelp() {
        return "Эта команда удаляет элемент с указанным ID. Пример использования: remove_by_id 15";
    }
}
