package com.example.testcarlosloeza.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testcarlosloeza.R;
import com.example.testcarlosloeza.interfaces.detailMovieInterface;
import com.example.testcarlosloeza.presenters.detailMoviePresenter;
import com.example.testcarlosloeza.presenters.listMoviePresenter;

import java.util.Objects;

public class detailMovieView extends AppCompatActivity implements detailMovieInterface.View {

    private Bundle bundleDataView;
    private String idMovie,titleMovie, dateReleaseMovie,voteMovie,descriptionMovie, posterPathMovie;
    private TextView txtTitleView, txtDateReleaseView,txtVoteView,txtDescriptionView, txtNameGenreView, txtDurationView;
    private detailMoviePresenter presenter;
    private ImageView imgMovieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_view);
        initializeElements();
    }

    public  void initializeElements()
    {
        presenter = new detailMoviePresenter(this, getApplicationContext());
        //activar la flecha de regresar en el toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.deatil_movie);

        txtTitleView = findViewById(R.id.txtTitle);
        txtDateReleaseView = findViewById(R.id.txtDataDateRelease);
        txtVoteView = findViewById(R.id.txtDataVote);
        txtDescriptionView = findViewById(R.id.txtDataDescription);
        imgMovieView = findViewById(R.id.imgPhotoMovie);
        txtNameGenreView = findViewById(R.id.txtDataGenre);
        txtDurationView = findViewById(R.id.txtDataDuration);

        bundleDataView = this.getIntent().getExtras();
        if(bundleDataView !=null){
            idMovie = bundleDataView.getString("idMovie");
            titleMovie = bundleDataView.getString("titleMovie");
            voteMovie = bundleDataView.getString("voteMovie");
            dateReleaseMovie = bundleDataView.getString("dateReleaseMovie");
            descriptionMovie = bundleDataView.getString("descriptionMovie");
            posterPathMovie = bundleDataView.getString("posterPathMovie");

            txtTitleView.setText(titleMovie);
            txtDateReleaseView.setText(dateReleaseMovie);
            txtVoteView.setText(voteMovie);
            txtDescriptionView.setText(descriptionMovie);
            //si existen los datos se llaman a los metodos
            presenter.getphotoMoviePresenter(posterPathMovie);
            presenter.getDetailMoviePresenter(idMovie);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        //accion de regresar
        onBackPressed();
        return false;
    }


    @Override
    public void menssageView(String menssage) {
        Toast.makeText( getApplicationContext(), menssage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadPhotoView(Bitmap bitmap) {
        imgMovieView.setImageBitmap(bitmap);
    }

    @Override
    public void loadNameGenreView(String nameGenre) {
        txtNameGenreView.setText(nameGenre);
    }

    @Override
    public void loadDurationView(String duration) {
        txtDurationView.setText(duration+ " min");
    }
}