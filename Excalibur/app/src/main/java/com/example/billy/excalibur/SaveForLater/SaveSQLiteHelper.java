package com.example.billy.excalibur.SaveForLater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by petermartinez on 4/20/16.
 */
public class SaveSQLiteHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String TAG = SaveSQLiteHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SAVED_DB";
    public static final String ARTICLES_TABLE_NAME = "ARTICLES_DB";


    public static final String COL_ID = "_id";
    public static final String COL_HTML = "HTML";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_SNIPPET = "SNIPPET";
    public static final String COL_URL = "URL";
    public static final String COL_IMAGE = "IMAGE";
    public static final String COL_CODE = "CODE";


    public static final String[] ARTICLES_COLUMNS = {COL_ID, COL_HTML, COL_TITLE, COL_SNIPPET, COL_URL, COL_IMAGE, COL_CODE};

    private static SaveSQLiteHelper instance;


    private static final String CREATE_ARTICLES_TABLE =
            "CREATE TABLE " + ARTICLES_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_HTML + " TEXT, " +
                    COL_TITLE + " TEXT, " +
                    COL_SNIPPET + " TEXT, " +
                    COL_URL + " TEXT, " +
                    COL_IMAGE + " TEXT, " +
                    COL_CODE + " LONG) ";


    public static SaveSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SaveSQLiteHelper(context);
        }
        return instance;
    }

    public SaveSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ARTICLES_TABLE_NAME);
        this.onCreate(db);
    }





//    public static void saveArticleForLater(ArticleSaveForLater article) {
//        insertIntoDbFromArticle(article);
//    }

//SaveSQLiteHelper mDbHelper = SaveSQLiteHelper.getInstance(SaveSQLiteHelper.this);
//db = mDbHelper.getWritableDatabase();

//    public static long insertIntoDbFromArticle(ArticleSaveForLater article){
//
////        Cursor cursor = ThreadsSQLiteHelper.getInstance(MainActivity.this).searchThreads(query);
//
//
//        ContentValues values = new ContentValues();
//        values.put(SaveSQLiteHelper.COL_HTML, article.getHtml());
//        values.put(SaveSQLiteHelper.COL_TITLE, article.getTitle());
//        values.put(SaveSQLiteHelper.COL_URL, article.getUrl());
//        values.put(SaveSQLiteHelper.COL_IMAGE, article.getImage());
//        values.put(SaveSQLiteHelper.COL_CODE, article.getCode());
//
//        long newRowId = db.insert(SaveSQLiteHelper.ARTICLES_TABLE_NAME, null,values);
//        return newRowId;
//    }


        public Cursor getAllSavedArticles(){

            SQLiteDatabase db = this.getReadableDatabase();
            String[] savedArticleNoHtml = {COL_ID, COL_TITLE, COL_SNIPPET, COL_URL, COL_IMAGE, COL_CODE};

            Cursor cursor = db.query(ARTICLES_TABLE_NAME, // a. table
                    savedArticleNoHtml, // b. column names
                    null, // c. selections
                    null, // d. selections args
                    null, // e. group by
                    null, // f. having
                    COL_CODE, // g. order by
                    null); // h. limit
                    return cursor;
             }
}