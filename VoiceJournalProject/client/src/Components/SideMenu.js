import logo from '../logo.svg';
import '../App.css';
import Field from "./Field";
import Button from "./Button";
import LoginForm from "./LoginForm";
import MenuItem from "./MenuItem";
import { Link } from 'react-router-dom';

import React from "react";
import { slide as Menu } from "react-burger-menu";
import { useHistory, useNavigate } from "react-router-dom";
import SpeechRecognition, { useSpeechRecognition } from 'react-speech-recognition'
import { useEffect, useState } from 'react';
import { getUserInfo, USER_INFO } from '../UserInfo';

function SideMenu() {
    return (
        <div className="SideMenu ">
            
        </div>
    );
}

// export default SideMenu;

export default props => {
  const navigate = useNavigate();
  const commands = [
    {
      command: "открыть *",
      callback: (page) => {
        console.log(page)
        switch (page) {
            case "дисциплины": 
            navigate("../disciplines", { replace: true }); break;
            case "расписание":
            navigate("../schedule", { replace: true }); break;   
        }
      },
    },
    {
        command: "начать пару",
        callback: () => {
              navigate("../class", { replace: true });
        },
    },
  ];
  const { transcript, resetTranscript } = useSpeechRecognition({ commands });
  const [isListening, setIsListening] = useState(false);
  
  const toggleVoiceNav = () => {
    console.log("toggle voice nav")
    if (!isListening) {
      setIsListening(true);
      SpeechRecognition.startListening({
        continuous: true,
      });
      
    }
    else {
      setIsListening(false);
      SpeechRecognition.stopListening();
    }
  }
  return (
      
    // Pass on our props
    <Menu {...props}>
      <div className="SideMenu-container">
                <Link to="/profile" className='link-profile'>
                    <div className="SideMenu-profile_container">
                    <div className="SideMenu-profile_wrapper">
                        <div className="SideMenu-profile_photo"></div>
                        <div className="SideMenu-profile_name">{getUserInfo().lastName} {getUserInfo().firstName} {getUserInfo().patronymic}</div>
                    </div>
                    </div>
                </Link>
                
                <div className="SideMenu-links_container">
                    <MenuItem closeOnClick={props.closeOnClick} name={"Расписание"}/>
                    <MenuItem closeOnClick={props.closeOnClick} name={"Дисциплины"}/>
                    <MenuItem closeOnClick={props.closeOnClick} name={"Начать пару"}/>
                    <MenuItem closeOnClick={props.closeOnClick} name={"Отчеты"}/>
                    <MenuItem  toggleVoiceNavFun={toggleVoiceNav} name={isListening ? "Выключить голос" : "Включить голос"}/>
                </div>
            </div>
    </Menu>
  );
};
