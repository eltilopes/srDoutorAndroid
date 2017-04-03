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
                        dot.setImageResource(R.drawable.circle_full);
                    } catch (Exception e)
                    {
                        Log.d("IndicatorPageAdapter","not locate identifier");
                    }
                }else
                {
                    dot.setImageResource(R.drawable.circle_empty);
                }
                main_holder.addView(dot);
            }
            main_holder.invalidate();
        }
    }