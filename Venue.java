public class Venue {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer capacity; //Поле может быть null, Значение поля должно быть больше 0

    public Venue(long id, String name, Integer capacity) {
        if (id>0){
        this.id = id;}
        else {throw new IllegalArgumentException("id не может быль null и не больше 0");}
        if (name!=null && name.isBlank()){ this.name = name; }
        else { throw new IllegalArgumentException("имя не должно быть пустым или быть null");}
        if (capacity==null || capacity>0 ){
        this.capacity = capacity;} else { throw new IllegalArgumentException("емкость должна быть больше 0");}
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
