package br.com.srdoutorandroid.activities;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.components.XMLParser;
import br.com.srdoutorandroid.components.customadapter.LazyAdapter;


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

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lista_inicio);
            setDateTimeField();
            setListenerOnSpinnerItemSelection();
            setFragmentDrawer();
            setListAtendimentos();
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
        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
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
