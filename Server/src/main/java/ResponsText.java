public class ResponsText {
    public String response404() {
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }

    public String responseWriteOk(String type, long length) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + type + "\r\n" +
                "Content-Length: " + length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }
}
