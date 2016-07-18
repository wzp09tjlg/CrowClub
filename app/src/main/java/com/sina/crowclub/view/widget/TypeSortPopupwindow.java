package com.sina.crowclub.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.sina.crowclub.R;
import com.sina.crowclub.view.adapter.TypeSortAdapter;

import java.util.List;

/**
 * Created by wu on 2016/7/18.
 */
public class TypeSortPopupwindow {
    private PopupWindow popupWindow;

    public  TypeSortPopupwindow(Context context, int position, List<String> data,ItemPopupWindowListener listener){
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_story_type_sort_content,null);
        ListView listView = (ListView)contentView.findViewById(R.id.list_story_type_sort_content);
        listView.setDividerHeight(0);
        TypeSortAdapter adapter = new TypeSortAdapter(context,position,data,listener);
        listView.setAdapter(adapter);

        popupWindow = new PopupWindow(contentView
              , ViewGroup.LayoutParams.MATCH_PARENT
              , ViewGroup.LayoutParams.WRAP_CONTENT
              , true);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.drawable_transparent));
    }

    public void show(View view){
        if(popupWindow != null)
          popupWindow.showAsDropDown(view,0,0);
    }

    public boolean isShowing(){
      if(popupWindow != null)
          return popupWindow.isShowing();
        return false;
    }

    public void dismiss(){
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }

    public void setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        if(popupWindow!= null)
            popupWindow.setOnDismissListener(dismissListener);
    }

    public interface ItemPopupWindowListener{
        void onItemPopupWindowListener(View view,int position);
    }
}
