# BLEConstants
Package with the Bluetooth LE UUIDS and their equivalent names

    @author : Pieter Hoekstra, pieter@nr8.nl

    All other files in this package are copy pasted from
    https://www.bluetooth.com/specifications/gatt

    A few adjustments:
    - and SPACE are translated as _ in the names of the variables
    
    To use as a maven repository : 
    
        implementation 'com.github.pieterhoekstra:BLEConstants:0.0.1'
        
   
   Usage :
   
   BLEConstants.getUUID(BLEService.Generic_Access));
   
   and: 
   
   BLEConstamts.getName((UUID) ....)

    
