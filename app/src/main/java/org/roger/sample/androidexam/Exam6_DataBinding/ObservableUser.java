package org.roger.sample.androidexam.Exam6_DataBinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.roger.sample.androidexam.BR;

/**
 * Created by liren on 15/7/13.
 */
public class ObservableUser extends BaseObservable {
    private String firstName;
    private String lastName;

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }
}