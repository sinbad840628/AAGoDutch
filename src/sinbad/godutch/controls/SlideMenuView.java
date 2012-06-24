package sinbad.godutch.controls;

import java.util.ArrayList;
import java.util.List;

import sinbad.godutch.R;
import sinbad.godutch.adapter.AdapterSlideMenu;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

/**
 * No.13 新增一个SlideMenuView的类
 * */
public class SlideMenuView {
	
	private Activity mActivity;
	private List mMenuList;
	private boolean mIsClosed;
	private RelativeLayout layBottomBox;
	private OnSlideMenuListener mSlideMenuListener;
	
	public interface OnSlideMenuListener
	{
		public abstract void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem);
	}
	
	//No.13 新增构造函数
	public SlideMenuView(Activity pActivity) {
		mActivity = pActivity;
		InitView();
		if (pActivity instanceof OnSlideMenuListener) {
			mSlideMenuListener = (OnSlideMenuListener) pActivity;
			InitVariable();
			InitListeners();
		}
	}

	//No.13 新增变量的函数
	public void InitVariable() {
		//Log.i("SlideMenuView", "InitVariable() 初始化变量");
		mMenuList = new ArrayList();
		
		mIsClosed = true;
	}
	
	//No.13 新增初始化View的函数
	public void InitView() {
		//Log.i("SlideMenuView", "InitView() 初始化View组件");
		layBottomBox = (RelativeLayout) mActivity.findViewById(R.id.IncludeBottom);
	}
	
	//No.13 新增初始化Listeners的函数
	public void InitListeners() {
		//Log.i("SlideMenuView", "InitListeners()初始化监听器");
		layBottomBox.setOnClickListener(new OnSlideMenuClick());
		
		//No.20 在初始化监听器的时候，追加一下两个方法
		layBottomBox.setFocusableInTouchMode(true);
		
		layBottomBox.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//当我们使用菜单键和按键的时候
				if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP) {
					Toggle();
				}				
				return false;
			}
		});
	}

	//No.13 新增打开函数
	private void Open()
	{
		//Log.i("SlideMenuView", "Open() 方法开始");
		//相对布局设定变量，布局填写满整个窗口
		RelativeLayout.LayoutParams _LayoutParams = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
						RelativeLayout.LayoutParams.FILL_PARENT);
		//追加抬头Title的布局
		_LayoutParams.addRule(RelativeLayout.BELOW, R.id.IncludeTitle);
		//将layBottomBox的布局设置进去
		layBottomBox.setLayoutParams(_LayoutParams);
		//Log.i("SlideMenuView", "mIsClosed = false");
		mIsClosed = false;
	}
	
	//No.14新增关闭函数
	private void Close()
	{
		Log.i("SlideMenuView", "Close() 方法开始");
		//关闭
		RelativeLayout.LayoutParams _LayoutParams = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 68);
		
		_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		layBottomBox.setLayoutParams(_LayoutParams);
		Log.i("SlideMenuView", "mIsClosed = true");
		mIsClosed = true;
	}
	
	//No.14 新增开关函数
	public void Toggle()
	{
		//Log.i("SlideMenuView", "Toggle() 方法开始");
		//如果当前状态是关闭的话，就调用开启函数，反之调用关闭函数
		if(mIsClosed)
		{
			Open();
		}
		else {
			Close();
		}
	}
	
	
	public void RemoveBottomBox()
	{
		//Log.i("SlideMenuView", "RemoveBottomBox() 方法开始");
		RelativeLayout _MainLayout = (RelativeLayout)mActivity.findViewById(R.id.layMainLayout);
		_MainLayout.removeView(layBottomBox);
		layBottomBox = null;
	}

	//No.14 新增追加函数
	//No.15 更新函数，追加参数
	public void Add(SlideMenuItem pSliderMenuItem) {
		mMenuList.add(pSliderMenuItem);
		
	}
	
	//No.15 新增绑定列表数组List函数（需要新增一个Adapter）
	public void BindList()
	{
		//Log.i("SlideMenuView", "BindList 方法开始");
		AdapterSlideMenu _AdapterSlideMenu = new AdapterSlideMenu(mActivity, mMenuList);
		//实例化ListView
		ListView _ListView = (ListView) mActivity.findViewById(R.id.lvSlideList);
		_ListView.setAdapter(_AdapterSlideMenu);
		
		_ListView.setOnItemClickListener(new OnSlideMenuItemClick());
	}
	
	//No.15 新增SlideMenu点击方法
	private class OnSlideMenuClick implements OnClickListener
	{
		public void onClick(View v) {
			Toggle();
		}
		
	}
	
	//No.15  新增SlideMenu点击方法
	private class OnSlideMenuItemClick implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> pAdapterView, View pView, int pPosition,long arg3) {
			//No.17 通过点击的位置找到对应的Item 
			SlideMenuItem _SlideMenuItem = (SlideMenuItem) pAdapterView.getItemAtPosition(pPosition);
			//Log.i("SlideMenuView", "然后调用mSlideMenuListener 的 接口 onSlideMenuItemClick 方法 生成监听器");
			mSlideMenuListener.onSlideMenuItemClick(pView, _SlideMenuItem);
		}
	}
}
