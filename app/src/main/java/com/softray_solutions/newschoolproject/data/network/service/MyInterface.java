package com.softray_solutions.newschoolproject.data.network.service;

import com.softray_solutions.newschoolproject.model.AccountBalanceDataModel;
import com.softray_solutions.newschoolproject.model.AddLessonPreparationSubject;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.AskTeacherDataModel;
import com.softray_solutions.newschoolproject.model.CategoryModel;
import com.softray_solutions.newschoolproject.model.Homework;
import com.softray_solutions.newschoolproject.model.Lesson;
import com.softray_solutions.newschoolproject.model.LessonDetails;
import com.softray_solutions.newschoolproject.model.MessageDataModel;
import com.softray_solutions.newschoolproject.model.MessageModel;
import com.softray_solutions.newschoolproject.model.News;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.model.ParentResetDataModel;
import com.softray_solutions.newschoolproject.model.PositivesStudentDataModel;
import com.softray_solutions.newschoolproject.model.RevisionLibrary;
import com.softray_solutions.newschoolproject.model.RoomModel;
import com.softray_solutions.newschoolproject.model.Semester;
import com.softray_solutions.newschoolproject.model.SemesterSample;
import com.softray_solutions.newschoolproject.model.Skill;
import com.softray_solutions.newschoolproject.model.Son;
import com.softray_solutions.newschoolproject.model.StudentClass;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.StudentEvaluationNames;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.model.StudentPlanClass;
import com.softray_solutions.newschoolproject.model.StudentSkillsDataModel;
import com.softray_solutions.newschoolproject.model.Subject;
import com.softray_solutions.newschoolproject.model.TeacherDay;
import com.softray_solutions.newschoolproject.model.TeacherLibraryModel;
import com.softray_solutions.newschoolproject.model.TeacherNotificationDataModel;
import com.softray_solutions.newschoolproject.model.TeacherPlanClass;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.model.UserCategoryModel;
import com.softray_solutions.newschoolproject.model.Week;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyInterface {
    @POST("android/login/check_login")
    @FormUrlEncoded
    Call<ObjectDataModel<User>> doLogin(@Field("user_name") String userName,
                                        @Field("password") String password);

    @POST("android/home/get_lst_item")
    @FormUrlEncoded
    Call<ArrayDataModel<News>> getNews(@Field("id") int id,
                                       @Field("lang") String language);

    @POST("android/home/get_item")
    @FormUrlEncoded
    Call<ObjectDataModel<News>> getNewsContent(@Field("token") String token,
                                               @Field("lang") String language);

    @POST("android/home/insert_support")
    @FormUrlEncoded
    Call<ObjectDataModel<String>> sendFeedback(@Field("txtMail") String emailAddress,
                                               @Field("txtName") String name,
                                               @Field("txtContent") String content);

    @POST("android/stu_class_table/getStudentClasstable")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentDay<StudentClass>>> getSchedule(@Field("ID") String id);

    @POST("android_father/father_sons/get_father_sons")
    @FormUrlEncoded
    Call<ArrayDataModel<Son>> getMySons(@Field("father_id") String fatherID);

    @POST("android/stu_class_table/getTeacherClasstable")
    @FormUrlEncoded
    Call<ArrayDataModel<TeacherDay>> getTeacherSchedule(@Field("ID") String id);

    @POST("android/emp_class/get_emp_class")
    @FormUrlEncoded
    Call<ArrayDataModel<Semester>> getSemesters(@Field("teacher_id") String id);

    @POST("android/emp_class/get_class_subjects")
    @FormUrlEncoded
    Call<ArrayDataModel<Subject>> getSubjects(@Field("teacher_id") String teacherID,
                                              @Field("RowLevelID") String rowLevelID,
                                              @Field("ClassID") String classID);

    @POST("android/emp_class/get_skills")
    @FormUrlEncoded
    Call<ArrayDataModel<Skill>> getSkills(@Field("SubjectID") String subjectID,
                                          @Field("RowLevelID") String rowLevelID);

    @POST("android_student/student_supject/getLessons")
    @FormUrlEncoded
    Call<ArrayDataModel<Lesson>> getLessons(@Field("RowLevelID") String rowLevelID,
                                            @Field("SubjectID") String subjectID,
                                            @Field("SchoolID") String schoolID,
                                            @Field("ClassID") String classID);

    @POST("android/emp_class/getLessonsClass")
    @FormUrlEncoded
    Call<ArrayDataModel<Lesson>> getTeacherLessons(@Field("RowLevelID") String rowLevelID,
                                                   @Field("SubjectID") String subjectID,
                                                   @Field("TeacherID") String teacherID,
                                                   @Field("ClassID") String classID,
                                                   @Field("SchoolID") String schoolID);

    @POST("android/emp_class/getAllSubject")
    @FormUrlEncoded
    Call<ArrayDataModel<Subject>> getRevisions(@Field("SchoolID") String schoolID,
                                               @Field("StudentID") String studentID);

    @POST("android/emp_class/get_emp_subjects")
    @FormUrlEncoded
    Call<ArrayDataModel<Subject>> getTeacherSubject(@Field("teacherID") String teacherID,
                                                    @Field("lang") String lang);

    @POST("android/emp_class/get_e_library")
    @FormUrlEncoded
    Call<ArrayDataModel<RevisionLibrary>> getLibrary(@Field("StudentID") String studentID,
                                                     @Field("SubjectID") String subjectID,
                                                     @Field("schoolID") String schoolID);

    @POST("android/emp_eval_student/getStudentClass")
    @FormUrlEncoded
    Call<ObjectDataModel<StudentEvaluationNames>> getStudentsClass(@Field("ClassID") String classID,
                                                                   @Field("RowLevelID") String rowLevelID,
                                                                   @Field("SchoolID") String schoolID);

    /*@POST("android/emp_class/getAskTeacher")
    @FormUrlEncoded
    Call<ArrayDataModel<List<AskYourTeacher>>> askYourTeacher(@Field("teacherID") String teacherID,
                                                              @Field("SubjectID") String subjectID,
                                                              @Field("schoolID") String schoolID,
                                                              @Field("rowlevelid") String rowLevelId,
                                                              @Field("type") String type
                                                              );*/
    @POST("android/emp_class/getAskTeacher")
    @FormUrlEncoded
    Call<AskTeacherDataModel> askYourTeacher(@Field("teacherID") String teacherID,
                                             @Field("SubjectID") String subjectID,
                                             @Field("schoolID") String schoolID,
                                             @Field("rowlevelid") String rowLevelId,
                                             @Field("type") String type
    );

    @POST("android_student/student_supject/get_lesson_details")
    @FormUrlEncoded
    Call<ObjectDataModel<LessonDetails>> getLesson(@Field("lessonToken") String lessonToken);

    @POST("android/emp_class/get_notifiction_emp")
    @FormUrlEncoded
    Call<ArrayDataModel<TeacherNotificationDataModel>> getTeacherNotification(@Field("teacherID") String teacherID,
                                                                              @Field("schoolID") String schoolId);


    @POST("android_student/student_supject/get_student_notification")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentNotificaionDataModel>> getStudentNotifications(@Field("studentID") String studentID,
                                                                              @Field("schoolID") String schoolID);

    @POST("android_father/father_sons/get_all_notification_father")
    @FormUrlEncoded
    Call<ArrayDataModel<ParentNotificationDataModel>> getParentNotifications(@Field("fatherID") String fatherID,
                                                                             @Field("SchoolID") String schoolID);


    @POST("android/emp_class/update_notification")
    @FormUrlEncoded
    Call<ObjectDataModel> updateNotification(@Field("teacherID") String teacherID,
                                             @Field("NotificationID") String notificationID);


    @POST("android_student/clerical_homework/index")
    @FormUrlEncoded
    Call<ArrayDataModel<Homework>> getStudentHomework(@Field("contactID") String studentId, @Field("SubjectID") String subjectId);

    @POST("android/emp_class/clerical_homework")
    @FormUrlEncoded
    Call<ArrayDataModel<Homework>> getTeacherHomework(@Field("teacherID") String teacherId,
                                                      @Field("RowLevelID") String rowLevelId,
                                                      @Field("SubjectID") String subjectId);

    @POST("android/emp_eval_student/get_positives")
    @FormUrlEncoded
    Call<ArrayDataModel<PositivesStudentDataModel>> getPositivesList(@Field("lang") String lang);

    @POST("android/emp_eval_student/get_skills_student")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentSkillsDataModel>> getStudentSkillsList(@Field("RowLevelID") String rowLevelId,
                                                                      @Field("SubjectID") String subjectID);

    @POST("android/emp_eval_student/add_eval")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentSkillsDataModel>> submitEval(@Field("studentIdList[]") List<String> studentIdList,
                                                            @Field("skillID") String skillID,
                                                            @Field("positviesID") String positviesID,
                                                            @Field("homeworkDegreeString") String homeworkDegreeString,
                                                            @Field("examDegreeString") String examDegreeString,
                                                            @Field("isParticipated") boolean isParticipated,
                                                            @Field("techerID") String techerID,
                                                            @Field("lessionID") String lessionID,
                                                            @Field("SubjectID") String subjectID,
                                                            @Field("Date") String date,
                                                            @Field("homeworkDegreeStringDef") String homeDegree,
                                                            @Field("examDegreeStringDef") String testDegree);

    @POST("android/stu_class_table/get_week")
    @FormUrlEncoded
    Call<ArrayDataModel<Week>> getWeeks(@Field("lang") String language);

    @POST("android/stu_class_table/getSemesters")
    @FormUrlEncoded
    Call<ArrayDataModel<SemesterSample>> getSemestersSamples(@Field("lang") String language);

    @POST("android/stu_class_table/getTeacherPlanWeek")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentDay<TeacherPlanClass>>> getPlanClass(@Field("ID") String id,
                                                                    @Field("weekID") String weekId,
                                                                    @Field("semesterID") String semesterId);

    @POST("android/stu_class_table/getStudentPlaneweek")
    @FormUrlEncoded
    Call<ArrayDataModel<StudentDay<StudentPlanClass>>> studentPlanWeek(@Field("ID") String studentID,
                                                                       @Field("weekID") String weekID,
                                                                       @Field("semesterID") String semesterID);

    @POST("android/login/uploadImg")
    @Multipart
    Call<ObjectDataModel> updateUserImage(@Part MultipartBody.Part image, @Part("ContectID") RequestBody userID);

    @POST("android_father/father_sons/get_payment")
    @FormUrlEncoded
    Call<ObjectDataModel<AccountBalanceDataModel>> getParentPaymentStatement(@Field("sessionToken") String sessionToken);

    @POST("android_father/father_sons/catch_reset")
    @FormUrlEncoded
    Call<ArrayDataModel<ParentResetDataModel>> getParentReset(@Field("sessionToken") String sessionToken);

    @POST("android/emp_lesson/get_all_lesson")
    @FormUrlEncoded
    Call<ArrayDataModel<AddLessonPreparationSubject>> getSubjects(@Field("TeacherID") String teacherID, @Field("lang") String lang);


    @POST("android/emp_lesson/upload")
    @Multipart
    Call<ObjectDataModel<String>> uploadAttachment(@Part List<MultipartBody.Part> files);


    @POST("android/emp_lesson")
    @FormUrlEncoded
    Call<ObjectDataModel<String>> uploadLesson(@Field("TeacherID") String teacherID,
                                               @Field("Select_subject") String selectedSubjectID,
                                               @Field("SelectSkills") String selectedSkillID,
                                               @Field("lessonTitle") String lessonTitle,
                                               @Field("DayDate") String dayDate,
                                               @Field("lessonStratigy") String lessonStrategy,
                                               @Field("txt_Aims1") String lessonGoals,
                                               @Field("txt_Aids1") String lessonMeans,
                                               @Field("lessonIntro") String lessonPreface,
                                               @Field("txt_Reviews1") String lessonEvaluation,
                                               @Field("trainhome") String lessonHomeworks,
                                               @Field("files") String filePath,
                                               @Field("Scripts") String Scripts

    );

    @POST("android/login/forgetPass")
    @FormUrlEncoded
    Call<ObjectDataModel<Integer>> getResetCode(@Field("userName") String username
            , @Field("mobile") String mobile
            , @Field("mail") String mail);

    @POST("android/login/checkActivation")
    @FormUrlEncoded
    Call<ArrayDataModel> checkActivation(@Field("ID") int id, @Field("activation_key") String code);

    @POST("android/login/resetPass")
    @FormUrlEncoded
    Call<ArrayDataModel> resetPassword(@Field("ID") int ID, @Field("password") String password, @Field("activation_key ") String code);

    @POST("android/login/editProfile")
    @FormUrlEncoded
    Call<ObjectDataModel<User>> updateData(@Field("type") String type
            , @Field("ID") String id
            , @Field("Name") String name
            , @Field("mail") String mail
            , @Field("Mobile") String mobile);

    @POST("android/login/newPass")
    @FormUrlEncoded
    Call<ArrayDataModel> setNewPassword(@Field("old_password") String oldPW, @Field("password") String PW, @Field("ID") String id);

    @POST("android/student_message/get_library_emp")
    @FormUrlEncoded
    Call<ArrayDataModel<TeacherLibraryModel>> getTeacherLibrary(@Field("RowLevelID") String rowLevelID,
                                                                @Field("SubjectID") String subjectID,
                                                                @Field("TeacherID") String teacherID,
                                                                @Field("SemesterID") String semesterID);

    @GET("chatting/Api/listMessages")
    Call<RoomModel> getRoomData(@Query("id") String user_id);

    @GET("chatting/Api/listMessages")
    Call<RoomModel> getRoomDataFilterBy(@Query("id") String user_id,
                                        @Query("type") String type
    );

    @Multipart
    @POST("chatting/Api/sendChatMessage")
    Call<MessageModel> sendChatText(@Part("chat_id") RequestBody chat_id,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("to_user") RequestBody to_user,
                                    @Part("msg_text") RequestBody msg_text);

    @Multipart
    @POST("chatting/Api/sendChatMessage")
    Call<MessageModel> sendChatFile(@Part("chat_id") RequestBody chat_id,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("to_user") RequestBody to_user,
                                    @Part MultipartBody.Part file
    );

    @Multipart
    @POST("chatting/Api/sendChatMessage")
    Call<MessageModel> sendChatTextFile(@Part("chat_id") RequestBody chat_id,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("to_user") RequestBody to_user,
                                        @Part("msg_text") RequestBody msg_text,
                                        @Part MultipartBody.Part file
    );


    @Multipart
    @POST("chatting/Api/acceptAudioFile")
    Call<MessageModel> sendChatAudioFile(@Part("user_id") RequestBody user_id,
                                         @Part("to_user") RequestBody to_user,
                                         @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("chatting/Api/chat")
    Call<MessageDataModel> getChatMessage(@Field("user_id") String user_id,
                                          @Field("to_user") String to_user,
                                          @Field("conversation_id") String conversation_id,
                                          @Field("chat_id") String chat_id
    );

    @GET("chatting/Api/listCategory")
    Call<List<CategoryModel>> getCategory(@Query("type") String type);


    @FormUrlEncoded
    @POST("chatting/Api/getUsers")
    Call<List<UserCategoryModel>> getCategoryUsers(@Field("id") String user_id,
                                                   @Field("school_id") String school_id,
                                                   @Field("lang") String lang,
                                                   @Field("category") String category
    );


    @Multipart
    @POST("chatting/Api/sendMessage")
    Call<ResponseBody> sendFirstText(@Part("user_id") RequestBody user_id,
                                     @Part("school_id") RequestBody school_id,
                                     @Part("to_user[]") List<RequestBody> toUsers,
                                     @Part("category") RequestBody category,
                                     @Part("msg_text") RequestBody msg_text

    );

    @Multipart
    @POST("chatting/Api/sendMessage")
    Call<ResponseBody> sendFirstFile(@Part("user_id") RequestBody user_id,
                                     @Part("school_id") RequestBody school_id,
                                     @Part("to_user[]") List<RequestBody> toUsers,
                                     @Part("category") RequestBody category,
                                     @Part MultipartBody.Part file
    );

    @Multipart
    @POST("chatting/Api/sendMessage")
    Call<ResponseBody> sendFirstTextFile(@Part("user_id") RequestBody user_id,
                                         @Part("school_id") RequestBody school_id,
                                         @Part("to_user[]") List<RequestBody> toUsers,
                                         @Part("category") RequestBody category,
                                         @Part("msg_text") RequestBody msg_text,
                                         @Part MultipartBody.Part file
    );

}