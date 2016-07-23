package com.sina.crowclub.view.widget.swipe;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SwipeMenuView extends LinearLayout implements OnClickListener {

    private SwipeMenuListView mListView;
    private SwipeMenuLayout mLayout;
    private SwipeMenu mMenu;
    private OnSwipeItemClickListener onItemClickListener;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SwipeMenuView(SwipeMenu menu, SwipeMenuListView listView) {
        super(menu.getContext());
        mListView = listView;
        mMenu = menu;
        List<SwipeMenuItem> items = menu.getMenuItems();
        int id = 0;
        for (SwipeMenuItem item : items) {
            addItem(item, id++);
        }
    }

    private void addItem(SwipeMenuItem item, int id) {
        if(item.getType() == 0 ){
            createLinearMenu(item,id);
        }else if(item.getType() == 1){
            createVerticalMenu(item, id);
        }else{
            createLinearMenu(item,id);
        }
    }

    private void createLinearMenu(SwipeMenuItem item, int id){
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setPadding(item.getPadding(), 0, item.getPadding(), 0);
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());
        parent.setOnClickListener(this);
        addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }
    }

    private ImageView createIcon(SwipeMenuItem item) {
        LayoutParams lp = new LayoutParams(item.getTitleSize(), item.getTitleSize());
        lp.rightMargin = item.getPadding() / 2;
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIcon());
        iv.setLayoutParams(lp);
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }

    private void createVerticalMenu(SwipeMenuItem item, int id){
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setPadding(item.getPadding(), 0, item.getPadding(), 0);
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setLayoutParams(params);
        if(null != item.getBackground())
          parent.setBackgroundDrawable(item.getBackground());
        parent.setOnClickListener(this);
        addView(parent);

        LayoutParams paramsInner = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        LinearLayout inner = new LinearLayout(getContext());
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setBackgroundDrawable(item.getIconBackGround());
        inner.setPadding(0, 0, 0, 0);
        inner.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
        inner.setLayoutParams(paramsInner);
        parent.addView(inner);

        if (item.getIcon() != null) {
            inner.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            inner.addView(createTitle(item));
        }
    }

    private ImageView createIconBackGround(SwipeMenuItem item){
        LayoutParams lp = new LayoutParams(item.getTitleSize(), item.getTitleSize());
        lp.rightMargin = item.getPadding() / 2;
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIconBackGround());
        iv.setLayoutParams(lp);
        return iv;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null && mLayout.isOpen()) {
            onItemClickListener.onItemClick(this, mMenu, v.getId());
        }
    }

    public OnSwipeItemClickListener getOnSwipeItemClickListener() {
        return onItemClickListener;
    }

    public void setOnSwipeItemClickListener(OnSwipeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setLayout(SwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

    public interface OnSwipeItemClickListener {
        void onItemClick(SwipeMenuView view, SwipeMenu menu, int index);
    }
}
