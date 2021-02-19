package com.example.testcarlosloeza.presenters;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.testcarlosloeza.interactors.detailMovieInteractor;
import com.example.testcarlosloeza.interactors.listMovieInteractor;
import com.example.testcarlosloeza.interfaces.detailMovieInterface;
import com.example.testcarlosloeza.views.detailMovieView;

public class detailMoviePresenter implements detailMovieInterface.Presenter, detailMovieInterface.listenerDetailMovie {

    private detailMovieView movieView;
    private Context context;
    private detailMovieInteractor interactor;

    public detailMoviePresenter(detailMovieView movieView, Context context) {
        this.movieView = movieView;
        this.context = context;
        interactor = new detailMovieInteractor(this, context);
    }

    @Override
    public void getphotoMoviePresenter(String poster_path) {
        if(movieView!=null)
        {
            interactor.getphotoMovieInteractor(poster_path, this);
        }
    }

    @Override
    public void getDetailMoviePresenter(String idMovie) {
        if(movieView!=null)
        {
            interactor.getDetailMovieInteractor(idMovie, this);
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
    public void loadPhotoListener(Bitmap bitmap) {
        if(movieView!=null)
        {
           movieView.loadPhotoView(bitmap);
        }
    }

    @Override
    public void loadNameGenreListener(String nameGenre) {
        if(movieView!=null)
        {
            movieView.loadNameGenreView(nameGenre);
        }
    }

    @Override
    public void loadDurationListener(String duration) {
        if(movieView!=null)
        {
            movieView.loadDurationView(duration);
        }
    }
}
