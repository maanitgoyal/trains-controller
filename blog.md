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
