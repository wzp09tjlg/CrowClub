package com.sina.crowclub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.crowclub.R;
import com.sina.crowclub.network.Parse.StoryBean;
import com.sina.crowclub.utils.LogUtil;

import java.util.List;

/**
 * Created by wu on 2016/8/8.
 */
public class AlbumAdapter extends BaseAdapter {
    private static final String TAG = "AlbumAdapter";
    private List<StoryBean> mData;
    private Context mContext;
    private OnItemDelete onItemDelete;
    private OnItemDrag onItemDrag;

    public AlbumAdapter(Context context,List<StoryBean> data){
        mContext = context;
        mData = data;
    }

    public void setOnItemDelete(OnItemDelete onItemDelete) {
        this.onItemDelete = onItemDelete;
    }

    public void setOnItemDrag(OnItemDrag onItemDrag) {
        this.onItemDrag = onItemDrag;
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
        viewHolder.textName.setText(mData.get(position).name);
        viewHolder.viewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext," onClick ",Toast.LENGTH_LONG).show();
            }
        });
//        viewHolder.viewDrag.setOnTouchListener(getTouchListener());
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"delete",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    private View.OnTouchListener getTouchListener(){
        View.OnTouchListener listener = new View.OnTouchListener() {
            float lastDownX,lastDownY;
            boolean isMove;
            int offset;
            boolean shortTouch;
            boolean longTouch;
            long mDownTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                offset = ViewConfiguration.get(mContext).getScaledTouchSlop();
                float x = event.getX();
                float y = event.getY();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        lastDownX = event.getX();
                        lastDownY = event.getY();
                        mDownTime = System.currentTimeMillis();
                        isMove = false;
                        LogUtil.e("ACTION_DOWN  x:" + lastDownX + "  y:" + lastDownY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(x - lastDownX)>offset || Math.abs(y - lastDownY)>offset){
                            isMove = true;
                        }
                        LogUtil.e("ACTION_MOVE  x:" + x + "  y:" + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(!isMove) return false;
                        if(System.currentTimeMillis()-mDownTime > 1000){
                            longTouch = true;
                        }else {
                            shortTouch = true;
                        }
                        LogUtil.e("ACTION_UP  x:" + x + "  y:" + y);
                        break;
                }
                if(longTouch){
                   Toast.makeText(mContext,"  longTouch ",Toast.LENGTH_LONG).show();
                }else if(shortTouch){
                    Toast.makeText(mContext," shortTouch ",Toast.LENGTH_LONG).show();
                }

                return false;
            }
        };
        return listener;
    }

    class ViewHolder{
        private ViewGroup viewDrag;
        private ViewGroup viewName;
        private TextView textName;
        private ImageView imgDelete;
        private ViewGroup viewDelete;

        public ViewHolder(View view){
            viewDrag = (ViewGroup) view.findViewById(R.id.layout_album_drag);
            viewName = (ViewGroup) view.findViewById(R.id.layout_album_name);
            textName = (TextView) view.findViewById(R.id.text_album_name);
            imgDelete = (ImageView) view.findViewById(R.id.img_album_edit);
            viewDelete = (ViewGroup) view.findViewById(R.id.layout_album_delete);
        }
    }

    public interface OnItemDelete{
        void onItemDelete(int position,StoryBean bean);
    }

    public interface OnItemDrag{
        void onItemDrag(int position);
    }
}
