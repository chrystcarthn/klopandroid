package appdeveloper.klop.klop.API;

/**
 * Created by CMDDJ on 12/7/2017.
 */

import appdeveloper.klop.klop.Model.BookingDetailResponse;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.CatSettingResponse;
import appdeveloper.klop.klop.Model.CategoryDbResponse;
import appdeveloper.klop.klop.Model.CategoryResponse;
import appdeveloper.klop.klop.Model.FacSettingResponse;
import appdeveloper.klop.klop.Model.FacilityDbResponse;
import appdeveloper.klop.klop.Model.FacilityResponse;
import appdeveloper.klop.klop.Model.LikeResponse;
import appdeveloper.klop.klop.Model.NewsResponse;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Model.ScheduleResponse;
import appdeveloper.klop.klop.Model.StoreHasCategoryResponse;
import appdeveloper.klop.klop.Model.StoreHasFacilityResponse;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Model.TreatmentResponse;
import appdeveloper.klop.klop.Model.User;
import appdeveloper.klop.klop.Model.UserResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceAPI {
    //==========================================================================================================//
    //                                         U     S     E     R                                              //
    //==========================================================================================================//

    //LOGIN
    @FormUrlEncoded
    @POST("Member/login")
    Call<UserResponse>
    login(@Field ("phone") String strPhone,
          @Field ("password") String strPassword);

    //checkPhoneExist
    @FormUrlEncoded
    @POST("Member/checkPhoneExist")
    Call<UserResponse>
    checkEmailExist(@Field ("email") String strEmail);

    //REGISTER
    @FormUrlEncoded
    @POST("Member/register")
    Call<UserResponse>
    register(@Field ("full_name") String strFullname,
             @Field ("phone") String strPhone,
             @Field ("password") String strPassword);

    //showInfoDetailUser
    @GET("Member/getUserByIdUser")
    Call<UserResponse>
    getInfoUser(@Query ("id_user") String strIdUser);


    //editProfile
    @FormUrlEncoded
    @POST("Member/edituser")
    Call<UserResponse>
    edituser(@Field ("id_user") String strIdUser,
             @Field ("full_name") String strFullname,
             @Field ("password") String strPassword,
             @Field ("phone") String strPhone,
             @Field ("email") String strmail,
             @Field ("avatar") String strAvatar,
             @Field ("encodedfile") String strEncodedfile);


    //==========================================================================================================//
    //                                         S     T     O     R     E                                        //
    //==========================================================================================================//
    @FormUrlEncoded
    @POST("Store/checkHasStore")
    Call<StoreResponse>
    doesUserHaveOutlet(@Field("id_user") String strIdUser);


    //addStore
    @FormUrlEncoded
    @POST("Store/addstore")
    Call<StoreResponse>
    addstore(@Field ("id_user") String strIdUser,
             @Field ("name_store") String strNameOutlet,
             @Field ("address_store") String strAddress,
             @Field ("phone_store") String strPhone,
             @Field ("latitude") String strLatitude,
             @Field ("longitude") String strLongitude,
             @Field ("distance") String strDistance);



    @FormUrlEncoded
    @POST("Store/getAllstore")
    Call<StoreResponse>
    showListOutletbyIdUser(@Field ("id_user") String strIdUser,
                         @Field ("latCurrent") double strLat,
                         @Field ("longCurrent") double strLong);

    @FormUrlEncoded
    @POST("Store/getAllstoreVerified")
    Call<StoreResponse>
    showListAllOutletVerified(@Field ("id_user") String strIdUser,
                           @Field ("latCurrent") double strLat,
                           @Field ("longCurrent") double strLong);

    @FormUrlEncoded
    @POST("Store/searchAllstoreVerifiedByKeyword")
    Call<StoreResponse>
    searchAllStoreByKeyword(@Field("id_user") String strIdUser,
                            @Field("keyword") String strKeyword,
                            @Field("latCurrent") double lat,
                            @Field("longCurrent") double lng);

    @FormUrlEncoded
    @POST("Store/searchAllUserStoreByKeyword")
    Call<StoreResponse>
    searchUserStoreByKeyword(@Field("id_user") String strIdUser,
                             @Field("keyword") String strKeyword,
                             @Field("latCurrent") double lat,
                             @Field("longCurrent") double lng);

    @FormUrlEncoded
    @POST("Store/searchFiltering")
    Call<StoreResponse>
    searchStoreFiltered(@Field("treatment") String strTreatment,
                        @Field("category") String strCategory,
                        @Field("facility") String strFacility,
                        @Field("days") String strDays,
                        @Field("price_Min") String strPriceMin,
                        @Field("price_Max") String strPriceMax,
                        @Field("rate_sumMin") String strRateMin,
                        @Field("rate_sumMax") String strRateMax,
                        @Field("latCurrent") double lat,
                        @Field("longCurrent") double lng);

    //showInfoDetailStore
    @FormUrlEncoded
    @POST("Store/getStoreByIdStore")
    Call<StoreResponse>
    showInfoOutlet(@Field("id_store") String strIdStore,
                   @Field("latCurrent") double lat,
                    @Field("longCurrent") double lng);

    //getLatestStoreCreatedByUser
    @FormUrlEncoded
    @POST("Store/getLastStoreAddedByIdUser")
    Call<StoreResponse>
    getLatestStore(@Field("id_user") String strIdUser);

    //editStore - Add Logo
    @FormUrlEncoded
    @POST("Store/editStoreAddLogo")
    Call<StoreResponse>
    addLogoStore(@Field ("id_store") String strIdUStore,
                 @Field ("logo_store") String strLogoUrl,
                 @Field ("encodedfile") String strEncodedFile);

    @FormUrlEncoded
    @POST("Store/editStoreAddBanner")
    Call<StoreResponse>
    addBannerStore(@Field ("id_store") String strIdUStore,
                 @Field ("banner") String strBannerUrl,
                 @Field ("encodedfile") String strEncodedFile);


    @FormUrlEncoded
    @POST("Store/useAsBanner")
    Call<StoreResponse>
    usePhotoAsBanner(@Field ("id_store") String strIdStore,
                     @Field ("banner") String strBanner);



    //editStore - Add Logo
    @FormUrlEncoded
    @POST("Store/addPhotoReview")
    Call<PhotoResponse>
    addPhotoRevStore(@Field ("id_store") String strIdStore,
                     @Field ("file") String strLogoUrl,
                     @Field ("encodedfile") String strEncodedFile,
                     @Field("id_user") String strIdUser);


    //updateRating
    @FormUrlEncoded
    @POST("Store/updateRating")
    Call<ReviewResponse>
    updateRating(@Field ("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/updateSetSch")
    Call<StoreResponse>
    updateSetManual(@Field ("id_store") String strIdStore,
                    @Field ("is_setmanual") String strManual);

    @FormUrlEncoded
    @POST("Store/updateStore")
    Call<StoreResponse>
    editStore(@Field ("id_store") String strIdStore,
              @Field ("name_store") String strNameOutlet,
              @Field ("address_store") String strAddress,
              @Field ("phone_store") String strPhone,
              @Field ("latitude") String strLatitude,
              @Field ("longitude") String strLongitude);



    @FormUrlEncoded
    @POST("Store/deleteStore")
    Call<StoreResponse>
    deleteStore(@Field("id_store") String strIdStore);


    //=================================================== CATEGORY =================================================//

    //showCategoryByStore
    @FormUrlEncoded
    @POST("Store/getCategoryByIdStore")
    Call<CategoryResponse>
    showCategoryByStore(@Field("id_store") String strIdStore);

    @GET("Store/getCategoryInDb")
    Call<CategoryDbResponse>
    showCategoryInDb();

    @GET("Store/getCatSetting")
    Call<CatSettingResponse>
    showCatSettings(@Query("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Store/deleteCategory")
    Call<StoreHasCategoryResponse>
    deleteCat(@Field("id_store") String strIdStore,
                   @Field("id_category") String strIdCat);


    //=================================================== STORE HAS CATEGORY =================================================//
    @FormUrlEncoded
    @POST("Store/addStoreCategory")
    Call<StoreHasCategoryResponse>
    addCategoryToStore(@Field("id_store") String strIdOutlet,
                       @Field("id_category") String strArrayIdCat);



//    =================================================== FACILITY =================================================

    //showFacilityByStore
    @FormUrlEncoded
    @POST("Store/getFacilityByIdStore")
    Call<FacilityResponse>
    showFacilityByStore(@Field("id_store") String strIdStore);

    @GET("Store/getFacilityInDb")
    Call<FacilityDbResponse>
    showFacilityInDb();

    @GET("Store/getFacSetting")
    Call<FacSettingResponse>
    showFacSettings(@Query("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/deleteFacility")
    Call<StoreHasFacilityResponse>
    deleteFac(@Field("id_store") String strIdStore,
              @Field("id_facility") String strIdFac);




    //=================================================== STORE HAS FACILITY =================================================//

    @FormUrlEncoded
    @POST("Store/addStoreFacility")
    Call<StoreHasFacilityResponse>
    addFacilityToStore(@Field("id_store") String strIdOutlet,
                       @Field("id_facility") String strArrayIdFas);


    //=================================================== SCHEDULE =================================================//

    //showScheduleByStore
    @FormUrlEncoded
    @POST("Store/getScheduleByIdStore")
    Call<ScheduleResponse>
    showScheduleByStore(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/addSchedule")
    Call<ScheduleResponse>
    addSchedule(@Field("days") String strDays,
                @Field("time_open") String strTimeOpen,
                @Field("time_closed") String strTimeClosed,
                @Field("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Store/addHarianSch")
    Call<ScheduleResponse>
    addHarianSch(@Field("days") String strDays,
                @Field("time_open") String strTimeOpen,
                @Field("time_closed") String strTimeClosed,
                @Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/delSchedule")
    Call<ScheduleResponse>
    deleteSchedule( @Field ("id_schedule") String strIdSchedule);


    @FormUrlEncoded
    @POST("Store/delAllSchedule")
    Call<ScheduleResponse>
    deleteAllSchedules(@Field ("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Store/updateSchedule")
    Call<ScheduleResponse>
    updateSchedule(@Field("id_schedule") String strSchedule,
               @Field("days") String strDays,
               @Field("time_open") String strTimeOpen,
               @Field("time_closed") String strTimeClosed);


    //    =================================================== TREATMENT =================================================

    //showTreatmentByStore
    @FormUrlEncoded
    @POST("Store/getTreatmentByIdStore")
    Call<TreatmentResponse>
    showTreatmentByStore(@Field("id_store") String strIdStore);

    @GET("Store/searchTr")
    Call<TreatmentResponse>
    searchTreatment(@Query("id_store") String strIdStore,
                    @Query("keyword") String strKeyword);


    @FormUrlEncoded
    @POST("Store/addTreatment")
    Call<TreatmentResponse>
    addTreatment(@Field("id_store") String strIdStore,
                @Field("name_treatment") String strNmTreatment,
                @Field("description_treatment") String strDesc,
                @Field("price_treatment") String strHrgTreatment);


    @FormUrlEncoded
    @POST("Store/updateTreatment")
    Call<TreatmentResponse>
    updateTreatment(@Field("id_treatment") String strIdTreatment,
                 @Field("name_treatment") String strNmTreatment,
                 @Field("description_treatment") String strDesc,
                 @Field("price_treatment") String strHrgTreatment);


    @FormUrlEncoded
    @POST("Store/deleteTreatment")
    Call<TreatmentResponse>
    deleteTreatment(@Field("id_treatment") String strIdTreatment);

    //    =================================================== REVIEW =================================================

    //showTreatmentByStore

    @FormUrlEncoded
    @POST("Store/getReviewByIdStore")
    Call<ReviewResponse>
    showReviewByStore(@Field("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Store/getReview1")
    Call<ReviewResponse>
    showRev1(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/getReview2")
    Call<ReviewResponse>
    showRev2(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/getReview3")
    Call<ReviewResponse>
    showRev3(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/getReview4")
    Call<ReviewResponse>
    showRev4(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/getReview5")
    Call<ReviewResponse>
    showRev5(@Field("id_store") String strIdStore);

    //addReview
    @FormUrlEncoded
    @POST("Store/addReview")
    Call<ReviewResponse>
    addReview(@Field ("id_store") String strIdStore,
              @Field ("id_user") String strIdUser,
                 @Field ("text_review") String strTextReview,
                 @Field ("rate") String strRate);

    @FormUrlEncoded
    @POST("Store/getReviewByIdStore")
    Call<ReviewResponse>
    showStatistic2(@Field("id_store") String strIdStore);


//    ======================================================= LIKES ===================================================

    //addLike
    @FormUrlEncoded
    @POST("Store/addLike")
    Call<LikeResponse>
    addLike( @Field ("id_review") String strIdReview,
               @Field ("id_user") String strIdUser);


    //deleteLike
    @FormUrlEncoded
    @POST("Store/deleteLike")
    Call<LikeResponse>
    unLike( @Field ("id_review") String strIdReview,
             @Field ("id_user") String strIdUser);

    //checkLikeOrNot
    @GET("Store/getCheckLikes")
    Call<LikeResponse>
    checkLikeOrNot(@Query("id_review") String strIdReview,
                   @Query("id_user") String strIdUser);


//    ====================================================== photo ===================================================

    //showInfoDetailStore
    @FormUrlEncoded
    @POST("Store/getPhotoByIdStore")
    Call<PhotoResponse>
    getOutletPhotoHeader(@Field ("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/deletePhoto")
    Call<PhotoResponse>
    delPhoto( @Field ("id_photo") String strIdPhoto);


    //    ====================================================== news ===================================================

    @FormUrlEncoded
    @POST("Store/getNewsPub")
    Call<NewsResponse>
    showPubNews(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/getAllNews")
    Call<NewsResponse>
    showAllNews(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Store/deleteNews")
    Call<NewsResponse>
    delNews(@Field ("id_news") String strIdNews);

    @FormUrlEncoded
    @POST("Store/addNews")
    Call<NewsResponse>
    addNews(@Field("id_store") String strIdStore,
             @Field("title") String strTitle,
             @Field("content") String strContent,
             @Field("status_news") String strStatus);


    @FormUrlEncoded
    @POST("Store/updateNews")
    Call<NewsResponse>
    updateNews(@Field("id_news") String strIdNews,
                    @Field("title") String strTitle,
                    @Field("content") String strContent,
                    @Field("status_news") String strStatus);



    //==========================================================================================================//
    //                                         B   O   O   klop   I   N   G                                              //
    //==========================================================================================================//

    @FormUrlEncoded
    @POST("Booking/getAllBookedByUser")
    Call<BookingResponse>
    showListAllBooking(@Field("id_user") String strIdUser);

    @FormUrlEncoded
    @POST("Booking/getAllDetailByBooking")
    Call<BookingDetailResponse>
    showListAllDetail(@Field("id_booking") String strIdBooking);


    //addBooking
    @FormUrlEncoded
    @POST("Booking/makeBooking")
    Call<BookingResponse>
    addBooking(@Field ("id_store") String strIdStore,
               @Field ("id_user") String strIdUser,
               @Field ("dates") String strDate,
               @Field ("times") String strTime,
               @Field ("guest_name") String strNmGuest,
               @Field ("guest_phone") String strTelpGuest,
               @Field ("sum_of_people") String strPeople,
               @Field ("total_payment") String strTotal);

    //addBooking
    @FormUrlEncoded
    @POST("Booking/addDetailBooking")
    Call<BookingResponse>
    addDetailBooking(@Field ("id_booking") String strIdBooking,
               @Field ("id_treatment") String strIdTr,
               @Field ("quantity") String strQty,
               @Field ("price_now") String strPrice,
               @Field ("sub_total") String strSub);


    //getLatestStoreCreatedByUser
    @FormUrlEncoded
    @POST("Booking/getLastBookingByUser")
    Call<BookingResponse>
    getLatestBooked(@Field("id_user") String strIdUser);


    //==========================================================================================================//
    //                                         R E S E R V A T I O N                                              //
    //==========================================================================================================//

    @FormUrlEncoded
    @POST("Booking/getCountBook")
    Call<BookingResponse>
    getCountBook(@Field("id_user") String strIdUser);


    @FormUrlEncoded
    @POST("Booking/getAllReservation")
    Call<BookingResponse>
    showAllRequest(@Field("id_owner") String strIdOwner);

    @FormUrlEncoded
    @POST("Booking/getWaitingRes")
    Call<BookingResponse>
    showWaiting(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Booking/getApprovedRes")
    Call<BookingResponse>
    showApproved(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Booking/getCanceledRes")
    Call<BookingResponse>
    showCanceled(@Field("id_store") String strIdStore);

    @FormUrlEncoded
    @POST("Booking/getRejectedRes")
    Call<BookingResponse>
    showRejected(@Field("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Booking/getAllReservationByStore")
    Call<BookingResponse>
    showStatistic(@Field("id_store") String strIdStore);


    @FormUrlEncoded
    @POST("Booking/approve")
    Call<BookingResponse>
    terimaReservasi(@Field ("id_booking") String strIdBooking);

    @FormUrlEncoded
    @POST("Booking/cancel")
    Call<BookingResponse>
    batalkanReservasi(@Field ("id_booking") String strIdBooking);

    @FormUrlEncoded
    @POST("Booking/decline")
    Call<BookingResponse>
    tolakReservasi(@Field ("id_booking") String strIdBooking);

    @FormUrlEncoded
    @POST("Booking/drop")
    Call<BookingResponse>
    batalkanReservasiOlehOutlet(@Field ("id_booking") String strIdBooking);


}
