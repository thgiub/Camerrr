package ru.kamaz.itis.timer.gallery;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;




import ru.kamaz.itis.timer.R;
import ru.kamaz.itis.timer.gallery.ui.ui.main.MediaPhotoPagerFragment;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment= new MediaPhotoPagerFragment();
        transaction.replace(R.id.gobedia2, fragment);
    }


}
