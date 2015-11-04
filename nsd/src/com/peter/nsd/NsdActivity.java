package com.peter.nsd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.RegistrationListener;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class NsdActivity extends Activity {

	 private String SERVICE_NAME = "NsdServer";
	 private String SERVICE_TYPE = "_nsdserver._tcp.";
	 private static final String REQUEST_CONNECT_CLIENT = "request-connect-client";
	 private SocketServerThread socketServerThread;
	 private NsdManager mNsdManager;
	 TextView textView;
	 //socket server running at port number 6000
	 private int SocketServerPort = 6000;
	 
	 private List<String> clientIPs;
	 
	 private static final String TAG = "NSDServer";
	 
	 private static final String REQUEST_TOAST_ME = "say_hi_to_server";
	 
	 public void showToast(final String toast) {
		 NsdActivity.this.runOnUiThread(new Runnable() {
	     public void run() {
	    	 Toast.makeText(NsdActivity.this,toast,Toast.LENGTH_LONG).show();
	      }
	     });
	 }
	 
	 public void showMessage(final String message) {
		 NsdActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				textView.setText(message);
			}
			 
		 });
	 }
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsd);
        
        textView = (TextView) findViewById(R.id.textView1);
        
        mNsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
        //nsd service running at port number 9000 
        registerService(9000);
        clientIPs = new ArrayList<String>();
        socketServerThread = new SocketServerThread();
        socketServerThread.start();
    }
    
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(SERVICE_NAME);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);
        
        mNsdManager.registerService(serviceInfo,NsdManager.PROTOCOL_DNS_SD,mRegistrationListener);
    }

	RegistrationListener mRegistrationListener = new NsdManager.RegistrationListener() {
    	 
        @Override
        public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
            String mServiceName = NsdServiceInfo.getServiceName();
            SERVICE_NAME = mServiceName;
            Log.d(TAG, "Registered name : " + mServiceName);
        }
 
        @Override
        public void onRegistrationFailed(NsdServiceInfo serviceInfo,
                                         int errorCode) {
            // Registration failed! Put debugging code here to determine
            // why.
        }
 
        @Override
        public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
            // Service has been unregistered. This only happens when you
            // call
            // NsdManager.unregisterService() and pass in this listener.
            Log.d(TAG,
                  "Service Unregistered : " + serviceInfo.getServiceName());
        }
 
        @Override
        public void onUnregistrationFailed(NsdServiceInfo serviceInfo,
                                           int errorCode) {
            // Unregistration failed. Put debugging code here to determine
            // why.
        }
    };
    
    private class SocketServerThread extends Thread {
    	 
        @Override
        public void run() {
 
            Socket socket = null;
            ServerSocket serverSocket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;
 
            try {                
                Log.i(TAG, "Creating server socket");                
                serverSocket = new ServerSocket(SocketServerPort);
 
                while (true) {
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream( 
                        socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
 
                    String messageFromClient = "", messageToClient, request;
                    
                    ArrayList<String> strList = new ArrayList<String>();
                    
                    //If no message sent from client, this code will block the Thread
                    BufferedReader in = new BufferedReader(new InputStreamReader(dataInputStream));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataOutputStream));
                    int count_space = 0;
                    while(true) {
                    	messageFromClient = in.readLine().toString();
                    	if(messageFromClient.equals(""))
                    		count_space += 1;
                    	if(count_space == 2)
                    		break;
                    	strList.add(messageFromClient);
                    	
                    }
                    
                    final JSONObject jsondata;
                    Log.d("message-from-client", strList.toString());
                    if(strList.size() == 1 || strList.get(1).trim().equals("") 
                    		|| strList.size() == 0 || !strList.get(0).equals("GET / HTTP/1.1")) {
                    	writer.write("HTTP/1.1 400 Bad Request\n");
                    	writer.newLine();
                    }
                    else {
                    	writer.write("HTTP/1.1 200 ok");
                    	writer.newLine();
                    	writer.write("Content-Type: text/plain");
                    	writer.newLine();
                    	writer.write("X-Powered-By: Server Socket");
                    	writer.newLine();
                    	writer.write("Content-Length: " + "Hello World!".getBytes().length);
                    	writer.newLine();
                    	writer.newLine();
                    	writer.write("Hello World!".trim());
                    	writer.newLine();
                    }
                    
                    writer.flush();
                    showToast(strList.toString());
                    showMessage(strList.toString());
                    /*
                    try {
                        jsondata = new JSONObject(messageFromClient);
                        request = jsondata.getString("request");
 
                        if (request.equals(REQUEST_CONNECT_CLIENT)) {
                            String clientIPAddress = jsondata.getString("ipAddress");
 
                            // Add client IP to a list
                            clientIPs.add(clientIPAddress);
                            showToast("Accepted");
                            showMessage("Connection Accepted");
                            messageToClient = "Connection Accepted";
 
 
                            // Important command makes client able to send message
                            dataOutputStream.writeUTF(messageToClient);
                            // ****** Paste here  Bonus 1
 
                            // ****** Paste here  Bonus 1
                        }
                        else if (request.equals(REQUEST_TOAST_ME)) {
                        	 String mess = jsondata.getString("ipAddress");
                        	 showToast(mess);
                        	     
                        	 messageToClient = "Connection Accepted";
                        	 dataOutputStream.writeUTF(messageToClient);
                        }
                        else {
                            // There might be other queries, but as of now nothing.
                            dataOutputStream.flush();
                        }
 
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Unable to get request");
                        dataOutputStream.flush();
                    }
                    */
                    
                }
 
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }   
 
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
 
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
 
        }       
 
    }
    
    

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	 if (mNsdManager != null) {
             mNsdManager.unregisterService(mRegistrationListener);
         }
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		 if (mNsdManager != null) {
			 mNsdManager.unregisterService(mRegistrationListener);
	     }
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*
		if (mNsdManager != null) {
            registerService(9000);
        }*/
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nsd, menu);
        return true;
    }
    
}
