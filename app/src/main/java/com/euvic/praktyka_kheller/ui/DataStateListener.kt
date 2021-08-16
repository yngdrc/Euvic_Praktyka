package com.euvic.praktyka_kheller.ui

import com.euvic.praktyka_kheller.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}