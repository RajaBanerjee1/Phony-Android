package io.github.aentfs.phony.phone;

import android.content.Intent;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.telecom.RemoteConference;
import android.telecom.RemoteConnection;
import android.telecom.TelecomManager;
import android.util.Log;

import io.github.aentfs.phony.sip.PhonySipUtil;

public class PhonyConnectionService extends ConnectionService {

    private static final String TAG = "PhonyConnectionService";

    public PhonyConnectionService() {
        super();

        Log.d(TAG, "PhonyConnectionService: called.");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: called.");

        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: called.");

        return super.onUnbind(intent);
    }

    @Override
    public Connection onCreateIncomingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateIncomingConnection: called.");

        return super.onCreateIncomingConnection(connectionManagerPhoneAccount, request);
    }

    @Override
    public Connection onCreateOutgoingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateOutgoingConnection: called.");

        try {
            SipAudioCall audioCall = PhonySipUtil.getSipManager(getApplicationContext()).makeAudioCall(connectionManagerPhoneAccount.getId(), Uri.decode(request.getAddress().toString()), null, PhonySipUtil.EXPIRY_TIME);

            PhonyConnection connection = new PhonyConnection(audioCall);

            connection.setAddress(request.getAddress(), TelecomManager.PRESENTATION_ALLOWED);
            connection.setInitialized();

            return connection;
        } catch (SipException e) {
            e.printStackTrace();
            return Connection.createFailedConnection(new DisconnectCause(DisconnectCause.ERROR, "SipExeption", "Check the stack trace for more information.", e.getLocalizedMessage()));
        }
    }

    @Override
    public void onConference(Connection connection1, Connection connection2) {
        Log.d(TAG, "onConference: called.");

        super.onConference(connection1, connection2);
    }

    @Override
    public void onRemoteConferenceAdded(RemoteConference conference) {
        Log.d(TAG, "onRemoteConferenceAdded: called.");

        super.onRemoteConferenceAdded(conference);
    }

    @Override
    public void onRemoteExistingConnectionAdded(RemoteConnection connection) {
        Log.d(TAG, "onRemoteExistingConnectionAdded: called.");

        super.onRemoteExistingConnectionAdded(connection);
    }
}
