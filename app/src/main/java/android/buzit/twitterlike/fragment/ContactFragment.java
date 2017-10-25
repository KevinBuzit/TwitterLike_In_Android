package android.buzit.twitterlike.fragment;

import android.app.Fragment;
import android.buzit.twitterlike.Adapters.ContactAdapter;
import android.buzit.twitterlike.ContactActivity;
import android.buzit.twitterlike.R;
import android.buzit.twitterlike.helper.JsonParser;
import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.Contact;
import android.buzit.twitterlike.model.HttpResult;
import android.buzit.twitterlike.model.Session;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by buzit on 25/10/2017.
 */

public class ContactFragment extends Fragment {
    ListView listView;
    String token_val;
    ContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {
        View v = inflater.inflate(
                R.layout.fragment_contacts, container, false);

        token_val = Session.getInstance().getToken();
        if(token_val == null) {
            Toast.makeText(getContext(), "Pas de Token présent", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        listView = (ListView) v.findViewById(R.id.contact_list);
        adapter = new ContactAdapter(getActivity());
        listView.setAdapter(adapter);

        new GetContactsAsyncTask(getContext()).execute();
        return v;
    }

    protected class GetContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

        Context context;

        public GetContactsAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected List<Contact> doInBackground(Void... params) {
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {
                HttpResult result = NetworkHelper.doGet("http://cesi.cleverapps.io/users", null, token_val);

                if(result.code == 200) {
                    // Convert the InputStream into a string
                    return JsonParser.getContact(result.json);
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
        public void onPostExecute(final List<Contact> contacts){
            int nb = 0;
            if(contacts != null){
                nb = contacts.size();
            }
            Toast.makeText(getContext(), "Nombre de contacts: "+nb, Toast.LENGTH_LONG).show();
            adapter.addContact(contacts);
        }
    }
}
