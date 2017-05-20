package com.mirea.laba1;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nikitos on 20.05.17.
 */
public class Main {
    public static void main(String[] args) {
        Comparator<? super Map.Entry<String, Integer>> cmp = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };
        Map<String, Integer> hashtable = new ConcurrentHashMap<String, Integer>();
        File textFile = new File("/Users/nikitos/Downloads/test.txt");
        try {
            Scanner scanner = new Scanner(textFile);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (hashtable.containsKey(word)) {
                    Integer count = hashtable.get(word);
                    hashtable.put(word, count + 1);
                } else {
                    hashtable.put(word, 1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(hashtable.entrySet());
        Collections.sort(list, cmp);
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        JFrame frame = new JFrame("График");
        frame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 800, 600);
        MyGraphic myGraphic = new MyGraphic  (list.subList(0, 11), frame.getWidth(), frame.getHeight());
        frame.add(myGraphic);
        frame.setVisible(true);
    }
}
