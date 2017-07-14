package co.ginx.mt.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.ginx.mt.Model.RekeningDto;
import co.ginx.mt.Model.TransferDto;

/**
 * Created by user-02 on 05/07/17.
 */

public class MtDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "mt_database";
    private static final int DATABASE_VERSION = 1;

    /*
    * daftar table
    * */
    private static final String TblMTransfer = "TblMTransfer";
    private static final String TblMTransfer_id = "id";
    private static final String TblMTransfer_pengirim = "pengirim";
    private static final String TblMTransfer_penerima = "penerima";
    private static final String TblMTransfer_jumlah_uang = "jumlah_uang";
    private static final String TblMTransfer_tanggal = "tanggal";

    private static final String TblMRekening = "TblMRekening";
    private static final String TblMRekening_id = "id";
    private static final String TblMRekening_no_rekening = "no_rekening";
    private static final String TblMRekening_nama_rekening = "nama_rekening";

    private SQLiteDatabase mDatabase;

    String createTblMTransfer = "CREATE TABLE "+TblMTransfer+
            " (id INTEGER PRIMARY KEY, pengirim INTEGER, penerima INTEGER, jumlah_uang REAL, tanggal DATETIME DEFAULT CURRENT_TIMESTAMP)";

    String createTblMRekening = "CREATE TABLE "+TblMRekening+
            " (id INTEGER PRIMARY KEY, no_rekening TEXT UNIQUE, nama_rekening TEXT)";

    public MtDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTblMTransfer);
        db.execSQL(createTblMRekening);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TblMTransfer);
        db.execSQL("DROP TABLE IF EXISTS " + TblMRekening);
        onCreate(db);
    }

    public long insertRekening(RekeningDto dto){
        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TblMRekening_no_rekening, dto.no_rekening);
        values.put(TblMRekening_nama_rekening, dto.nama_rekening);
        return mDatabase.insert(TblMRekening,null,values);
    }

    public long insertTransfer(TransferDto dto){
        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TblMTransfer_penerima, dto.penerima);
        values.put(TblMTransfer_pengirim, dto.pengirim);
        values.put(TblMTransfer_jumlah_uang, dto.jumlah);
        return mDatabase.insert(TblMTransfer,null,values);
    }

    public Cursor getDataTransfer(){
        mDatabase = this.getReadableDatabase();
        return mDatabase.query(TblMTransfer,null,null,null,null,null,null);
    }

    public Cursor getDataRekening(){
        mDatabase = this.getReadableDatabase();
        return mDatabase.query(TblMRekening,null,null,null,null,null,null);
    }
}
