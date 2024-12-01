import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Signup from './pages/signup/signup';

function App() {
  return (
    <Router>
      <div>
       

        <main>
          <Routes>
            <Route path="/signup" element={<Signup />} />
            <Route path="/" element={<div>LiBooks</div>} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;