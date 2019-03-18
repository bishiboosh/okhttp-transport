package eu.sweetlygeek.google.api.client.http.okhttp;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpRequest extends LowLevelHttpRequest {

    private OkHttpClient client;

    private OkHttpClient.Builder clientBuilder;

    private String method;

    private String url;

    private Request.Builder requestBuilder;

    public OkHttpRequest(OkHttpClient client, String method, String url) {
        this.client = client;
        this.method = method;
        this.url = url;
    }

    @Override
    public void addHeader(String name, String value) {
        if (requestBuilder == null) requestBuilder = new Request.Builder();
        requestBuilder.addHeader(name, value);
    }

    @Override
    public void setTimeout(int connectTimeout, int readTimeout) {
        if (clientBuilder == null) clientBuilder = client.newBuilder();
        clientBuilder
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public LowLevelHttpResponse execute() throws IOException {
        if (getStreamingContent() != null) {
            if (HttpMethods.POST.equals(method) || HttpMethods.PUT.equals(method)) {
                requestBuilder.method(method, new RequestBody() {
                    @Override
                    public long contentLength() {
                        return getContentLength();
                    }

                    @Nullable
                    @Override
                    public MediaType contentType() {
                        if (getContentType() == null) {
                            return null;
                        } else {
                            return MediaType.parse(getContentType());
                        }
                    }

                    @Override
                    public void writeTo(@Nonnull BufferedSink sink) throws IOException {
                        getStreamingContent().writeTo(sink.outputStream());
                    }
                });
            } else {
                throw new IllegalArgumentException(method + " with non-zero content is not supported");
            }
        } else {
            requestBuilder.method(method, null);
        }

        OkHttpClient client = clientBuilder == null ? this.client : clientBuilder.build();

        return new OkHttpResponse(client.newCall(requestBuilder.url(url).build()).execute());
    }
}
