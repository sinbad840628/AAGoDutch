package sinbad.godutch.adapter;
/**
 * No.8 新建AdapterAppGrid
 * 目的用于去的九宫格里面的所有数据
 * 包括图标组，所以这个类的目的是AppGrid的适配器
 * 
 * 该界面需要加载一个布局，所以继续新建布局
 * 
 * 
 * */

import sinbad.godutch.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterAppGrid extends BaseAdapter {
	
	//No.9  声明一个 Context
	private Context mContext;
	
	//No.9  图标组资源的容器类：包含图标组用的图标ImageView和说明TextView
	private class Holder
	{
		ImageView ivIcon;
		TextView tvName;
	}
	//No.9 声明一组ImageString数组，存贮图标组对应的说明
	private String[] mImageString = new String[6];
	
	//No.9  新建构造函数AdapterAppGrid
	public AdapterAppGrid(Context pContext)
	{
		mContext = pContext;
		//记录消费
		mImageString[0] = pContext.getString(R.string.appGridTextPayoutAdd);
		//查询消费
		mImageString[1] = pContext.getString(R.string.appGridTextPayoutManage);
		//统计管理
		mImageString[2] = pContext.getString(R.string.appGridTextStatisticsManage);
		//账本管理
		mImageString[3] = pContext.getString(R.string.appGridTextAccountBookManage);
		//类别管理
		mImageString[4] = pContext.getString(R.string.appGridTextCategoryManage);
		//人员管理
		mImageString[5] = pContext.getString(R.string.appGridTextUserManage);
	}
	
	//No.9 新建    一组ImageString数组，存贮图标组对应的图片
	private Integer[] mImageInteger = {
			R.drawable.grid_payout,
			R.drawable.grid_bill,
			R.drawable.grid_report,
			R.drawable.grid_account_book,
			R.drawable.grid_category,
			R.drawable.grid_user,
	};

	//No.9 取得线性图标组数组的总长度
	public int getCount() {
		return mImageString.length;
	}

	//No.9 获取点击图标组的实体项位置
	public Object getItem(int position) {
		return mImageString[position];
	}

	//No.9 取得点击图标组对应的位置
	public long getItemId(int position) {
		return position;
	}
	//No.9 取得视图的方法
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//调用Holder的图标容器类
		Holder _Holder;
		
		//如果没有视图的话,就动态加载一个
		if (convertView == null) {
			//通过动态加载当前的内容
			LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
			//然后，将main_body_item这个图标组的布局，inflate到convertView中去，因为是根目录，所以没有父目录，null表示
			convertView = _LayoutInflater.inflate(R.layout.main_body_item, null);
			
			//新建图标容器类
			_Holder = new Holder();
			
			//绑定图标
			_Holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			//绑定图标名
			_Holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			
			//设置Tag标签
			convertView.setTag(_Holder);
		}
		else {
			//如果已经有了，那么就直接得到Tag标签
			_Holder = (Holder) convertView.getTag();
		}
				
		//在全部图标组都就绪的情况下
		
		//给对应的图标设置资源库的图片
		_Holder.ivIcon.setImageResource(mImageInteger[position]);
		//No.11 将对应的图片设置为50 X 50的图标
		LinearLayout.LayoutParams _LayoutParams = new LinearLayout.LayoutParams(50, 50);
		
		//No.11 设置图标的布局格式，是线性布局
		_Holder.ivIcon.setLayoutParams(_LayoutParams);
		
		//No.11 设置图标的缩放类型：是填充XY轴的方式
		_Holder.ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
		
		//No.11 设置图标名称的对应文本：绑定图片字符串对应的所在位置
		_Holder.tvName.setText(mImageString[position]);
		
		//No.11 返回对应的View
		return convertView;
	}

}
