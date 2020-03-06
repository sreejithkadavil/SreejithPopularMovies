package android.example.popularmoviespart1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.MovieAdapterViewHolder> {

    private final String TAG = MovieImageAdapter.class.getSimpleName();
    private final MovieImageAdapterOnClickHandler m_clickHandler;

    private Context m_context;
    private List<Movie> m_movieList;

    public MovieImageAdapter(Context context, MovieImageAdapterOnClickHandler clickHandler) {
        m_context = context;
        m_clickHandler = clickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movieItem = m_movieList.get(position);
        ImageView imageView = movieAdapterViewHolder.getMovieListImageItem();
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(movieItem.getPosterListImageUri()).into(imageView);


    }

    @Override
    public int getItemCount() {
        if(m_movieList == null || m_movieList.isEmpty()) return 0;
        return m_movieList.size();
    }

    public void setMovieData(List<Movie> movieData) {
        m_movieList = movieData;
        notifyDataSetChanged();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView m_IV_movieListTb;

        public ImageView getMovieListImageItem() {
            return m_IV_movieListTb;
        }

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            m_IV_movieListTb = (ImageView)itemView.findViewById(R.id.iv_movie_list_tb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPos = getAdapterPosition();
            Movie movie = m_movieList.get(adapterPos);
            m_clickHandler.onClick(movie);
        }
    }

    public interface MovieImageAdapterOnClickHandler {
        void onClick(Movie movie);
    }
}