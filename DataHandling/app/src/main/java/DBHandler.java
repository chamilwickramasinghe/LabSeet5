import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.UserManager;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";
    private static final int factory = 1 ;

    public DBHandler(Context context){super(context, DATABASE_NAME, factory: null, version: 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE "+ UsersMaster.User.TABLE_NAME + "("+
                        UsersMaster.User._ID + "INTEGER PRIMARY KEY,"+
                        UsersMaster.User.COLUMN_NAME_USERNAME + "TEXT,"+
                        UsersMaster.User.COLUMN_NAME_PASSWORD + "TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public void addInfo(String usename, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.User.COLUMN_NAME_USERNAME, usename);
        values.put(UsersMaster.User.COLUMN_NAME_PASSWORD, password);

        long newRoeId = db.insert(UsersMaster.User.TABLE_NAME, null, values)

    }

    public List readAllInfo(){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.User._ID,
                UsersMaster.User.COLUMN_NAME_USERNAME,
                UsersMaster.User.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.User.COLUMN_NAME_USERNAME + "DESC";

        Cursor cursor = db.query(
                UsersMaster.User.TABLE_NAME,
                projection,
                selection:null,
                groupBy:null,
                having:null,
                sortOrder
        );

        List userName = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.User.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.User.COLUMN_NAME_PASSWORD));
            userName.add(username);
            passwords.add(password);
        }
        cursor.close();
        return userName;
    }

    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();

        String selection = UsersMaster.User.COLUMN_NAME_USERNAME + "LIKE";

        String[] selctionArgs = { userName };

        db.delete(UsersMaster.User.TABLE_NAME, selection, selctionArgs);
    }

    public void updateInfo(String userName, String password){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.User.COLUMN_NAME_PASSWORD, password);

        String selection = UsersMaster.User.COLUMN_NAME_USERNAME + "LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UsersMaster.User.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
