package t.e.medicaldiary;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tanja on 21/02/2018.
 */

public class CustomList extends ArrayAdapter {

    private final Activity context;
    private final String[] action;
    private final Integer[] imageId;

    public CustomList(Activity context, String[] action, Integer[] imageId) {
        super(context, R.layout.list_activity_main, action);
        this.context = context;
        this.action = action;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_activity_main, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tv_actionmenu);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.lv_image);
        txtTitle.setText(action[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
