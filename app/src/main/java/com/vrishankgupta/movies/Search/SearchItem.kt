package com.vrishankgupta.movies.Search

import java.io.Serializable

/**
 * Created by vrishankgupta on 10/01/18.
 */

class SearchItem : Serializable {
    var vote_average: String? = null
    var original_title: String? = null
    var poster_path: String? = null
    var backdrop_path: String? = null
    var original_language: String? = null
    var overview: String? = null
    var id: String? = null
    var release_date: String? = null
    var type: String? = null
}
