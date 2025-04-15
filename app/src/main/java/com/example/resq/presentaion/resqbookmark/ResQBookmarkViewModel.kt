package com.example.resq.presentaion.resqbookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.presentaion.resqbookmark.model.ResQBookmark
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQBookmarkViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _bookmarks = MutableStateFlow(emptyList<ResQBookmark>())
    val bookmarks: StateFlow<List<ResQBookmark>> = _bookmarks

    init {
        getBookmarks("")
    }

    fun getBookmarks(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _bookmarks.value = (1..10).map { ResQBookmark(it.toString()) }
            } catch (e: Exception) {
                Log.d("getBookmarks", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun deleteBookmark(bookmark: String) {
        viewModelScope.launch {
            try {
                // bookmark 삭제
                getBookmarks("")
            } catch (e: Exception) {
                Log.d("getBookmarks", e.message.toString())
            }
        }
    }
}