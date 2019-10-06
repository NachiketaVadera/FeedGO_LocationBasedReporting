package sudo.cide.squad.feedgo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pathsense.android.sdk.location.PathsenseGeofenceEvent;

import sudo.cide.squad.feedgo.util.Global;


public class GeofenceReciever extends BroadcastReceiver {

    private static final String CHANNEL_ID = "FeedGO-notify";

    @Override
    public void onReceive(Context context, Intent intent) {
        PathsenseGeofenceEvent geofenceEvent = PathsenseGeofenceEvent.fromIntent(intent);

        if (geofenceEvent != null) {
            if (geofenceEvent.isIngress()) {
                Toast.makeText(context, "Entered an area maked as " +
                        Global.getGeofenceStore().getCategory(), Toast.LENGTH_LONG).show();
            }
            if (geofenceEvent.isEgress()) {
                Toast.makeText(context, "Sad to see you leave", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
