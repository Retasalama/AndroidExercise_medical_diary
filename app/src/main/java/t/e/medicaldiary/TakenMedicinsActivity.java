package t.e.medicaldiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TakenMedicinsActivity extends AppCompatActivity {

    private List<Medicin> medicinList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MedsAdapter mAdapter;
    private DBUserAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taken_medicins);
        db = new DBUserAdapter(this);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        db.open();
        medicinList = db.getAllMedicins("1");


        mAdapter = new MedsAdapter(medicinList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
