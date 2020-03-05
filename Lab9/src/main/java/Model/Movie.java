package Model;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private int director_id;
    private ArrayList<Actor> actors = new ArrayList<>();

    public Movie(int id, String name, int director_id) {
        this.id = id;
        this.name = name;
        this.director_id = director_id;
    }

    public void addActor(Actor actor){
        actors.add(actor);
    }
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director_id=" + director_id +
                '}';
    }
}
