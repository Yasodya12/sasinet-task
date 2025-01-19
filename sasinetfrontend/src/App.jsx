import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {LoginPage} from "./components/LoginPage.jsx";

import RegisterPage from "./components/RegisterPage.jsx";
import Dashboard from "./components/Dashboard.jsx";
import Navbar from "./components/Navbar.jsx";
import Loan from "./components/Loan.jsx";
import FixedDeposit from "./components/FixedDeposit.jsx";
import SavingAccount from "./components/SavingAccount.jsx";
import Transaction from "./components/Transaction.jsx";


function App() {
  const [count, setCount] = useState(0)

  return (
      <BrowserRouter>

          <Routes>
              <Route path="/" Component={LoginPage}/>
              <Route path="/home" element={<Dashboard />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/loan-account" element={<Loan />} />
              <Route path="/fixed-deposit-account" element={<FixedDeposit />} />
              <Route path="/saving-account" element={<SavingAccount />} />
              <Route path="/transaction" element={<Transaction />} />
          </Routes>

      </BrowserRouter>
  )
}

export default App
