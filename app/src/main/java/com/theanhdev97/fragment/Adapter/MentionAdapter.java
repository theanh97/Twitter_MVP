package com.theanhdev97.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theanhdev97.fragment.Model.Mention;
import com.theanhdev97.fragment.R;

import java.util.List;

/**
 * Created by DELL on 05/01/2018.
 */

public class MentionAdapter extends RecyclerView.Adapter<MentionAdapter.MentionItemHolder> {
  private List<Mention> mMentions;
  private Context mContext;
  private TweetAdapter.ItemClickListener mItemClickListener;

  // Pass in the contact array into the constructor
  public MentionAdapter(List<Mention> mentions, Context context) {
    this.mMentions = mentions;
    this.mContext = context;
  }


  @Override
  public int getItemViewType(int position) {
    return 0;
  }

  @Override
  public MentionItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.item_tweet_photo, parent, false);
    return new MentionItemHolder(v);
  }


  @Override
  public void onBindViewHolder(MentionItemHolder holder, int position) {
    Mention mention = mMentions.get(position);
  }

  @Override
  public int getItemCount() {
    return mMentions.size();
  }

  public interface ItemClickListener {
    void onClick(View view, int position);
  }

  public class MentionItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public MentionItemHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the mContext from any ViewHolder instance.
      super(itemView);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      mItemClickListener.onClick(view, getAdapterPosition());
    }
  }

  public void setItemClickListener(TweetAdapter.ItemClickListener itemClickListener) {
    this.mItemClickListener = itemClickListener;
  }

}
