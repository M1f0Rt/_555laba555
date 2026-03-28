package org.example._555laba555;

import org.example._555laba555.cli.CommandHandler;
import org.example._555laba555.fileManager.Conservation;
import org.example._555laba555.service.ServiceManager;


public class Main {
    private static final String DEFAULT_DATA_FILE = "lab5_data.csv";
    public static void main(String[] args) {
        // Парсим аргументы командной строки
        String dataFile = parseDataFile(args);

        // Создаем менеджер сервисов (общий для всех режимов)
        ServiceManager services = new ServiceManager();

        // Создаем менеджер сохранения
        Conservation storage = new Conservation(dataFile);

        // Загружаем данные из файла при старте
        try {
            storage.load(services);
            System.out.println("Данные загружены из: " + dataFile);
        } catch (Exception e) {
            System.out.println("Предупреждение: не удалось загрузить данные - " + e.getMessage());
            System.out.println("Начинаем с пустыми данными");
        }
        CommandHandler handler = new CommandHandler(services, storage);
        handler.run();
    }
    private static String parseDataFile(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--file")) {
                return args[i + 1];
            }
        }
        return DEFAULT_DATA_FILE;
    }
}