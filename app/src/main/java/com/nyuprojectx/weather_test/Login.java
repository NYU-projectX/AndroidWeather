package com.nyuprojectx.weather_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nyuprojectx.weather_test.User.User;

public class Login extends AppCompatActivity {

    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        User admin = new User("Admin", "admin");
        btLogin = (Button)findViewById(R.id.bLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsername = (EditText)findViewById(R.id.etUsername);
                EditText etPassword = (EditText)findViewById(R.id.etPassword);
                TextView tvInfo = (TextView)findViewById(R.id.tvInfo);
                String inputUsername = etUsername.getText().toString();
                String inputPassword = etPassword.getText().toString();
                if (inputUsername.equals("Admin") && inputPassword.equals("admin")) {
                    tvInfo.setText("Welcome! " + inputUsername);
                }
                else {
                    tvInfo.setText("Wrong username or password. Try again.");
                }

            }
        });
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     // Inflate the menu; this adds items to the action bar if it is present.
    //     getMenuInflater().inflate(R.menu.menu_login, menu);
    //     return true;
    // }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
