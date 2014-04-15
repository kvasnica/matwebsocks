% creates matwebsocks.jar

% NOTE: the source must be compiled with "-target 1.6 -source 1.6",
% otherwise Matlab won't load the jar!

!rm sk/stuba/fchpt/kirp/*.class matwebsocks.jar
!javac -target 1.6 -source 1.6 -cp java_websocket.jar sk/stuba/fchpt/kirp/*.java
!jar cf matwebsocks.jar sk/stuba/fchpt/kirp/*.class
!rm sk/stuba/fchpt/kirp/*.class
