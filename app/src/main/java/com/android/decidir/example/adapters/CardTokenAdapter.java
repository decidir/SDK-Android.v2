package com.android.decidir.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decidir.example.R;
import com.decidir.sdk.dto.CardToken;

import java.util.List;

/**
 * Created by biandra on 23/09/16.
 */
public class CardTokenAdapter extends BaseAdapter {

    private Context context;
    private List<CardToken> items;

    public CardTokenAdapter(Context context, List<CardToken> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.card_token, parent, false);
        }

        ImageView ivItemCardBrand = (ImageView) rowView.findViewById(R.id.ivItemCardToken);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvItemCardToken);
        ImageView ivItemStatus = (ImageView) rowView.findViewById(R.id.ivItemStatus);

        CardToken item = this.items.get(position);
        tvTitle.setText(getInformation(item));
        switch (item.getCard_brand()){
            case MASTERCARD :
                ivItemCardBrand.setImageResource(R.mipmap.mastercard);
                break;
            case VISA:
                ivItemCardBrand.setImageResource(R.mipmap.visa);
                break;
            case AMEX :
                ivItemCardBrand.setImageResource(R.mipmap.amex);
                break;
            case CABAL :
                ivItemCardBrand.setImageResource(R.mipmap.cabal);
                break;
            default: ivItemCardBrand.setImageResource(R.mipmap.ic_launcher);
        }
        if (item.getExpired()){
            ivItemStatus.setImageResource(R.mipmap.delete);
        } else {
            ivItemStatus.setImageResource(R.mipmap.accept);
        }

        return rowView;
    }

    private String getInformation(CardToken cardToken){
        return  "bin: " + cardToken.getBin() +  System.getProperty("line.separator")
                + "last four digits: " + cardToken.getLast_four_digits() + System.getProperty( "line.separator" )
                + "expiration day: " + cardToken.getExpiration_month() + "/20" + cardToken.getExpiration_year();
    }
}
