package com.vpaveldm.wordgame.presentationLayer.view.fragments.choose_deck;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class DeckRecyclerAdapter extends RecyclerView.Adapter<DeckRecyclerAdapter.ViewHolder> {

    private List<Deck> mDecks;

    DeckRecyclerAdapter() {
        mDecks = new ArrayList<>();
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
        holder.bind(mDecks.get(position));
    }

    @Override
    public int getItemCount() {
        return mDecks.size();
    }

    public void swapList(List<Deck> decks) {
        if (decks == null) {
            mDecks = new ArrayList<>();
        } else {
            mDecks = decks;
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Inject
        Router mRouter;
        private final TextView deckNameTV;
        private final TextView wordCountTV;
        private final Context mContext;

        ViewHolder(View itemView) {
            super(itemView);
            ActivityComponentManager.getActivityComponent().inject(this);
            itemView.setOnClickListener(v -> {
                mRouter.navigateTo(itemView.getContext().getString(R.string.fragment_choose_deck));
            });
            mContext = itemView.getContext().getApplicationContext();
            deckNameTV = itemView.findViewById(R.id.deckNameTV);
            wordCountTV = itemView.findViewById(R.id.deckWordCountTV);
        }

        void bind(Deck model) {
            deckNameTV.setText(model.getDeckName());
            wordCountTV.setText(mContext.getString(R.string.label_word_count, model.getCards().size()));
        }
    }
}
