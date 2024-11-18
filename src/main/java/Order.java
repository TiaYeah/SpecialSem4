import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@ToString

public class Order {
    private List<Integer> vertexes;
    private Map<Integer, List<Integer>> edges;
    private Map<Integer, Integer> resources;
    private Map<Integer, Integer> duration;
    private int startTime;
    private int directiveTime;
    private int completionTime;

    public Order(List<Integer> vertexes, Map<Integer, List<Integer>> edges, Map<Integer, Integer> resources, Map<Integer, Integer> duration, int startTime, int directiveTime) {
        this.vertexes = vertexes;
        this.edges = edges;
        this.resources = resources;
        this.duration = duration;
        this.startTime = startTime;
        this.directiveTime = directiveTime;
    }
}
