import '../../App.css';
import LoginForm from '../LoginForm';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { setUserInfo } from '../../UserInfo';

function LoginPage({ setToken }) {
    const [userName, setUserName] = useState();
    const [passWord, setPassword] = useState();

    function handleSubmit(e) {
        e.preventDefault();
        console.log(userName, passWord)
        axios.post(`http://localhost:9090/login`, {username: userName, password: passWord}, {headers: {
            // Authorization: `Bearer ${response.data.jwt}`
          }})
        .then(function(response) {
            console.log(response);
            setToken(response.data.token)
            console.log(response.data.token);
            setUserInfo({
                id: response.data.id,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
                patronymic: response.data.patronymic,
            })
            window.location.reload()
        })
    }

    return (
        <LoginForm setUserNameFun={setUserName} setPasswordFun={setPassword} handleSubmit={handleSubmit}></LoginForm>
    )
}

export default LoginPage;