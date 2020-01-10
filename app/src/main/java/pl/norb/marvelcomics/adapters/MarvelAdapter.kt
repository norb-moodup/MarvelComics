package pl.norb.marvelcomics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pl.norb.marvelcomics.R
import pl.norb.marvelcomics.adapters.viewholders.MarvelViewHolder
import pl.norb.marvelcomics.viewmodels.MarvelViewModel

class MarvelAdapter(
    private val comicsList: List<MarvelViewModel>
) : RecyclerView.Adapter<MarvelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        return MarvelViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_marvel_comics, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.title.text = comicsList[position].title
        holder.description.text = comicsList[position].description
        Glide
            .with(holder.picture.context)
            .load(comicsList[position].pictureUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.no_image_placeholder)
            )
            .into(holder.picture)
    }
}