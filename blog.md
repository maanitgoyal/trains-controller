Blog 1 - Task A

Day 1- 25-09-2025

It's a really long assignment. I have thought a lot about how to structure my task A. I was confused if I should use InfoResponses classes for my task A but they have written that I can't use that. So I have decided to make my own classes from stations, track and trains. 
I have completed the Station.java file and I hope it fulfills all the requirements. I have also finished Track.java and will work on Train.java tomorrow.

Day 2- 26-09-2025
I have started Train.java but I am not able to figure out how to check if a route is circular or not. 
Update - I have made a function in Train.java for checking this. The function will use list of tracks which is in TrainsController.java and will check for a track between two stations based on that. I have completed Task A. It wasn't that hard.

Blog 2 - Task B

Day 1 - 26-09-2025
This is a really big task. A lot of stuff is happening at once . I am starting with simulating function. I need to modify Station.java to return maximum number of trains allowed at a station. Now i am thinking that i need to make current number of trains as well. Added few functions in Station.java for accounting current number of trains. Simulate function is done. Had to make some changes in Train.java. Have added some new fields for tracking last station the train has visited. This was easy.
I just found out that I missed checking for circular routes in simulate function. I have added that now. I also forgot to increment and decrement the current amount of trains, I have done that now. Used ternary operator to use cur index which will help in that.
Now for task bii, i am figuring out how to check for loads and passengers. Probably have to make few classes.
I have created Passenger.java and Cargo.java for storing passenger and cargo info. I feel like i need to find station in lot of classes so I am gonna make a helper file for that.

Day 2 - 27-09-2025
For task bii, i need several functions for different conditions. I need functions for:

i - Function to decrease the train load when cargo and passengers disembark
ii - Function to increase the train load. For this i also need a function to see if the train has the destination station in its route.

I have created two classes for passenger and cargo. But I need to change it to Load.java. I am figuring out how to add all this stuff in simulate() function. In the meantime, i have added some functions in train.java and station.java.
I have made a function in Helper.java to simulate the embark criteria of the load and now i'll figure out disembark. I have done that as well. So with this, my main functionality is done and now i need to add LoadInfoResponses to both Train.java and Station.java. I am almost there just need to figure out how i am gonna add the cargo to the train for the first ever simulate because my program is not taking that into account. For this i need to make a function which checks how many trains are there on the station when the load is created.
I dont know what happening but my code is wrong somewhere. I will try this some other time.

Day 3 - 28-09-2025
I figured my bug in my sleep that i am updating LoadInfoResponse in Station and Train class but the actual StationInfoResponse class is not being updated. I need to figure out how to do that. There is no setter in StationInfoRespoonse for setting load. Nope this was not the bug. The bug was that while deleting the loadinforesponses i was not doing a null check. So my program was crashing. I have changed the function for adding and deleting loadInfoResponses from station and train and now I make new list everytime from the current loads present. I have also changed simulate function to check embark and disembark from stationCur instead of stationFinal. I am doing one more mistake. I need to delete the loads in disembark section but i am still keeping them. For this i just delete the line in disembark function where i was adding that load to the station. bii should be good now.
Task biii looks easy. Should not take much time. I forgot to add the functionality for setting load position while simulate. Need to confirm this spec - (including the time it takes to stop at stations).
I think biii should be good now.
I have tested my code on the frontend. It's working fine but there is one weird bug. Its called ConcurrentModificationException. The perishablecargo when gets deleted the frontend goes into some error. but when i click simulate again the frontend starts working fine again. So i am gonna write a test now to check if there is some problem with my code or is it the frontend. This error is coming in test as well so I need to do debug it now. Okay i figured it out. I am removing an element using for loop iterator. I cant do that so changing it now. I have made a toRemove array and i delete every element in that array from the main array. biii works perfectly in the frontend as well now.

Blog 3 - Task C

Day 1 - 28-09-2025
Task C looks pretty easy. It's just a lot of things all together but it shouldn't be that hard. Added isBreakable field in constructor for track. Added a method in Train.java for giving me the total weight of load on the train. Made a method in Helper.java to find a track between two stations.

Day 2 - 29-09-2025
The first thing i am gonna do today is add a method for setting the type of a track. Now I am working on trackSimulator in Helper.java.
I found a bug in my code. In simulate function I am using the wrong logic for embarking the passengers. In my code if a train has left a station and then I create a passenger at the station which the train left, in the next tick my train is picking up that passenger as well which should not be the case. I am getting ConcurrentModificationExceptions again and again, so I'm gonna use the iterator method to delete the items from now on. Changing all the deletion function to iterator based deletion. I have now fixed the Concurrent error and now i am checking if breakable track functionality is fine or not. The code is working fine. I found one more bug that the speed should be reset to the original speed.

Day 3 - 30-09-2025
The first thing which I'll do today is to make a method to reset speed of a train after it reaches a station. I need to change the method for train moving. My logic is flawed. But for now I will be starting with task cii. I have made controller in TrainsController.java. Add weight condition in embarking. Task cii works perfectly fine i think. I need to check for reverse route when the route line finishes.

Day 4 - 01-10-2025
There was a lot of feedback from my tutor. I need to refactor my entire code. I have to make parent class and sub classes which inherit from them. I am gonna start working on Station.java first. I have created 4 subclasses of Station representing every station. I have created a helper method in Helper.java which returns a Station object based on the type. In that function, I have used "POLYMORPHISM" concept of OOPS design, where I declare a variable of parent type (Station) and assign it to object of subclass. I have also changed the data structure for storing stations to hashmap. I have also deleted the InfoResponses variables in Station.java.
Now i am gonna refactor Track.java. Create a setter for location of a train. Finished with refactor of Train.java. Now I need to refactor Load.java. I have refactored most of the code and all of my tests pass.

Day 5 - 02-10-2025
My goal for today is to finish the assignment.
I think I should make more subclasses for Load.java and Track.java. I should distribute the methods which are used by just subclass to subclass only. I have made three new subclass for Load.java and I have created an interface for Train.java which has all the functions which are used for trains carrying Cargo or PerishableCargo. Now I am refining simulate function and adding logic for going in reverse route. I have fixed the reverse logic as well and my code is working fine now. I am doing some more refining and I'll be done with my assignment soon. I think I am done. Now the only thing that is left is adding JavaDoc and creating an interface for PassengerTrain i think.

Blog 4 - UML Diagram and Bug Testing
I am making the UML diagram for my code. Its going good although i need to ask how to connect Station and Train class. I also need to check if I am checking if mechanics can only embark on RepairTrains or both repair trains and PassengerTrains. Also ask about how Helper file will be connected.