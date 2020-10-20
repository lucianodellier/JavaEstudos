package com.example.organnizee.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r","");
    }

    public static String decoficiarBase64(String textoCoficiado){
        return new String(Base64.decode(textoCoficiado, Base64.DEFAULT));
    }

}
