import React, { useState } from "react";
import { loginUser } from "../api";

export default function Login() {

  const [email,setEmail] = useState("");
  const [password,setPassword] = useState("");

  const handleLogin = async () => {

    const data = await loginUser({
      email,
      password
    });

    localStorage.setItem(
      "token",
      data.token
    );

    alert("Logged in");

  };

  return (

    <div>

      <h2>Login</h2>

      <input
        placeholder="email"
        onChange={e=>setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="password"
        onChange={e=>setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>
        Login
      </button>

    </div>

  );

}