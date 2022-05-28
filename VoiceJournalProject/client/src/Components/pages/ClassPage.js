import "../../ClassPage.css"
import StudentGrade from "../StudentGrade";
import axios from "axios";
import React, { useEffect, useState } from 'react';
import { BrowserRouter, useNavigate } from "react-router-dom";
import Timer from "../Timer";
import SpeechRecognition, { useSpeechRecognition } from 'react-speech-recognition'
import { getUserInfo, USER_INFO } from "../../UserInfo";

function getCurrentClass(data) {

    console.log("today possibble classes")
    console.log(data)
    let today = new Date();
    let time = today.getHours() + ":" + today.getMinutes();
    let result = null;
    let delta = 999999999;

    for (let classobj of data) {
        let classTimeArray = classobj.time.split(":");
        let classDate = new Date(today.getFullYear(), today.getMonth(), today.getDate(), Number(classTimeArray[0]), Number(classTimeArray[1]), 0);
        if (Math.abs(classDate - today) < delta) {
            delta = Math.abs(classDate - today);
            result = classobj;
        }
        console.log("mat habs: " + Math.abs(classDate - today))
        console.log("difference: " + (classDate - today));
    }

    return result;
}

function ClassPage() {
  const commands = [
    {
      command: "*",
      callback: (command) => {
        let commandSplit = command.split(" ");
        let grade = commandSplit[commandSplit.length-1];
        commandSplit.pop();
        let student = commandSplit.join(' ').toLowerCase();

      let finalResult = ["NO_STUDENT", grade]

      for (let stud of students) {
        if (stud.lastName.toLowerCase() === student) 
          finalResult[0] = student
      }

      if (finalResult[0] === "NO_STUDENT") {
        for (let posStud of dictionary) {
          for (let posRes of posStud.possibleResults) {
            if (student === posRes)
              finalResult[0] = posStud.lastName
          }

        }
      }

      console.log("EXECUTE COMMAND: " + finalResult[0] + " " + finalResult[1]);
      if (finalResult[0] != "NO_STUDENT") {

        let newStudents = students;
        let i = newStudents.findIndex(stud => stud.lastName.toLowerCase() === finalResult[0])
        if (listeningMode === "grade") {
          console.log("command grade: "+ finalResult[0] + " " + finalResult[1])
          newStudents[i].grade = finalResult[1];
          setStudents(students)
        }
        else if (listeningMode === "attendance") {
          console.log("index "+ i)
          if (grade === "нет" || grade === "отсутствует")
                newStudents[i].attendance = false
          if (grade === "здесь" || grade === "тут")
          setStudents(newStudents)
          console.log("command attend: " + finalResult[0] + " " + finalResult[1])
        }
          else console.log("nothing")  
      }
    },
  },
  ];
  const { transcript, resetTranscript } = useSpeechRecognition({ commands });
  const [isListening, setIsListening] = useState(false);
  const [listeningMode, setListeningMode] = useState(null);

  const [classInfo, setClassInfo] = useState(null);
  const [students, setStudents] = useState([]);
  const [time, setTime] = useState(0);
  const [value, setValue] = useState(0);

  const teacherId = getUserInfo().id;

  const [startTime, setStartTime] = useState(new Date().toLocaleString())
  const [dictionary, setDictionary] = useState(null);

  useEffect(() => {
    SpeechRecognition.lang = "ru-RU";

    axios.get("http://localhost:9090/dictionary").then((resp) => {
      const allPersons = resp.data.lastNames;
      setDictionary(allPersons);
    });
  }, [setDictionary]);

    const navigate = useNavigate();

    useEffect(() => {
        async function getClass() {
          const response = await axios.get(`http://localhost:9090/schedule?teacherId=${teacherId}&expand`);
          const data = response.data;

          let date = new Intl.DateTimeFormat('ru-RU', {weekday: 'long'}).format(Date.now());

          const todayClasses = data.filter(classInfo => classInfo.dayName.toLowerCase() == date)
          let curclass = getCurrentClass(todayClasses);
          console.log("TODAY CLASS: ")
          console.log(curclass);

          const response2 = await axios.get(`http://localhost:9090/student?groupId=${curclass.groupId}`);
          const students = response2.data;
          for (let i = 0; i < students.length; i++) {
              students[i].attendance = true;
              students[i].grade = 0;
          }
          setStudents(students);
          console.log(students)

          setClassInfo(curclass);
        }
        getClass();
      }, []);

      useEffect(() => {
        let interval = null;
          interval = setInterval(() => {
            setTime((time) => time + 10);
          }, 10);
      }, []);

    const checkAttendance = i => { console.log(i)
                                    let newStudents = students;
                                    newStudents[i].attendance = !newStudents[i].attendance;
                                    console.log("new attendance: ");
                                    console.log(newStudents[i].attendance)
    }

    const setGrade = i => {
      console.log(i);
      let newStudents = students;
      newStudents[i].grade = newStudents[i].grade + 1;
      console.log("new attendance: ");
      console.log(newStudents[i].grade)
    }

    async function endClass() {
      const endTime = new Date().toLocaleString()
      console.log(startTime);
      console.log(endTime);

      const classRecord = {
        scheduleId: classInfo.id,
        startDate: startTime,
        endDate: endTime
      }
      const response = await axios.post(`http://localhost:9090/class_record`, classRecord)
      console.log(response.data)

      for (let student of students) {
        console.log({classRecordId: response.data.id,
          studentId: student.id,
          attendance: student.attendance,
          grade: student.grade,
          note: ""});
        const response2 = await axios.post(`http://localhost:9090/student_work`, {
                                                                                  studentId: student.id,
                                                                                  classRecordId: response.data.id,
                                                                                attendance: student.attendance,
                                                                                note: "",
                                                                              grade: student.grade
                                                                            })
        console.log(response2.data);                                                                      

      }

      navigate(`../class_record/${response.data.id}`, { replace: true })
    }

    function setListenMode(mode) {
      setListeningMode(mode)
      if (mode === "grade") {
        setToggledButtonGr({bg: "#7D9FFC", color: "white"})
        setToggledButtonAt({});
      }
      if (mode === "attendance") {
        setToggledButtonAt({bg: "#7D9FFC", color: "white"})
        setToggledButtonGr({});
      }
      setIsListening(true);
      SpeechRecognition.startListening({
        continuous: true,
      });
    }

    useEffect(() => {
      console.log(transcript)
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [transcript]);

    const [toggledButtonGr, setToggledButtonGr] = useState({bg: "", color: ""});
    const [toggledButtonAt, setToggledButtonAt] = useState({bg: "", color: ""});

    return(
        <div className="ClassPage">
            <div className="class-info-wrap">
                <div className="class-duration"><Timer time={time}></Timer></div>
                <div className="class-info">
                    <div className="current-class-caption">текущая пара</div>
                    <div className="current-class-name">{classInfo != null ? classInfo.discipline.name : null}</div>
                    <div className="current-date">{new Date().toLocaleString("ru", {
                                                                                year: 'numeric',
                                                                                month: 'long',
                                                                                day: 'numeric',
                                                                                timezone: 'UTC'
                                                                                })} {classInfo != null ? classInfo.time.substring(0, 5) : ""}, {classInfo != null ? classInfo.group.groupNumber : ""}</div>
                    <div className="current-student-group">{classInfo != null ? "гр. " + classInfo.group.groupNumber : ""}</div>
                </div>
            </div>
            <div className="student-info-wrap">
                {students != null ? students.map(function(object, i){
                return <StudentGrade readonly={false} onClickGrade={() => setGrade(i)} value={object.grade} onClickAttendance={() => checkAttendance(i)} attendance={object.attendance} firstName={object.firstName} lastName={object.lastName} key={i} />;
                }) : "loading"}
            </div>
            <div className="voice-input-wrap">
                <div className="end-class-wrap">
                  <button className="end-class-button" onClick={endClass}>Завершить</button>
                  <button style={{background: toggledButtonGr.bg, color: toggledButtonGr.color}} onClick={() => {setListenMode("grade")}} className="set-grade-button">Баллы</button>
                  <button style={{background: toggledButtonAt.bg, color: toggledButtonAt.color}} onClick={() => setListenMode("attendance")} className="set-attendance-button">Посещения</button></div>
            </div>
        </div>
    )
}

export default ClassPage;