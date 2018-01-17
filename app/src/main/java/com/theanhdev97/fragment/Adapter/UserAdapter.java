package com.theanhdev97.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Utils.CircleTransform;

import java.util.List;

/**
 * Created by DELL on 05/01/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MediaItemHolder> {
  private List<User> mUsers;
  private Context mContext;
  private TweetAdapter.ItemClickListener mItemClickListener;

  // Pass in the contact array into the constructor
  public UserAdapter(List<User> medias, Context context) {
    this.mUsers = medias;
    this.mContext = context;
  }


  @Override
  public int getItemViewType(int position) {
    return 0;
  }

  @Override
  public MediaItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
    return new MediaItemHolder(v);
  }


  @Override
  public void onBindViewHolder(MediaItemHolder holder, int position) {
    User user = mUsers.get(position);
    holder.tvUserName.setText(user.getName());
    holder.tvScreenName.setText(user.getScreenName());
    Glide.with(mContext)
        .load(user.getProfileImageUrl())
        .centerCrop()
        .transform(new CircleTransform(mContext))
        .placeholder(android.R.drawable.ic_menu_info_details)
        .into(holder.imvIcon);
  }

  @Override
  public int getItemCount() {
    return mUsers.size();
  }

  public interface ItemClickListener {
    void onClick(View view, int position);
  }

  public class MediaItemHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
    ImageView imvIcon ;
    TextView tvUserName;
    TextView tvScreenName;

    public MediaItemHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the mContext from any ViewHolder instance.
      super(itemView);
      this.imvIcon = itemView.findViewById(R.id.imv_icon);
      this.tvScreenName = itemView.findViewById(R.id.tv_screen_name);
      this.tvUserName = itemView.findViewById(R.id.tv_user_name);
    }
  }


  public void setItemClickListener(TweetAdapter.ItemClickListener itemClickListener) {
    this.mItemClickListener = itemClickListener;
  }

}
