package edu.msudenver.cs3013.MovieList

class Movie(val title: String?, val genre: String?, val year: String?,val rating: String?) {
    override fun toString(): String {
        return "TITLE = " + title + " Genre = " + genre
    }
}