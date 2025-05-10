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
        //!Need to be filled out, currently just filler to not crash
        //val intent = Intent(this, SecondActivity::class.java)
        //startActivity(intent)
    }
    fun saveList(view: View) {
        Log.d("MainActivity", "Save List button clicked")
        // Add logic to save the movie list
    }
}