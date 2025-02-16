import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class TicketManager {
    private PriorityQueue<Ticket> tickets;
    private final String filePath;
    private final List<String> commandHistory;

    public TicketManager(String filePath) {
        this.filePath = filePath;
        this.tickets = new PriorityQueue<>(Comparator.comparingLong(Ticket::getId));
        this.commandHistory = new LinkedList<>();
        loadFromFile();
    }

    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("Ticket");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                long price = Long.parseLong(element.getElementsByTagName("price").item(0).getTextContent());

                tickets.add(new Ticket(id, name, price));
            }
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("<Tickets>\n");
            for (Ticket ticket : tickets) {
                writer.write(ticket.toXmlString() + "\n");
            }
            writer.write("</Tickets>");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл.");
        }
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void processCommand(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 7) {
            commandHistory.remove(0);
        }

        switch (command.split(" ")[0]) {
            case "help":
                System.out.println("Список команд: ...");
                break;
            case "show":
                tickets.forEach(System.out::println);
                break;
            case "save":
                saveToFile();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Неизвестная команда.");
        }
    }

    public static void main(String[] args) {
        String filePath = System.getenv("TICKET_FILE");
        if (filePath == null) {
            filePath = "tickets.xml";
        }
        TicketManager manager = new TicketManager(filePath);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            manager.processCommand(command);
        }
    }
}
