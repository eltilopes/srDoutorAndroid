package br.com.srdoutorandroid.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import br.com.srdoutorandroid.R;
import br.com.srdoutorandroid.model.FieldsErroRetrofit;
import br.com.srdoutorandroid.webservice.endpoint.ValidadorCallBack;
import retrofit.client.Response;

/**
 * Created by elton on 05/02/2017.
 */

public class ToastUtil {
    public static final int INFORMATION = 0;
    public static final int WARNING     = 1;
    public static final int ERROR       = 2;
    public static Toast toast;
    public static void show(final Activity context, String text, int toastType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.toast_layout, null);

        TextView tv = (TextView) layout.findViewById(R.id.text_toast);
        tv.setText(text);

        LinearLayout llRoot = (LinearLayout) layout.findViewById(R.id.llRoot);
        Drawable img;
        int bg;
        switch (toastType) {
            case WARNING:
                img = context.getResources().getDrawable(R.drawable.ic_warn_toast);
                bg = R.drawable.toast_background_yellow;
                break;
            case ERROR:
                img = context.getResources().getDrawable(R.drawable.ic_error_toast);
                bg = R.drawable.toast_background_red;
                break;
            default:
                img = context.getResources().getDrawable(R.drawable.ic_info_toast);
                bg = R.drawable.toast_background_blue;
                break;
        }

        tv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        llRoot.setBackgroundResource(bg);
        if (context != null){
            context.runOnUiThread(new Runnable() {
                public void run() {
                    if (toast != null)
                        toast.cancel();
                    toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            });
        }
    }

    public static void showErro(final Activity activity, final Response resp) {
        JsonUtil<FieldsErroRetrofit> json = new JsonUtil();
        final FieldsErroRetrofit erroField = json.converteObject(resp, new TypeToken<FieldsErroRetrofit>() {
        }.getType());
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (erroField != null && erroField.getErrors() != null && erroField.getErrors().size() > 0) {
                        StringBuilder erroString = new StringBuilder();
                        for (int i = 0; i < erroField.getErrors().size(); i++) {
                            erroString.append(erroField.getErrors().get(i));
                            if (i != erroField.getErrors().size() - 1)
                                erroString.append(" \n ");
                        }
                        ToastUtil.show(activity, erroString.toString(), ToastUtil.WARNING);
                    } else if (resp == null) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_conexao_servidor), ToastUtil.ERROR);
                    } else if (ValidadorCallBack.Status.contemStatus(resp.getStatus())) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_credenciais_invalidas), ToastUtil.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.ERROR_SERVIDOR.getStatus()) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_servidor), ToastUtil.ERROR);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.NAO_ENCONTRADO.getStatus()) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_nao_encontrado), ToastUtil.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.DADOS_DUPLICADOS.getStatus()) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_dados_duplicados), ToastUtil.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.DADOS_INVALIDOS.getStatus()) {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_dados_invalidos), ToastUtil.WARNING);
                    } else {
                        ToastUtil.show(activity, activity.getResources().getString(R.string.error_inesperado) + resp.getStatus(), ToastUtil.ERROR);
                    }
                }
            });
    }

}
