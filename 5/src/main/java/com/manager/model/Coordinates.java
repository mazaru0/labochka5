package com.manager.model;

public class Coordinates {
    private int x; // Используем int для целочисленных координат
    private int y;

    public Coordinates(int x, int y) {
        if (x > -9) { // Убедимся, что x больше -9
            this.x = x;
        } else {
            throw new IllegalArgumentException("Значение x должно быть больше -9");
        }
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
