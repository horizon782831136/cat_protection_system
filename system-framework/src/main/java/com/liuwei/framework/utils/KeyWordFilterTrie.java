package com.liuwei.framework.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

public class KeyWordFilterTrie {
    private static TrieNode root;
    private static BufferedReader bufferedReader;

    // 私有构造方法，避免外部实例化
    private KeyWordFilterTrie() {
    }

    // 静态初始化代码块，用于初始化 Trie 树和关键字列表
    static {
        root = new TrieNode();
        try {
            // 使用BufferedReader从文件中读取关键字列表
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(".\\system-framework" +
                    "\\src\\main\\java\\com\\liuwei\\framework\\utils\\keyword.txt")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.trim().split("\\s+");
                for (String word : words) {
                    insert(word.trim());
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向 Trie 中插入关键词
    private static void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    // 进行关键词过滤
    public static String filterKeywords(String text) {
        StringBuilder filteredText = new StringBuilder();
        TrieNode current = root;

        int start = 0;
        int end = 0;

        while (end < text.length()) {
            char c = text.charAt(end);
            if (current.children.containsKey(c)) {
                current = current.children.get(c);
                end++;
                if (current.isEndOfWord) {
                    // 将匹配到的关键词替换为 *
                    filteredText.append("*".repeat(end - start));
                    start = end;
                    current = root;
                }
            } else {
                // 如果没有找到匹配的字符，从下一个字符开始重新匹配
                filteredText.append(text.charAt(start));
                start++;
                end = start;
                current = root;
            }
        }

        // 将剩余的文本添加到过滤后的文本中
        filteredText.append(text.substring(start));

        return filteredText.toString();
    }

    public static void main(String[] args) {
        String text = "这是一个包含色情和不当内容的文本习近平。";

        String filteredText = KeyWordFilterTrie.filterKeywords(text);
        System.out.println(filteredText);  // 输出：这是一个包含******和******的文本。
    }
}
