const express = require("express");
const router = express.Router();

const airlines = [
  "Lufthansa",
  "Air India",
  "British Airways",
  "Emirates",
  "Qatar Airways",
  "KLM",
  "Air France"
];

const random = (min, max) =>
  Math.floor(Math.random() * (max - min + 1)) + min;

router.get("/search", (req, res) => {

  const {
    from,
    to,
    departDate,
    returnDate,
    tripType,
    passengers,
    travelClass
  } = req.query;

  if (!from || !to || !departDate) {
    return res.status(400).json({
      message: "Missing params"
    });
  }

  const flights = Array.from({ length: 6 }).map((_, i) => {

    let basePrice = random(100, 500);

    // class price
    if (travelClass === "business") basePrice += 200;
    if (travelClass === "first") basePrice += 400;

    // passenger price
    basePrice *= Number(passengers || 1);

    // student discount
    basePrice = Math.floor(basePrice * 0.8);

    const airline =
      airlines[random(0, airlines.length - 1)];

    return {
      id: i + 1,
      airline,
      price: basePrice,
      departDate,
      returnDate:
        tripType === "round"
          ? returnDate
          : null,
      passengers,
      travelClass,
      deep_link:
        `https://www.google.com/flights?q=${from}+${to}+${departDate}`
    };

  });

  res.json({ flights });

});

module.exports = router;