package br.com.srdoutorandroid.webservice.endpoint;

/**
 * Created by elton on 02/02/2017.
 */


import android.content.Context;

import com.google.gson.reflect.TypeToken;

import br.com.srdoutorandroid.model.Token;
import br.com.srdoutorandroid.model.UsuarioSession;
import br.com.srdoutorandroid.util.JsonUtil;
import br.com.srdoutorandroid.util.UsuarioSharedUtil;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class LoginService extends ValidadorCallBack {

    public static LoginEndPoint service;

    private static final String SCOPE = "read";
    private static final String CLIENT_ID = "smemobile";
    private static final String CLIENT_SECRET = "lamperouge";
    private static final String GRANT_TYPE = "password";

    public LoginService(Context context){
        super(context);        service = restAdapter.create(LoginEndPoint.class);    }

    public void login(String login, String senha){
        Response response = service.login(login, senha, SCOPE, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE);
        JsonUtil jsonUtil = new JsonUtil<Token>();
        Token token = (Token)jsonUtil.converteObject(response, new TypeToken<Token>(){}.getType());
        if(response.getStatus() == Status.SUCESSO.getStatus()) {
            UsuarioSharedUtil.setUsuarioShared(new UsuarioSession(token.getUser().getId(), login, senha, token.getUser().getNome(), token.getAccess_token(),token.getIdUsuarioGlpi(), token.getUser().getCpf(), token.getRoles()), ctx);
        }else{
            throw new RuntimeException();
        }
    }

    public void reLogin(){
        UsuarioSession usuario = UsuarioSharedUtil.getUsuarioShared(ctx);
        login(usuario.getLogin(), usuario.getSenha());
    }
    public Response cadastroUsuario(String email, String password, String cpf){
        return service.cadastrarUsuario(email, password, cpf);
    }

    public String solicitarAlteracaoSenha(String novaSenha, String cpf) {
        Response resp = service.solicitarAlteracaoSenha(novaSenha, cpf);
        String email =  new String(((TypedByteArray)resp.getBody()).getBytes());
        return email;
    }
    public Response alterarDadosdoUsuario(String email, String novaSenha){
        String token = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_TOKEN);
        String id = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_ID);
        String cpf = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_CPF);
        if("".equals(novaSenha)){
            novaSenha = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_PASSWORD);
        }
        Response resp = service.atualizarUsuario("Bearer " + token, id, email, cpf, novaSenha);
        String response =   new String(((TypedByteArray)resp.getBody()).getBytes());
        UsuarioSharedUtil.set(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_TOKEN, response.replace("\"", ""));
        return resp;
    }
    public Response logout(){
        String token = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtil.getElementoSalvo(ctx, UsuarioSharedUtil.Preferences.PREFERENCES_LOGIN);
        return service.logout("Bearer " + token, login);
    }


}
