CREATE TABLE IF NOT EXISTS Statuses
(
    ID          int          not null,
    color        varchar(32)  not null,

    primary key (ID)
);

CREATE TABLE IF NOT EXISTS Users
(
    ID          int          auto_increment,
    name        varchar(32)  not null,
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

INSERT INTO Users (name, password) VALUES ('admin', 'admin');
INSERT INTO Users (name, password) VALUES ('user', 'user');
INSERT INTO Users (name, password) VALUES ('dennis', 'dennis');
