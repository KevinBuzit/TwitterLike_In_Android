package android.buzit.twitterlike.helper;

import android.buzit.twitterlike.model.Contact;
import android.buzit.twitterlike.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by buzit on 25/10/2017.
 */

public class JsonParser {
    public static List<Message> getMessages(String json) throws JSONException {
        List<Message> messages = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        Message msg;
        for(int i=0; i < array.length(); i++){
            obj = array.getJSONObject(i);
            msg = new Message(obj.optString("username"), obj.optString("message"), obj.optLong("date"));
            messages.add(msg);
        }

        return messages;
    }

    public static List<Contact> getContact(String json) throws JSONException {
        List<Contact> contacts = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        Contact ctc;
        for(int i=0; i < array.length(); i++){
            obj = array.getJSONObject(i);
            ctc = new Contact(obj.optString("username"), obj.optString("pwd"), obj.optString("url"));
            contacts.add(ctc);
        }

        return contacts;
    }

    public static String getToken(String response) throws JSONException {
        return new JSONObject(response).optString("token");
    }
}
