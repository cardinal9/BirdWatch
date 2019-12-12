package Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bird_table")
public class Bird {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "bird")
    public String bName;
    @ColumnInfo(name = "rarity")
    public String bRarity;
    @ColumnInfo(name = "notes")
    public String bNotes;

    public Bird(String bName, String bRarity, String bNotes) {
        this.bName = bName;
        this.bRarity = bRarity;
        this.bNotes = bNotes;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbRarity() {
        return bRarity;
    }

    public void setbRarity(String bRarity) {
        this.bRarity = bRarity;
    }

    public String getbNotes() {
        return bNotes;
    }

    public void setbNotes(String bNotes) {
        this.bNotes = bNotes;
    }
}
