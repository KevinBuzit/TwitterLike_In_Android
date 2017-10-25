package android.buzit.twitterlike.model;

/**
 * Created by buzit on 25/10/2017.
 */

public class Message {
    private String login;
    private String message;
    private long date;

    public Message(String username, String message, long date) {
        this.login = username;
        this.message = message;
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
