package ca.uqam.mgl7230.tp1;

import java.io.IOException;

import static ca.uqam.mgl7230.tp1.config.ApplicationInitializer.init;
import static ca.uqam.mgl7230.tp1.config.ServerInitializer.initServer;
import static ca.uqam.mgl7230.tp1.service.ExecuteService.execute;

public class Application {

    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            System.out.println("Server service started...");
            initServer();
        } else {
            System.out.println("Service started...");
            init();
            execute();
        }
    }
}
