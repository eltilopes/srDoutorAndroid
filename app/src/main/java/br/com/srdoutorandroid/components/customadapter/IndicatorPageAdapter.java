package br.com.srdoutorandroid.components.customadapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.com.srdoutorandroid.R;

/**
 * Created by elton on 02/04/2017.
 */

public class IndicatorPageAdapter {

    LinearLayout main_image_holder;
        public static void createDotScrollBar(Context context, LinearLayout main_holder, int selectedPage, int count)
        {
            for(int i=0;i<count;i++)
            {
                ImageView dot = null;
                dot= new ImageView(context);
                LinearLayout.LayoutParams vp =
                        new LinearLayout.LayoutParams(40,40);
                vp.setMargins(20,20,20,40);

                dot.setLayoutParams(vp);
                if(i==selectedPage)
                {
                    try {
                        dot.setImageResource(R.drawable.rectangle_full);
                    } catch (Exception e)
                    {
                        Log.d("IndicatorPageAdapter","not locate identifier");
                    }
                }else
                {
                    dot.setImageResource(R.drawable.rectangle_empty);
                }
                main_holder.addView(dot);
            }
            main_holder.invalidate();
        }

    public static void verificarPaginator(ImageView imageViewTela1, ImageView imageViewTela2, ImageView imageViewTela3, int selectedPage, int count) {
        switch(selectedPage) {
            case 0:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_full);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_empty);
                break;
            case 1:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_full);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_empty);
                break;
            case 2:
                imageViewTela1.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela2.setBackgroundResource(R.drawable.rectangle_empty);
                imageViewTela3.setBackgroundResource(R.drawable.rectangle_full);
                break;
        }

    }
}