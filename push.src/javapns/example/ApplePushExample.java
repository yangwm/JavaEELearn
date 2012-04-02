package javapns.example;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

/**
 * apple push example 
 * 
 * @author yangwm Oct 10, 2010 3:26:25 PM
 */
public class ApplePushExample {
    // APNs Server Host & port
    private static final String HOST = "gateway.sandbox.push.apple.com";
    private static final int PORT = 2195;

    // Badge
    private static final int BADGE = 1;

    private static String iPhoneKey = "yangwm-iPhone";
    // iPhone's UDID (64-char device token)
    private static String iPhoneId = "3285995c14fb53ca1e60a8ad47f180604af56f2058cb08fc17c0224b42f139c5";
    private static String certificate = "D:/study/tempProjects/yangwmProject/JavaEELearn/push.src/javapns/example/certificate2.p12";
    private static String passwd = "123";

    public static void main(String[] args) throws Exception {

        System.out.println(iPhoneId.length());

        try {
            // Setup up a simple message
            PayLoad aPayload = new PayLoad();
            aPayload.addBadge(BADGE);
            aPayload.addAlert("yangwm test apns");
            System.out.println("Payload setup successfull.");
            System.out.println(aPayload);

            // Get PushNotification Instance
            PushNotificationManager pushManager = PushNotificationManager.getInstance();

            // Link iPhone's UDID (64-char device token) to a stringName
            pushManager.addDevice(iPhoneKey, iPhoneId);
            System.out.println("iPhone UDID taken.");

            // Get iPhone client
            Device client = pushManager.getDevice(iPhoneKey);
            System.out.println("Client setup successfull. Token: " + client.getToken());

            // Initialize connection
            pushManager.initializeConnection(HOST, PORT, certificate, passwd, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
            System.out.println("Connection initialized...");

            // Send message
            long begin = 0;
            Thread.sleep(300);
            begin = System.currentTimeMillis();
            pushManager.sendNotification(client, aPayload);
            System.out.println("Message sent! push spent : " + (System.currentTimeMillis() - begin) + " ms");

            System.out.println("# of attempts: " + pushManager.getRetryAttempts());
            pushManager.stopConnection();

            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}