INSTRUCTIONS:
    In this command line calendar application, you can create single events, multiday single events, and
    series of events.  Individual events can have specific start and end dates with times or be all day
    events with a start time of 08:00AM and end time of 17:00PM.  Singular events can be edited using
    the edit event command, but note that changing the start time of an event in a series will cause it
    to be removed from the series.  Multiple events can be edited in a series based on a specified start
    date using the edit events command.  Once again, if the start date is changing the events in the
    series on or after the specified date will form a new series.  A whole series of events can be
    edited by specifying the date of a specific event in the series and using the edit series command.
    These changes can be verified using the show status command which displays busy if there are events
    on a date and Available if not or any of the print events commands.  Command details have been
    listed below in features.

    This program can be run in interactive mode by specifying --mode interactive or in headless mode
    with file input using --mode headless fileName.txt in command line arguments

FEATURES:
    - NOTE: <property> can be subject, start, end, description, location, or status in the formats
    string, dateStringTimeString, dateStringTimeString, string, string, and string
    - NOTE: dateStringTimeString is in format YYYY-MM-DDThh:mm
    - NOTE: dateString is in format YYYY-MM-DD

    CREATE COMMANDS
    create event <eventSubject> from <dateStringTimeString> to <dateStringTimeString>
    create event <eventSubject> from <dateStringTimeString> to <dateStringTimeString> repeats
    <weekdays> for <N> times
    create event <eventSubject> from <dateStringTimeString> to <dateStringTimeString> repeats
    <weekdays> until <dateString>
    create event <eventSubject> on <dateString>
    create event <eventSubject> on <dateString> repeats <weekdays> for <N> times
    create event <eventSubject> on <dateString> repeats <weekdays> until <dateString>

    EDIT COMMANDS
    edit event <property> <eventSubject> from <dateStringTimeString> to <dateStringTimeString>
    with <NewPropertyValue>
    edit events <property> <eventSubject> from <dateStringTimeString> with <NewPropertyValue>
    edit series <property> <eventSubject> from <dateStringTimeString> with <NewPropertyValue>

    PRINT COMMANDS
    print events on <dateString>
    print events from <print events from <dateStringTimeString> to <dateStringTimeString>>
    to <dateStringTimeString>

    SHOW STATUS COMMANDS
    show status on <dateStringTimeString>

FEATURES TO FIX:
    none that we are aware of

WORK DISTRIBUTION:

    Both: overall MVC design, controller, create commands and factories, model data definitions


    Gauri: edit commands, view


    Issa: print and status commands, parsing input

ADDITIONAL INFORMATION:
- was not sure how to test exit command in CalendarControllerImplTest so commented out test in file.
when file with exit command was run the console showed that it exited the test method completely.
- all commands aside from edit are tested in commands folder, all edit commands are tested by using
the EditCommandFactory in EditCalendarTest