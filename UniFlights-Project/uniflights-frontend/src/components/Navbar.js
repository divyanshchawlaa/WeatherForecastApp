import React from "react";
import { Link } from "react-router-dom";
import "../App.css";

export default function Navbar() {

  return (

    <div className="navbar">

      <div className="logo">
        UniFlights ✈️
      </div>

      <div className="nav-links">

        <Link to="/">Search</Link>

        <Link to="/login">Login</Link>

        <Link to="/register">Register</Link>

        <Link to="/saved">Saved</Link>

      </div>

    </div>

  );

}