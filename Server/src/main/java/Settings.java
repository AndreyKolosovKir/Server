import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    public int readSetting() {
        int port = 0;
        File file = new File("C:\\Users\\User\\IdeaProjects\\untitled98\\Server\\public\\settings.txt");
        List<Integer> listPort = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int i;
            while ((i = fileInputStream.read()) != -1) {
                listPort.add(i - '0');
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i : listPort) {
            port = port * 10 + 1;
        }
        return port;
    }
}
