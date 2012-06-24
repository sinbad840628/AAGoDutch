package sinbad.godutch.activity;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.model.ModelCategoryTotal;

public class ActivityCategoryChart extends ActivityFrame {
	
	private List<ModelCategoryTotal> mModelCategoryTotal;
	private TextView tvContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ActivityCategoryChart", "onCreate 创建");
		InitVariable();
		View _PieView = CategoryStatistics();
		AppendMainBody(_PieView);
		RemoveBottomBox();
	}
	
	private View CategoryStatistics() {
		Log.i("ActivityCategoryChart", "CategoryStatistics 创建");
		int[] _Color = new int[] { Color.parseColor("#FF5552"), Color.parseColor("#2A94F1"), Color.parseColor("#F12792"), Color.parseColor("#FFFF52"), Color.parseColor("#84D911"),Color.parseColor("#5255FF") };
		DefaultRenderer _DefaultRenderer = BuildCategoryRenderer(_Color);
		CategorySeries _CategorySeries = _BuildCategoryDataset("消费类别统计", mModelCategoryTotal);
		View _PieView = ChartFactory.getPieChartView(this, _CategorySeries, _DefaultRenderer);
		return _PieView;
	}
	
	protected DefaultRenderer BuildCategoryRenderer(int[] colors) {
		Log.i("ActivityCategoryChart", "DefaultRenderer BuildCategoryRenderer 创建");
        DefaultRenderer _Renderer = new DefaultRenderer();
        _Renderer.setZoomButtonsVisible(true);
        _Renderer.setLabelsTextSize(15);
        _Renderer.setLegendTextSize(15);
        _Renderer.setLabelsColor(Color.BLUE);
        _Renderer.setMargins(new int[] { 20, 30, 15, 10 });
        int _Color = 0;
        for (int i = 0;i<mModelCategoryTotal.size();i++) {
          SimpleSeriesRenderer _R = new SimpleSeriesRenderer();
          _R.setColor(colors[_Color]);
          _Renderer.addSeriesRenderer(_R);
          _Color++;
          if (_Color > colors.length) {
        	  _Color = 0;
          }
        }
        return _Renderer;
      }
    protected CategorySeries _BuildCategoryDataset(String title, List<ModelCategoryTotal> values) {
    	Log.i("ActivityCategoryChart", "CategorySeries _BuildCategoryDataset 创建");
        CategorySeries series = new CategorySeries(title);
        for (ModelCategoryTotal value : values) {
          series.add("数量： " + value.Count, Double.parseDouble(value.Count));
        }

        return series;
      }
	
	private void SetTitle() {
		Log.i("ActivityCategoryChart", "SetTitle 设置标题");
		String _Titel = getString(R.string.ActivityCategoryTotal);
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
	}

	protected void InitListeners() {
		
	}
	

	protected void InitVariable() {
		Log.i("ActivityCategoryChart", "InitVariable 初始化变量");
		mModelCategoryTotal = (List<ModelCategoryTotal>) getIntent().getSerializableExtra("Total");
	}

	protected void BindData()
	{
		Log.i("ActivityCategoryChart", "BindData 绑定数据");
		SetTitle();
		String _Content = "";
		for (int i = 0; i < mModelCategoryTotal.size(); i++) {
			ModelCategoryTotal _ModelCategoryTotal = mModelCategoryTotal.get(i);
			_Content += _ModelCategoryTotal.CategoryName + "--" + _ModelCategoryTotal.Count + "--" + _ModelCategoryTotal.SumAmount + "\r\n";
		}
		
		tvContent.setText(_Content);
	}

}
