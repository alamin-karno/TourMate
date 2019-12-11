package com.teamblank.tourmate.model_class;

public class Trip {
    private String userid,triptitle,startlocation,triplocation,startdate,enddate,tripdetails,tripbudget;

    public Trip() {
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Trip(String triptitle, String startlocation, String triplocation, String startdate, String enddate, String tripdetails, String tripbudget) {
        this.triptitle = triptitle;
        this.startlocation = startlocation;
        this.triplocation = triplocation;
        this.startdate = startdate;
        this.enddate = enddate;
        this.tripdetails = tripdetails;
        this.tripbudget = tripbudget;
    }

    public Trip(String userid, String triptitle, String startlocation, String triplocation, String startdate, String enddate, String tripdetails, String tripbudget) {
        this.userid = userid;
        this.triptitle = triptitle;
        this.startlocation = startlocation;
        this.triplocation = triplocation;
        this.startdate = startdate;
        this.enddate = enddate;
        this.tripdetails = tripdetails;
        this.tripbudget = tripbudget;
    }

    public String getUserid() {
        return userid;
    }

    public String getTriptitle() {
        return triptitle;
    }

    public String getStartlocation() {
        return startlocation;
    }

    public String getTriplocation() {
        return triplocation;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getTripdetails() {
        return tripdetails;
    }

    public String getTripbudget() {
        return tripbudget;
    }
}
