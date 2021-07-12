JACOB ORELLANA ASSESSMENT PROJECT TO IOET

Hello!!

Content 
1. Code requirements.
2. Project description.
3. Project installation.
4. Technical details and USER MANUAL.
5. Other details.

--------------------------------------------------------------------------------

1. Code Requirements

This project was made using:

  Java 1.8
  
  Eclipse Eclipse IDE for Enterprise Java Developers. Version: 2019-09 R (4.13.0).
  
  MAVEN 3.6
  
  Windows X.
  
The project has Maven dependencies for JUNIT, no binaries were commited.
  
--------------------------------------------------------------------------------

2. Project Description

Excercise

The company ACME offers their employees the flexibility to work the hours they want. They will pay for the hours worked based on the day of the week and time of day, according to the following table:

Monday - Friday

00:01 - 09:00 25 USD
09:01 - 18:00 15 USD
18:01 - 00:00 20 USD
Saturday and Sunday
00:01 - 09:00 30 USD
09:01 - 18:00 20 USD
18:01 - 00:00 25 USD

The goal of this exercise is to calculate the total that the company has to pay an employee, based on the hours they worked and the times during which they worked. The following abbreviations will be used for entering data:
MO: Monday
TU: Tuesday
WE: Wednesday
TH: Thursday
FR: Friday
SA: Saturday
SU: Sunday

Input: the name of an employee and the schedule they worked, indicating the time and hours. This should be a .txt file with at least five sets of data. You can include the data from our two examples below.
Output: indicate how much the employee has to be paid

For example:
Case 1:
INPUT
RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00
OUTPUT:
The amount to pay RENE is: 215 USD
Case 2:
INPUT
ASTRID=MO10:00-12:00,TH12:00-14:00,SU20:00-21:00
OUTPUT:
The amount to pay ASTRID is: 85 USD

--------------------------------------------------------------------------------

3. Project Installation

Download the code as ZIP.

Import the solution as "Maven Project" in Eclipse.

Run the Maven pom.xml file with the option INSTALL.

  The installation will create the folder C:\jacob, be sure you have permission.
  
  The installation will run JUNIT tests.
  
--------------------------------------------------------------------------------

4. Technical details and USER MANUAL

This project will be a runnable jar, the execution take one single parameter from commmand line:

-> the path for the a_jacob_sample,txt, pay_by_day.txt.  Described below.

Typically this path will be C:/jacob as the pom.xml shows.

The project was made to be fully configurable.  It means, the hours range and values could be changed.
You will need to INSTALL the project again in case of changes.

In the to_process folder inside the project, you will see 2 files:

a_jacob_sample

pay_by_day.txt


The file a_jacob_sample.txt has the Employee Work information.
The pay_by_day.txt has has the "pay by hour range" information.  

Format and content of the pay_by_day.txt:

Pay by hour range file
hour gaps are not supported yet, fill all the day with ranges.  From 00:00 to 23:59
	  
Hours should not overlap, consistency will be supported soon.

End of the day is 23:59 for calculations consistency.

You will find the following notes in the file.

Format:

   day(2 digits)  tab start_hour tab end_hour tab value

#MONDAY

MO	00:01	09:00	25

MO	09:01	18:00	15

MO	18:01	23:59	20

#TUESDAY

TU	00:01	09:00	25

TU	09:01	18:00	15

TU	18:01	23:59	20

#WEDNESDAY

WE	00:01	09:00	25

WE	09:01	18:00	15

WE	18:01	23:59	20

#THURSDAY

TH	00:01	09:00	25

TH	09:01	18:00	15

TH	18:01	23:59	20

#FRIDAY

FR	00:01	09:00	25

FR	09:01	18:00	15

FR	18:01	23:59	20

#SATURDAY

SA	00:01	09:00	30

SA	09:01	18:00	20

SA	18:01	23:59	20

#SUNDAY

SU	00:01	09:00	25

SU	09:01	18:00	20

SU	18:01	23:59	25


After the execution the output will be something like this:

RENE=215.0

ASTRID=85.0

JACOB=230.0


--------------------------------------------------------------------------------

5. Other details

This project validates the configuration syntax consistency.

This project was meant to be simple, no framework was used.

All the biz logic is in the com.jacob.bizOperations.IoetLogic Class

All the solution was made from scratch, to avoid copy-paste.

It was built in 3.5 hours, but the main biz logic takes an hour.

After this excercise, I am building an on-promises localhost simple CI/CD infraestructure to show you my knowledge.



Greetings,

Jcb



