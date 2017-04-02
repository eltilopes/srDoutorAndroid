package br.com.srdoutorandroid.service.bd;

/**
 * Created by elton on 29/03/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
public class CriarBanco extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "srdoutor.db";
    public static final String TB_PRIMEIRO_ACESSO = "tb_primeiro_acesso";
    public static final String CI_PRIMEIRO_ACESSO = "ci_primeiro_acesso";
    public static final String FL_PRIMEIRO_ACESSO = "fl_primeiro_acesso";
    private static final int VERSAO = 1;
    private Context context;

    public CriarBanco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
        this.context = context;
    }

/**
    public static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
**/

    public void deleteBD(){
        context.deleteDatabase(NOME_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TB_PRIMEIRO_ACESSO+"("
                + CI_PRIMEIRO_ACESSO + " integer primary key autoincrement,"
                + FL_PRIMEIRO_ACESSO + " boolean"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TB_PRIMEIRO_ACESSO);
        onCreate(db);
    }

}