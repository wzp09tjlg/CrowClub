package com.sina.crowclub.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;

import java.util.List;

/**
 * Created by wu on 2016/8/13.
 */
public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder> {
    private static final String TAG = AlbumDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<StoryBean> mData;

    private boolean isEditAble = true;

    public List<StoryBean> getData(){
        return mData;
    }

    public void setEditAble(boolean editAble) {
        isEditAble = editAble;
    }

    public AlbumDetailAdapter(Context context, List<StoryBean> data){
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album_edit,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoryBean bean = mData.get(position);
        holder.textName.setText(bean.name);
        holder.viewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"viewName ",Toast.LENGTH_SHORT).show();
            }
        });
        if(!isEditAble){
            holder.viewDelete.setVisibility(View.INVISIBLE);
            holder.viewDelete.setOnClickListener(null);
        }else{
            holder.viewDelete.setVisibility(View.VISIBLE);
            holder.viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"viewDelete ",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ViewGroup viewDrag;
        private ViewGroup viewName;
        private TextView textName;
        private ViewGroup viewDelete;
        private ImageView imgDelete;

        public ViewHolder(View view){
           super(view);
            viewDrag = (ViewGroup) view.findViewById(R.id.layout_album_drag);
            viewName = (ViewGroup) view.findViewById(R.id.layout_album_name);
            textName = (TextView) view.findViewById(R.id.text_album_name);
            viewDelete = (ViewGroup) view.findViewById(R.id.layout_album_delete);
            imgDelete = (ImageView) view.findViewById(R.id.img_album_drag);
        }
    }

}
