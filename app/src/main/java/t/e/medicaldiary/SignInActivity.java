package t.e.medicaldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private DBUserAdapter db;
    private String givenUsername;
    private String givenPassword;
    private String returnedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText)findViewById(R.id.et_password);
        db = new DBUserAdapter(this);
    }

    public void userSignIn(View view){

        db.open();
        givenUsername = etUsername.getText().toString();
        givenPassword = etPassword.getText().toString();
        returnedPassword = db.checkUserAndPassword(givenUsername);

        if (returnedPassword.equals(givenPassword)){
            Toast.makeText(this, "Password correct", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (returnedPassword.equals("USER DOESN´T EXIST")){
            Toast.makeText(this, returnedPassword, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Password incorrect!", Toast.LENGTH_LONG).show();
        }
        db.close();
        etUsername.getText().clear();
        etPassword.getText().clear();


    }

    public void userRegister(View view){
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
        
    }
}
