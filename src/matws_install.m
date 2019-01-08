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

% read the contents of javaclasspath, remove lines that contain our jars
fid = fopen(classFile, 'r');
new = {};
if fid>0
    tline = fgetl(fid);
    while ischar(tline)
        include = true;
        for i = 1:numel(jars)
            include = include & isempty(strfind(tline, jars{i}));
        end
        if include
            new{end+1} = tline;
        end
        tline = fgetl(fid);
    end
    fclose(fid);
end

fprintf('Adding jars to %s\n', classFile);
fid = fopen(classFile, 'w');
if fid==-1
    error('WSCLIENT:FileError', 'Unable to create "%s".', classFile);
end
% write back original content sans our jars
for i = 1:numel(new)
    fprintf(fid, '%s\n', new{i});
end
% add our jars
for i = 1:length(jars)
    fprintf('  %s\n', jars{i});
    fprintf(fid, '%s%s%s\n', ourPath, filesep, jars{i});
end
fclose(fid);
fprintf('\nYou need to restart Matlab now!\n');

end
