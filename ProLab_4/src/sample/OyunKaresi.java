package sample;

import java.util.ArrayList;

public class OyunKaresi {
    private int id;
    private Boolean available;
    public ArrayList<Integer> komsular;
    public OyunKaresi birOnceki;
    private int x;

    public OyunKaresi(){
        komsular = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setXY(){
        setX(getId() % 13);
        setY(getId() / 13);
    }

}
