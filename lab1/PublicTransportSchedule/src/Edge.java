import java.time.LocalTime;

public class Edge {
    private final int id;
    private final String company;
    private final String line;
    private final String startStop;
    private final LocalTime departureTime;
    private final String endStop;
    private final LocalTime arrivalTime;
    private final double startLatitude;
    private final double startLongitude;
    private final double endLatitude;
    private final double endLongitude;

    public Edge(int id, String company, String line, String startStop, LocalTime departureTime, String endStop,
                LocalTime arrivalTime, double startLatitude, double startLongitude,
                double endLatitude, double endLongitude) {
        this.id = id;
        this.company = company;
        this.line = line;
        this.startStop = startStop;
        this.departureTime = departureTime;
        this.endStop = endStop;
        this.arrivalTime = arrivalTime;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
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

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
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
                ", startLatitude=" + startLatitude +
                ", startLongitude=" + startLongitude +
                ", endLatitude=" + endLatitude +
                ", endLongitude=" + endLongitude +
                '}';
    }
}
