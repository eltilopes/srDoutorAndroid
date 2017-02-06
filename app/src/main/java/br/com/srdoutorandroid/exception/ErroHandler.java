package br.com.srdoutorandroid.exception;

import android.content.Context;
import android.content.Intent;

import br.com.srdoutorandroid.ListaMedicoActivity;
import br.com.srdoutorandroid.webservice.endpoint.ValidadorCallBack;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by elton on 05/02/2017.
 */

public class ErroHandler implements retrofit.ErrorHandler {

    private final Context ctx;

    public ErroHandler(Context ctx) {
        this.ctx = ctx;
    }

    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == ValidadorCallBack.Status.TOKEN_INVALIDO.getStatus() && ctx != null && !cause.getUrl().contains("oauth/token")) {
            throw new TokenException();
        }else if(r != null && r.getStatus() == ValidadorCallBack.Status.ACESSO_NEGADO.getStatus()){
            Intent intent = new Intent(ctx, ListaMedicoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            // throw new AcessoNegadoException(r);
        }
        return cause;
    }
}
