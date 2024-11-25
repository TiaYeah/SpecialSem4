import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class TestTask4 {
    private Scanner scanner;
    private static List<Double> baseResults = new ArrayList<>();
    private static List<Double> baseIterResults = new ArrayList<>();
    private static List<Double> ownResults = new ArrayList<>();
    private static List<Double> ownIterResults = new ArrayList<>();

    @Test
    public void test01() {
        File file = new File("src/main/resources/task_1_n10_m2_k2.txt");
        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);
        
        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test02() {
        File file = new File("src/main/resources/task_2_n40_m4_k3.txt");
        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test03() {
        File file = new File("src/main/resources/task_3_n90_m6_k4.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test04() {
        File file = new File("src/main/resources/task_4_n160_m8_k5.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test05() {
        File file = new File("src/main/resources/task_5_n250_m10_k6.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test06() {
        File file = new File("src/main/resources/task_6_n360_m12_k7.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test07() {
        File file = new File("src/main/resources/task_7_n490_m14_k8.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);

    }

    @Test
    public void test08() {
        File file = new File("src/main/resources/task_8_n640_m16_k9.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);

    }

    @Test
    public void test09() {
        File file = new File("src/main/resources/task_9_n810_m18_k10.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }

        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();

        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
    }

    @Test
    public void test10() {
        File file = new File("src/main/resources/task_10_n1000_m20_k11.txt");

        List<Order> orders = new ArrayList<>();
        ImmutablePair<Integer, Integer> mAndK = getDataFromFile(file, orders);
        List<Integer> localBaseIterResults = new ArrayList<>();
        List<Integer> localOwnIterResults = new ArrayList<>();

        //System.out.println(orders);

        Solver baseSolver = new Solver();
        int baseResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.MinTD);
        localBaseIterResults.add(baseResult);
        for (int i = 0; i < mAndK.right; i++) {
            int randResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.RANDOM);
            localBaseIterResults.add(randResult);
        }
        
        int baseIterRecord = localBaseIterResults.stream().min(Integer::compareTo).get();

        int ownResult = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.BiggestNextVertexes);

        List<Integer> currentDelays = new ArrayList<>();
        for (int i = 0; i < mAndK.right; i++) {
            currentDelays.add(0);
        }


        for (int i = 0; i < mAndK.right; i++) {
            currentDelays = baseSolver.solve(mAndK.left, mAndK.right, copyOrders(orders), SortingStrategy.DelayIteration, currentDelays);
            localOwnIterResults.add(currentDelays.stream().mapToInt(a -> a).sum());
        }
        
        int ownIterResult = localOwnIterResults.stream().min(Integer::compareTo).get();
        
        System.out.println(baseResult);
        System.out.println(baseIterRecord);
        System.out.println(ownResult);
        System.out.println(ownIterResult);
        baseResults.add((double) baseResult / baseResult);
        baseIterResults.add((double) baseIterRecord / baseResult);
        ownResults.add((double) ownResult / baseResult);
        ownIterResults.add((double) ownIterResult / baseResult);

        
        System.out.println("Базовая просрочка " + (double) baseResult / baseResult);
        System.out.println("Базовая итерационная просрочка " +  (double) baseIterRecord / baseResult);

        System.out.println("Своя просрочка " + (double) ownResult / baseResult);
        System.out.println("Своя итерационная просрочка " + (double) ownIterResult / baseResult);
        System.out.println("Базовая средняя просрочка " + baseResults.stream().mapToDouble(a -> a).average().getAsDouble());
        System.out.println("Базовая средняя итерационная просрочка " + baseIterResults.stream().mapToDouble(a -> a).average().getAsDouble());

        System.out.println("Своя средняя просрочка " + ownResults.stream().mapToDouble(a -> a).average().getAsDouble());
        System.out.println("Своя средняя итерационная просрочка " + ownIterResults.stream().mapToDouble(a -> a).average().getAsDouble());
    }

    private ImmutablePair<Integer, Integer> getDataFromFile(File file, List<Order> orders) {
        try {
            scanner = new Scanner(file);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int k = scanner.nextInt();

            Pattern p = Pattern.compile("[GAV].*");

            for (int i = 0; i < k; i++) {
                while (scanner.hasNext(p)) {
                    scanner.nextLine(); // Пропускаем строку
                }
                String[] stringV = scanner.nextLine().split(" ");
                List<Integer> V = new ArrayList<>();

                for (int j = 0; j < stringV.length; j++) {
                    V.add(Integer.parseInt(stringV[j]));
                }
                //System.out.println(V);
                scanner.nextLine();
                String[] stringA = scanner.nextLine().split(" ");
                Map<Integer, List<Integer>> A = getIntegerListMap(stringA);
                //System.out.println(A);
                scanner.nextLine();
                String[] stringR = scanner.nextLine().split(" ");

                Map<Integer, Integer> r = new HashMap<>();

                scanner.nextLine();
                String[] stringT = scanner.nextLine().split(" ");
                Map<Integer, Integer> t = new HashMap<>();


                for (int j = 0; j < stringR.length; j++) {
                    Map<Integer, Boolean> temp = new HashMap<>();
                    temp.put(Integer.parseInt(stringR[j]), false);
                    //r.add(temp);
                    r.put(V.get(j), Integer.parseInt(stringR[j]));
                    t.put(V.get(j), Integer.parseInt(stringT[j]));
                }
                //System.out.println(r);
                //System.out.println(t);
                int tRN, tD;

                scanner.nextLine();
                tRN = scanner.nextInt();
                scanner.nextLine();
                scanner.nextLine();
                tD = scanner.nextInt();
                scanner.nextLine();
                //System.out.println(tRN);
                //System.out.println(tD);
                orders.add(new Order(V, A, r, t, tRN, tD));
            }
            scanner.close();

            return new ImmutablePair<>(m, k);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<Integer, List<Integer>> getIntegerListMap(String[] stringA) {
        Map<Integer, List<Integer>> A = new HashMap<>();

        for (String string : stringA) {
            String[] currentWay = string.replace("(", "").replace(")", "").split(",");
            List<Integer> endpoints = A.get(Integer.parseInt(currentWay[0]));
            if (endpoints == null) {
                endpoints = new ArrayList<>();
            }

            endpoints.add(Integer.parseInt(currentWay[1]));

            A.put(Integer.parseInt(currentWay[0]), endpoints);
        }
        return A;
    }

    private static List<Order> copyOrders(List<Order> orders) {
        List<Order> ordersCopy = new ArrayList<>();

        for (Order order : orders) {
            List<Integer> copyVertexes = new ArrayList<>(order.getVertexes());

            Map<Integer, List<Integer>> copyEdges = new HashMap<>();

            for (Map.Entry<Integer, List<Integer>> entry : order.getEdges().entrySet()) {
                List<Integer> endpoints = new ArrayList<>(entry.getValue());
                copyEdges.put(entry.getKey(), endpoints);
            }

            ordersCopy.add(new Order(copyVertexes, copyEdges, order.getResources(), order.getDuration(), order.getStartTime(), order.getDirectiveTime()));
        }

        return ordersCopy;
    }
}
