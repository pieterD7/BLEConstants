package eu.mediself.ble.BLEConstants;

import java.util.ArrayList;
import eu.mediself.ble.BLEConstants.BLEGAPConstants;

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


        }

        @Override
        public boolean equals(Object ad){
            if(ad instanceof ADStructure){
                return ((ADStructure) ad).type.equals(this.type);
            }
            return false;
        }
    }

    BLEScanRecord(byte[] scanRecord){
        this.bytes = scanRecord;

        this.hex = bytesToHex(this.bytes);

        parseADStructures();
    }

    private String bytesToHex(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for(byte b : bytes){
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private int getADStructure(int start){

        byte[] octet = {this.bytes[start]};

        int len = Integer.parseInt(new String(bytesToHex(octet)), 16);

        if((start + len) > this.bytes.length - 1) return -1;

        if(len == 0) return -1;


        String data = new String();

        int d = 0;
        for(int c = start + 1; c < start + len + 1; c++){
            byte[] bar = {this.bytes[c]};
            data += new String(bytesToHex(bar));
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
