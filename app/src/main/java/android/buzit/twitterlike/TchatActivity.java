package android.buzit.twitterlike;

import android.buzit.twitterlike.Adapters.MessageAdapter;
import android.buzit.twitterlike.helper.JsonParser;
import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.HttpResult;
import android.buzit.twitterlike.model.Message;
import android.content.Context;
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

    public static String Token = "Token";

    ListView listView;
    EditText msg;
    String token_val;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tchat);

        token_val = this.getIntent().getExtras().getString(Token);
        if(token_val == null) {
            Toast.makeText(this, "Pas de Token présent", Toast.LENGTH_SHORT).show();
            finish();
        }

        listView = (ListView) findViewById(R.id.post_list);
        adapter = new MessageAdapter(TchatActivity.this);
        listView.setAdapter(adapter);

        new GetMessagesAsyncTask(TchatActivity.this).execute();

        msg = (EditText) findViewById(R.id.message_msg);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg.getText().toString().isEmpty()){
                    msg.setError("Veuillez saisir un message");
                    return;
                }
                new SendMessageAsyncTask().execute(msg.getText().toString());
                msg.setText("");
            }
        });
    }

    protected class GetMessagesAsyncTask extends AsyncTask<Void, Void, List<Message>> {

        Context context;

        public GetMessagesAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected List<Message> doInBackground(Void... params) {
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {
                HttpResult result = NetworkHelper.doGet("http://cesi.cleverapps.io/messages", null, token_val);

                if(result.code == 200) {
                    // Convert the InputStream into a string
                    return JsonParser.getMessages(result.json);
                }
                return null;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (Exception e) {
                Log.e("NetworkHelper", e.getMessage());
                return null;
            }
        }

        @Override
        public void onPostExecute(final List<Message> msgs){
            int nb = 0;
            if(msgs != null){
                nb = msgs.size();
            }
            Toast.makeText(TchatActivity.this, "loaded nb messages: "+nb, Toast.LENGTH_LONG).show();
            adapter.addMessage(msgs);
        }
    }

    protected class SendMessageAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                Map<String, String> p = new HashMap<>();
                p.put("message", params[0]);
                HttpResult r = NetworkHelper.doPost("http://cesi.cleverapps.io/messages", p, token_val);

                return r.code;

            } catch (Exception e) {
                Log.e("NetworkHelper", e.getMessage());
                return null;
            }
        }

        @Override
        public void onPostExecute(Integer status) {
            new GetMessagesAsyncTask(TchatActivity.this).execute();
            if (status != 200) {
                Toast.makeText(TchatActivity.this, "Erreur d'envoi du message", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
