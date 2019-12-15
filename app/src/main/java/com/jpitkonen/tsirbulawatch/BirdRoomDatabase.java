package com.jpitkonen.tsirbulawatch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Dao.BirdDao;
import Model.Bird;

@Database(entities = {Bird.class}, version = 2, exportSchema = false)
public abstract class BirdRoomDatabase extends RoomDatabase {

    public abstract BirdDao birdDao();

    private static volatile BirdRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    static BirdRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BirdRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BirdRoomDatabase.class,
                            "bird_database").addMigrations(MIGRATION_1_2).addCallback(sRoomDatabaseCallback).build();

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
                //birdDao.deleteAll();

                Bird bird = new Bird("Red Robin", "Rare", "Found one!", "13.12.2019");
                birdDao.insert(bird);

                bird = new Bird("Punatulkku", "Common", "Found another..", "11.9.2019");
                birdDao.insert(bird);
            });
        }
    };

}
