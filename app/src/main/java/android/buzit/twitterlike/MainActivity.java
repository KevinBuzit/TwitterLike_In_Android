package android.buzit.twitterlike;

import android.buzit.twitterlike.helper.NetworkHelper;
import android.buzit.twitterlike.model.HttpResult;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation des valeurs
        login = (EditText) findViewById(R.id.login_txt);
        pwd = (EditText) findViewById(R.id.pwd_txt);

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new SigninAsyncTask().execute(login.getText().toString(), pwd.getText().toString());
            }
        });
    }

    public class SigninAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if(!NetworkHelper.isInternetAvailable((MainActivity.this))){
                return "Error !";
            }

            Map<String, String> parameters = new HashMap<>();
            parameters.put("username", params[0]);
            parameters.put("pwd", params[1]);

            HttpResult r = NetworkHelper.doPost("http://cesi.cleverapps.io/signin",parameters, null);

            if(r.code == 200) {
                try {
                    return new JSONObject(r.json).optString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                Intent i = new Intent(MainActivity.this, TchatActivity.class);
                i.putExtra(TchatActivity.Token, s);
                startActivity(i);
            }
            else{
                Toast.makeText(MainActivity.this, "Erreur d'authentification", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
