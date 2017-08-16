package com.example.vb.test_gson_retrofit_realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.Required;

/**
 * Created by VB on 10.08.2017.
 */

public class Clubs{
    @Required
    @SerializedName("clubs")
    @Expose
    private RealmList<Club> clubs = null;

    public RealmList<Club> getClubs() {
        return clubs;
    }

    public void setClubs(RealmList<Club> clubs) {
        this.clubs = clubs;
    }
}
