package t.e.medicaldiary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegisterActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private DBUserAdapter db;
    private Cursor myListCursor;
    private TextView tvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etUsername = (EditText)findViewById(R.id.et_register_username);
        etPassword = (EditText)findViewById(R.id.et_register_password);
        tvUsers = (TextView)findViewById(R.id.tv_register);
        db = new DBUserAdapter(this);
    }

    protected void registerUser(View view){
        db.open();
        db.insertUser(etUsername.getText().toString(), etPassword.getText().toString());
        db.close();
        User user = new User();
        String str = user.getId();
        Toast.makeText(this, "user id = " + str, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        etUsername.getText().clear();
        etPassword.getText().clear();
    }

    protected void showAll(View view){
        String str="";
        db.open();
        Cursor c = db.getAllUsers();
        if (c.moveToFirst())
        {
            do {
                str=str + c.getString(0) + "\t" +  c.getString(1) + "\t" + c.getString(2)+ "\n";
            } while (c.moveToNext());
            tvUsers.setText(str);
        }
        db.close();

    }



}
