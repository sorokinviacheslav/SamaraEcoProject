-- begin ECO_ADDRESS
create table ECO_ADDRESS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COORDINATES_ID varchar(36) not null,
    STREET_ID varchar(36),
    STREET_NUMBER integer not null,
    ADDRESS_PARAMS varchar(10),
    --
    primary key (ID)
)^
-- end ECO_ADDRESS
-- begin ECO_POINT
create table ECO_POINT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    LONGITUDE double precision not null,
    LATITUDE double precision not null,
    --
    primary key (ID)
)^
-- end ECO_POINT
-- begin ECO_ORGANIZATION
create table ECO_ORGANIZATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    INN varchar(20),
    DESCRIPTION varchar(1000) not null,
    --
    primary key (ID)
)^
-- end ECO_ORGANIZATION
-- begin ECO_STREET
create table ECO_STREET (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    STREET_TYPE integer not null,
    --
    primary key (ID)
)^
-- end ECO_STREET
-- begin ECO_BUILDING
create table ECO_BUILDING (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ADDRESS_ID varchar(36),
    DESCRIPTION varchar(1000),
    --
    primary key (ID)
)^
-- end ECO_BUILDING
-- begin ECO_BUILDING_ORGANIZATION_LINK
create table ECO_BUILDING_ORGANIZATION_LINK (
    BUILDING_ID varchar(36) not null,
    ORGANIZATION_ID varchar(36) not null,
    primary key (BUILDING_ID, ORGANIZATION_ID)
)^
-- end ECO_BUILDING_ORGANIZATION_LINK
