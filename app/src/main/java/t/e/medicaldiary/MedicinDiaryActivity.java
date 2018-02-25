package t.e.medicaldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MedicinDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicin_diary);
        User user = new User();
        String str = user.getId();
        String str1 = user.getUsername();
        String str2 = user.getPassword();
        Toast.makeText(this, "user id = " + str + "\n"
                + "username = " + str1 + "\n" + "password =" + str2, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicin_diary, menu);
        return true;
    }

    // When menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (R.id.add_new):
                Toast.makeText(this, "Add new selected", Toast.LENGTH_LONG).show(); return true;
            case (R.id.history):
                Toast.makeText(this, "History selected", Toast.LENGTH_LONG).show(); return true;
        }
        return false; }
}

