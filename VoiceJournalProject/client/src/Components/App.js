import logo from '../logo.svg';
import '../App.css';
import Field from "./Field";
import Button from "./Button";
import LoginForm from "./LoginForm";
import BasePage from "./pages/BasePage";
import { useState } from 'react';
import LoginPage from './pages/LoginPage';
import useToken from './useToken';


function App() {
  const { token, setToken } = useToken();

  if(!token) {
    console.log("no token");
    console.log(token);
    console.log(setToken)
    return <LoginPage setToken={setToken} />
  }

  return (
    <div className="App">
        {/* <div className="burger-button-container">

        </div> */}
      <BasePage/>
    </div>
  );
}

export default App;
