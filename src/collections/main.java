package collections;

import collections.map.CustomHashMap;
import collections.map.CustomMap;
import dto.Student;

import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args) {
        /*CustomMap<String, Integer> customMap = new CustomHashMap<>();
        customMap.put("Test", 124);
        customMap.put("Test", 123);
        customMap.put("Test", 124);*/
        CustomMap<Integer, Student> map = new CustomHashMap<>();
        map.put(1, new Student(1, "Raz", "Dva"));
        map.put(2, new Student(2, "Raz", "Dva"));
        map.put(3, new Student(2, "Raz1", "Dva1"));
        map.remove(3);

        /*Map<String, Integer> map = new HashMap<>();
        map.put("Test", 123);
        map.put("Test2", 222);
        System.out.println("1");*/
    }
}
