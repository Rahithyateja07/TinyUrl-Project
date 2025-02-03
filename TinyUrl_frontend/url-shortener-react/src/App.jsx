import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { getApps } from './utils/helper'
function App() {
  const CurrentApp = getApps();

  return (

    <BrowserRouter>
      <CurrentApp />
    </BrowserRouter>
  )
}

export default App
