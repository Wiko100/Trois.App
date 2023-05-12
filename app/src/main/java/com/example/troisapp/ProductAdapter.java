package com.example.troisapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends FirebaseRecyclerAdapter<GetSetProduct, ProductAdapter.ViewHolder> {
    private ListItemClickListener mListener;

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<GetSetProduct> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position, @NonNull GetSetProduct model) {
        Glide.with(holder.mProdPict.getContext())
                .load(model.getPicture())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mProdPict);

        holder.mProdName.setText(model.getProduct());
        holder.mPrice.setText(model.getPrice());
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);

        return new ViewHolder(view);
    }

    public interface ListItemClickListener{
        public void onListItemClick(View v, int position);
    }

    public void setListener(ListItemClickListener listener){
        this.mListener = listener;
    }

//    @Override
//    public int getItemCount() {
//        return mGetSetProduct.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mProdPict;
        private TextView mProdName;
        private TextView mPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProdPict = itemView.findViewById(R.id.list_prod_pict);
            mProdName = itemView.findViewById(R.id.list_prod_name);
            mPrice = itemView.findViewById(R.id.list_prod_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}
