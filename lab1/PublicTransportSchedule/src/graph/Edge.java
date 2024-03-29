package graph;

import java.time.LocalTime;

public class Edge {
    private final int id;
    private final String company;
    private final String line;
    private final String startStop;
    private final LocalTime departureTime;
    private final String endStop;
    private final LocalTime arrivalTime;


    public Edge(int id, String company, String line, String startStop, LocalTime departureTime, String endStop,
                LocalTime arrivalTime) {
        this.id = id;
        this.company = company;
        this.line = line;
        this.startStop = startStop;
        this.departureTime = departureTime;
        this.endStop = endStop;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public String getLine() {
        return line;
    }

    public String getStartStop() {
        return startStop;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getEndStop() {
        return endStop;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", line='" + line + '\'' +
                ", startStop='" + startStop + '\'' +
                ", departureTime=" + departureTime +
                ", endStop='" + endStop + '\'' +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public int compareTo(Edge edge) {
        return this.getDepartureTime().compareTo(edge.getDepartureTime());
    }

}
