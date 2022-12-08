package cn.tedu.knows.client;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        List<String> list = Stream.of("ROLE_STUDENT","/index.html","/question/create").collect(Collectors.toList());
        System.out.println(list);
        String[] auth = list.toArray(new String[0]);
        System.out.println(auth);
    }
}
