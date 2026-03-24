import React from "react";

import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Navbar from "./components/Navbar";
import FlightSearch from "./components/FlightSearch";
import Login from "./components/Login";
import Register from "./components/Register";
import SavedFlights from "./components/SavedFlights";

import "./App.css";

function App() {

  return (

    <BrowserRouter>

      <Navbar />

      <Routes>

        <Route
          path="/"
          element={<FlightSearch />}
        />

        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/register"
          element={<Register />}
        />

        <Route
          path="/saved"
          element={<SavedFlights />}
        />

      </Routes>

    </BrowserRouter>

  );

}

export default App;