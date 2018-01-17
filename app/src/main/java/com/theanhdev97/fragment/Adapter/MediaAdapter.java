package com.theanhdev97.fragment.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Utils.Log;

import java.util.List;

/**
 * Created by DELL on 05/01/2018.
 */

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<Media> mMedias;
  private Context mContext;
  private TweetAdapter.ItemClickListener mItemClickListener;

  final int PHOTO = 1;
  final int MP4 = 2;
  final String TYPE = "animated_gif";

  // Pass in the contact array into the constructor
  public MediaAdapter(List<Media> medias, Context context) {
    this.mMedias = medias;
    this.mContext = context;
  }


  @Override
  public int getItemViewType(int position) {
    if (mMedias.get(position).getType().equalsIgnoreCase(TYPE))
      return MP4;
    return PHOTO;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = null;
    switch (viewType) {
      case MP4:
        v = LayoutInflater.from(mContext).inflate(R.layout.item_media_video, parent, false);
        return new MediaVideoItemHolder(v);
      case PHOTO:
        v = LayoutInflater.from(mContext).inflate(R.layout.item_media_photo, parent, false);
        return new MediaPhotoItemHolder(v);
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Media media = mMedias.get(position);
    switch (getItemViewType(position)) {
      case MP4:
        MediaVideoItemHolder itemHolder = (MediaVideoItemHolder) holder;
        VideoView mVideoView = itemHolder.videoView;

        MediaController mediaController = new MediaController(mContext);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        if (media.getVideo_info() != null) {
          mVideoView.setVideoURI(Uri.parse(media.getVideo_info().getVariants().get(0).getUrl()));
          mVideoView.start();
          mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
              mp.setLooping(true);
            }
          });
          Log.d("debug123","Video url : " + media.getVideo_info().getVariants().get(0).getUrl());
        }
        break;
      case PHOTO:
        MediaPhotoItemHolder itemHolder1 = (MediaPhotoItemHolder) holder;
        Glide.with(mContext)
            .load(media.getMedia_url())
            .placeholder(android.R.drawable.ic_menu_info_details)
            .centerCrop()
            .into(itemHolder1.imvMedia);
        break;
      default:
        MediaPhotoItemHolder itemHolder2 = (MediaPhotoItemHolder) holder;
        Glide.with(mContext)
            .load(media.getMedia_url())
            .placeholder(android.R.drawable.ic_menu_info_details)
            .centerCrop()
            .into(itemHolder2.imvMedia);
        break;
    }
  }

  @Override
  public int getItemCount() {
    return mMedias.size();
  }

  public class MediaPhotoItemHolder extends RecyclerView.ViewHolder {
    ImageView imvMedia;

    public MediaPhotoItemHolder(View itemView) {
      super(itemView);
      this.imvMedia = itemView.findViewById(R.id.imv_media);
    }
  }

  public class MediaVideoItemHolder extends RecyclerView.ViewHolder {
    VideoView videoView;

    public MediaVideoItemHolder(View itemView) {
      super(itemView);
      this.videoView = itemView.findViewById(R.id.video_view);
    }
  }

  public interface ItemClickListener {
    void onClick(View view, int position);
  }

  public void setItemClickListener(TweetAdapter.ItemClickListener itemClickListener) {
    this.mItemClickListener = itemClickListener;
  }

}
