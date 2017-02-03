package br.com.srdoutorandroid.model;

/**
 * Created by elton on 02/02/2017.
 */

import java.util.List;

public class Token {

    private String access_token;
    private String idUsuarioGlpi;
    private UsuarioSession user;
    private List<Role> roles;

    public String getIdUsuarioGlpi() {
        return idUsuarioGlpi;
    }
    public void setIdUsuarioGlpi(String idUsuarioGlpi) {
        this.idUsuarioGlpi = idUsuarioGlpi;
    }

    public String getAccess_token() { return access_token; }
    public void setAccess_token(String access_token) { this.access_token = access_token; }

    public UsuarioSession getUser() { return user; }
    public void setUser(UsuarioSession user) { this.user = user; }

    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }

}
