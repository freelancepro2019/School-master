package com.softray_solutions.newschoolproject.data.network.service;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.softray_solutions.newschoolproject.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.softray_solutions.newschoolproject.BaseApp.schoolNameVariable;

public class Common {

    public static final String STUDENT ="S";
    public static final String TEACHER ="E";
    public static final String FATHER ="F";
    public static final String ADMIN ="U";


    public static MyInterface getMyInterface() {
        String BASE_URL;
        switch (schoolNameVariable) {
            case "مدارس بن رشد":
                BASE_URL = "https://rushd.esol.com.sa/index.php/";
                break;
            case "مدارس بيت القيم":
                BASE_URL = "https://beetelqiam.esol.com.sa/index.php/";
                break;
            case "مدارس زيد":
                BASE_URL = "https://zaid.esol.com.sa/index.php/";
                break;
            case "مدارس الأندلس":
                BASE_URL = "https://andalus.esol.com.sa/index.php/";
                break;
            case "مدارس ابها":
                BASE_URL = "https://abhais.com/index.php/";
                break;
            case "مدارس عهد":
                BASE_URL = "https://ahadschools.com/index.php/";
                break;
            default:
                BASE_URL = "https://mnhl.esol.com.sa/index.php/";
        }

        return RetrofitClient.getClient(BASE_URL).create(MyInterface.class);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePath(Context context, Uri uri) {
        int currentApiVersion;
        try {
            currentApiVersion = Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            //API 3 will crash if SDK_INT is called
            currentApiVersion = 3;
        }
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {


            if (DocumentsContract.isDocumentUri(context, uri)) {

                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        } else if (currentApiVersion <= Build.VERSION_CODES.HONEYCOMB_MR2 && currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            String[] proj = {MediaStore.Images.Media.DATA};
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(
                    context,
                    uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
            return result;
        } else {

            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index
                    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    private static File getFileFromImagePath(String path) {
        File file = new File(path);
        return file;
    }

    public static MultipartBody.Part getMultiPart(Context context, Uri uri, String partName) {
        File file = getFileFromImagePath(getImagePath(context, uri));
        RequestBody requestBody = getRequestBodyImage(file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(partName, System.currentTimeMillis()+".png", requestBody);
        return part;

    }

    public static MultipartBody.Part getMultiPartFile(Context context, String path, String partName) {
        File file = getFileFromImagePath(path);
        String mim_type = file.getAbsolutePath().substring(file.getAbsoluteFile().toString().lastIndexOf("."));
        RequestBody requestBody = getRequestBodyFile(file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(partName, System.currentTimeMillis() + mim_type, requestBody);
        return part;

    }

    private static RequestBody getRequestBodyImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        return requestBody;
    }

    private static RequestBody getRequestBodyFile(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        return requestBody;
    }

    public static RequestBody getRequestBodyText(String data) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), data);
        return requestBody;
    }

    public static ProgressDialog createProgressDialog(Context context, String msg) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        ProgressBar bar = new ProgressBar(context);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        return dialog;

    }

}