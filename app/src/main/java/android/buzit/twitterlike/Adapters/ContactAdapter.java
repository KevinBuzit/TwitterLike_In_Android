package android.buzit.twitterlike.Adapters;

import android.app.Activity;
import android.buzit.twitterlike.R;
import android.buzit.twitterlike.model.Contact;
import android.buzit.twitterlike.model.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by buzit on 25/10/2017.
 */

public class ContactAdapter extends BaseAdapter {
    List<Contact> contacts;
    Activity a;

    public ContactAdapter(Activity a){
        this.a = a;
    }

    @Override
    public int getCount() {
        if(contacts == null){
            return 0;
        }
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactAdapter.ViewHolder vh;
        if(convertView == null){
            LayoutInflater inflater = a.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
            vh = new ContactAdapter.ViewHolder();
            vh.username = (TextView) convertView.findViewById(R.id.user_name);
            vh.pwd = (TextView) convertView.findViewById(R.id.user_pwd);
            convertView.setTag(vh);
        }else
        {
            vh = (ContactAdapter.ViewHolder) convertView.getTag();
        }
        vh.username.setText(getItem(position).getUsername());
        vh.pwd.setText(getItem(position).getPwd());

        return convertView;
    }

    private class ViewHolder{
        TextView username, pwd;
    }

    public void addContact(List<Contact> liste_contacts) {
        this.contacts = liste_contacts;
        this.notifyDataSetChanged();
    }
}
