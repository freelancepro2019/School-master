package com.softray_solutions.newschoolproject.ui.fragments.revisions;

class RevisionLibraryInfoPresenter {
    private RevisionLibraryInfoView revisionLibraryInfoView;

    RevisionLibraryInfoPresenter(RevisionLibraryInfoView revisionLibraryInfoView) {
        this.revisionLibraryInfoView = revisionLibraryInfoView;
    }

    void passData(String name, String revisionName, String url) {
        revisionLibraryInfoView.setTeacherName(name);
        revisionLibraryInfoView.setSubjectName(revisionName);
        revisionLibraryInfoView.setDownloadUrl(url);
    }

    void passTeacherData(String revisionName, String downloadUrl, String subjectName, String youtubeLink) {
        revisionLibraryInfoView.setTeacherName(subjectName);
        revisionLibraryInfoView.setSubjectName(revisionName);
        revisionLibraryInfoView.setDownloadUrl(downloadUrl + "\n" + youtubeLink);
    }
}
