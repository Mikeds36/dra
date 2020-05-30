package xyz.bcfriends.dra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "records.db";
    public static final String TABLE_NAME = "Records";
    public static final String COL_1 = "date";
    public static final String COL_2 = "timestamp";
    public static final String COL_3 = "depressStatus";
    public static final String COL_4 = "note";

    public static DataBaseHelper getInstance(Context context) {
        return new DataBaseHelper(context);
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /*
    Table Name: Records
        date DEFAULT date(now, localtime),
        datetime DEFAULT datetime(now, localtime),
        depressStatus INT(1)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" +
                "date DEFAULT (date('now', 'localtime')) PRIMARY KEY, " +
                "datetime DEFAULT (datetime('now', 'localtime'))," +
                "depressStatus int(1) " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    void insertDepressStatus(LocalDate date, Integer depressStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, String.valueOf(date));
        contentValues.put(COL_3, String.valueOf(depressStatus));

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    boolean insertDepressStatus(Integer depressStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        contentValues.put(COL_3, String.valueOf(depressStatus));

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    // Get Data from records
    public Cursor getData(LocalDate startDate, LocalDate endDate) {
        SQLiteDatabase db = this.getReadableDatabase();

//        Log.d("Date Debug", String.valueOf(startDate));
//        Log.d("Date Debug", String.valueOf(endDate));
        // yyyy-MM-dd > yyyy-MM-dd

        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE date BETWEEN strftime('" + startDate + "', 'now') AND strftime('" + endDate + "', 'now');", null);
    }

    public boolean updateNote(LocalDate date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        contentValues.put(COL_4, note);
        db.update(TABLE_NAME, contentValues, "date = ?", new String[] { dateFormat.format(date) } );

        return true;
    }

    public int deleteNote(LocalDate date) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        return db.delete(TABLE_NAME, "date = ?",  new String[] { dateFormat.format(date) } );
    }

}
