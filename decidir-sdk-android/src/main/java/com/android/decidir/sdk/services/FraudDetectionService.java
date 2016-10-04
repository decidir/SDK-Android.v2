package com.android.decidir.sdk.services;

import android.content.Context;

import com.android.decidir.sdk.converters.ErrorConverter;
import com.android.decidir.sdk.dto.DecidirError;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.dto.FraudDetectionData;
import com.android.decidir.sdk.dto.FraudDetectionResponse;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.resources.FraudDetectionApi;
import com.threatmetrix.TrustDefenderMobile.EndNotifier;
import com.threatmetrix.TrustDefenderMobile.ProfilingResult;
import com.threatmetrix.TrustDefenderMobile.THMStatusCode;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by biandra on 05/08/16.
 */
public class FraudDetectionService {

    private FraudDetectionApi fraudDetectionApi;
    private static FraudDetectionService service = null;
    public static final int HTTP_500 = 500;
    public static final int HTTP_401 = 401;
    private ErrorConverter errorConverter;

    public FraudDetectionService(FraudDetectionApi fraudDetectionApi, ErrorConverter errorConverter){
        this.fraudDetectionApi = fraudDetectionApi;
        this.errorConverter = errorConverter;
    }

    public static FraudDetectionService getInstance(FraudDetectionApi fraudDetectionApi) {
        if(service == null) {
            service = new FraudDetectionService(fraudDetectionApi, new ErrorConverter());
        }
        return service;
    }

    public FraudDetectionData getFraudDetection(String sessionID, Context context) throws DecidirException {
        FraudDetectionResponse fDResponse;
        try {
            Response<FraudDetectionResponse> response = this.fraudDetectionApi.getfrauddetectionconf().execute();
            if (response.isSuccessful()) {
                fDResponse = response.body();
            } else {
                DecidirResponse<DecidirError> error = errorConverter.convert(response);
                throw DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
            }
        } catch(IOException ioe) {
            throw new DecidirException(HTTP_500, ioe.getMessage());
        }
        FraudDetectionData fraudDetectionData = new FraudDetectionData();
        fraudDetectionData.setDevice_unique_identifier(getUniqueIdFD(fDResponse, context));
        return fraudDetectionData;
    }

    private String getUniqueIdFD(FraudDetectionResponse fDResponse, Context context){




        /*TrustDefenderMobile profile = new TrustDefenderMobile(fDResponse.getOrg_id());
        THMStatusCode status = profile.doProfileRequest();
        if(status == THMStatusCode.THM_OK) {
            return profile.getResult().getSessionID();
        } else {
            throw new DecidirException(HTTP_401, status.getDesc());
        }*/

        RiskHelperService riskHelper = new RiskHelperService(fDResponse, context);
        return riskHelper.getSessionId();
    }


}
