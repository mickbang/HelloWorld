package com.lq.helloworld;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;


import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ImageExifUtils {
    public static String EXIF_TIME_FORMAT = "yyyy:MM:dd HH:mm:ss Z";

    public static String getExifTimeFormatNowApi() {
        return TimeUtil.format(new Date(), TimeUtil.FORMAT_TIME_API);
    }

    /**
     * 将传递给后台的时间格式转换为exif的时间格式
     *
     * @param time
     * @return
     */
    public static String formatExifTime(String time) {
        String result = "";
        try {
            Date format = new SimpleDateFormat(TimeUtil.FORMAT_TIME_API).parse(time);
            result = TimeUtil.format(format, EXIF_TIME_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void writeImageExif(String path, ImageExif imageExif) {
        try {
            File file = new File(path);
            if (FileUtils.isFileExists(file)) {
                ExifInterface exif = new ExifInterface(path);
                String tagLat = exif
                        .getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String tagLon = exif
                        .getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String createTime = exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);

//                if (tagLat == null && tagLon == null) {// 无经纬度信息
                    if (imageExif.getLatitude() != 0 && imageExif.getLongitude() != 0) {
                        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
                                decimalToDMS(Math.abs(imageExif.getLatitude())));
                        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF,
                                imageExif.getLatitude() > 0 ? "N" : "S"); // 区分南北半球
                        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
                                decimalToDMS(Math.abs(imageExif.getLongitude())));
                        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF,
                                imageExif.getLongitude() > 0 ? "E" : "W"); // 区分东经西经

                        exif.saveAttributes();
                    }
//                }

                if (TextUtils.isEmpty(createTime)) {
                    if (!TextUtils.isEmpty(imageExif.getCreateTime())) {
                        String time = formatExifTime(imageExif.getCreateTime());
                        exif.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, time);
                        exif.saveAttributes();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ImageExif getImageExif(String path) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = getImageExifInterface(path);
            double latitude = 0;
            double longitude = 0;
            if (exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null
                    && exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF) != null
                    && exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null
                    && exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null) {
                latitude = convertRationalLatLonToFloat(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE), exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
                longitude = convertRationalLatLonToFloat(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE), exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
            }
            String createTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);
            String timezone = exifInterface.getAttribute(ExifInterface.TAG_OFFSET_TIME_ORIGINAL);
            if (TextUtils.isEmpty(timezone)) {
                timezone = TimeZone.getDefault().getDisplayName()+"";
            }
            createTime = createTime +" "+ timezone;
            try {
                if (!TextUtils.isEmpty(createTime)) {
                    SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(EXIF_TIME_FORMAT);
                    Date date = simpleDateFormat.parse(createTime);
                    createTime = TimeUtil.formatApi(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new ImageExif(createTime, latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageExif getImageExif(Context context, Uri uri) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                InputStream inputStream = null;
                inputStream = context.getContentResolver().openInputStream(uri);
                ExifInterface exifInterface = getImageExifInterface(inputStream);
                double latitude = 0;
                double longitude = 0;
                if (exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null
                        && exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF) != null
                        && exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null
                        && exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null) {
                    latitude = convertRationalLatLonToFloat(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE), exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
                    longitude = convertRationalLatLonToFloat(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE), exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
                }
                String createTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);
                String timezone = exifInterface.getAttribute(ExifInterface.TAG_OFFSET_TIME_ORIGINAL);
                if (TextUtils.isEmpty(timezone)) {
                    timezone = TimeZone.getDefault().getDisplayName()+"";
                }
                createTime = createTime +" "+ timezone;
                inputStream.close();
                try {
                    if (!TextUtils.isEmpty(createTime)) {
                        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(EXIF_TIME_FORMAT);
                        Date date = simpleDateFormat.parse(createTime);
                        createTime = TimeUtil.formatApi(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return new ImageExif(createTime, latitude, longitude);
            } else {
                String path = getFilePathByUri(context, uri);
                return getImageExif(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static double convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return -result;
        }
        return result;
    }

    public static ExifInterface getImageExifInterface(String path) throws IOException {
        return new ExifInterface(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ExifInterface getImageExifInterface(InputStream inputStream) throws IOException {
        return new ExifInterface(inputStream);
    }


    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
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
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        } else {
            // 以 file:// 开头的
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
                path = uri.getPath();
                return path;
            }
            // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        if (columnIndex > -1) {
                            path = cursor.getString(columnIndex);
                        }
                    }
                    cursor.close();
                }
                return path;
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * 浮点型经纬度值转成度分秒格式
     *
     * @param coord
     * @return
     */
    public static String decimalToDMS(double coord) {
        String output, degrees, minutes, seconds;

        // gets the modulus the coordinate divided by one (MOD1).
        // in other words gets all the numbers after the decimal point.
        // e.g. mod := -79.982195 % 1 == 0.982195
        //
        // next get the integer part of the coord. On other words the whole
        // number part.
        // e.g. intPart := -79

        double mod = coord % 1;
        int intPart = (int) coord;

        // set degrees to the value of intPart
        // e.g. degrees := "-79"

        degrees = String.valueOf(intPart);

        // next times the MOD1 of degrees by 60 so we can find the integer part
        // for minutes.
        // get the MOD1 of the new coord to find the numbers after the decimal
        // point.
        // e.g. coord := 0.982195 * 60 == 58.9317
        // mod := 58.9317 % 1 == 0.9317
        //
        // next get the value of the integer part of the coord.
        // e.g. intPart := 58

        coord = mod * 60;
        mod = coord % 1;
        intPart = (int) coord;
        if (intPart < 0) {
            // Convert number to positive if it's negative.
            intPart *= -1;
        }

        // set minutes to the value of intPart.
        // e.g. minutes = "58"
        minutes = String.valueOf(intPart);

        // do the same again for minutes
        // e.g. coord := 0.9317 * 60 == 55.902
        // e.g. intPart := 55
        coord = mod * 60;
        intPart = (int) coord;
        if (intPart < 0) {
            // Convert number to positive if it's negative.
            intPart *= -1;
        }

        // set seconds to the value of intPart.
        // e.g. seconds = "55"
        seconds = String.valueOf(intPart);

        // I used this format for android but you can change it
        // to return in whatever format you like
        // e.g. output = "-79/1,58/1,56/1"
        output = degrees + "/1," + minutes + "/1," + seconds + "/1";

        // Standard output of D°M′S″
        // output = degrees + "°" + minutes + "'" + seconds + "\"";

        return output;
    }
}
