package br.com.srdoutorandroid.util;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.com.srdoutorandroid.R;

/**
 * Created by elton on 05/02/2017.
 */

public class ValidaUtil {
    public static boolean validaCampoEditText(EditText ed){
        String campo = ed.getText().toString();
        if(campo != null && campo.trim().length() > 0){
            return true;
        }
        ed.setError(ed.getResources().getString(R.string.campo_obrigatorio));
        return false;
    }

    public static boolean validaCampoEditText(EditText edText, TextInputLayout ed){
        String campo = edText.getText().toString();
        if(campo != null && campo.trim().length() > 0){
            ed.setError(null);
            return true;
        }else{
            ed.setError(ed.getResources().getString(R.string.campo_obrigatorio));
        }

        return false;
    }

    public static boolean validaCampoEditText(EditText... edit){
        boolean toReturn = true;
        if(edit != null){
            for(EditText ed : edit){
                String campo = ed.getText().toString();
                int qtd = campo.trim().length();
                if(campo != null && qtd == 0){
                    toReturn = false;
                    ed.setError(ed.getResources().getString(R.string.campo_obrigatorio));
                }
            }
        }

        return toReturn;
    }
}
