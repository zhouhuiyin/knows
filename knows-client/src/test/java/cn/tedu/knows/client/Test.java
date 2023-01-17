package cn.tedu.knows.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        List<String> list = Stream.of("ROLE_STUDENT","/index.html","/question/create").collect(Collectors.toList());

        String[] auth = list.toArray(new String[0]);

        System.out.println(Arrays.toString(auth));
        List<String> list1 = new ArrayList<>();
        list1.add("test1");
        list1.add("test2");
        Iterator<String> iterator = list1.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        FileWriter file = new FileWriter("/Users/zhuyahui/feishu/jmx/url.txt",true);
        BufferedWriter out = new BufferedWriter(file);
        String str = "https://git.meetsocial.cn/automation-test/mstf_cb_test/-/merge_requests/19";
        out.write(str+"\r\n");
        out.close();
        file.close();





    }
}
