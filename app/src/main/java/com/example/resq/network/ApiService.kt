package com.example.resq.network

import com.example.resq.network.model.AccessTokenResponse
import com.example.resq.network.model.ElasticSearchResponse
import com.example.resq.network.model.FavoriteResQListResponse
import com.example.resq.network.model.NewRoomResponse
import com.example.resq.network.model.ResponseMessage
import com.example.resq.network.model.RoomsResponse
import com.example.resq.network.model.SignInResponse
import com.example.resq.network.model.UploadAudioResponse
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
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    // 구글 로그인
    @POST("api/auth/login/google")
    suspend fun googleSignIn(
        @Body serverAuthCode: String
    ): Response<SignInResponse>

    // 새로운 accessToken 발급
    @POST("api/auth/refresh-token")
    @Headers("Need-Auth: true")
    fun getNewAccessToken(
        @Body accessToken: String
    ): Response<AccessTokenResponse>

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
    fun getFavoriteResQList(): Response<FavoriteResQListResponse>

    // 즐겨찾기에 추가
    @POST("api/favorites/{situationIndex}")
    @Headers("Need-Auth: true")
    fun addToFavoriteResQList(
        @Path("situationIndex") resQ: String
    ): Response<ResponseMessage>

    // 즐겨찾기에 제거
    @DELETE("api/favorites/{situationIndex}")
    @Headers("Need-Auth: true")
    fun deleteToFavoriteResQList(
        @Path("situationIndex") resQ: String
    ): Response<ResponseMessage>

    // 공유 방 조회
    @GET("api/groups/me")
    @Headers("Need-Auth: true")
    suspend fun getRooms(): Response<RoomsResponse>

    // 공유 방 생성
    @POST("api/groups")
    @Headers("Need-Auth: true")
    suspend fun newRoom(
        @Body roomTitle: String
    ): Response<NewRoomResponse>

    // 공유 방 초대
    @POST("api/groups/{id}/invite")
    @Headers("Need-Auth: true")
    suspend fun inviteRoomMember(
        @Path("id") roomId: String,
        @Body email: String
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
    @POST("api/recordings")
    @Headers("Need-Auth: true")
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part,
    ): Response<UploadAudioResponse>
}