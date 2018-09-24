package rubikcube.moves;

/**
 *
 * @author jpereda, April 2014 - @JPeredaDnr
 */
public class Move{
    private String face;
    private long timestamp;
    private String tiempoMov;
    
    public Move(String face, long timestamp){
        this.face=face;
        this.timestamp=timestamp;
    }

    public Move(String face, String tiempoMov) {
        this.face = face;
        this.tiempoMov = tiempoMov;
    }
    
    
    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Move{" + "face=" + face + ", timestamp=" + timestamp + '}';
    }

    public String getTiempoMov() {
        return tiempoMov;
    }

    public void setTiempoMov(String tiempoMov) {
        this.tiempoMov = tiempoMov;
    }

}
