package com.example.resq.network

import com.example.resq.network.model.AccessTokenResponse
import com.example.resq.network.model.AuthRequest
import com.example.resq.network.model.CountryInfoResponse
import com.example.resq.network.model.CountryRequest
import com.example.resq.network.model.ElasticSearchResponse
import com.example.resq.network.model.FavoriteResQListResponse
import com.example.resq.network.model.GetRecordsResponse
import com.example.resq.network.model.InviteRoomRequest
import com.example.resq.network.model.MedicalInfoRequest
import com.example.resq.network.model.MedicalInfoResponse
import com.example.resq.network.model.NewRoomRequest
import com.example.resq.network.model.NewRoomResponse
import com.example.resq.network.model.NewTokenRequest
import com.example.resq.network.model.ResponseMessage
import com.example.resq.network.model.RoomsResponse
import com.example.resq.network.model.SignInResponse
import com.example.resq.network.model.TranslateInfoRequest
import com.example.resq.network.model.TranslateInfoResponse
import com.example.resq.network.model.UploadAudioResponse
import com.example.resq.network.model.UserInfoResponse
import com.example.resq.presentaion.resqdetail.model.ResQDetailResponse
import com.example.resq.presentaion.resqsearch.model.SearchRequest
import com.example.resq.presentaion.roomdetail.model.RoomDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    // 구글 로그인
    @POST("api/auth/login/google")
    suspend fun googleSignIn(
        @Body serverAuthCode: AuthRequest
    ): Response<SignInResponse>

    // 새로운 accessToken 발급
    @POST("api/auth/refresh-token")
    @Headers("Need-Auth: true")
    suspend fun getNewAccessToken(
        @Body accessToken: NewTokenRequest
    ): Response<AccessTokenResponse>

    // 사용자 정보 조회
    @GET("api/users/me")
    @Headers("Need-Auth: true")
    suspend fun getMyInfo(): Response<UserInfoResponse>

    // 특정 사용자 정보 조회
    @GET("api/users/info/{user_id}")
    @Headers("Need-Auth: true")
    suspend fun getUserInfo(
        @Path("user_id") userId: Int
    ): Response<UserInfoResponse>

    // 구조 방법 상세 설명
    @GET("api/situation/actions/case/{slug}/{language}")
    suspend fun getResQDetail(
        @Path("slug") slug: String,
        @Path("language") language: String
    ): Response<ResQDetailResponse>

    // 구조 방법 검색
    @POST("resq.situation/_search?pretty")
    suspend fun elasticSearch(
        @Body request: SearchRequest
    ): Response<ElasticSearchResponse>

    // 즐겨찾기 구조 리스트 조회
    @GET("api/favorites")
    @Headers("Need-Auth: true")
    suspend fun getFavoriteResQList(): Response<FavoriteResQListResponse>

    // 즐겨찾기에 추가
    @POST("api/favorites/{situationIndex}")
    @Headers("Need-Auth: true")
    suspend fun addToFavoriteResQList(
        @Path("situationIndex") resQ: Int
    ): Response<ResponseMessage>

    // 즐겨찾기에 제거
    @DELETE("api/favorites/{situationIndex}")
    @Headers("Need-Auth: true")
    suspend fun deleteToFavoriteResQList(
        @Path("situationIndex") resQ: Int
    ): Response<ResponseMessage>

    // 공유 방 조회
    @GET("api/groups/me")
    @Headers("Need-Auth: true")
    suspend fun getRooms(): Response<RoomsResponse>

    // 초대받은 공유 방 조회
    @GET("api/groups/pending/me")
    @Headers("Need-Auth: true")
    suspend fun getInvitedRooms(): Response<RoomsResponse>

    // 공유 방 생성
    @POST("api/groups")
    @Headers("Need-Auth: true")
    suspend fun newRoom(
        @Body roomTitle: NewRoomRequest
    ): Response<NewRoomResponse>

    // 공유 방 초대
    @POST("api/groups/{id}/invite")
    @Headers("Need-Auth: true")
    suspend fun inviteRoomMember(
        @Path("id") roomId: String,
        @Body email: InviteRoomRequest
    ): Response<ResponseMessage>

    // 공유 방 정보 조회
    @GET("api/groups/{id}")
    @Headers("Need-Auth: true")
    suspend fun getRoomInfo(
        @Path("id") roomId: String
    ): Response<RoomDetailResponse>

    // 공유 방 멤버 조회
//    @GET("api/groups/{id}/members")
//    @Headers("Need-Auth: true")
//    suspend fun getRoomMembers(
//        @Path("id") roomId: String
//    ): Response<RoomMembersResponse>

    // 공유 방 이름 수정
//    @PATCH("api/groups/{groupId}")
//    @Headers("Need-Auth: true")
//    suspend fun fixRoomTitle(
//        @Path("groupId") groupId: String,
//        @Body request: UpdateGroupRequest
//    ): retrofit2.Response<Unit>

    // 공유 방 삭제
    @DELETE("api/groups/{id}")
    @Headers("Need-Auth: true")
    suspend fun deleteRoom(
        @Path("id") roomId: String
    ): Response<ResponseMessage>

    // 공유 방 나가기
    @DELETE("api/groups/{id}/members/me")
    @Headers("Need-Auth: true")
    suspend fun outRoom(
        @Path("id") roomId: String
    ): Response<ResponseMessage>

    // 공유 방 초대 수락
    @POST("api/groups/{id}/accept")
    suspend fun acceptNotify(
        @Path("id") roomId: String
    ): Response<ResponseMessage>

    // 공유 방 초대 거절
    @POST("api/groups/{id}/reject")
    suspend fun rejectNotify(
        @Path("id") roomId: String
    ): Response<ResponseMessage>

    // 녹음 파일 저장
    @Multipart
    @POST("api/recordings/stt")
    @Headers("Need-Auth: true")
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part,
    ): Response<UploadAudioResponse>

    // 녹음 파일 불러오기
    @GET("api/recordings/me")
    suspend fun getRecords():Response<GetRecordsResponse>

    // 특정 사용자 의료 정보 조회
    @GET("api/medical-info/{id}")
    @Headers("Need-Auth: true")
    suspend fun getMedicalInfo(
        @Path("id") userId: Int
    ): Response<MedicalInfoResponse>

    // 사용자 의료 정보 생성
    @POST("api/medical-info")
    @Headers("Need-Auth: true")
    suspend fun newInfo(
        @Body request: MedicalInfoRequest
    ): Response<MedicalInfoResponse>

    // 사용자 의료 정보 조회
    @GET("api/medical-info/me")
    @Headers("Need-Auth: true")
    suspend fun getInfo(): Response<MedicalInfoResponse>

    // 사용자 의료 정보 수정
    @PUT("api/medical-info")
    @Headers("Need-Auth: true")
    suspend fun editInfo(
        @Body request: MedicalInfoRequest
    ): Response<MedicalInfoResponse>

    // 여행 국가 변경
    @PATCH("api/users/me/country")
    suspend fun updateCountry(
        @Body request: CountryRequest
    ): Response<UserInfoResponse>

    // 사용자 의료 정보 번역
    @POST("api/medical-info/translate")
    @Headers("Need-Auth: true")
    suspend fun translateInfo(
        @Body request: TranslateInfoRequest
    ): Response<TranslateInfoResponse>

    // 나라 관련 정보 조회
    @GET("api/country/{country_code}")
    suspend fun getCountryInfo(
        @Path("country_code") countryCode: String
    ): Response<CountryInfoResponse>
}