package com.sina.crowclub.view.widget.DragListView;


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

import java.util.ArrayList;
import java.util.List;

public class DragListAdapter extends BaseAdapter {
    private ArrayList<String> arrayTitles;
    private ArrayList<Integer> arrayDrawables;
    private Context context;

    private List<StoryBean> mData;

    public DragListAdapter(Context context,List<StoryBean> data) {
        this.context = context;
        mData = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        /***
         * 在这里尽可能每次都进行实例化新的，这样在拖拽ListView的时候不会出现错乱.
         * 具体原因不明，不过这样经过测试，目前没有发现错乱。虽说效率不高，但是做拖拽LisView足够了。
         */
        view = LayoutInflater.from(context).inflate(
                R.layout.item_album_edit, null);

        TextView textView = (TextView) view
                .findViewById(R.id.text_album_name);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.img_album_drag);
        textView.setText(mData.get(position).name);

        ViewGroup viewName = (ViewGroup) view.findViewById(R.id.layout_album_name);

        viewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"name onClick:" + position,Toast.LENGTH_LONG).show();
            }
        });

        ViewGroup viewDelete = (ViewGroup) view.findViewById(R.id.layout_album_delete);

        viewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"delete  onClick:" + position,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    /***
     * 动态修改ListVIiw的方位.
     *
     * @param start
     *            点击移动的position
     * @param down
     *            松开时候的position
     */
    public void update(int start, int down) {
        // 获取删除的东东.
        String str1 = mData.get(start).name;
        String str2 = mData.get(down).name;
        mData.get(start).name = str2;
        mData.get(down).name = str1;

        notifyDataSetChanged();// 刷新ListView
    }

    @Override
    public int getCount() {

        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}