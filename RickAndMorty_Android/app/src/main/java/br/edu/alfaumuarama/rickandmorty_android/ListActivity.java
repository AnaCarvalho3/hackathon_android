package br.edu.alfaumuarama.rickandmorty_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    int page = 1;
    AppManager.Type type;
    ScrollView scrollView;
    LinearLayout linearLayout;

    String characterRequest = "https://rickandmortyapi.com/api/character";
    String episodeRequest = "https://rickandmortyapi.com/api/episode";

    HashMap<String, JSONObject> characters_map = new HashMap<>();
    HashMap<String, JSONObject> episodes_map = new HashMap<>();

    AppManager appManager = new AppManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);

        scrollView = findViewById(R.id.list_scroll_id);
        linearLayout = findViewById(R.id.list_linear_layout_id);

        String value = getIntent().getExtras().getString("type");

        switch(value) {
            case "character":
                type = AppManager.Type.CHARACTER;
                if(!characters_map.containsKey(characterRequest)) {
                    MakeRequest(characterRequest);
                } else {
                    try {
                        HandleCharacterResponse(characters_map.get(characterRequest).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "episode":
                type = AppManager.Type.EPISODE;
                if(!episodes_map.containsKey(episodeRequest)) {
                    MakeRequest(episodeRequest);
                } else {
                    try {
                        HandleLocationResponse(episodes_map.get(episodeRequest).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
            HandleScrollChanged(view);
        });
    }

    void LoadInfoForCharacter(JSONObject character) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("type", "character");
        intent.putExtra("character", character.toString());
        startActivity(intent);
    }

    void LoadInfoForEpisode(JSONObject episode) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("type", "episode");
        intent.putExtra("episode", episode.toString());
        startActivity(intent);
    }

    void HandleCharacterResponse(String response) throws JSONException {
        JSONObject reader = new JSONObject(response);
        JSONArray results = reader.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            final JSONObject result = results.getJSONObject(i);
            String image = result.getString("image");
            ImageView imageView = appManager.CreateImageView(1000, this, linearLayout);
            Picasso.get().load(image).into(imageView);
            imageView.setOnClickListener(v -> LoadInfoForCharacter(result));
            TextView text = appManager.CreateTextView(this, linearLayout);
            text.setText(result.getString("name"));
        }
    }


    void HandleEpisodeResponse(String response) throws JSONException {
        JSONObject reader = new JSONObject(response);
        JSONArray results = reader.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            final JSONObject result = results.getJSONObject(i);
            TextView text = appManager.CreateTextView(this, linearLayout);
            String name = result.getString("name");
            String episode = result.getString("episode");
            String episode_text = name + " " + episode;
            text.setText(episode_text);
            text.setTextColor(Color.WHITE);
            text.setOnClickListener(v -> LoadInfoForEpisode(result));
        }
    }

    void HandleScrollChanged(View view)
    {
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        if (diff == 0) {
            page++;
            if(type == AppManager.Type.CHARACTER) {
                characterRequest += "/?page=" + String.valueOf(page);
                if(!characters_map.containsKey(characterRequest)) {
                    MakeRequest(characterRequest);
                } else {
                    try {
                        HandleCharacterResponse(characters_map.get(characterRequest).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if(type == AppManager.Type.EPISODE) {
                episodeRequest += "/?page=" + String.valueOf(page);
                if(!characters_map.containsKey(episodeRequest)) {
                    MakeRequest(episodeRequest);
                }
                }
            }
        }
    }

    void MakeRequest(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        switch(type) {
                            case CHARACTER:
                                HandleCharacterResponse(response);
                                break;
                            case EPISODE:
                                HandleEpisodeResponse(response);
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> { });

        queue.add(stringRequest);
    }
}