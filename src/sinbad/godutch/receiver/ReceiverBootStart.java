package sinbad.godutch.receiver;

import sinbad.godutch.service.ServiceDatabaseBackup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverBootStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context pContext, Intent intent) {
		Intent _Intent = new Intent(pContext, ServiceDatabaseBackup.class);
		pContext.startService(_Intent);

	}

}
