import logo from '../logo.svg';
import '../App.css';
import Field from "./Field";
import Button from "./Button";
import "../LoginPage.css"

function LoginForm(props) {

    return (
        <div className="LoginForm">
            <div className="login-label">Вход</div>
            <form className="LoginForm-form" onSubmit={props.handleSubmit}>
            <div className="Field">
            <input placeholder={"Логин"} type={"text"} onChange={e => props.setUserNameFun(e.target.value)}/>
            </div>
            <div className="Field">
            <input placeholder={"Пароль"} type={"password"} onChange={e => props.setPasswordFun(e.target.value)}/>
            </div>
                <Button type={"submit"}/>
            </form>
        </div>
    );
}

export default LoginForm;
