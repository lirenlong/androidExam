package org.roger.sample.androidexam.Exam6_DataBinding.basic;

/**
 * Created by liren on 15/7/13.
 */
// POJO
//    no implements
//    no extends
//    no annotation
public class User {
    public final String firstName;
    public final String lastName;
    public User(String f, String l) {
        firstName = f;
        lastName = l;
    }
}

// javabean
//    a kind of pojo with serialization
//    has no-argument contructor
//    has getter and setter for all values

//public class User {
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    private final String firstName;
//    private final String lastName;
//
//}
