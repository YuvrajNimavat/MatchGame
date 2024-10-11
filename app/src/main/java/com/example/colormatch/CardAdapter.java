package com.example.colormatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class CardAdapter extends BaseAdapter {

    private Context context;
    private Character[] cards;
    private boolean[] flipped;
    private Set<Integer> matchedCards;

    public CardAdapter(Context context, Character[] cards) {
        this.context = context;
        this.cards = cards;
        this.flipped = new boolean[cards.length];
        this.matchedCards = new HashSet<>();
    }

    @Override
    public int getCount() {
        return cards.length;
    }

    @Override
    public Object getItem(int position) {
        return cards[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        }

        TextView cardText = convertView.findViewById(R.id.cardText);
        cardText.setText(flipped[position] ? cards[position].toString() : "");

        return convertView;
    }

    public void flipCard(int position) {
        flipped[position] = !flipped[position];
        notifyDataSetChanged();
    }

    public boolean isCardFlipped(int position) {
        return flipped[position];
    }

    public boolean isAllMatched() {
        // All cards are matched if the size of matched cards is half of the total number of cards
        return matchedCards.size() == cards.length ;
    }

    public void addMatchedCard(int position) {
        matchedCards.add(position);
    }

    public void clearMatchedCards() {
        matchedCards.clear();
    }
}
