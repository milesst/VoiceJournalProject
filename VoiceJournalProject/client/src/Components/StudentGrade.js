function StudentGrade(props) {
    return (
        <div className="StudentGrade">
            <div  className="student-name">{props.lastName} {props.firstName}</div>
            <div onClick={() => props.onClickAttendance()} className="student-presence">{props.attendance ? "✓" : "X"}</div>
            <div  className="student-grade" onClick={props.onClickGrade}><input max="100" value={props.value}   type="number" defaultValue={0}/></div>
        </div>
    )
}

export default StudentGrade;