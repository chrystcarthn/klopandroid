package appdeveloper.klop.klop.Fragment;

import android.app.ListFragment;
import android.view.MenuItem;
import android.widget.SearchView;

/**
 * Created by CMDDJ on 4/30/2018.
 */

public class SearchViewFragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
