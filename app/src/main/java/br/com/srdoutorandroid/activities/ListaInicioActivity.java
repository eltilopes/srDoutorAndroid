package br.com.srdoutorandroid.activities;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.LazyAdapter;
import br.com.srdoutorandroid.model.Medico;


/**
     * Created by elton on 20/09/2016.
     */
    public class ListaInicioActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {
        static final String URL = "http://api.androidhive.info/music/music.xml";
        // XML node keys
        static final String KEY_SONG = "song"; // parent node
        static final String KEY_ID = "id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_ARTIST = "artist";
        public static final String KEY_DURATION = "duration";
        public static final String KEY_THUMB_URL = "thumb_url";

        private ListView listAtendimentos;
        private LazyAdapter adapterImage;
        private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        private View mFabButton;
        private View mHeader;
        private SimpleDateFormat dateFormatter;
        private EditText editTextData;
        private DatePickerDialog datePickerDialogConsulta;
        private Spinner spinnerEspecialidade;
        private Toolbar mToolbar;
        private FragmentDrawer drawerFragment;
        private List<Medico> medicos ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                medicos = (List<Medico>) getIntent().getSerializableExtra("medicos"); //Obtaining data
            }
            setContentView(R.layout.activity_lista_inicio);
            setDateTimeField();
            setListenerOnSpinnerItemSelection();
            setFragmentDrawer();
            setListAtendimentos();
            setImageAppBar();
        }

    private void setImageAppBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_branca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }
    private void setListAtendimentos() {
            getListaAtendimentos();
            mostrarListaAtendimento();
        }

        private void mostrarListaAtendimento() {
            listAtendimentos=(ListView)findViewById(R.id.list);
            adapterImage=new LazyAdapter(this, songsList);
            listAtendimentos.setAdapter(adapterImage);
            listAtendimentos.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + " selecionado!", Toast.LENGTH_SHORT).show();

                }
            });

        }

    private void getListaAtendimentos() {
        for (Medico medico : medicos) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(KEY_ID, medico.getIdMedico().toString());
            map.put(KEY_TITLE, medico.getNome());
            map.put(KEY_ARTIST, medico.getCrm());
            map.put(KEY_DURATION, medico.getCpf());
            map.put(KEY_THUMB_URL, medico.getUrlFoto());
            songsList.add(map);
        }
    }

        private void setFragmentDrawer() {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            setSuportActionBar();
            drawerFragment = (FragmentDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);
        }

        private void setListenerOnSpinnerItemSelection() {
            spinnerEspecialidade = (Spinner) findViewById(R.id.spinnerEspecialidade);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.especialidadeArrays, R.layout.spinner_item); //
            spinnerEspecialidade.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                String firstItem = String.valueOf(spinnerEspecialidade.getSelectedItem());
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (firstItem.equals(String.valueOf(spinnerEspecialidade.getSelectedItem()))) {
                    } else {
                        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + " selecionado!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        }


        private void setDateTimeField() {
            dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialogConsulta = new DatePickerDialog(this,R.style.Base_V21_Theme_AppCompat_Light_Dialog, new OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }





        @Override
        protected void onResume() {
            super.onResume();
            if(getSupportActionBar() != null){
                setSuportActionBar();
            }
        }

        private void setSuportActionBar() {
            CharSequence c = "SrDoutor";
            getSupportActionBar().setTitle(c);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

        }

        @Override
        public void onDrawerItemSelected(View view, int position) {

        }
    }
