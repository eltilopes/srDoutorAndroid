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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.util.ImageUtil;

public class GoogleCardsAdapter extends ArrayAdapter<Medico>
        implements View.OnClickListener {

    private LayoutInflater mInflater;

    public GoogleCardsAdapter(Context context, List<Medico> items) {
        super(context, 0, items);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_item_google_cards, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.list_item_google_cards_media_image);
            holder.nomeMedico = (TextView) convertView
                    .findViewById(R.id.list_item_google_cards_media_nome_medico );
            holder.especialidade = (TextView) convertView
                    .findViewById(R.id.list_item_google_cards_media_especialidade);
            holder.email = (TextView) convertView
                    .findViewById(R.id.list_item_google_cards_email);
            holder.like = (TextView) convertView
                    .findViewById(R.id.list_item_google_cards_media_like_1);
            holder.favorite = (TextView) convertView
                    .findViewById(R.id.list_item_google_cards_media_favorite);
            holder.like.setOnClickListener(this);
            holder.favorite.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.like.setTag(position);
        holder.favorite.setTag(position);
        Medico item = getItem(position);
        ImageUtil.displayImage(holder.image, item.getUrlFoto(), null);
        holder.nomeMedico.setText(item.getNome());
        holder.especialidade.setText(item.getEspecialidade());
        holder.email.setText(item.getEmail());
        return convertView;
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView nomeMedico ;
        public TextView especialidade;
        public TextView text;
        public TextView email;
        public TextView like;
        public TextView favorite;
        public TextView share;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int possition = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.list_item_google_cards_media_like_1:
                // click on share button
                Toast.makeText(getContext(), "Like " + possition, Toast.LENGTH_SHORT).show();
                break;
            case R.id.list_item_google_cards_media_favorite:
                // click on share button
                Toast.makeText(getContext(), "Favorite " + possition, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
