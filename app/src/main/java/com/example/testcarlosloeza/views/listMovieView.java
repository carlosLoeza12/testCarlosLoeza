package com.example.testcarlosloeza.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.testcarlosloeza.R;
import com.example.testcarlosloeza.adapters.listMovieAdapter;
import com.example.testcarlosloeza.entieties.listMovieEntity;
import com.example.testcarlosloeza.interfaces.listMovieInterface;
import com.example.testcarlosloeza.presenters.listMoviePresenter;

import java.util.ArrayList;

public class listMovieView extends AppCompatActivity implements listMovieInterface.View {

    private listMoviePresenter moviePresenter;
    private RecyclerView recyclerView;
    private listMovieAdapter listMovieAdapterView;
    private LayoutAnimationController animationController;
    private String idMovie,titleMovie, dateReleaseMovie,voteMovie,descriptionMovie, posterPathMovie;
    private Intent intent;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);

        initializeElements();
    }

    public void initializeElements()
    {
        listMovieAdapterView = new listMovieAdapter(this);

        recyclerView = findViewById(R.id.recyclerListMovie);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(listMovieAdapterView);
        recyclerView.setHasFixedSize(true);

        animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_top);

        moviePresenter = new listMoviePresenter(this, getApplicationContext());
        moviePresenter.getListMoviePresenter();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshMovie);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listMovieAdapterView.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void menssageView(String menssage) {
        Toast.makeText( getApplicationContext(), menssage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadDataView(ArrayList<listMovieEntity> listMovie) {
        recyclerView.setLayoutAnimation(animationController);
        listMovieAdapterView.sendList(listMovie);
        //click items
        listMovieAdapterView.listenerClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getId();
                titleMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getTitle();
                dateReleaseMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getDateRelease();
                voteMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getVote();
                descriptionMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getDescription();
                posterPathMovie = listMovie.get(recyclerView.getChildAdapterPosition(v)).getPoster_path();
                //obtener los datos
                intent = new Intent(getApplicationContext(), detailMovieView.class);
                intent.putExtra("idMovie", idMovie);
                intent.putExtra("titleMovie", titleMovie);
                intent.putExtra("voteMovie", voteMovie);
                intent.putExtra("dateReleaseMovie", dateReleaseMovie);
                intent.putExtra("descriptionMovie", descriptionMovie);
                intent.putExtra("posterPathMovie", posterPathMovie);
                startActivity(intent);
            }
        });
    }
}