package br.com.srdoutorandroid.components.customadapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.srdoutorandroid.R;

/**
 * Created by elton on 02/04/2017.
 */

public class IndicatorPageAdapter {

    public static void verificarPaginator(ImageView imageViewTela1, ImageView imageViewTela2, ImageView imageViewTela3,
                                          Button buttonComecar, int selectedPage, int count) {
        switch(selectedPage) {
            case 0:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_full);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_empty);
                buttonComecar.setVisibility(View.INVISIBLE);
                break;
            case 1:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_full);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_empty);
                buttonComecar.setVisibility(View.INVISIBLE);
                break;
            case 2:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_full);
                buttonComecar.setVisibility(View.VISIBLE);
                break;
        }

    }
}