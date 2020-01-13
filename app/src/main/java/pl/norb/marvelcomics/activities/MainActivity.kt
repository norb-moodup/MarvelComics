package pl.norb.marvelcomics.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var layoutManager: LinearLayoutManager
    lateinit var marvelAdapter: MarvelAdapter

    private var isLoading = false
    private var isAdapterInitialized = false
    private var title = ""
    private var marvelComicsList: ArrayList<MarvelResultsModel> = ArrayList()

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
                    isAdapterInitialized = false
                    marvelComicsList.clear()
                    title = ""
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                title = query
                getComics(title)
                hideKeyboard()
                offset = 0
                isAdapterInitialized = false
                marvelComicsList.clear()
                return false
            }
        }
        )
    }

    private fun getComics() {
        progressView.visibility = View.VISIBLE
        isLoading = true
        marvelViewModel.getComics(offset)
            .subscribe(
                { marvel ->
                    hideSpinner()
                    isLoading = false
                    initRecyclerView(marvel.data.results)
                },
                { e ->
                    Log.e(TAG, e.printStackTrace().toString())
                    hideSpinner()
                    isLoading = false
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
                    isLoading = false
                    initRecyclerView(marvel.data.results)
                },
                { e ->
                    Log.e(TAG, e.printStackTrace().toString())
                    hideSpinner()
                    isLoading = false
                }
            )
    }

    private fun hideSpinner() {
        progressView.visibility = View.GONE
        noComicsView.visibility = View.VISIBLE

    }

    private fun initRecyclerView(comicsList: ArrayList<MarvelResultsModel>) {
        marvelComicsList.addAll(comicsList)
        layoutManager = LinearLayoutManager(this)
        if (comicsList.isEmpty()) {
            noComicsView.visibility = View.VISIBLE
            comicsRecyclerView.visibility = View.GONE
        } else {
            noComicsView.visibility = View.GONE
            comicsRecyclerView.visibility = View.VISIBLE
            comicsRecyclerView.layoutManager = layoutManager
            if (!isAdapterInitialized) {
                marvelAdapter = MarvelAdapter(marvelComicsList)
                comicsRecyclerView.adapter = marvelAdapter
                isAdapterInitialized = true
            } else {
                marvelAdapter.notifyDataSetChanged()
            }

            comicsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = marvelAdapter.itemCount

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            offset++
                            if (title.isEmpty()) {
                                getComics()
                            } else {
                                getComics(title)
                            }
                        }

                    }
                }
            })
        }
    }

}
