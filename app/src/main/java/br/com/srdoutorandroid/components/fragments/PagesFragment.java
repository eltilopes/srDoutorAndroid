package br.com.srdoutorandroid.components.fragments;

/**
 * Created by elton on 29/03/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.srdoutorandroid.R;

public class PagesFragment extends Fragment  {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Context context;
    private ImageView imageView;

    public PagesFragment newInstance(Context context, int page) {
        Bundle args = new Bundle();
        this.context = context;
        args.putInt(ARG_PAGE, page);
        PagesFragment fragment = new PagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageViewFragmentPage);
        if(mPage==1){
            imageView.setBackgroundResource(R.drawable.sliding1);
        }else if(mPage==2){
            imageView.setBackgroundResource(R.drawable.sliding2);
        }else if(mPage==3){
            imageView.setBackgroundResource(R.drawable.sliding3);
        }
        return view;
    }


}