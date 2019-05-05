cd ~/Xbee/bin
# watch -n 1 java -classpath $XBJL_CLASS_PATH com.digi.xbee.example.MainApp    
 
watch -n 1 java -classpath .:/home/pi/Xbee/libs/xbee-java-library-1.2.1.jar:/home/pi/Xbee/libs/RXTXcomm.jar:/home/pi/Xbee/libs/slf4j-api-1.7.12.jar:/home/pi/Xbee/libs/slf4j-nop-1.7.12.jar:/home/pi/Xbee/libs/okhttp-3.14.0.jar:/home/pi/Xbee/libs/okio-2.2.2.jar:/usr/lib/android-sdk/platforms/android-23/android.jar:/home/pi/Xbee/libs/kotlin-stdlib-1.3.21.jar:/home/pi/Xbee/libs/kotlin-stdlib-common-1.3.21.jar com.digi.xbee.example.MainApp    

