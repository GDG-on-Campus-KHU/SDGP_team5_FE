package com.example.resq.presentaion.resqbookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.FAVORITE_RESQ_LIST
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.resqbookmark.model.ResQBookmark
import com.example.resq.presentaion.sign.GoogleSignViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQBookmarkViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQBookmarks = MutableStateFlow(emptyList<ResQBookmark>())
    val resQBookmarks: StateFlow<List<ResQBookmark>> = _resQBookmarks

    init {
        _resQBookmarks.value = FAVORITE_RESQ_LIST
    }
    
    fun deleteBookmark(bookmark: Int) {
        viewModelScope.launch {
            try {
                apiService.deleteToFavoriteResQList(bookmark)
                GoogleSignViewModel().getFavoriteResQList {
                    FAVORITE_RESQ_LIST = it
                    _resQBookmarks.value = it
                }
            } catch (e: Exception) {
                Log.d("deleteBookmark", e.message.toString())
            }
        }
    }
}