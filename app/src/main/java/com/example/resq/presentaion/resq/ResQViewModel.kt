package com.example.resq.presentaion.resq

import androidx.lifecycle.ViewModel
import com.example.resq.R
import com.example.resq.presentaion.resq.model.ResQ
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResQViewModel : ViewModel() {

    private val _resQList = MutableStateFlow(
        listOf(
            ResQ("의식장애/심정지", R.drawable.resq4),
            ResQ("호흡곤란", R.drawable.resq3),
            ResQ("출혈", R.drawable.resq2),
            ResQ("외상", R.drawable.resq1),
            ResQ("경련/발작", R.drawable.resq7),
            ResQ("화상", R.drawable.resq8),
            ResQ("온열/한랭", R.drawable.resq6),
            ResQ("정신적 응급", R.drawable.resq5)
        )
    )
    val resQList: StateFlow<List<ResQ>> = _resQList

}