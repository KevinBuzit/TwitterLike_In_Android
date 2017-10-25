package android.buzit.twitterlike.model;

/**
 * Created by buzit on 25/10/2017.
 */

public class Contact {
    private String username;
    private String pwd;
    private String url;

    public Contact(String username, String pwd, String url){
        this.username = username;
        this.pwd = pwd;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
