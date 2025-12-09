# 🌤️ Weather Forecast App (Java OOP Project)

## Project Overview

This is a Java-based **Weather Forecast Application** built using **Object-Oriented Programming (OOP) principles**.
It fetches **real-time weather data** for any city worldwide using the **Open-Meteo API** and displays it in a **user-friendly Swing interface**.

## Features

* Fetch **current and forecasted weather** for cities worldwide.
* Multi-line JTable cells showing **city + date**.
* **Weather icons and condition descriptions**.
* **Temperature, clothing suggestions, and pleasantness score**.
* **Colored alerts** for extreme weather (hot, cold, rain).
* **History manager** to store previous forecasts.
* Non-editable table to prevent accidental changes.
* **Enter key support** to quickly fetch forecasts.

## Tech Stack

* Java 11+
* Swing for GUI
* JSON library (org.json)
* Git & GitHub for version control

## Project Structure

```
WeatherForecastApp/
├─ src/
│  ├─ WeatherAppGUI.java
│  ├─ WeatherApp.java
│  ├─ APIWeatherProvider.java
│  ├─ WeatherData.java
│  ├─ HistoryManager.java
│  ├─ WeatherScorer.java
│  ├─ ClothingAdvisor.java
│  └─ Geocoder.java
├─ lib/            ← JSON library JAR
├─ README.md
└─ .gitignore
```

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/divyanshchawlaa/WeatherForecastApp.git
```

2. Open the project in IntelliJ IDEA.
3. Add **org.json.jar** to your project library.
4. Run `WeatherApp.java`.
5. Enter a **city name** and press **Enter** or click **Get Forecast**.

## Screenshots

![App Screenshot](link-to-your-screenshot1)
![App Screenshot](link-to-your-screenshot2)

## Demo Video

[Watch the demo video](link-to-your-video)

## Author

* Name: Divyansh Chawla
* Student ID: GH1031116
