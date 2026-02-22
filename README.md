# Weather Program

For this module, I plan to create a Weather Information Server using Python and socket programming (TCP). 
The server will accept connections from multiple clients, get a location request, retrieve weather data from a Json file, send a Json response to the client. 
The client will send a weather request for a specific location, will be able to display and receive weather information, and place multiple requests for multiple locations. 
The features for this project will be to get the current weather and temperature conversion from C to F. 


## Instructions for Build and Use

[Software Demo](Put_Your_Video_Link_Here)

Steps to build and/or run the software:

1. server.py, client.py, and the json file in the same folder
2. Run the server.py in the first terminal 
3. In a second terminal run the client.py. 
4. Obtained a json file with weather information of locations within the US
5. Added temperature convertions from C to F

Instructions for using the software:

1. Enter a location in the JSON format
2. Decide the temperature you want. Celsius is the default in case nothing is selected
3. Repeat and try with other locations. Type quit to exit

## Development Environment

To recreate the development environment, you need the following software and/or libraries with the specified versions:

* Latest version of python and VS code 
*
*

## Useful Websites to Learn More

I found these websites useful in developing this software:

* https://docs.python.org/3/library/json.html
* https://docs.python.org/3/library/threading.html
* https://www.w3schools.com/python/python_json.asp
* https://chat.openai.com/ and Copilot 



## Future Work

The following items I plan to fix, improve, and/or add to this project in the future:

* [ ] Probably add a command that lists all the available locations on the json file so the user knows
* [ ] Add a file that contains international places, outside the US
* [ ] Add a real API integration 

