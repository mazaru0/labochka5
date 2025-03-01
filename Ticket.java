import java.util.Date;

public class Ticket {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long price; //Значение поля должно быть больше 0
    private TicketType type; //Поле не может быть null
    private Venue venue; //Поле не может быть null

    public Ticket(long id, String name, Coordinates coordinates, long price, TicketType type, Venue venue) {
        if (id>0){
            this.id = id;
        } else {throw new IllegalArgumentException("id должно быть больше 0"); }
        if (name!=null && name.isBlank()){ this.name = name; }
        else { throw new IllegalArgumentException("имя не должно быть пустым или быть null");}
        if ( coordinates!= null){
        this.coordinates = coordinates;}
        else {throw new IllegalArgumentException("координаты не могут быть null");}
        if (creationDate!= null){
        this.creationDate = new java.util.Date();}
        else { throw new IllegalArgumentException("дата создания не может быть null");}
        if (price > 0){
        this.price = price;}
        else { throw new IllegalArgumentException("цена должна быть больше 0");}
        if (type != null){
        this.type = type;}
        else {throw new IllegalArgumentException("тип не может быть null");}
        if (venue != null){
        this.venue = venue;}
        else {throw new IllegalArgumentException("место проведения не может быть null");}
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
