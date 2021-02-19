package com.example.testcarlosloeza.presenters;
import android.content.Context;

import com.example.testcarlosloeza.entieties.listMovieEntity;
import com.example.testcarlosloeza.interactors.listMovieInteractor;
import com.example.testcarlosloeza.interfaces.listMovieInterface;
import com.example.testcarlosloeza.views.listMovieView;

import java.util.ArrayList;

public class listMoviePresenter implements listMovieInterface.Presenter, listMovieInterface.listenerListMovie{

    private listMovieView movieView;
    private Context context;
    private listMovieInteractor movieInteractor;

    public listMoviePresenter(listMovieView movieView, Context context) {
        this.movieView = movieView;
        this.context = context;
        movieInteractor = new listMovieInteractor(this, context);
    }

    @Override
    public void getListMoviePresenter() {
        if(movieView!=null)
        {
            movieInteractor.getListMovieInteractor(this);
        }
    }

    @Override
    public void menssageListener(String menssage) {
        if(movieView!=null)
        {
            movieView.menssageView(menssage);
        }
    }

    @Override
    public void loadDataListener(ArrayList<listMovieEntity> listMovie) {
        if(movieView!=null)
        {
            movieView.loadDataView(listMovie);
        }
    }
}
