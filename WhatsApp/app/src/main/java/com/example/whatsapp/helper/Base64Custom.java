package com.example.whatsapp.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decoficarBase64(String textoCondificado){
        return new String(Base64.decode(textoCondificado, Base64.DEFAULT));
    }

}
