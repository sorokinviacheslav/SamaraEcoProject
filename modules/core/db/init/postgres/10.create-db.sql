-- begin ECO_ADDRESS
create table ECO_ADDRESS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COORDINATES_ID uuid not null,
    STREET_ID uuid,
    STREET_NUMBER integer not null,
    ADDRESS_PARAMS varchar(10),
    --
    primary key (ID)
)^
-- end ECO_ADDRESS
-- begin ECO_POINT
create table ECO_POINT (
    ID uuid,
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
    ID uuid,
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
    DATE date,
    IMAGE_FILE_ID uuid,
    --
    primary key (ID)
)^
-- end ECO_ORGANIZATION
-- begin ECO_STREET
create table ECO_STREET (
    ID uuid,
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
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ADDRESS_ID uuid,
    DESCRIPTION varchar(1000),
    --
    primary key (ID)
)^
-- end ECO_BUILDING
-- begin ECO_BUILDING_ORGANIZATION_LINK
create table ECO_BUILDING_ORGANIZATION_LINK (
    BUILDING_ID uuid,
    ORGANIZATION_ID uuid,
    primary key (BUILDING_ID, ORGANIZATION_ID)
)^
-- end ECO_BUILDING_ORGANIZATION_LINK
