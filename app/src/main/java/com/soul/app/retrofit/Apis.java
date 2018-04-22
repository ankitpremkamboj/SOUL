package com.soul.app.retrofit;


import com.soul.app.models.req.DenominationReq;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.req.GetChatDataReq;
import com.soul.app.models.req.GetUnreadChatDataReq;
import com.soul.app.models.req.LoginReq;
import com.soul.app.models.req.SendMessageReq;
import com.soul.app.models.res.ChangeProfileImagesRes;
import com.soul.app.models.res.ChatConversationRes;
import com.soul.app.models.res.CommonUserRes;
import com.soul.app.models.res.DeleteImagesRes;
import com.soul.app.models.res.EditProfileRes;
import com.soul.app.models.res.GetChatDataRes;
import com.soul.app.models.res.GetFilterRes;
import com.soul.app.models.res.GetSettingRes;
import com.soul.app.models.res.InterestListRes;
import com.soul.app.models.res.LikeDislikeRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.LoginRes;
import com.soul.app.models.res.LogoutRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.OtherProfileRes;
import com.soul.app.models.res.SendMessageRes;
import com.soul.app.models.res.UpdateFilterRes;
import com.soul.app.models.res.UserImageRes;
import com.soul.app.models.res.UserListRes;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.models.res.UserProfileRes;
import com.soul.app.retrofit.ApiConstants;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Apis {


    @POST(ApiConstants.REGISTER)
    Call<ObjResp<LoginRes>> signUp(@Body LoginReq req);

    @POST(ApiConstants.UPDATE_DENOMINATION)
    Call<ObjResp> updateDenomination(@Body DenominationReq req);

    @POST(ApiConstants.INTEREST_LIST)
    Call<ListResp<InterestListRes>> getInterestList(@Body GeneralReq req);

    @POST(ApiConstants.USER_INTEREST)
    Call<ListResp<InterestListRes>> getUserInterest(@Body GeneralReq req);

    @POST(ApiConstants.USER_LIST)
    Call<UserListRes> getUserList(@Body GeneralReq req);

    @POST(ApiConstants.OTHER_PROFILE)
    Call<ObjResp<OtherProfileRes>> otherProfile(@Body GeneralReq req);

    @POST(ApiConstants.USER_PROFILE)
    Call<ObjResp<UserProfileRes>> userProfile(@Body GeneralReq req);

    @POST(ApiConstants.USER_LIKE_DISLIKE)
    Call<LikeDislikeRes> userLikeDislike(@Body GeneralReq req);

    @POST(ApiConstants.GET_USER_SETTING)
    Call<ObjResp<GetSettingRes>> getSetting(@Body GeneralReq req);

    @POST(ApiConstants.UPDATE_USER_SETTING)
    Call<CommonUserRes> updateSetting(@Body GeneralReq req);

    @POST(ApiConstants.UPDATE_INCOGNITO_MODE)
    Call<CommonUserRes> updateIncognitoMode(@Body GeneralReq req);

    @POST(ApiConstants.SIGNOUT)
    Call<LogoutRes> signOut(@Body GeneralReq req);

    @POST(ApiConstants.REMOVE_ACCOUNT)
    Call<CommonUserRes> removeAccount(@Body GeneralReq req);

    @POST(ApiConstants.USER_MATCH)
    Call<ListResp<UserMatchesRes>> getUserMatches(@Body GeneralReq req);

    // Update Edit Profile
//    @Multipart
//    @POST(ApiConstants.EDIT_PROFILE)
//    Call<EditProfileRes> updateEditProfile(@PartMap Map<String, RequestBody> requestBodyMap,
//                                           @Part("json") GeneralReq bean);

    @Multipart
    @POST(ApiConstants.EDIT_PROFILE)
    Call<EditProfileRes> updateEditProfile(@PartMap Map<String, RequestBody> requestBodyMap,@Part("json") GeneralReq bean);

    @Multipart
    @POST(ApiConstants.UPLOAD_IMAGE)
    Call<UserImageRes> updateUserImage(@PartMap Map<String, RequestBody> requestBodyMap);

    @POST(ApiConstants.UPDATE_LOCATION)
    Call<ObjResp> updateLoc(@Body GeneralReq req);

    @POST(ApiConstants.GET_CHAT_DATA)
    Call<GetChatDataRes> getChatData(@Body GetChatDataReq req);

    @POST(ApiConstants.SEND_MESSAGE)
    Call<SendMessageRes> sendMessageData(@Body SendMessageReq req);

    @POST(ApiConstants.GET_UNREAD_CHAT_DATA)
    Call<GetChatDataRes> getUnreadChatData(@Body GetUnreadChatDataReq req);


    @POST(ApiConstants.CHAT_CONV)
    Call<ListResp<ChatConversationRes>> chatConversation(@Body GeneralReq req);

    @POST(ApiConstants.FLAGED_USER)
    Call<ObjResp> reportUser(@Body GeneralReq req);


    @POST(ApiConstants.UN_MATCH_USER)
    Call<ObjResp> unMatchUser(@Body GeneralReq req);


    @POST(ApiConstants.CHANGE_PROFILE_IMAGES)
    Call<ChangeProfileImagesRes> changeHttpProfileImages(@Body GeneralReq req);

    @POST(ApiConstants.DELETE_PROFILE_IMAGES)
    Call<DeleteImagesRes> deleteImagesRes(@Body GeneralReq req);

    @POST(ApiConstants.GET_USER_FILTER)
    Call<GetFilterRes> getFilterRes(@Body GeneralReq req);

    @POST(ApiConstants.UPDATE_USER_FILTER)
    Call<UpdateFilterRes> updateFilterRes(@Body GeneralReq req);
}

