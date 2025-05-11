package edu.msudenver.cs3013.MovieList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

        // three recyclerview lines below are main steps to
        val recyclerView = findViewById<RecyclerView?>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // add lines below to allow swipe delete
        val itemTouchHelper = ItemTouchHelper(movieAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.setAdapter(movieAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun startSecond(view : View){
        Log.d("MainActivity", "Add Movie button clicked")
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }
    fun saveList(view: View) {
        Log.d("MainActivity", "Save List button clicked")
        writeFile()
    }

    //  FUNCTION writeFile - writes the full movie list to file MOVIELIST.csv
    fun writeFile() {
        Log.d("PERSIST1", "writeFile() entered")
        try {
            val file = File(myPlace + "/MOVIELIST.csv")
            if (file.exists()) {
                Log.d("PERSIST1", "EXISTS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            } else {
                Log.d("PERSIST1", "DOES NOT EXIST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            }
            val fw = FileWriter(file, false)
            val count = movieList.size
            Log.d("PERSIST1", "Count >>>>> =  $count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            for (movie in movieList) {
                if (movie != null) {
                    val line = "${movie.title},${movie.year},${movie.genre},${movie.rating}"
                    Log.d("PERSIST1", "Writing: $line >>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                    fw.write(line + "\n")
                }
            }
            fw.flush()
            fw.close()
        } catch (iox: IOException) {
            Log.d("PERSIST1", "HIT EXCEP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            Log.d("PERSIST1", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> EXCEP $iox")
        }
    }

    // FUNCTION readFile - reads the file MOVIELIST.csv - populating the movie list
    fun readFile() {
        Log.d("PERSIST1", "readFile() entered")
        try {
            val file = File(myPlace + "/MOVIELIST.csv")

            //! I did this for grading purposes, I know I used a different package name.
            // So to save you from needed to add file manually I create one here if you
            //Dont add one using the values form Top20.csv
            if (!file.exists()) {
                Log.d("PERSIST1", "File does not exist. Creating default MOVIELIST.csv")
                val defaultMovies = listOf(
                    Movie("The Shawshank Redemption", "Drama", "1994", "9.3"),
                    Movie("The Godfather", "Crime", "1972", "9.2"),
                    Movie("The Godfather: Part II", "Crime", "1974", "9"),
                    Movie("The Dark Knight", "Action", "2008", "9"),
                    Movie("12 Angry Men", "Crime", "1957", "8.9"),
                    Movie("Schindler's List", "Biography", "1993", "8.9"),
                    Movie("Pulp Fiction", "Crime", "1994", "8.9"),
                    Movie("The Lord of the Rings: The Return of the King", "Action", "2003", "8.9"),
                    Movie("Forrest Gump", "Drama", "1994", "8.8"),
                    Movie("The Lord of the Rings: The Fellowship of the Ring", "Action", "2001", "8.8"),
                    Movie("Fight Club", "Drama", "1999", "8.8"),
                    Movie("Inception", "Sci-Fi", "2010", "8.8"),
                    Movie("Dag II", "Action", "2016", "8.8"),
                    Movie("Dil Bechara", "Comedy", "2020", "8.8"),
                    Movie("One Flew Over the Cuckoo's Nest", "Drama", "1975", "8.7"),
                    Movie("Star Wars: Episode V - The Empire Strikes Back", "Action", "1980", "8.7"),
                    Movie("Goodfellas", "Crime", "1990", "8.7"),
                    Movie("The Matrix", "Sci-Fi", "1999", "8.7"),
                    Movie("The Lord of the Rings: The Two Towers", "Action", "2002", "8.7"),
                    Movie("It's a Wonderful Life", "Drama", "1946", "8.6")
                )
                movieList.clear()
                movieList.addAll(defaultMovies)
                writeFile() //saving default
            }
            val myReader = Scanner(file)
            movieList.clear() // clear existing list
            while (myReader.hasNextLine()) {
                val data = myReader.nextLine()
                Log.d("PERSIST1", "LINE of input data: $data")
                val parts = data.split(",")
                //Title, Year, Genre,Rating -> Movie(Title, Genre, Year, Rating
                val movie = Movie(parts[0], parts[2], parts[1], parts[3])
                movieList.add(movie)

            }
            myReader.close()
            movieAdapter.notifyDataSetChanged()
        } catch (e: IOException) {
            Log.d("PERSIST1", "HIT EXCEP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> $e")
            e.printStackTrace()
        }
    }

}