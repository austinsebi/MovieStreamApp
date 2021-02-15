package com.example.movieuitemplate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieuitemplate.models.Movie;
import com.example.movieuitemplate.adapters.MovieAdapter;
import com.example.movieuitemplate.adapters.MovieItemClickListener;
import com.example.movieuitemplate.R;
import com.example.movieuitemplate.models.Slide;
import com.example.movieuitemplate.adapters.SliderPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private List<Slide> lstSlides ;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderpager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);



        //prepare a list of slides ...
        lstSlides = new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.wolverine, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.avengers, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.wolverine, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.avengers, "Slide Title \nmore text here"));
        SliderPagerAdapter adapter = new SliderPagerAdapter(this,lstSlides);
        sliderpager.setAdapter(adapter);



        // setup time

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(), 4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

        // Recyclerview Setup
        // ini data
        List<Movie> lstMovies = new ArrayList<>();
        lstMovies.add(new Movie("Moana", R.drawable.moana, R.drawable.avengerscover));
        lstMovies.add(new Movie("Black Panther", R.drawable.blackpanther, R.drawable.avengerscover));
        lstMovies.add(new Movie("The Godfather", R.drawable.godfather));
        lstMovies.add(new Movie("Lion King", R.drawable.lionking));
        lstMovies.add(new Movie("The Avengers", R.drawable.avengers));
        lstMovies.add(new Movie("Wolverine", R.drawable.wolverine));


        MovieAdapter movieAdapter = new MovieAdapter(this, lstMovies, this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL, false));









    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        //here we send movie information to detail activity
        //also we'll create the transition animation between the two activities
        Intent intent = new Intent(this, MovieDetailActivity.class);

        //send movie information to detailActivity
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgURL", movie.getThumbnail());
        intent.putExtra("imgCover", movie.getCoverPhoto());


        //here we are gonna create the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, movieImageView, "sharedName");

        Toast.makeText(this, "item clicked : " + movie.getTitle(), Toast.LENGTH_LONG).show();
        startActivity(intent, options.toBundle());



    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });
        }
    }
}