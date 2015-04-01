# List errands in location #

/list?latitude=3.234&longitude=234.34 (range parameter is optional)
```
<errands>

    <errand id="123123" timeout="1234567890" user="olle" userReliability="1.0" price="50">
    // unix timestamp of timeout

        <location lat="12312323" long="123124" range="300">

        Hotel Ravishankar

        </location>

        // range in meters, text data is arbitrary user input

        <description>

        What is the name of the really cute waitress in the bar?

        </description>

    </errand>

    <errand ...>...< /> ... 

</errands>
```

# View errand #

/viewErrand?id=123123

```
    <errand id="123123" timeout="1234567890" user="olle" userReliability="1.0" price="50">
    // unix timestamp of timeout

        <location lat="12312323" long="123124" range="300">

        Hotel Ravishankar

        </location>

        // range in meters, text data is arbitrary user input

        <description>

        What is the name of the really cute waitress in the bar?

        </description>

    </errand>
```


# Perform errand #

/perform?user=Gussoh?id=123123&latitude=12312323&longitude=123124&answer=Amy
// location of user running errand, optional


response from server:
```
<response>
  <status>OK</status> // OK || ERROR
  <message></message>
</response>
```

# View errands created by user #

/listErrands?user=gussoh

Response:
```
<errands>

    <errand id="123123" timeout="1234567890" user="gussoh" price="50">

    // unix timestamp of timeout

        <location latitude="123.12323" longitude="12.3124" range="300">

        Hotel Ravishankar

        </location>

        // range in meters, text data is arbitrary user input

        <description>

        What is the name of the really cute waitress in the bar?

        </description>

        <answers>
             <answer id="2145442" user="Peter" timestamp="xxx" latitude="x" longitude="y" pointsRewarded="0">
                  Amy
             </answer>
        </answers>

    </errand>

    <errand ...>...< /> ... 

</errands>
```

# View errands performed by user #

/listErrandsPerformed?user=gussoh

Response:

```
<errands>

    <errand id="123123" timeout="1234567890" user="olle" price="50">

    // unix timestamp of timeout

        <location latitude="123.12323" longitude="12.3124" range="300">

        Hotel Ravishankar

        </location>

        // range in meters, text data is arbitrary user input

        <description>

        What is the name of the really cute waitress in the bar?

        </description>

        <answers>
             <answer id="2145442" user="Gussoh" timestamp="xxx" latitude="x" longitude="y">
                  Amy
             </answer>
        </answers>

    </errand>

    <errand ...>...< /> ... 

</errands>
```

# Give reward #

/reward?id=2145442

response:
```
<response>
  <status>OK</status> // OK || ERROR
  <message></message>
</response>


<response>
  <status>ERROR/status> // OK || ERROR
  <message>Already rewarded</message>
</response>
```

# Get user info #
/whois?user=gussoh

response:
```
<user id="olle">
   <balance>340</balance>
   <disposablebalance>130</disposablebalance>
   <reliability>0.743</reliability>
</user>
```