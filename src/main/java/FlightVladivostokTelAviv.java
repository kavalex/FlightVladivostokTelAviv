import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.OptionalDouble;

public class FlightVladivostokTelAviv {
    public static ArrayList<Long> flightTime = new ArrayList<>();
    public static int percentile = 90;

    public static void main(String[] args) {
        String path = args[0];
        File fileJson = new File(path);
        getFlightTime(fileJson);
        output();
    }
    private static void getFlightTime(File fileJson) {
        ObjectMapper mapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
        try {
            JsonNode rootNode = mapper.readTree(fileJson);
            JsonNode ticketsNode = rootNode.path("tickets");
            Iterator ticketsIterator = ticketsNode.elements();
            while (ticketsIterator.hasNext()) {
                JsonNode ticketNode = mapper.readTree(ticketsIterator.next().toString());
                LocalDateTime departure = getLocalDateTime(ticketNode, "departure_date", "departure_time", formatter);
                LocalDateTime arrival = getLocalDateTime(ticketNode, "arrival_date", "arrival_time", formatter);
                flightTime.add(Duration.between(departure, arrival).toMinutes());
            }
            Collections.sort(flightTime);
            for(Long counter: flightTime){
                System.out.println(counter);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static LocalDateTime getLocalDateTime(JsonNode jsonNode, String dateNode, String timeNode, DateTimeFormatter formatter) {
        String date = jsonNode.path(dateNode).toString().replace("\"", "");
        String time = jsonNode.path(timeNode).toString().replace("\"", "");
        return LocalDateTime.parse(date + " " + time, formatter);
    }

    private static void output() {
        OptionalDouble averageFlightTime = flightTime.stream().mapToLong(e -> e).average();
        int percentileFlightTime = (int) Math.round(((float) percentile/100)*flightTime.size());
        System.out.println();
        System.out.println("Перелет Владивосток - Тель-Авив");
        System.out.println("-------------------------------");
        System.out.println("1. Среднее время: " + averageFlightTime.getAsDouble() + " минут");
        System.out.println("2. " + percentile + "-й процентиль: " + flightTime.get(percentileFlightTime - 1));
        System.out.println("-------------------------------");
    }
}
