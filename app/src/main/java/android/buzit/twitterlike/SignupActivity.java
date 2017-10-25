package android.buzit.twitterlike;

import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.HttpResult;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText login;
    EditText pwd;
    EditText url;
    Button inscription_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login = (EditText) findViewById(R.id.login_txt);
        pwd = (EditText) findViewById(R.id.pwd_txt);
        url = (EditText) findViewById(R.id.url_txt);
        inscription_btn = (Button) findViewById(R.id.inscription_btn);

        inscription_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new SignupAsyncTask().execute(login.getText().toString(), pwd.getText().toString(), url.getText().toString());
            }
        });
    }

    public class SignupAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if(!NetworkHelper.isInternetAvailable((SignupActivity.this))){
                return "Error !";
            }

            Map<String, String> parameters = new HashMap<>();
            parameters.put("username", params[0]);
            parameters.put("pwd", params[1]);
            parameters.put("urlPhoto", params[2]);

            HttpResult r = NetworkHelper.doPost("http://cesi.cleverapps.io/signup",parameters, null);

            if(r.code == 200) {
                return "Succès";
            }
            return "Echec";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(SignupActivity.this,s + " de la création du compte",Toast.LENGTH_SHORT).show();
            SignupActivity.this.finish();
        }
    }
}
