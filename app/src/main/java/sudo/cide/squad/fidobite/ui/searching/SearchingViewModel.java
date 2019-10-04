package sudo.cide.squad.fidobite.ui.searching;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}