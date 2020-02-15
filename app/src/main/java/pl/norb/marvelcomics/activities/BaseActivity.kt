package pl.norb.marvelcomics.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        onActivityReady()
    }

    protected abstract fun onActivityReady()

    protected abstract fun getLayout(): Int

    protected abstract fun showLoading(shouldShow: Boolean)

    protected abstract fun showError(message: String)
}