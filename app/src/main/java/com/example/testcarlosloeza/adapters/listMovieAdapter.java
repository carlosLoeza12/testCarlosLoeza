package com.example.testcarlosloeza.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.testcarlosloeza.R;
import com.example.testcarlosloeza.entieties.listMovieEntity;
import com.example.testcarlosloeza.singleton.volleySingleton;

import java.util.ArrayList;

public class listMovieAdapter extends RecyclerView.Adapter<listMovieAdapter.viewHolderListMovie>
        implements View.OnClickListener{

    private Context context;
    private ArrayList<listMovieEntity> listMovie;
    private View.OnClickListener listener;
    private String urlImageAdapter;

    public listMovieAdapter(Context context) {
        this.context = context;
    }

    public void sendList(ArrayList<listMovieEntity> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public listMovieAdapter.viewHolderListMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflar el recycler view
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_movie, parent, false);
        vista.setOnClickListener(this);
        return new viewHolderListMovie(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull listMovieAdapter.viewHolderListMovie holder, int position) {
        holder.txtTitleAdapter.setText(listMovie.get(position).getTitle().toString());
        holder.txtDateReleaseAdapter.setText(listMovie.get(position).getDateRelease().toString());
        holder.txtVoteAdapter.setText(listMovie.get(position).getVote().toString());
        if (listMovie.get(position).getPoster_path() != null) {
            urlImageAdapter= context.getString(R.string.baseConfiguration)+listMovie.get(position).getPoster_path();
            loadImageService(urlImageAdapter, holder);
        } else {
            //si no se pone la imagen por defecto
            holder.imgMovieAdapter.setImageResource(R.drawable.img_not_available);
        }
    }

    @Override
    public int getItemCount() {
        if (listMovie != null)
            return listMovie.size();
        else
            return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener!= null)
        {
            listener.onClick(v);
        }
    }
    public void listenerClick(View.OnClickListener listener)
    {
        //metodo del listener que le esta llegando
        this.listener=listener;
    }

    public class viewHolderListMovie extends RecyclerView.ViewHolder {
        TextView txtTitleAdapter, txtDateReleaseAdapter, txtVoteAdapter;
        ImageView imgMovieAdapter;

        public viewHolderListMovie(@NonNull View itemView) {
            super(itemView);
            txtTitleAdapter = itemView.findViewById(R.id.txtTitleMovie);
            txtDateReleaseAdapter = itemView.findViewById(R.id.txtDateReleaseMovie);
            txtVoteAdapter = itemView.findViewById(R.id.txtVoteMovie);
            imgMovieAdapter = itemView.findViewById(R.id.imgPhotoMovie);
        }
    }

    private void loadImageService(String urlImage, final  listMovieAdapter.viewHolderListMovie holder) {

        ImageRequest imageRequest=new ImageRequest(urlImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //llamar a la clase holder
                holder.imgMovieAdapter.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        volleySingleton.getInstanciaVolley(context).addToRequestQueue(imageRequest);
    }
}
