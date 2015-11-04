package org.roger.sample.androidexam.Exam6_DataBinding.Observable;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

/**
 * Created by liren on 15/7/15.
 */
public class PlainUser {
    @NonNull
    public ObservableField<String> firstName = new ObservableField<>();
    @NonNull
    public ObservableField<String> lastName = new ObservableField<>();
    @NonNull
    public ObservableInt grade = new ObservableInt();
}
