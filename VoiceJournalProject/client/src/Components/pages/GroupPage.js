import axios from "axios";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../../DisciplinesPage.css"
import { StudentCard } from "../StudentCard";
import "../../GroupPage.css";

export function GroupPage(props) {
    let { id } = useParams();
    const [students, setStudents] = useState(null);

    useEffect(() => {
        async function getStudents() {
            const response = await axios.get(`http://localhost:9090/student_group?groupNumber=${id}`)
            const data = response.data
            const response2 = await axios.get(`http://localhost:9090/student?groupId=${data[0].id}`)
            console.log(response2.data)
            setStudents(response2.data)
        }
        getStudents();
    }, []);

    let navigate = useNavigate();
    function goBack() {
        navigate(`../disciplines/`, {replace: true})
    }
    return (
        <div className="GroupPage">
            <img onClick={goBack} className="close-icon" src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/OOjs_UI_icon_close.svg/1200px-OOjs_UI_icon_close.svg.png" alt="" />
        <div className="page-label-wrap">
        Группа {id}
        </div>
        <div className="groups-wrap">
        {students != null ? students.map(function(object, i){
            return <StudentCard firstName={object.firstName} lastName={object.lastName} patronymic={object.patronymic}></StudentCard>
        })
        : ""}
        </div>
        </div>
    )
}