package tech.alvarez.numbers.models.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Daniel Alvarez on 8/11/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class SearchQueryRealm extends RealmObject {

    @PrimaryKey
    private String query;
    private Date date;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
