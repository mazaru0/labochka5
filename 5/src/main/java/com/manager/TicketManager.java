package com.manager;

import com.manager.commands.*;
import com.manager.commands.base.Command;
import com.manager.commands.base.Environment;
import com.manager.commands.exception.CommandException;
import com.manager.model.*;

import java.time.Instant;
import java.util.*;
import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class TicketManager {
    private PriorityQueue<Ticket> tickets;
    private final String filePath;

    public TicketManager(String filePath) {
        this.filePath = filePath;
        this.tickets = new PriorityQueue<>(Comparator.comparingLong(Ticket::getId));
        loadFromFile(filePath);
    }

    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("Ticket");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String idStr = element.getElementsByTagName("id").item(0).getTextContent();
                long id = 0;
                if (idStr != null && !idStr.trim().isEmpty() && !idStr.equals("null")) {
                    id = Long.parseLong(idStr);
                } else {
                    System.err.println("Ошибка: неверный ID для билета. Пропускаем этот билет.");
                    continue;
                }

                String name = element.getElementsByTagName("name").item(0).getTextContent();
                if (name == null || name.trim().isEmpty() || name.equals("null")) {
                    System.err.println("Ошибка: имя билета не может быть пустым или 'null'. Пропускаем этот билет.");
                    continue;
                }

                String priceStr = element.getElementsByTagName("price").item(0).getTextContent();
                long price = 0;
                if (priceStr != null && !priceStr.trim().isEmpty() && !priceStr.equals("null")) {
                    price = Long.parseLong(priceStr);
                } else {
                    System.err.println("Ошибка: неверная цена для билета. Пропускаем этот билет.");
                    continue;
                }

                Element coordinatesElement = (Element) element.getElementsByTagName("coordinates").item(0);
                String xStr = coordinatesElement.getElementsByTagName("x").item(0).getTextContent();
                int x = 0;
                if (xStr != null && !xStr.trim().isEmpty() && !xStr.equals("null")) {
                    x = Integer.parseInt(xStr);
                } else {
                    System.err.println("Ошибка: неверное значение координаты x. Пропускаем этот билет.");
                    continue;
                }

                String yStr = coordinatesElement.getElementsByTagName("y").item(0).getTextContent();
                int y = 0;
                if (yStr != null && !yStr.trim().isEmpty() && !yStr.equals("null")) {
                    y = Integer.parseInt(yStr);
                } else {
                    System.err.println("Ошибка: неверное значение координаты y. Пропускаем этот билет.");
                    continue;
                }
                Coordinates coordinates = new Coordinates(x, y);

                TicketType type = null;
                try {
                    String typeString = element.getElementsByTagName("type").item(0).getTextContent().toUpperCase();
                    if (typeString != null && !typeString.trim().isEmpty() && !typeString.equals("null")) {
                        type = TicketType.valueOf(typeString); // Преобразуем строку в TicketType
                    } else {
                        System.err.println("Ошибка: неверный тип билета для ID " + id + ". Пропускаем этот билет.");
                        continue;
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка: неверный тип билета для ID " + id + ". Пропускаем этот билет.");
                    continue;
                }

                // Загрузка места
                Element venueElement = (Element) element.getElementsByTagName("venue").item(0);
                String venueIdStr = venueElement.getElementsByTagName("id").item(0).getTextContent();
                long venueId = 0;
                if (venueIdStr != null && !venueIdStr.trim().isEmpty() && !venueIdStr.equals("null")) {
                    venueId = Long.parseLong(venueIdStr);
                } else {
                    System.err.println("Ошибка: неверный ID места для билета ID " + id + ". Пропускаем этот билет.");
                    continue;
                }

                String venueName = venueElement.getElementsByTagName("name").item(0).getTextContent();
                if (venueName == null || venueName.trim().isEmpty() || venueName.equals("null")) {
                    System.err.println("Ошибка: название места не может быть пустым для билета ID " + id + ". Пропускаем этот билет.");
                    continue;
                }

                String capacityStr = venueElement.getElementsByTagName("capacity").item(0).getTextContent();
                Integer venueCapacity = null;
                if (capacityStr != null && !capacityStr.trim().isEmpty() && !capacityStr.equals("null")) {
                    venueCapacity = Integer.parseInt(capacityStr);
                }

                Venue venue = new Venue(venueName, venueCapacity);

                Ticket ticket = new Ticket(name, coordinates, Date.from(Instant.now()), price, type, venue);
                tickets.add(ticket);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        final List<String> commandHistory = new LinkedList<>();

        String filePath = System.getenv("TICKET_FILE");
        if (filePath == null) {
            filePath = "tickets.xml";
        }
        TicketManager manager = new TicketManager(filePath);
        Scanner scanner = new Scanner(System.in);

        HashMap<String, Command> map = new HashMap<>();
        Add add = new Add();
        map.put(add.getName(), add);
        Update update = new Update();
        map.put(update.getName(), update);
        Clear clear = new Clear();
        map.put(clear.getName(), clear);
        Info info = new Info();
        map.put(info.getName(), info);
        Exit exit = new Exit();
        map.put(exit.getName(), exit);
        RemoveAnyByPrice removeAnyByPrice = new RemoveAnyByPrice();
        map.put(removeAnyByPrice.getName(), removeAnyByPrice);
        RemoveById removeById = new RemoveById();
        map.put(removeById.getName(), removeById);
        RemoveHead removeHead = new RemoveHead();
        map.put(removeHead.getName(), removeHead);
        Save save = new Save();
        map.put(save.getName(), save);
        Show show = new Show();
        map.put(show.getName(), show);
        Help help = new Help();
        map.put(help.getName(), help);
        History history = new History();
        map.put(history.getName(), history);

        Environment environment = new Environment(map, manager.tickets, manager.filePath, commandHistory);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] s = line.split(" ");
            String[] commandArgs = new String[s.length - 1];
            System.arraycopy(s, 1, commandArgs, 0, commandArgs.length);
            if (map.containsKey(s[0])) {
                commandHistory.add(s[0]);
                if (commandHistory.size() > 7) {
                    commandHistory.remove(0);
                }
                Command command = map.get(s[0]);
                try {
                    command.execute(environment, System.out, System.in, commandArgs);
                } catch (CommandException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Неизвестная команда, используйте help, чтобы посмотреть список доступных команд.");
            }
        }
    }
}