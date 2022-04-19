package dev.colin.utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class Logger {
    public static void Log(String message, LogLevel level) {
        String logMessage = "\n" + level.name() + " " + message + " " + new Date();
        try {
            Files.write(Paths.get("C:\\Users\\Colin\\Desktop\\colin_buckley_p1\\appsLog"),
                    logMessage.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Log(String message, LogLevel level, String method, String location){
        String logMessage = "\n" + level.name() + " Calling: " + method + " Location: " + location + " Date: " + new Date() + "\n\tmessage: " + message;
        try {
            Files.write(Paths.get("C:\\Users\\Colin\\Desktop\\colin_buckley_p1\\appsLog"),
                    logMessage.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
