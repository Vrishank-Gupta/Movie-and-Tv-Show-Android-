package com.vrishankgupta.movies.TvShow

import java.io.Serializable

/**
 * Created by vrishankgupta on 08/01/18.
 */
class Tv : Serializable {
    var original_name: String? = null
    var vote_average: String? = null
    var id: String? = null
    var first_air_date: String? = null
    var overview: String? = null
    var poster_path: String? = null
    var backdrop_path: String? = null
    var language: String? = null
    var seasonCount: String? = null
    var episodeCount: String? = null
}
