alter table ECO_ORGANIZATION add constraint FK_ECO_ORGANIZATION_ON_IMAGE foreign key (IMAGE) references SYS_FILE(ID);
