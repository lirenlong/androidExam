package org.roger.sample.androidexam.Exam10_hotfix;

/**
 * Created by liren on 16/1/12.
 */
public class hookedClass {

    public static String stringAppendChar(String s, char c) {
        if( s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder(s).append(c);
        sb.append("-ello");
        return sb.toString();
    }
}
