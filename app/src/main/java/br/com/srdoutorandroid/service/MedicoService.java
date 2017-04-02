package br.com.srdoutorandroid.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.util.JsonUtil;
import br.com.srdoutorandroid.webservice.endpoint.MedicoEndPoint;
import br.com.srdoutorandroid.webservice.endpoint.ValidadorCallBack;
import retrofit.client.Response;
/**
 * Created by elton on 01/04/2017.
 */

public class MedicoService extends ValidadorCallBack {

    public static MedicoEndPoint service;

    public MedicoService(Context context){
        super(context);
        service = restAdapter.create(MedicoEndPoint.class);
    }

    public List<Medico> buscarMedicos() {
        List<Medico> medicos;
        Response response = service.listarMedicos( );
        JsonUtil<List<Medico>> json = new JsonUtil();
        medicos = json.converteObject(response, new TypeToken<List<Medico>>() {
        }.getType());
        return medicos;
    }
}
