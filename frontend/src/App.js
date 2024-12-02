import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google'; // Import the GoogleOAuthProvider
import Signup from './pages/signup/signup';
import Login from './pages/login/Login';
import GmailSignup from './pages/welcome/welcome'; 

function App() {
  return (
    <GoogleOAuthProvider clientId="939403070001-juvk1couqnroi7ft04o3cqvjqi9hbqol.apps.googleusercontent.com"> {}
      <Router>
        <div>
          <main>
            <Routes>
              <Route path="/signup" element={<Signup />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<GmailSignup />} />
              <Route path="/" element={<GmailSignup />} />
            </Routes>
          </main>
        </div>
      </Router>
    </GoogleOAuthProvider>
  );
}

export default App;
