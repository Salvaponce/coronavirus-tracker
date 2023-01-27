package com.example.coronavirustracker.models;

public class PlacesStats {
    private String state;
    private String country;
    private int latestRecord;
	private int incresedCases;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestRecord() {
        return latestRecord;
    }

    public void setLatestRecord(int latestRecord) {
        this.latestRecord = latestRecord;
    }
	
	public int getIncresedCases() {
        return incresedCases;
    }

    public void setIncresedCases(int incresedCases) {
        this.incresedCases = incresedCases;
    }

    @Override
    public String toString() {
        return "PlacesStats{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", latestRecord=" + latestRecord +
                ", incresedCases=" + incresedCases +
                '}';
    }
}
