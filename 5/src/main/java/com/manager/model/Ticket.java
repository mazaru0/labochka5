package com.manager.model;

import java.util.Date;

public class Ticket {
    private static long idCounter = 1;
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private long price;
    private TicketType type;
    private Venue venue;

    public Ticket(String name, Coordinates coordinates, java.util.Date creationDate, long price, TicketType type, Venue venue) {
        this.id = idCounter++;
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        if (coordinates != null){
            this.coordinates = coordinates;
        } else {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        if (creationDate != null){
            this.creationDate = new java.util.Date();
        } else {
            throw new IllegalArgumentException("Дата не можеь быть пустой");
        }
        if (price > 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("ID должно быть больше нуля");
        }
        if (type != null){
            this.type = type;
        } else {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        if (venue != null){
            this.venue = venue;
        } else {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
    }

    public long getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public Venue getVenue() {
        return venue;
    }

    public String toXmlString() {
        return "    <Ticket>\n" +
                "        <id>" + id + "</id>\n" +
                "        <name>" + name + "</name>\n" +
                "        <price>" + price + "</price>\n" +
                "        <coordinates>\n" +
                "            <x>" + (coordinates != null ? coordinates.getX() : "") + "</x>\n" +
                "            <y>" + (coordinates != null ? coordinates.getY() : "") + "</y>\n" +
                "        </coordinates>\n" +
                "        <creationDate>" + creationDate.toString() + "</creationDate>\n" +
                "        <type>" + type + "</type>\n" +
                "        <venue>\n" +
                "            <id>" + (venue != null ? venue.getId() : "") + "</id>\n" +
                "            <name>" + (venue != null ? venue.getName() : "") + "</name>\n" +
                "            <capacity>" + (venue != null ? venue.getCapacity() : "") + "</capacity>\n" +
                "        </venue>\n" +
                "    </Ticket>";
    }

    public static void setIdCounter(long idCounter) {
        Ticket.idCounter = idCounter;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}