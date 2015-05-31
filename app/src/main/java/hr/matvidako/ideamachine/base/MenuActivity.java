package hr.matvidako.ideamachine.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hr.matvidako.ideamachine.IdeaApplication;
import hr.matvidako.ideamachine.R;
import hr.matvidako.ideamachine.drawer.DrawerItemAdapter;
import hr.matvidako.ideamachine.idea.storage.IdeaStorage;

public abstract class MenuActivity extends BaseActivity {

    @InjectView(R.id.menu_list)
    ListView menuList;
    DrawerItemAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        setupMenuDrawer();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.menu);
    }

    protected void setupMenuDrawer() {
        menuAdapter = new DrawerItemAdapter(this);
        menuList.setOnItemClickListener(new DrawerItemClickListener());

        View header = getLayoutInflater().inflate(R.layout.header_menu, null, false);
        menuList.addHeaderView(header, null, false);
        final TextView tvIdeaCount = ButterKnife.findById(header, R.id.idea_count);
        final TextView tvIdeaStreak = ButterKnife.findById(header, R.id.idea_streak);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                IdeaStorage ideaStorage = getApp().getIdeaStorage();
                int ideasToday = ideaStorage.getIdeaCountForToday();
                tvIdeaCount.setText(resources.getQuantityString(R.plurals.ideas_today, ideasToday, ideasToday));
                int streak = ideaStorage.getCurrentIdeaStreak();
                tvIdeaStreak.setText(resources.getQuantityString(R.plurals.streak, streak, streak));
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        menuList.setAdapter(menuAdapter);
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0) return;
            position--;
            drawerLayout.closeDrawer(menuList);
            Class activityClass = menuAdapter.getItem(position).activityClass;
            Intent i = new Intent(MenuActivity.this, activityClass);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

}