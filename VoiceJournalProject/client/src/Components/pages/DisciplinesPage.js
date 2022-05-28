import "../../DisciplinesPage.css"
import { DisciplineItem } from "../Discipline/DisciplineItem";
import axios from "axios";
import React, { useEffect, useState } from 'react';

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

function DisciplinesPage() {
    const [disciplines, setDisciplines] = useState(null);

    useEffect(() => {
        async function getDisciplines() {
          const response = await axios.get('http://localhost:9090/schedule?expand');
          const result = mapToGroups(response.data);

          setDisciplines(result);
          console.log(response.data)
        }
        getDisciplines();
      }, []);
      
    return (
        <div className="DisciplinesPage">
            <div className="page-label-wrap">
                Мои дисциплины
            </div>
            <div className="disciplines-wrap">
                {disciplines != null ? disciplines.map(function(object, i){
                return <DisciplineItem id={object.id} disciplineName={object.discipline} groups={object.groups} key={i} />;
                }) : "loading"}
            </div>
        </div>
    );
}

export default DisciplinesPage;