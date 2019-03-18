package eu.sweetlygeek.google.api.client.http.okhttp;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import okhttp3.OkHttpClient;

import java.io.IOException;

/**
 * {@link HttpTransport} using OkHttp as a basis.
 */
public class OkHttpTransport extends HttpTransport {

    private OkHttpClient client;

    public OkHttpTransport(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpTransport() {
        this(new OkHttpClient());
    }

    @Override
    protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new OkHttpRequest(client, method, url);
    }
}
