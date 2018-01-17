package com.theanhdev97.fragment.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.theanhdev97.fragment.Model.Entities;
import com.theanhdev97.fragment.Model.MediaUrl;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Utils.CircleTransform;
import com.theanhdev97.fragment.Utils.TextViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 03/01/2018.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<Tweet> mTweets;
  private Context mContext;
  private ItemClickListener mItemClickListener;
  private UserIconClickListener mUserIconClickListener;
  public boolean mFlagUserIconClickListener;

  final int PHOTO = 1;
  final int MP4 = 2;
  final String TYPE = "animated_gif";

  // Pass in the contact array into the constructor
  public TweetAdapter(List<Tweet> tweets, Context context) {
    this.mTweets = tweets;
    this.mContext = context;
    this.mFlagUserIconClickListener = false;
  }


  @Override
  public int getItemViewType(int position) {
//    if (mTweets.get(position).getExtendedEntities().getMedia().get(0)
//        .getVideo_info().getVariants().get(0).getUrl().equalsIgnoreCase(TYPE)) {
//      return MP4;
//    }
    getListVideoPosition();
    if (isVideoItemHolder(position))
      return MP4;
    return PHOTO;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;
    switch (viewType) {
      case PHOTO:
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tweet_photo, parent, false);
        return new TweetPhotoItemHolder(v);
      case MP4:
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tweet_video, parent, false);
        return new TweetVideoItemHolder(v);
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Tweet tweet = mTweets.get(position);
    switch (getItemViewType(position)) {
      case PHOTO:
        TweetPhotoItemHolder holder1 = (TweetPhotoItemHolder) holder;
        holder1.tvText.setText(tweet.getText());
        TextViewHelper.makeUrlHighlightAndClickable(tweet.getText(), holder1.tvText);
        holder1.tvTime.setText(tweet.getCreate_at());
        holder1.tvUserName.setText(tweet.getUser().getName());
        holder1.tvScreenName.setText(tweet.getUser().getScreenName());
        Glide.with(mContext)
            .load(tweet.getUser().getProfileImageUrl())
            .centerCrop()
            .transform(new CircleTransform(mContext))
            .placeholder(android.R.drawable.ic_menu_info_details)
            .into(holder1.imvIcon);

        List<MediaUrl> mediaUrls = tweet.getEntities().getMediaUrls();
        if (mediaUrls != null && mediaUrls.size() > 0) {
          Glide.with(mContext)
              .load(mediaUrls.get(0).getMediaUrl())
              .centerCrop()
              .placeholder(android.R.drawable.ic_menu_info_details)
              .into(holder1.imvPostIcon);
        } else {
          holder1.imvPostIcon.setVisibility(View.GONE);
        }

        if (tweet.getFavoriteCount() > 0) {
          holder1.imvFavourite.setImageResource(R.drawable.likeactionon);
          holder1.tvFavourite.setText("" + tweet.getFavoriteCount());
        } else {
          holder1.imvFavourite.setImageResource(R.drawable.likeaction);
          holder1.tvFavourite.setText("0");
        }

        if (tweet.getRetweetCount() > 0) {
          holder1.imvRetweet.setImageResource(R.drawable.retweetactionon);
          holder1.tvRetweet.setText("" + tweet.getRetweetCount());
        } else {
          holder1.imvRetweet.setImageResource(R.drawable.retweetaction);
          holder1.tvRetweet.setText("0");
        }
        break;
      case MP4:
        TweetVideoItemHolder holder2 = (TweetVideoItemHolder) holder;
        holder2.tvText.setText(tweet.getText());
        TextViewHelper.makeUrlHighlightAndClickable(tweet.getText(), holder2.tvText);
        holder2.tvTime.setText(tweet.getCreate_at());
        holder2.tvUserName.setText(tweet.getUser().getName());
        holder2.tvScreenName.setText(tweet.getUser().getScreenName());
        Glide.with(mContext)
            .load(tweet.getUser().getProfileImageUrl())
            .centerCrop()
            .transform(new CircleTransform(mContext))
            .placeholder(android.R.drawable.ic_menu_info_details)
            .into(holder2.imvIcon);

        List<MediaUrl> mediaUrls2 = tweet.getEntities().getMediaUrls();

        // LOAD VIDEO URL

        VideoView mVideoView = holder2.videoViewPostIcon;
//        mediaController.setAnchorView(mVideoView);
//        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(Uri.parse(tweet.getExtendedEntities().getMedia().get(0)
            .getVideo_info().getVariants().get(0).getUrl()));
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mp) {
            mp.setLooping(true);
          }
        });

        // ------------

        if (tweet.getFavoriteCount() > 0) {
          holder2.imvFavourite.setImageResource(R.drawable.likeactionon);
          holder2.tvFavourite.setText("" + tweet.getFavoriteCount());
        } else {
          holder2.imvFavourite.setImageResource(R.drawable.likeaction);
          holder2.tvFavourite.setText("0");
        }

        if (tweet.getRetweetCount() > 0) {
          holder2.imvRetweet.setImageResource(R.drawable.retweetactionon);
          holder2.tvRetweet.setText("" + tweet.getRetweetCount());
        } else {
          holder2.imvRetweet.setImageResource(R.drawable.retweetaction);
          holder2.tvRetweet.setText("0");
        }
        break;
      default:
        TweetPhotoItemHolder holder3 = (TweetPhotoItemHolder) holder;
        holder3.tvText.setText(tweet.getText());
        TextViewHelper.makeUrlHighlightAndClickable(tweet.getText(), holder3.tvText);
        holder3.tvTime.setText(tweet.getCreate_at());
        holder3.tvUserName.setText(tweet.getUser().getName());
        holder3.tvScreenName.setText(tweet.getUser().getScreenName());
        Glide.with(mContext)
            .load(tweet.getUser().getProfileImageUrl())
            .centerCrop()
            .transform(new CircleTransform(mContext))
            .placeholder(android.R.drawable.ic_menu_info_details)
            .into(holder3.imvIcon);

        List<MediaUrl> mediaUrls3 = tweet.getEntities().getMediaUrls();
        if (mediaUrls3 != null && mediaUrls3.size() > 0) {
          Glide.with(mContext)
              .load(mediaUrls3.get(0).getMediaUrl())
              .centerCrop()
              .placeholder(android.R.drawable.ic_menu_info_details)
              .into(holder3.imvPostIcon);
        } else {
          holder3.imvPostIcon.setVisibility(View.GONE);
        }

        if (tweet.getFavoriteCount() > 0) {
          holder3.imvFavourite.setImageResource(R.drawable.likeactionon);
          holder3.tvFavourite.setText("" + tweet.getFavoriteCount());
        } else {
          holder3.imvFavourite.setImageResource(R.drawable.likeaction);
          holder3.tvFavourite.setText("0");
        }

        if (tweet.getRetweetCount() > 0) {
          holder3.imvRetweet.setImageResource(R.drawable.retweetactionon);
          holder3.tvRetweet.setText("" + tweet.getRetweetCount());
        } else {
          holder3.imvRetweet.setImageResource(R.drawable.retweetaction);
          holder3.tvRetweet.setText("0");
        }
        break;
    }
  }

  @Override
  public int getItemCount() {
    return mTweets.size();
  }

  public class TweetPhotoItemHolder extends RecyclerView.ViewHolder /*implements View
  .OnClickListener*/ {

    ImageView imvIcon;
    ImageView imvPostIcon;
    TextView tvUserName;
    TextView tvScreenName;
    TextView tvTime;
    TextView tvText;
    TextView tvFavourite;
    ImageView imvFavourite;
    ImageView imvRetweet;
    TextView tvRetweet;

    public TweetPhotoItemHolder(final View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the mContext from any ViewHolder instance.
      super(itemView);
      tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
      tvScreenName = (TextView) itemView.findViewById(R.id.tv_screen_name);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvText = (TextView) itemView.findViewById(R.id.tv_text);
      tvFavourite = (TextView) itemView.findViewById(R.id.tv_like);
      tvRetweet = (TextView) itemView.findViewById(R.id.tv_retweet);
      imvIcon = (ImageView) itemView.findViewById(R.id.imv_icon);
      imvPostIcon = (ImageView) itemView.findViewById(R.id.imv_post_icon);
      imvFavourite = (ImageView) itemView.findViewById(R.id.imv_favourite);
      imvRetweet = (ImageView) itemView.findViewById(R.id.imv_retweet);
      if (mFlagUserIconClickListener) {
        imvIcon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mUserIconClickListener.onUserIconClickListener(itemView, getAdapterPosition());
          }
        });
      }
