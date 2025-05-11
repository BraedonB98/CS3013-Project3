package edu.msudenver.cs3013.MovieList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    val fileDirectory = ""
    val movieList: MutableList<Movie?> = ArrayList<Movie?>()
    val movieAdapter = MovieAdapter(movieList as MutableList<Movie>)
    var myPlace: String? = null
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
            val data = activityResult.data
            val title = data?.getStringExtra("title")?:""
            val genre = data?.getStringExtra("genre") ?: ""
            val year = data?.getStringExtra("year") ?: ""
            val rating = data?.getStringExtra("rating") ?: ""
            movieList.add(Movie(title,genre,year,rating))
            Log.d("RECYCLE", "MAIN ===== TITLE $title ================")
            movieAdapter.notifyDataSetChanged()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        //Basic Setup Stuff
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //File Stuff
        //  FIND DIRECTORY TO WRITE LIST FILE
        Log.d("PERSIST1", "CREATED - about TO GET FILE DIR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        val myDir = this.getFilesDir()
        val myDirName = myDir.getAbsolutePath()
        myPlace = myDirName
        Log.d("PERSIST1", "My Dir Path = " + myDirName + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

        readFile()
        movieList.add(Movie("Title1", "SPOOKY!", "1919", "1"))
        movieList.add(Movie("Title2", "Comedy", "1929", "2"))
        movieList.add(Movie("Title3", "Romance", "1939", "3"))
        movieList.add(Movie("Title4", "Thriller", "1949", "4"))
        movieList.add(Movie("Title5", "Action", "1959", "5"))


        // three recyclerview lines below are main steps to
        val recyclerView = findViewById<RecyclerView?>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // add lines below to allow swipe delete
        val itemTouchHelper = ItemTouchHelper(movieAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.setAdapter(movieAdapter)


    }
    fun startSecond(view : View){
        Log.d("MainActivity", "Add Movie button clicked")
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }
    fun saveList(view: View) {
        Log.d("MainActivity", "Save List button clicked")
        // Add logic to save the movie list
    }
}