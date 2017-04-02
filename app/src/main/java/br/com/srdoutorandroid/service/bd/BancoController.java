package br.com.srdoutorandroid.service.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by elton on 29/03/2017.
 */

public class BancoController {

    private SQLiteDatabase db;
    private CriarBanco banco;

    public BancoController(Context context){
        banco = new CriarBanco(context);
    }

    public String inserePrimeiroAcesso(Boolean acessou) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriarBanco.FL_PRIMEIRO_ACESSO, acessou);

        resultado = db.insert(CriarBanco.TB_PRIMEIRO_ACESSO, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        return "Registro Inserido com sucesso";
    }

    public boolean getPrimeiroAcesso(){
        Cursor cursor;
        String[] campos =  {banco.CI_PRIMEIRO_ACESSO,banco.FL_PRIMEIRO_ACESSO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TB_PRIMEIRO_ACESSO, campos, null, null, null, null, null, null);

        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return false;
            }
            cursor.close();
        }
        db.close();
        return true;
    }
}