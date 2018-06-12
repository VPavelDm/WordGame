package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {

    private List<Deck> mDecks;

    DeckAdapter() {
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView deckNameTV;
        private TextView wordCountTV;
        private Context mContext;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {

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
