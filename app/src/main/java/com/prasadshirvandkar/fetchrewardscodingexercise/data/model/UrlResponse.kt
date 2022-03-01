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

fun List<UrlResponse>.convertToMap(): Map<Int, List<String?>> {
    return this.filter { !it.name.isNullOrEmpty() }.groupBy { it.listId }
        .mapValues { entry -> entry.value.map { it.name } }.toSortedMap()
}