//      itemView.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view) {
//      mItemClickListener.onClick(view, getAdapterPosition());
//    }
  }

  public class TweetVideoItemHolder extends RecyclerView.ViewHolder {
    ImageView imvIcon;
    VideoView videoViewPostIcon;
    TextView tvUserName;
    TextView tvScreenName;
    TextView tvTime;
    TextView tvText;
    TextView tvFavourite;
    ImageView imvFavourite;
    ImageView imvRetweet;
    TextView tvRetweet;

    public TweetVideoItemHolder(final View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the mContext from any ViewHolder instance.
      super(itemView);
      tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
      tvScreenName = (TextView) itemView.findViewById(R.id.tv_screen_name);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvText = (TextView) itemView.findViewById(R.id.tv_text);
      tvFavourite = (TextView) itemView.findViewById(R.id.tv_like);
      tvRetweet = (TextView) itemView.findViewById(R.id.tv_retweet);
      imvIcon = (ImageView) itemView.findViewById(R.id.imv_icon);
      videoViewPostIcon = (VideoView) itemView.findViewById(R.id.video_view_post);
      imvFavourite = (ImageView) itemView.findViewById(R.id.imv_favourite);
      imvRetweet = (ImageView) itemView.findViewById(R.id.imv_retweet);
      if (mFlagUserIconClickListener) {
        imvIcon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mUserIconClickListener.onUserIconClickListener(itemView, getAdapterPosition());
          }
        });
      }
