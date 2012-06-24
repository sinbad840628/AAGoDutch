package sinbad.godutch.adapter.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * No.14 发觉Adapter的方法有共同规律的时候，自己去新建一个新的AdapterBase，
 *       来作为新的基类。
 * */
public abstract class AdapterBase extends BaseAdapter{
	
	//No.14 追加一个共用的变量
	private List mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	//No.14 追加一个构造函数，因为你很有可能在构造函数中获取String，或者R资源
	public AdapterBase(Context pContext,List pList)
	{
		mContext = pContext;
		mList = pList;
		//直接动态加载
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	//No.14  新建一个Get方法来调用 Context
	public Context GetContext()
	{
		return mContext;
	}
	
	
	//No.14  新建一个Get方法来调用mLayoutInflater
	public LayoutInflater GetLayoutInflater()
	{
		return mLayoutInflater;
	}

	//No.15 新建一个 Get方法来调用 mList
	public List GetList()
	{
		return mList;
	}
	
	//No.25  需要新建一个 SetList 方法来调用 mList
	public void SetList(List pList) {
		mList = pList;
	}
	
	/* No. 14  自动生成的函数： 用来取得对应数组的长度 */
	public int getCount() {
		return mList.size();
	}

	/* No. 14  自动生成的函数  用来取得mList的对应位置*/
	public Object getItem(int pPosition) {
		return mList.get(pPosition);
	}

	/* No. 14  自动生成的函数  用来取得List对应的ID*/
	public long getItemId(int pPosition) {
		return pPosition;
	}

	/* No. 14  自动生成的函数  因为没有办法封装，所以就改用接口来用了*/
	/*public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
