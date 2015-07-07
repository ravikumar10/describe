# Introduction #

Contents of DEScribe's XML form files

"questions" node contains a lists of "question" which "title" node value is the question itself. "type" attribute determinates rather question is "open" (answer free to be keyed in) or "QCMRadio" (multiple choices question with at most one selected) or "QCMChkBox (multiple choices question with at most one selected). "QCMXXX" type questions contain "choice" nodes which values are answer choices. Actions represents something to do at the moment of the question. At the moment it can only be "take a screenshot". Screenshots are saved to chosen session folder (settings frame). Questions also have a "rules" node containing "rule" nodes which represent conditions of asking or non asking of the question ( for instance rule : type="notif" event="fullscreen" means that the question won't be asked if an application is currently running in fullscreen mode).

# Questions with rules, then actions and session meta data #
Root is "questions" node. Within this node, are first a list of "question" nodes described further in this Wiki page. Then a node "actions" containing a list of actions to do when the question is asked. After "actions" node, comes a "meta" node containing any other information about the session itself like the time to live of any session created with this form or the number max of Questions asked per hour (for instance 2 per hour max). Example : <br />



&lt;questions&gt;


> 

&lt;question&gt;


> ...
> 

&lt;/question&gt;




> <!-- Actions associated with all questions of the form -->
> <!-- Takes a screenshot every time a question is asked -->
> 

&lt;actions&gt;


> > 

&lt;action type="screenshot"&gt;


> > 

&lt;/action&gt;



> 

&lt;/actions&gt;


> <!-- When a new session is created the countdown starts -->
> <!-- Once it reaches his end, session is automatically  -->
> <!-- closed and user is warned 				-->
> <!-- Max is 2 questions asked per hour			-->
> 

&lt;meta&gt;



> 

&lt;htimetolive&gt;

48

&lt;/htimetolive&gt;


> 

&lt;nbQuestionsPerHour&gt;

2

&lt;/nbQuestionsPerHour&gt;




&lt;/meta&gt;




&lt;/questions&gt;




# Example of open question #
User can type his own answer. In the following example, "What was your experience?" is asked if a file (or a list of files) is copied and only if no fullscreen application is running at the moment of the question.<br />


&lt;question type="open"&gt;


> 

&lt;title&gt;

What was your experience?

&lt;/title&gt;


> 

&lt;rules&gt;


> > 

&lt;rule type="if" event="copyFile" /&gt;


> > 

&lt;rule type="notif" event="fullscreen" /&gt;



> 

&lt;/rules&gt;




&lt;/question&gt;




