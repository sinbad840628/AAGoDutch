package sinbad.godutch.activity.base;
/**
 *  No.8 新建ActivityFrame类
 * */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import sinbad.godutch.R;
import sinbad.godutch.controls.SlideMenuItem;
import sinbad.godutch.controls.SlideMenuView;

public class ActivityFrame extends ActivityBase {
	
	//No. 15  追加SlideMenuView 变量
	private SlideMenuView mSlideMenuView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //No.8 设置为没有title的窗体
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);		
		//Log.i("ActivityFrame", "ActivityFrame onCreate创建");		
		View view = findViewById(R.id.ivAppBack);
		OnBackListener _OnBackListener = new OnBackListener();
		view.setOnClickListener(_OnBackListener);
	}
	
	private class OnBackListener implements android.view.View.OnClickListener
	{
		public void onClick(View view)
		{
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 设置TopBar标题
	 * @param pRestID 要设置的String资源ID
	 */
	protected void SetTopBarTitle(String pText) {
		//Log.i("ActivityFrame", "SetTopBarTitle 设置TopBarTitle");
		TextView _tvTitle = (TextView) findViewById(R.id.tvTopTitle);
		_tvTitle.setText(pText);
		
	}
	
	protected void HideTitleBackButton()
	{
		//Log.i("ActivityFrame", "调用 HideTitleBackButton 函数");
		findViewById(R.id.ivAppBack).setVisibility(View.GONE);
	}
	
	/**
	 * No.8
	 * 添加Layout到程序Body中
	 * @param pResID 要添加的Layout资源ID
	 */
	protected void AppendMainBody(int pResID)
	{
		//Log.i("ActivityFrame", "调用 AppendMainBody 函数，通过资源文件调用");
		LinearLayout _MainBody = (LinearLayout)findViewById(GetMainBodyLayoutID());		
		View _View = LayoutInflater.from(this).inflate(pResID, null);
		RelativeLayout.LayoutParams _LayoutParams = 
				new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(_View,_LayoutParams);
	}
	
	/**
	 * 添加Layout到程序Body中
	 * @param pView 要添加的View
	 */
	protected void AppendMainBody(View pView)
	{
		//Log.i("ActivityFrame", "调用 AppendMainBody 函数，通过试图文件调用");
		LinearLayout _MainBody = (LinearLayout)findViewById(GetMainBodyLayoutID());		
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(pView,_LayoutParams);
	}
	
	/**
	 * No.15 追加新建SlideMenu的函数
	 * */
	protected void CreateSlideMenu(int pResID) {
		//Log.i("ActivityFrame", "创建滑动菜单 CreateSlideMenu");
		mSlideMenuView = new SlideMenuView(this);
		
		String[] _MenuItemArray = getResources().getStringArray(pResID);				
		
		//No.16  追加逻辑更新
		for (int i = 0; i < _MenuItemArray.length; i++) {
			SlideMenuItem _Item = new SlideMenuItem(i, _MenuItemArray[i]);
			mSlideMenuView.Add(_Item);
		}		
		mSlideMenuView.BindList();
	}
	
	/**
	 * No.8
	 * 添加Layout资源ID到程序Layout中
	 */
	private int GetMainBodyLayoutID()
	{
		//Log.i("ActivityFrame", "返回 主界面布局的ID    GetMainBodyLayoutID");
		//返回主界面的线性布局Layout资源
		return R.id.layMainBody;
	}
	
	/**
	 * No.31
	 * 添加SlideMenuToogle
	 */
	protected void SlideMenuToggle() {
		//Log.i("ActivityFrame", "调用滑动菜单开关   SlideMenuToggle");
		mSlideMenuView.Toggle();
	}
	
	protected void RemoveBottomBox()
	{
		//Log.i("ActivityFrame", "删除底部的  RemoveBottomBox");
		mSlideMenuView = new SlideMenuView(this);
		mSlideMenuView.RemoveBottomBox();
	}
	
	protected void CreateMenu(Menu pMenu)
	{
		//Log.i("ActivityFrame", "创建菜单  CreateMenu");
		int _GroupID = 0;
		int _Order = 0;
		int[] pItemID = {1,2};
		
		for(int i=0;i<pItemID.length;i++)
		{
			switch(pItemID[i])
			{
			case 1:
				pMenu.add(_GroupID, pItemID[i], _Order, R.string.MenuTextEdit);
				break;
			case 2:
				pMenu.add(_GroupID, pItemID[i], _Order, R.string.MenuTextDelete);
				break;
			default:
				break;
			}
		}
	}
	
}
