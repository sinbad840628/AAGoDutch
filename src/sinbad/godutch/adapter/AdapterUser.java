package sinbad.godutch.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import sinbad.godutch.R;
import sinbad.godutch.adapter.base.AdapterBase;
import sinbad.godutch.business.BusinessUser;
import sinbad.godutch.model.ModelUser;
/**
 * No.25 新增UserAdapter
 * */
public class AdapterUser extends AdapterBase{
	
	// No.25  容器类
	private class Holder
	{
		ImageView ivUserIcon;
		TextView tvUserName;
	}

	/* No.25 自动生成函数 */
	public AdapterUser(Context pContext) {
		//No.25 添加对应的逻辑
		super(pContext, null);
		BusinessUser _BusinessUser = new BusinessUser(pContext);
		//查找不是逻辑删除用户的有效列表
		List _List = _BusinessUser.GetNotHideUser();
		SetList(_List);
	}

	/* No.25 自动生成函数 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//No.25 修改对应的逻辑
		Holder _Holder;
		
		if (convertView == null) {
			convertView = GetLayoutInflater().inflate(R.layout.user_list_item, null);
			_Holder = new Holder();
			_Holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
			_Holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			convertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) convertView.getTag();
		}
		
		ModelUser _Info = (ModelUser) GetList().get(position);
		
		_Holder.ivUserIcon.setImageResource(R.drawable.user_big_icon);
		_Holder.tvUserName.setText(_Info.getUserName());
		
		return convertView;
	}

}
