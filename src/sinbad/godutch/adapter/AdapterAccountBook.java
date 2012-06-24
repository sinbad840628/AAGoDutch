package sinbad.godutch.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import sinbad.godutch.R;
import sinbad.godutch.adapter.base.AdapterBase;
import sinbad.godutch.business.BusinessAccountBook;
import sinbad.godutch.model.ModelAccountBook;

public class AdapterAccountBook extends AdapterBase {
	
	private class Holder
	{
		ImageView ivIcon;
		TextView tvName;
		TextView tvTotal;
		TextView tvMoney;
	}
	

	public AdapterAccountBook(Context pContext) {
		super(pContext, null);
		Log.i("AdapterAccountBook", "AdapterAccountBook构造函数调用");
		BusinessAccountBook _BusinessAccountBook = new BusinessAccountBook(pContext);
		List _List = _BusinessAccountBook.GetAccountBook("");
		SetList(_List);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("AdapterAccountBook", "getView方法调用");
		Holder _Holder;
		if( convertView == null)
		{
			convertView = GetLayoutInflater().inflate(R.layout.account_book_list_item, null);
			_Holder = new Holder();
			_Holder.ivIcon = (ImageView)convertView.findViewById(R.id.ivAccountBookIcon);
			_Holder.tvName = (TextView)convertView.findViewById(R.id.tvAccountBookName);
			_Holder.tvMoney = (TextView)convertView.findViewById(R.id.tvMoney);
			_Holder.tvTotal = (TextView)convertView.findViewById(R.id.tvTotal);
			convertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) convertView.getTag();
		}
		
		ModelAccountBook _ModelAccountBook = (ModelAccountBook)getItem(position);
		if (_ModelAccountBook.GetIsDefault() == 1) {
			_Holder.ivIcon.setImageResource(R.drawable.account_book_default);
		}
		else {
			_Holder.ivIcon.setImageResource(R.drawable.account_book_big_icon);
		}
		_Holder.tvName.setText(_ModelAccountBook.GetAccountBookName());
		return convertView;
	}

}
