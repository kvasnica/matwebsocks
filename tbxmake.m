function tbxmake
% Creates, uploads and registers the distribution package

%% Package configuration

% package name on tbxmanager
config.package = 'matwebsocks';

% version to make
config.version = '1.3';

% package repository
config.repository = 'stable';

% platform of the package
config.platform = 'all';

% directory to create the archive from
config.directory = 'src/';

% archive format
config.format = 'zip';

% upload destination
config.destination = ['h1:~/public_html/tbxpool/' config.package '/'];

% upload method
config.method = 'scp';

% URL of the package for tbxmanager
config.url = ['http://www.uiam.sk/~kvasnica/tbxpool/' config.package '/'];


%% Do not edit beyond this line

% call the master make routine
tbxcli('make', config);

end
