import React, { useEffect, useRef, useState } from 'react'
import './Weather.css'
import search_icon from '../assets/search.png'
import clearD_icon from '../assets/ClearD.png'
import clearN_icon from '../assets/ClearN.png'
import fewCloudD_icon from '../assets/FewCloudsD.png'
import fewCloudN_icon from '../assets/FewCloudsN.png'
import scatteredCloudD_icon from '../assets/ScatterCloudsD.png'
import scatteredCloudN_icon from '../assets/ScatterCloudsN.png'
import brokenCloudD_icon from '../assets/BrokenCloudsD.png'
import brokenCloudN_icon from '../assets/BrokenCloudsN.png'
import showerRainD_icon from '../assets/ShowerRainD.png'
import showerRainN_icon from '../assets/ShowerRainN.png'
import rainD_icon from '../assets/RainD.png'
import rainN_icon from '../assets/RainN.png'
import thunderstormD_icon from '../assets/ThunderstormD.png'
import thunderstormN_icon from '../assets/ThunderstormN.png'
import snowD_icon from '../assets/SnowD.png'
import snowN_icon from '../assets/SnowN.png'
import mistD_icon from '../assets/MistD.png'
import mistN_icon from '../assets/MistN.png'
import wind_icon from '../assets/wind.png'
import humidity_icon from '../assets/humidity.png'

const Weather = () => {
    const inputRef = useRef()
    const [weatherData, setWeatherData] = useState(false);
    const allIcons = {
        "01d": clearD_icon,
        "01n": clearN_icon,
        "02d": fewCloudD_icon,
        "02n": fewCloudN_icon,
        "03d": scatteredCloudD_icon,
        "03n": scatteredCloudN_icon,
        "04d": brokenCloudD_icon,
        "04n": brokenCloudN_icon,
        "09d": showerRainD_icon,
        "09n": showerRainN_icon,
        "10d": rainD_icon,
        "10n": rainN_icon,
        "11d": thunderstormD_icon,
        "11n": thunderstormN_icon,
        "13d": snowD_icon,
        "13n": snowN_icon,
        "50d": mistD_icon,
        "50n": mistN_icon
    }
    const search = async (city)=>{
        if(city === ""){
            alert("Enter City Name");
            return;
        }
        try {
            const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${import.meta.env.VITE_APP_ID}`;
            const response = await fetch(url);
            const data = await response.json();
            if(!response.ok){
                alert(data.message);
                return;
            }
            console.log(data);
            const icon = allIcons[data.weather[0].icon] || clear_icon;
            setWeatherData({
                humidity: data.main.humidity,
                windSpeed: data.wind.speed,
                temperature: Math.floor(data.main.temp),
                location: data.name,
                icon: icon
            })
        } catch (error){
            setWeatherData(false);
            console.error("Error in fetching weather data");
        }
    }
    useEffect(()=>{
        search("New York");
    },[])
  return (
    <div className='weather'>
      <div className='search-bar'>
        <input ref={inputRef} type='text' placeholder='Search'/>
        <img src={search_icon} alt="" onClick={()=>search(inputRef.current.value)}/>
      </div>
      {weatherData?<>
        <img src={weatherData.icon} alt="" className='weather-icon'/>
        <p className='temperature'>{weatherData.temperature}°C</p>
        <p className='location'>{weatherData.location}</p>
        <div className="weather-data">
            <div className="col">
                <img src={humidity_icon} alt=""/>
                <div>
                    <p>{weatherData.humidity} %</p>
                    <span>Humidity</span>
                </div>
            </div>
            <div className="col">
                <img src={wind_icon} alt=""/>
                <div>
                    <p>{weatherData.windSpeed} Km/h</p>
                    <span>Wind Speed</span>
                </div>
            </div>
        </div>
      </>:<></>}      
    </div>
  )
}

export default Weather
