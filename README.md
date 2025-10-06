# SOAS – Aplikacija za razmenu fiat i crypto valuta  

# Mikroservisi i API rute

# Currency Exchange (fiat rates)
GET http://localhost:8765/currency-exchange?from=USD&to=EUR

# Crypto Exchange (crypto rates)
GET http://localhost:8765/crypto-exchange?from=BTC&to=ETH

# Users Service
GET    http://localhost:8765/users
GET    http://localhost:8765/users/email?email=user@uns.ac.rs
POST   http://localhost:8765/users/newUser
	JSON:
	{
    		"email": "user1@uns.ac.rs",
    		"password": "password",
    		"role": "USER"
	}
PUT    http://localhost:8765/users
	JSON:
	{
    		"email": "user1@uns.ac.rs",
    		"password": "passwordd",
    		"role": "USER"
	}
DELETE http://localhost:8765/users/email?email=user@uns.ac.rs

# Bank Account Service
GET    http://localhost:8765/accounts
GET    http://localhost:8765/accounts/myAccount
POST   http://localhost:8765/accounts/newAccount
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"gbp": 0.00,
    		"usd": 110.00,
    		"eur": 0.00,
    		"chf": 130.00,
    		"rsd": 0.00,
    		"cad": 10.00
	}
PUT    http://localhost:8765/accounts
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"gbp": 100.00,
    		"usd": 0.00,
    		"eur": 120.00,
    		"chf": 0.00,
    		"rsd": 12000.00,
    		"cad": 100.00
	}
DELETE http://localhost:8765/accounts
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"gbp": 100.00,
    		"usd": 0.00,
    		"eur": 120.00,
    		"chf": 0.00,
    		"rsd": 12000.00,
    		"cad": 100.00
	}

# Currency Conversion 
GET http://localhost:8765/currency-conversion?from=EUR&to=USD&quantity=100 

# Crypto Wallet Service
GET    http://localhost:8765/wallets
GET    http://localhost:8765/wallets/myWallet
POST   http://localhost:8765/wallets/newWallet
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"btc": 10.0,
    		"eth": 0.00,
    		"ust": 20.00
	}

PUT    http://localhost:8765/wallets
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"btc": 11.0,
    		"eth": 10.00,
    		"ust": 20.00
	}
DELETE http://localhost:8765/wallets
	JSON:
	{
    		"email": "user@uns.ac.rs",
    		"message": null,
    		"btc": 0.00,
    		"eth": 0.00,
    		"ust": 0.00
	}

# Crypto Conversion
GET http://localhost:8765/crypto-conversion?from=BTC&to=ETH&quantity=1

# Trade Currency
GET http://localhost:8765/trade-currency?from=BTC&to=EUR&quantity=1
GET http://localhost:8765/trade-currency?from=EUR&to=UST&quantity=300

# Kredencijali

OWNER →  email: owner@uns.ac.rs   |  password: password
ADMIN →  email: admin@uns.ac.rs   |  password: password
USER  →  email: user@uns.ac.rs    |  password: password


