import { useParams, useNavigate } from "react-router-dom";
import StudentGrade from "../StudentGrade";
import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";

function parseTime(s) {
    var msec = s;
    var hh = Math.floor(msec / 1000 / 60 / 60);
    msec -= hh * 1000 * 60 * 60;
    var mm = Math.floor(msec / 1000 / 60);
    msec -= mm * 1000 * 60;
    var ss = Math.floor(msec / 1000);
    msec -= ss * 1000;

    let mmstr = mm > 9? mm : "0" + mm;
    let ssstr = ss > 9? ss : "0" + ss;
    let hhstr = hh > 9? hh : "0" + hh;
    return(hhstr + ":" + mmstr + ":" + ssstr);
}

export function PastClassPage() {
    const [value, setValue] = useState(0);
    const [classRecord, setClassRecord] = useState(null);
    const [scheduleRecord, setScheduleRecord] = useState(null);
    const [students, setStudents] = useState(null);
    let {class_id} = useParams();

    useEffect(() => {
        async function getClass() {
          const response = await axios.get(`http://localhost:9090/class_record/${class_id}`);  
          console.log(response.data);
          const classRecordData = response.data;
          const response2 = await axios.get(`http://localhost:9090/schedule/${classRecordData.scheduleId}?expand`);
          console.log(response2.data);
          const scheduleData = response2.data;

          let date = new Intl.DateTimeFormat('ru-RU', {weekday: 'long'}).format(Date.now());

          const response3 = await axios.get(`http://localhost:9090/student?groupId=${scheduleData.groupId}`);
          console.log(response3.data)
          let studentsData = response3.data;
          
          const response4 = await axios.get(`http://localhost:9090/student_work?classRecordId=${class_id}`);
          const studentWorkData = response4.data;
          console.log(response4.data)
          
          for (let i = 0; i < studentsData.length; i++) {
            const studentWork = studentWorkData.find(item => item.studentId === studentsData[i].id)
            console.log(studentWork)
            studentsData[i].attendance = studentWork.attendance;
            studentsData[i].grade = studentWork.grade;
          }
          
          const classStartDate = classRecordData.startDate;
          const correctedStartDate =  classStartDate.substring(3, 5) + "." + classStartDate.substring(0, 2) + classStartDate.substring(5);
          classRecordData.startDate = correctedStartDate;

          const classEndDate = classRecordData.endDate;
          const correctedEndDate =  classEndDate.substring(3, 5) + "." + classEndDate.substring(0, 2) + classEndDate.substring(5);
          classRecordData.endDate = correctedEndDate;

          const date2 = new Date(new Date(correctedEndDate) - new Date(correctedStartDate));
          console.log(date2);

          setStudents(studentsData);
          setClassRecord(classRecordData);
          setScheduleRecord(scheduleData);
        }
        getClass();
      }, []);

    let navigate = useNavigate();
    function goBack() {
        navigate(`../classes/${scheduleRecord.disciplineId}`, {replace: true})
    }
    return(
        <div className="ClassPage">
            <img onClick={goBack} className="close-icon" src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/OOjs_UI_icon_close.svg/1200px-OOjs_UI_icon_close.svg.png" alt="" />
           
            <div className="class-info-wrap">
                <div className="class-duration">{classRecord != null ? parseTime(new Date(classRecord.endDate) - new Date(classRecord.startDate)) : ""}</div>
                <div className="class-info">
                    <div className="current-class-caption">запись пары</div>
                    <div className="current-class-name">{scheduleRecord != null ? scheduleRecord.discipline.name : ""}</div>
                    <div className="current-date">{classRecord != null ? new Date(classRecord.startDate).toLocaleString("ru", {
                                                                                year: 'numeric',
                                                                                month: 'long',
                                                                                day: 'numeric',
                                                                                timezone: 'UTC'
                                                                                }) : ""} {scheduleRecord != null ? scheduleRecord.time.substring(0, 5) + ", ауд. " + scheduleRecord.classroom : ""}</div>
                    <div className="current-student-group">{scheduleRecord != null ? "гр. " + scheduleRecord.group.groupNumber : ""}</div>
                    <div className="class-time">{classRecord != null ? classRecord.startDate.substring(11) + " —" + classRecord.endDate.substring(11) : ""}</div>
                </div>
            </div>
            <div className="student-info-wrap">
                {students != null ? students.map(function(object, i){
                return <StudentGrade readonly={true} value={object.grade} attendance={object.attendance} firstName={object.firstName} lastName={object.lastName} key={i} />;
                }) : "loading"}
            </div>
        </div>
    )
}