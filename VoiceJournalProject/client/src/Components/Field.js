import logo from '../logo.svg';
import '../App.css';
import "../LoginPage.css"


function Field(props) {
    return (
        <div className="Field">
            <input placeholder={props.value} type={props.type} onChange={e => props.setFun(e.target.value)}/>
        </div>
    );
}

export default Field;
