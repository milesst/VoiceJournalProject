import DayDate from "../Schedule/DayDate";
import "../../SchedulePage.css";
import ScheduleCard from "../Schedule/ScheduleCard";
import axios from "axios";
import React, { useEffect, useState } from 'react';
import { getUserInfo, USER_INFO } from "../../UserInfo";

function getCurrentWeekSpan() {
    let date = new Date();
    let day = date.getDate();
    let weekBeginning, weekEnd = 0;
    let week = date.getDay();
    weekBeginning = day - week + 1;
    weekEnd = day + (7 - week);
    console.log(week + " " + weekBeginning + " " + weekEnd)
    return [weekBeginning, weekEnd];
}

function SchedulePage(params) {
    const [schedule, setSchedule]= useState(null);
    const date = new Date();
    const [week, setWeek] = useState(getCurrentWeekSpan());
    // const teacherId = getUserInfo().id;
    const teacherId = 1;

    useEffect(() => {
        async function getSchedule() {
            const response = await axios.get(`http://localhost:9090/schedule?teacherId=${teacherId}&expand`)
            const data = response.data;
            console.log(data);
            setSchedule(data);
        }
    getSchedule();
    }, []);

    // useEffect(() => {
    //     setWeek(getCurrentWeekSpan(date));
    // })

    const changeWeek = e => {
        // if (e.target.textContent === "<") {
        //     date = new Date(date.getFullYear, date.getMonth, date.getDate - 7, date.getHours, date.getMinutes, date.getSeconds)
            
        // }
        // else {
        //     date = new Date(date.getFullYear, date.getMonth, date.getDate + 7, date.getHours, date.getMinutes, date.getSeconds)
        // }
    }

    return(
        <div className="SchedulePage">
            <div className="week-info-wrap">
                <div className="date-wrap">
                    <div className="current-week">{date.toLocaleString("ru", {month: 'long'}) } {week[0]}-{week[1]}</div>
                    <div className="current-year">{date.getFullYear()}</div>
                </div>
            </div>
            
                {/* <DayDate dayNumber="4" dayName="????"></DayDate>
                <DayDate dayNumber="5" dayName="????"></DayDate>
                <DayDate dayNumber="6" dayName="????"></DayDate>
                <DayDate dayNumber="7" dayName="????"></DayDate>
                <DayDate dayNumber="8" dayName="????"></DayDate>
                <DayDate dayNumber="9" dayName="????"></DayDate> */}
                {schedule != null ? (<div className="schedule-wrap">
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????????????????")} dayNumber={week[0]} dayName="????"></ScheduleCard>
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????????")} dayNumber={week[0]+1} dayName="????"></ScheduleCard>
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????")} dayNumber={week[0]+2} dayName="????"></ScheduleCard>
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????????")} dayNumber={week[0]+3} dayName="????"></ScheduleCard>
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????????")} dayNumber={week[0]+4} dayName="????"></ScheduleCard>
                <ScheduleCard events={schedule.filter(event => event.dayName=="??????????????")} dayNumber={week[0]+5} dayName="????"></ScheduleCard>
                </div>) : ""}
            {/* <div className="nav-wrap">
                <div onClick={(e) => changeWeek(e)} className="nav-button button-back">{"<"}</div>
                <div onClick={(e) => changeWeek(e)} className="nav-button button-forward">{">"}</div></div> */}
        </div>
    )
}

export default SchedulePage;