import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import { Provider } from 'react-redux'
import store from './store/store.js'
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './keycloak';
import "./index.css";

ReactDOM.createRoot(document.getElementById('root')).render(
  // <React.StrictMode>
     <ReactKeycloakProvider authClient={keycloak}>
    <Provider store={store}>
    <App />
    </Provider>
    </ReactKeycloakProvider>
//  </React.StrictMode>,
)
