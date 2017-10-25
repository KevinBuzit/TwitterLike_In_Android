package android.buzit.twitterlike.Adapters;

import android.app.Activity;
import android.buzit.twitterlike.R;
import android.buzit.twitterlike.model.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by buzit on 25/10/2017.
 */

public class MessageAdapter extends BaseAdapter {
    List<Message> messages;
    Activity a;

    public MessageAdapter(Activity a){
        this.a = a;
    }

    @Override
    public int getCount() {
        if(messages == null){
            return 0;
        }
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            LayoutInflater inflater = a.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            vh = new ViewHolder();
            vh.username = (TextView) convertView.findViewById(R.id.msg_user);
            vh.message = (TextView) convertView.findViewById(R.id.msg_message);
            vh.date = (TextView) convertView.findViewById(R.id.msg_date);
            convertView.setTag(vh);
        }else
        {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.username.setText(getItem(position).getLogin());
        vh.message.setText(getItem(position).getMessage());
        vh.date.setText(String.valueOf(getItem(position).getDate()));

        return convertView;
    }

    public void addMessage(List<Message> msgs) {
        this.messages = msgs;
        this.notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView username, message, date;
    }
}
