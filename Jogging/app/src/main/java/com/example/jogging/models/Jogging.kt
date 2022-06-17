package com.example.jogging.models

data class Jogging(val id: String, val title: String, val description: String, val image: String, val category: String, val positions: List<MapPosition> ) {
    companion object {
        private val joggingCity: MutableList<Jogging> = mutableListOf()
        private val joggingOffroad: MutableList<Jogging> = mutableListOf()
        fun getJoggingCity() = joggingCity
        fun getJoggingOffroad() = joggingOffroad
        fun setJoggingCity(data: MutableList<Jogging>) {
            joggingCity.apply {
                clear()
                addAll(data)
            }
        }
        fun setJoggingOffroad(data: MutableList<Jogging>) {
            joggingOffroad.apply {
                clear()
                addAll(data)
            }
        }
        fun getJogging(id: String): Jogging? {
            val temp = mutableListOf<Jogging>()
            temp.addAll(joggingCity)
            temp.addAll(joggingOffroad)
            return temp.find{it.id == id}
        }
    }
}
