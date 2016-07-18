package com.sina.crowclub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.view.widget.TypeSortPopupwindow;

import java.util.List;

/**
 * Created by wu on 2016/7/18.
 */
public class TypeSortAdapter extends BaseAdapter {
    private static final String TAG = TypeSortAdapter.class.getSimpleName();
    private List<String> mData;
    private Context mContext;
    private int mCheckedId; // 0是时间顺序 1是时间倒序
    private TypeSortPopupwindow.ItemPopupWindowListener mListener;

    public TypeSortAdapter(Context context, int checked,List<String> data
            ,TypeSortPopupwindow.ItemPopupWindowListener listener){
        mContext = context;
        mCheckedId = checked;
        mData = data;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_story_type_sort,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(mData.get(position));

        if(position == mCheckedId){
            viewHolder.name.setSelected(true);
            viewHolder.check.setVisibility(View.VISIBLE);
        }else{
            viewHolder.check.setVisibility(View.INVISIBLE);
        }
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemPopupWindowListener(v,position);
            }
        });
        return convertView;
    }

    class ViewHolder{
        private View item;
        private TextView name;
        private ImageView check;

        public ViewHolder(View view){
            item = view.findViewById(R.id.layout_type_sort);
            name = (TextView)view.findViewById(R.id.text_type_sort_name);
            check = (ImageView)view.findViewById(R.id.img_type_sort_checked);
        }
    }
}
