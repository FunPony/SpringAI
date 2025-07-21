package com.xjtu.util;

import java.util.List;

public class QuestionDetector {

    private static final List<String> QUESTION_WORDS = List.of(
            "什么", "如何", "怎样", "是不是", "是否", "为何", "为什么", "谁", "哪", "能否", "可以吗", "吗", "嘛", "呢", "有何", "请问", "能不能"
    );

    // 判断是否为问题
    public static boolean isQuestion(String text) {
        if (text == null || text.isBlank()) return false;
        for (String word : QUESTION_WORDS) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}

