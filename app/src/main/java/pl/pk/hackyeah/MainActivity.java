package pl.pk.hackyeah;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import pl.pk.hackyeah.ui.CallAmbulanceActivity;
import pl.pk.hackyeah.ui.CallAmbulanceActivity_;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    public void init(){

    }
    @Click(R.id.phone)
    public void onPhoneClick(){
        CallAmbulanceActivity_.intent(MainActivity.this).start();    }
}
