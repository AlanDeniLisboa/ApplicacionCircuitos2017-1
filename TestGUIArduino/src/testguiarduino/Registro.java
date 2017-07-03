package testguiarduino;

import java.io.Serializable;

/**
 * Created by corperk on 03/07/17.
 */
public class Registro implements Serializable {

    private String id;
    private String date;

    public Registro(String id, String date){
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}