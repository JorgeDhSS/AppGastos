package com.example.appgastos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appgastos.R;

public class SpinnerOptionAdapter extends ArrayAdapter{

    String[] spinnerTitles;
    int[] spinnerImages;
    Context mContext;

    @SuppressLint("ResourceType")
    public SpinnerOptionAdapter(@NonNull Context context, String[] titles, int[] images) {
        super(context,R.layout.row_spinner);
        this.spinnerTitles = titles;
        this.spinnerImages = images;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return spinnerTitles.length;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_spinner,
                    parent,
                    false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.iconSpinner);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.nameSpinner);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mFlag.setImageResource(spinnerImages[position]);
        mViewHolder.mName.setText(spinnerTitles[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView mFlag;
        TextView mName;
    }

}
