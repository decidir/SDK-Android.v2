package com.android.decidir.sdk.services;

    import android.content.Context;

    import com.android.decidir.sdk.exceptions.DecidirException;
    import com.threatmetrix.TrustDefenderMobile.EndNotifier;
    import com.threatmetrix.TrustDefenderMobile.ProfilingResult;
    import com.threatmetrix.TrustDefenderMobile.THMStatusCode;
    import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
/**
 * Created by biandra on 30/09/16.
 */

public class RiskHelperService {

    public static final int HTTP_401 = 401;
    final public static int PROFILING_TIMEOUT_SECS = 30;
    private int timeout;
    private String sessionId;
    private TrustDefenderMobile profile;
    private Context context;

    public RiskHelperService(String orgId, Context context, Integer profilingTimeoutSecs) {
        this.context = context;
        this.profile = new TrustDefenderMobile(orgId);
        this.timeout = (profilingTimeoutSecs == null) ? PROFILING_TIMEOUT_SECS : profilingTimeoutSecs.intValue();
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
                .setTimeout(timeout)
                .setRegisterForLocationServices(true)
                .setContext(this.context)
                .setEndNotifier(new EndNotifier() {
                    @Override
                    public void complete(ProfilingResult result) {
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
            return this.profile.getResult().getSessionID();
        } else {
            throw new DecidirException(HTTP_401, status.getDesc());
        }
    }

    /**
     * Stop location services.
     */
    private void stopLocationServices() {
        this.profile.pauseLocationServices(true);
    }

}