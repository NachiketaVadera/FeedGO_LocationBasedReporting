package sudo.cide.squad.fidobite.ui.reporting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import sudo.cide.squad.fidobite.MainActivity;
import sudo.cide.squad.fidobite.MapsActivity;
import sudo.cide.squad.fidobite.R;

public class ReportingFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final ReportingViewModel reportingViewModel = ViewModelProviders.of(this).get(ReportingViewModel.class);

        reportingViewModel.setLatitude(MainActivity.latitude);
        reportingViewModel.setLongitude(MainActivity.longitude);

        View view = inflater.inflate(R.layout.fragment_reporting, container, false);
        Button button = view.findViewById(R.id.btn_check);
        final TextView tvTitle = view.findViewById(R.id.et_report_title);
        final TextView tvDesc = view.findViewById(R.id.et_report_desc);
        final Spinner choice = view.findViewById(R.id.sp_report_category);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                String title = tvTitle.getText().toString();
                String description = tvDesc.getText().toString();
                String selectedItem = choice.getSelectedItem().toString();

                Intent toMapActivity = new Intent(getContext(), MapsActivity.class);

                toMapActivity.putExtra("lat", reportingViewModel.getLatitude());
                toMapActivity.putExtra("lng", reportingViewModel.getLongitude());
                toMapActivity.putExtra("title", title);
                toMapActivity.putExtra("desc", description);
                toMapActivity.putExtra("choice", selectedItem);

                startActivity(toMapActivity);
            }
        });

        return view;
    }
}