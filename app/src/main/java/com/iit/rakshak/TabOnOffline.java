package com.iit.rakshak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TabOnOffline extends AppCompatActivity
{
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_on_offline);
        CardView cardview_online = (CardView)findViewById(R.id.cardview_online);
        CardView cardview_offline = (CardView)findViewById(R.id.cardview_offline);
        cardview_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPrefManager.getInstance(TabOnOffline.this).isLoggedIn()) {
                    finish();
                   // startActivity(new Intent(TabOnOffline.this, Primary.class));
                     i = new Intent(TabOnOffline.this, Primary.class);
                    i.putExtra("tab", "online");
                    startActivity(i);
                    return;
                }
                else
                    //startActivity(new Intent(TabOnOffline.this, SigninActivity.class));
                 i = new Intent(TabOnOffline.this, SigninActivity.class);
                i.putExtra("tab", "online");
                startActivity(i);
                finish();
            }
        });
        cardview_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPrefManager.getInstance(TabOnOffline.this).isLoggedIn()) {
                    finish();
                   // startActivity(new Intent(TabOnOffline.this, Primary.class));
                    i = new Intent(TabOnOffline.this, Primary.class);
                    i.putExtra("tab", "offline");
                    startActivity(i);
                    return;
                } else
                    //startActivity(new Intent(TabOnOffline.this, First.class));
                    i = new Intent(TabOnOffline.this, SigninActivity.class);
                i.putExtra("tab", "offline");
                startActivity(i);
                finish();
            }
        });
    }
}
