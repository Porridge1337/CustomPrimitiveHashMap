package collections;

import collections.map.CustomHashMap;
import collections.map.CustomMap;
import dto.Student;

import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args) {
        CustomMap<Integer, Student> map = new CustomHashMap<>();
        map.put(1, new Student(1, "Raz", "Dva"));
        map.put(2, new Student(2, "Raz", "Dva"));
        map.put(3, new Student(2, "Raz1", "Dva1"));
        System.out.println(map.get(1));
        System.out.println(map.remove(2));
        System.out.println(map.size());
        map.removeAll();

    }
}
