package org.example._555laba555;

import org.example._555laba555.cli.CommandHandler;
import org.example._555laba555.service.ServiceManager;


public class Main {
    public static void main(String[] args) {
        ServiceManager services = new ServiceManager();
        CommandHandler handler = new CommandHandler(services);
        handler.run();
    }
}