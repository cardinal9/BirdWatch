package com.jpitkonen.tsirbulawatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Dao.BirdDao;
import Model.Bird;

public class BirdRepository {

    private BirdDao birdDao;
    private LiveData<List<Bird>> allBirds;

    public BirdRepository(Application application) {
        BirdRoomDatabase database = BirdRoomDatabase.getDatabase(application);
        birdDao = database.birdDao();
        allBirds = birdDao.getBirds();
    }

    public void insert(Bird bird) {
        BirdRoomDatabase.databaseWriteExecutor.execute(() -> {
            birdDao.insert(bird);
        });
    }

    public void delete() {
        BirdRoomDatabase.databaseWriteExecutor.execute(() -> {
            birdDao.deleteAll();
        });
    }

    public LiveData<List<Bird>> getAllBirds() {
        return allBirds;
    }

}
