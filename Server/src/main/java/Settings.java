import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Settings {
    public int readSetting() {
        int port = 0;
        File file = new File("Server/public/settings.txt");
        List<Integer> listPort = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int i;
            while ((i = fileInputStream.read()) != -1) {
                listPort.add(i - '0');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int x : listPort) {
            port = port * 10 + x;
        }
        return port;
    }
}

