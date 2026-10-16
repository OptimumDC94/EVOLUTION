package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class DatamuseWord(
  @Json(name = "word") val word: String,
  @Json(name = "score") val score: Int,
  @Json(name = "numSyllables") val numSyllables: Int?,
  @Json(name = "tags") val tags: List<String>?
)

interface DatamuseApiService {
  @GET("words")
  suspend fun getRhymes(@Query("rel_rhy") word: String, @Query("max") max: Int = 50): List<DatamuseWord>

  @GET("words")
  suspend fun getNearRhymes(@Query("rel_nry") word: String, @Query("max") max: Int = 40): List<DatamuseWord>

  @GET("words")
  suspend fun getSynonyms(@Query("rel_syn") word: String, @Query("max") max: Int = 50): List<DatamuseWord>

  @GET("words")
  suspend fun getAntonyms(@Query("rel_ant") word: String, @Query("max") max: Int = 30): List<DatamuseWord>
}

object DatamuseClient {
  private val retrofit =
    Retrofit.Builder()
      .baseUrl("https://api.datamuse.com/")
      .addConverterFactory(MoshiConverterFactory.create())
      .build()

  val api: DatamuseApiService = retrofit.create(DatamuseApiService::class.java)
}
