package co.edu.unal.reto8;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.UserViewHolder>{

    private  List<Company> userItemDBList;
    private Context mContext;


    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView name, url, index;
        public LinearLayout editLayout, deleteLayout;

        public UserViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            url = (TextView) view.findViewById(R.id.url);
            index = (TextView) view.findViewById(R.id.index);
            editLayout = (LinearLayout) view.findViewById(R.id.edit_layout);
            deleteLayout = (LinearLayout) view.findViewById(R.id.delete_layout);
        }
    }


    public CompanyAdapter(List<Company> userItemDBList,Context context) {
        this.userItemDBList = userItemDBList;
        this.mContext = context;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_row, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        final Company user = userItemDBList.get(position);

        holder.name.setText(user.getName());
        holder.url.setText(user.getUrl());
        holder.index.setText(user.getIndex());

        holder.editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof CompanyListActivity) {
                    ((CompanyListActivity)mContext).presentAlert(user.getName(), user.getUrl(), user.getIndex());
                    notifyDataSetChanged();
                }
            }
        });

        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentAlertDelete(holder.getAdapterPosition(), user.getIndex());
            }
        });


    }

    public void updateUserList(List<Company> newlist) {
        userItemDBList.clear();
        userItemDBList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userItemDBList.size();
    }

    public void presentAlertDelete(final int position, final String index){

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("Eliminar empresa")
                .setMessage("Seguro desea eliminar esta empresa?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance(mContext).delete(index);
                        deleteUser(position);
                    }
                })
                .setNegativeButton("No", null)
                .create();
        dialog.show();

    }

    private void deleteUser(int position) {
        userItemDBList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,userItemDBList.size());

    }


}