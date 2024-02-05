import { useKeycloak } from '@react-keycloak/web'
import React from 'react'

const Navigation = () => {
    const {initialized, keycloak}= useKeycloak();
  return (
    <div> 
        <ul>
    <li><a href="/">Home Page </a></li>

    {keycloak && !keycloak.authenticated &&
        <li className="your-normal-styles hover:cursor-pointer"><a className="btn-link" onClick={() => keycloak.login()}>Login</a></li>
    }

    {keycloak && keycloak.authenticated &&
        <li className="your-normal-styles hover:cursor-pointer">
            <a className="btn-link" onClick={() => keycloak.logout()}>Logout ({
                keycloak.tokenParsed.preferred_username
            })</a>
        </li>
    }

</ul></div>
  )
}

export default Navigation