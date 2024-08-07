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

create table Friends
(
    UserID   int not null,
    FriendID int not null,
    constraint Friends_pk
        primary key (UserID, FriendID),
    constraint Friends_Users_ID_ID_fk
        foreign key (FriendID, UserID) references Users (ID, ID)
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
INSERT INTO Statuses (ID, color, description) VALUES (1, 'green', 'Online');
INSERT INTO Statuses (ID, color, description) VALUES (2, 'red', 'Offline');
INSERT INTO Statuses (ID, color, description) VALUES (3, 'yellow', 'Away From Keyboard');

-- Insert test data for Users
insert into Users (name, password, status_ID) values ('a', 'a', 1);
INSERT INTO Users (name, password, status_ID) VALUES ('admin', 'admin', 1);
INSERT INTO Users (name, password, status_ID) VALUES ('obi', 'obi', 2);
INSERT INTO Users (name, password, status_ID) VALUES ('amir', 'amir', 3);

-- Insert test data for Channels
INSERT INTO Channels (name, topic) VALUES ('general', 'General discussion');
INSERT INTO Channels (name, topic) VALUES ('random', 'Random chat');
INSERT INTO Channels (name, topic) VALUES ('programming', 'Programming discussion');

-- Insert test data for Messages
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('Hello, world!', 1, 1);
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('How are you?', 2, 1);
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('Good morning!', 3, 2);
INSERT INTO Messages (content, user_ID, channel_ID) VALUES ('Server channel', 1, 3);

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
INSERT INTO ChannelGroupChannels (ChannelGroupID, ChannelID) VALUES (1, 4);
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
from (`ODDiscord`.`Messages` `m` join `ODDiscord`.`Users` `u` on (`u`.`ID` = `m`.`user_ID`));


create definer = DAO@`%` view FriendlistWithFriendname as
select `f`.`UserID` AS `UserID`,
       `u`.`ID` AS `friendID`,
       `u`.`name` AS `name`
from (`ODDiscord`.`Friends` `f` join `ODDiscord`.`Users` `u` on (`f`.`FriendID` = `u`.`ID`));


create definer = DAO@`%` view UserWithStatus as
select `u`.`ID` AS `id`,
       `u`.`name` AS `name`,
       `u`.`status_text` AS `status_text`,
       `s`.`description` AS `description`
from (`ODDiscord`.`Users` `u` join `ODDiscord`.`Statuses` `s` on (`u`.`status_ID` = `s`.`ID`));
