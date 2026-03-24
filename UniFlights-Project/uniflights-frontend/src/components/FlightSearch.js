import React, { useState } from "react";
import { searchFlights, saveFlight } from "../api";
import "./FlightSearch.css";

export default function FlightSearch() {

  const [tripType,setTripType] = useState("one");

  const [from,setFrom] = useState("");
  const [to,setTo] = useState("");

  const [departDate,setDepartDate] = useState("");
  const [returnDate,setReturnDate] = useState("");

  const [passengers,setPassengers] = useState(1);
  const [travelClass,setTravelClass] = useState("economy");

  const [flights,setFlights] = useState([]);
  const [filtered,setFiltered] = useState([]);

  const [maxPrice,setMaxPrice] = useState(2000);
  const [airlineFilter,setAirlineFilter] = useState("all");


  const logos = {

    Lufthansa:
      "https://logo.clearbit.com/lufthansa.com",

    Emirates:
      "https://logo.clearbit.com/emirates.com",

    "Air India":
      "https://logo.clearbit.com/airindia.com",

    KLM:
      "https://logo.clearbit.com/klm.com",

    "British Airways":
      "https://logo.clearbit.com/britishairways.com"

  };


  const handleSearch = async () => {

    const data = await searchFlights({

      from,
      to,
      departDate,
      returnDate,
      tripType,
      passengers,
      travelClass

    });

    setFlights(data);
    setFiltered(data);

  };


  const applyFilter = () => {

    let list = [...flights];

    list = list.filter(
      f => f.price <= maxPrice
    );

    if (airlineFilter !== "all") {

      list = list.filter(
        f => f.airline === airlineFilter
      );

    }

    setFiltered(list);

  };


  const handleSave = (f) => {

    const token = localStorage.getItem("token");

    if (!token) {
      alert("Login first");
      return;
    }

    saveFlight({

      airline: f.airline,
      price: f.price,
      departDate: f.departDate,
      returnDate: f.returnDate,
      passengers: f.passengers,
      travelClass: f.travelClass,
      link: f.deep_link

    }, token);

  };


  return (

    <div className="main-layout">


      {/* SEARCH BAR */}

      <div className="search-bar">

        <button
          className={tripType==="one"?"active":""}
          onClick={()=>setTripType("one")}
        >
          One Way
        </button>

        <button
          className={tripType==="round"?"active":""}
          onClick={()=>setTripType("round")}
        >
          Round Trip
        </button>


        <input
          placeholder="From"
          value={from}
          onChange={e=>setFrom(e.target.value)}
        />

        <input
          placeholder="To"
          value={to}
          onChange={e=>setTo(e.target.value)}
        />

        <input
          type="date"
          value={departDate}
          onChange={e=>setDepartDate(e.target.value)}
        />

        {tripType==="round" && (

          <input
            type="date"
            value={returnDate}
            onChange={e=>setReturnDate(e.target.value)}
          />

        )}


        <select
          value={passengers}
          onChange={e=>setPassengers(e.target.value)}
        >

          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>

        </select>


        <select
          value={travelClass}
          onChange={e=>setTravelClass(e.target.value)}
        >

          <option value="economy">
            Economy
          </option>

          <option value="business">
            Business
          </option>

          <option value="first">
            First
          </option>

        </select>


        <button onClick={handleSearch}>
          Search
        </button>

      </div>



      <div className="content">


        {/* SIDEBAR */}

        <div className="sidebar">

          <h3>Filters</h3>

          <label>Max Price</label>

          <input
            type="range"
            min="100"
            max="2000"
            value={maxPrice}
            onChange={e=>setMaxPrice(e.target.value)}
          />

          <p>${maxPrice}</p>


          <label>Airline</label>

          <select
            value={airlineFilter}
            onChange={e=>setAirlineFilter(e.target.value)}
          >

            <option value="all">All</option>
            <option>Lufthansa</option>
            <option>Emirates</option>
            <option>Air India</option>
            <option>KLM</option>

          </select>


          <button onClick={applyFilter}>
            Apply
          </button>

        </div>



        {/* RESULTS */}

        <div className="results">

          {filtered.map(f => (

            <div
              key={f.id}
              className="flight-card"
            >

              <img
                src={logos[f.airline]}
                alt=""
                width="50"
              />

              <h3>{f.airline}</h3>

              <p>${f.price}</p>

              <p>Passengers: {f.passengers}</p>

              <p>Class: {f.travelClass}</p>

              <p>Depart: {f.departDate}</p>

              {f.returnDate && (
                <p>Return: {f.returnDate}</p>
              )}

              <a
                href={f.deep_link}
                target="_blank"
                rel="noreferrer"
              >
                Book
              </a>

              <button
                onClick={()=>handleSave(f)}
              >
                Save
              </button>

            </div>

          ))}

        </div>

      </div>

    </div>

  );

}