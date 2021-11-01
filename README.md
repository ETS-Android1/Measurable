# Measurable
Back in 2017, when I was in highschool, AR was not as common place as it is today. Being able to measure real life objects with your phone was still a fairly new concept, and I thought I would take a stab at it. 
Measurable is an Android application that allows users to measure the height and distance away from objects. 

The application works by getting the height of the user, and using the accelerometer to find the angle from the normal. 
We can then use sin and cos to find the distance to the object, and the height of the object. 

There are still a lot of limitations to the application: 
  1. The application assumes that the object and the user are on a level ground, and at the same altitude.
  2. A noise reduction algorithm would be useful to get a more approximate value for theta. 
  3. Technically a user's total height is not the correct value since they are measuring from the eyes, however I tried to address this by adding a varying forehead length paramter. 
  
A demo of the application can be found here:
https://drive.google.com/file/d/10Twn7a8x-iGvW0Evn8BRh06VSD_2Wjyo/view?usp=sharing

<img src="record.gif" />
