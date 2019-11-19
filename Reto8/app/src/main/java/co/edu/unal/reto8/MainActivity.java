package co.edu.unal.reto8;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import co.edu.unal.reto8.Company;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.this.getClass().getSimpleName();
    EditText enterIndex, enterName, enterUrl, enterTelephone, enterEmail, enterProducts, enterServices, enterType;
    Button btnSubmit, btnViewUsers;
    List<Company> userItemDBList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterIndex = findViewById(R.id.txt_index);
        enterName = findViewById(R.id.txt_name);
        enterUrl = findViewById(R.id.txt_url);
        enterTelephone = findViewById(R.id.txt_telephone);
        enterEmail = findViewById(R.id.txt_email);
        enterProducts = findViewById(R.id.txt_products);
        enterServices = findViewById(R.id.txt_services);
        enterType = findViewById(R.id.txt_type);

        btnSubmit = findViewById(R.id.btn_submit);
        btnViewUsers = findViewById(R.id.btn_view_list);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompany();
            }
        });

        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompanyListActivity.class);
                startActivity(intent);
            }
        });
    }


    private void addCompany(){
        String name = enterName.getText().toString().trim();
        String index = enterIndex.getText().toString().trim();
        String url = enterUrl.getText().toString().trim();
        String telephone = enterUrl.getText().toString().trim();
        String email = enterUrl.getText().toString().trim();
        String products = enterUrl.getText().toString().trim();
        String services = enterUrl.getText().toString().trim();
        String type = enterUrl.getText().toString().trim();

        if((name != null && !name.isEmpty())&& (index != null && !index.isEmpty()) && (url != null && !url.isEmpty()) && (telephone != null && !telephone.isEmpty()
                && (email != null && !email.isEmpty()) && (products != null && !products.isEmpty()) && (services != null && !services.isEmpty())
                && (type != null && !type.isEmpty()) )){

            Company companyDb = new Company();
            companyDb.setIndex(index);
            companyDb.setName(name);
            companyDb.setUrl(url);
            companyDb.setTelephone(telephone);
            companyDb.setEmail(email);
            companyDb.setProducts(products);
            companyDb.setServices(services);
            companyDb.setType(type);

            addCompanyToDB(companyDb);
            userItemDBList =  DatabaseManager.getInstance(getApplicationContext()).getAll();
            for(Company itemDB: userItemDBList){
                Log.i(TAG,"name: "+itemDB.getName());
            }
        }
    }

    public int addCompanyToDB(Company companyDb){
        int isSuccess;
        isSuccess = DatabaseManager.getInstance(getApplicationContext()).insert(companyDb,false);
        if(isSuccess == 0){
            Toast.makeText(getApplicationContext(),"Save Company",Toast.LENGTH_SHORT).show();
        }else if(isSuccess == 1){
            Toast.makeText(getApplicationContext(),"Company with this id exist",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Company adding failed",Toast.LENGTH_SHORT).show();
        }
        return isSuccess;
    }

    public void presentToast(){
        Toast.makeText(getApplicationContext(),"Save Company",Toast.LENGTH_SHORT).show();
    }

    public List<Company> getAllUsers(){
        return  DatabaseManager.getInstance(getApplicationContext()).getAll();
    }

}
