# Project 2 The Power - Stock Application - StockInfoAPI

## API Endpoints

GET:/stocks
	-takes nothing, returns short list of tickers

GET:/compare/{stock1}/{stock2}
	- returns the daily data of two stocks

GET:/customindex/{timeframe}
	- returns the health of a custom index over a timeframe
		-{timeframe} should be a number of days ex. 5

GET:/Dailyhigh/{stock}/{days}
	-returns average high as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5

GET:/Dailylow/{stock}/{days}
	-returns average low as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5

GET:/DailyMovement/{stock}/{days}
	returns average change as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5

GET:/gradeIndex/
	-should be passed json in body
	- will take the movement, flucutation, highs & lows to get grade for index

GET:/gradeIndex/
	-should be passed json in body
	- will take the movement, flucutation, highs & lows to get grade for index


GET:/stock/{stock}/{days}
	returns average low,high,movement as a double for the numbers of days
	-{stock} should be the ticker
	-{days} sbhould be the number of days ex. 5

GET:/portfolio/
	-takes portfolio as json
	- will take the movement, flucutation, highs & lows & shares to get grade for portfolio

