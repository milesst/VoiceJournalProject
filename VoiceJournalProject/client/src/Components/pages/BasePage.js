import logo from '../../logo.svg';
import '../../App.css';
import Field from "../Field";
import Button from "../Button";
import LoginForm from "../LoginForm";
import VoiceInput from "../VoiceInput";
import SideMenu from "../SideMenu";
import LoginPage from './LoginPage';
import ClassPage from './ClassPage';
import { ProfilePage } from './ProfilePage';
import SchedulePage from './SchedulePage';
import React from "react";
import { useSwipeable } from "react-swipeable";
import SpeechRecognition, { useSpeechRecognition } from 'react-speech-recognition'
import { useEffect, useState } from 'react';
import { useHistory, useNavigate } from "react-router-dom";
import { DocumentPage } from './DocumentPage';

import SideBar from "../SideMenu";
import { GroupPage } from './GroupPage';
import { PastClassesPage } from './PastClassesPage';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import DisciplinesPage from './DisciplinesPage';
import { PastClassPage } from './PastClassPage';

const swipeOpenMenuStyles = {
  float: "left",
  position: "fixed",
  width: "5%",
  height: "100%",
  // border: "2px dashed gray"
};

function BasePage() {
  const [isOpen, setOpen] = React.useState(false);
  const handlers = useSwipeable({
    trackMouse: true,
    onSwipedRight: () => setOpen(true)
  });


    return (
      <div className="BasePage" id="base-page">
        <Router id="router-wrap">
          
        <div {...handlers} style={swipeOpenMenuStyles} />
        <SideBar
        isOpen={isOpen}
        onStateChange={s => setOpen(s.isOpen)}
        pageWrapId={"page-wrap"}
        outerContainerId={"router-wrap"}
        className="SideMenu"
        closeOnClick={() => {setOpen(false); console.log("fewf")}}
        />
        <div id="page-wrap">
            <Routes>
                <Route path="/login" element={<LoginPage/>}>
                </Route>
                <Route path="/class_record/:class_id" element={<PastClassPage/>}>
                </Route>
                <Route path="/classes/:id" element={<PastClassesPage/>}>
                </Route>
                <Route path="/disciplines" element={<DisciplinesPage/>}>
                </Route>
                <Route path="/class" element={<ClassPage/>}>
                </Route>  
                <Route path="/schedule" element={<SchedulePage/>}>
                </Route>
                <Route path="/profile" element={<ProfilePage/>}>
                </Route>
                <Route path="/groups/:id" element={<GroupPage/>}>
                </Route>
                <Route path="/documents" element={<DocumentPage/>}>
                </Route>
                <Route path="/" element={<VoiceInput/>}>
                </Route>
            </Routes>
        </div>
      </Router>
      </div>
      
    );
}

export default BasePage;
