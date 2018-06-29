package com.vpaveldm.wordgame.uiLayer.view.fragments.choose_deck;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class DeckAdapter extends PagedListAdapter<Deck, DeckAdapter.ViewHolder> {

    DeckAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View deck = inflater.inflate(R.layout.deck_view, parent, false);
        return new ViewHolder(deck);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Deck deck = getItem(position);
        if (deck != null) {
            holder.bind(getItem(position));
        } else {
            holder.clear();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Inject
        Router mRouter;
        private String id;
        private final TextView deckNameTV;
        private final TextView wordCountTV;
        private final Context mContext;

        ViewHolder(View itemView) {
            super(itemView);
            ActivityComponentManager.getActivityComponent().inject(this);
            itemView.setOnClickListener(v -> {
                Object data = id;
                mRouter.navigateTo(itemView.getContext().getString(R.string.fragment_play), data);
            });
            mContext = itemView.getContext().getApplicationContext();
            deckNameTV = itemView.findViewById(R.id.deckNameTV);
            wordCountTV = itemView.findViewById(R.id.deckWordCountTV);
        }

        void bind(Deck model) {
            id = model.id;
            deckNameTV.setText(model.deckName);
            wordCountTV.setText(mContext.getString(R.string.label_word_count, model.cards.size()));
        }

        void clear() {
            id = "";
            deckNameTV.setText("");
            wordCountTV.setText("");
        }
    }

    private static final DiffUtil.ItemCallback<Deck> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Deck>() {
                @Override
                public boolean areItemsTheSame(Deck oldItem, Deck newItem) {
                    return oldItem.id.equals(newItem.id);
                }

                @Override
                public boolean areContentsTheSame(Deck oldItem, Deck newItem) {
                    return oldItem.deckName.equals(newItem.deckName);
                }
            };
}
