/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Gussoh
 */
public class RecordManager {

    private final static String SETTINGS = "settings";
    private final static int SERVER_ADDRESS = 1,  USER_ID = 2;

    public static String getServerAddress() throws RecordStoreException {
        RecordStore settings = RecordStore.openRecordStore(SETTINGS, true);
        ensureNumberOfRecords(settings, SERVER_ADDRESS);
        byte[] data = settings.getRecord(SERVER_ADDRESS);
        settings.closeRecordStore();

        if (data != null && data.length > 0) {
            return new String(data);
        }

        return null;
    }

    public static String getUserId() throws RecordStoreException {
        RecordStore settings = RecordStore.openRecordStore(SETTINGS, true);
        ensureNumberOfRecords(settings, USER_ID);
        byte[] data = settings.getRecord(USER_ID);
        settings.closeRecordStore();
        if (data != null && data.length > 0) {
            return new String(data);
        }

        return null;
    }

    public static void setServerAddressAndUserId(String address, String userId) throws RecordStoreException {
        RecordStore settings = null;
        settings = RecordStore.openRecordStore(SETTINGS, true);
        ensureNumberOfRecords(settings, USER_ID);
        settings.setRecord(SERVER_ADDRESS, address.getBytes(), 0, address.getBytes().length);
        settings.setRecord(USER_ID, userId.getBytes(), 0, userId.getBytes().length);
        settings.closeRecordStore();
        System.out.println("SAVING ADDRESS AND USERID: " + address + ", " + userId);
    }

    public static void clearData() throws RecordStoreException {

        String[] stores = RecordStore.listRecordStores();
        if (stores == null) {
            return;
        }
        for (int i = 0; i < stores.length; i++) {
            String store = stores[i];
            RecordStore recordStore = RecordStore.openRecordStore(store, false);

            if (recordStore != null) {
                for (int j = recordStore.getNumRecords(); j >= 1; j--) {
                    recordStore.deleteRecord(j);
                }
                recordStore.closeRecordStore();
                RecordStore.deleteRecordStore(store);
            }
        }
    }

    private static void ensureNumberOfRecords(RecordStore recordStore, int numberOfRecords) throws RecordStoreException {
        for (int i = recordStore.getNumRecords(); i < numberOfRecords; i++) {
            recordStore.addRecord(null, 0, 0);
        }
    }
}
