package edu.msudenver.cs3013.MovieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter // Constructor
    (private val movieList: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder?>() {
    // added to enable swipe delete
    val swipeToDeleteCallback = SwipeToDeleteCallback()

    // ViewHolder class - embedded class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your view elements here
        var titleTextView: TextView
        var genreTextView: TextView
        var yearTextView: TextView
        var ratingTextView: TextView

        init {
            titleTextView = itemView.findViewById<TextView?>(R.id.titleTextView)
            genreTextView = itemView.findViewById<TextView?>(R.id.genreTextView)
            yearTextView = itemView.findViewById<TextView?>(R.id.yearTextView)
            ratingTextView = itemView.findViewById<TextView?>(R.id.ratingTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    // binds view holder to one particular movieList item
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.titleTextView.text = movie.title //check this
        holder.genreTextView.text = movie.genre
        holder.yearTextView.text = movie.year
        holder.ratingTextView.text = movie.rating
        /*
        val book = bookList.get(position)
        holder.titleTextView.setText(book.title)
        holder.authorTextView.setText(book.author)
        holder.pubYearTextView.setText(book.publicationYear)
         */
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun removeItem(position: Int) {
        movieList.removeAt(position)
        // use parent class RecyclerView.ViewHolder function to notify of change
        notifyItemRemoved(position)
    }

    // implement to achieve swipe-delete
    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) =
            if (viewHolder is MovieViewHolder) {
                makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_IDLE,
                    ItemTouchHelper.RIGHT
                ) or makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_SWIPE,
                    ItemTouchHelper.RIGHT
                )
            } else {
                0
            }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }
    }
}