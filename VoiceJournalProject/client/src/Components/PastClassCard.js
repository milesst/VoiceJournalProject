import { useNavigate } from "react-router-dom"

export function PastClassCard(props) {
    const navigate = useNavigate();

    function openClassRecord() {
        navigate(`../class_record/${props.id}`, {replace: true})
    }

    function parseDate(date) {
        let strDate = date.substring(3, 5) + "/" + date.substring(0, 2) + "/" + date.substring(6, 10);
        console.log(strDate);

        return new Date(strDate)
    }

    return (
        <div className="card-wrap" onClick={openClassRecord}>
        <div className="info-wrap"><div className="class-date">
            {/* {props.date.toLocaleString("ru", {
                                                                                year: 'numeric',
                                                                                month: 'long',
                                                                                day: 'numeric',
                                                                                timezone: 'UTC'
                                                                                })} */}
                                                                                {props.date != null ? parseDate(props.date).toLocaleString("ru", {
                                                                                year: 'numeric',
                                                                                month: 'long',
                                                                                day: 'numeric',
                                                                                timezone: 'UTC'
                                                                                }) : "NO_DATE"}
                                                                                </div>
            <div className="class-group">группа {props.group}</div>
            <div className="class-classroom">аудитория {props.classroom}</div></div>
        <div className="details-wrap">
            <div className="icon"><img src="https://barbotex.pt/site/public/paginas/fe0126e9c317d055c214380bbc6a3702.png" alt="" /></div></div>    
        </div>
    )
}