package pl.norb.marvelcomics.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import pl.norb.marvelcomics.R
import pl.norb.marvelcomics.adapters.MarvelAdapter
import pl.norb.marvelcomics.backend.ApiClient
import pl.norb.marvelcomics.models.MarvelModel
import pl.norb.marvelcomics.viewmodels.MarvelViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onActivityReady()
    }

    private fun onActivityReady() {
        runOnUiThread {
            getComics()
            initSearch()
        }
    }

    private fun initSearch() {
        searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("")) {
                    hideKeyboard()
                    getComics()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                getComics(query)
                hideKeyboard()
                return false
            }
        }
        )
    }

    private fun getComics() {
        progressView.visibility = View.VISIBLE
        val ts = 1
        val apikey = "3d3ce5daa8ec0f7c17afc52bb68f15f7"
        val hash = "a45bdb0bf57b06e72ad4c2c5854e2843"
        val limit = 25
        val offset = 0
        val orderBy = "-onsaleDate"
        val single: Single<MarvelModel> =
            ApiClient.getClient.getComics(ts, apikey, hash, limit, offset, orderBy)
        single.subscribe(
            {
                val comicsList = it.data.results.map {
                    return@map MarvelViewModel(this, it)
                }
                hideSpinner()
                initRecyclerView(comicsList)
            }, {
                it.printStackTrace()
                hideSpinner()
            }
        )
    }

    fun hideKeyboard() {
        val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

    private fun getComics(comicTitle: String) {

        progressView.visibility = View.VISIBLE
        val ts = 1
        val apikey = "3d3ce5daa8ec0f7c17afc52bb68f15f7"
        val hash = "a45bdb0bf57b06e72ad4c2c5854e2843"
        val limit = 25
        val offset = 0
        val title = comicTitle
        val orderBy = "-onsaleDate"
        val single: Single<MarvelModel> =
            ApiClient.getClient.getComics(ts, apikey, hash, limit, offset, title, orderBy)
        single.subscribe(
            {
                val comicsList = it.data.results.map {
                    return@map MarvelViewModel(this, it)
                }
                hideSpinner()
                initRecyclerView(comicsList)
            }, {
                it.printStackTrace()
                hideSpinner()
            }
        )
    }

    private fun hideSpinner() {
        runOnUiThread {
            progressView.visibility = View.GONE
            noComicsView.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView(comicsList: List<MarvelViewModel>) {
        runOnUiThread {
            if (comicsList.isEmpty()) {
                noComicsView.visibility = View.VISIBLE
                comicsRecyclerView.visibility = View.GONE
            } else {
                noComicsView.visibility = View.GONE
                comicsRecyclerView.visibility = View.VISIBLE
                val marvelAdapter = MarvelAdapter(comicsList)
                comicsRecyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = marvelAdapter
                }
            }
        }
    }
}
