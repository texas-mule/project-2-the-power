# User Routes

This is the definitions of the user routes and how to use them


## Add new user Route: 
**/users/newUserCreation**
<br/>
This route takes raw json that has a username key and a password key like so...

EXAMPLE:
```
{
	"username":"testUser",
	"password":"password"
}
```


## Get user info Route: 
GET
**/users/{username}**

This route takes a username as a URI param and returns a user object as json


## Add funds to user Route: 
POST
**/users/{username}/addFunds/{amount}**

This route takes a username as a uri param and an amount as a double via the uri param and returns the user as json with updated info


## Get custom index from user Route: 
GET
**/users/{username}/index**

This route takes a username as a uri param and returns as json the custom indexes for the user aka the stocks they choose to follow


## Add custom index from user Route:
POST 
**/users/{username}/index?stock=TickerSymbol&stock=TickerSymbol&stock=TickerSymbol...**



This route takes a username as a uri param and the stocks via query params for ticker symbols to add a new set of stocks the user wishes to follow, returns user as json with updated info 


Notes:
```
Ticker has to equal ticker.
TickerSymbol can be the real ticker Symbol
```

## Get portfolio to user Route: 
GET
**/users/{username}/portfolio**

This route takes a username as a uri param returns the portfolio for the user


## Add first portfolio to user Route:
**/users/{username}/portfolio?ticker=StockTicker&amount=Shares&ticker=StockTicker&amount=Shares...**

This route takes a username as a uri param and gets the tickers and amounts of shares from the query params and adds them to user 

Notes:
Should ONLY be used when user who is just created wants to add a portfolio
ticker equals ticker
StockTicker can be the stock name
amount equals amount
Shares is the number of shares you own


## Buy Stock user Route: 
POST
**/users/{username}/buy?ticker=TickerSymbol&amount=Number**

This route takes a username as a uri param and will add to your portfolio if funds are sufficient the stock and the amount you attempt to purchase, will detract from funds

Note:
```
ticker must equal ticker
TickerSymbol must be a symbol from your portfolio
amount must equal amount
Number must be within range of user funds

Can only buy any number of stocks, if funds allow, from one ticker symbol at a time
```

## Sell Stock user Route: 
POST
**/users/{username}/sell?ticker=TickerSymbol&amount=Number**

This route takes a username as a uri param and will sell stocks from your portfolio if valid within portfolio will add sell amount to user funds

Note:
```
ticker must equal ticker
TickerSymbol must be a symbol from your portfolio
amount must equal amount
Number must be a number within your portfolio

Can only sell any number of stocks, if you have them in your portfolio, from one ticker symbol at a time
```
