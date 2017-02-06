package br.com.srdoutorandroid.model;

/**
 * Created by elton on 05/02/2017.
 */

public class FieldError {
    private String message;

    private String field;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getField ()
    {
        return field;
    }

    public void setField (String field)
    {
        this.field = field;
    }

    @Override
    public String toString()
    {
        String erros = "";
        if(field != null){
            erros = field.toUpperCase() + ": " + message + " ";
        }else{
            erros = message + " ";
        }

        return erros;
    }
}
