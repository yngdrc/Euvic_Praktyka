package com.euvic.praktyka_kheller.ui.main.state

sealed class MainStateEvent {
    class GetHeroesEvent: MainStateEvent()
    class GetHeroesFromDatabaseEvent: MainStateEvent()
    class GetDetailsEvent(
        val heroID: Int
    ): MainStateEvent()
    class None: MainStateEvent()
}