<br />![http://describe.googlecode.com/files/askFrameOpenQuestion.jpg](http://describe.googlecode.com/files/askFrameOpenQuestion.jpg)<br />

# Example of Multiple Choice Question with 0..1 answer (Radio Buttons) #
One answer max.<br />
> 

&lt;question type="QCMRadio"&gt;


> > 

&lt;title&gt;

Did you know what you were doing with the mouse's cursor?

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;

Yes

&lt;/choice&gt;


> > > 

&lt;choice type="normal"&gt;

No

&lt;/choice&gt;



> > 

&lt;/choices&gt;



> 

&lt;rules&gt;


> > 

&lt;rule type="notif" event="fullscreen" /&gt;



> 

&lt;/rules&gt;


> 

&lt;/question&gt;



<br />![http://describe.googlecode.com/files/askFrameQCMRadioQuestion.jpg](http://describe.googlecode.com/files/askFrameQCMRadioQuestion.jpg)<br />

# Example of Multiple Choice Question with 0..n answers (Checkboxes) #
As many answers as user wants.<br />
> 

&lt;question type="QCMChkBox"&gt;


> > 

&lt;title&gt;

You were thinking about...

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;

...the past

&lt;/choice&gt;



> > 

&lt;choice type="normal"&gt;

...the present

&lt;/choice&gt;


> > 

&lt;choice type="normal"&gt;

...the future

&lt;/choice&gt;


> > > 

&lt;/choices&gt;


> > > 

&lt;rules&gt;



> > 

&lt;rule type="notif" event="fullscreen" /&gt;


> > > 

&lt;/rules&gt;



> 

&lt;/question&gt;



<br />![http://describe.googlecode.com/files/askFrameQCMChkBoxQuestion.jpg](http://describe.googlecode.com/files/askFrameQCMChkBoxQuestion.jpg)<br />

<br />

More question types to come...<br />
<br />

# Full form example #
<br />
If you want to study experience in copy and pate context, here is an intersting form example of what you can do.
<br />
<?xml version="1.0" encoding="iso-8859-1"?>


&lt;questions&gt;



> <!-- Copy-and-paste of text event -->
> 

&lt;question type="QCMRadio"&gt;


> > 

&lt;title&gt;


> > Were you really about to paste this text?
> > 

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > Yes

> > > 

&lt;/choice&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > No

> > > 

&lt;/choice&gt;



> > 

&lt;/choices&gt;


> > 

&lt;rules&gt;


> > > 

&lt;rule type="if" event="copyText"&gt;


> > > 

&lt;/rule&gt;


> > > 

&lt;rule type="notif" event="fullscreen"&gt;


> > > 

&lt;/rule&gt;



> > 

&lt;/rules&gt;



> 

&lt;/question&gt;


> 

&lt;question type="QCMRadio"&gt;


> > 

&lt;title&gt;


> > Did you know exactly where you were going to paste this text?
> > 

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > Yes

> > > 

&lt;/choice&gt;


> > > 

&lt;choice type="other"&gt;


> > > > No

> > > 

&lt;/choice&gt;



> > 

&lt;/choices&gt;


> > 

&lt;rules&gt;


> > > 

&lt;rule type="if" event="copyText"&gt;


> > > 

&lt;/rule&gt;


> > > 

&lt;rule type="notif" event="fullscreen"&gt;


> > > 

&lt;/rule&gt;



> > 

&lt;/rules&gt;



> 

&lt;/question&gt;


> 

&lt;question type="QCMRadio"&gt;


> > 

&lt;title&gt;


> > Were you about to paste this text in another application?
> > 

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > Yes

> > > 

&lt;/choice&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > No

> > > 

&lt;/choice&gt;



> > 

&lt;/choices&gt;


> > 

&lt;rules&gt;


> > > 

&lt;rule type="if" event="copyText"&gt;


> > > 

&lt;/rule&gt;


> > > 

&lt;rule type="notif" event="fullscreen"&gt;


> > > 

&lt;/rule&gt;



> > 

&lt;/rules&gt;



> 

&lt;/question&gt;


> 

&lt;question type="QCMRadio"&gt;


> > 

&lt;title&gt;


> > Were you feeling any particular state of mind or feeling?
> > 

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;


> > > > No

> > > 

&lt;/choice&gt;


> > > 

&lt;choice type="other"&gt;


> > > > Yes. Please describe it down there:

> > > 

&lt;/choice&gt;



> > 

&lt;/choices&gt;


> > 

&lt;rules&gt;


> > > 

&lt;rule type="if" event="copyText"&gt;


> > > 

&lt;/rule&gt;


> > > 

&lt;rule type="notif" event="fullscreen"&gt;


> > > 

&lt;/rule&gt;



> > 

&lt;/rules&gt;



> 

&lt;/question&gt;


> 

&lt;question type="QCMChkBox"&gt;


> > 

&lt;title&gt;

You were thinking about...

&lt;/title&gt;


> > 

&lt;choices&gt;


> > > 

&lt;choice type="normal"&gt;

...the past

&lt;/choice&gt;


> > > 

&lt;choice type="normal"&gt;

...the present

&lt;/choice&gt;


> > > 

&lt;choice type="other"&gt;

...the future

&lt;/choice&gt;



> > 

&lt;/choices&gt;


> > 

&lt;rules&gt;


> > > 

&lt;rule type="notif" event="fullscreen" /&gt;


> > > 

&lt;rule type="if" event="copyText"&gt;


> > > 

&lt;/rule&gt;



> > 

&lt;/rules&gt;



> 

&lt;/question&gt;




> <!-- Actions associated with all questions of the form -->
> > 

&lt;actions&gt;


> > <!-- Takes a screenshot every time a question is asked -->
> > > 

&lt;action type="screenshot"&gt;


> > > 

&lt;/action&gt;


> > > 

&lt;/actions&gt;



> 

&lt;meta&gt;


> > <!-- When a new session is created the countdown starts -->
> > <!-- Once it reaches his end, session is automatically  -->
> > <!-- closed and user is warned 							-->
> > 

&lt;htimetolive&gt;

48

&lt;/htimetolive&gt;




> <!-- Max is 3 questions asked per hour					-->
> 

&lt;nbQuestionsPerHour&gt;

3

&lt;/nbQuestionsPerHour&gt;


> 

&lt;/meta&gt;




&lt;/questions&gt;



<br />
<br />
<br />The result
<br />![http://describe.googlecode.com/files/askFrameFullFormExample.jpg](http://describe.googlecode.com/files/askFrameFullFormExample.jpg)<br />