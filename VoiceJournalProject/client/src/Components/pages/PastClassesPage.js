import { useEffect, useState } from "react"
import { PastClassCard } from "../PastClassCard";
import "../../PastClassesPage.css"
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { getUserInfo } from "../../UserInfo";

export function PastClassesPage(props) {
    let { id } = useParams();
    const [classRecords, setClassRecords] = useState(null);
    const [discipline, setDiscipline] = useState(null);
    const [schedule, setSchedule] = useState(null);
    const [group, setGroup] = useState(null);

    useEffect(() => {
        async function getClasses() {
            const response = await axios.get(`http://localhost:9090/schedule?disciplineId=${id}&teacherId=${getUserInfo().id}&expand`);
            console.log(response.data[0])

            // setSchedule(response.data[0]);
            // console.log("schedule")
            // console.log(schedule)
            
            // const response2 = await axios.get(`http://localhost:9090/class_record?scheduleId=${response.data[0].id}&expand`);
            setSchedule(response.data);
            let classRecords = [];
            for (let schedule of response.data) {
                const response2 = await axios.get(`http://localhost:9090/class_record?scheduleId=${schedule.id}&expand`);
                classRecords = [...classRecords, ...response2.data]
            }


            // setClassRecords(response2.data)
            setClassRecords(classRecords)
            console.log(classRecords)
            // console.log(classRecords)
        }
        getClasses();
        // console.log(discipline);
    }, [])

    // useEffect(() => {
    //     async function getClassRecords() {
    //         if (schedule != null) {
    //         const response = await axios.get(`http://localhost:9090/class_record?scheduleId=${schedule.id}&expand`);
    //         console.log(response.data)

    //          setClassRecords(response.data)
    //         }
    //     }
    //     getClassRecords();
    // }, [])

    let navigate = useNavigate();
    function goBack() {
        navigate(`../disciplines/`, {replace: true})
    }
    
    return (
        <div className="PastClassesPage">
            <img onClick={goBack} className="close-icon" src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/OOjs_UI_icon_close.svg/1200px-OOjs_UI_icon_close.svg.png" alt="" />
            <div className="header">{schedule != null ? schedule[0].discipline.name : ""}</div>
            <div className="classes-wrap">
                { (classRecords != null && classRecords.length === 0) ? "По данной дисциплине пар еще не проводилось" : ""}
                {classRecords != null ? classRecords.map(item => {return <PastClassCard id={item.id} date={item.startDate} group={item.schedule.group.groupNumber} classroom={item.schedule.classroom}></PastClassCard>}) : ""}
               
            </div>
        </div>
    )
}