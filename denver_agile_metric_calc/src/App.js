import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './routes/LoginPage'


function App() {
  return (
    <div className='container'>
        <LoginPage />
    </div>

  );
}

export default App;
