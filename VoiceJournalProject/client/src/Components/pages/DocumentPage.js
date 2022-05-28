import { useState } from "react"
import { useEffect } from "react";
import axios from "axios";
import "../../DocumentPage.css";
import { getUserInfo } from "../../UserInfo";

function mapToGroups(data) {
    const disciplineList = [...new Set(data.map(item => item.discipline.name))];
    const disciplineIdList = [...new Set(data.map(item => item.discipline.id))];
    console.log(disciplineIdList)
    let result = [];

    for (let dis of disciplineList) {
        let groups = []
        let disId = 0;

        for (let obj of data) {
            if (obj.discipline.name == dis) {
                groups.push({
                    groupNumber: obj.group.groupNumber,
                    id: obj.groupId});
                
                    disId = obj.disciplineId;
            }
        }
        
        let disGroups = []
        for (let group of groups ) {
            if (disGroups.find(g => g.id === group.id) == null)
                disGroups.push(group);
        }
        result.push({
            id: disId,
            discipline: dis,
            groups: disGroups
        })
    }
    console.log("mapped:");
    console.log(result);
    return result;
}

export function DocumentPage() {
    const [disciplineChoice, setDisciplineChoice] = useState(null);
    const [disciplines, setDisciplines] = useState(null);

    useEffect(() => {
        let isMounted = true;
        async function getDisciplines() {
            const response = await axios.get(`http://localhost:9090/schedule?teacherId=${getUserInfo().id}&expand`);
            const result = mapToGroups(response.data);
            console.log(response.data)

            if (isMounted) {
                setDisciplines(result);
                setDisciplineChoice(result[0])
            // setDisciplineChoice(disciplines[0])
            }
          }
          getDisciplines();
          return () => isMounted = false;
      }, []);

    function refreshDisciplines(event) {
        for (let dis of disciplines) {
            if (dis.discipline === event.target.value)
                setDisciplineChoice(dis)
            console.log(dis)    
            console.log(dis.groups)
        }
     
    }

    function downloadDocument() {
        const teacherId = getUserInfo().id;
        window.open(`http://localhost:9090/document/grade_sheet/${teacherId}/2/1`);
    }

    return (
        <div className="DocumentPage">
            <div className="header">Отчет по успеваемости студентов</div>
            <div className="form-wrap">
                <p className="info">Чтобы сгенерировать ведомость, выберите нужную дисциплину и группу. <br></br><br /> Ведомость будет сгенерирована в формате .docx.</p>
                <form onSubmit={downloadDocument}>
                {/* <div className="wrapper"> */}
                <label htmlFor="discipline-choice" className="label">дисциплина</label>
                <select id="discipline-choice" onChange={e => refreshDisciplines(e)}>
                    {disciplines != null ? disciplines.map(dis => <option value={disciplines.id}>{dis.discipline}</option>) : ""}
                </select>
                {/* </div> */}
                <label htmlFor="group-choice" className="label">группа</label>
                <select id="group-choice">
                    {disciplineChoice != null ?
                        disciplineChoice.groups.map(group => <option value={group.id}>{group.groupNumber}</option>) : ""}
                </select>
                <input type="submit" value="Скачать ведомость" className="download" />
                </form>
            </div>
        </div>
    )
}