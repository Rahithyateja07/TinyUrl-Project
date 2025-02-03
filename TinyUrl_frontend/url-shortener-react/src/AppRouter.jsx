import React from 'react'
import ShorternUrlPage from './components/ShorternUrlPage';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './components/NavBar';
import Footer from './components/Footer';
import { Toaster } from 'react-hot-toast';
import LandingPage from './components/LandingPage';
import AboutPage from './components/AboutPage';
import RegisterPage from './components/RegisterPage';
import LoginPage from './components/LoginPage';
import DashboardLayout from './components/dashboard/DashboardLayout';
import PrivateRoute from './PrivateRoute';
import ErrorPage from './components/ErrorPage';

const AppRouter = () => {
  const hideHeaderFooter = location.pathname.startsWith("/s");
  return (
    <>
        {!hideHeaderFooter && <Navbar /> }
        <Toaster position='bottom-center' />
        <Routes>
          <Route path='/' element={<LandingPage />} />
          <Route path='/about' element={<AboutPage />} />
          <Route path="/s/:url" element={<ShorternUrlPage />} />
          <Route path="/register" element={<PrivateRoute publicPage={true}><RegisterPage /></PrivateRoute>} />
          <Route path="/login" element={<PrivateRoute publicPage={true}><LoginPage /></PrivateRoute>} />
          <Route path="/dashboard" element={ <PrivateRoute publicPage={false}><DashboardLayout /></PrivateRoute>} />
          <Route path="/error" element={ <ErrorPage />} />
          <Route path="*" element={ <ErrorPage message="We can't seem to find the page you're looking for"/>} />
        </Routes>
        {!hideHeaderFooter && <Footer />}
      </>
  );
}

export default AppRouter;

export const SubDomainRouter = ()=>{
    return (

        <Routes>
          <Route path='/:url' element={<ShorternUrlPage/>} />
        </Routes>

    )
}