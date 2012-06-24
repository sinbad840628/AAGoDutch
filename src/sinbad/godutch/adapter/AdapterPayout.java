package sinbad.godutch.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import sinbad.godutch.R;
import sinbad.godutch.adapter.base.AdapterBase;
import sinbad.godutch.business.BusinessPayout;
import sinbad.godutch.business.BusinessUser;
import sinbad.godutch.model.ModelPayout;
import sinbad.godutch.utility.DateTools;

public class AdapterPayout extends AdapterBase {
	
	private class Holder
	{
		ImageView Icon;
		TextView Name;
		TextView Total;
		TextView PayoutUserAndPayoutType;
		View RelativeLayoutDate;
	}

	private BusinessPayout m_BusinessPayout;
	private int mAccountBookID;
	
	public AdapterPayout(Context pContext,int pAccountBookID)
	{
		this(pContext,null);
		m_BusinessPayout = new BusinessPayout(pContext);
		mAccountBookID = pAccountBookID;
		List _List = m_BusinessPayout.GetPayoutByAccountBookID(pAccountBookID);
		SetList(_List);
	}
	
	public AdapterPayout(Context pContext, List pList) {
		super(pContext, pList);
	}

	public View getView(int pPosition, View pConvertView, ViewGroup parent) {
		Holder _Holder;
		
		if (pConvertView == null) {
			pConvertView = GetLayoutInflater().inflate(R.layout.payout_list_item, null);
			_Holder = new Holder();
			_Holder.Icon = (ImageView)pConvertView.findViewById(R.id.PayoutIcon);
			_Holder.Name = (TextView)pConvertView.findViewById(R.id.PayoutName);
			_Holder.Total = (TextView)pConvertView.findViewById(R.id.Total);
			_Holder.PayoutUserAndPayoutType = (TextView)pConvertView.findViewById(R.id.PayoutUserAndPayoutType);
			_Holder.RelativeLayoutDate = (View)pConvertView.findViewById(R.id.RelativeLayoutDate);
			pConvertView.setTag(_Holder);
		}
		else {
			_Holder = (Holder) pConvertView.getTag();
		}
		
		_Holder.RelativeLayoutDate.setVisibility(View.GONE);
		ModelPayout _ModelPayout = (ModelPayout)getItem(pPosition);
		String _PayoutDate = DateTools.getFormatShortTime(_ModelPayout.GetPayoutDate());
		Boolean _IsShow = false;
		if(pPosition > 0)
		{
			ModelPayout _ModelPayoutLast = (ModelPayout)getItem(pPosition - 1);
			String _PayoutDateLast = DateTools.getFormatShortTime(_ModelPayoutLast.GetPayoutDate());
			_IsShow = !_PayoutDate.equals(_PayoutDateLast);
		}
		if(_IsShow || pPosition == 0)
		{
			_Holder.RelativeLayoutDate.setVisibility(View.VISIBLE);
			String _Message = m_BusinessPayout.GetPayoutTotalMessage(_PayoutDate,mAccountBookID); 
			((TextView)_Holder.RelativeLayoutDate.findViewById(R.id.tvPayoutDate)).setText(_PayoutDate);
			((TextView)_Holder.RelativeLayoutDate.findViewById(R.id.tvTotal)).setText(_Message);
		}
		
		_Holder.Icon.setImageResource(R.drawable.payout_small_icon);
		_Holder.Total.setText(_ModelPayout.GetAmount().toString());
		_Holder.Name.setText(_ModelPayout.GetCategoryName());
		
		BusinessUser _BusinessUser = new BusinessUser(GetContext());
		String _UserNameString = _BusinessUser.GetUserNameByUserID(_ModelPayout.GetPayoutUserID());
		_Holder.PayoutUserAndPayoutType.setText(_UserNameString + " " + _ModelPayout.GetPayoutType());
		
		return pConvertView;
	}

}
