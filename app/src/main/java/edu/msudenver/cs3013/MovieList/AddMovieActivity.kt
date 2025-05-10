package edu.msudenver.cs3013.MovieList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun backToFirst(v : View) {
        //setMovieInfo(<Movie Title>, <Movie Genre>, <Year> , <Rating> )
        Log.d("RECYCLE", "AddBookAct backToFirst ===== ENTERED  ================")
        val movieTitle = findViewById<EditText>(R.id.title)//set as val (ie. const)
        val movieGenre = findViewById<EditText>(R.id.genre)
        val movieYear = findViewById<EditText>(R.id.year)
        val movieRating = findViewById<EditText>(R.id.rating)
        // get the string versions of numbers
        val title = movieTitle.getText().toString()
        val genre = movieGenre.getText().toString()
        val year = movieYear.getText().toString()
        val rating = movieRating.getText().toString()
        Log.d("RECYCLE", "AddMovieAct backToFirst ===== TITLE $title ================")
        setMovieInfo(title, genre, year, rating)
    }
    private fun setMovieInfo(title: String, genre: String, year: String, rating: String){
        var movieInfoIntent = Intent()
        Log.d("RECYCLE", "AddMovieAct setMovieInfo ===== TITLE $title ================")
        movieInfoIntent.putExtra("title", title)
        movieInfoIntent.putExtra("genre", genre)
        movieInfoIntent.putExtra("year", year)
        movieInfoIntent.putExtra("rating", rating)
        setResult(Activity.RESULT_OK, movieInfoIntent)
        finish()
    }
}