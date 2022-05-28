import logo from '../logo.svg';
import '../App.css';

import "../LoginPage.css"

function Button(props) {
    return (
        <div className="Button">
            <input  className="login-btn" type={props.type} value={"Войти"}/>
        </div>
    );
}

export default Button;
