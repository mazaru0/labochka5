public class Ticket {
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private long price;
    private TicketType type;
    private Venue venue;
    public Ticket(long id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String toXmlString() {
        return "    <Ticket>\n" +
                "        <id>" + id + "</id>\n" +
                "        <name>" + name + "</name>\n" +
                "        <price>" + price + "</price>\n" +
                "    </Ticket>";
    }
}
