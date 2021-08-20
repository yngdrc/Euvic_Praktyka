package com.euvic.praktyka_kheller.util

import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

data class DataState<T>(
    var message: Event<String>? = null,
    var loading: SwipeRefreshState = SwipeRefreshState(false),
    var data: Event<T>? = null
) {
    companion object {
        fun<T> error(
            message: String
        ): DataState<T> {
            return DataState(
                message = Event(message),
                loading = SwipeRefreshState(false),
                data = null
            )
        }

        fun<T> loading(
            isLoading: Boolean
        ): DataState<T> {
            return DataState(
                message = null,
                loading = SwipeRefreshState(isLoading),
                data = null
            )
        }

        fun<T> data(
            message: String? = null,
            data: T? = null
        ): DataState<T> {
            return DataState(
                message = Event.messageEvent(message),
                loading = SwipeRefreshState(false),
                data = Event.dataEvent(data)
            )
        }
    }

    override fun toString(): String {
        return "DataState(message=$message, loading=$loading, data=$data"
    }
}