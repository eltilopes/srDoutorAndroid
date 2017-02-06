package br.com.srdoutorandroid.exception;

import retrofit.client.Response;

/**
 * Created by elton on 05/02/2017.
 */

public class AcessoNegadoException extends RuntimeException {
    private Response response;

    public AcessoNegadoException(Response response){
        this.response = response;
    }
    public AcessoNegadoException(){}

    public Response getResponse() {
        return response;
    }
}
