package com.olvera.moviedb.ui.movieRated

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.olvera.moviedb.api.remote.Api
import com.olvera.moviedb.model.Movie
import com.olvera.moviedb.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieRatedViewModel(application: Application) : AndroidViewModel(application) {

    private val api = Api()
    private val disposable = CompositeDisposable()

    val movieRated = MutableLiveData<List<Movie>>()
    val loadingMovies = MutableLiveData<Boolean>()

    fun getMovieRated() {
        disposable.add(
                api.getTopRatedMovies()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                        override fun onSuccess(t: MovieResponse) {
                            movieRated.value = t.results
                            loadingMovies.value = false
                            Log.i("POPULAR VIEW MODEL", "WORKING")
                        }

                        override fun onError(e: Throwable) {
                            Log.i("POPULAR VIEW MODEL", "NOT WORKING ${e.message}")
                        }

                    })
            )
        }

}