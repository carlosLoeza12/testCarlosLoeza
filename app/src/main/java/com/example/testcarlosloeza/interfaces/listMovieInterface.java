package com.example.testcarlosloeza.interfaces;

import com.example.testcarlosloeza.entieties.listMovieEntity;

import java.util.ArrayList;

public interface listMovieInterface {
    interface View
    {
        void menssageView(String menssage);
        void loadDataView(ArrayList<listMovieEntity> listMovie);
    }
    interface Presenter
    {
        void getListMoviePresenter();
    }
    interface interactor
    {
        void  getListMovieInteractor(listenerListMovie listenerListMovie);
    }

    interface listenerListMovie
    {
        void menssageListener(String menssage);
        void loadDataListener(ArrayList<listMovieEntity> listMovie);
    }
}
