import org.apache.http.NameValuePair;
import java.util.List;

public class Request {
    private final String method;
    private final String path;

    private final String protocolVersion;
    private final List<String> headers;
    private final List<NameValuePair> queryParams;


    public Request(String method, String path, String protocolVersion, List<String> headers, List<NameValuePair> queryParams) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        this.queryParams = queryParams;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getHeaders() {
        return headers;
    }


    public String getPath() {
        return path;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getQueryParam(String name) {
        for (NameValuePair param : queryParams) {
            if (param.getName().equals(name)) {
                return param.getValue();
            }
        }
        return null;
    }

    public List<NameValuePair> getQueryParams() {
        return queryParams;
    }
}
