package com.example.testcarlosloeza.interactors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.testcarlosloeza.R;
import com.example.testcarlosloeza.entieties.listMovieEntity;
import com.example.testcarlosloeza.interfaces.detailMovieInterface;
import com.example.testcarlosloeza.presenters.detailMoviePresenter;
import com.example.testcarlosloeza.singleton.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class detailMovieInteractor implements detailMovieInterface.interactor {
    private detailMoviePresenter Presenter;
    private Context context;
    private String urlPhotoMovie;
    private String urlDetailMovie;
    private ArrayList<listMovieEntity> listDetailMovie;
    private  int durationMovie;


    public detailMovieInteractor(detailMoviePresenter presenter, Context context) {
        Presenter = presenter;
        this.context = context;
    }


    @Override
    public void getphotoMovieInteractor(String postherPath, detailMovieInterface.listenerDetailMovie listenerListMovie) {
        urlPhotoMovie = context.getString(R.string.baseConfiguration) + postherPath;
        ImageRequest imageRequest = new ImageRequest(urlPhotoMovie, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                listenerListMovie.loadPhotoListener(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listenerListMovie.menssageListener("Error al cargar imagen");
            }
        });
        volleySingleton.getInstanciaVolley(context).addToRequestQueue(imageRequest);
    }

    @Override
    public void getDetailMovieInteractor(String idMovie, detailMovieInterface.listenerDetailMovie listenerDetailMovie) {
        urlDetailMovie = context.getString(R.string.api_detail_movie) + idMovie + "?api_key="
                + context.getString(R.string.apiKeyMovie) + "&language=es-MX";

        listDetailMovie = new ArrayList<>();

        StringRequest stringrequest = new StringRequest(Request.Method.GET, urlDetailMovie,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //intentando obtener el arreglo json
                        listMovieEntity movieEntity = null;
                        Log.e("responseMovie", response);
                        try {
                            //convertimos el string response en objeto json
                            JSONObject object = new JSONObject(response);
                            //obtener el tiempo de duracion
                            durationMovie = (int) object.get("runtime");
                            getDurationMobieInteractor(durationMovie, listenerDetailMovie);
                            JSONArray jsonArrayGenre = object.optJSONArray("genres");
                            //recorremos el tamaño del arreglo
                            for (int i = 0; i < jsonArrayGenre.length(); i++) {
                                //creamos un objeto de la clase
                                movieEntity = new listMovieEntity();
                                JSONObject c = jsonArrayGenre.getJSONObject(i);
                                movieEntity.setNameGenre(c.optString("name"));
                                listDetailMovie.add(movieEntity);
                            }

                            getNameGenreInteractor(listDetailMovie, listenerDetailMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listenerDetailMovie.menssageListener(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listenerDetailMovie.menssageListener(context.getString(R.string.error_connection));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //poner los datos que se van a enviar a travez de los parametros
                return params;
            }
        };
        volleySingleton.getInstanciaVolley(context).addToRequestQueue(stringrequest);
    }

    @Override
    public void getNameGenreInteractor(ArrayList<listMovieEntity> listDetailMovie, detailMovieInterface.listenerDetailMovie listenerDetailMovie) {
        String nameGenre = "";
        for (int i = 0; i < listDetailMovie.size(); i++) {
            nameGenre = nameGenre + " " + listDetailMovie.get(i).getNameGenre();
        }
        //Log.e("nameGenre", nameGenre);
        if (!nameGenre.equals("")) {
            listenerDetailMovie.loadNameGenreListener(nameGenre);
        }
    }

    @Override
    public void getDurationMobieInteractor(int duration, detailMovieInterface.listenerDetailMovie listenerDetailMovie) {
        listenerDetailMovie.loadDurationListener(String.valueOf(duration));
    }
}
