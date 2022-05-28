function ClassInfo(params) {
    return (
        <div className="ClassInfo">
            <div className="schedule-class-info-wrap">
            <div className="info"> <div className="schedulecard-header"><span className="time">{params.time}</span> ауд. {params.classroom}</div> 
                                    <span className="rest"></span>{params.className}<span className="group"><br />гр. {params.group}</span></div>
            <div className="class-name"></div>
            <div className="group"></div>
            <div className="classroom"></div></div>
        </div>
    )
}

export default ClassInfo;