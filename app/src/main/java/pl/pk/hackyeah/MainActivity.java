package pl.pk.hackyeah;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.sliderViewPager) ViewPager viewPager;
//    @ViewById(R.id.dotsLayout) LinearLayout linearLayout;

    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sliderAdapter = new SliderAdapter(MainActivity.this);

        viewPager.setAdapter(sliderAdapter);
    }

    @AfterViews
    public void init(){

    }

}
