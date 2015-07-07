# Introduction #

If you send an event notification to DEScribe, it will check if questions according to this event can be asked. If so, then question frame will appear containing all questions about this event.


# How it works #

DEScribe is designed to listen on port 32145 and receive JSON objects.
Thus, an event notification is a JSON object containing a field named "type". This "type" value of event has to be the name of a rule . For instance, with the "copyText" event which corresponds to text copy and paste, an object sent could be :
<br />
{"traceUri":"1D91EE76-3D47-596B-107F-6FCE84EE8D17","uri":"CF3413CD-FEC6-438C-0F29-6FD06BD8E907","begin":1875930072,"type":"copyText","end":0,"subject":"sujet1","props":{}}
<br />
Note: the only obligatory field at the moment is "type" but it could be optimized in the future.

With this example, a question will be asked if you have in your xml form file a rule of this kind for a question :
> 

&lt;rule type="if" event="copyText"&gt;


> 

&lt;/rule&gt;
