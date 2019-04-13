package eu.mediself.ble.BLEConstants;

/*
    @author : Pieter Hoekstra, pieter@nr8.nl

    All other files in this package are copy pasted from
    https://www.bluetooth.com/specifications/gatt

    A few adjustments:
    - and SPACE are translated as _ in the names of the variables
 */

import java.lang.reflect.Field;
import java.util.UUID;

public class BLEConstants {

    public static UUID getUUID(String str){

        return UUID.fromString("0000" + str + "-0000-1000-8000-00805f9b34fb");
    }

    public static String getNames(UUID[] names){
        String strs = "";
        for(UUID name : names){
            strs += getName(name) + " ";
        }
        return strs;
    }

    public static String getName(UUID uuid){
        String str = null;

        try{
            if(uuid != null){

                Object[] serv = {
                        new BLEService(),
                        new BLECharacteristic(),
                        new BLEDeclaration(),
                        new BLEDescriptor()};

                ClassLoader classLoader = BLEConstants.class.getClassLoader();
                for(int m = 0; m < serv.length; m++){
                    Class cls = classLoader.loadClass( serv[m].getClass().getName());
                    Object clsObject = cls.newInstance();

                    Field[] fields = serv[m].getClass().getDeclaredFields();
                    for(int n = 0; n < fields.length; n++){

                        if(fields[n].get(clsObject) != null
                                && getUUID(fields[n].get(clsObject) + "").equals(uuid)) {
                            str =  serv[m].getClass().getSimpleName() + " " + fields[n].getName();
                            break;
                        }
                    }
                    if(str != null) break;
                }
            }
        }
        catch(Exception e){
            
        }

        return str;
    }

    public static String getKey(String name){

        try{
            String str = "";
            Object[] serv = { new BLEGAPConstants()};
            ClassLoader classLoader = BLEConstants.class.getClassLoader();

            for(int c = 0; c < serv.length; c++){
                Class cls = classLoader.loadClass( serv[c].getClass().getName());
                Object clsObject = cls.newInstance();
                Field[] fields = serv[c].getClass().getDeclaredFields();

                for(int d = 0; d < fields.length; d++){

                    if(fields[d].get(clsObject) != null
                    && fields[d].getName().equals(name.toUpperCase())){
                        str = fields[d].get(clsObject) + "";
                        break;
                    }
                }

                return str;
            }
        }
        catch (Exception e){

        }
        return "";
    }
}
