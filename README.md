# matwebsocks #

Simple Java-based websocket client for Matlab.

## Installation

The package can be installed by [tbxmanager](http://www.tbxmanager.com):
```
#!matlab

tbxmanager install matwebsocks
```

Then add java libraries to the static java path in `[prefdir filesep 'javaclasspath.txt']` by
```
#!matlab

matws_install()
```
and restart Matlab (really, this is required). Note that the libraries must be put on the **static** java classpath. Loading them dynamically via `javaaddpath()` will **not** work.

## Updating

To update the matwebsocks package (and other installed packages as well) to the latest version, use
```
#!matlab
tbxmanager update
```

## Usage

Create a new websocket object pointing to a given url:
```
#!matlab

socket = sk.stuba.fchpt.kirp.MatlabWebSocketClient(java.net.URI(socket_url))
```

Connect to the websocket:
```
#!matlab

socket.connect()
```

Send a string over the socket:
```
#!matlab

socket.send('my message')
```

Get the last received string message:
```
#!matlab

msg = socket.message
```

Add a custom callback which will be called when a new message arrives:
```
#!matlab

set(socket, 'MessageReceivedCallback', @(s, e) callback(s, e))
```
where `callback` is a function which takes two inputs: the socket object `s` and the event `e`. The message is available in `e.message`.

Close the websocket:
```
#!matlab

socket.close()
```

## More comfortable usage

Install the [wsclient package](http://bitbucket.org/kvasnica/wsclient).

## Demo

In this demo we connect to a websocket echo server:
```
#!matlab

% create the socket
server = 'ws://kirp.chtf.stuba.sk:8025/test/echo';
socket = sk.stuba.fchpt.kirp.MatlabWebSocketClient(java.net.URI(server));

% set the callback which displays received messages
set(socket, 'MessageReceivedCallback', @(~, e) fprintf('Received: %s\n', char(e.message)));

% connect the socket
socket.connect();

% send a message
socket.send('are you there?');

% you should see "Received: are you there?" printed in the Matlab command window

% close the socket
pause(0.5)
set(socket, 'MessageReceivedCallback', []); % clear the callback
socket.close();

```

## Credits

The implementation is based on Brendad Andrade's [web-matlab-bridge](https://github.com/BrendanAndrade/web-matlab-bridge) and uses the [Java WebSocket](https://github.com/TooTallNate/Java-WebSocket) library.
