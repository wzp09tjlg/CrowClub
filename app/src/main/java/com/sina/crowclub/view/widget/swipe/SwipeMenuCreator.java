package com.sina.crowclub.view.widget.swipe;

public interface SwipeMenuCreator {
    //接口实现两个方法 带obj 的方法 是为根据数据控制需要显示的menu
	void create(SwipeMenu menu);

	void create(SwipeMenu menu, Object obj);
}
