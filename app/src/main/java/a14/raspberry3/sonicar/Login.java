package a14.raspberry3.sonicar;

/**
 * Created by eyung on 3/23/2018.
 */
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView attempt;
    private Button login_button;
    int attempt_counter = 3;
    String login;
    boolean Start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }


    public void LoginButton(){
        username = findViewById(R.id.editText_user);
        password = findViewById(R.id.editText_password);
        attempt = findViewById(R.id.textView_attempt_count);
        login_button = findViewById(R.id.button_login);

        attempt.setText(Integer.toString(attempt_counter));


        login_button.setOnClickListener(
                new View.OnClickListener() {



                    @Override
                    public void onClick(View v) {
                        if ((username.getText().toString().equals("user")) &&
                                (password.getText().toString().equals("pass"))){
                            Toast.makeText(Login.this,"Access Granted",
                                    Toast.LENGTH_SHORT).show();

                           FragmentSettings.switchButton2.setVisibility(View.VISIBLE);
                           FragmentSettings.textView2.setVisibility(View.VISIBLE);
//                           FragmentBluetooth.Driver1.setEnabled(false);

                        }
                        else {
                            Toast.makeText(Login.this,"Incorrect Username and/or password",
                                    Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            attempt.setText(Integer.toString(attempt_counter));
                            if(attempt_counter==0)
                                login_button.setEnabled(false);
                        }


                    }
                }
        );
    }


}