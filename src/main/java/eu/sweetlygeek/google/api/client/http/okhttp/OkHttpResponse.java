package eu.sweetlygeek.google.api.client.http.okhttp;

import com.google.api.client.http.LowLevelHttpResponse;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.InputStream;

public class OkHttpResponse extends LowLevelHttpResponse {

    private Response response;

    public OkHttpResponse(Response response) {
        this.response = response;
    }

    @Override
    public InputStream getContent() {
        ResponseBody body = response.body();
        return body == null ? null : body.byteStream();
    }

    @Override
    public String getContentEncoding() {
        return response.header("content-encoding");
    }

    @Override
    public long getContentLength() {
        ResponseBody body = response.body();
        return body == null ? 0 : body.contentLength();
    }

    @Override
    public String getContentType() {
        ResponseBody body = response.body();
        MediaType contentType = body == null ? null : body.contentType();
        return contentType == null ? null : contentType.toString();
    }

    @Override
    public String getStatusLine() {
        return response.message();
    }

    @Override
    public int getStatusCode() {
        return response.code();
    }

    @Override
    public String getReasonPhrase() {
        return null;
    }

    @Override
    public int getHeaderCount() {
        return response.headers().size();
    }

    @Override
    public String getHeaderName(int index) {
        return response.headers().name(index);
    }

    @Override
    public String getHeaderValue(int index) {
        return response.headers().value(index);
    }
}
