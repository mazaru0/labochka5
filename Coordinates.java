public class Coordinates {
    private int x; // Используем int для целочисленных координат //Значение поля должно быть больше -9, Поле не может быть null
    private int y; //Значение поля должно быть больше -133

    public Coordinates(int x, int y) {
        if (x > -9) { // Убедимся, что x больше -9
            this.x = x;
        } else {
            throw new IllegalArgumentException("Значение x должно быть больше -9");
        }
        if (y>-133){
        this.y = y;}
        else { throw new IllegalArgumentException("Значение у должно быть больше -133");}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
