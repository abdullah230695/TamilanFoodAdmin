package com.shalla.tamilanfoodadmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.shalla.tamilanfoodadmin.Models.DB_TO_RECYCLERVIEW;
import com.shalla.tamilanfoodadmin.R;


public class DBAdapter_TO_RecylerView extends FirestoreRecyclerAdapter<DB_TO_RECYCLERVIEW, DBAdapter_TO_RecylerView.viewHolder> {


    public DBAdapter_TO_RecylerView(@NonNull FirestoreRecyclerOptions<DB_TO_RECYCLERVIEW> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, final int position, @NonNull final DB_TO_RECYCLERVIEW model) {
        holder.Title.setText(model.getTitle());
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_food_list_inflate, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView Title;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.tvIngredients);
        }

    }
}
