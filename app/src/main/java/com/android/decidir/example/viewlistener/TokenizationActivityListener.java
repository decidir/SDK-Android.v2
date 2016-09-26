package com.android.decidir.example.viewlistener;

import com.android.decidir.example.domain.ErrorDetail;
import com.decidir.sdk.dto.CardTokens;

/**
 * Created by biandra on 23/09/16.
 */
public interface TokenizationActivityListener {

    void onGetCardTokenStarted();
    void onGetCardTokenSuccess(CardTokens cardTokens);
    void onGetCardTokenError(ErrorDetail error);

}
