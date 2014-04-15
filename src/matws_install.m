function matws_install()
% Adds MatWebSocks java libraries to the static java class path
%
% Adds libraries to [prefdir filesep 'javaclasspath.txt']:
%   ./java_websocket.jar
%   ./matwebsocks.jar
%
% Warning: Classes that trigger events must be put on the
%          STATIC java classpath! Loading them dynamically
%          leads to an error when attaching an event listener.

ourPath = fileparts(which(mfilename));
jars = { 'java_websocket.jar', 'matwebsocks.jar' };
classFile = [prefdir filesep 'javaclasspath.txt'];
fid = fopen(classFile, 'a+');
if fid==-1
    error('WSCLIENT:FileError', 'Unable to create "%s".', classFile);
end
fprintf('Adding jars to %s\n', classFile);
for i = 1:length(jars)
    fprintf('  %s\n', jars{i});
    fprintf(fid, '%s%s%s\n', ourPath, filesep, jars{i});
end
fclose(fid);
fprintf('\nYou need to restart Matlab now!\n');

end
