package pl.norb.marvelcomics.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.norb.marvelcomics.R
import pl.norb.marvelcomics.adapters.MarvelAdapter
import pl.norb.marvelcomics.models.MarvelResultsModel
import pl.norb.marvelcomics.viewmodels.MarvelViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityTag"

    private val marvelViewModel: MarvelViewModel by viewModel()
    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onActivityReady()
    }

    private fun onActivityReady() {
        getComics()
        initSearch()
    }

    private fun initSearch() {
        searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("")) {
                    hideKeyboard()
                    offset = 0
                    getComics()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                getComics(query)
                hideKeyboard()
                offset = 0
                return false
            }
        }
        )
    }

    private fun getComics() {
        progressView.visibility = View.VISIBLE
        marvelViewModel.getComics(offset)
            .subscribe(
                { marvel ->
                    hideSpinner()
                    initRecyclerView(marvel.data.results)
                },
                { e ->
                    Log.e(TAG, e.printStackTrace().toString())
                    hideSpinner()
                }
            )
    }

    fun hideKeyboard() {
        val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }

    private fun getComics(comicTitle: String) {
        progressView.visibility = View.VISIBLE
        marvelViewModel.getComicsWithTitle(offset, comicTitle)
            .subscribe(
                { marvel ->
                    hideSpinner()
                    initRecyclerView(marvel.data.results)
                },
                { e ->
                    Log.e(TAG, e.printStackTrace().toString())
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

    private fun initRecyclerView(comicsList: ArrayList<MarvelResultsModel>) {
        runOnUiThread {
            offset++
            if (comicsList.isEmpty()) {
                noComicsView.visibility = View.VISIBLE
                comicsRecyclerView.visibility = View.GONE
            } else {
                noComicsView.visibility = View.GONE
                comicsRecyclerView.visibility = View.VISIBLE
                val marvelAdapter = MarvelAdapter(comicsList)
                marvelAdapter.notifyDataSetChanged()
                comicsRecyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = marvelAdapter
                }
            }
        }
    }

}
