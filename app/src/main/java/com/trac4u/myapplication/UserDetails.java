package com.trac4u.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserDetails extends AppCompatActivity {
    TextView idtvUserName, idtvEmail;
    ImageView idivAvatar;
    Toolbar toolbar;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        idtvUserName = findViewById(R.id.idtvUserName);
        idtvEmail = findViewById(R.id.idtvEmail);
        toolbar = findViewById(R.id.toolbar);
        idivAvatar = findViewById(R.id.idivAvatar);
        toolbar.setTitle(getString(R.string.userDetails));
        context = this;
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String email = intent.getStringExtra("email");
        String avatarUrl = intent.getStringExtra("avatarUrl");

        idtvEmail.setText(email);
        idtvUserName.setText(userName);

        Picasso.with(context).load(avatarUrl).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(idivAvatar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
