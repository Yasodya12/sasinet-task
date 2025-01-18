import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {LoginPage} from "./components/LoginPage.jsx";

import RegisterPage from "./components/RegisterPage.jsx";
import Dashboard from "./components/Dashboard.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
      <BrowserRouter>

          <Routes>
              <Route path="/" Component={LoginPage}/>
              <Route path="/home" element={<Dashboard />} />
              <Route path="/register" element={<RegisterPage />} />
          </Routes>

      </BrowserRouter>
  )
}

export default App
