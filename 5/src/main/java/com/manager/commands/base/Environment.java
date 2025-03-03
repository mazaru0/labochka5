package com.manager.commands.base;

import com.manager.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public final class Environment {
    private final HashMap<String, Command> stringCommandHashMap;
    private PriorityQueue<Ticket> tickets;
    private final String filePath;
    final List<String> commandHistory;

    public Environment(HashMap<String, Command> stringCommandHashMap, PriorityQueue<Ticket> tickets, String filePath, List<String> commandHistory) {
        this.stringCommandHashMap = stringCommandHashMap;
        this.tickets = tickets;
        this.filePath = filePath;
        this.commandHistory = commandHistory;
    }

    public HashMap<String, Command> getStringCommandHashMap() {
        return stringCommandHashMap;
    }

    public PriorityQueue<Ticket> getTickets() {
        return tickets;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setTickets(PriorityQueue<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }
}
