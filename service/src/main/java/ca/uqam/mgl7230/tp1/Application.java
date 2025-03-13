package ca.uqam.mgl7230.tp1;

import java.io.IOException;

import static ca.uqam.mgl7230.tp1.config.ApplicationInitializer.init;
import static ca.uqam.mgl7230.tp1.service.ExecuteService.execute;

public class Application {

    public static void main(String[] args) throws IOException {
        System.out.println("Service started...");
        init();
        execute();
    }
}
