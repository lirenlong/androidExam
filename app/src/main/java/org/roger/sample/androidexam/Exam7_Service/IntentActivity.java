package org.roger.sample.androidexam.Exam7_Service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.imagepipeline.cache.BufferedDiskCache;

import org.roger.sample.androidexam.R;

public class IntentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        TextView tv = (TextView) findViewById(R.id.textview);

        Intent i = getIntent();
        LocalActivity.Person person = (LocalActivity.Person) i.getSerializableExtra("person");
        if (person != null) {
            Log.i("IntentActivity", "Age is " + person.getAge());
            tv.setText("Age is " + person.getAge());
        }

        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Book book = (Book) bundle.getParcelable("parcelable");
            if(book != null ) {
                Log.i("IntentActivity", "Author : " + book.getAuthor() + "\nBookName : " + book.getBookName() + "\nPublishTime : " + book.getPublishTime());
                tv.setText("Author : " + book.getAuthor() + "\nBookName : " + book.getBookName() + "\nPublishTime : " + book.getPublishTime());
            }
        }


        Bitmap bm = i.getParcelableExtra("bitmap");
        if(bm != null) {
            ((ImageView) findViewById(R.id.imageview)).setImageBitmap(bm);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
