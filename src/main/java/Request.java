import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Request {
    private final String method;
    private final String path;

    private final String protocolVersion;
    private final List<String> headers;
    private final byte[] body;

    public Request(String method, String path, String protocolVersion, List<String> headers, byte[] body) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getQueryParam(String name) {
        String value = null;
        String paramsLine = new String(getBody());
        List<NameValuePair> params = URLEncodedUtils.parse(paramsLine, StandardCharsets.UTF_8);
        for (NameValuePair param : params) {
            if (param.getName() == name) {
                value = param.getValue();
            }
        }
        return value;
    }

    public List<NameValuePair> getQueryParams() {
        String paramsLine = new String(getBody());
        List<NameValuePair> params = URLEncodedUtils.parse(paramsLine, StandardCharsets.UTF_8);
        return params;
    }


}
