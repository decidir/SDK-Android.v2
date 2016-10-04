package com.android.decidir.sdk.services;

    import android.content.Context;

    import com.android.decidir.sdk.dto.FraudDetectionResponse;
    import com.threatmetrix.TrustDefenderMobile.EndNotifier;
    import com.threatmetrix.TrustDefenderMobile.ProfilingResult;
    import com.threatmetrix.TrustDefenderMobile.THMStatusCode;
    import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
/**
 * Created by biandra on 30/09/16.
 */



public class RiskHelperService {

    /** The Constant PROFILING_TIMEOUT_SECS. */
    final public static int PROFILING_TIMEOUT_SECS = 30;

    /** The session id. */
    private String sessionId;

    private TrustDefenderMobile profile;
    private Context context;
    //private String merchant_id;

    /**
     * The constructor
     *
     */
    public RiskHelperService(FraudDetectionResponse fDResponse, Context context) {
        this.context = context;
        //this.merchant_id = fDResponse.getMerchant_id();
        this.profile = new TrustDefenderMobile(fDResponse.getOrg_id());
    }


    /**
     * Gets the session id.
     *
     * @return the session id - may be null if profiling fails
     */
    public String getSessionId() {
        // if a profiling is not currently in progress
        if (this.sessionId == null) {
            // initialize profiling again
            this.initProfiling();

            // start profiling
            this.sessionId = this.startProfiling();
        }

        return this.sessionId;
    }


    /**
     * Initialize the TrustDefenderMobile profiling with options.
     */
    private void initProfiling() {
        // get a reference to the risk helper for passing inside
        final RiskHelperService riskHelper = this;

        // initialize Threatmetrix with profiling options
        this.profile.init(new com.threatmetrix.TrustDefenderMobile.Config()
                .setDisableOkHttp(true)
                .setTimeout(PROFILING_TIMEOUT_SECS)
                .setRegisterForLocationServices(true)
                .setContext(/*this.config.getContext()*/this.context)
                .setEndNotifier(new EndNotifier() {
                    @Override
                    public void complete(ProfilingResult result) {
                        // Called once profiling is complete
                        if (result.getStatus() == THMStatusCode.THM_OK) {
                            // success!
                            // do nothing special
                        } else {
                            // failed
                            // do nothing special
                        }

                        // stop requesting location
                        riskHelper.stopLocationServices();

                        // delete the sessionId
                        riskHelper.sessionId = null;

                        // cleanup resources
                        profile.tidyUp();
                    }
                }));
    }

    /**
     * Start profiling.
     *
     * @return the session id
     */
    private String startProfiling() {
        THMStatusCode status = this.profile.doProfileRequest();

        if (status == THMStatusCode.THM_OK) {
            // The profiling successfully started, return session id
            return this.profile.getResult().getSessionID();
        } else {
            // profiling failed, return null
            return null;
        }
    }

    /**
     * Stop location services.
     */
    private void stopLocationServices() {
        this.profile.pauseLocationServices(true);
    }

}