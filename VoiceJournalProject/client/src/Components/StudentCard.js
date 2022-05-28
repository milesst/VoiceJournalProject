import "../GroupPage.css";

export function StudentCard(props) {
    return (
        <div className="StudentCard">
            {props.lastName} {props.firstName} {props.patronymic}
        </div>
    )
}