# CS 441- Engineering Distributed Objects for Cloud Computing

# **_**NAME: JEET MEHTA**_**

# UIN: 668581235

## Homework 2 - Log File Generator

## Overview

The objective of this homework was to process the log generator file using **Hadoop Map Reduce Framework**. This will help in parallel processing of the data.

## Instructions

### My Environment

The project was developed using the following environment
1) Windows OS
2) IDE: IntelliJ IDEA 2021
3) VMWare Workstation 16 Pro
4) Hortonworks 3.0.1 Sandbox 
### Pre-requisite

1) Java 1.8 needs to be installed on the system
2) Setup the HDP Sandbox
3) SBT needs to be installed on your system

### Demo of how to deploy the map-reduce jobs on the AWS EMR

Click on this [link](https://www.youtube.com/watch?v=yhbI30JZJ0o) to see how to deploy your Map Reduce on AWS EMR

### Working of the Map Reduce:

We start off by creating a log generator dataset. We have created a log file consisting of 50,000 log messages. This will be used to implement all the four jobs to be performed.
We then perform tasks for all the four [functionalities](https://github.com/0x1DOCD00D/LogFileGenerator#functionality) mentioned.


### MapReduce Jobs
 
1) Job 1:
   Mapper Class: Mapper_Job1
   Reducer Class: Reducer_Job1
   Goal: To show different messages(ERROR, DEBUG, INFO, WARN) across predefined time intervals along with their string instances of the designated regex pattern.
   ![](C:\Users\ASUS\Desktop\Mapper_Job1.png)

2) Job 2:
   Mapper Class: Mapper_Job2
   Reducer Class: Reducer_Job2
   Goal: The message of type ERROR is to be displayed in the descending order of its time interval having the strings instances of the designated string pattern.
   ![](C:\Users\ASUS\Desktop\Mapper_Job2.png)
3) Job 3:
   Mapper Class: Mapper_Job3
   Reducer Class: Reducer_Job3
   Goal: Compute aggregation of the messages produced. For eg. (ERROR, 16), (INFO,22)
   ![](C:\Users\ASUS\Desktop\Mapper_Job3.png)
4) Job 4:
   Mapper Class: Mapper_Job4
   Mapper Class: Reducer_Job4
   Goal: For each of the message type we have to compute the total number of characters it's string instances has which are found in the designated Regex pattern.
   ![](C:\Users\ASUS\Desktop\Mapper_Job4.png)

### Running these jobs

1. Clone this repo onto your system
2. Open command line of your OS and browse to project directory
3. Build using(In the Intellij terminal or cmd in Windows):
   `sbt clean compile assembly`
4. Using VSCode open the folder of this jar file and click on Go Live.
5. Start VMWare Workstation Pro
6. Run using:
   `hadoop jar jarname.jar inp_dir out_dir`

### OUTPUT:

This is the sample output that I have received for each of the jobs.

#### Output for Map_Reduce Job1:

14610=DEBUG,1
14611=INFO,1
14611=WARN,1
14612=DEBUG,1
14614=INFO,1
14614=WARN,1
14615=ERROR,1
14617=ERROR,1
14617=INFO,1
14617=WARN,1

#### Output for Map_Reduce Job2:

7,14618
7,14669
7,14886
7,14881
7,14848
7,14761
7,14641
4,14740
4,14671
4,14692

#### Output for Map_Reduce Job3:

DEBUG=10,8737
ERROR=10,843

### Output for Map_Reduce Job4:

INFO,10
WARN,10