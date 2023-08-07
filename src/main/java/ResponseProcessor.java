import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseProcessor {

    private static final List<String> validPaths = List.of(
            "/index.html",
            "/spring.svg",
            "/spring.png",
            "/resources.html",
            "/styles.css",
            "/app.js",
            "/links.html",
            "/forms.html",
            "/classic.html",
            "/events.html",
            "/events.js");

    public static void responsLogic(String path, BufferedOutputStream out, ResponsText respons) throws IOException {
        if (!validPaths.contains(path)) {
            out.write((respons.response404()).getBytes());
            out.flush();
        } else {
            Path filePath = getFilePath(path);
            String mimeType = getType(filePath);
            responsePositive(path, out, respons, mimeType, String.valueOf(filePath));
        }
    }

    public static Path getFilePath(String path) {
        return Path.of("public", path);
    }

    public static String getType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }

    public static void responsePositive(String path, BufferedOutputStream out, ResponsText respons, String mimeType, String filePath) throws IOException {
        switch (path) {
            case ("/classic.html"):
                var template = Files.readString(Path.of(filePath));
                var content = template.replace(
                        "{time}",
                        LocalDateTime.now().toString()
                ).getBytes();
                out.write((respons.responseWriteOk(mimeType, content.length)
                ).getBytes());
                out.write(content);
                out.flush();
                break;
            default:
                var length = Files.size(Path.of(filePath));
                out.write((respons.responseWriteOk(mimeType, length)
                ).getBytes());
                Files.copy(Path.of(filePath), out);
                out.flush();
                break;
        }
    }
}
