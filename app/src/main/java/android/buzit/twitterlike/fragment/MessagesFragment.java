package android.buzit.twitterlike.fragment;

import android.app.Fragment;
import android.buzit.twitterlike.Adapters.MessageAdapter;
import android.buzit.twitterlike.ContactActivity;
import android.buzit.twitterlike.R;
import android.buzit.twitterlike.TchatActivity;
import android.buzit.twitterlike.helper.JsonParser;
import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.HttpResult;
import android.buzit.twitterlike.model.Message;
import android.buzit.twitterlike.model.Session;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by buzit on 25/10/2017.
 */

public class MessagesFragment extends Fragment {

    private MessageAdapter adapter;
    String token_val;
    ListView listView;
    EditText msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View v = inflater.inflate(
                R.layout.fragment_messages, container, false);

        token_val = Session.getInstance().getToken();

        if(token_val == null) {
            Toast.makeText(getContext(),"Pas de Token présent", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        listView = (ListView) v.findViewById(R.id.post_list);
        adapter = new MessageAdapter(getActivity());
        listView.setAdapter(adapter);

        new GetMessagesAsyncTask(getContext()).execute();

        msg = (EditText) v.findViewById(R.id.message_msg);
        v.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
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

        v.findViewById(R.id.btn_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ContactActivity.class);
                startActivity(i);
            }
        });
        return v;
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
                HttpResult result = NetworkHelper.doGet("http://cesi.cleverapps.io/messages", null, Session.getInstance().getToken());

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
            new GetMessagesAsyncTask(getContext()).execute();
            if (status != 200) {
                Toast.makeText(getContext(), "Erreur d'envoi du message", Toast.LENGTH_SHORT).show();
            }
        }
    }
}