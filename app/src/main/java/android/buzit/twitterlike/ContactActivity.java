package android.buzit.twitterlike;

import android.buzit.twitterlike.Adapters.ContactAdapter;
import android.buzit.twitterlike.Adapters.MessageAdapter;
import android.buzit.twitterlike.helper.JsonParser;
import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.Contact;
import android.buzit.twitterlike.model.HttpResult;
import android.buzit.twitterlike.model.Message;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ContactActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }


}
