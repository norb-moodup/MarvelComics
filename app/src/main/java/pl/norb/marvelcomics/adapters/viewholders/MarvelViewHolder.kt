package pl.norb.marvelcomics.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_marvel_comics.view.*

class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.comicTitle
    val description = view.comicDescription
    val picture = view.comicPic
}