package com.silbytech.mundo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class IntroductionActivity extends AppCompatActivity {
    private ViewPager pager;
    private int[] layouts;
    private LinearLayout dotsLayout;
    private Button btnNext;
    private Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.introduction_layout);
        pager = findViewById(R.id.pager);
        dotsLayout = findViewById(R.id.id_layout_dots);
        btnNext = findViewById(R.id.btnNext);
        this.btnSkip = findViewById(R.id.btnSkip);
        layouts = new int[]{R.layout.screen_1_layout, R.layout.screen_2_layout, R.layout.screen_3_layout};

        addBottomDots(0);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(viewListner);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IntroductionActivity.this, LandingActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if(current < layouts.length) {
                    pager.setCurrentItem(current);
                }
                else {
                    Intent i = new Intent(IntroductionActivity.this, LandingActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }


    private void addBottomDots(int position){
        TextView[] dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i){
        return pager.getCurrentItem() + 1;
    }


    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position == layouts.length - 1){
                btnNext.setText("PROCEED");
                btnSkip.setVisibility(View.GONE);
            }
            else {
                btnNext.setText("NEXT");
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    /*************************************************************************
     * This inner class will be the ViewPagerAdapter
     *************************************************************************/
    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}

