package com.android.decidir.example.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    private BtnClickListener btnClickListener;

    public CardTokenAdapter(Context context, List<CardToken> items, BtnClickListener btnClickListener) {
        this.context = context;
        this.items = items;
        this.btnClickListener = btnClickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.card_token, parent, false);
        }

        ImageView ivItemCardBrand = (ImageView) rowView.findViewById(R.id.ivItemCardToken);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvItemCardToken);
        Button buttonDelete = (Button) rowView.findViewById(R.id.buttonDelete);

        final CardToken item = this.items.get(position);
        tvTitle.setText(getInformation(item));
        switch (item.getPayment_method_id()){
            case 15 :
                ivItemCardBrand.setImageResource(R.mipmap.mastercard);
                break;
            case 1:
                ivItemCardBrand.setImageResource(R.mipmap.visa);
                break;
            case 65 :
                ivItemCardBrand.setImageResource(R.mipmap.amex);
                break;
            case 27 :
                ivItemCardBrand.setImageResource(R.mipmap.cabal);
                break;
            default: ivItemCardBrand.setImageResource(R.mipmap.ic_launcher);
        }

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnClickListener.onBtnClick(position);
            }
        });
        if (item.getExpired()){
            rowView.setBackgroundColor(Color.RED);
        } else {
            rowView.setBackgroundColor(Color.WHITE);
        }

        return rowView;
    }

    private String getInformation(CardToken cardToken){
        return  "bin: " + cardToken.getBin() +  System.getProperty("line.separator")
                + "last four digits: " + cardToken.getLast_four_digits() + System.getProperty( "line.separator" )
                + "expiration day: " + cardToken.getExpiration_month() + "/20" + cardToken.getExpiration_year();
    }
}
