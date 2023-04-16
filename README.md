[![Gitter](https://img.shields.io/badge/Available%20on-Intersystems%20Open%20Exchange-00b2a9.svg)](https://openexchange.intersystems.com/package/tokenizator)

# Tokenizator
This is a REST Service to tokenize sensitive data, store it on IRIS SQL Cloud, and get real values again when necessary.

## What the Tokenizator Does

Tokenizator receives sensitive data to tokenize, store the original value into a secure isolated database (IRIS SQL Cloud) and allows to you get the original value again. You send to the Tokenizator the ticket that you received on the tokenizator response and you receive your original values.

## Installation: Docker
1. Clone/git pull the repo into any local directory

```
$ git clone https://github.com/yurimarx/tokenizator.git
```

2. Open the terminal in this directory and run:

```
$ docker build --tag=tokenizator:latest .
```

3. Run the Tokenizator container:

```
$ docker run -p8080:8080 tokenizator:latest
```


## How to Run the Sample

1. Open your Postman.
2. Create a request to Tokenize using STARS and PERSON methods to this sensitive data sample:
	- Method: POST
	- URL: http://localhost:8080/token/tokenize
	- Body (JSON): 
		```
		[
		    {
		    	"tokenType":"STARS",
		    	"originalValueString":"545049405679",
		    	"settings": {
		        	"starsPosition":"1",
		        	"starsQuantity":"4"
		    	}
		    },
		    {
			    "tokenType":"PERSON",
			    "originalValueString":"Yuri Marx Pereira Gomes",
			    "settings": {
			        "localeLanguage":"en",
			        "localeCountry":"US",
			        "withAddress":"true",
			        "withEmail":"true"
			    }
			}
		]
		```
	- See the results. You get a tokenizated value (tokenizedValueString) to store into your local database. 

3. Copy the ticket from the response (store it into your local database with the tokenized value)
4. Now with the ticket, you can get Original value. Create a request to get the original value using the ticket:
	- Method: GET
	- URL: http://localhost:8080/token/query-ticket/TICKET-VALUE-HERE
	- See your original values
	
