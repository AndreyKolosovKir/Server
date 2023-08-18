import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ConnectionHandler {

    public static void handle(Socket socket, Map<String, Map<String, Handler>> handlers) {

        try (
                final var in = new BufferedInputStream(socket.getInputStream());
                final var out = new BufferedOutputStream(socket.getOutputStream());
        ) {
            Request request = RequestParser.parse(in, out);

            var methodHandlersMap = handlers.get(request.getMethod());

            if (methodHandlersMap == null) {
                notFound(out);
                return;
            }

            var handler = methodHandlersMap.get(request.getPath());

            if (handler == null) {
                notFound(out);
                return;
            }

            handler.handle(request, out);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void notFound(BufferedOutputStream outputStream) throws IOException {
        outputStream.write((
                "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        outputStream.flush();
    }
}
