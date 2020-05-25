package com.example.b10730218_hw2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b10730218_hw2.data.WaitlistContract;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public MyAdapter(Context context, Cursor cursor){
        this.mContext = context;
        this.mCursor = cursor;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //not same
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_item_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        String num = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));

        holder.txtItem.setText(name);
        holder.txtNum.setText(num);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null)
            mCursor.close();
        mCursor = newCursor;
        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtItem;
        private TextView txtNum;

        ViewHolder(View itemView) {
            super(itemView);
            txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            txtNum = (TextView) itemView.findViewById(R.id.txtNum);
        }
    }
//    public void setColor(String color){
//        @ColorInt
//        int shapeColor;
//
//        if (color.equals("blue")) {
//            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeBlue);
//        } else if (color.equals("green")) {
//            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeGreen);
//        } else {
//            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeRed);
//        }
//        mContext.shapeColor
//    }
}
