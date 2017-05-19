package br.com.srdoutorandroid.components.customadapter;

/**
 * Created by elton on 11/05/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.model.DummyModel;
import br.com.srdoutorandroid.util.ImageUtil;

public class GoogleCardsAdapter extends ArrayAdapter<DummyModel> {

    private LayoutInflater mInflater;

    public GoogleCardsAdapter(Context context, List<DummyModel> items) {
        super(context, 0, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_google_cards, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DummyModel item = getItem(position);
        ImageUtil.displayImage(holder.image, item.getImageURL(), null);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView image;
    }
}
