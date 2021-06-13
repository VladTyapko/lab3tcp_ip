package sample;

import java.io.Serializable;

public class Station implements Serializable {
    public int station_id;
    public String name;

    public Station(int station_id, String name) {
        this.station_id = station_id;
        this.name = name;
    }
}
