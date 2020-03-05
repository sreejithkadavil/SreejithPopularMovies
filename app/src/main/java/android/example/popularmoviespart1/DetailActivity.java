package android.example.popularmoviespart1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.google.gson.*;



public class DetailActivity extends AppCompatActivity {


    private ImageView m_IV_poster;
    private TextView m_TV_title;
    private TextView m_TV_releaseDate;
    private TextView m_TV_voteAverage;
    private TextView m_TV_overview;
    private RatingBar m_RB_voterAverage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        m_IV_poster = (ImageView)findViewById(R.id.iv_movie_details_tb);
        m_TV_title = (TextView)findViewById(R.id.tv_title);
        m_TV_releaseDate = (TextView)findViewById(R.id.tv_date_released);
        m_TV_voteAverage = (TextView)findViewById(R.id.tv_vote_average);
        m_TV_overview = (TextView)findViewById(R.id.tv_overview);
        m_RB_voterAverage = (RatingBar)findViewById(R.id.rb_voter_average);


        Intent intentThatStartedMovieDetails = getIntent();
        if(intentThatStartedMovieDetails != null) {
            if(intentThatStartedMovieDetails.hasExtra(Intent.EXTRA_TEXT)) {
                String movieAsJson = intentThatStartedMovieDetails.getStringExtra(Intent.EXTRA_TEXT);
                Movie moviePassed = new Gson().fromJson(movieAsJson, Movie.class);

                Picasso.get().load(moviePassed.getPosterDetailsImageUri()).into(m_IV_poster);
                m_TV_title.setText(moviePassed.getTitle());
                m_TV_releaseDate.setText(moviePassed.getReleaseDateForDisplay());
                m_TV_voteAverage.setText(String.valueOf(moviePassed.getVoteAverage()));
                m_TV_overview.setText(moviePassed.getOverview());
                m_RB_voterAverage.setRating(moviePassed.getVoteAverage());
            }
        }
    }

    public void OnClickBackToList(View view) {
        Context context = this;
        Class destinationClass = MainActivity.class;
        Intent intentToStartMainActivity = new Intent(context, destinationClass);
        startActivity(intentToStartMainActivity);
    }
}