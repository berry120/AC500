AC500
=======

A simple Java libary for retrieving time information from the Galleon AC500 atomic clock receiver.

Example usage:

    AC500 ac500 = new AC500(AC500_PORT);
    ZonedDateTime ldt = ac500.getTime().asZonedDateTime(); //Get current time as the Java 8 LocalDateTime object
    DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    System.out.println(dtf.format(ldt)); //eg. Wed, 7 Jan 2015 17:30:13 GMT