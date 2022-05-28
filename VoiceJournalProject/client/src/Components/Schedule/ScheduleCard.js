import { eventWrapper } from "@testing-library/user-event/dist/utils";
import DayDate from "./DayDate";
import ClassInfo from "./ClassInfo";

function ScheduleCard(props) {
    let events = props.events;
    if( props.events == null) 
    events = getInfo(props.dayDate); //получим массив объектов с инфой о занятиях
    
    events = events.sort(function compare(a, b) {if (a.time > b.time) return 1; if  (a.time < b.time) return -1; return 0})

    return(
        <div className="ScheduleCard">
            <DayDate dayNumber={props.dayNumber} dayName={props.dayName}></DayDate>
            <div className="events-wrap">
            {events.map(function(object, i) {
                return <ClassInfo time={object.time.substring(0, 5)} className={object.discipline.name} group={object.group.groupNumber} classroom={object.classroom}></ClassInfo>
            })}
            {/* <ClassInfo time={events[0].time.substring(0, 5)} className={events[0].discipline.name} group={events[0].group.groupNumber} classroom={events[0].classroom}></ClassInfo> */}
            </div>
        </div>
    )
}

function getInfo() {
    return(
        [
            {
                time: "10:10",
                discipline: {name:"CASE-технологии"},
                group: {groupNumber: "09-051"},
                classroom: "907"
            },
            {
                time: "10:10",
                discipline: {name:"CASE-технологии"},
                group: {groupNumber: "09-051"},
                classroom: "907"
            },
            {
                time: "10:10",
                discipline: {name:"CASE-технологии"},
                group: {groupNumber: "09-051"},
                classroom: "907"
            }
        ]
    )
}

function parseToJSX(event) {
    return <ClassInfo time={event.time} className={event.className} group={event.group} classroom={event.classroom}></ClassInfo>
}

export default ScheduleCard;