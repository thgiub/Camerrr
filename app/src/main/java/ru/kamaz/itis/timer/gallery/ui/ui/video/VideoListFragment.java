package ru.kamaz.itis.timer.gallery.ui.ui.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.kamaz.itis.timer.R;
import ru.kamaz.itis.timer.gallery.HelperUtils;

import ru.kamaz.itis.timer.gallery.domain.domain.Media;
import ru.kamaz.itis.timer.gallery.domain.interfaces.video.VideoInterface;
import ru.kamaz.itis.timer.gallery.presentation.MediaScannerBroadcast;
import ru.kamaz.itis.timer.gallery.presentation.presenters.video.VideoListPresenter;
import ru.kamaz.itis.timer.gallery.ui.ui.utils.VideoDeleteUtils;


public class VideoListFragment extends Fragment implements MediaScannerBroadcast.MediaScannerBroadcastListener, VideoInterface.View {

  private static VideoListFragment instance;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  Unbinder unbinder;
  private Context context;
  private MediaScannerBroadcast broadcast;
  VideoInterface.Presenter presenter;
  MediaAdapter adapter;
  List<Media> videoList;
  SparseBooleanArray sparseBooleanArray;

  @SuppressLint("ValidFragment")
  public VideoListFragment() {}

  public static VideoListFragment newInstance() {
    if (instance == null) {
      instance = new VideoListFragment();
    }
    return instance;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_video_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter = new VideoListPresenter();
    presenter.setView(this);
    presenter.init();
  }

  public void initVars() {
    broadcast = new MediaScannerBroadcast(this::mediaScannerBroadcastCallback);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),  4));
    sparseBooleanArray= new SparseBooleanArray();
    sparseBooleanArray.delete(0);
  }

  @Override
  public void onResume() {
    super.onResume();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
    intentFilter.addDataScheme("file");
    intentFilter.addDataScheme("content");
    context.registerReceiver(broadcast, intentFilter);
  }

  @Override
  public void onPause(){
    super.onPause();
    context.unregisterReceiver(broadcast);
  }

  public void removeListener() {
    presenter.removeListener();
  }

  public void addListener() {
    presenter.addListener();
  }

  public void initContent() {
    videoList = HelperUtils.getVideoList(context, null);
    adapter = new MediaAdapter(getActivity(), videoList, getActivity(), presenter);
    recyclerView.setAdapter(adapter);
  }
  @Override
  public void updateVideoList() {
    sparseBooleanArray.clear();
    sparseBooleanArray.delete(0);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateGalleryList() {
    videoList.clear();
    videoList = HelperUtils.getVideoList(context,null);
    adapter.updateData(videoList);
  }

  @Override
  public void deleteSelectedItem() throws IOException {
    List<String> selectedItems = getSelectedItems();

    Thread thread = new Thread(new VideoDeleteUtils(selectedItems, presenter, context));
    thread.start();

    presenter.enableEditMode(false);

  }
  private List<String> getSelectedItems() {
    List<String> selectedItems = new ArrayList<>();

    for (int i = 0; i < videoList.size(); i++) {
      if (sparseBooleanArray.get(i, false))
        selectedItems.add(videoList.get(i).path);
    }
    return selectedItems;
  }

  @Override
  public void onVideoCheckedChanged(int itemID, boolean isChecked) {
    if(sparseBooleanArray.get(itemID,false))
      sparseBooleanArray.delete(itemID);
    else
      sparseBooleanArray.put(itemID,true);
    if(sparseBooleanArray.size()==0)
      presenter.enableEditMode(false);
  }

  @Override
  public void showToast(String message) {

  }

  @Override
  public void onFileDeleted(String filePath) {

  }

  @Override
  public void shareSelectedItems() {
    List<String> selectedItems = getSelectedItems();

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.setType("video/*");

    for (String item : selectedItems) {
      shareIntent.putExtra(Intent.EXTRA_STREAM, HelperUtils.getUri(context, item));
    }

    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

    presenter.enableEditMode(false);
  }

  @Override
  public void mediaScannerBroadcastCallback() {
    initContent();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void setListeners() {

  }


}
