package t.e.medicaldiary;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicinHistoryActivity extends AppCompatActivity {
    private DBUserAdapter db;
    private TextView textView;
    private int counter = 0;

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
                //medicin_names.add(cursor.getString(3));
                //medicin_names.add(cursor.getString(0));
                //medicin_names.add(cursor.getString(1));

                //get date and medicin name from cursor and split the date in the form "dd.mm.yyyy"
                String date = cursor.getString(0);
                String medicin = cursor.getString(1);
                String[] items1 = date.split("-");
                String date2 = items1[2] + "." + items1[1] + "." + items1[0];
                medicin_names.add(date2);
                medicin_names.add(medicin);

            } while (cursor.moveToNext());

        }
        for (int i=0; i < medicin_names.size();i++){
            textView.append(medicin_names.get(i) + "\t" + "\t");
            counter++;
            if(counter == 2){
                textView.append("\n");
                counter = 0;
            }

        }

        db.close();
    }
}
