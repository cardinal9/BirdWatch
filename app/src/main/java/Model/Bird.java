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
    @ColumnInfo(name = "timestamp")
    public String bTimeStamp;

    public Bird(String bName, String bRarity, String bNotes, String bTimeStamp) {
        this.bName = bName;
        this.bRarity = bRarity;
        this.bNotes = bNotes;
        this.bTimeStamp = bTimeStamp;
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

    public String getbTimeStamp() {
        return bTimeStamp;
    }

    public void setbTimeStamp(String bTimeStamp) {
        this.bTimeStamp = bTimeStamp;
    }

}
