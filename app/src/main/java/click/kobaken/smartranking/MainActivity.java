package click.kobaken.smartranking;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import click.kobaken.rxirohaandroid.qr.QRReaderBuilder;
import click.kobaken.rxirohaandroid.qr.ReadQRCallback;
import click.kobaken.smartranking.databinding.ActivityMainBinding;
import click.kobaken.smartranking.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        ElectionsFragment.OnListFragmentInteractionListener,
        MyPageFragment.OnFragmentInteractionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private MyPageFragment myPageFragment;
    private ElectionsFragment electionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_my_page:
                            switchFragment(myPageFragment, MyPageFragment.TAG);
                            binding.toolbar.setTitle("マイページ");
                            return true;
                        case R.id.navigation_podium:
                            switchFragment(electionsFragment, ElectionsFragment.TAG);
                            binding.toolbar.setTitle("選挙");
                            return true;
                        case R.id.navigation_points:
                            Intent intent = new QRReaderBuilder(this).setLayoutId(R.layout.activity_qr_reader)
                                    .setCallback(new ReadQRCallback() {
                                        @Override
                                        public void onSuccessful(String result) {
                                            Toast.makeText(MainActivity.this, "投票権を獲得しました！", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Throwable throwable) {
                                            Log.e(TAG, "onFailure: ", throwable);
                                        }
                                    })
                                    .build();
                            startActivity(intent);
                            return true;
                    }
                    return false;
                }
        );

        initFragments();
    }

    private void initFragments() {
        if (electionsFragment == null) {
            electionsFragment = ElectionsFragment.newInstance(1);
        }
        if (myPageFragment == null) {
            myPageFragment = MyPageFragment.newInstance("kobaken", "100");
        }

        switchFragment(electionsFragment, ElectionsFragment.TAG);
    }

    protected void switchFragment(Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            return;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = manager.beginTransaction();

        final Fragment currentFragment = getCurrentFragment(manager, R.id.container);
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }

        fragmentTransaction.add(R.id.container, fragment, tag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    protected <T extends Fragment> boolean isCurrentFragment(
            Class<T> clazz, @IdRes @LayoutRes int containerLayoutId) {
        return getCurrentFragment(getSupportFragmentManager(), containerLayoutId).getClass() == clazz;
    }

    protected Fragment getCurrentFragment(
            @NonNull FragmentManager manager, @IdRes @LayoutRes int containerLayoutId) {
        return manager.findFragmentById(containerLayoutId);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // nothing
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d(TAG, "onListFragmentInteraction: " + item.id);
        if (item.id.equals("1")) {
            startActivity(ElectionActivity.getCallingIntent(this, "mmrn_election"));
        } else {
            startActivity(ElectionResultActivity.getCallingIntent(this, "mmrn_election"));
        }
    }
}
