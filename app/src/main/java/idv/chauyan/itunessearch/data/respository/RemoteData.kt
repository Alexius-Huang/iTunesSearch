package idv.chauyan.itunessearch.data.respository

import idv.chauyan.itunessearch.data.model.DataArtWork
import idv.chauyan.itunessearch.remote.RemoteDataImpl
import idv.chauyan.itunessearch.remote.api.SearchAPI

interface RemoteData : DataSource {
  suspend fun getArtWorks(keyword: String): List<DataArtWork>

  companion object {
    fun createRemoteDataSource(debug: Boolean): RemoteData {
      return RemoteDataImpl(SearchAPI.create(debug))
    }
  }
}