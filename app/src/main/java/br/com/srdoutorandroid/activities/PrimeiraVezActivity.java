package br.com.srdoutorandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.IndicatorPageAdapter;
import br.com.srdoutorandroid.components.customadapter.ViewPagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PrimeiraVezActivity extends FragmentActivity  {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Bind(R.id.imageViewTela1)
    ImageView imageViewTela1;

    @Bind(R.id.imageViewTela2)
    ImageView imageViewTela2;

    @Bind(R.id.imageViewTela3)
    ImageView imageViewTela3;

    @Bind(R.id.buttonPular)
    Button buttonPular;

    @Bind(R.id.buttonComecar)
    Button buttonComecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_vez);
        ButterKnife.bind(this);
        imageViewTela1.setBackgroundResource(R.drawable.rectangle_full);
        imageViewTela2.setBackgroundResource(R.drawable.rectangle_empty);
        imageViewTela3.setBackgroundResource(R.drawable.rectangle_empty);
        buttonComecar.setVisibility(View.INVISIBLE);
        setPagesTable();
    }

    private void setPagesTable() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), PrimeiraVezActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

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
        IndicatorPageAdapter.verificarPaginator(imageViewTela1,imageViewTela2,imageViewTela3,buttonComecar,currentPage, 3);
    }

    public void pular(View view) {
        direcionarTelaSplash();
    }

    public void direcionarTelaSplash(){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
