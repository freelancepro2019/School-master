
package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RevisionLibrary {

    @SerializedName("ContactID")
    private String mContactID;
    @SerializedName("ContactName")
    private String mContactName;
    @SerializedName("Downloads")
    private String mDownloads;
    @SerializedName("FileDate")
    private String mFileDate;
    @SerializedName("FileID")
    private String mFileID;
    @SerializedName("FileTitle")
    private String mFileTitle;
    @SerializedName("FileType")
    private String mFileType;
    @SerializedName("FileUrl")
    private String mFileUrl;
    @SerializedName("link_youtube")
    private String mLinkYoutube;
    @SerializedName("SchoolID")
    private String mSchoolID;
    @SerializedName("SchoolName")
    private String mSchoolName;
    @SerializedName("SubjectName")
    private String mSubjectName;

    public String getContactID() {
        return mContactID;
    }

    public void setContactID(String contactID) {
        mContactID = contactID;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String contactName) {
        mContactName = contactName;
    }

    public String getDownloads() {
        return mDownloads;
    }

    public void setDownloads(String downloads) {
        mDownloads = downloads;
    }

    public String getFileDate() {
        return mFileDate;
    }

    public void setFileDate(String fileDate) {
        mFileDate = fileDate;
    }

    public String getFileID() {
        return mFileID;
    }

    public void setFileID(String fileID) {
        mFileID = fileID;
    }

    public String getFileTitle() {
        return mFileTitle;
    }

    public void setFileTitle(String fileTitle) {
        mFileTitle = fileTitle;
    }

    public String getFileType() {
        return mFileType;
    }

    public void setFileType(String fileType) {
        mFileType = fileType;
    }

    public String getFileUrl() {
        return mFileUrl;
    }

    public void setFileUrl(String fileUrl) {
        mFileUrl = fileUrl;
    }

    public String getLinkYoutube() {
        return mLinkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        mLinkYoutube = linkYoutube;
    }

    public String getSchoolID() {
        return mSchoolID;
    }

    public void setSchoolID(String schoolID) {
        mSchoolID = schoolID;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String subjectName) {
        mSubjectName = subjectName;
    }

}
