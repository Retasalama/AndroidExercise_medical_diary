package t.e.medicaldiary;

import android.app.Activity;
import android.content.Intent;
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

        // actions user can choose from listview
        actions = getResources().getStringArray(R.array.actionmenu);
        list = (ListView)findViewById(R.id.lv_main);

        CustomList adapter = new CustomList(MainActivity.this, actions, imageId);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You clicked at " +actions[+position], Toast.LENGTH_SHORT).show();

                // open new activity based on clicked listview item
                switch (position){
                    case 0 :
                        Intent intent = new Intent(MainActivity.this, MedicinDiaryActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


    }


}


