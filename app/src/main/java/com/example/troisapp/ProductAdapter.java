package com.example.troisapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context mContext;
    private List<GetSetProduct> mGetSetProduct;

    public ProductAdapter(Context context, List<GetSetProduct> getSetProduct){
        this.mContext = context;
        this.mGetSetProduct = getSetProduct;
    }

    private ListItemClickListener mListener;

    public interface ListItemClickListener{
        public void onListItemClick(View v, int position);
    }

    public void setListener(ListItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        GetSetProduct itemsRV = mGetSetProduct.get(position);

//        Picasso.Builder builder = new Picasso.Builder(mContext);
//        builder.build().load(itemsRV.getPicture())
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(holder.mProdPict);

        holder.mProdName.setText(itemsRV.getProduct());
        holder.mPrice.setText(itemsRV.getPrice());
    }

    @Override
    public int getItemCount() {
        return mGetSetProduct.size();
    }

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
