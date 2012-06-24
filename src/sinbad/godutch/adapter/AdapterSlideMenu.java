package sinbad.godutch.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sinbad.godutch.R;
import sinbad.godutch.adapter.base.AdapterBase;
import sinbad.godutch.controls.SlideMenuItem;

/**
 * No.14 我们为了绑定List组件，需要新建一个Adapter控件
 *       当然，在新建Adatper控件之前，我们先需要追加一个Adapter的父类
 *       然后让他来调用这个类,然后让  AdapterBase 成为 他的父类
 * */
public class AdapterSlideMenu extends AdapterBase{
	
	//No.14  变量容器类
	private class Holder
	{
		//只追加TextView
		TextView tvMenuName;
	}

	/* No.14  自动实现的函数 */
	public AdapterSlideMenu(Context pContext, List pList) {
		super(pContext, pList);
	}
	
	/* No.15  自动实现的函数   更新 getView 得到的信息*/
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder _Holder;
		//如果convertView为空，那么新建一个
		if (convertView == null) {			
			convertView = GetLayoutInflater().inflate(R.layout.slidemenu_list_item, null);			
			_Holder = new Holder();			
			_Holder.tvMenuName = (TextView) convertView.findViewById(R.id.tvMenuName);			
			convertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) convertView.getTag();
		}
		
		SlideMenuItem _Item = (SlideMenuItem) GetList().get(position);		
		_Holder.tvMenuName.setText(_Item.getTitle());		
		return convertView;
	}

}
