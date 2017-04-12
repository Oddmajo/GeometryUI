The Exception Handler is to gracefully handle exceptions.  That is, rather than stopping
the program from running, it logs the exception to a text file using the Logger (also in
Utilities).

The first throwException method takes in a String and only logs to the default exception
logger, as there is no way to know where else to log it.

The second throwException method takes an Exception.  It checks what class of Exception
the input is, and sets the logger it will log to accordingly.
NOTE: YOU MAY NEED TO ADD MORE CHECKS IF MORE EXCEPTION TYPES AND LOGGERS ARE REQUIRED.

Currently, there is an exception and related logger for each major component.  The
loggerID for each exception class is set within the Logger.  See the Logger's
README for more details on setting the loggerID for an exception class.

When creating a new Exception class, it is important to do a few things:
1) extend Exception

2) have the following:
	a static int loggerID
	a static getLoggerID() method
	a static setLoggerID(int) method
	a static setLoggerID(Logger) method
	a constructor (obviously)

See ArguementException as a complete and working example.