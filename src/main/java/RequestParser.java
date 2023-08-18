import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RequestParser {

    public static Request parse(BufferedInputStream in, BufferedOutputStream out) throws IOException {

        BuilderRequest builderRequest = new BuilderRequest();
        final var limit = 4096;
        in.mark(limit);
        final var buffer = new byte[limit];
        final var read = in.read(buffer);

        final var requestLineDelimiter = new byte[]{'\r', '\n'};
        final var requestLineEnd = indexOf(buffer, requestLineDelimiter, 0, read);

        printError(requestLineEnd, out);

        final var requestLine = new String(Arrays.copyOf(buffer, requestLineEnd)).split(" ");
        final var method = requestLine[0];
        final var path = requestLine[1];

        System.out.println("----->Method\r\n" + method);
        System.out.println("----->Path\r\n" + path);

        final var headersDelimiter = new byte[]{'\r', '\n', '\r', '\n'};
        final var headersStart = requestLineEnd + requestLineDelimiter.length;
        final var headersEnd = indexOf(buffer, headersDelimiter, headersStart, read);

        printError(headersEnd, out);

        in.reset();

        in.skip(headersStart);

        final var headersBytes = in.readNBytes(headersEnd - headersStart);
        final var headers = Arrays.asList(new String(headersBytes).split("\r\n"));
        System.out.println(headers);

        if (!method.equals("GET")) {
            in.skip(headersDelimiter.length);

            final var contentLength = extractHeader(headers, "Content-Length");
            byte[] bodyBytes = new byte[0];
            if (contentLength.isPresent()) {
                final int length = Integer.parseInt(contentLength.get());
                bodyBytes = in.readNBytes(length);

                final String body = new String(bodyBytes);
                System.out.println("----->Body\r\n" + body);
            }
            return builderRequest
                    .method(method)
                    .path(path)
                    .headers(headers)
                    .body(bodyBytes)
                    .build();
        }
        return builderRequest
                .method(method)
                .path(path)
                .headers(headers)
                .build();
    }

    private static Optional<String> extractHeader(List<String> headers, String header) {
        return headers.stream()
                .filter(o -> o.startsWith(header))
                .map(o -> o.substring(o.indexOf(" ")))
                .map(String::trim)
                .findFirst();
    }


    private static int indexOf(byte[] array, byte[] target, int start, int max) {
        outer:
        for (int i = start; i < max - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    public static void printError(int value, BufferedOutputStream out) throws IOException {
        if (value == -1) {
            ConnectionHandler.notFound(out);
            throw new IOException("Something went wrong");
        }
    }
}
