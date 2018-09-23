[![Build Status](https://travis-ci.org/berry120/AC500.svg?branch=master)](https://travis-ci.org/berry120/AC500)

AC500
======

<img src="https://www.galsys.co.uk/images/time-receivers/ac-500-msf/msf-time-receiver-left-940x580.jpg" alt="AC500" width="400"/>

A simple Java libary for retrieving time information from the Galleon AC500 atomic clock receiver.

It will generally return the correct time to the nearest second or so, but is not hugely accurate beyond this point since it does not (at present) account for the transmit time. PR's to take the exact transmit time into account are welcome - this is nothing more than a thrown together proof of concept at present :-)

JSSC is required as a dependency for the serial communication.

Example usage:

    AC500 ac500 = new AC500("COM2"); //Change to whatever port the AC500 is attached to.
    ZonedDateTime ldt = ac500.getTime().asZonedDateTime(); //Get current time as the Java 8 LocalDateTime object
    DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    System.out.println(dtf.format(ldt)); //eg. Wed, 7 Jan 2015 17:30:13 GMT
