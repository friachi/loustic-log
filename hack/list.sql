#uncomment to init dB tables after sqlite file deletion
#CREATE TABLE LS_CONF (ID INTEGER PRIMARY KEY)
#CREATE TABLE LS_USER (ID INTEGER PRIMARY KEY , USER TEXT , PWD TEXT)
#CREATE TABLE LS_SESSION (ID INTEGER PRIMARY KEY ,SESSION BLOB)

#dummy user to test authentication
#insert into LS_USER (USER,PWD) values ('gkhoueiry@murex.com','123456')

#select all users
select * from LS_USER

#remove users by ID
#delete from LS_USER where ID = 2
