package com.digi.xbee.example;

import com.digi.xbee.api.models.XBeeMessage;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import java.util.Date;

public class MainApp {
	/* Constants */
	// TODO Replace with the port where your sender module is connected to.
	private static final String PORT = "/dev/ttyUSB0";
	// TODO Replace with the baud rate of your sender module.
	private static final int BAUD_RATE = 9600;

	public static void main(String[] args) throws IOException {
		XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);
		Double Buffer = 0d;
        String[] arg;
        while(true)
			try {
				// Open the Local Device
				myDevice.open();
				// Get NetWork
				XBeeNetwork xbeeNetwork = myDevice.getNetwork();
				// Connect to the remote device
				RemoteXBeeDevice remoteDevice = xbeeNetwork.discoverDevice(" ");
				if (remoteDevice == null) {
					// Unable to connect to device
					System.out.println("Couldn't find the remote XBee device with '" + "" + "' Node Identifier.");
				}

				else {
					System.out.println("Successfully connected");

					// Read data from remote device
					XBeeMessage xbeeMessage = myDevice.readDataFrom(remoteDevice);

					// Message Test
					// System.out.println(xbeeMessage.getDataString());

					// Normalize string format
					if (xbeeMessage != null && xbeeMessage.getDataString().split("-").length >= 2) {
						arg = (xbeeMessage.getDataString().split("-"))[1].split(",");
					} else
						return;

					// Check data
					if (Buffer != Double.parseDouble(arg[1]) + Double.parseDouble(arg[1])
							+ Double.parseDouble(arg[1])) {
						Buffer = Double.parseDouble(arg[1]) + Double.parseDouble(arg[1]) + Double.parseDouble(arg[1]);
						System.out.println(Buffer);

						Date d = new Date();

						// Example for date transform
						// d.setTime(1553411690044L);
						// System.out.println(d.toString());
						// Result: Sun Mar 24 15:14:50 CST 2019

						// RowKey : Timestamp - SensorUID
						arg[0] = d.getTime() + "-" + arg[0];

						// Output Result for check
						for (String s : arg)
							System.out.print(s + " ");
						System.out.println();

						// After Postman test then generate JAVA code ## Remenber import the needed
						// packages/Libraries
						// ############################################## POSTMAN CODE##############################################
						OkHttpClient client = new OkHttpClient();
						MediaType mediaType = MediaType
							.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
						RequestBody body = RequestBody.create(mediaType,
								"------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"RowKey\"\r\n\r\n"
								+ arg[0]
								+ "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Humi\"\r\n\r\n"
								+ Double.parseDouble(arg[1])
								+ "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Temp\"\r\n\r\n"
								+ Double.parseDouble(arg[2])
								+ "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Lumi\"\r\n\r\n"
								+ Double.parseDouble(arg[3]) + "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
						Request request = new Request.Builder()
							.url("http://140.118.5.51/ARProject/Script/Upload_Sensor_Data.php").post(body)
							.addHeader("content-type",
									"multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
							.addHeader("cache-control", "no-cache")
							.addHeader("Postman-Token", "f777817f-83c0-4101-9d4f-2c3433229930").build();

						Response response = client.newCall(request).execute();
						response.close();
						// ############################################## POSTMAN CODE##############################################
					} else {
						return;
					}
				}

			} catch (XBeeException e) {
				e.printStackTrace();
				System.exit(1);
			} finally {
				myDevice.close();
			}
	}
}

