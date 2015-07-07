# Introduction #

Information about DEScribe according to your operating system.


# About DEScribe #
- DEScribe is a new open source java application for Descriptive Experience Sampling (DES). Launched in background, questions (loaded from an XML file form) will be asked to user and answers will be collected in order to export them (to XML file) and send them to an experimenter who will be able to analyze them.<br />

- A set of questions and their answers represents what is here called "session". A session can be created, consulted, exported to XML, paused or closed from the sessions frame.<br />

- DEScribe's question frame allows user to fill a form with questions generated during a session. It can have many questions of different types. At the end user can validate or skip the form (no data saved) by clicking on the frame's bottom buttons.
<br />![http://describe.googlecode.com/files/askFrameFullFormExample.jpg](http://describe.googlecode.com/files/askFrameFullFormExample.jpg)<br />
- DEScribe's sessions frame allows user to manage questions sessions (closed or not): i.e. close, delete, delay, export or visualize answers of a session.
<br />![http://describe.googlecode.com/files/sessionsFrame.jpg](http://describe.googlecode.com/files/sessionsFrame.jpg)<br />


- DEScribe's settings frame allows user to edit DEScribe settings like : sound, language and DEScribe's sessions output folder (which will contain every files producted by sessions, for instance, screenshots file and the XML answers file after exporting a session).
<br />![http://describe.googlecode.com/files/settingsFrame.jpg](http://describe.googlecode.com/files/settingsFrame.jpg)<br />
<br />

# MAC OS X #

- <b>How to install DEScribe:</b><br />
1. Launch DEScribe\_beta\_macosx.pkg and follow installer's instructions.<br />
2. DEScribe will now automatically get launched at user's logging.<br /><br />

- <b>How to uninstall DEScribe:</b><br />
1. Launch remove-des-at-launch.app located in DEScribe/login\_item/ directory in order to remove DEScribe from user's login item list (applications launched automatically when user gets logged).<br />
2. Delete des-application directory (/Applications/DEScribe).<br /><br />

# WINDOWS #
- <b>How to install DEScribe:</b><br />
1. Launch DEScribe\_beta\_windows installer and follow installer's instructions<br />
2. DEScribe will now automatically get launched at user's logging.<br /><br />

# LINUX #
- <b>How to install DEScribe:</b><br />
Compile java project from svn. Then add application to your starting at login process list according to your linux distribution.<br />

# How to send us new languages for DEScribe #
- Feel free to submit us new language files so we can add a new language to the application. To proceed, copy a file from directory "language" in DEScribe directory and translate it then rename it "yourlanguage.xml".<br />

# Contacts #
Sebastien Faure <sebastien.faure3@gmail.com><br />
Yannick Prié 	<yannick.prie@univ-lyon1.fr><br />
Amaury Belin    <amaury.belin@gmail.com><br />