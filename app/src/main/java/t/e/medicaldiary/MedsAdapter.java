package t.e.medicaldiary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.MyViewHolder> {

    private List<Medicin> medicinList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView medicin_name, date_taken;
        public ImageButton button_edit, button_delete;

        public MyViewHolder(View view) {
            super(view);
            medicin_name = (TextView) view.findViewById(R.id.medicin_taken);
            date_taken = (TextView) view.findViewById(R.id.date_taken);
            //button_edit = (ImageButton)view.findViewById(R.id.button_edit);
            button_delete = (ImageButton)view.findViewById(R.id.button_delete);

        }
    }


    public MedsAdapter(List<Medicin> medicinList) {
        this.medicinList = medicinList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicin_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medicin medicin = medicinList.get(position);
        holder.medicin_name.setText(medicin.getMedicin_name());
        holder.date_taken.setText(medicin.getDate_given());
        //holder.button_edit.setImageResource(android.R.drawable.ic_menu_edit);
        holder.button_delete.setImageResource(android.R.drawable.ic_menu_delete);
    }

    @Override
    public int getItemCount() {
        return medicinList.size();
    }
}
