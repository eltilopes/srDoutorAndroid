package br.com.srdoutorandroid.components.customadapter;

import br.com.srdoutorandroid.model.FragmentoFactory;

/**
 * Created by raphael.bruno on 24/07/2015.
 */
public class CustomAdapterItem {

    private String text1;
    private String text2;
    private Integer icon1;
    private boolean permissao;
    private FragmentoFactory.NavegacaoEnum url;

    public CustomAdapterItem(Integer icon1, String text1) {
        this.text1 = text1;
        this.icon1 = icon1;
    }
    public CustomAdapterItem(Integer icon1, String text1, String text2) {
        this.text1 = text1;
        this.icon1 = icon1;
        this.text2 = text2;
    }

    public CustomAdapterItem(Integer icon1, String text1, int color1) {
        this.text1 = text1;
        this.icon1 = icon1;

    }
    public CustomAdapterItem(Integer icon1, FragmentoFactory.NavegacaoEnum value, String text2, boolean permissao) {
        this.text1 = value.getLabel();
        this.text2 = text2;
        this.icon1 = icon1;
        this.permissao = permissao;
        this.url = value;
    }


    public CustomAdapterItem(Integer icon1, String text1, String text2, int color1) {
        this.text1 = text1;
        this.text2 = text2;
        this.icon1 = icon1;
     //   this.color1 = color1;
    }

    public String getText1() { return text1; }
    public void setText1(String text1) { this.text1 = text1; }

    public String getText2() {
        return text2;
    }
    public void setText2(String text2) { this.text2 = text2; }

    public Integer getIcon() {
        return icon1;
    }
    public void setIcon(Integer icon1) { this.icon1 = icon1; }

    public boolean isPermissao() {
        return permissao;
    }

    public void setPermissao(boolean permissao) {
        this.permissao = permissao;
    }

    public FragmentoFactory.NavegacaoEnum getUrl() {
        return url;
    }

    public void setUrl(FragmentoFactory.NavegacaoEnum url) {
        this.url = url;
    }
}
