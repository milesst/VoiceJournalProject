import logo from '../logo.svg';
import '../App.css';
import Field from "./Field";
import Button from "./Button";
import LoginForm from "./LoginForm";
import { Link } from "react-router-dom";

function MenuItem(props) {
    let link = "";
    switch (props.name) {
        case "Расписание": link = "/schedule"; break;
        case "Начать пару": link = "/class"; break;
        case "Дисциплины": link = "/disciplines"; break;
        case "Отчеты": link = "/documents"; break;
    }

    let onClickFun = null;
    if (props.toggleVoiceNavFun != null)
    onClickFun = props.toggleVoiceNavFun
    else {
        onClickFun = props.closeOnClick;
    }    

    return (
        <div onClick={onClickFun} className="MenuItem">
            <div className="MenuItem-container">
                {link !== "" ? <Link to={link} className="MenuItem-link"> {props.name}</Link> : props.name}
            </div>
        </div>
    );
}

export default MenuItem;
