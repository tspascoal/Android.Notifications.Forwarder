package tspascoal.android.notificationsforwarder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RecyclerView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppListAdapter appListAdapter;
    private List<AppInfo> appList;
    private EditText webEndpointEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = findViewById(R.id.recyclerView);
        webEndpointEditText = findViewById(R.id.webEndpointEditText);
        saveButton = findViewById(R.id.saveButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appList = getInstalledApps();
        appListAdapter = new AppListAdapter(appList);
        recyclerView.setAdapter(appListAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appListAdapter.filter(newText);
                return false;
            }
        });

        saveButton.setOnClickListener(v -> {
            saveSelectedApps();
            saveWebEndpointUrl();
            finish();
        });

        loadSavedSettings();
    }

    private List<AppInfo> getInstalledApps() {
        // Implement method to get the list of installed apps
        return new ArrayList<>();
    }

    private void saveSelectedApps() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("selectedApps", appListAdapter.getSelectedApps());
        editor.apply();
    }

    private void saveWebEndpointUrl() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("webEndpointUrl", webEndpointEditText.getText().toString());
        editor.apply();
    }

    private void loadSavedSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        webEndpointEditText.setText(sharedPreferences.getString("webEndpointUrl", ""));
        appListAdapter.setSelectedApps(sharedPreferences.getStringSet("selectedApps", new HashSet<>()));
    }
}
