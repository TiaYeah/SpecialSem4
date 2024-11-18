import java.util.*;

public class BaseSolver {
    private Map<Integer, ResourceState> resourcesState = new HashMap<>();
    TreeSet<Integer> events = new TreeSet<>();
    private List<Integer> previousDelays = new ArrayList<>();

    private void init(int m, int k, List<Order> orders) {
        for (int i = 0; i < k; i++) {
            events.add(orders.get(i).getStartTime());
        }

        for (int i = 1; i <= m; i++) {
            resourcesState.put(i, new ResourceState(-1, null)); // -1 - свободен иначе - время освобождения
        }
    }

    public int solve(int m, int k, List<Order> orders, SortingStrategy strategy) {
        init(m, k, orders);

        while (!events.isEmpty()) {
            List<Operation> front;
            //new Comparator<Integer>
            while (!(front = frontalAlgorithm(events.getFirst(), orders)).isEmpty()) {

                frontSort(front, orders, strategy, events.getFirst());
                Operation operationToStart = front.getFirst();
                Order orderOfOperation = orders.get(operationToStart.getOrderId());

                Integer resourceFreeTime = events.getFirst() + orderOfOperation.getDuration().get(operationToStart.getNumber());
                events.add(resourceFreeTime);

                //orderOfOperation.getEdges().remove(operationToStart.getNumber());
                resourcesState.put(orderOfOperation.getResources().get(operationToStart.getNumber()), new ResourceState(resourceFreeTime, operationToStart));

                //System.out.println(resourcesState);
                if (front.size() == 1) {
                    break;
                }
            }
            events.removeFirst();
        }

        int sumOfDelay = 0;

        for (int i = 0; i < orders.size(); i++) {
            int delay = orders.get(i).getCompletionTime() > orders.get(i).getDirectiveTime() ? orders.get(i).getCompletionTime() - orders.get(i).getDirectiveTime() : 0;
            //System.out.println("Заказ №" + (i + 1) + " Просрочка " + delay);
            sumOfDelay += delay;
        }

        //System.out.println("Заказы " + orders);
        return sumOfDelay;
    }

    public List<Integer> solve(int m, int k, List<Order> orders, SortingStrategy strategy, List<Integer> previousDelays) {
        init(m, k, orders);
        this.previousDelays = previousDelays;

        while (!events.isEmpty()) {
            List<Operation> front;
            //new Comparator<Integer>
            while (!(front = frontalAlgorithm(events.getFirst(), orders)).isEmpty()) {

                frontSort(front, orders, strategy, events.getFirst());
                Operation operationToStart = front.getFirst();
                Order orderOfOperation = orders.get(operationToStart.getOrderId());

                Integer resourceFreeTime = events.getFirst() + orderOfOperation.getDuration().get(operationToStart.getNumber());
                events.add(resourceFreeTime);

                //orderOfOperation.getEdges().remove(operationToStart.getNumber());
                resourcesState.put(orderOfOperation.getResources().get(operationToStart.getNumber()), new ResourceState(resourceFreeTime, operationToStart));

                //System.out.println(resourcesState);
                if (front.size() == 1) {
                    break;
                }
            }
            events.removeFirst();
        }

        List<Integer> delays = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            int delay = orders.get(i).getCompletionTime() > orders.get(i).getDirectiveTime() ? orders.get(i).getCompletionTime() - orders.get(i).getDirectiveTime() : 0;
            //System.out.println("Заказ №" + (i + 1) + " Просрочка " + delay);
            delays.add(delay);
        }

