package hr.matvidako.ideamachine.ideatag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import hr.matvidako.ideamachine.R;
import hr.matvidako.ideamachine.base.UpActivity;
import hr.matvidako.ideamachine.tag.Tag;
import hr.matvidako.ideamachine.tag.storage.TagStorage;

public class IdeasByTagListActivity extends UpActivity {

    private static final String EXTRA_TAG_ID = "tagId";
    private Tag tag;
    private TagStorage tagStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagStorage = getApp().getTagStorage();
        loadDataFromIntent(getIntent());
        getSupportActionBar().setTitle(getString(R.string.tag_ideas, tag.getTitle()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_delete) {
            onDeleteTag();
            return true;
        } else if(id == R.id.action_edit) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDeleteTag() {
        tagStorage.delete(tag);
        finish();
        Toast.makeText(this, getString(R.string.tag_deleted), Toast.LENGTH_SHORT).show();
    }

    private void loadDataFromIntent(Intent intent) {
        if(intent == null) {
            return;
        }
        int tagId = intent.getIntExtra(EXTRA_TAG_ID, 0);
        tag = tagStorage.getById(tagId);
    }

    public static Intent buildIntent(Context context, int tagId) {
        Intent i = new Intent(context, IdeasByTagListActivity.class);
        i.putExtra(EXTRA_TAG_ID, tagId);
        return i;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_idea_list;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_tag;
    }
}
