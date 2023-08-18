import org.apache.http.NameValuePair;

import java.util.List;

public class BuilderRequest {
    private String method;
    private String path;

    private String protocolVersion;
    private List<String> headers;
    private List<NameValuePair> queryParams;

    public BuilderRequest method(String method) {
        this.method = method;
        return this;
    }

    public BuilderRequest path(String path) {
        this.path = path;
        return this;
    }

    public BuilderRequest protocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    public BuilderRequest headers(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public BuilderRequest queryParams(List<NameValuePair> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public Request build() {
        return new Request(method, path, protocolVersion, headers, queryParams);
    }
}
