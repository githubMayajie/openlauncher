package com.benny.openlauncher.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.bennyv5.materialpreffragment.MaterialPrefFragment;
import com.benny.openlauncher.R;
import com.benny.openlauncher.util.LauncherSettings;

public class SettingsActivity extends AppCompatActivity implements MaterialPrefFragment.OnPrefChangedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        switch (LauncherSettings.getInstance(this).generalSettings.theme) {
            case Light:
                setTheme(R.style.NormalActivity_Light);
                break;
            case Dark:
                setTheme(R.style.NormalActivity_Dark);
                break;
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.tb));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (savedInstanceState == null){
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
            int color = typedValue.data;

            TypedValue typedValue2 = new TypedValue();
            theme.resolveAttribute(R.attr.colorViewBackground, typedValue2, true);
            int color2 = typedValue2.data;

            TypedValue typedValue3 = new TypedValue();
            theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue3, true);
            int color3 = typedValue3.data;

            TypedValue typedValue4 = new TypedValue();
            theme.resolveAttribute(android.R.attr.textColorSecondary, typedValue4, true);
            int color4 = typedValue4.data;


            Fragment fragment = MaterialPrefFragment.newInstance(new MaterialPrefFragment.Builder(color3,color4,color2,color,false)
                    .add(new MaterialPrefFragment.GroupTitle("Desktop"))
                    .add(new MaterialPrefFragment.TBPref("showsearchbar","Show search bar","Display a search bar always on top of the desktop",LauncherSettings.getInstance(this).generalSettings.showsearchbar))
                    .add(new MaterialPrefFragment.GroupTitle("AppDrawer"))
                    .add(new MaterialPrefFragment.TBPref("rememberappdrawerpage","Remember last page","The page will not reset to the first page when reopen app drawer",!LauncherSettings.getInstance(this).generalSettings.rememberappdrawerpage))
                    .add(new MaterialPrefFragment.GroupTitle("Others"))
                    .setOnPrefChangedListener(this));
            getSupportFragmentManager().beginTransaction().add(R.id.ll, fragment).commit();
        }

    }

    @Override
    public void onPrefChanged(String id, Object p2) {
        switch (id){
            case "rememberappdrawerpage":
                LauncherSettings.getInstance(this).generalSettings.rememberappdrawerpage = !(boolean) p2;
                break;
            case "showsearchbar":
                LauncherSettings.getInstance(this).generalSettings.showsearchbar = (boolean) p2;
                if (!(boolean)p2)
                    Home.searchBar.setVisibility(View.GONE);
                else
                    Home.searchBar.setVisibility(View.VISIBLE);
                break;
        }
    }
}