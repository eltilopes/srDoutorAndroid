package br.com.srdoutorandroid.model;

import java.util.List;

/**
 * Created by elton on 05/02/2017.
 */

public class FieldsErroRetrofit {
    private List<FieldError> errors;

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
}
