package eu.sweetlygeek.google.api.client.http.okhttp;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import okhttp3.OkHttpClient;

import java.util.Arrays;

/**
 * {@link HttpTransport} using OkHttp as a basis.
 */
public class OkHttpTransport extends HttpTransport {

    private static final String[] SUPPORTED_METHODS = new String[]{
            HttpMethods.PUT,
            HttpMethods.POST,
            HttpMethods.CONNECT,
            HttpMethods.DELETE,
            HttpMethods.GET,
            HttpMethods.HEAD,
            HttpMethods.OPTIONS,
            HttpMethods.PATCH,
            HttpMethods.TRACE
    };

    static {
        Arrays.sort(SUPPORTED_METHODS);
    }

    private OkHttpClient client;

    public OkHttpTransport(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpTransport() {
        this(new OkHttpClient());
    }

    @Override
    protected LowLevelHttpRequest buildRequest(String method, String url) {
        return new OkHttpRequest(client, method, url);
    }

    @Override
    public boolean supportsMethod(String method) {
        return Arrays.binarySearch(SUPPORTED_METHODS, method) >= 0;
    }
}
