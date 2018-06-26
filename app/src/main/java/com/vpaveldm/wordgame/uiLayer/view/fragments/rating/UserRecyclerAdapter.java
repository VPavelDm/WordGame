package com.vpaveldm.wordgame.uiLayer.view.fragments.rating;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.store.model.User;
import com.vpaveldm.wordgame.databinding.UserRatingViewBinding;
import com.vpaveldm.wordgame.uiLayer.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    private List<User> mUsers;

    UserRecyclerAdapter() {
        mUsers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_rating_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mUsers.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void swapUsers(List<User> users) {
        if (users == null) {
            mUsers = new ArrayList<>();
        } else {
            mUsers = users;
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Inject
        @Named("Application")
        Context mContext;
        private final UserRatingViewBinding mBinding;

        ViewHolder(View itemView) {
            super(itemView);
            Application.getAppComponent().inject(this);
            mBinding = UserRatingViewBinding.bind(itemView);
        }

        private void bind(User user, int position) {
            mBinding.userPositionTV.setText(mContext.getString(R.string.label_user_position, position + 1));
            mBinding.userNameTV.setText(user.name);
            mBinding.timeTV.setText(mContext.getString(R.string.label_time, user.time));
        }
    }

}
