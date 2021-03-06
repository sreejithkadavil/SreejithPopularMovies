package android.example.popularmoviespart1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.net.URL;
import java.util.List;


import android.example.popularmoviespart1.MovieImageAdapter;
import android.example.popularmoviespart1.Movie;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements MovieImageAdapter.MovieImageAdapterOnClickHandler {
    private RecyclerView m_RV_movieListView;
    private ProgressDialog m_PD_loadingIndicator;
    private MovieImageAdapter m_movieAdapter;

    //OnCreate - Initialize the layout, recyclerview and adapter and invoke the loadMovies method.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_RV_movieListView = (RecyclerView) findViewById(R.id.rv_movies);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        m_RV_movieListView.setLayoutManager(gridLayoutManager);
        m_RV_movieListView.setHasFixedSize(true);


        m_movieAdapter = new MovieImageAdapter(this, this);
        m_RV_movieListView.setAdapter(m_movieAdapter);

        loadMovies("popular");


    }

    //Inflate the sort menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);

        return true;
    }
    //handle the menu click and invoke loadMovies depending on option selected.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_by_popular) {
            loadMovies("popular");
        } else {
            loadMovies("top_rated");
        }

        return super.onOptionsItemSelected(item);
    }

    //loadMovies method - execute the asynchronous task by passing the sort by parameter

    private void loadMovies(String sortBy) {
        new MovieFetcherTask().execute(sortBy);
    }

    //Handle onClick on the movie icons - Create an Intent and start the DetailActivity

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        Gson gson = new Gson();

        String movieAsJson = gson.toJson(movie);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieAsJson);
        startActivity(intentToStartDetailActivity);

    }

    private class MovieFetcherTask extends AsyncTask<String, Void, List<Movie>> {
        private final String TAG = MovieFetcherTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            m_PD_loadingIndicator = ProgressDialog.show(MainActivity.this, "Please Wait...", "Fetching movies...", true);
        }

        //AsyncTask to Fetch the movies
        @Override
        protected List<Movie> doInBackground(String... params) {

            String queryPath = null;
            if (params.length == 0) {
                queryPath = "popular";
            }
            queryPath = params[0];

            URL movieRequestUrl = NetworkUtils.buildUrl(queryPath);
            try {

                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                Log.d(TAG, "Response Back: " + jsonMovieResponse);

                List<Movie> simpleJsonMovieData = MovieJsonUtils.getSimpleMoviesFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(List<Movie> movies) {
            m_PD_loadingIndicator.dismiss();
            if(!movies.isEmpty()) {
                m_movieAdapter.setMovieData(movies);
            }
            super.onPostExecute(movies);
        }
    }

    //Start the MainActivity
    public void OnClickBackToList(View view) {
        Context context = this;
        Class destinationClass = MainActivity.class;
        Intent intentToStartMainActivity = new Intent(context, destinationClass);
        startActivity(intentToStartMainActivity);
    }




}
