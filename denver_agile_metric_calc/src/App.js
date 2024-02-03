// App.js
import React from 'react';
import './App.css';
import ProjectComponent from './components/projectComponent';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          <h1>Taiga</h1>
          <ProjectComponent />
        </p>
      </header>
    </div>
  );
}

export default App;
