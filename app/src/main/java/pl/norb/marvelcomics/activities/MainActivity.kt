package pl.norb.marvelcomics.activities

import android.app.AlertDialog
import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.norb.marvelcomics.R
import pl.norb.marvelcomics.adapters.MarvelAdapter
import pl.norb.marvelcomics.models.AppState
import pl.norb.marvelcomics.models.MarvelResultsModel
import pl.norb.marvelcomics.viewmodels.MarvelViewModel

class MainActivity : BaseActivity() {
    private val TAG = "MainActivityTag"

    private val marvelViewModel: MarvelViewModel by viewModel()
    private var offset = 0
    lateinit var layoutManager: LinearLayoutManager
    lateinit var marvelAdapter: MarvelAdapter

    private var title = ""
    private var isAdapterInitialized = false
    private var marvelComicsList: ArrayList<MarvelResultsModel> = ArrayList()

    override fun onActivityReady() {
        initStateObserve()
        initSearch()
        marvelViewModel.onViewReady()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun showLoading(shouldShow: Boolean) {
        progressView.visibility = if (shouldShow) VISIBLE else GONE
    }

    override fun showError(message: String) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setNeutralButton("Ok") { dialog, which ->
            noComicsView.visibility = VISIBLE
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun initStateObserve() {
        marvelViewModel.appState.observe(this, Observer { appState ->
            when (appState) {
                is AppState.Initialized -> marvelViewModel.getComics(offset, null)
                is AppState.InProgress -> showLoading(true)
                is AppState.NoResults -> {
                    noComicsView.visibility = VISIBLE
                    comicsRecyclerView.visibility = GONE
                    showLoading(false)
                }
                is AppState.OnResults -> {
                    noComicsView.visibility = GONE
                    comicsRecyclerView.visibility = VISIBLE
                    initRecyclerView(appState.marvelList)
                    showLoading(false)
                }
                is AppState.Error -> {
                    showError(appState.errorMessage.toString())
                    comicsRecyclerView.visibility = GONE
                    showLoading(false)
                }
            }
        })
    }

    private fun initSearch() {
        searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("")) {
                    hideKeyboard()
                    offset = 0
                    marvelViewModel.getComics(offset, null)
                    marvelComicsList.clear()
                    title = ""
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                title = query
                offset = 0
                marvelViewModel.getComics(offset, title)
                hideKeyboard()
                marvelComicsList.clear()
                return false
            }
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

    private fun initRecyclerView(comicsList: ArrayList<MarvelResultsModel>) {marvelComicsList.addAll(comicsList)
        layoutManager = LinearLayoutManager(this)
            comicsRecyclerView.layoutManager = layoutManager
            if (!isAdapterInitialized) {
                marvelAdapter = MarvelAdapter(marvelComicsList)
                comicsRecyclerView.adapter = marvelAdapter
                isAdapterInitialized = true
            } else {
                marvelAdapter.notifyDataSetChanged()
            }
    }

}
