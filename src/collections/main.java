package collections;

import collections.map.CustomHashMap;
import collections.map.CustomMap;
import dto.Student;

public class main {

    public static void main(String[] args) {
        CustomMap<Integer, Student> map = new CustomHashMap<>();
        map.put(1, new Student(1, "Raz", "Dva"));
        map.put(2, new Student(2, "Raz", "Dva"));
        map.put(3, new Student(2, "Raz1", "Dva1"));
        map.put(6, new Student(2, "Raz1", "Dva1"));
        map.put(2, new Student(2, "Raz1", "Dva1"));
        map.put(2, new Student(2, "Raz1", "Dva1"));

        System.out.println(map.keySet());
        System.out.println(map.get(1));
        System.out.println(map.remove(2));
        System.out.println(map.size());

        CustomMap<String, Student> map2 = new CustomHashMap<>();
        map2.put("ak,hjk", new Student(1, "Raz", "Dva"));
        map2.put("sdf", new Student(2, "Raz", "Dva"));
        map2.put("dfhd", new Student(2, "Raz1", "Dva1"));
        map2.put("fdsgds", new Student(2, "Raz1", "Dva1"));
        map2.put("aghfdf", new Student(2, "Raz1", "Dva1"));
        map2.put("ghgggffgh", new Student(2, "Raz1", "Dva1"));

        System.out.println(map2.keySet());
        System.out.println(map2.size());

        map.removeAll();


    }
}
