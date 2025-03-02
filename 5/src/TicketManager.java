import java.util.*;
import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class TicketManager {
    private PriorityQueue<Ticket> tickets;
    private final String filePath;
    private final List<String> commandHistory;
    private final Scanner scanner;

    public TicketManager(String filePath) {
        this.filePath = filePath;
        this.tickets = new PriorityQueue<>(Comparator.comparingLong(Ticket::getId));
        this.commandHistory = new LinkedList<>();
        this.scanner = new Scanner(System.in);
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

                // Загрузка ID
                String idStr = element.getElementsByTagName("id").item(0).getTextContent();
                long id = 0;
                if (idStr != null && !idStr.trim().isEmpty() && !idStr.equals("null")) {
                    id = Long.parseLong(idStr);
                } else {
                    System.err.println("Ошибка: неверный ID для билета. Пропускаем этот билет.");
                    continue;
                }

                // Загрузка имени билета
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                if (name == null || name.trim().isEmpty() || name.equals("null")) {
                    System.err.println("Ошибка: имя билета не может быть пустым или 'null'. Пропускаем этот билет.");
                    continue;
                }

                // Загрузка цены билета
                String priceStr = element.getElementsByTagName("price").item(0).getTextContent();
                long price = 0;
                if (priceStr != null && !priceStr.trim().isEmpty() && !priceStr.equals("null")) {
                    price = Long.parseLong(priceStr);
                } else {
                    System.err.println("Ошибка: неверная цена для билета. Пропускаем этот билет.");
                    continue;
                }

                // Загрузка координат
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

                // Загрузка типа билета
                TicketType type = null;
                try {
                    String typeString = element.getElementsByTagName("type").item(0).getTextContent().toUpperCase();
                    if (typeString != null && !typeString.trim().isEmpty() && !typeString.equals("null")) {
                        type = TicketType.valueOf(typeString); // Преобразуем строку в TicketType
                    } else {
                        System.err.println("Ошибка: неверный тип билета для ID " + id + ". Пропускаем этот билет.");
                        continue;  // Пропускаем текущий билет, если тип не подходит
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка: неверный тип билета для ID " + id + ". Пропускаем этот билет.");
                    continue;  // Пропускаем текущий билет, если тип не подходит
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

                Venue venue = new Venue(venueId, venueName, venueCapacity);

                // Создание и добавление билета
                Ticket ticket = new Ticket(id, name, coordinates, price, type, venue);
                tickets.add(ticket);
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

    public void addTicket() {
        try {
            System.out.println("Введите данные для нового билета:");

            // Ввод данных для поля "id"
            System.out.print("Введите ID билета: ");
            long id = Long.parseLong(scanner.nextLine());  // Получаем ID от пользователя
            if (id <= 0) {
                throw new IllegalArgumentException("ID должен быть больше 0.");
            }
            // Проверка на уникальность ID
            if (tickets.stream().anyMatch(ticket -> ticket.getId() == id)) {
                throw new IllegalArgumentException("Билет с таким ID уже существует.");
            }


            // Ввод данных для поля "name"
            System.out.print("Введите имя билета: ");
            String name = scanner.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя билета не может быть пустым.");
            }

            // Ввод данных для поля "price"
            System.out.print("Введите цену билета (больше 0): ");
            long price = Long.parseLong(scanner.nextLine());
            if (price <= 0) {
                throw new IllegalArgumentException("Цена должна быть больше 0.");
            }

            // Ввод данных для поля "coordinates"
            System.out.println("Введите координаты:");
            System.out.print("Введите x (больше -9): ");
            int x = Integer.parseInt(scanner.nextLine());
            if (x <= -9) {
                throw new IllegalArgumentException("Значение x должно быть больше -9.");
            }

            System.out.print("Введите y (больше -133): ");
            int y = Integer.parseInt(scanner.nextLine());
            if (y <= -133) {
                throw new IllegalArgumentException("Значение y должно быть больше -133.");
            }

            Coordinates coordinates = new Coordinates(x, y);

            // Ввод данных для поля "TicketType"
            TicketType type = null;
            boolean validType = false;
            while (!validType) {
                try {
                    System.out.println("Выберите тип билета (VIP, USUAL, BUDGETARY, CHEAP):");
                    String typeInput = scanner.nextLine().toUpperCase();
                    type = TicketType.valueOf(typeInput);  // Преобразуем введенное значение в тип
                    validType = true;  // Если ввод валиден, выходим из цикла
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка: неверный тип билета. Попробуйте снова.");
                }
            }

            // Ввод данных для поля "Venue"
            System.out.print("Введите id места: ");
            long venueId = Long.parseLong(scanner.nextLine());
            System.out.print("Введите название места: ");
            String venueName = scanner.nextLine();
            if (venueName == null || venueName.trim().isEmpty()) {
                throw new IllegalArgumentException("Название места не может быть пустым.");
            }

            System.out.print("Введите вместимость места (может быть пустым): ");
            String capacityStr = scanner.nextLine();
            Integer capacity = (capacityStr.isEmpty()) ? null : Integer.parseInt(capacityStr);

            Venue venue = new Venue(venueId, venueName, capacity);

            // Создание нового билета с заданным пользователем ID
            Ticket newTicket = new Ticket(id, name, coordinates, price, type, venue);

            // Добавление билета в коллекцию
            tickets.add(newTicket);
            System.out.println("Билет успешно добавлен!");

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: введено неправильное число.");
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении билета: " + e.getMessage());
        }
    }
    public void removeHead() {
        if (tickets.isEmpty()) {
            System.out.println("Коллекция пуста, удалить элемент невозможно.");
            return;
        }

        Ticket head = tickets.poll(); // Удаляем и получаем первый элемент
        System.out.println("Удален первый элемент: " + head);
    }

    public void processCommand(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 7) {
            commandHistory.remove(0);
        }

        switch (command.split(" ")[0]) {
            case "help":
                showHelp();
                break;
            case "info":
                System.out.println("Тип коллекции: " + tickets.getClass().getSimpleName());
                System.out.println("Дата инициализации: " + new Date());
                System.out.println("Количество элементов: " + tickets.size());
                break;
            case "show":
                tickets.forEach(System.out::println);
                break;
            case "add":
                addTicket();  // Добавление нового билета
                break;
            case "save":
                saveToFile();
                break;
            case "remove_head":
                removeHead();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Неизвестная команда. Введите 'help' для получения списка команд.");
        }
    }

    private void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("  help             - Показать список доступных команд.");
        System.out.println("  info             - Вывести информацию о текущем состоянии коллекции (тип, дата и количество элементов).");
        System.out.println("  show             - Показать все элементы коллекции.");
        System.out.println("  add              - Добавить новый билет. В процессе будет предложено ввести имя, цену, координаты и другие данные.");
        System.out.println("  save             - Сохранить коллекцию билетов в файл.");
        System.out.println("  exit             - Закрыть приложение.");
        System.out.println("  command history  - Показать последние 7 введенных команд.");

        System.out.println("\nПример добавления билета:");
        System.out.println("  add");
        System.out.println("  Введите имя билета: Билет на концерт");
        System.out.println("  Введите цену билета (больше 0): 1500");
        System.out.println("  Введите координаты:");
        System.out.println("    Введите x (больше -9): 10");
        System.out.println("    Введите y (больше -133): 50");
        System.out.println("  Выберите тип билета (VIP, USUAL, BUDGETARY, CHEAP): VIP");
        System.out.println("  Введите id места: 1");
        System.out.println("  Введите название места: Концертный зал");
        System.out.println("  Введите вместимость места (может быть пустым): 500");
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