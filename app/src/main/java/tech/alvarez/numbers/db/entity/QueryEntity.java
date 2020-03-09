package tech.alvarez.numbers.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class QueryEntity {

    @PrimaryKey
    private String query;
    private Date date;
}
