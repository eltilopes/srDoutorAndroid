package br.com.srdoutorandroid.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.GoogleCardsAdapter;
import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.util.DummyContent;
import br.com.srdoutorandroid.util.ToastUtil;

/**
 * Created by elton on 11/05/2017.
 */

public class MedicoCardsActivity extends ActionBarActivity implements OnDismissCallback,FragmentDrawer.FragmentDrawerListener {

    private static final int INITIAL_DELAY_MILLIS = 300;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private GoogleCardsAdapter mGoogleCardsAdapter;
    private Activity medicoCardsActivity ;
    private LinearLayout buttonFiltro;
    private LinearLayout buttonEspecialidade;
    private LinearLayout buttonCalendario;
    private List<Medico> medicos ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        setButtonFiltro();
        setButtonEspecialidade();
        setButtonCalendario();
        setListaMedicos();
        setSuportActionBar();
        setFragmentDrawer();
        setLogoAppBar();
    }

    private void setListaMedicos() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            medicos = (List<Medico>) getIntent().getSerializableExtra("medicos"); //Obtaining data
        }
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MedicoCardsActivity.this));
        ListView listView = (ListView) findViewById(R.id.list_view);
        medicoCardsActivity = this;
        mGoogleCardsAdapter = new GoogleCardsAdapter(this,
                DummyContent.getDummyModelList());
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(mGoogleCardsAdapter, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(swingBottomInAnimationAdapter);
    }

    private void setButtonCalendario() {
        View v = findViewById(R.id.button_calendario);
        buttonCalendario = (LinearLayout) v;
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });
    }

    private void setButtonFiltro() {
        View v = findViewById(R.id.button_filtro);
        buttonFiltro = (LinearLayout) v;
        buttonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });
    }

    private void setButtonEspecialidade() {
        View v = findViewById(R.id.button_especialidade);
        buttonEspecialidade = (LinearLayout) v;
        buttonEspecialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarEspecialidades();
            }
        });
    }

    private void mostrarEspecialidades() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Especialidades");
        String[] types = {"Gineco", "Fisio", "Terapeuta", "Terapeuta", "Terapeuta", "Terapeuta"};
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        ToastUtil.show(MedicoCardsActivity.this, "Gineco", ToastUtil.INFORMATION);
                        break;
                    case 1:
                        ToastUtil.show(MedicoCardsActivity.this, "Fisio", ToastUtil.INFORMATION);
                        break;
                    case 2:
                        ToastUtil.show(MedicoCardsActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 3:
                        ToastUtil.show(MedicoCardsActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 4:
                        ToastUtil.show(MedicoCardsActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 5:
                        ToastUtil.show(MedicoCardsActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                }
            }

        });

        b.show();

    }
    private void setLogoAppBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.logo);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    public void mostrarCalendario() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_calendario);
        dialog.setTitle(R.string.segunda);
        dialog.show();
    }

    private void setFragmentDrawer() {
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }
    private void setSuportActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(@NonNull final ViewGroup listView,
                          @NonNull final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }
}