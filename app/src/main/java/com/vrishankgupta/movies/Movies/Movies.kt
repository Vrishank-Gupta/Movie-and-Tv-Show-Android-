package com.vrishankgupta.movies.Movies

import java.io.Serializable

/**
 * Created by vrishankgupta on 08/01/18.
 */

class Movies : Serializable {
    var vote_average: String? = null
    var original_title: String? = null
    var overview: String? = null
    var release_date: String? = null
    var id: String? = null
    var backdrop_path: String? = null
    var language: String? = null

    var poster_path: String? = null
    var popularity: String? = null
}