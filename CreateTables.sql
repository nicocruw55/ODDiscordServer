Drop DATABASE IF EXISTS ODDiscord;
CREATE DATABASE IF NOT EXISTS ODDiscord;

USE ODDiscord;
CREATE TABLE IF NOT EXISTS Statuses
(
    ID          int          not null,
    color        varchar(32)  not null,

    primary key (ID)
);

CREATE TABLE IF NOT EXISTS Users
(
    ID          int          auto_increment,
    name        varchar(32)  not null UNIQUE,
    password    varchar(64)  not null,
    status_ID   int          not null default 0,
    status_text varchar(32),
    primary key (ID),
    constraint Users_StatusID_fk
        foreign key (status_ID) references Statuses (ID)
);

CREATE TABLE IF NOT EXISTS Channels
(
    ID          int          auto_increment,
    name        varchar(32)  not null,
    topic       varchar(256) not null,
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS Messages
(
    ID          int          auto_increment,
    content        varchar(2048) not null,
    user_ID      int          not null,
    channel_ID   int          not null,
    timestamp   timestamp    not null default CURRENT_TIMESTAMP,
    primary key (ID),
    constraint Messages_UserID_fk
        foreign key (user_ID) references Users (ID),
    constraint Messages_ChannelID_fk
        foreign key (channel_ID) references Channels (ID)
);

CREATE TABLE IF NOT EXISTS ChannelMembers
(
    ChannelID   int          not null,
    UserID      int          not null,
    primary key (ChannelID, UserID),
    constraint ChannelMembers_ChannelID_fk
        foreign key (ChannelID) references Channels (ID),
    constraint ChannelMembers_UserID_fk
        foreign key (UserID) references Users (ID)
);

CREATE TABLE IF NOT EXISTS Server
(
    ID          int          auto_increment,
    name        varchar(32)  not null,
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS ServerMembers
(
    ID          int          auto_increment,
    ServerID    int          not null,
    UserID      int          not null,
    primary key (ID),
    constraint ServerMembers_ServerID_fk
        foreign key (ServerID) references Server (ID),
    constraint ServerMembers_UserID_fk
        foreign key (UserID) references Users (ID)
);

CREATE TABLE IF NOT EXISTS ServerChannels
(
    ID          int          auto_increment,
    ServerID    int          not null,
    ChannelID   int          not null,
    primary key (ID),
    constraint ServerChannels_ServerID_fk
        foreign key (ServerID) references Server (ID),
    constraint ServerChannels_ChannelID_fk
        foreign key (ChannelID) references Channels (ID)
);

CREATE TABLE IF NOT EXISTS Roles (
    ID          int          auto_increment,
    name        varchar(32)  not null,
    priority     int          not null,
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS RoleMembers
(
    RoleID      int          not null,
    ServerMemberID int       not null,
    primary key (RoleID, ServerMemberID),
    constraint RoleMembers_RoleID_fk
        foreign key (RoleID) references Roles (ID),
    constraint RoleMembers_ServerMemberID_fk
        foreign key (ServerMemberID) references ServerMembers (ID)
);

CREATE TABLE IF NOT EXISTS RequiredRole (
    RoleID      int          not null,
    ServerChannelID int       not null,
    primary key (RoleID, ServerChannelID),
    constraint RequiredRole_RoleID_fk
        foreign key (RoleID) references Roles (ID),
    constraint RequiredRole_ServerChannelID_fk
        foreign key (ServerChannelID) references ServerChannels (ID)
);

CREATE TABLE IF NOT EXISTS Permissions (
    ID          int          auto_increment,
    description varchar(32)  not null,
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS RolePermissions
(
    RoleID      int          not null,
    PermissionID int         not null,
    primary key (RoleID, PermissionID),
    constraint RolePermissions_RoleID_fk
        foreign key (RoleID) references Roles (ID),
    constraint RolePermissions_PermissionID_fk
        foreign key (PermissionID) references Permissions (ID)
);

# Testdata
-- Insert test data for Statuses
INSERT INTO Statuses (ID, color) VALUES (1, 'green');
INSERT INTO Statuses (ID, color) VALUES (2, 'red');
INSERT INTO Statuses (ID, color) VALUES (3, 'yellow');

-- Insert test data for Users
INSERT INTO Users (name, password, status_ID, status_text) VALUES ('admin', 'admin', 1, 'Online');
INSERT INTO Users (name, password, status_ID, status_text) VALUES ('user', 'user', 2, 'Offline');
INSERT INTO Users (name, password, status_ID, status_text) VALUES ('dennis', 'dennis', 3, 'Away');

-- Insert test data for Channels
INSERT INTO Channels (name, topic) VALUES ('general', 'General discussion');
INSERT INTO Channels (name, topic) VALUES ('random', 'Random chat');

-- Insert test data for Messages
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('Hello, world!', 1, 1);
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('How are you?', 2, 1);
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('Good morning!', 3, 2);

-- Insert test data for ChannelMembers
INSERT INTO ChannelMembers (ChannelID, UserID) VALUES (1, 1);
INSERT INTO ChannelMembers (ChannelID, UserID) VALUES (1, 2);
INSERT INTO ChannelMembers (ChannelID, UserID) VALUES (2, 3);

-- Insert test data for Server
INSERT INTO Server (name) VALUES ('Main Server');

-- Insert test data for ServerMembers
INSERT INTO ServerMembers (ServerID, UserID) VALUES (1, 1);
INSERT INTO ServerMembers (ServerID, UserID) VALUES (1, 2);
INSERT INTO ServerMembers (ServerID, UserID) VALUES (1, 3);

-- Insert test data for ServerChannels
INSERT INTO ServerChannels (ServerID, ChannelID) VALUES (1, 1);
INSERT INTO ServerChannels (ServerID, ChannelID) VALUES (1, 2);

-- Insert test data for Roles
INSERT INTO Roles (name, priority) VALUES ('Admin', 1);
INSERT INTO Roles (name, priority) VALUES ('Moderator', 2);
INSERT INTO Roles (name, priority) VALUES ('Member', 3);

-- Insert test data for RoleMembers
INSERT INTO RoleMembers (RoleID, ServerMemberID) VALUES (1, 1);
INSERT INTO RoleMembers (RoleID, ServerMemberID) VALUES (2, 2);
INSERT INTO RoleMembers (RoleID, ServerMemberID) VALUES (3, 3);

-- Insert test data for RequiredRole
INSERT INTO RequiredRole (RoleID, ServerChannelID) VALUES (1, 1);
INSERT INTO RequiredRole (RoleID, ServerChannelID) VALUES (2, 2);

-- Insert test data for Permissions
INSERT INTO Permissions (description) VALUES ('Read');
INSERT INTO Permissions (description) VALUES ('Write');
INSERT INTO Permissions (description) VALUES ('Delete');

-- Insert test data for RolePermissions
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (1, 1);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (1, 2);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (2, 1);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (3, 1);
