
Implement given TelephoneBillCalculator interface as maven module.

Parameter of calculate(String phoneLong) is a string representing the list of calls seperated by the end of line. It is in CSV format with following fields:

     - Telephone number in normalized format - numbers only (e.g. 420774555455)
     - Beginning of the call - dd-MM-yyyy HH:mm:ss
     - End of the call - dd-MM-yyyy HH:mm:ss

     Example:
     __________________________________________________________
     420774555455,13-01-2020 18:10:15,13-01-2020 18:12:57
     420772554980,18-01-2020 08:59:20,18-01-2020 09:10:00
     __________________________________________________________



Output of the method is the amount to be paid calculated by the following rules:

     1) Each minute which starts within the interval of <8:00:00,16:00:00) we charge rate of 1 Kč.
        We charge reduced rate of 0,50 Kč for each minute which starts outside of that interval. We charge also
        unfinished minutes.

     2) We charge reduced rate of 0,20 Kč for fifth minute of the call and further regardless of when the call is
        taking place (within or outside of the above interval or mixed).

     3) All cals to the most called number (number of calls; NOT total length) are for free - we do not charge
        them at all. In case there are two or more such numbers we choose the arithmetical highest number.