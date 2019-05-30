# Project 2 The Power - Stock Application - StockInfoAPI

## API Endpoints
BASEURI: /stock

GET:/stocks
	-takes nothing, returns short list of tickers

GET:/compare/{stock1}/{stock2}
	- returns the daily data of two stocks

GET:/customindex/{timeframe}/{username}
	- returns the health of a custom index over a timeframe
		-{timeframe} should be a number of days ex. 5

GET:/high/{stock}/{days}
	-returns average high as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5

GET:/low/{stock}/{days}
	-returns average low as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5


GET:/stockinfo/{stock}/{days}
	returns average low,high,movement as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5
	
GET:/fairprice/{stock}
	returns an Array List including the stock peRatio, the sector peRatio, and a String 			dictating whether or not to buy.
	-{stock} should be the ticker

GET:/portfolio/{username}
	-takes portfolio as json
	- will take the movement, flucutation, highs & lows & shares to get grade for portfolio

