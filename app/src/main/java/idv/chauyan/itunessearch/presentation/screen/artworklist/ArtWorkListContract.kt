package idv.chauyan.itunessearch.presentation.screen.artworklist

import idv.chauyan.itunessearch.presentation.BasePresenter
import idv.chauyan.itunessearch.presentation.BaseView
import idv.chauyan.itunessearch.presentation.model.PresentationArtWork

interface ArtWorkListContract {

  interface Model {
    suspend fun getAlbumsByKeyword(keyword: String): List<PresentationArtWork>
    fun cacheSearchResult(
      keyword: String,
      artWorks: List<PresentationArtWork>
    )

    fun getCachedResultByKey(keyword: String): List<PresentationArtWork>

    fun cleanCache()
  }

  interface Presenter : BasePresenter {
    fun getAlbumsByKeyword(keyword: String)
    fun cacheSearchResult(
      keyword: String,
      artWorks: List<PresentationArtWork>
    )

    fun getCachedResultByKey(keyword: String): List<PresentationArtWork>

    fun cleanCache()
  }

  interface View : BaseView<Presenter> {
    fun updateAlbumList(albums: List<PresentationArtWork>)

    interface ArtWorkListBehavior {
      fun onSelectedAlbum(artWork: PresentationArtWork)
    }
  }
}