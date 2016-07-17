package com.sina.crowclub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sina.crowclub.R;

import java.util.List;

/**
 * Created by wu on 2016/7/17.
 */
public class AbsBaseAdapter extends BaseAdapter {
    private static final String TAG = AbsBaseAdapter.class.getSimpleName();

    private Context mContext;
    private List<StoryBean> mData;

    public AbsBaseAdapter(Context context,List<StoryBean> data){
        mContext = context;
        mData = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        StoryBean bean = mData.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_view_sotry,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText("" + bean.id);
        viewHolder.name.setText(bean.name);
        viewHolder.time.setText("" + bean.create_time);
        return convertView;
    }

    class ViewHolder{
        private TextView id;
        private TextView name;
        private TextView time;
        public ViewHolder(View view){
            id = (TextView)view.findViewById(R.id.text_story_id);
            name = (TextView)view.findViewById(R.id.text_story_name);
            time = (TextView)view.findViewById(R.id.text_story_create_time);
        }
    }
}
