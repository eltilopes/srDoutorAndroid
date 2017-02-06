package br.com.srdoutorandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import br.com.srdoutorandroid.activities.CustomizedListView;
import br.com.srdoutorandroid.components.ProgressDialogAsyncTask;
import br.com.srdoutorandroid.exception.AcessoNegadoException;
import br.com.srdoutorandroid.util.ConexaoUtil;
import br.com.srdoutorandroid.util.ToastUtil;
import br.com.srdoutorandroid.util.ValidaUtil;
import br.com.srdoutorandroid.webservice.endpoint.ExecutorMetodoService;
import br.com.srdoutorandroid.webservice.endpoint.LoginService;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListaMedicoActivity extends AppCompatActivity implements ProgressDialogAsyncTask.IProgressActivity{

    @Bind(R.id.email_login)
    EditText login;
    @Bind(R.id.senha_login)
    EditText senha;
    @Bind(R.id.dialog_progress)
    RelativeLayout layoutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medico);
        ButterKnife.bind(this);
    }


    public void logar(View view) {
        ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
        if (!ConexaoUtil.isConexao(getApplicationContext())) {
            ToastUtil.show(this, getResources().getString(R.string.error_conexao_internet), ToastUtil.ERROR);
        } else if (ValidaUtil.validaCampoEditText(this.login) && ValidaUtil.validaCampoEditText(senha)) {
            task.execute();
        }
    }

    public void redirect(){
        Intent intent = new Intent(this, CustomizedListView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void executaProgressoDialog() {
        try {
            ExecutorMetodoService.invoke(new LoginService(this), "login", login.getText().toString(), senha.getText().toString());
            //registrarIdGcm();
            redirect();
        } catch (RetrofitError error) {
            final Response resp = error.getResponse();
            ToastUtil.showErro(this, resp);
        } catch (AcessoNegadoException erro) {
            ToastUtil.show(ListaMedicoActivity.this, getResources().getString(R.string.error_acesso_negado), ToastUtil.WARNING);
        } catch (RuntimeException erro) {
            ToastUtil.show(ListaMedicoActivity.this, getResources().getString(R.string.error_dados_invalidos), ToastUtil.WARNING);
        }
    }

    @Override
    public boolean isAddedValidation() {
        return true;
    }

    @Override
    public void onPostExecute() {

    }
}
