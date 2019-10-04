package sudo.cide.squad.fidobite.ui.searching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import sudo.cide.squad.fidobite.R;

public class SearchingFragment extends Fragment {

    private SearchingViewModel searchingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchingViewModel =
                ViewModelProviders.of(this).get(SearchingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_searching, container, false);

        return root;
    }
}