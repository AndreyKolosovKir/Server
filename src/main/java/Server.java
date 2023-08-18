import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    protected Map<String, Map<String, Handler>> handlers;

    public Server() {
        handlers = new HashMap<>();
    }

    public void start(int countThread) {
        Settings settings = new Settings();
        ExecutorService threadPool = Executors.newFixedThreadPool(countThread);

        try (ServerSocket serverSocket = new ServerSocket(settings.port())) {
            while (true) {
                Socket socket = serverSocket.accept();

                threadPool.execute(() -> ConnectionHandler.handle(socket, handlers));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> methodMap = handlers.get(method);

        if (methodMap == null) {
            methodMap = new HashMap<>();
        }

        methodMap.put(path, handler);
        handlers.put(method, methodMap);
    }
}

