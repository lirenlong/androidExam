package org.roger.sample.androidexam.Exam6_DataBinding.Observable;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by liren on 15/7/15.
 */
public class PlainUser {
    public ObservableField<String> firstName = new ObservableField<>();
    public ObservableField<String> lastName = new ObservableField<>();
    public ObservableInt grade = new ObservableInt();
}
