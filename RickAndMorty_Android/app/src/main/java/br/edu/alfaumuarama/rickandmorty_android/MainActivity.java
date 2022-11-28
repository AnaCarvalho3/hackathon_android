package br.edu.alfaumuarama.rickandmorty_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button characterButton = findViewById(R.id.characters_button);
        Button episodesButton = findViewById(R.id.episodes_button);
        characterButton.setOnClickListener(v -> OpenActivity("character"));
        episodesButton.setOnClickListener(v -> OpenActivity("episode"));


    }
    public void OpenActivity(String value) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("type", value);
        startActivity(intent);
    }
}