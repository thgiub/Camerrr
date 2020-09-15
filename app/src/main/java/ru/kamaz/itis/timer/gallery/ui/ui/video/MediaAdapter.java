package ru.kamaz.itis.timer.gallery.ui.ui.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.kamaz.itis.timer.R;
import ru.kamaz.itis.timer.gallery.HelperUtils;
import ru.kamaz.itis.timer.gallery.domain.EnumFileType;
import ru.kamaz.itis.timer.gallery.domain.domain.Media;
import ru.kamaz.itis.timer.gallery.domain.interfaces.video.VideoInterface;


class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

  private List<Media> videoList;
  private Context context;
  private Activity mActivity;
  private VideoInterface.Presenter presenter;
  private int lastLongPressedItemID = -1;

  public MediaAdapter(Context context, List<Media> videoList, Activity activity,VideoInterface.Presenter presenter) {
    this.videoList = videoList;
    this.context = context;
    mActivity = activity;
    this.presenter= presenter;
  }

  @NonNull
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.media_item_tile, viewGroup, false);
    return new ViewHolder(view);
  }

  public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
    final Media media = videoList.get(i);
    viewHolder.title.setText(media.title);
    viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

    if (media.type == EnumFileType.IMAGE) {
      Glide.with(context).load(HelperUtils.getUri(context, media.path)).into(viewHolder.img);
    } else if (media.type == EnumFileType.VIDEO) {
      Glide.with(context)
          .load(HelperUtils.getUri(context, media.path))
          .into(viewHolder.img);

    }
    if(presenter.isEditMode()){
      viewHolder.checkBox.setVisibility(View.VISIBLE);
    } else {
      viewHolder.checkBox.setVisibility(View.GONE);
    }
    viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.onVideoCheckedChanged(i, isChecked);
      }
    });
    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        presenter.onVideoLongClicked(i);
        lastLongPressedItemID=i;
        return true;
      }
    });
    if (lastLongPressedItemID == i) {
      viewHolder.checkBox.setChecked(true);
      lastLongPressedItemID = -1;
    } else {
      viewHolder.checkBox.setChecked(false);
    }

    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (media.type == EnumFileType.VIDEO) {

          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setDataAndType(HelperUtils.getUri(context, media.path), "video/*");
          context.startActivity(intent);
        }
      }
    });
    if (presenter.isEditMode()) {
      viewHolder.checkBox.setVisibility(View.VISIBLE);
    } else {
      viewHolder.checkBox.setVisibility(View.GONE);
    }
    viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.onVideoCheckedChanged(i, isChecked);
      }
    });
  }
    public void updateData(List<Media> data) {
        videoList.clear();

        if (data != null)
          videoList.addAll(data);

        notifyDataSetChanged();
    }


  public int getItemCount() {
    return videoList != null ? videoList.size() : 0;
  }



  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.checkBoxVideo)
    CheckBox checkBox;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
