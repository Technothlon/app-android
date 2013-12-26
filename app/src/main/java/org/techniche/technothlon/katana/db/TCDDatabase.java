package org.techniche.technothlon.katana.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kAd on 26/12/13.
 * Part of org.techniche.technothlon.katana.db
 */
public class TCDDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "katana.db";

    public TCDDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void  reset(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(Contracts.SQL_DELETE_STRING);
        db.execSQL(Contracts.SQL_CREATE_STRING);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contracts.SQL_CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contracts.SQL_DELETE_STRING);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(int id, String display_id, String color, String title, String question,
                       String facebook, String google, String tumblr, String ans_url,
                       String by, String time, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.FIELD_ID, id);
        values.put(Contracts.FIELD_DISPLAY_ID, display_id);
        values.put(Contracts.FIELD_COLOR, color);
        values.put(Contracts.FIELD_TITLE, title);
        values.put(Contracts.FIELD_QUESTION, question);
        values.put(Contracts.FIELD_FACEBOOK, facebook);
        values.put(Contracts.FIELD_GOOGLE, google);
        values.put(Contracts.FIELD_TUMBLR, tumblr);
        values.put(Contracts.FIELD_ANSWER_URL, ans_url);
        values.put(Contracts.FIELD_BY, by);
        values.put(Contracts.FIELD_TIME, time);
        values.put(Contracts.FIELD_ANSWER, answer);
        assert db != null;
        db.insert(Contracts.NAME, null, values);
        return 0;
    }

    public static final class Contracts implements BaseColumns {
        public static final String NAME = "technocoupdoeil",
                FIELD_ID = "uniqueId",
                FIELD_DISPLAY_ID = "displayId",
                FIELD_COLOR = "color",
                FIELD_TITLE = "title",
                FIELD_QUESTION = "question",
                FIELD_FACEBOOK = "facebook",
                FIELD_GOOGLE = "google",
                FIELD_TUMBLR = "tumblr",
                FIELD_ANSWER_URL = "answerurl",
                FIELD_BY = "by",
                FIELD_TIME = "post_time",
                FIELD_ANSWER = "answer";
        private static final String COMMA = ", ";
        private static final String TEXT = " TEXT";
        private static final String SQL_CREATE_STRING = "CREATE TABLE " + NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                FIELD_ID + TEXT + COMMA +
                FIELD_DISPLAY_ID + TEXT + COMMA +
                FIELD_COLOR + TEXT + COMMA +
                FIELD_TITLE + TEXT + COMMA +
                FIELD_QUESTION + TEXT + COMMA +
                FIELD_FACEBOOK + TEXT + COMMA +
                FIELD_GOOGLE + TEXT + COMMA +
                FIELD_TUMBLR + TEXT + COMMA +
                FIELD_ANSWER_URL + TEXT + COMMA +
                FIELD_BY + TEXT + COMMA +
                FIELD_TIME + " DATETIME" + COMMA +
                FIELD_ANSWER + ")";
        private static final String SQL_DELETE_STRING = "DROP TABLE IF EXISTS " + NAME;

        public Contracts() {
        }
    }
}
