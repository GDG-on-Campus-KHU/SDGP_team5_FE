package com.example.resq.presentaion.resqsearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.searchApiService
import com.example.resq.presentaion.resqsearch.model.MatchDetail
import com.example.resq.presentaion.resqsearch.model.MatchQuery
import com.example.resq.presentaion.resqsearch.model.QueryWrapper
import com.example.resq.presentaion.resqsearch.model.ResQInfo
import com.example.resq.presentaion.resqsearch.model.SearchRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQSearchViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQDetail = MutableStateFlow(emptyList<ResQInfo>())
    val resQDetail: MutableStateFlow<List<ResQInfo>> = _resQDetail

    fun getResQSearch(resQ: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val request = SearchRequest(
                query = QueryWrapper(
                    match = MatchQuery(
                        resQDetail = MatchDetail(query = resQ)
                    )
                )
            )
            try {
                val response = searchApiService.elasticSearch(request)
                response.body()?.let { _resQDetail.value = it.hits.hits }
            } catch (e: Exception) {
                Log.d("getResQSearch", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}