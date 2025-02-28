public class Venue {
    private long id;
    private String name;
    private Integer capacity;

    public Venue(long id, String name, Integer capacity) {
        this.id = id;
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
}
