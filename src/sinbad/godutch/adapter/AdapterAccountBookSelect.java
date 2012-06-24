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

public class AdapterAccountBookSelect extends AdapterBase {
	
	private class Holder
	{
		ImageView Icon;
		TextView Name;
	}

	public AdapterAccountBookSelect(Context pContext)
	{
		this(pContext, null);
		Log.i("AdapterAccountBookSelect", "AdapterAccountBookSelect(Context pContext)");
		BusinessAccountBook _BusinessAccountBook = new BusinessAccountBook(pContext);
		List _List = _BusinessAccountBook.GetAccountBook("");
		SetList(_List);
	}
	
	public AdapterAccountBookSelect(Context pContext, List pList) {
		super(pContext, pList);
		Log.i("AdapterAccountBookSelect", "AdapterAccountBookSelect(Context pContext, List pList) ");
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("AdapterAccountBookSelect", "getView ");
		
		Holder _Holder;
		
		if(convertView == null)
		{
			convertView = GetLayoutInflater().inflate(R.layout.account_book_select_list_item, null);
			_Holder = new Holder();
			_Holder.Icon = (ImageView)convertView.findViewById(R.id.AccountBookIcon);
			_Holder.Name = (TextView)convertView.findViewById(R.id.AccountBookName);
			convertView.setTag(_Holder);
		}else {
			_Holder = (Holder)convertView.getTag();
		}
		
		ModelAccountBook _ModelAccountBook = (ModelAccountBook)getItem(position);
		_Holder.Icon.setImageResource(R.drawable.account_book_small_icon);
		_Holder.Name.setText(_ModelAccountBook.GetAccountBookName());
		
		return convertView;
	}

}
