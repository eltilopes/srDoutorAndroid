package br.com.srdoutorandroid.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.LinearLayout;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.IndicatorPageAdapter;
import br.com.srdoutorandroid.components.customadapter.ViewPagerAdapter;

public class PrimeiraVezActivity extends FragmentActivity  {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    LinearLayout linearLayoutPaginator ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_primeira_vez);
        linearLayoutPaginator = (LinearLayout) findViewById(R.id.pagination) ;
        setPagesTable();
        updateIndicator(1);
    }

    private void setPagesTable() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), PrimeiraVezActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {


            // optional
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateIndicator(position);
            }

            // optional
            @Override
            public void onPageSelected(int position) {
            }

            // optional
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void updateIndicator(int currentPage) {
        linearLayoutPaginator.removeAllViews();
        IndicatorPageAdapter.createDotScrollBar(this, linearLayoutPaginator,
                currentPage, 3);
    }

}
