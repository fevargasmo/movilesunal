package co.edu.unal.reto8;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "movil";
    private static final int DATABASE_VERSION = 1;

    private Dao<Company, Long> companyDBS;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Company.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(checkTableExist(database,"Company"))
                TableUtils.dropTable(connectionSource,Company.class,false);

            onCreate(database,connectionSource);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkTableExist(SQLiteDatabase database, String tableName){
        Cursor c = null;
        boolean tableExist = false;
        try {
            c = database.query(tableName, null,null,null,null,null,null);
            tableExist = true;
        }catch (Exception e){

        }
        return tableExist;
    }

    public Dao<Company, Long> getUserItemDao() throws SQLException{
        if(companyDBS == null){
            companyDBS = getDao(Company.class);
        }
        return companyDBS;
    }

    @Override
    public void close() {
        companyDBS = null;
        super.close();
    }

    public void clearTable() throws SQLException{
        TableUtils.clearTable(getConnectionSource(),Company.class);
    }
}