package br.com.srdoutorandroid.activities;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.customadapter.LazyAdapter;
import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.util.ToastUtil;


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
        private LinearLayout buttonFiltro;
        private LinearLayout buttonEspecialidade;
        private LinearLayout buttonCalendario;
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
            setButtonFiltro();
            setButtonEspecialidade();
            setButtonCalendario();
            setDateTimeField();
            setFragmentDrawer();
            setListAtendimentos();
            setImageAppBar();
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
                        ToastUtil.show(ListaInicioActivity.this, "Gineco", ToastUtil.INFORMATION);
                        break;
                    case 1:
                        ToastUtil.show(ListaInicioActivity.this, "Fisio", ToastUtil.INFORMATION);
                        break;
                    case 2:
                        ToastUtil.show(ListaInicioActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 3:
                        ToastUtil.show(ListaInicioActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 4:
                        ToastUtil.show(ListaInicioActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                    case 5:
                        ToastUtil.show(ListaInicioActivity.this, "Terapeuta", ToastUtil.INFORMATION);
                        break;
                }
            }

        });

        b.show();

    }

    private void setImageAppBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_branca);
    }

    public void mostrarCalendario() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_calendario);
        dialog.setTitle(R.string.segunda);
        dialog.show();
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
