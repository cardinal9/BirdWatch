package com.jpitkonen.tsirbulawatch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Dao.BirdDao;
import Model.Bird;

@Database(entities = {Bird.class}, version = 1, exportSchema = false)
public abstract class BirdRoomDatabase extends RoomDatabase {

    public abstract BirdDao birdDao();

    private static volatile BirdRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BirdRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BirdRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BirdRoomDatabase.class, "bird_database").addCallback(sRoomDatabaseCallback).build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase database) {
            super.onOpen(database);

            databaseWriteExecutor.execute(() -> {
                BirdDao birdDao = INSTANCE.birdDao();
                birdDao.deleteAll();

                Bird bird = new Bird("Red Robin", "Rare", "Found one!");
                birdDao.insert(bird);

                bird = new Bird("Punatulkku", "Common", "Found another..");
                birdDao.insert(bird);
            });
        }
    };

}
