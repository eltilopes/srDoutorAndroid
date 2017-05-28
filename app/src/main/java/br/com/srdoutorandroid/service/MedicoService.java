package br.com.srdoutorandroid.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.srdoutorandroid.model.Medico;
import br.com.srdoutorandroid.webservice.endpoint.MedicoEndPoint;
import br.com.srdoutorandroid.webservice.endpoint.ValidadorCallBack;
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
       // List<Medico> medicos;
       // Response response = service.listarMedicos( );
       // JsonUtil<List<Medico>> json = new JsonUtil();
       // medicos = json.converteObject(response, new TypeToken<List<Medico>>() {}.getType());
        List<Medico> medicos = new ArrayList<Medico>();
        Medico medico1 = new Medico(1, "Aline Alef", "email@email.com", "CRM315648", "01234567890", "http://www.unimedjf.coop.br/novosite/Imagens/Site/imagemDestaqueDownloadGuiaMedico.png","Ginecologista");
        Medico medico2 = new Medico(2, "Renan Silva", "email@email.com", "CRM315648", "01234567890", "http://www.medical-pe.com.br/wp-content/uploads/2013/09/img-interna-esps-como-sera-medico-futuro.jpg","Urologista");
        medicos.add(medico1);
        medicos.add(medico2);
        return medicos;
    }
}
