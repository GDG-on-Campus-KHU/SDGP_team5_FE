package com.example.resq.presentaion.resqdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.presentaion.resqdetail.model.ResQAction
import com.example.resq.presentaion.resqdetail.model.ResQDescriptionDetail
import com.example.resq.presentaion.resqdetail.model.ResQDetail
import com.example.resq.presentaion.resqdetail.model.ResQDetailResponse
import com.example.resq.presentaion.resqdetail.model.ResQTitleLanguage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQDetail = MutableStateFlow(
        ResQDetailResponse(
            null,
            null,
            null,
            null,
            null,
            null
        )
    )
    val resQDetail: StateFlow<ResQDetailResponse> = _resQDetail

    fun getResQDetail(resQ: String, language: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _resQDetail.value = exGetResQDetailData()
//                val response = apiService.getResQDetail(resQ, language)
//                response.body()?.let { _resQDetail.value = it }
            } catch (e: Exception) {
                Log.d("getResQDetail", e.message.toString())
            }
            _isLoading.value = false
        }
    }
}

fun exGetResQDetailData(): ResQDetailResponse {
    return ResQDetailResponse(
        1,
        "cardiac-arrest",
        "",
        ResQTitleLanguage("의식 장애 + 심정지", "Unconsciousness + Cardiac Arrest"),
        ResQDescriptionDetail(
            listOf(
                "의식 없음은 생명 위협의 신호입니다.",
                "심정지란 심장이 멈춰 혈액이 뇌로 공급되지 않는 상태로, 호흡도 함께 멈추거나 비정상적인 호흡(가쁜 숨, 헐떡임)이 나타납니다.",
                "초기 3~4분 안에 CPR을 하지 않으면 뇌 손상이 시작됩니다."
            )
        ),
        ResQAction(
            listOf(
                ResQDetail(
                    "안전 확인 후 환자 상태 확인",
                    listOf("이름을 부르고, 어깨를 살짝 흔들어 반응을 확인")
                ),
                ResQDetail(
                    "호흡 확인",
                    listOf(
                        "가슴이 오르내리는지 관찰 (10초 이내)",
                        "없거나 이상하면 심정지로 간주"
                    )
                ),
                ResQDetail(
                    "119 신고 요청",
                    emptyList()
                ),
                ResQDetail(
                    "CPR 시행",
                    listOf(
                        "환자를 딱딱한 바닥에 눕히고",
                        "손바닥을 겹쳐 가슴 중앙에 두고, 팔을 곧게 펴서 체중으로 압박",
                        "깊이 5cm, 분당 100~120회 속도"
                    )
                ),
                ResQDetail(
                    "AED(자동심장충격기) 사용",
                    listOf(
                        "근처에 있다면 가져와 지시 음성 따라 사용"
                    )
                )
            )
        )
    )
}