package com.example.testcarlosloeza.singleton;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class volleySingleton {

    private static volleySingleton instanceVolley;
    private RequestQueue request;
    private static Context contextVolley;

    private volleySingleton(Context context) {
        //constructor para tener el contexto y la peticion de la aplicacion
        contextVolley = context;
        request = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        //metodo para tener la peticion
        if (request == null) {
            // si el request en null lo instaciamos por primera vez
            request = Volley.newRequestQueue(contextVolley.getApplicationContext());
        }
        return request;
    }


    public static synchronized volleySingleton getInstanciaVolley(Context context) {
        //metodo para instanciar  si es nula la crea y si esta creada solo se retorna
        if (instanceVolley == null) {
            instanceVolley = new volleySingleton(context);
        }
        return instanceVolley;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        //recibe el objeto json o string request
        getRequestQueue().add(request);
    }
}
