package com.example.jogging.models

data class Jogging(val id: String, val title: String, val description: String, val image: String, val category: String, val positions: List<MapPosition> ) {
    companion object {
        private val joggingList: MutableList<Jogging> = mutableListOf(
            Jogging("1", "Trasa 1", "Opis trasy", "image_url", "short", listOf()),
            Jogging("2", "Trasa 2", "Opis trasy", "image_url", "normal", listOf()),
            Jogging("3", "Trasa 3", "Opis trasy", "image_url", "long", listOf()),
            Jogging("4", "Trasa 4", "Opis trasy", "image_url", "short", listOf()),
            Jogging("5", "Trasa 5", "Opis trasy", "image_url", "short", listOf()),
            Jogging("6", "Trasa 6", "Opis trasy", "image_url", "normal", listOf()),
            Jogging("7", "Trasa 7", "Opis trasy", "image_url", "normal", listOf()),
            Jogging("8", "Trasa 8", "Opis trasy", "image_url", "long", listOf()),
            Jogging("9", "Trasa 9", "Opis trasy", "image_url", "long", listOf()),
            Jogging("10", "Trasa 10", "Opis trasy", "image_url", "normal", listOf())
        )
        fun getJoggingList() = joggingList
        fun setJoggingList(data: MutableList<Jogging>) {
            joggingList.apply {
                clear()
                addAll(data)
            }
        }
    }
}
