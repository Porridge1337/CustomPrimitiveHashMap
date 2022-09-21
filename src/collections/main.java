package collections;

import collections.map.CustomHashMap;
import collections.map.CustomMap;
import dto.Student;

import java.util.Map;
import java.util.TreeMap;

public class main {

    public static void main(String[] args) {

        Map<String, String> tree = new TreeMap<>();

        CustomMap<Integer, Student> map = new CustomHashMap<>();
        map.put(1, new Student(1, "Raz", "Dva"));
        map.put(2, new Student(2, "Raz", "Dva"));
        map.put(3, new Student(2, "Raz1", "Dva1"));
        map.put(6, new Student(2, "Raz1", "Dva1"));
        map.put(5, new Student(2, "Raz1", "Dva1"));
        map.put(4, new Student(2, "Raz1", "Dva1"));

        //System.out.println(map.keys());
        System.out.println(map.get(1));
        System.out.println(map.remove(null));
        System.out.println(map.size());

        CustomMap<String, Student> map2 = new CustomHashMap<>();
        map2.put("ak,hjk", new Student(1, "Raz", "Dva"));
        map2.put("sdf", new Student(2, "Raz", "Dva"));
        map2.put("dfhd", new Student(2, "Raz1", "Dva1"));
        map2.put("fdsgds", new Student(2, "Raz1", "Dva1"));
        map2.put("aghfdf", new Student(2, "Raz1", "Dva1"));
        map2.put("ghgggffgh", new Student(2, "Raz1", "Dva1"));

        System.out.println(map2.keys());
        System.out.println(map2.size());

        map.removeAll();


    }
}
