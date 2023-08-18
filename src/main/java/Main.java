import java.io.IOException;

public class Main {
    final static int CountThreads = 64;

    public static void main(String[] args) {
        Server server = new Server();

        server.addHandler("GET", "/messages", (request, responseStream) ->
        {
            try {
                responseStream.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n").getBytes());
                responseStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        server.addHandler("GET", "/updates", (request, responseStream) ->
        {
            try {
                var response = "Hello";
                responseStream.write((
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: " + response.length() + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n").getBytes());
                responseStream.write(response.getBytes());
                responseStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        server.addHandler("POST", "/updatesforpost", (request, responseStream) ->
        {
            try {
                var response = "Hello";
                responseStream.write((
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: " + response.length() + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n").getBytes());
                responseStream.write(response.getBytes());
                responseStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        server.start(CountThreads);
    }
}



