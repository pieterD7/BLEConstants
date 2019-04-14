# BLEConstants
Package with the Bluetooth LE UUIDS and their equivalent names

@author : Pieter Hoekstra, pieter@nr8.nl

All other files in this package are copy pasted from
https://www.bluetooth.com/specifications/gatt

A few adjustments:
\- and SPACE are translated as _ in the names of the variables

To use as a jitpack repository : 

    implementation 'com.github.pieterhoekstra:BLEConstants:0.1.0'
        
Usage :   
   
    UUID uuid = BLEConstants.getUUID(BLEService.Generic_Access));
   
and: 
   
    String name = BLEConstamts.getName((UUID) ....)

    
