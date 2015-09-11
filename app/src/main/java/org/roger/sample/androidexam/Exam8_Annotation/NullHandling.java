package org.roger.sample.androidexam.Exam8_Annotation;

import android.support.annotation.NonNull;
//import edu.umd.cs.findbugs.annotations;
//import javax.annotation.CheckForNull;

import java.util.Locale;

/**
 * Created by liren on 15/9/11.
 */
//public class NullHandling {
//    public String value;
//    public int getValueLength() {
//        return this.value.length();
//    }
//    public String myToUppercase( String parameter )
//    {
//        if( parameter == null )
//            return "";
//        return parameter.toUpperCase(Locale.getDefault());
//    }
//
//    public String getValue() {
//        return this.value;
//    }
//    public void justUseGetValue() {
//        int length;
//        if( getValue() == null )
//            length = 0;
//        else
//            length = getValue().length();
//        System.out.println( "Value length: " + length);
//    }
//    public String justUseMyToUppercase() {
//        return myToUppercase(null);
//    }
//}

public class NullHandling {
//    @CheckForNull
    public String value;
    public int getValueLength() {
        return this.value.length();
    }
    @NonNull
    public String myToUppercase(@NonNull String parameter )
    {
        if( parameter == null )
            return "";
        return parameter.toUpperCase(Locale.getDefault());
    }

    @NonNull
    public String getValue() {
        return this.value;
    }
    public void justUseGetValue() {
        int length;
        if( getValue() == null )
            length = 0;
        else
            length = getValue().length();
        System.out.println( "Value length: " + length);
    }
    @NonNull
    public String justUseMyToUppercase() {
//        return myToUppercase(null);
        value = null;//这类问题不行。
        return myToUppercase(getValue());
    }
}