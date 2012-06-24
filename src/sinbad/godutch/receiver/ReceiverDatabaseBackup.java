package sinbad.godutch.receiver;

import sinbad.godutch.R;
import sinbad.godutch.activity.ActivityMain;
import sinbad.godutch.service.ServiceDatabaseBackup;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverDatabaseBackup extends BroadcastReceiver {

	NotificationManager m_NotificationManager;   
    Notification m_Notification;   
       
    Intent m_Intent;   
    PendingIntent m_PendingIntent;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		m_NotificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);          
		Log.i("GoDutch", "广播：" + (intent.getLongExtra("Date",0)/1000));
        //点击通知时转移内容   
        m_Intent=new Intent(context,ActivityMain.class);   
           
        m_PendingIntent=PendingIntent.getActivity(context, 0, m_Intent, 0);   
           
        m_Notification=new Notification();   
        
        //设置通知在状态栏显示的图标   
        m_Notification.icon=R.drawable.icon;   
           
        //当我们点击通知时显示的内容   
        m_Notification.tickerText="AA费用小助手已执行数据备份";   
                           
        //通知时发出的默认声音   
        m_Notification.defaults=Notification.DEFAULT_SOUND;   
           
        //设置通知显示的参数   
        m_Notification.setLatestEventInfo(context, "AA费用小助手已执行数据备份", "AA费用小助手已执行数据备份",m_PendingIntent);
        
        //这个可以理解为开始执行这个通知   
        m_NotificationManager.notify(0,m_Notification);
        
        Intent _Intent = new Intent(context, ServiceDatabaseBackup.class);
        context.startService(_Intent);

	}

}
