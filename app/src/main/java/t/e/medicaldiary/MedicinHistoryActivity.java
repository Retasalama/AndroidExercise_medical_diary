package t.e.medicaldiary;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicinHistoryActivity extends AppCompatActivity {
    private DBUserAdapter db;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicin_history);
        db = new DBUserAdapter(this);
        textView = (TextView)findViewById(R.id.tv_medicin_history);
        User user = new User();
        String userId = user.getId();

        String str="";
        db.open();


        Cursor cursor = db.getAllTakenMedicins(userId);
        ArrayList<String> medicin_names = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                medicin_names.add(cursor.getString(1));
                medicin_names.add(cursor.getString(2));
                medicin_names.add(cursor.getString(3));
            } while (cursor.moveToNext());

        }
        for (int i=0; i < medicin_names.size();i++){
            textView.append(medicin_names.get(i));
            textView.append("\n");
        }

        db.close();
    }
}
