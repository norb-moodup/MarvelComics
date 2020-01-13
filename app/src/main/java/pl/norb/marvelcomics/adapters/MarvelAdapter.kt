package pl.norb.marvelcomics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pl.norb.marvelcomics.R
import pl.norb.marvelcomics.adapters.viewholders.MarvelViewHolder
import pl.norb.marvelcomics.models.MarvelResultsModel

class MarvelAdapter(
    private val comicsList: ArrayList<MarvelResultsModel>
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
        holder.title.text = comicsList[position].comicTitle
        holder.description.text = comicsList[position].comicDescription
        Glide
            .with(holder.picture.context)
            .load(comicsList[position].comicThumbnailUrl.path + "." + comicsList[position].comicThumbnailUrl.extension)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.no_image_placeholder)
            )
            .into(holder.picture)
    }
}