package android.buzit.twitterlike.model;

/**
 * Created by buzit on 25/10/2017.
 */

public class HttpResult {
    public int code;
    public String json;

    public HttpResult(int code, String s) {
        this.code = code;
        this.json = s;
    }
}
