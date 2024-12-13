import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google'; // Import the GoogleOAuthProvider
import Signup from './pages/signup/signup';
import Login from './pages/login/Login';
import GmailSignup from './pages/welcome/welcome'; 
import BookBrowsingPage from "./pages/BookBrowsing/BookBrowsingPage";

function App() {
  return (
    <GoogleOAuthProvider clientId="441471832178-me4tprachirkf15ra6umrlff54i5utlu.apps.googleusercontent.com"> {}
      <Router>
        <div>
          <main>
            <Routes>
              <Route path="/signup" element={<Signup />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<GmailSignup />} />
              <Route path="/" element={<Login />} />
              <Route path="/BookBrowsingPage" element={<BookBrowsingPage />} />
            </Routes>
          </main>
        </div>
      </Router>
    </GoogleOAuthProvider>
  );
}

export default App;
