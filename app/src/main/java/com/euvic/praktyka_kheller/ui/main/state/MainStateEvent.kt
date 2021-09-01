package com.euvic.praktyka_kheller.ui.main.state

sealed class MainStateEvent {
    object GetHeroesEvent : MainStateEvent()
    object GetHeroesFromDatabaseEvent : MainStateEvent()
    class GetDetailsEvent(
        val heroID: Int
    ): MainStateEvent()
    object None : MainStateEvent()
}