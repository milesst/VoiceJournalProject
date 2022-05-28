import { GroupItem } from "./GroupItem"
import "../../DisciplinesPage.css"
import { useNavigate } from "react-router-dom";
export function DisciplineItem(props) {
    const navigate = useNavigate();
    // console.log("groups of " + props.disciplineName + ": " );
    console.log(props.groups);

    function openPastClasses() {
        navigate(`../classes/${props.id}`, {replace: true})
    }

    return (
        <div className="DisciplineItem">
            <div  onClick={openPastClasses} className="discipline-header">
                <div className="discipline-label">{props.disciplineName}</div>
                <div className="details-icon"><img src="https://barbotex.pt/site/public/paginas/fe0126e9c317d055c214380bbc6a3702.png" alt="" /></div>
            </div>
            <div className="group-list">
                {props.groups != null ? props.groups.map(function(object, i){
                return <GroupItem groupNumber={object.groupNumber} key={i} />;
                }) : "loading"}
            </div>
        </div>
    )
}

export default DisciplineItem;