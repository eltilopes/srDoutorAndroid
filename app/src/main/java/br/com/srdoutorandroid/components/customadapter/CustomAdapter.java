package br.com.srdoutorandroid.components.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.srdoutorandroid.R;

/**
 * Created by raphael.bruno on 24/07/2015.
 */
public class CustomAdapter extends BaseAdapter {

    Context mContext;
    int mLayoutResourceId;
    CustomAdapterItem mData[] = null;

    // Constructor
    public CustomAdapter(Context context, int layoutResourceId, CustomAdapterItem[] data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }

    public CustomAdapter(Context context, int layoutResourceId, List<CustomAdapterItem> data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = new CustomAdapterItem[data.size()];
        for (int i = 0; i<data.size(); i++) this.mData[i] = data.get(i);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }


    public int getItemPosition(String label) {
        for (int i = 0; i < mData.length; i++) {
            if(mData[i].getText1().equals(label)){
                return i;
            }
        }
        return 0;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void remove(int position){
        mData[position] = null;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
       // if(view != null) return view;

        CustomAdapterItem objectDrawerItem = mData[position];
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItem = inflater.inflate(mLayoutResourceId, parent, false);

        TextView nameTextView1 = (TextView) listItem.findViewById(R.id.text1);
        TextView nameTextView2 = (TextView) listItem.findViewById(R.id.text2);
        ImageView iconImageView1 = (ImageView) listItem.findViewById(R.id.icon1);

        nameTextView1.setText(objectDrawerItem.getText1());
        if(nameTextView2 != null && nameTextView2.getParent() != null){
            if(objectDrawerItem.getText2() != null) {
                nameTextView2.setText(objectDrawerItem.getText2());
                nameTextView2.setVisibility(View.VISIBLE);
            }else {
                nameTextView2.setVisibility(View.INVISIBLE);
            }
        }
        if(objectDrawerItem.getIcon() != null){
            iconImageView1.setImageDrawable(listItem.getResources().getDrawable(objectDrawerItem.getIcon()));
        }

        return listItem;
    }

    public void add(CustomAdapterItem customAdapterItem, int i) {
        mData[i] = customAdapterItem;
    }
}
