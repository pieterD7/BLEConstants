package eu.mediself.ble;

import android.bluetooth.le.ScanRecord;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;

/*
* A BLE scan record starts with ADStructures and is padded with 0x00
* up to a length of 62 bytes on Android.
* */

public class BLEScanRecord {

    String TAG = BLEScanRecord.class.getSimpleName();

    byte[] bytes;

    String hex = "";

    ArrayList<ADStructure> hdrs;

    public class ADStructure{

        // The type of the ADStructure. This is the first octet;
        String octet = "";

        // Translated type
        String type = "";

        // The data
        String value = "";

        ADStructure(String hex){

            if(hex.length() < 1) return;

            octet = hex.substring(0,2);

            String code = "GAP_" + hex.substring(0,2);

            this.type = BLEGAPConstants.getKey(code);

            this.value = hex.substring(2);

            Log.v(TAG, type + " " + value);
        }

        @Override
        public boolean equals(Object ad){
            if(ad instanceof ADStructure){
                return ((ADStructure) ad).type.equals(this.type);
            }
            return false;
        }
    }

    BLEScanRecord(ScanRecord scanRecord){
        this.bytes = scanRecord.getBytes();
        this.hex = new String(Hex.encodeHex(scanRecord.getBytes()));

        parseADStructures();
    }

    private int getADStructure(int start){

        byte[] octet = {this.bytes[start]};

        int len = Integer.parseInt(new String(Hex.encodeHex(octet)), 16);

        if((start + len) > this.bytes.length - 1) return -1;

        if(len == 0) return -1;


        String data = new String();

        int d = 0;
        for(int c = start + 1; c < start + len + 1; c++){
            byte[] bar = {this.bytes[c]};
            data += new String(Hex.encodeHex(bar));
        }

        ADStructure ad = new ADStructure(data);
        if(!hdrs.contains(ad))
            hdrs.add(ad);

        return start + len + 1;
    }

    public ArrayList<ADStructure> parseADStructures(){

        if(this.hdrs == null){
            hdrs = new ArrayList<>();
            int start = 0;
            while((start < this.bytes.length) && start > -1) {
                start = getADStructure(start);
            }
        }
        return this.hdrs;
    }

    @Override
    public String toString() {

        parseADStructures();

        String ads = "";
        for(int c = 0; c < this.hdrs.size(); c++){
            ads += this.hdrs.get(c).type + " " + this.hdrs.get(c).value + " ";
        }
        return ads;
    }
}