        //System.out.println("Заказы " + orders);
        return delays;
    }

    private void frontSort(List<Operation> front, List<Order> orders, SortingStrategy strategy, int currentTime) {
        if (strategy == SortingStrategy.MinTD) {
            front.sort((op1, op2) -> orders.get(op1.getOrderId()).getDirectiveTime() - orders.get(op2.getOrderId()).getDirectiveTime());
        } else if (strategy == SortingStrategy.RANDOM) {
            List<Integer> indexesList = generateIndexes(front.size());
            List<Operation> newFront = new ArrayList<>();

            for (int i = 0; i < front.size(); i++) {
                newFront.add(front.get(indexesList.get(i)));
            }

            for (int i = 0; i < front.size(); i++) {
                front.set(i, newFront.get(i));
            }
        } else if (strategy == SortingStrategy.BiggestNextVertexes) {
            Map<Integer, Integer> remainingOperations = new HashMap<>();
            for (int i = 0; i < orders.size(); i++) {
                remainingOperations.put(i, orders.get(i).getVertexes().size());
            }


            //front.sort(Comparator.comparingInt(op -> orders.get(op.getOrderId()).getDirectiveTime() - (currentTime + orders.get(op.getOrderId()).getDuration().get(op.getNumber()))));
            //front.sort(Comparator.comparingInt(op -> remainingOperations.get(op.getOrderId())));

            //double k1 = 0.4, k2 = 0.3,  k3 = 1 - k1 - k2; // на 0.75 и сортировке k2 через остаток операций есть улучшение


            double k1 = 0.5, k2 = 1 - k1; // dt + remain 0,5 0,5 маленькое улучшение 075 025 лучше 0978
            front.sort((op1, op2) -> {
                List<Integer> edges1 = orders.get(op1.getOrderId()).getEdges().get(op1.getNumber());
                List<Integer> edges2 = orders.get(op2.getOrderId()).getEdges().get(op2.getNumber());

                double priority1 = k1 * orders.get(op1.getOrderId()).getDirectiveTime()
                        + -k2 * (edges1 == null ? 0 : edges1.size());
                       // + k2 *
                                 //+ k2 * orders.get(op1.getOrderId()).getDuration().get(op1.getNumber())
                                 //+ k2 * (remainingOperations.get(op1.getOrderId()));
                double priority2 = k1 * orders.get(op2.getOrderId()).getDirectiveTime()
//                                 //+ k2 * orders.get(op1.getOrderId()).getDuration().get(op1.getNumber())
                        + -k2 * (edges2 == null ? 0 : edges2.size());
                                 //+ k2 * (remainingOperations.get(op2.getOrderId()));

                return Double.compare(priority1, priority2);
            });
        } else if (strategy == SortingStrategy.DelayIteration) {
            Map<Integer, Integer> remainingOperations = new HashMap<>();
            for (int i = 0; i < orders.size(); i++) {
                remainingOperations.put(i, orders.get(i).getVertexes().size());
            }

            double k1 = 0.8, k2 = 1 - k1; // пока 0.8 деление + пути разогнал до 0.955

            front.sort((op1, op2) -> {
                List<Integer> edges1 = orders.get(op1.getOrderId()).getEdges().get(op1.getNumber());
                List<Integer> edges2 = orders.get(op2.getOrderId()).getEdges().get(op2.getNumber());

                double priority1 =  -1 * k1 * previousDelays.get(op1.getOrderId()) / remainingOperations.get(op1.getOrderId())
                        + -k2 * (edges1 == null ? 0 : edges1.size());
                double priority2 = -1 * k1 * previousDelays.get(op2.getOrderId()) / remainingOperations.get(op2.getOrderId())
                        + -k2 * (edges2 == null ? 0 : edges2.size());

                //Иногда сработает
                //double priority1 =  -1 * ((double) previousDelays.get(op1.getOrderId()) / remainingOperations.get(op1.getOrderId()));

                //double priority2 = -1 * ((double) previousDelays.get(op2.getOrderId()) / remainingOperations.get(op2.getOrderId()));
                return Double.compare(priority1, priority2);
            });

            //front.sort((op1, op2) -> -1 * (previousDelays.get(op1.getOrderId()) - previousDelays.get(op2.getOrderId())));
        }
    }

    public List<Operation> frontalAlgorithm(int time, List<Order> orders) {
        List<Operation> result = new ArrayList<>();
        //Map<Integer, ArrayList<Integer>> reverse
        for (Map.Entry<Integer, ResourceState> entry : resourcesState.entrySet()) {
            ResourceState entryResourceState = entry.getValue();
            if (entryResourceState.getFreeTime() == time) {
                Operation entryOperation = entryResourceState.getOperation();
                Order entryOrder = orders.get(entryOperation.getOrderId());

                entryOrder.getEdges().remove(entryOperation.getNumber());
                entryOrder.getVertexes().remove(entryOperation.getNumber());
                if (entryOrder.getVertexes().isEmpty()) {
                    entryOrder.setCompletionTime(time);
                }
                entryResourceState.setFreeTime(-1);
                entryResourceState.setOperation(null);
            }
        }


        for (int i = 0; i < orders.size(); i++) {
            Order currentOrder = orders.get(i);
            boolean readyToStart = true;

            for (int j = 0; j < currentOrder.getVertexes().size(); j++) {
                readyToStart = true;
                for (Map.Entry<Integer, List<Integer>> entry : currentOrder.getEdges().entrySet()) {
                    if (entry.getValue().contains(currentOrder.getVertexes().get(j))) {
                        readyToStart = false;
                    }
                }
                if (resourcesState.get(currentOrder.getResources().get(currentOrder.getVertexes().get(j))).getFreeTime() != -1){
                    readyToStart = false;
                }

                if (orders.get(i).getStartTime() > time) {
                    readyToStart = false;
                }

                if (readyToStart) {
                    result.add(new Operation(currentOrder.getVertexes().get(j), i));
                }
            }
        }
        //System.out.println("Время " + time);
        //System.out.println("Результат " + result);

        return result;
    }

    private List<Integer> generateIndexes(int n) {
        List<Integer> genList = new ArrayList<>();
        Random random = new Random();

        while (genList.size() != n) {
            int randNumber = random.nextInt(n);
            if (!genList.contains(randNumber)) {
                genList.add(randNumber);
            }
        }

        return genList;
    }
}
