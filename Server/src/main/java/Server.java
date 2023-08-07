import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(64);
        Settings settings = new Settings();
        ResponsText respons = new ResponsText();
        LogicResponse logicResponse = new LogicResponse();


        try (ServerSocket serverSocket = new ServerSocket(settings.readSetting())) {
            while (true) {
                Socket socket = serverSocket.accept();

                threadPool.execute(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

                        String requestLine = in.readLine();
                        String[] parts = requestLine.split(" ");

                        if (parts.length != 3) {
                            threadPool.shutdown();
                        }

                        String path = parts[1];

                        logicResponse.responsLogic(path, out, respons); //по сути сервер до этого момента принимает станджартные запросы, а работает

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
}



