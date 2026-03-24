import React, { useEffect, useState } from "react";
import axios from "axios";

export default function SavedFlights() {

  const [flights,setFlights] = useState([]);

  useEffect(()=>{

    const load = async () => {

      const token =
        localStorage.getItem("token");

      const res = await axios.get(

        "http://localhost:8000/api/bookings",

        {
          headers:{
            Authorization:
              `Bearer ${token}`
          }
        }

      );

      setFlights(res.data);

    };

    load();

  },[]);


  return (

    <div>

      <h2>Saved Flights</h2>

      {flights.map(f => (

        <div key={f.id}>

          {f.airline} - ${f.price}

        </div>

      ))}

    </div>

  );

}