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

class MainActivity : AppCompatActivity() {
    //val movieList: MutableList<Movie?> = ArrayList<Movie?>()
    //val movieAdapter = MovieAdapter(movieList as MutableList<Movie>)
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
            val data = activityResult.data
            val title = data?.getStringExtra("title")?:""
            val genre = data?.getStringExtra("genre") ?: ""
            val year = data?.getStringExtra("year") ?: ""
            val rating = data?.getStringExtra("rating") ?: ""
            //movieList.add(Movie(title,genre,year,rating))
            Log.d("RECYCLE", "MAIN ===== TITLE $title ================")
            //movieAdapter.notifyDataSetChanged()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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