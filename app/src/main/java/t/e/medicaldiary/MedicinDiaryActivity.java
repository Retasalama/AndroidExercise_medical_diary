package t.e.medicaldiary;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicinDiaryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    final Context context = this;
    private DBUserAdapter db;
    ArrayList<String> medicin_names;
    private Spinner spinner;
    private DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicin_diary);
        datePicker=(DatePicker) findViewById(R.id.datePicker);
        User user = new User();
        String str = user.getId();
        String str1 = user.getUsername();
        String str2 = user.getPassword();
        Toast.makeText(this, "user id = " + str + "\n"
                + "username = " + str1 + "\n" + "password =" + str2, Toast.LENGTH_LONG).show();
        loadSpinnerData();
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
                //Toast.makeText(this, "Add new selected", Toast.LENGTH_LONG).show(); return true;
                LayoutInflater li = LayoutInflater.from(context);
                View dialogView = li.inflate(R.layout.add_medicin_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Add new medicin");

                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);

                final EditText inputMedicinName = (EditText) dialogView.findViewById(R.id.etMedicinName);
                final EditText inputMedicinDosage = (EditText) dialogView.findViewById(R.id.etMedicinDosage);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        String medicinName = inputMedicinName.getText().toString();
                                        String medicinDosage = inputMedicinDosage.getText().toString();
                                        db.open();
                                        db.insertMedicin(medicinName, medicinDosage);
                                        db.close();
                                        loadSpinnerData();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


            case (R.id.history):
                Toast.makeText(this, "History selected", Toast.LENGTH_LONG).show(); return true;
        }
        return false; }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void loadSpinnerData(){
        db = new DBUserAdapter(this);

        //populate spinner
        db.open();
        Cursor cursor = db.getAllMedicins();
        medicin_names = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                medicin_names.add(cursor.getString(1));
            } while (cursor.moveToNext());

        }

        cursor.close();

        spinner = (Spinner) findViewById(R.id.spinner_medicins);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, medicin_names);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        db.close();
    }

    public void addMedTaken(){
        
    }
}

