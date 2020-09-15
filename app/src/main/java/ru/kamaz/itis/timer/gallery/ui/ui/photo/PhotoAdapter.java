package ru.kamaz.itis.timer.gallery.ui.ui.photo;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;



import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.kamaz.itis.timer.R;
import ru.kamaz.itis.timer.gallery.HelperUtils;
import ru.kamaz.itis.timer.gallery.constants.ConstantUtils;
import ru.kamaz.itis.timer.gallery.domain.Photo;
import ru.kamaz.itis.timer.gallery.domain.interfaces.photo.MediaPhotoInterface;
import ru.kamaz.itis.timer.gallery.presentation.PhotoViewActivity;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> galleryList;
    private Context context;
    private MediaPhotoInterface.Presenter presenter;
    private int lastLongPressedItemID = -1;

    public PhotoAdapter(Context context, List<Photo> galleryList, MediaPhotoInterface.Presenter presenter) {
        this.galleryList = galleryList;
        this.context = context;
        this.presenter = presenter;
        //Collections.reverse(galleryList);
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.photo_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        Picasso.get().load(HelperUtils.getUri(context, galleryList.get(i).getImagePath()))
                .resize(240, 240).centerCrop().into(viewHolder.img);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewActivity.class);
                intent.putExtra(ConstantUtils.BUNDLE_PHOTO_ID, galleryList.get(i).getPhotoId());
                intent.putExtra(ConstantUtils.BUNDLE_PHOTOS_LIST, (Serializable) galleryList);
                intent.putExtra(ConstantUtils.BUNDLE_LIST_SELECT_POSITION, i);
                context.startActivity(intent);
             // presenter.onPhotoClicked(i);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              presenter.onPhotoLongClicked(i);
              lastLongPressedItemID = i;
              return true;
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
                presenter.onPhotoCheckedChanged(i, isChecked);
            }
        });

        if (lastLongPressedItemID == i) {
            viewHolder.checkBox.setChecked(true);
            lastLongPressedItemID = -1;
        } else {
            viewHolder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return galleryList != null ? galleryList.size() : 0;
    }

    public void updateData(List<Photo> data) {
        galleryList.clear();

        if (data != null)
            galleryList.addAll(data);

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
