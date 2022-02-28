package com.prasadshirvandkar.fetchrewardscodingexercise.data.model

data class UrlResponse(
    var id: Int? = 0,
    var listId: Int = 0,
    var name: String? = null
) {
    override fun toString(): String {
        return "UrlResponse(id=$id, listId=$listId, name=$name)"
    }
}