package br.com.srdoutorandroid.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import java.util.ArrayList;
import java.util.List;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.service.MedicoService;
import br.com.srdoutorandroid.service.bd.BancoController;
import br.com.srdoutorandroid.util.ConexaoUtil;
import br.com.srdoutorandroid.util.ContaGoogleUtil;
import br.com.srdoutorandroid.util.ToastUtil;
import br.com.srdoutorandroid.webservice.endpoint.ExecutorMetodoService;
import retrofit.RetrofitError;


/**
 * Created by elton on 24/09/2016.
 */
public class SplashActivity extends Activity  {
    private BancoController bdController ;
    private ImageView splashImageView;
    private ContaGoogleUtil contaGoogleUtil;
    private List<Medico> medicos ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bdController = new BancoController(getBaseContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        primeiraVezApp();
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Medico.class);
        ActiveAndroid.initialize(config.create());
        carregarListaMedicos();
    }

    private void primeiraVezApp() {
        if(!bdController.getPrimeiroAcesso()) {
            bdController.inserePrimeiroAcesso(true);
            direcionarTelaPrimeiraVez();
        }else splashImageView.setBackgroundResource(R.drawable.splash01);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return false;
    }

    public void direcionarTelaPrimeiraVez(){
        Intent intent = new Intent(this, PrimeiraVezActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void carregarListaMedicos() {
        medicos = new ArrayList<Medico>();
        if (ConexaoUtil.isConexao(this)) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        medicos = ExecutorMetodoService.invoke(new MedicoService(SplashActivity.this), "buscarMedicos");
                    } catch (RetrofitError error) {
                        //application.createEventAnalytics(getString(R.string.analytics_categoria_erro), BUSCAR_CHAMADO_WEB_SERVICE, null);
                        ToastUtil.showErro(SplashActivity.this, error.getResponse());
                    } catch (Exception error) {
                        ToastUtil.show(SplashActivity.this, error.getMessage(), ToastUtil.WARNING);
                        error.printStackTrace();
                    }
                    return null;
                }
            }.execute();

        }
        splashImageView.setBackgroundResource(R.drawable.splash02);
    }

}