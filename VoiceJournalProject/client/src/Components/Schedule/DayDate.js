function DayDate(params) {
    return(
        <div className="DayDate">
            <div className="daynumber">{params.dayNumber}</div>
            <div className="day-name">{params.dayName}</div>
        </div>
    )
}

export default DayDate;