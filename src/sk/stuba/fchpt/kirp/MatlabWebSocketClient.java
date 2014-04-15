package sk.stuba.fchpt.kirp;
/*   
This software is covered under the 2-clause BSD license.

Copyright (c) 2014, Michal Kvasnica   
Copyright (c) 2013, Brendan Andrade
All rights reserved.

Redistribution and use in source and binary forms, with or without 
modification, are permitted provided that the following conditions 
are met:

Redistributions of source code must retain the above copyright 
notice, this list of conditions and the following disclaimer.
  
Redistributions in binary form must reproduce the above copyright 
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
*/

/* This implementation is loosely derived from 
 * https://github.com/BrendanAndrade/web-matlab-bridge/blob/master/Java-WebSocket/src/main/java/org/java_websocket/bridge/ROSBridgeClient.java
*/

/* 
 Uses the Java-WebSocket module from https://github.com/TooTallNate/Java-WebSocket 
*/

/*
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
*/

import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.EventObject;
import java.util.EventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatlabWebSocketClient extends WebSocketClient {
	
	public String message;
	private URI server;
    private List _listeners = new ArrayList();
    private boolean debug = false;
	
	public MatlabWebSocketClient(URI server) {
		super(server);
		this.server = server;
	}

    public MatlabWebSocketClient(URI server, boolean debug) {
		super(server);
		this.server = server;
        this.debug = debug;
	}

	@Override
	public void onMessage( String message ) {
		this.message = message;
        if (this.debug) {
            System.out.println("onMessage: "+this.message);
        }
        _fireMessageEvent( message );
    }
			
	@Override
	public void onOpen( ServerHandshake handshake ) {
        if (this.debug) {
            System.out.println("Opening connection to "+this.server.toString());
        }
	}
			
	@Override
	public void onClose( int code, String reason, boolean remote ) {
        if (this.debug) {
            System.out.println("Connection closed: "+this.server.toString());
        }
	}
			
	@Override
	public void onError( Exception ex ) {
        if (this.debug) {
            System.out.println("Error: " + ex);
        }
	}
    
    public synchronized void addMessageListener(MessageListener L) {
        _listeners.add(L);
    }
    
    public synchronized void removeMessageListener(MessageListener L) {
        _listeners.remove(L);
    }
    
    private synchronized void _fireMessageEvent(String message) {
        MessageEvent message_event = new MessageEvent( this, message);
        Iterator listeners = _listeners.iterator();
        while (listeners.hasNext() ) {
            ( (MessageListener) listeners.next() ).messageReceived( message_event );
        }
    }
    
    public class MessageEvent extends EventObject {
        public String message;
        private static final long serialVersionUID = 1L;
        
        public MessageEvent( Object source, String message) {
            super( source );
            // System.out.println("MessageEvent: "+message);
            this.message = message;
        }
        
    }
    
    public interface MessageListener extends java.util.EventListener {
        void messageReceived( MessageEvent event );
    }

}
