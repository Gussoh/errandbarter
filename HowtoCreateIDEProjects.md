# How the repository is used

Inside the /trunk folder there are two folders:
Server and Client

Server is the webbapplication, JSP web application (template from oddoneout).
Client is the MIDP-application.

**Start with checking out the project.** svn checkout. see "source tab".

# Server #
_How to create the server project in Netbeans:_

  1. Create new pweb application from existing sources.
  1. Source location: errandbarter/server/
  1. Project name/folder: whatever. I use "ErrandBarterServerProject"
  1. Netbeans should automatically find web folder and WEB-INF
  1. libraries is the server/lib folder
  1. Source package should be errandbarter/server/src/java/


# Client #
_How to create the client project in Netbeans:_

  1. Create new mobile project with existing MIDP sources.
  1. Select errandbarter/client/src/ as source and it should find the JAD-file automatically.

**remember not to put any IDE-specific files inside the svn**