//      itemView.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view) {
//      mItemClickListener.onClick(view, getAdapterPosition());
//    }
  }

  public interface UserIconClickListener {
    void onUserIconClickListener(View view, int position);
  }

  public void setUserIconClickListener(UserIconClickListener userIconClickListener) {
    this.mUserIconClickListener = userIconClickListener;
  }

  public interface ItemClickListener {
    void onClick(View view, int position);
  }

  public void setItemClickListener(ItemClickListener itemClickListener) {
    this.mItemClickListener = itemClickListener;
  }

  private void spannableText(String text, TextView textView, HashMap<Integer, Integer> hashMapTag) {
    Spannable wordtoSpan = new SpannableString(text);

    for (Map.Entry<Integer, Integer> entry : hashMapTag.entrySet()) {
      wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE),
          entry.getKey(),
          entry.getValue() + 1,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    textView.setText(wordtoSpan);
  }

  boolean isVideoItemHolder(int position) {
    for (Integer i : mlistVideoPosition) {
      if (i == position)
        return true;
    }
    return false;
  }

  private ArrayList<Integer> mlistVideoPosition;

  private void getListVideoPosition() {
    mlistVideoPosition = new ArrayList<Integer>();
    mlistVideoPosition.clear();

    for (int i = 0; i < mTweets.size(); i++) {
      if (mTweets.get(i).getExtendedEntities() != null) {
        if (mTweets.get(i).getExtendedEntities().getMedia().size() > 0
            && mTweets.get(i).getExtendedEntities().getMedia().get(0).getVideo_info() != null
            && mTweets.get(i).getExtendedEntities().getMedia().get(0).getVideo_info().getVariants() != null
            && mTweets.get(i).getExtendedEntities().getMedia().get(0).getVideo_info()
            .getVariants().size() > 0) {
          mlistVideoPosition.add(i);
        }
      }
    }
  }
}
