# Introduction #
At any moment, user can export its session's data (answers, screenshots) in order to give/send it to DEScribe experimenter.


# Exporting a session #

**A session is exported by user from the sessions frame "export" button.** It creates a zip archive named (Username)_Session(SessionNumber)_(SessionName).zip.
**The archive contains the full session's folder. This folder contains, the XML file listing all answers** All the screenshots associated with answers in JPEG format

# About the XML session export file #

XML export file contains a list of "reponse" nodes located in the "reponses" root node. A "reponse" (=answer) contains the question associated with this answer ("question" node), the rules of the question (node "rules", for instance "notif:fullscreen" means only ask if no other application is currently running in fullscreen mode, it also could be "if:copyText" for copy and paste of text), the answer itself ("content" node's value), the instant of answering ("timecode" node) and the screenshot's path if exists (node "screenshot").

# Example of XML file obtained with DEScribe #



&lt;reponses&gt;


> 

&lt;reponse&gt;


> > 

&lt;question&gt;

1. What was your experience?

&lt;/question&gt;


> > 

&lt;rules&gt;

notif:fullscreen

&lt;/rules&gt;


> > 

&lt;content&gt;

1. What was your experience? : I was thinking about my day tasks. A bit nervous. Using calendar application.

&lt;/content&gt;


> > 

&lt;timecode&gt;

Thu Jul 21 10:22:56 CEST 2011

&lt;/timecode&gt;


> > 

&lt;screenshot&gt;

sessions\session6\session6\_reponse1\_screenshot.jpg

&lt;/screenshot&gt;


> > 

&lt;/reponse&gt;




&lt;/reponses&gt;

