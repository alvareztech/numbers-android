package tech.alvarez.numbers.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class QueryEntity {

    @PrimaryKey
    private String query;
    private Date date;
}
