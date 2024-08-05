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

CREATE TABLE IF NOT EXISTS ChannelGroups
(
    ID          int          auto_increment,
    name        varchar(32)  not null,
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS ChannelGroupMembers
(
    ID          int          auto_increment,
    ChannelGroupID    int          not null,
    UserID      int          not null,
    primary key (ID),
    constraint ChannelGroupMembers_ChannelMembersID_fk
        foreign key (ChannelGroupID) references ChannelGroups (ID),
    constraint ChannelGroupMembers_UserID_fk
        foreign key (UserID) references Users (ID)
);

CREATE TABLE IF NOT EXISTS ChannelGroupChannels
(
    ID          int          auto_increment,
    ChannelGroupID    int          not null,
    ChannelID   int          not null,
    primary key (ID),
    constraint ChannelGroupChannels_ChannelsID_fk
        foreign key (ChannelGroupID) references ChannelGroups (ID),
    constraint ChannelGroupChannels_ChannelID_fk
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
    ChannelGroupMemberID int       not null,
    primary key (RoleID, ChannelGroupMemberID),
    constraint RoleMembers_RoleID_fk
        foreign key (RoleID) references Roles (ID),
    constraint RoleMembers_ChannelGroupMemberID_fk
        foreign key (ChannelGroupMemberID) references ChannelGroupMembers (ID)
);

CREATE TABLE IF NOT EXISTS RequiredRole (
                                            RoleID      int          not null,
                                            ChannelGroupChannelID int       not null,
                                            primary key (RoleID, ChannelGroupChannelID),
                                            constraint RequiredRole_RoleID_fk
                                                foreign key (RoleID) references Roles (ID),
                                            constraint RequiredRole_ChannelGroupChannelID_fk
                                                foreign key (ChannelGroupChannelID) references ChannelGroupChannels (ID)
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

-- Insert test data for ChannelGroups
INSERT INTO ChannelGroups (name) VALUES ('Main ChannelGroup');
INSERT INTO ChannelGroups (name) VALUES ('Secondary ChannelGroup');

-- Insert test data for ChannelGroupMembers
INSERT INTO ChannelGroupMembers (ChannelGroupID, UserID) VALUES (1, 1);
INSERT INTO ChannelGroupMembers (ChannelGroupID, UserID) VALUES (1, 2);
INSERT INTO ChannelGroupMembers (ChannelGroupID, UserID) VALUES (1, 3);
INSERT INTO ChannelGroupMembers (ChannelGroupID, UserID) VALUES (2, 1);

-- Insert test data for ChannelGroupChannels
INSERT INTO ChannelGroupChannels (ChannelGroupID, ChannelID) VALUES (1, 1);
INSERT INTO ChannelGroupChannels (ChannelGroupID, ChannelID) VALUES (1, 2);

-- Insert test data for Roles
INSERT INTO Roles (name, priority) VALUES ('Admin', 1);
INSERT INTO Roles (name, priority) VALUES ('Moderator', 2);
INSERT INTO Roles (name, priority) VALUES ('Member', 3);

-- Insert test data for RoleMembers
INSERT INTO RoleMembers (RoleID, ChannelGroupMemberID) VALUES (1, 1);
INSERT INTO RoleMembers (RoleID, ChannelGroupMemberID) VALUES (2, 2);
INSERT INTO RoleMembers (RoleID, ChannelGroupMemberID) VALUES (3, 3);

-- Insert test data for RequiredRole
INSERT INTO RequiredRole (RoleID, ChannelGroupChannelID) VALUES (1, 1);
INSERT INTO RequiredRole (RoleID, ChannelGroupChannelID) VALUES (2, 2);

-- Insert test data for Permissions
INSERT INTO Permissions (description) VALUES ('Read');
INSERT INTO Permissions (description) VALUES ('Write');
INSERT INTO Permissions (description) VALUES ('Delete');

-- Insert test data for RolePermissions
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (1, 1);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (1, 2);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (2, 1);
INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (3, 1);



create definer = DAO@`%` view MessagesWithUsername as
select `m`.`ID`         AS `ID`,
       `m`.`timestamp`  AS `timestamp`,
       `m`.`channel_ID` AS `channel_ID`,
       `m`.`user_ID`    AS `user_ID`,
       `u`.`name`       AS `name`,
       `m`.`content`    AS `content`
from (`ODDiscord`.`Messages` `m` join `ODDiscord`.`Users` `u` on (`u`.`ID` = `m`.`user_ID`))

