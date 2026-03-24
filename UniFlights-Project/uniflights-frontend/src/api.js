import axios from "axios";

const API = "https://uniflights-backend.onrender.com";

export const searchFlights = async (params) => {

  try {

    const res = await axios.get(
      `${API}/api/flights/search`,
      { params }
    );

    return res.data.flights;

  } catch (err) {

    console.log(err);
    return [];

  }

};


// SAVE FLIGHT

export const saveFlight = async (data, token) => {

  try {

    const res = await axios.post(

      `${API}/api/bookings/save`,

      data,

      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }

    );

    return res.data;

  } catch (err) {

    console.log(err);

  }

};


// LOGIN

export const loginUser = async (data) => {

  const res = await axios.post(
    `${API}/api/auth/login`,
    data
  );

  return res.data;

};