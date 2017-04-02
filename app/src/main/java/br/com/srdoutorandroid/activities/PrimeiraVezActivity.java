package br.com.srdoutorandroid.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.ViewPagerAdapter;

public class PrimeiraVezActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_primeira_vez);
        setPagesTable();
    }

    private void setPagesTable() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), PrimeiraVezActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
