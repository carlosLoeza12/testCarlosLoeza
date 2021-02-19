package com.example.testcarlosloeza.interactors;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.testcarlosloeza.R;
import com.example.testcarlosloeza.entieties.listMovieEntity;
import com.example.testcarlosloeza.interfaces.listMovieInterface;
import com.example.testcarlosloeza.presenters.listMoviePresenter;
import com.example.testcarlosloeza.singleton.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class listMovieInteractor implements listMovieInterface.interactor {
    private listMoviePresenter moviePresenter;
    private Context context;
    private ArrayList<listMovieEntity> listMovie;


    public listMovieInteractor(listMoviePresenter moviePresenter, Context context) {
        this.moviePresenter = moviePresenter;
        this.context = context;
    }

    @Override
    public void getListMovieInteractor(listMovieInterface.listenerListMovie listenerListMovie) {

        String urlListMovie = "https://api.themoviedb.org/3/movie/now_playing?api_key="+context.getString(R.string.apiKeyMovie)+"&language=es-MX&page=1";
        listMovie = new ArrayList<>();

        StringRequest stringrequest = new StringRequest(Request.Method.POST, urlListMovie,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //intentando obtener el arreglo json
                        listMovieEntity movieEntity = null;
                        Log.e("responseMovie", response);
                        try {
                            //convertimos el string response en objeto json
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.optJSONArray("results");
                            //recorremos el tamaño del arreglo
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //creamos un objeto de la clase
                                movieEntity = new listMovieEntity();
                                JSONObject c = jsonArray.getJSONObject(i);
                                movieEntity.setId(c.optString("id"));
                                movieEntity.setTitle(c.optString("title"));
                                movieEntity.setDateRelease(c.optString("release_date"));
                                movieEntity.setVote(c.optString("vote_average"));
                                movieEntity.setDescription(c.optString("overview"));
                                movieEntity.setPoster_path(c.optString("poster_path"));
                                listMovie.add(movieEntity);
                            }
                            listenerListMovie.loadDataListener(listMovie);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listenerListMovie.menssageListener(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listenerListMovie.menssageListener(context.getString(R.string.error_connection));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //poner los datos que se van a enviar a travez de los parametros
                //params.put("api_key", "a28c4bc831b590dc669ef8a459fdbff7");
                //params.put("language", "es-MX");
                //params.put("page", "1");
                return params;
            }
        };
        volleySingleton.getInstanciaVolley(context).addToRequestQueue(stringrequest);
    }
}
