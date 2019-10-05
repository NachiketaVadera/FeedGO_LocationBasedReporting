package sudo.cide.squad.feedgo;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = "__FIDOBITE__";

    private static String title;
    private static String description;
    private static String choice;

    private DocumentReference firestore;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        firestore = FirebaseFirestore.getInstance().document("/");

        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog();
            }
        });

        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog dialog = new SweetAlertDialog(MapsActivity.this, SweetAlertDialog.NORMAL_TYPE);
                dialog.setTitle("Confirm Report");
                dialog.setTitleText("Is everything cool? Or do you want to change something?");
                dialog.setConfirmButton("Yep, all cool!", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismissWithAnimation();
                        // upload data to Firebase
                    }
                });
                dialog.setCancelButton("Nah, change.", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismissWithAnimation();
                        showChangeDialog();
                    }
                });
                dialog.show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.i(TAG, "onCreate: getMapAsync is null");
        }

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("desc");
        choice = getIntent().getStringExtra("choice");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng userLocation = new LatLng(MainActivity.latitude, MainActivity.longitude);
        marker = googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title(title)
                .snippet("Descreption:\n" + description + "\nCategory:" + choice));
        marker.showInfoWindow();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f));

        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content),
                "Change or Confirm the Report", Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    private void showChangeDialog() {
        View dialog = getLayoutInflater().inflate(R.layout.dialog_change_report, null);
        final EditText etTitle = dialog.findViewById(R.id.et_dialog_report_title);
        final EditText etDesc = dialog.findViewById(R.id.et_dialog_report_desc);
        final Spinner spChoice = dialog.findViewById(R.id.sp_dialog_report_category);
        etTitle.setText(title);
        etDesc.setText(description);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("-- Select a Choice --");
        spinnerArray.add("Danger");
        spinnerArray.add("Interesting Place");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        );

        spChoice.setAdapter(adapter);

        final DialogPlusBuilder dialogPlusBuilder = DialogPlus.newDialog(this);
        dialogPlusBuilder.setContentHolder(new ViewHolder(dialog));
        dialogPlusBuilder.setExpanded(true);
        dialogPlusBuilder.setGravity(Gravity.CENTER);
        final DialogPlus changeDialog = dialogPlusBuilder.create();
        dialogPlusBuilder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                title = etTitle.getText().toString();
                description = etDesc.getText().toString();
                choice = spChoice.getSelectedItem().toString();
                marker.setTitle(title);
                marker.setSnippet("Description:\n" + description + "\nCategory:" + choice);
                marker.showInfoWindow();
                changeDialog.dismiss();
            }
        });
        changeDialog.show();
    }
}
