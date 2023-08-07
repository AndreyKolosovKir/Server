import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class Settings {

    public Map<String, Integer> readSetting() {

        File file = new File("public/settings.txt");
        Map<String, Integer> settings = new HashMap<>();
        String text;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);) {
            text = new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] lines = text.split("\r\n");
        for (String x : lines) {
            String[] line = x.split(":");
            settings.put(line[0], Integer.valueOf(line[1]));
        }
        return settings;
    }

    public int port() {
        Map<String, Integer> settings = readSetting();
        return settings.get("PORT");
    }
}

