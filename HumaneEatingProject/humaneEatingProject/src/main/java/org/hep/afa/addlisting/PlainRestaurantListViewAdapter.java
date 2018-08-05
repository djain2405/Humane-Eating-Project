package org.hep.afa.addlisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.hep.afa.R;
import org.hep.afa.model.HEPRestaurant;
import org.hep.afa.utils.ColorUtils;

import java.util.List;

/**
 * RestaurantAdapter translates underlying Restaurant object to View object.
 */
public class PlainRestaurantListViewAdapter extends ArrayAdapter<HEPRestaurant>
{
    // Make usa of ViewHolder pattern. For recycling list view items.
    private static class ViewHolder
    {
        public TextView name;
    }

    List<HEPRestaurant> restaurantAdapter;
    int itemLayout;
    Context context;
    LayoutInflater inflator;

    public PlainRestaurantListViewAdapter(Context context, int itemLayout, List<HEPRestaurant> objects)
    {
        super(context, itemLayout, objects);
        this.restaurantAdapter = objects;
        this.itemLayout = itemLayout;
        this.context = context;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflator.inflate(itemLayout, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.restaurant_name);
            convertView.setTag(holder);
        } else {    // we have a recycled view, reuse.
            holder = (ViewHolder) convertView.getTag();
        }

        // set restaurant name.
        holder.name.setText(this.restaurantAdapter.get(position).name());
        // set background color.
        convertView.setBackgroundColor(ColorUtils.alternateColor(position));
        return convertView;
    }

}


