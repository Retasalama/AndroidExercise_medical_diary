package t.e.medicaldiary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView list;
    String[] actions;
    Integer[] imageId = {
            R.drawable.diary,
            R.drawable.pill,
            R.drawable.doctor,
            R.drawable.mood};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actions = getResources().getStringArray(R.array.actionmenu);

        CustomList adapter = new CustomList(MainActivity.this, actions, imageId);
        list = (ListView)findViewById(R.id.lv_main);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You clicked at " +actions[+position], Toast.LENGTH_SHORT).show();
            }
        });


    }


}


