const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");

const app = express();
const port = 8080;

// CORS setup for frontend access
app.use(cors());
app.use(bodyParser.json());

// sample data
let traders = [
  {
    key: "1",
    id: 1,
    firstName: "Mike",
    lastName: "Spencer",
    dob: "1990-01-01",
    country: "Canada",
    email: "mike@test.com",
    amount: 0,
    actions: "<button (click)='deleteTrader'>Delete Trader</button>",
  },
  {
    key: "2",
    id: 2,
    firstName: "Hellen",
    lastName: "Miller",
    dob: "1990-01-01",
    country: "Austria",
    email: "hellen@test.com",
    actions: "<button (click)='deleteTrader'>Delete Trader</button>",
    amount: 0,
  },
];

let quotes = [
  {
    ticker: "AAPL",
    lastPrice: 172.85,
    bidPrice: 172.8,
    bidSize: 1500,
    askPrice: 172.9,
    askSize: 1200,
  },
  {
    ticker: "GOOGL",
    lastPrice: 2815.5,
    bidPrice: 2815.0,
    bidSize: 1000,
    askPrice: 2816.0,
    askSize: 800,
  },
  {
    ticker: "AMZN",
    lastPrice: 3365.25,
    bidPrice: 3365.0,
    bidSize: 2000,
    askPrice: 3366.0,
    askSize: 1500,
  },
];

// fetch all traders
app.get("/dashboard/traders", (req, res) => {
  res.json(traders);
});

// fetch all quotes
app.get("/quotes/dailyList", (req, res) => {
  res.json(quotes);
});

// create a new trader
app.post(
  "/trader/firstname/:firstName/lastname/:lastName/dob/:dob/country/:country/email/:email",
  (req, res) => {
    const { firstName, lastName, dob, country, email } = req.params;

    const newTrader = {
      key: String(traders.length + 1),
      id: traders.length + 1,
      firstName,
      lastName,
      dob,
      country,
      email,
      amount: 0,
      actions: "<button (click)='deleteTrader'>Delete Trader</button>",
    };

    traders.push(newTrader);
    res.status(201).json(newTrader);
  }
);

// delete a trader by ID
app.delete("/trader/:id", (req, res) => {
  const { id } = req.params;
  const index = traders.findIndex((trader) => trader.id === parseInt(id));
  if (index !== -1) {
    traders.splice(index, 1);
    res.status(200).json({ message: "Trader deleted successfully." });
  } else {
    res.status(404).json({ message: "Trader not found." });
  }
});

// start the server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
