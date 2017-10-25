package android.buzit.twitterlike;

import android.buzit.twitterlike.Adapters.MessageAdapter;
import android.buzit.twitterlike.helper.JsonParser;
import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.HttpResult;
import android.buzit.twitterlike.model.Message;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TchatActivity extends AppCompatActivity {

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tchat);


    }


}
