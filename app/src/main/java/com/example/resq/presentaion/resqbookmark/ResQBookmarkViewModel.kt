package com.example.resq.presentaion.resqbookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.sign.GoogleSignViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQBookmarkViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun deleteBookmark(bookmark: Int) {
        viewModelScope.launch {
            try {
                apiService.deleteToFavoriteResQList(bookmark)
                GoogleSignViewModel().getFavoriteResQList()
            } catch (e: Exception) {
                Log.d("deleteBookmark", e.message.toString())
            }
        }
    }
}