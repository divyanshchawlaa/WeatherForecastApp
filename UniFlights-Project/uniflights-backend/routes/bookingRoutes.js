const express = require("express");
const router = express.Router();

const pool = require("../config");

const verifyToken =
  require("../middleware/authMiddleware");


// SAVE FLIGHT

router.post(
  "/save",
  verifyToken,
  async (req, res) => {

    const userId = req.user.id;

    const {
      airline,
      price,
      departDate,
      returnDate,
      passengers,
      travelClass,
      link
    } = req.body;

    try {

      const result = await pool.query(

        `INSERT INTO saved_flights
        (user_id, airline, price,
         depart_date, return_date,
         passengers, travel_class, link)

         VALUES ($1,$2,$3,$4,$5,$6,$7,$8)

         RETURNING *`,

        [
          userId,
          airline,
          price,
          departDate,
          returnDate,
          passengers,
          travelClass,
          link
        ]

      );

      res.json(result.rows[0]);

    } catch (err) {

      console.log(err);

      res.status(500).json({
        error: "Save failed"
      });

    }

  }
);


// GET SAVED FLIGHTS

router.get(
  "/",
  verifyToken,
  async (req, res) => {

    const userId = req.user.id;

    try {

      const result =
        await pool.query(

          "SELECT * FROM saved_flights WHERE user_id=$1",

          [userId]

        );

      res.json(result.rows);

    } catch (err) {

      console.log(err);

      res.status(500).json({
        error: "Fetch failed"
      });

    }

  }
);



module.exports = router;