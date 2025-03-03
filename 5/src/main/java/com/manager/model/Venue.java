package com.manager.model;

public class Venue {
    private static long idCounter = 1;
    private long id;
    private String name;
    private Integer capacity;

    public Venue(String name, Integer capacity) {
        this.id = idCounter++;
        this.name = name;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public static void setIdCounter(long idCounter) {
        Venue.idCounter = idCounter;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
