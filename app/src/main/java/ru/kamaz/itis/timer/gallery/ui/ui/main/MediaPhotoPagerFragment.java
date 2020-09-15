package ru.kamaz.itis.timer.gallery.ui.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;




import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.kamaz.itis.timer.R;
import ru.kamaz.itis.timer.gallery.ViewPagerAdapter;
import ru.kamaz.itis.timer.gallery.constants.ConstantUtils;
import ru.kamaz.itis.timer.gallery.domain.interfaces.main.MediaPhotoPagerInterface;
import ru.kamaz.itis.timer.gallery.presentation.presenters.main.MediaPhotoPagerPresenter;
import ru.kamaz.itis.timer.gallery.ui.ui.photo.MediaPhotoFragment;
import ru.kamaz.itis.timer.gallery.ui.ui.video.VideoListFragment;
import ru.kamaz.itis.timer.test2Fragment;
import ru.kamaz.itis.timer.testFragment;


public class MediaPhotoPagerFragment extends Fragment implements MediaPhotoPagerInterface.View {

  private static MediaPhotoPagerFragment instance;
  private MediaPhotoPagerInterface.Presenter presenter;
  @BindView(R.id.viewpager)
  ViewPager viewpager;
  @BindView(R.id.rbShare)
  RadioButton rbShare;
  @BindView(R.id.rbDelete)
  RadioButton rbDelete;
  @BindView(R.id.radioGroup)
  RadioGroup radioGroup;
  @BindView(R.id.rgEditButtons)
  RadioGroup rgEditButtons;
  @BindView(R.id.clPagerButtons)
  ConstraintLayout clPagerButtons;
  @BindView(R.id.clEditButtons)
  ConstraintLayout clEditButtons;
  Unbinder unbinder;

  @SuppressLint("ValidFragment")


  public static MediaPhotoPagerFragment newInstance() {
    if (instance == null) {
      instance = new MediaPhotoPagerFragment();
    }
    return instance;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_photo_pager, container, false);
    unbinder = ButterKnife.bind(this, v);
    presenter = new MediaPhotoPagerPresenter();
    presenter.setView(this);
    presenter.init();
    setListeners();
    setupViewPager(viewpager);
    return v;
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.onPause();
  }

  public void setListeners() {
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_photo){
          viewpager.setCurrentItem(0);
        } else if (checkedId == R.id.rb_video){
          viewpager.setCurrentItem(1);
        }
      }
    });

    rbShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.onShareButtonClicked();
      }
    });

    rbDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.onDeleteButtonClicked();
      }
    });

    viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        if (position == 0){
          if (radioGroup.getCheckedRadioButtonId() != R.id.rb_photo) {
            radioGroup.check(R.id.rb_photo);
          }
          MediaPhotoFragment.newInstance().addListener();
          VideoListFragment.newInstance().removeListener();
        } else if (position == 1){
          if (radioGroup.getCheckedRadioButtonId() != R.id.rb_video) {
            radioGroup.check(R.id.rb_video);
          }
          MediaPhotoFragment.newInstance().removeListener();
          VideoListFragment.newInstance().addListener();
        }
      }
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });
  }

  @Override
  public void initVars() {

  }

  @Override
  public void showEditButton() {
    clPagerButtons.setVisibility(View.INVISIBLE);
    clEditButtons.setVisibility(View.VISIBLE);
  }

  @Override
  public void showPagerButton() {
    clEditButtons.setVisibility(View.INVISIBLE);
    clPagerButtons.setVisibility(View.VISIBLE);
  }


  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
    adapter.addFragment(MediaPhotoFragment.newInstance(), ConstantUtils.MEDIA_PHOTO_FRAGMENT);
    adapter.addFragment(VideoListFragment.newInstance(), ConstantUtils.VIDEO_LIST_FRAGMENT);
    viewPager.setAdapter(adapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
