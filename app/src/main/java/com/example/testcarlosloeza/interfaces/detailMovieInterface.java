package com.example.testcarlosloeza.interfaces;

import android.graphics.Bitmap;

import com.example.testcarlosloeza.entieties.listMovieEntity;

import java.util.ArrayList;

public interface detailMovieInterface {

    interface View {
        void menssageView(String menssage);
        void loadPhotoView(Bitmap bitmap);
        void loadNameGenreView(String nameGenre);
        void loadDurationView(String duration);
    }

    interface Presenter {
        void getphotoMoviePresenter(String poster_path);
        void getDetailMoviePresenter(String idMovie);
    }

    interface interactor {
        void getphotoMovieInteractor(String postherPath, listenerDetailMovie listenerListMovie);
        void getDetailMovieInteractor(String idMovie, listenerDetailMovie listenerDetailMovie);
        void getNameGenreInteractor(ArrayList<listMovieEntity> listDetailMovie, listenerDetailMovie listenerDetailMovie);
        void getDurationMobieInteractor(int duration, listenerDetailMovie listenerDetailMovie);
    }

    interface listenerDetailMovie {
        void menssageListener(String menssage);
        void loadPhotoListener(Bitmap bitmap);
        void loadNameGenreListener(String nameGenre);
        void loadDurationListener(String duration);
    }
}
