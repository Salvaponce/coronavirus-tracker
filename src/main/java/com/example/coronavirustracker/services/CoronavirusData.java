package com.example.coronavirustracker.services;

import com.example.coronavirustracker.models.PlacesStats;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusData {

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<PlacesStats> stats = new ArrayList<>();

    public List<PlacesStats> getStats() {
        return stats;
    }

    @PostConstruct
    @Scheduled(cron = "* * * 1 * * ")
    public void getVirusData() throws IOException, InterruptedException {
        List<PlacesStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader reader = new StringReader(httpResponse.body()); //Reader instance
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(reader);

        for (CSVRecord record : records) {
            PlacesStats placesStat = new PlacesStats();
            placesStat.setState(record.get("Province/State"));
            placesStat.setCountry(record.get("Country/Region"));
			int lastRecord = Integer.parseInt(record.get(record.size() - 1));
			int previousDayRecord = Integer.parseInt(record.get(record.size() - 2));
            placesStat.setLatestRecord(lastRecord);
			placesStat.setIncresedCases(lastRecord - previousDayRecord);
            System.out.println(placesStat);
            newStats.add(placesStat);
        }
        this.stats = newStats;
    }
}
