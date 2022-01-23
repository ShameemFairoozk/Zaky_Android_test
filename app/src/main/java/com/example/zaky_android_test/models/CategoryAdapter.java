package com.example.zaky_android_test.models;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zaky_android_test.R;

import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VHolder> {


    ArrayList<CategoryGS> CategoryGSs;
    Context context;

    public CategoryAdapter(ArrayList<CategoryGS> CategoryGSs, Context context) {
        this.CategoryGSs = CategoryGSs;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);



        View view = null;

        view =layoutInflater.inflate(R.layout.category_adapter,parent,false);

        return  new VHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        final CategoryGS s= CategoryGSs.get(position);
        holder.CategoryName.setText(s.getCategoryName());

            if(s.getIsSelected().equals("True")){
                holder.layout.setBackgroundResource(R.drawable.background_unselected);
            }else {
                holder.layout.setBackgroundResource(R.drawable.background_nothing);
            }





    }

    @Override
    public int getItemCount() {
        return CategoryGSs.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{


        TextView CategoryName;
        RelativeLayout layout;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName=itemView.findViewById(R.id.categoryName);
            layout=itemView.findViewById(R.id.layout);

        }
    }
    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private CategoryAdapter.RecyclerItemClickListener.OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, CategoryAdapter.RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
