package idv.chauyan.itunessearch.presentation.screen.artworklist.view.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import idv.chauyan.itunessearch.R
import idv.chauyan.itunessearch.presentation.model.PresentationArtWork
import idv.chauyan.itunessearch.presentation.screen.artworklist.ArtWorkListContract
import kotlinx.android.synthetic.main.fragment_art_work_item.view.*
import kotlinx.android.synthetic.main.fragment_art_work_loadmore.view.*

class ArtWorkListAdapter(
  private var artWorks: ArrayList<PresentationArtWork>,
  private val listener: ArtWorkListContract.View.ArtWorkListBehavior?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  enum class ItemType {
    TYPE_DISPLAY_ARTWORK,
    TYPE_LOADING
  }

  private val itemOnClickListener: View.OnClickListener

  init {
    itemOnClickListener = View.OnClickListener { v ->
      val item = v.tag as PresentationArtWork
      listener?.onSelectedArtWork(item)
    }
  }


  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder = when (viewType) {
    ItemType.TYPE_DISPLAY_ARTWORK.ordinal -> {
      val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_art_work_item, parent, false)
      ArtWorkItem(view)
    }
    else -> {
      val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_art_work_loadmore, parent, false)
      ArtWorkLoading(view)
    }
  }

  override fun getItemCount(): Int = artWorks.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is ArtWorkItem) {
      val item = artWorks[position]
      Picasso.get()
        .load(item.artWorkThumbnailSmall)
        .into(holder.artWorkLogo)
      holder.artistName.text = item.artistName
      item.trackName?.let {
        holder.trackName.text = item.collectionName.plus(" - ").plus(item.trackName)
      } ?: kotlin.run {
        holder.trackName.text = item.collectionName
      }


      with(holder.view) {
        tag = item
        setOnClickListener(itemOnClickListener)
      }
    } else if (holder is ArtWorkLoading) {
      holder.artWorkLoading.isIndeterminate = true
    }
  }

  fun updateArtworks(
    data: List<PresentationArtWork>,
    refreshing: Boolean
  ) {
    if (refreshing) artWorks.clear()
    artWorks.addAll(data)
    notifyDataSetChanged()
  }

  fun showLoading() {
    artWorks.add(
      PresentationArtWork()
    )
    notifyItemInserted(artWorks.size - 1)
  }

  fun dismissLoading() {
    artWorks.removeAt(artWorks.lastIndex)
    notifyItemRemoved(artWorks.size)
  }

  /**
   * Art work item
   */
  inner class ArtWorkItem(val view: View) : RecyclerView.ViewHolder(view) {

    val artWorkLogo: ImageView = view.artWorkImage
    val artistName: TextView = view.artistName
    val trackName: TextView = view.trackName
  }

  inner class ArtWorkLoading(val view: View) : RecyclerView.ViewHolder(view) {
    val artWorkLoading: ProgressBar = view.artWorkLoading
  }
}