import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google'; // Import the GoogleOAuthProvider

import Login from './pages/login/Login';
import Signup from './pages/signup/signup';
import Profile from './pages/profile/Profile';
import GmailSignup from './pages/welcome/welcome'; 
import BookBrowsingPage from "./pages/BookBrowsing/BookBrowsingPage";
import SearchResultsPage from "./pages/SearchResults/SearchResultsPage";
import ForgetPasswordForm from './pages/forgetPassword/ForgetPasswordForm';
import ResetPasswordForm from './pages/forgetPassword/ResetPasswordForm';
import BookDetailsPage from "./pages/BookDetails/BookDetailsPage";
import BookShelfBooks from "./Components/BookShelves/BookShelfBooks.jsx";
import EmailVerification from './pages/EmailVerification/emailVerification.jsx';

function App() {
  return (
    <GoogleOAuthProvider clientId="441471832178-me4tprachirkf15ra6umrlff54i5utlu.apps.googleusercontent.com"> {}
      <Router>
          <main>
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/register" element={<GmailSignup />} />
              <Route path="/" element={<Login />} />
              <Route path="/BookBrowsingPage" element={<BookBrowsingPage />} />
              <Route path="/search/:category" element={<SearchResultsPage />} />
              <Route path="/forgetPassword" element={<ForgetPasswordForm />} />
              <Route path="/resetPassword/:token" element={<ResetPasswordForm />} />
              <Route path="/verify-email" element={<EmailVerification />} />
              <Route path="/book/:bookId" element={<BookDetailsPage />} />
              <Route path="/book/:bookId" element={<BookDetailsPage />} />
              <Route path="/BookBrowsingPage" element={<BookBrowsingPage />} />
              <Route path="/bookshelf/:bookShelfId" element={<BookShelfBooks />} />
            </Routes>
          </main>
      </Router>
    </GoogleOAuthProvider>
  );
}

export default App;
