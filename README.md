AC500
=======

A simple Java libary for retrieving time information from the Galleon AC500 atomic clock receiver. It will generally return the correct time to the nearest second or so, but is not hugely accurate beyond this point since it does not (at present) account for the transmit time. (JSSC is required as a dependency for the serial communication.)

[Download the release here.](https://github.com/berry120/AC500/blob/master/AC500-1.0-Release.zip?raw=true)

Example usage:

    AC500 ac500 = new AC500("COM2"); //Change to whatever port the AC500 is attached to.
    ZonedDateTime ldt = ac500.getTime().asZonedDateTime(); //Get current time as the Java 8 LocalDateTime object
    DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    System.out.println(dtf.format(ldt)); //eg. Wed, 7 Jan 2015 17:30:13 GMT