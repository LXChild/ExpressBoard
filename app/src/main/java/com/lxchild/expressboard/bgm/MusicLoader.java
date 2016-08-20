package com.lxchild.expressboard.bgm;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/5.
 */
public class MusicLoader {

    private static ArrayList<MusicInfo> musicInfoList = new ArrayList<>();
    private static MusicLoader musicLoader;
    private static ContentResolver contentResolver;
    private final String TAG = MusicLoader.class.getSimpleName();
    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };
    // private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 ";
    private String sortOrder = MediaStore.Audio.Media.DATA;

    private MusicLoader() {
        //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, sortOrder);
        if (cursor == null) {
            Log.v(TAG, "Music Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            Log.v(TAG, "Music Loader cursor.moveToFirst() returns false.");
        } else {
            int displayNameCol = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int idCol = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int sizeCol = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int urlCol = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicInfo musicInfo = new MusicInfo(id, title);
                musicInfo.setAlbum(album);
                musicInfo.setDuration(duration);
                musicInfo.setSize(size);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicInfoList.add(musicInfo);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public static MusicLoader instance(ContentResolver pContentResolver) {
        if (musicLoader == null) {
            contentResolver = pContentResolver;
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    public ArrayList<MusicInfo> getmusicInfoList() {
        return musicInfoList;
    }

    public Uri getMusicUriById(long id) {
        return ContentUris.withAppendedId(contentUri, id);
    }
}
