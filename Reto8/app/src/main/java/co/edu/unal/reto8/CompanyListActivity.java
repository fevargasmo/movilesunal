package co.edu.unal.reto8;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import co.edu.unal.reto8.Company;
import co.edu.unal.reto8.DatabaseManager;
import co.edu.unal.reto8.CompanyAdapter;

import java.util.ArrayList;
import java.util.List;

public class CompanyListActivity extends AppCompatActivity {

    List<Company> userItemDBList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    private CompanyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        userItemDBList = getAllCompany();
        mAdapter = new CompanyAdapter(userItemDBList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
    }

    public void presentAlert(String name, String url, final String index){
        final EditText nameEditText = new EditText(this);
        final EditText urlEditText = new EditText(this);
        final EditText telephoneEditText = new EditText(this);
        final EditText emailEditText = new EditText(this);
        final EditText productsEditText = new EditText(this);
        final EditText servicesEditText = new EditText(this);
        final EditText typeEditText = new EditText(this);

        nameEditText.setText(name);
        urlEditText.setText(url);

        nameEditText.setWidth(100);
        urlEditText.setWidth(100);
        telephoneEditText.setWidth(100);
        emailEditText.setWidth(100);
        productsEditText.setWidth(100);
        servicesEditText.setWidth(100);
        typeEditText.setWidth(100);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(nameEditText);
        linearLayout.addView(urlEditText);
        linearLayout.addView(telephoneEditText);
        linearLayout.addView(emailEditText);
        linearLayout.addView(productsEditText);
        linearLayout.addView(servicesEditText);
        linearLayout.addView(typeEditText);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit Company")
                .setView(linearLayout)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Company userItemDB = new Company();
                        userItemDB.setName(nameEditText.getText().toString());
                        userItemDB.setUrl(urlEditText.getText().toString());
                        userItemDB.setIndex(index);
                        DatabaseManager.getInstance(getApplicationContext()).insert(userItemDB,true);
                        //mAdapter.notifyDataSetChanged();
                        mAdapter.updateUserList(getAllCompany());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }



    private void deleteUser(int index) {
    }


    public List<Company> getAllCompany(){
        return  DatabaseManager.getInstance(getApplicationContext()).getAll();
    }

}