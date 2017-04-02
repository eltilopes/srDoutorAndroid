package br.com.srdoutorandroid.webservice.endpoint;


import retrofit.client.Response;
import retrofit.http.GET;
/**
 * Created by elton on 05/02/2017.
 */

public interface MedicoEndPoint {

    @GET("/medico/listar")
    Response listarMedicos();
}
