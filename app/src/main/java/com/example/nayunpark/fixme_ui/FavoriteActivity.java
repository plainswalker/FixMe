package com.example.nayunpark.fixme_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        TextView favoriteName = (TextView) findViewById(R.id.favoriteName);
        favoriteName.setText("중앙대학교");

        TextView favoriteAddress = (TextView) findViewById(R.id.favoriteAddress);
        favoriteAddress.setText("서울특별시 동작구 흑석로 48");

        final ImageView starImage = (ImageView) findViewById(R.id.starImage);

        final Button favoriteButton = (Button) findViewById(R.id.favoriteButton);
        favoriteButton.setText("즐겨찾기 해제");
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (String.valueOf(favoriteButton.getText())) {
                    case "즐겨찾기 해제":
                        favoriteButton.setText("즐겨찾기 등록");
                        starImage.setImageResource(R.drawable.favorite_star_dark_200);
                        break;
                    case "즐겨찾기 등록":
                        favoriteButton.setText("즐겨찾기 해제");
                        starImage.setImageResource(R.drawable.favorite_star_200);
                        break;
                }
            }
        });

        EditText favoriteNick = (EditText) findViewById(R.id.favoriteNickname);
        nickname = String.valueOf(favoriteNick.getText());
    }
}
