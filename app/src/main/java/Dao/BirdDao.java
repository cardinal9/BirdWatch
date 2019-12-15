package Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Model.Bird;

@Dao
public interface BirdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bird bird);

    @Query("DELETE FROM bird_table")
    void deleteAll();

    @Query("SELECT * FROM bird_table ORDER BY timestamp ASC")
    LiveData<List<Bird>> getBirds();
}
