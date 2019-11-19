package co.edu.unal.reto8;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import co.edu.unal.reto8.Company;

import java.sql.SQLException;
import java.util.List;

class DatabaseManager {

    private final String TAG = DatabaseManager.this.getClass().getSimpleName();
    private final Context mContext;
    private static DatabaseManager INSTANCE;
    private DataBaseHelper databaseHelper;

    private Dao<Company, Long> userItemDao;
    private static String INDEX = "index";
    private static String NAME = "name";
    private static String URL = "url";
    private static String ID = "id";
    private static String EMAIL = "email";
    private static String PRODUCTS = "products";
    private static String SERVICES = "services";
    private static String TYPE = "type";


    public DatabaseManager(Context mContext) {
        Log.i(TAG, "DatabaseManager");
        this.mContext = mContext;
        databaseHelper = OpenHelperManager.getHelper(mContext, DataBaseHelper.class);

        try {
            userItemDao = databaseHelper.getUserItemDao();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new DatabaseManager(context);
        return INSTANCE;
    }

    public void releaseDB(){
        if (databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
            INSTANCE = null;
        }
    }

    public int clearAllData(){
        try {
            if (databaseHelper == null) return -1;
            databaseHelper.clearTable();
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isCompanyExisting(String index){
        QueryBuilder queryBuilder = userItemDao.queryBuilder();
        boolean flag = false;
        try {
            if(queryBuilder.where().eq(INDEX,index).countOf()>0){

            flag = true;
        }else {
            flag = false;
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
        return flag;
    }


    public int insert(Company companyDb, boolean isEdit){
        int count = 0;
        try {
            UpdateBuilder updateBuilder = userItemDao.updateBuilder();
            String index = companyDb.getIndex() != null ? companyDb.getIndex() : "";
            String name = companyDb.getName() != null ? companyDb.getName(): "";
            String url = companyDb.getUrl() != null ? companyDb.getUrl(): "";
            String telephone = companyDb.getTelephone() != null ? companyDb.getTelephone(): "";
            String email = companyDb.getEmail() != null ? companyDb.getEmail(): "";
            String products = companyDb.getProducts() != null ? companyDb.getProducts(): "";
            String services = companyDb.getServices() != null ? companyDb.getServices(): "";
            String type = companyDb.getType() != null ? companyDb.getType(): "";


            if(userItemDao == null) return -1;

            if(isCompanyExisting(index)){
                Log.i(TAG,"this user exist");
                count = 1;
                if(isEdit){
                    delete(index);
                    userItemDao.create(companyDb);
                }
            }else {
                count = 0;
                userItemDao.create(companyDb);
            }
            return count;
        }catch (SQLException e){
            e.printStackTrace();
            return  -1;
        }
    }

    public int delete(String index){
        try {
            if(userItemDao == null) return -1;
            DeleteBuilder deleteBuilder = userItemDao.deleteBuilder();
            if(index != null || !index.isEmpty()) deleteBuilder.where().eq(INDEX,index);
            deleteBuilder.delete();
            Log.i(TAG,"company deleted");
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return  -1;
        }
    }

    public List<Company> getAll(){
        try {
            if (userItemDao == null)return null;
            return userItemDao.queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}