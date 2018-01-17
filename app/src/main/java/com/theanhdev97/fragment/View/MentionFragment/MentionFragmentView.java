package com.theanhdev97.fragment.View.MentionFragment;

import android.view.View;

/**
 * Created by DELL on 13/01/2018.
 */

public interface MentionFragmentView {
  void setEventListener(View view);

  void repairMentionRecyclerView();

  void getMentions(int page);

  void showViewNoData(boolean show);

  void handleRefreshMentions();
}
