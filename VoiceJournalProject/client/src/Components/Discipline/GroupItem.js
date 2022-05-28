import { useNavigate } from "react-router-dom";
import "../../DisciplinesPage.css"

export function GroupItem(props) {
    const navigate = useNavigate();
    function openGroupList() {
        navigate(`../groups/${props.groupNumber}`, { replace: true });
    }
    console.log("groupitem:" + props.groupNumber)
    return (
        <div onClick={openGroupList} className="GroupItem">
            <div className="group-number-label">гр. {props.groupNumber}</div>
        </div>
    )
}