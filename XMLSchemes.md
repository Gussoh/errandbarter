## Propsed XML schemes: ##
List of errands:


&lt;errands&gt;


> 

&lt;errand id="123123" timeout="1234567890" user="olle" price="50"&gt;

 // unix timestamp of timeout
> > 

&lt;location lat="12312323" long="123124" range="300"&gt;

Hotel Ravishankar

&lt;/location&gt;

 // range in meters, text data is arbitrary user input
> > 

&lt;description&gt;

What is the name of the really cute waitress in the bar?

&lt;/description&gt;



> 

&lt;/errand&gt;



> <errand ...>...< />
> ...


&lt;/errands&gt;



## User info: ##


&lt;userinfo user="olle" balance="340" disposablebalance="130"&gt;


> 

&lt;pending&gt;


> > <errand .... />
> > ...

> 

&lt;/pending&gt;


> 

&lt;done&gt;


> > <errand... />

> 

&lt;/done&gt;




&lt;/userinfo&gt;



## Create new errand: ##
> 

&lt;errand timeout="1234567890" user="olle" price="50"&gt;

 // unix timestamp of timeout
> > 

&lt;location lat="12312323" long="123124" range="300"&gt;

Hotel Ravishankar

&lt;/location&gt;

 // range in meters, text data is arbitrary user input
> > 

&lt;description&gt;

What is the name of the really cute waitress in the bar?

&lt;/description&gt;



> 

&lt;/errand&gt;

