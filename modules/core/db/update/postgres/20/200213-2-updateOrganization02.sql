alter table ECO_ORGANIZATION rename column image to image__u44003 ;
alter table ECO_ORGANIZATION drop constraint FK_ECO_ORGANIZATION_ON_IMAGE ;
alter table ECO_ORGANIZATION add column IMAGE_FILE_ID uuid ;
