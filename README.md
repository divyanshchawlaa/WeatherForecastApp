# 🌤️ Weather Forecast App (Java OOP Project)

## Project Overview

This is a Java-based **Weather Forecast Application** built using **Object-Oriented Programming (OOP) principles**.  
It fetches **real-time hourly and daily weather data** for any city worldwide using the **Open-Meteo API** and displays it in a **user-friendly Swing interface**.

## Features

- Fetch **current and forecasted weather** for cities worldwide.
- Display **hourly forecast** including:
  - Time  
  - Temperature (°C)  
  - Condition (with icons)  
  - Clothing recommendation  
  - Weather alerts  
  - Comfort/pleasantness score (1–10)
- Display **daily forecast** including:
  - Date  
  - Minimum & maximum temperatures  
  - Condition (with icons)
- Multi-line `JTable` cells showing **city + date**.  
- **Colored alerts** for extreme weather (hot, cold, rain, snow).  
- **History manager** to store previous forecasts and quickly revisit them.  
- Non-editable tables to prevent accidental changes.  
- **Hover-highlighted rows** with **color-coded temperatures** in the hourly view.  
- **Enter key support** to quickly fetch forecasts without clicking buttons.  
- Fully **API-driven**, with no hardcoded weather data.

## Tech Stack

- Java 11+  
- Swing for GUI  
- JSON library: **org.json** (`json` artifact)
- Git & GitHub for version control  

## Project Structure

```text
WeatherForecastApp/
├─ src/
│  ├─ WeatherApp.java              // Main entry point
│  ├─ WeatherAppGUI.java           // Swing GUI
│  ├─ APIWeatherProvider.java      // Fetches weather from Open-Meteo API
│  ├─ WeatherData.java             // Core weather model
│  ├─ HourlyWeatherData.java       // Hourly weather model (time, temp, condition, clothing, alert, score)
│  ├─ DailyWeatherData.java        // Daily weather model (date, min/max temp, condition)
│  ├─ HistoryManager.java          // Manages search history
│  ├─ WeatherScorer.java           // Computes comfort/pleasantness score
│  ├─ ClothingAdvisor.java         // Generates clothing suggestions
│  └─ Geocoder.java                // Converts city name → latitude/longitude
├─ lib/                            // JSON library JAR (if not using Maven)
├─ README.md
└─ .gitignore
```

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/divyanshchawlaa/WeatherForecastApp.git
```

2. Open the project in **IntelliJ IDEA**.  
3. Add `org.json.jar` (or the Maven dependency) to your project library.
4. Run `WeatherApp.java`.  
5. Enter a **city name**, select forecast days (1–7), and press **Enter** or click **Get Forecast / Fetch Weather**.  
6. Switch between **Hourly** and **Daily** tabs to view forecasts and use hover effects to see **color-coded temperatures**.

## API Used

- **Open-Meteo Weather API** for hourly and daily forecasts.
- Uses **geocoding** to convert city names to latitude/longitude.  
- Fetches:
  - **Hourly:** `temperature_2m`, `weathercode`  
  - **Daily:** `temperature_2m_max`, `temperature_2m_min`, `weathercode`

## Screenshots

![App Screenshot](images/screenshot1)
![App Screenshot](images/screenshot2)
![App Screenshot](images/screenshot3)
  
  

## Demo Video

[Watch the demo video](link-to-your-video)

## Notes

- Ensure you have **internet connectivity** to fetch data from the API.
- The **comfort score** is calculated based on how close the temperature is to around 22°C.  
- **Clothing suggestions** are automatically generated based on temperature and alerts.  
- **Alerts** appear for conditions such as extreme heat, cold, rain, drizzle, or snow.

## Author

- Name: Divyansh Chawla  
- Student ID: GH1031116  
