public class Ticket {
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private long price;
    private TicketType type;
    private Venue venue;

    public Ticket(long id, String name, Coordinates coordinates, long price, TicketType type, Venue venue) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new java.util.Date();
        this.price = price;
        this.type = type;
        this.venue = venue;
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

}
