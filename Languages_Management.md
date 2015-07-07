# Introduction #

This page shows how to make and submit us your own languages XML files to add new languages in DEScribe.


# Location #
- DEScribe's language files are located in "language" directory of DEScribe's folder.<br />

# Contents #
Each XML language file contains the same number of "strings" elements associating a reference of a string to display in DEScribe and its translation in the current language. Reference is not choosen randomly but respects the following pattern :"JavaClassName.VariableName", so it is easier to call the getValueFromRef(ref) in source code.

<br />
For instance, you will find in english.xml the following "string" element :<br />
> 

&lt;string&gt;


> > 

&lt;ref&gt;

SessionFrame.strQuestionsSetLoaded

&lt;/ref&gt;


> > 

&lt;value&gt;

Questions successfully loaded!

&lt;/value&gt;



> 

&lt;/string&gt;


<br />Comment : Here,SessionFrame.strQuestionsSetLoaded reference is associated to the effective string "Questions successfully loaded!".
In source code, to get the effective translated string, just use method getValueFromRef("SessionFrame.strQuestionsSetLoaded").

# How it works #
At application's launch, or at language change, a hashmap associating references to strings is filled from xml file. Then, calling getValueFromRef method with in parameter a reference gives you the current translated string.

# How to send us new languages for DEScribe #
- Feel free to submit us new language files so we can add a new language to the application. To proceed, copy a file from directory "language" in DEScribe directory and translate it then rename it "yourlanguage.xml".<br />

# Contacts #
Sebastien Faure <sebastien.faure3@gmail.com><br />
Yannick Prié 	<yannick.prie@univ-lyon1.fr><br />
Amaury Belin    <amaury.belin@gmail.com><br />