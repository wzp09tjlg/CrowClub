package com.sina.crowclub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;

import java.util.List;

/**
 * Created by wu on 2016/8/8.
 */
public class AlbumAdapter extends BaseAdapter {
    private static final String TAG = "AlbumAdapter";
    private List<StoryBean> mData;
    private Context mContext;

    private int holdPosition;//触发点位置
    private boolean isHide; //
    private boolean isEndOperShow = false;

    public void setEndOperShow(boolean endOperShow) {
        isEndOperShow = endOperShow;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean show) {
        isHide = show;
    }

    public AlbumAdapter(Context context, List<StoryBean> data){
        mContext = context;
        mData = data;
    }

    public void addData(List<StoryBean> data){
        if(mData == null)
            mData = data;
        else
            mData.addAll(data);
        notifyDataSetChanged();
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
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_album_edit,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setVisibility(View.VISIBLE);

        viewHolder.textName.setText(mData.get(position).name);

        if(position == holdPosition && isHide && !isEndOperShow){
            viewHolder.textName.setText("");
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
        }

        viewHolder.viewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext," onClick ",Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"delete",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    public void exchange(int start,int stop){
        StoryBean bean = mData.get(start);
        if (start < stop) {
            mData.add(stop + 1, bean);
            mData.remove(start);
        } else {
            mData.add(stop, bean);
            mData.remove(start + 1);
        }

        holdPosition = stop;
        isHide = true;
        notifyDataSetChanged();
    }

    class ViewHolder{
        private View itemView;
        private ViewGroup viewDrag;
        private ViewGroup viewName;
        private TextView textName;
        private ImageView imgDelete;
        private ViewGroup viewDelete;

        public ViewHolder(View view){
            itemView = view.findViewById(R.id.layout_root);
            viewDrag = (ViewGroup) view.findViewById(R.id.layout_album_drag);
            viewName = (ViewGroup) view.findViewById(R.id.layout_album_name);
            textName = (TextView) view.findViewById(R.id.text_album_name);
            imgDelete = (ImageView) view.findViewById(R.id.img_album_edit);
            viewDelete = (ViewGroup) view.findViewById(R.id.layout_album_delete);
        }
    }
}
