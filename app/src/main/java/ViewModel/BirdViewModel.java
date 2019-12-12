package ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Bird;
import com.jpitkonen.tsirbulawatch.BirdRepository;

public class BirdViewModel extends AndroidViewModel {

    private BirdRepository birdRepository;

    private LiveData<List<Bird>> bAllBirds;

    public BirdViewModel(@NonNull Application application) {
        super(application);
        birdRepository = new BirdRepository(application);
        bAllBirds = birdRepository.getAllBirds();
    }

    public LiveData<List<Bird>> getAllBirds() {
        return bAllBirds;
    }

    public void insert(Bird bird) {
        birdRepository.insert(bird);
    }

}
