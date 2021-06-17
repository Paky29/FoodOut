package model.utility;

import model.rider.Rider;

public class RiderSession {
    private final int id;

    public RiderSession(Rider rd){
        this.id=rd.getCodice();
    }

    public int getId() {
        return id;
    }
}
